package org.universaal.tools.conformanceTools.run;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.internal.registry.Contribution;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.menus.IContributionRoot;
import org.osgi.framework.Bundle;
import org.universaal.tools.conformanceTools.Activator;
import org.universaal.tools.conformanceTools.markers.CTMarker;
import org.universaal.tools.conformanceTools.markers.MarkerResolution;
import org.universaal.tools.conformanceTools.markers.Markers;
import org.universaal.tools.conformanceTools.utils.HtmlPage;
import org.universaal.tools.conformanceTools.utils.HtmlPage.Table;
import org.universaal.tools.conformanceTools.utils.RunPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ToolsRun {

	private RunPlugin plugin;
	private IProject projectToAnalyze;
	private IWorkbenchWindow window;
	private ISelection selection;

	private int bugIdCounter = -1;
	private ArrayList<BugDescriptor> bugsMap = new ArrayList<BugDescriptor>();
	private ArrayList<BugDescriptor> orderderbugsMap = new ArrayList<BugDescriptor>();

	private CTMarker markers = new Markers();
	
	private final String no_severity = "HINT";
	private final String low_severity = "INFO";
	private final String normal_severity = "WARNING";
	private final String high_severity = "ERROR";
	
	private final int lineNotPresent = -1;

	public void run(IWorkbenchWindow window, RunPlugin plugin) {

		this.plugin = plugin;
		this.window = window;

		this.selection = window.getSelectionService().getSelection("org.eclipse.jdt.ui.PackageExplorer");
		if(this.selection == null){
			this.selection = window.getSelectionService().getSelection("org.eclipse.ui.navigator.ProjectExplorer");
			System.out.println("uAAL CT: using selection from Project Explorer.");
		}
		else
			System.out.println("uAAL CT: using selection from Package Explorer.");

		if ((selection != null) && (selection instanceof StructuredSelection)) {

			Object selected = ((StructuredSelection) selection).getFirstElement();

			if (selected instanceof JavaProject)
				this.projectToAnalyze = ((JavaProject) selected).getProject();			
			else if (selected instanceof IProject)
				this.projectToAnalyze = ((IProject) selected);			
			else {
				MessageDialog.openInformation(window.getShell(),
						"uAAL Conformance Tools", "Not a project.");

				return;
			}
			try{
				markers.deleteAll(this.projectToAnalyze);
				testConformance();
			}
			catch(Exception ex){ ex.printStackTrace(); }
		}
		else
			System.out.println("uAAL CT: no valid selection.");
	}

	private void testConformance() throws CoreException {

		final String path_ = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName();

		Job job = new Job("AAL Studio") {
			protected IStatus run(IProgressMonitor monitor) {

				try{					
					// Continuous progress bar
					monitor.beginTask("uAAL - verifying conformance", IProgressMonitor.UNKNOWN);

					IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();

					if (projectToAnalyze != null && !projectToAnalyze.hasNature(IMavenConstants.NATURE_ID)) {
						monitor.done();
						return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL - not a Maven project.");
					}

					IFile pomResource = projectToAnalyze.getFile(IMavenConstants.POM_FILE_NAME);
					if (pomResource == null) {
						monitor.done();
						return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL - missing POM file.");
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

						if(plugin == RunPlugin.FindBugs)
							goals.add("findbugs:findbugs");

						else if(plugin == RunPlugin.CheckStyle)
							goals.add("checkstyle:checkstyle");

						request.setGoals(goals);
						MavenExecutionResult result = maven.execute(request, monitor);

						if(result.hasExceptions()) {
							String errors = "Results: \n";
							for(Throwable e : result.getExceptions()) {

								if(e.getCause() != null && e.getCause().getMessage() != null)
									errors += e.getCause().getMessage();
							}

							monitor.done();
							System.out.println("uAAL CT: report blocked - "+errors);
							if(errors.contains("java.lang.OutOfMemoryError"))
								System.out.println("uAAL CT: verify start parameter [-XX:MaxPermSize] of AAL Studio and increase the value set.");

							return new Status(Status.ERROR, Activator.PLUGIN_ID, errors);
						}
						else{
							monitor.done(); // test monitor

							// generate report and visualize it
							monitor.beginTask("uAAL - reporting conformance tests.", IProgressMonitor.UNKNOWN);

							goals.clear();
							MavenExecutionRequest request2 = projectManager.createExecutionRequest(pomResource,
									projectFacade.getResolverConfiguration(), monitor);

							Properties props = new Properties();
							if(plugin == RunPlugin.FindBugs){

								goals.add("findbugs:check");
								props.setProperty("findbugs.failOnError", "false");
							}
							else if(plugin == RunPlugin.CheckStyle){

								goals.add("checkstyle:check");
								props.setProperty("checkstyle.failOnViolation", "false");
								props.setProperty("checkstyle.failsOnError", "false");
							}

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
								return new Status(Status.ERROR, Activator.PLUGIN_ID, "uAAL CT: "+errors);
							}

							// visualize report - open report file
							window.getWorkbench().getDisplay().syncExec(new Runnable() {

								@Override
								public void run() {

									try{									
										File f = null;

										if(plugin == RunPlugin.CheckStyle){
											f = new File(path_+"/target/checkstyle-result.xml"); 
										}

										if(plugin == RunPlugin.FindBugs)
											f = new File(path_+"/target/findbugsXml.xml"); 

										if (f != null && f.exists() ){
											if(plugin == RunPlugin.FindBugs){ 
												parseFindBugsResults(f);
												f = new File(path_+"/target/site/findbugs.html");
											}
											else if(plugin == RunPlugin.CheckStyle){
												parseCheckstyleResult(f);
												f = new File(path_+"/target/site/checkstyle.html");
											}

											org.eclipse.core.filesystem.IFileStore fileStore = EFS.getLocalFileSystem().getStore(f.toURI());
											IWorkbenchPage page = window.getActivePage();

											try {
												verifyImages();
												if(page != null && fileStore != null)
													IDE.openEditorOnFileStore( page, fileStore );
												else
													System.out.println("uAAL CT: can't open report file - "+plugin);
											} 
											catch ( PartInitException e ) {
												e.printStackTrace();
											}

										} 
										else {
											if(plugin == RunPlugin.CheckStyle)
												System.out.println("uAAL CT: does file "+path_+"/target/site/checkstyle.html"+" exist?");
											if(plugin == RunPlugin.FindBugs)
												System.out.println("uAAL CT: does file "+path_+"/target/site/findbugs.html"+" exist?");
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
				return new Status(Status.OK, Activator.PLUGIN_ID, "uAAL CT - all ok!");
			}
		};

		job.setUser(true);
		job.schedule();	
	}

	private void parseFindBugsResults(File xml){

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
						bd.setErrorType(bug_.getAttribute("type"));
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

			orderBySeverity(maxSeverity, minSeverity);
			markClasses();

			visualizeFindBugsResults();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void parseCheckstyleResult(File xml){

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
								if(severity.equalsIgnoreCase("ignore"))
									bd.setSeverity(3);
								else if(severity.equalsIgnoreCase("info"))
									bd.setSeverity(8);
								else if(severity.equalsIgnoreCase("warning"))
									bd.setSeverity(13);
								else if(severity.equalsIgnoreCase("error"))
									bd.setSeverity(19);
								bd.setSeverityDescription(getSeverityDescription(bd.getSeverity()));

								bd.setDescr(error_.getAttribute("message"));
								bd.setErrorType(error_.getAttribute("source")); //errorType

								bugsMap.add(bd);
							}
						}
					}
				}
			}

			orderBySeverity(19, 3);
			markClasses();

			//visualizaCheckStyleResults(); //html page already done
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

				if(orderderbugsMap.size() == bugsMap.size())
					break;

				k--;
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void markClasses(){

		try {
			ArrayList<ICompilationUnit> cus = getAllClasses();
			for(int i = 0; i < cus.size(); i++){
				for(int j = 0; j < orderderbugsMap.size(); j++){
					if(cus.get(i).getCorrespondingResource().getName().equalsIgnoreCase(orderderbugsMap.get(j).getClazz())){
						orderderbugsMap.get(j).setCu(cus.get(i));				

						// marker types: TASK, PROBLEM, TEXT, BOOKMARK
						Map<String, Object> attributes = new HashMap<String, Object>();
						attributes.put(IMarker.LINE_NUMBER, orderderbugsMap.get(j).getLine());
						attributes.put(IMarker.MESSAGE, orderderbugsMap.get(j).getDescr());
						attributes.put(IMarker.SOURCE_ID, orderderbugsMap.get(j).getFrontEndID());
						attributes.put(IMarker.LINE_NUMBER, orderderbugsMap.get(j).getLine());

						if(orderderbugsMap.get(j).getSeverityDescription().equals(no_severity)){
							// ignore
						}
						else if(orderderbugsMap.get(j).getSeverityDescription().equals(low_severity)){
							attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
							attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_LOW);

							System.out.println("uAAL CT: generating marker BOOKMARK as SEVERITY_INFO and priority PRIORITY_LOW for "+orderderbugsMap.get(j).getDescr()+" at line "+orderderbugsMap.get(j).getLine());
							if(orderderbugsMap.get(j).getLine() != lineNotPresent) // no line to refer, no marker
								orderderbugsMap.get(j).setMarkerID(markers.createMarker(this.projectToAnalyze, cus.get(i).getCorrespondingResource(), attributes, CTMarker.BOOKMARK));
						}
						else if(orderderbugsMap.get(j).getSeverityDescription().equals(normal_severity)){
							attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
							attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);

							System.out.println("uAAL CT: generating marker TASK as SEVERITY_WARNING and priority PRIORITY_NORMAL for "+orderderbugsMap.get(j).getDescr()+" at line "+orderderbugsMap.get(j).getLine());
							if(orderderbugsMap.get(j).getLine() != lineNotPresent) // no line to refer, no marker
								orderderbugsMap.get(j).setMarkerID(markers.createMarker(this.projectToAnalyze, cus.get(i).getCorrespondingResource(), attributes, CTMarker.TASK));
						}
						else if(orderderbugsMap.get(j).getSeverityDescription().equals(high_severity)){
							attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);			
							attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);

							System.out.println("uAAL CT: generating marker PROBLEM as SEVERITY_ERROR and PRIORITY_HIGH for "+orderderbugsMap.get(j).getDescr()+" at line "+orderderbugsMap.get(j).getLine());
							if(orderderbugsMap.get(j).getLine() != lineNotPresent) // no line to refer, no marker
								orderderbugsMap.get(j).setMarkerID(markers.createMarker(this.projectToAnalyze, cus.get(i).getCorrespondingResource(), attributes, CTMarker.PROBLEM, true));
						}
					}
				}
			}

			//??force opening task list view

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

	public void visualizeFindBugsResults(){

		try{
			HtmlPage page = new HtmlPage("FINDBUGS ANALYSIS RESULTS");
			String path_ = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/images/logos/maven-feather.png";
			page.getBody().addElement("<img src='"+path_+"' alt='Maven Logo'><br/><br/>");

			Table t = page.new Table(orderderbugsMap.size()+2, 5);

			// table header
			t.addContent("<font size='5'><b><center>ERROR TYPE</center></b></font>", 0, 0);
			t.addContent("<font size='5'><b><center>SEVERITY</center></b></font>", 0, 1);
			t.addContent("<font size='5'><b><center>CLASS</center></b></font>", 0, 2);
			t.addContent("<font size='5'><b><center>DESCRIPTION</center></b></font>", 0, 3);
			t.addContent("<font size='5'><b><center>LINE</center></b></font>", 0, 4);

			int j = 0;
			for(int i = 0; i < orderderbugsMap.size(); i++){
				t.addContent("<center>"+orderderbugsMap.get(i).getErrorType()+"</center>", i+2, j);
				t.addContent("<center>"+orderderbugsMap.get(i).getSeverity()+"</center>", i+2, ++j);
				t.addContent("<center>"+orderderbugsMap.get(i).getClazz()+"</center>", i+2, ++j);
				t.addContent("<center>"+orderderbugsMap.get(i).getDescr()+"</center>", i+2, ++j);
				if(orderderbugsMap.get(i).getLine() != lineNotPresent)
					t.addContent("<center>"+orderderbugsMap.get(i).getLine()+"</center>", i+2, ++j);
				else
					t.addContent("<center>n.a.</center>", i+2, ++j);
				j = 0;
			}

			page.getBody().addElement(t.getTable());
			String path = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+projectToAnalyze.getDescription().getName()+"/target/site/findbugs.html";
			page.write(new File(path));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void verifyImages(){

		try{
			String errFN = "icon_error_sml.gif";
			String infFN = "icon_info_sml.gif";
			String warnFN = "icon_warning_sml.gif";
			String mavFN = "maven-feather.png";

			String destPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+this.projectToAnalyze.getDescription().getName();
			String destInternalProjectPath = "/target/site/images/";

			File target, site, images, logos;
			target = new File(destPath+"/target");
			if(!target.exists())
				target.mkdir();
			site = new File(destPath+"/target/site");
			if(!site.exists())
				target.mkdir();
			images = new File(destPath+"/target/site/images");
			if(!images.exists())
				images.mkdir();
			logos = new File(destPath+"/target/site/images/logos/");
			if(!logos.exists())
				logos.mkdir();

			Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);

			copyFile(bundle, destPath, destInternalProjectPath, errFN, "/icons/");
			copyFile(bundle, destPath, destInternalProjectPath, infFN, "/icons/");
			copyFile(bundle, destPath, destInternalProjectPath, warnFN, "/icons/");
			copyFile(bundle, destPath, destInternalProjectPath+"logos/", mavFN, "/icons/");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void copyFile(Bundle bundle, String destPath, String destInternalProjectPath, String fileName, String sourceInternalProjectPath){

		try{
			Path path = new Path(sourceInternalProjectPath+fileName);
			URL fileURL = Platform.find(bundle, path);
			InputStream is = fileURL.openStream(); 			
			OutputStream os = new FileOutputStream(destPath+destInternalProjectPath+fileName);
			byte[] buffer = new byte[4096];  
			int bytesRead;  
			while ((bytesRead = is.read(buffer)) != -1) {  
				os.write(buffer, 0, bytesRead);  
			}  
			is.close();  
			os.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private ArrayList<ICompilationUnit> getAllClasses() throws JavaModelException {

		ArrayList<ICompilationUnit> classList = new ArrayList<ICompilationUnit>();

		IJavaProject javaProject = JavaCore.create(this.projectToAnalyze);
		IPackageFragment[] packages = javaProject.getPackageFragments();

		for (IPackageFragment myPackage : packages) {
			ICompilationUnit[] cus = myPackage.getCompilationUnits();
			for(ICompilationUnit cu : cus){
				classList.add(cu);
			}
		}		

		return classList;
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

	private class BugDescriptor{

		private int frontEndID;
		private int markerID;
		private int severity;
		private String severityDescription;
		private int line;
		private String descr;
		private String clazz;
		private String errorType;
		private ICompilationUnit cu;

		public ICompilationUnit getCu() {
			return cu;
		}
		public String getSeverityDescription() {
			return severityDescription;
		}
		public void setSeverityDescription(String severityDescription) {
			this.severityDescription = severityDescription;
		}
		public void setCu(ICompilationUnit cu) {
			this.cu = cu;
		}
		public int getSeverity() {
			return severity;
		}
		public void setSeverity(int severity) {
			this.severity = severity;
		}
		public int getLine() {
			return line;
		}
		public void setLine(int line) {
			this.line = line;
		}
		public String getDescr() {
			return descr;
		}
		public void setDescr(String descr) {
			this.descr = descr;
		}
		public String getClazz() {
			return clazz;
		}
		public void setClazz(String clazz) {
			this.clazz = clazz;
		}
		public String getErrorType() {
			return errorType;
		}
		public void setErrorType(String errorType) {
			this.errorType = errorType;
		}
		public int getMarkerID() {
			return markerID;
		}
		public void setMarkerID(int markerID) {
			this.markerID = markerID;
		}
		public int getFrontEndID() {
			return frontEndID;
		}
		public void setFrontEndID(int frontEndID) {
			this.frontEndID = frontEndID;
		}		
	}
}