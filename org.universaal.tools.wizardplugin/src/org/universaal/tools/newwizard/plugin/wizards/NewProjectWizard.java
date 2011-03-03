package org.universaal.tools.newwizard.plugin.wizards;

import org.apache.maven.model.Model;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import java.io.*;

import org.eclipse.ui.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.IProgressConstants;
import org.maven.ide.eclipse.MavenPlugin;
import org.maven.ide.eclipse.actions.OpenMavenConsoleAction;
import org.maven.ide.eclipse.core.IMavenConstants;
import org.maven.ide.eclipse.core.Messages;
import org.maven.ide.eclipse.project.ProjectImportConfiguration;

/**
 * This is a sample new wizard. Its role is to create a new file 
 * project
 */

public class NewProjectWizard extends Wizard implements INewWizard {
	//These are the folders for a maven project.
	private static final ProjectFolder JAVA = new ProjectFolder("src/main/java", "target/classes");
	private static final ProjectFolder JAVA_TEST = new ProjectFolder("src/test/java", "target/test-classes");
	private static final ProjectFolder RESOURCES = new ProjectFolder("src/main/resources", "target/classes");
	private static final ProjectFolder RESOURCES_TEST = new ProjectFolder("src/test/resources",
	"target/test-classes");
	
	private static final ProjectFolder[] JAR_DIRS = {JAVA, JAVA_TEST, RESOURCES, RESOURCES_TEST};
	private NewProjectWizardPage1 page1;
	private NewProjectWizardPage2 page2;
	private ISelection selection;
	ProjectImportConfiguration configuration;


	public NewProjectWizard() {
		//Some details about the wizard...
		super();
		setNeedsProgressMonitor(true);
		ImageDescriptor image =
            AbstractUIPlugin.
                imageDescriptorFromPlugin("org.universaal.tools.newwizard.plugin",
                   "icons/ic-uAAL-hdpi.png");
        setDefaultPageImageDescriptor(image);

	}
	

	public void addPages() {
		configuration = new ProjectImportConfiguration();
		page1 = new NewProjectWizardPage1(selection);
		page2 = new NewProjectWizardPage2(selection);
		addPage(page1);
		addPage(page2);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		//These instructions build up the model of the maven project
		final Model model = new Model();
		model.setModelVersion("4.0.0");
		model.setGroupId(page1.getMavenGroupId().getText());
		model.setArtifactId(page1.getMavenArtifactId().getText());
		model.setVersion(page1.getMavenVersion().getText());
		model.setName(page1.getMavenName().getText());
		model.setDescription(page1.getMavenDescription().getText());
		//This is the rest of the info coming from the wizard
		final String pack=page2.getPackaging().getText();
		final boolean[] checks={false,false,false,false,false,false};
		checks[0]=page2.getCpublisher().getSelection();
		checks[1]=page2.getCsubscriber().getSelection();
		checks[2]=page2.getIsubscriber().getSelection();
		checks[3]=page2.getOpublisher().getSelection();
		checks[4]=page2.getScallee().getSelection();
		checks[5]=page2.getScaller().getSelection();

		//I use deprecated methods because I haven´t found the new way to create a new project
		//TODO: Use the latest methods
		final String projectName = configuration.getProjectName(model);
		IStatus nameStatus = configuration.validateProjectName(model);
		if(!nameStatus.isOK()) {
			MessageDialog.openError(getShell(), "wizard.project.job.failed1", nameStatus.getMessage());
			return false;
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IPath location = null;
		final IWorkspaceRoot root = workspace.getRoot();
		final IProject project = configuration.getProject(root, model);

		//If there is already a pom there we cannot create the project
		boolean pomExists = (root.getLocation().append(project.getName())).append(IMavenConstants.POM_FILE_NAME).toFile().exists();
		if ( pomExists ) {
			MessageDialog.openError(getShell(), "wizard.project.job.failed2", "wizard.project.error.pomAlreadyExists");
			return false;
		}

		final Job job,job2;
		final MavenPlugin plugin = MavenPlugin.getDefault();

		//This job creates a blank maven project with the POM as defined in the wizard
		job = new WorkspaceJob(Messages.getString("wizard.project.job.creatingProject", projectName)) {
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				setProperty(IProgressConstants.ACTION_PROPERTY, new OpenMavenConsoleAction());
				try {
					//Here we use the maven plugin to create and shape the project
					plugin.getProjectConfigurationManager().createSimpleProject(project, location, model, getFolders(), //
							configuration, monitor);
					return Status.OK_STATUS;
				} catch(CoreException e) {
					return e.getStatus();
				} finally {
					monitor.done();
				}
			}
		};
		
		//This job modifies the newly created blank maven project to be uaal(PERSONA)-compliant
		job2 = new WorkspaceJob("wizard.project.job.second") {
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				setProperty(IProgressConstants.ACTION_PROPERTY, new OpenMavenConsoleAction());
				try {
					//Set the name of the package
					IFolder src=project.getFolder(JAVA.getPath());
					String[] folders=pack.replace(".", "#").split("#");
					for(int i=0;i<folders.length;i++){
						IFolder packFold=src.getFolder(folders[i]);
						packFold.create(true, true, monitor);
						src=packFold;
					}
					//create the selected files
					IFile f1=src.getFile("Activator.java");
					f1.create(customizeFileStream("Activator.java",pack,checks), true, monitor);
					if(checks[0]){
					IFile f2=src.getFile("CPublisher.java");
					f2.create(customizeFileStream("CPublisher.java",pack,checks), true, monitor);
					}if(checks[1]){
					IFile f3=src.getFile("CSubscriber.java");
					f3.create(customizeFileStream("CSubscriber.java",pack,checks), true, monitor);
					}if(checks[2]){
					IFile f4=src.getFile("ISubscriber.java");
					f4.create(customizeFileStream("ISubscriber.java",pack,checks), true, monitor);
					}if(checks[3]){
					IFile f5=src.getFile("OPublisher.java");
					f5.create(customizeFileStream("OPublisher.java",pack,checks), true, monitor);
					}if(checks[4]){
					IFile f6=src.getFile("ProvidedService.java");
					f6.create(customizeFileStream("ProvidedService.java",pack,checks), true, monitor);
					}if(checks[4]){
					IFile f7=src.getFile("SCallee.java");
					f7.create(customizeFileStream("SCallee.java",pack,checks), true, monitor);
					}if(checks[5]){
					IFile f8=src.getFile("SCaller.java");
					f8.create(customizeFileStream("SCaller.java",pack,checks), true, monitor);
					}
					IFile pom=project.getFile("pom.xml");
					if(pom.exists()){
						//Modify the pom to be uaal(PERSONA)-compliant
						pom.setContents(customizePomStream(pack,pom.getContents(),checks), true, true, monitor);
					}else{
						//TODO: If there is no pom -> fail. Set some message here...
						System.out.println(">>>>>>>>>>>>>>NO POM!!!!!!!!!");
					}
					return Status.OK_STATUS;
				} catch(CoreException e) {
					return e.getStatus();
				} finally {
					monitor.done();
				}
			}
		};
		//Listener in case job fails
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				final IStatus result = event.getResult();
				if(!result.isOK()) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(getShell(), //
									"wizard.project.job.failed3", result.getMessage());
						}
					});
				}
			}
		});
		//Listener in case job fails
		job2.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				final IStatus result = event.getResult();
				if(!result.isOK()) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(getShell(), //
									"wizard.project.job.failed4", result.getMessage());
						}
					});
				}
			}
		});

		ProjectListener listener = new ProjectListener();
		workspace.addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
		try {
			//Execute the first job (create maven)
			job.setRule(plugin.getProjectConfigurationManager().getRule());
			job.schedule();

			// MNGECLIPSE-766 wait until new project is created
			while(listener.getNewProject() == null && (job.getState() & (Job.WAITING | Job.RUNNING)) > 0) {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException ex) {
					// ignore
				}
			}
			//Execute the second job (modify to uaal)
			job2.setRule(plugin.getProjectConfigurationManager().getRule());
			job2.schedule();

			// MNGECLIPSE-766 wait until new project is created
			while(listener.getNewProject() == null && (job2.getState() & (Job.WAITING | Job.RUNNING)) > 0) {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException ex) {
					// ignore
				}
			}
		} finally {
			workspace.removeResourceChangeListener(listener);
		}
		return true;
	}
	
	/**
	 * This method parses a newly created Activator file to make it init, start and stop
	 * as appropriate the rest of uaal-specific files. It also adapts package name to all files.
	 */
	private InputStream customizeFileStream(String filename, String packname, boolean[]checks) {
		try {
			//TODO: Modify if necessary the rest of files, not only Activator.
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("files/"+filename)));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.contains("/*TAG:PACKAGE*/")){
					output.append("package "+packname+";\n");
				}else if(line.contains("/*TAG:INIT*/")){
					if(checks[4])output.append("	public static SCallee callee=null;\n");
					if(checks[5])output.append("	public static SCaller caller=null;\n");
					if(checks[2])output.append("	public static ISubscriber input=null;\n");
					if(checks[3])output.append("	public static OPublisher output=null;\n");
					if(checks[1])output.append("	public static CSubscriber csubscriber=null;\n");
					if(checks[0])output.append("	public static CPublisher cpublisher=null;\n");
				}else if(line.contains("/*TAG:START*/")){
					if(checks[4])output.append("		callee=new SCallee(context);\n");
					if(checks[5])output.append("		caller=new SCaller(context);\n");
					if(checks[2])output.append("		input=new ISubscriber(context);\n");
					if(checks[3])output.append("		output=new OPublisher(context);\n");
					if(checks[1])output.append("		csubscriber=new CSubscriber(context);\n");
					if(checks[0])output.append("		cpublisher=new CPublisher(context);\n");
				}else if(line.contains("/*TAG:STOP*/")){
					if(checks[4])output.append("		callee.close();\n");
					if(checks[5])output.append("		caller.close();\n");
					if(checks[2])output.append("		input.close();\n");
					if(checks[3])output.append("		output.close();\n");
					if(checks[1])output.append("		csubscriber.close();\n");
					if(checks[0])output.append("		cpublisher.close();\n");
				}else{
					output.append(line+"\n");
				}
			}
			return new ByteArrayInputStream(output.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method parses the blank pom template and adds dependencies and 
	 * configurations for the project to be uaal-compliant
	 * @param checks 
	 */
	private InputStream customizePomStream(String packname,InputStream instream, boolean[] checks) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.contains("</project>")){
					output.append("  <packaging>bundle</packaging>\n");
					output.append("    <dependencies>\n"+
							"		<dependency>\n"+
							"			<groupId>org.apache.felix</groupId>\n"+
							"			<artifactId>org.osgi.core</artifactId>\n"+
							"			<version>1.0.1</version>\n"+
							"		</dependency>\n"+
							"		<dependency>\n" +
							"			<groupId>org.universAAL.middleware</groupId>\n" +
							"			<artifactId>mw.data.representation</artifactId>\n" +
							"			<version>0.3.0-SNAPSHOT</version>\n" +
							"		</dependency>\n");
					if(checks[0]||checks[1]){
						output.append("		<dependency>\n"+
								"			<groupId>org.universAAL.middleware</groupId>\n"+
								"			<artifactId>mw.bus.context</artifactId>\n"+
								"			<version>0.3.0-SNAPSHOT</version>\n"+
								"		</dependency>\n");
					}
					if(checks[2]||checks[3]){
						output.append("		<dependency>\n"+
								"			<groupId>org.universAAL.middleware</groupId>\n"+
								"			<artifactId>mw.bus.io</artifactId>\n"+
								"			<version>0.3.0-SNAPSHOT</version>\n"+
								"		</dependency>\n");
					}
					if(checks[4]||checks[5]){
						output.append("		<dependency>\n"+
								"			<groupId>org.universAAL.middleware</groupId>\n"+
								"			<artifactId>mw.bus.service</artifactId>\n"+
								"			<version>0.3.0-SNAPSHOT</version>\n"+
								"		</dependency>\n");
					}
					output.append("	</dependencies>\n");
					output.append("    <build>\n"+
							"		<plugins>\n"+
							"			<plugin>\n"+
							"				<groupId>org.apache.felix</groupId>\n"+
							"				<artifactId>maven-bundle-plugin</artifactId>\n"+
							"				<extensions>true</extensions>\n"+
							"				<configuration>\n"+
							"					<instructions>\n"+
							"						<Bundle-Name>${pom.name}</Bundle-Name>\n"+
							"						<Bundle-Activator>"+packname+".Activator</Bundle-Activator>\n"+
							"						<Bundle-Description>${pom.description}</Bundle-Description>\n"+
							"						<Bundle-SymbolicName>${pom.artifactId}</Bundle-SymbolicName>\n"+
							"					</instructions>\n"+
							"				</configuration>\n"+
							"			</plugin>\n"+
							"		</plugins>\n"+
					"	</build>\n");
					output.append("	<repositories>\n"+
							"		<repository>\n" +
							"			<id>central</id>\n" +
							"			<name>Central Maven Repository</name>\n" +
							"			<url>http://repo1.maven.org/maven2</url>\n" +
							"			<snapshots>\n" +
							"				<enabled>false</enabled>\n" +
							"			</snapshots>\n" +
							"		</repository>\n" +
							"		<repository>\n" +
							"			<id>apache-snapshots</id>\n" +
							"			<name>Apache Snapshots</name>\n" +
							"			<url>http://people.apache.org/repo/m2-snapshot-repository</url>\n" +
							"			<releases>\n" +
							"				<enabled>false</enabled>\n" +
							"			</releases>\n" +
							"			<snapshots>\n" +
							"				<updatePolicy>daily</updatePolicy>\n" +
							"			</snapshots>\n" +
							"		</repository>\n" +
							"		<repository>\n" +
							"			<id>uaal</id>\n" +
							"			<name>universAAL Repositories</name>\n" +
							"			<url>http://ala.isti.cnr.it:8080/nexus/content/repositories/releases/</url>\n" +
							"			<snapshots>\n" +
							"				<enabled>false</enabled>\n" +
							"			</snapshots>\n" +
							"		</repository>\n" +
							"		<repository>\n" +
							"			<id>uaal-snapshots</id>\n" +
							"			<name>universAAL Snapshot Repositories</name>\n" +
							"			<url>http://ala.isti.cnr.it:8080/nexus/content/repositories/snapshots/</url>\n" +
							"			<releases>\n" +
							"				<enabled>false</enabled>\n" +
							"			</releases>\n" +
							"		</repository>\n" +
							"	</repositories>\n");
					output.append("<distributionManagement>\n" +
							"   <repository>\n" +
							"       <id>releases</id>\n" +
							"       <url>http://ala.isti.cnr.it:8080/nexus/content/repositories/releases</url>\n" +
							"   </repository>\n" +
							"   <snapshotRepository>\n" +
							"        <id>snapshots</id>\n" +
							"        <url>http://ala.isti.cnr.it:8080/nexus/content/repositories/snapshots</url>\n" +
							"   </snapshotRepository>\n" +
							"</distributionManagement>\n");
					output.append("</project>\n");
				}else{
					output.append(line+"\n");
				}
			}
			return new ByteArrayInputStream(output.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	public boolean canFinish(){
		return page1.isPageComplete() && page2.isPageComplete();
	}
	
	static class ProjectListener implements IResourceChangeListener {
		private IProject newProject = null;

		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta root = event.getDelta();
			IResourceDelta[] projectDeltas = root.getAffectedChildren();
			for (int i = 0; i < projectDeltas.length; i++) {              
				IResourceDelta delta = projectDeltas[i];
				IResource resource = delta.getResource();
				if (delta.getKind() == IResourceDelta.ADDED) {
					newProject = (IProject)resource;
				}
			}
		}
		
		public IProject getNewProject() {
			return newProject;
		}
	}
	
	//Returns the maven default folders
	public String[] getFolders() {
		  ProjectFolder[] mavenDirectories = JAR_DIRS;
		  String[] directories = new String[mavenDirectories.length];
		  for(int i = 0; i < directories.length; i++ ) {
			  directories[i] = mavenDirectories[i].getPath();
		  }
		  return directories;
	}
	
	//Class for folder representation
	final static class ProjectFolder {
	    /** Folder path */
	    private String path = null;
	    /** Output path */
	    private String outputPath = null;
	    ProjectFolder( String path, String outputPath ) {
	      this.path = path;
	      this.outputPath = outputPath;
	    }
	    String getPath() {
	      return path;
	    }
	    String getOutputPath() {
	      return outputPath;
	    }
	    boolean isSourceEntry() {
	      return this.getOutputPath()!=null;
	    }
	    
	  }

}