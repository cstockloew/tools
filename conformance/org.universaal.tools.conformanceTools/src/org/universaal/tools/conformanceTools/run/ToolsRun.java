package org.universaal.tools.conformanceTools.run;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;
import org.universaal.tools.conformance.filecheck.plugin.handler.FileCheckHandler;
import org.universaal.tools.conformanceTools.Activator;
import org.universaal.tools.conformanceTools.checks.api.CheckImpl;
import org.universaal.tools.conformanceTools.checks.impl.ActivatorCheck;
import org.universaal.tools.conformanceTools.checks.impl.Maven_nature;
import org.universaal.tools.conformanceTools.checks.impl.NameGroupID;
import org.universaal.tools.conformanceTools.checks.impl.OSGI_bundle;
import org.universaal.tools.conformanceTools.checks.impl.POM_file;
import org.universaal.tools.conformanceTools.markers.CTMarker;
import org.universaal.tools.conformanceTools.markers.Markers;
import org.universaal.tools.conformanceTools.utils.HtmlPage;
import org.universaal.tools.conformanceTools.utils.HtmlPage.Table;
import org.universaal.tools.conformanceTools.utils.RunPlugin;
import org.universaal.tools.conformanceTools.utils.Utilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ToolsRun {

	private static ToolsRun instance;

	private IProject projectToAnalyze;
	private IWorkbenchWindow window;
	private ISelection selection;

	private int bugIdCounter = -1;
	private ArrayList<BugDescriptor> bugsMap;
	private ArrayList<BugDescriptor> orderderbugsMap;

	private CTMarker markers;

	private final String fileNameResults = "uAAL_CTanalysisResult.html";
	private final String no_severity = "HINT";
	private final String low_severity = "INFO";
	private final String normal_severity = "WARNING";
	private final String high_severity = "ERROR";

	private final int lineNotPresent = -1;	

	private ToolsRun(){
		orderderbugsMap = new ArrayList<BugDescriptor>();
		markers = Markers.getInstance();		
	}

	public static synchronized ToolsRun getInstance(){
		if(instance == null)
			instance = new ToolsRun();

		return instance;
	}

	public void run(IWorkbenchWindow window, RunPlugin plugin) {

		this.window = window;

		this.selection = window.getSelectionService().getSelection("org.eclipse.jdt.ui.PackageExplorer");
		if(this.selection == null){
			this.selection = window.getSelectionService().getSelection("org.eclipse.ui.navigator.ProjectExplorer");
			System.out.println("uAAL CT - using selection from Project Explorer.");
		}
		else
			System.out.println("uAAL CT - using selection from Package Explorer.");

		if ((selection != null) && (selection instanceof StructuredSelection)) {

			Object selected = ((StructuredSelection) selection).getFirstElement();

			if (selected instanceof JavaProject)
				this.projectToAnalyze = ((JavaProject) selected).getProject();			
			else if (selected instanceof IProject)
				this.projectToAnalyze = ((IProject) selected);			
			else {
				MessageDialog.openInformation(window.getShell(),
						"uAAL Conformance Tools", "the selection is not a project or is broken.");

				return;
			}
			try{
				verifyImages();

				if(plugin == RunPlugin.CodeStyle){
					removeOldBugs();
					testConformance();
				}

				if(plugin == RunPlugin.CustomChecks){

					List<String> checks = new ArrayList<String>();
					checks.add(ActivatorCheck.class.getName());
					checks.add(Maven_nature.class.getName());
					checks.add(NameGroupID.class.getName());
					checks.add(OSGI_bundle.class.getName());
					checks.add(POM_file.class.getName());			
					//checks.add(UICallerImpl.class.getName());			

					List<Result> results = test(checks);
					visualizeResultsCC(results);
				}				
			}
			catch(Exception ex){ ex.printStackTrace(); }
		}
		else
			System.out.println("uAAL CT - no valid selection.");
	}

	public void run(IWorkbenchWindow window, RunPlugin plugin, ExecutionEvent ev) {

		if(plugin == RunPlugin.FileConformance){
			try {
				FileCheckHandler fch = new FileCheckHandler();			
				fch.execute(ev);

				if(fch.bugsMap != null){
					this.window = window;
					this.projectToAnalyze = fch.prj;
					verifyImages();

					visualizeResultsFC(fch.bugsMap);
				}
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private List<Result> test(List<String> checks){

		List<Result> results = new ArrayList<Result>();

		for(String c: checks){
			try{
				Object ck = Class.forName(c).newInstance();
				CheckImpl check = (CheckImpl) ck;

				String name = check.getCheckName();
				String description = check.getCheckDescription();

				String resultImg = check.check(this.projectToAnalyze);
				String resultDscr = check.getCheckResultDescription();

				results.add(new Result(name, description, resultImg, resultDscr));
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}

		if(results != null)
			return results;

		return null;
	}

	private void testConformance() throws CoreException {

		final String path_ = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName();

		Job job = new Job("AAL Studio") {
			protected IStatus run(IProgressMonitor monitor) {

				try{					
					// Continuous progress bar
					monitor.beginTask("uAAL CT - verifying conformance", IProgressMonitor.UNKNOWN);

					IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();

					if (projectToAnalyze != null && !projectToAnalyze.hasNature(IMavenConstants.NATURE_ID)) {
						monitor.done();
						return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - not a Maven project.");
					}

					IFile pomResource = projectToAnalyze.getFile(IMavenConstants.POM_FILE_NAME);
					if (pomResource == null) {
						monitor.done();
						return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - missing POM file.");
					}
					IMavenProjectFacade projectFacade = projectManager.create(pomResource, true, monitor);

					IMaven maven = MavenPlugin.getMaven();
					if(pomResource != null && projectFacade != null){
						MavenExecutionRequest request = projectManager.createExecutionRequest(pomResource, projectFacade.getResolverConfiguration(), monitor);

						List<String> goals = new ArrayList<String>();

						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IWorkspaceDescription description = workspace.getDescription();
						if (!description.isAutoBuilding())
							goals.add("compiler:compile"); // compile it if autobuilding is off

						goals.add("findbugs:findbugs");
						request.setGoals(goals);
						MavenExecutionResult resultFB = maven.execute(request, monitor);

						// two distinct executions trying to avoid "PermGen space" exception
						goals.clear();
						goals.add("checkstyle:checkstyle");
						request.setGoals(goals);
						MavenExecutionResult resultCK = maven.execute(request, monitor);

						if(resultFB.hasExceptions() || resultCK.hasExceptions()) {
							String errors = "Results: \n";
							for(Throwable e : resultCK.getExceptions()) {

								if(e.getCause() != null && e.getCause().getMessage() != null)
									errors += e.getCause().getMessage();
							}
							for(Throwable e : resultFB.getExceptions()) {

								if(e.getCause() != null && e.getCause().getMessage() != null)
									errors += e.getCause().getMessage();
							}

							monitor.done();
							System.out.println("uAAL CT - report blocked - "+errors);
							if(errors.contains("java.lang.OutOfMemoryError"))
								System.out.println("uAAL CT: verify start parameter [-XX:MaxPermSize] of AAL Studio and increase the value set.");

							return new Status(Status.ERROR, Activator.PLUGIN_ID, errors);
						}
						else{
							monitor.done(); // test monitor

							// generate report and visualize it
							monitor.beginTask("uAAL CT - reporting conformance tests.", IProgressMonitor.UNKNOWN);

							goals.clear();
							MavenExecutionRequest request2 = projectManager.createExecutionRequest(pomResource,
									projectFacade.getResolverConfiguration(), monitor);

							Properties props = new Properties();

							goals.add("findbugs:check");
							props.setProperty("findbugs.failOnError", "false");

							goals.add("checkstyle:check");
							props.setProperty("checkstyle.failOnViolation", "false");
							props.setProperty("checkstyle.failsOnError", "false");

							request2.setUserProperties(props);
							request2.setGoals(goals);
							MavenExecutionResult result2 = maven.execute(request2, monitor);

							if(result2.hasExceptions()) {
								String errors = "Results: \n";
								for(Throwable e : result2.getExceptions()) {

									if(e.getCause() != null && e.getCause().getMessage() != null)
										errors += e.getCause().getMessage();
								}

								monitor.done();
								return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - "+errors);
							}

							// visualize report - open report file
							window.getWorkbench().getDisplay().syncExec(new Runnable() {

								@Override
								public void run() {

									try{									
										int[] maxMinCK = parseCheckstyleResult(new File(path_+"/target/checkstyle-result.xml"));
										int[] maxMinFB = parseFindBugsResults(new File(path_+"/target/findbugsXml.xml"));

										int max = 0, min = 0;
										if(maxMinCK[0] >= maxMinFB[0])
											max = maxMinCK[0];
										else
											max = maxMinFB[0];
										if(maxMinCK[1] < maxMinFB[1])
											min = maxMinCK[1];
										else
											min = maxMinFB[1];

										orderBySeverity(max, min);
										visualizeResultsFromCK_FB();

										org.eclipse.core.filesystem.IFileStore fileStore = EFS.getLocalFileSystem().getStore(
												new File(path_+"/target/site/"+fileNameResults).toURI());

										try {
											if(fileStore != null)
												IDE.openEditorOnFileStore( window.getActivePage(), fileStore );
											else
												System.out.println("uAAL CT - can't open report file.");
										} 
										catch ( PartInitException e ) {
											e.printStackTrace();
										}
									}
									catch(Exception ex){
										ex.printStackTrace();
									}
								}
							});
						}
					}
					else
						return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - something went wrong.");
				}
				catch(Exception ex){
					ex.printStackTrace();
					monitor.done();
					return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT - save console log and e-mail it for debugging purpose.");
				}

				monitor.done();
				return new Status(Status.OK, Activator.PLUGIN_ID, "uAAL CT - good!");
			}
		};

		job.setUser(true);
		job.schedule();	
	}

	private int[] parseFindBugsResults(File xml){

		int maxSeverity = -1;
		int minSeverity = 1000;
		try{
			if(xml != null){
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document results = docBuilder.parse(xml);

				NodeList bugs = results.getElementsByTagName("BugInstance");				
				for(int i = 0; i < bugs.getLength(); i++){
					Node bug = bugs.item(i);

					BugDescriptor bd = new BugDescriptor();

					//bd.setPlugin(RunPlugin.FindBugs);

					if(bug.getNodeType() == Node.ELEMENT_NODE){
						Element bug_ = (Element) bug;

						try{
							bd.setLine(Integer.parseInt(getNodesContent(bug_.getChildNodes(), "SourceLine", "start").get(0)));
						}
						catch(Exception ex){
							bd.setLine(-1);
						}
						bd.setClazz(getNodesContent(bug_.getChildNodes(), "SourceLine", "sourcepath").get(0));
						bd.setDescr(getNodesContent(bug_.getChildNodes(), "LongMessage", null).get(0));
						bd.setErrorType(bug_.getAttribute("type").toLowerCase());
						try{
							bd.setSeverity(Integer.parseInt(bug_.getAttribute("rank"))); 
						}
						catch(Exception ex){
							bd.setSeverity(lineNotPresent);
						}
						bd.setSeverityDescription(getSeverityDescription(bd.getSeverity()));			

						if(bd.getSeverity() > maxSeverity)
							maxSeverity = bd.getSeverity();
						if(bd.getSeverity() < minSeverity)
							minSeverity = bd.getSeverity();

						bugsMap.add(bd);
					}
				}
			}

			//orderBySeverity(maxSeverity, minSeverity);
			markClasses();
			return new int[]{maxSeverity, minSeverity};
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return new int[]{0, 0};
	}

	private int[] parseCheckstyleResult(File xml){

		int min = 0, max = 0;
		try{
			if(xml != null){
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document results = docBuilder.parse(xml);

				NodeList files = results.getElementsByTagName("file");		
				for(int i = 0; i < files.getLength(); i++){
					Node file = files.item(i); 
					if(file.getNodeType() == Node.ELEMENT_NODE){
						Element file_ = (Element) file;			
						String fileName = file_.getAttribute("name");

						for(int j = 0; j < file_.getElementsByTagName("error").getLength(); j++){
							Node error = file_.getElementsByTagName("error").item(j);
							if(error.getNodeType() == Node.ELEMENT_NODE){
								Element error_ = (Element) error;

								BugDescriptor bd = new BugDescriptor();
								//bd.setPlugin(RunPlugin.CheckStyle);

								String separator = "\\\\";
								String[] path = fileName.trim().split(separator);
								bd.setClazz(path[path.length-1]);

								try{
									bd.setLine(Integer.parseInt(error_.getAttribute("line"))); 
								}
								catch(Exception ex){
									bd.setLine(lineNotPresent);
								}
								String severity = error_.getAttribute("severity"); //severity -> number
								if(severity.equalsIgnoreCase("ignore")){
									bd.setSeverity(0); min = 0; //3
								}
								else if(severity.equalsIgnoreCase("info"))
									bd.setSeverity(3); //8
								else if(severity.equalsIgnoreCase("warning"))
									bd.setSeverity(8); //13
								else if(severity.equalsIgnoreCase("error")){
									bd.setSeverity(13); max = 13; // 19
								}
								bd.setSeverityDescription(getSeverityDescription(bd.getSeverity()));

								bd.setDescr(error_.getAttribute("message"));
								String[] type = error_.getAttribute("source").trim().split("\\."); //errorType
								bd.setErrorType(type[type.length - 1]); 

								bugsMap.add(bd);
							}
						}
					}
				}
			}

			//orderBySeverity(max, min);
			markClasses();
			return new int[]{max, min};
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return new int[]{0, 0};
	}

	private void removeOldBugs(){

		try{
			bugsMap = new ArrayList<BugDescriptor>();

			int k = 0; 
			for(int i = 0; i < orderderbugsMap.size(); i++){
				if(orderderbugsMap.get(i) != null)
					//if(orderderbugsMap.get(i).getPlugin() == this.plugin){
					orderderbugsMap.set(i, null);
				k++;
				//}
			}
			System.out.println("uAAL CT - deleted "+k+" bug instances.");

			markers.deleteAll(/*this.plugin*/);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void orderBySeverity(int max, int min){

		try{
			int k = max;

			while(k >= min){
				for(int i = 0; i < bugsMap.size(); i++){

					BugDescriptor error = bugsMap.get(i);					

					if(error.getSeverity() == k){
						error.setFrontEndID(++bugIdCounter);
						orderderbugsMap.add(error);
					}									
				}

				k--;
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void markClasses(){

		try {
			ArrayList<ICompilationUnit> cus = Utilities.getAllClasses(this.projectToAnalyze);
			for(int i = 0; i < cus.size(); i++){
				for(int j = 0; j < orderderbugsMap.size(); j++){
					if(orderderbugsMap.get(j) != null && orderderbugsMap.get(j).getMarkerID() == -1)
						if(cus.get(i).getCorrespondingResource().getName().equalsIgnoreCase(orderderbugsMap.get(j).getClazz())){
							orderderbugsMap.get(j).setCu(cus.get(i));				

							Map<String, Object> attributes = new HashMap<String, Object>();
							attributes.put(IMarker.LINE_NUMBER, orderderbugsMap.get(j).getLine());
							attributes.put(IMarker.MESSAGE, orderderbugsMap.get(j).getDescr());
							attributes.put(IMarker.LOCATION, orderderbugsMap.get(j).getFrontEndID());
							attributes.put(IMarker.LINE_NUMBER, orderderbugsMap.get(j).getLine()); 
							attributes.put(IMarker.TEXT, orderderbugsMap.get(j).getErrorType());

							if(!orderderbugsMap.get(j).getSeverityDescription().equals(no_severity)){

								//attributes.put(IMarker.SOURCE_ID, orderderbugsMap.get(j).getPlugin());

								if(orderderbugsMap.get(j).getSeverityDescription().equals(low_severity)){
									attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
									attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_LOW);
								}
								else if(orderderbugsMap.get(j).getSeverityDescription().equals(normal_severity)){
									attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
									attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
								}
								else if(orderderbugsMap.get(j).getSeverityDescription().equals(high_severity)){
									attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);			
									attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
								}
								orderderbugsMap.get(j).setMarkerID(markers.createMarker(this.projectToAnalyze, cus.get(i).getCorrespondingResource(), attributes, false));
							}
						}
				}
			}

		} catch (CoreException e1) {
			e1.printStackTrace();
		}
	}

	private List<String> getNodesContent(NodeList elements, String nodeName, String attribute){

		List<String> result = new ArrayList<String>();
		if(elements != null && nodeName != null){
			for(int i = 0; i < elements.getLength(); i++){
				Node node = elements.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element node_ = (Element) node;
					if(node_.getNodeName().equalsIgnoreCase(nodeName))
						if(attribute != null)
							result.add(node_.getAttribute(attribute));
						else
							result.add(node_.getTextContent());
				}
			}
		}

		return result;
	}

	private void visualizeResultsFromCK_FB(){

		try{
			HtmlPage page = new HtmlPage("uAAL CONFORMANCE TOOLS - ANALYSIS RESULTS");
			String path_ = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/images/logos/maven-feather.png";
			page.getBody().addElement("<img src='"+path_+"' alt='Maven Logo' /><br/><br/>");

			Table t = page.new Table(getBugsNumber()+2, 6);

			// table header
			t.addContentCentered("<font size='5'><b>MARK NUMBER</b></font>", 0, 0);
			t.addContentCentered("<font size='5'><b>ERROR TYPE</b></font>", 0, 1);
			t.addContentCentered("<font size='5'><b>SEVERITY</b></font>", 0, 2);
			t.addContentCentered("<font size='5'><b>CLASS</b></font>", 0, 3);
			t.addContentCentered("<font size='5'><b>DESCRIPTION</b></font>", 0, 4);
			t.addContentCentered("<font size='5'><b>LINE</b></font>", 0, 5);

			int j = 0, mark = 0;
			for(int i = 0; i < orderderbugsMap.size(); i++){
				if(orderderbugsMap.get(i) != null){					
					t.addContentCentered(++mark+"", mark, j);			
					t.addContentCentered(orderderbugsMap.get(i).getErrorType(), mark, ++j);
					t.addContentCentered(orderderbugsMap.get(i).getSeverityDescription(), mark, ++j);
					t.addContentCentered(orderderbugsMap.get(i).getClazz(), mark, ++j);
					t.addContentCentered(orderderbugsMap.get(i).getDescr(), mark, ++j);
					if(orderderbugsMap.get(i).getLine() != lineNotPresent)
						t.addContentCentered(orderderbugsMap.get(i).getLine()+"", mark, ++j);
					else
						t.addContentCentered("n.a.", mark, ++j);
					j = 0;
				}
			}

			page.getBody().addElement(t.getTable());
			page.getBody().addElement("<br/><br/><p>Total: "+getBugsNumber()+" marks.</p>");
			String path = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/"+fileNameResults;
			page.write(new File(path));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void visualizeResultsCC(List<Result> results){

		try{
			HtmlPage page = new HtmlPage("uAAL CONFORMANCE TOOLS - ANALYSIS RESULTS");
			String path_ = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/images/logos/";

			Table t = page.new Table(results.size()+2, 4);

			// table header
			t.addContentCentered("<font size='5'><b>TEST RESULT</b></font>", 0, 0);
			t.addContentCentered("<font size='5'><b>TEST NAME</b></font>", 0, 1);
			t.addContentCentered("<font size='5'><b>TEST DESCRIPTION</b></font>", 0, 2);
			t.addContentCentered("<font size='5'><b>RESULT DESCRIPTION</b></font>", 0, 3);

			for(int i = 0; i < results.size(); i++){
				Result check = results.get(i);
				t.addContentCentered("<img src='"+path_+check.getResultImg()+"' />", i+1, 0);		
				t.addContentCentered(check.getCheckName(), i+1, 1);			
				t.addContentCentered(check.getCheckDescription(), i+1, 2);
				t.addContentCentered(check.getResultDscr(), i+1, 3);
			}

			page.getBody().addElement(t.getTable());
			page.getBody().addElement("<br/><br/><p>Total: "+results.size()+" performed test(s).</p>");
			String filePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/"+fileNameResults;
			File file = new File(filePath);
			page.write(file);

			IDE.openEditorOnFileStore( window.getActivePage(), EFS.getLocalFileSystem().getStore(file.toURI()) );
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void visualizeResultsFC(List<BugDescriptor> bugs){

		try{
			HtmlPage page = new HtmlPage("uAAL CONFORMANCE TOOLS - ANALYSIS RESULTS");

			Table t = page.new Table(bugs.size()+2, 3);

			// table header
			t.addContentCentered("<font size='5'><b>TEST NAME</b></font>", 0, 0);
			t.addContentCentered("<font size='5'><b>TEST RESULT</b></font>", 0, 1);
			t.addContentCentered("<font size='5'><b>SEVERITY</b></font>", 0, 2);

			for(int i = 0; i < bugs.size(); i++){
				BugDescriptor check = bugs.get(i);
				t.addContentCentered(check.getErrorType(), i+1, 0); // File Check			
				t.addContentCentered(check.getDescr(), i+1, 1); // result
				t.addContentCentered(getSeverityDescription(check.getSeverity()), i+1, 2); // severity
			}

			page.getBody().addElement(t.getTable());
			page.getBody().addElement("<br/><br/><p>Total: "+bugs.size()+" performed test(s).</p>");
			String filePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/"+fileNameResults;
			File file = new File(filePath);
			page.write(file);

			IDE.openEditorOnFileStore( window.getActivePage(), EFS.getLocalFileSystem().getStore(file.toURI()) );
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private int getBugsNumber(){
		int j = 0;

		for(int i = 0; i < orderderbugsMap.size(); i++) 
			if(orderderbugsMap.get(i) != null)
				j++;

		return j;
	}

	private void verifyImages(){

		try{	
			String destAbsPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+this.projectToAnalyze.getDescription().getName();
			String destInternalProjectPath = "/target/site/images/";

			Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);

			File sourceDir;
			File[] icons = new File[10];

			if(!Activator.absolutePath.endsWith("jar")){
				sourceDir = new File(Activator.absolutePath+"/icons/");
				icons = sourceDir.listFiles();
			}
			else{
				List<String> files = new ArrayList<String>();
				files.add("icons/icon_info_sml.gif");
				files.add("icons/icon_question_sml.gif");
				files.add("icons/icon_success_sml.gif");
				files.add("icons/icon_warning_sml.gif");
				files.add("icons/maven-feather.png");
				files.add("icons/test.png");

				for(int i = 0; i < files.size(); i++)
					icons[i] = new File(Activator.getBundleContext().getBundle().getResource(files.get(i)).getFile());			
			}

			File target, site, images, logos; // subdirectories structure
			target = new File(destAbsPath+"/target");
			if(!target.exists())
				target.mkdir();

			site = new File(destAbsPath+"/target/site");
			if(!site.exists())
				site.mkdir();

			images = new File(destAbsPath+"/target/site/images");
			if(!images.exists())
				images.mkdir();

			logos = new File(destAbsPath+"/target/site/images/logos/");
			if(!logos.exists()) 
				logos.mkdir();

			for(File icon: icons){
				Utilities.copyFile(bundle, destAbsPath+destInternalProjectPath, icon.getName(), "/icons/");
				Utilities.copyFile(bundle, destAbsPath+destInternalProjectPath+"logos/", icon.getName(), "/icons/");
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private String getSeverityDescription(int sev){

		if(sev < 4)
			return no_severity;
		else if(sev < 9)
			return low_severity;
		else if(sev < 14)
			return normal_severity;
		else if(sev < 20)
			return high_severity;

		return no_severity;
	}
}