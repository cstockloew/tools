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
import org.eclipse.jface.viewers.ISelection; //import org.eclipse.core.resources.*;
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
 * This is a sample new wizard. Its role is to create a new file project
 */

public class NewProjectWizard extends Wizard implements INewWizard {
    // These are the folders for a maven project.
    private static final ProjectFolder JAVA = new ProjectFolder(
	    "src/main/java", "target/classes"); //$NON-NLS-1$ //$NON-NLS-2$
    private static final ProjectFolder JAVA_TEST = new ProjectFolder(
	    "src/test/java", "target/test-classes"); //$NON-NLS-1$ //$NON-NLS-2$
    private static final ProjectFolder RESOURCES = new ProjectFolder(
	    "src/main/resources", "target/classes"); //$NON-NLS-1$ //$NON-NLS-2$
    private static final ProjectFolder RESOURCES_TEST = new ProjectFolder(
	    "src/test/resources", "target/test-classes"); //$NON-NLS-1$ //$NON-NLS-2$

    private static final ProjectFolder[] JAR_DIRS = { JAVA, JAVA_TEST,
	    RESOURCES, RESOURCES_TEST };
    private NewProjectWizardPage1 page1;
    private NewProjectWizardPage2 page2;
    private ISelection selection;
    ProjectImportConfiguration configuration;

    public NewProjectWizard() {
	// Some details about the wizard...
	super();
	setNeedsProgressMonitor(true);
	ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(
		"org.universaal.tools.newwizard.plugin", //$NON-NLS-1$
		"icons/ic-uAAL-hdpi.png"); //$NON-NLS-1$
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
     * This method is called when 'Finish' button is pressed in the wizard. We
     * will create an operation and run it using wizard as execution context.
     */
    public boolean performFinish() {
	// These instructions build up the model of the maven project
	final Model model = new Model();
	model.setModelVersion("4.0.0"); //$NON-NLS-1$
	model.setGroupId(page1.getMavenGroupId().getText());
	model.setArtifactId(page1.getMavenArtifactId().getText());
	model.setVersion(page1.getMavenVersion().getText());
	model.setName(page1.getMavenName().getText());
	model.setDescription(page1.getMavenDescription().getText());
	// This is the rest of the info coming from the wizard
	final String pack = page2.getPackaging().getText();
	final boolean[] checks = { false, false, false, false, false, false, false, false };
	checks[0] = page2.getCpublisher().getSelection();
	checks[1] = page2.getCsubscriber().getSelection();
	checks[2] = page2.getIsubscriber().getSelection();
	checks[3] = page2.getOpublisher().getSelection();
	checks[4] = page2.getScallee().getSelection();
	checks[5] = page2.getScaller().getSelection();
	checks[6] = page2.getIpublisher().getSelection();
	checks[7] = page2.getOsubscriber().getSelection();

	// I use deprecated methods because I haven´t found the new way to
	// create a new project
	// TODO: Use the latest methods
	final String projectName = configuration.getProjectName(model);
	IStatus nameStatus = configuration.validateProjectName(model);
	if (!nameStatus.isOK()) {
	    MessageDialog.openError(getShell(), org.universaal.tools.newwizard.plugin.wizards.Messages.getString("Project.1"), //$NON-NLS-1$
		    nameStatus.getMessage());
	    return false;
	}

	IWorkspace workspace = ResourcesPlugin.getWorkspace();
	final IPath location = null;
	final IWorkspaceRoot root = workspace.getRoot();
	final IProject project = configuration.getProject(root, model);

	// If there is already a pom there we cannot create the project
	boolean pomExists = (root.getLocation().append(project.getName()))
		.append(IMavenConstants.POM_FILE_NAME).toFile().exists();
	if (pomExists) {
	    MessageDialog.openError(getShell(), org.universaal.tools.newwizard.plugin.wizards.Messages.getString("Project.2"), //$NON-NLS-1$
		    org.universaal.tools.newwizard.plugin.wizards.Messages.getString("Project.3")); //$NON-NLS-1$
	    return false;
	}

	final Job job, job2;
	final MavenPlugin plugin = MavenPlugin.getDefault();

	// This job creates a blank maven project with the POM as defined in the
	// wizard
	job = new WorkspaceJob("wizard.project.job.creatingProject") { //$NON-NLS-1$
	    public IStatus runInWorkspace(IProgressMonitor monitor) {
		setProperty(IProgressConstants.ACTION_PROPERTY,
			new OpenMavenConsoleAction());
		try {
		    // Here we use the maven plugin to create and shape the
		    // project
		    plugin.getProjectConfigurationManager()
			    .createSimpleProject(project, location, model,
				    getFolders(), //
				    configuration, monitor);
		    return Status.OK_STATUS;
		} catch (CoreException e) {
		    return e.getStatus();
		} finally {
		    monitor.done();
		}
	    }
	};

	// This job modifies the newly created blank maven project to be
	// uaal(PERSONA)-compliant
	job2 = new WorkspaceJob("wizard.project.job.second") { //$NON-NLS-1$
	    public IStatus runInWorkspace(IProgressMonitor monitor) {
		setProperty(IProgressConstants.ACTION_PROPERTY,
			new OpenMavenConsoleAction());
		try {
		    // Set the name of the package
		    IFolder src = project.getFolder(JAVA.getPath());
		    String[] folders = pack.replace(".", "#").split("#"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		    for (int i = 0; i < folders.length; i++) {
			IFolder packFold = src.getFolder(folders[i]);
			packFold.create(true, true, monitor);
			src = packFold;
		    }
		    // create the selected files
		    IFile f1 = src.getFile("Activator.java"); //$NON-NLS-1$
		    f1.create(customizeFileStream("Activator", pack, //$NON-NLS-1$
			    checks), true, monitor);
		    if (checks[0]) {
			IFile f2 = src.getFile("CPublisher.java"); //$NON-NLS-1$
			f2.create(customizeFileStream("CPublisher", pack, //$NON-NLS-1$
				checks), true, monitor);
		    }
		    if (checks[1]) {
			IFile f3 = src.getFile("CSubscriber.java"); //$NON-NLS-1$
			f3.create(customizeFileStream("CSubscriber", pack, //$NON-NLS-1$
				checks), true, monitor);
		    }
		    if (checks[2]) {
			IFile f4 = src.getFile("ISubscriber.java"); //$NON-NLS-1$
			f4.create(customizeFileStream("ISubscriber", pack, //$NON-NLS-1$
				checks), true, monitor);
		    }
		    if (checks[3]) {
			IFile f5 = src.getFile("OPublisher.java"); //$NON-NLS-1$
			f5.create(customizeFileStream("OPublisher", pack, //$NON-NLS-1$
				checks), true, monitor);
		    }
		    if (checks[4]) {
			IFile f6 = src.getFile("SCalleeProvidedService.java"); //$NON-NLS-1$
			f6.create(customizeFileStream("SCalleeProvidedService", //$NON-NLS-1$
				pack, checks), true, monitor);
		    }
		    if (checks[4]) {
			IFile f7 = src.getFile("SCallee.java"); //$NON-NLS-1$
			f7.create(customizeFileStream("SCallee", pack, //$NON-NLS-1$
				checks), true, monitor);
		    }
		    if (checks[5]) {
			IFile f8 = src.getFile("SCaller.java"); //$NON-NLS-1$
			f8.create(customizeFileStream("SCaller", pack, //$NON-NLS-1$
				checks), true, monitor);
		    }
		    if (checks[6]) {
			IFile f9 = src.getFile("IPublisher.java"); //$NON-NLS-1$
			f9.create(customizeFileStream("IPublisher", pack, //$NON-NLS-1$
				checks), true, monitor);
		    }
		    if (checks[7]) {
			IFile f10 = src.getFile("OSubscriber.java"); //$NON-NLS-1$
			f10.create(customizeFileStream("OSubscriber", pack, //$NON-NLS-1$
				checks), true, monitor);
		    }
		    IFile pom = project.getFile("pom.xml"); //$NON-NLS-1$
		    if (pom.exists()) {
			// Modify the pom to be uaal(PERSONA)-compliant
			pom.setContents(customizePomStream(pack, pom
				.getContents(), checks), true, true, monitor);
		    } else {
			// TODO: If there is no pom -> fail. Set some message
			// here...
			System.out.println(">>>>>>>>>>>>>>NO POM!!!!!!!!!"); //$NON-NLS-1$
		    }
		    return Status.OK_STATUS;
		} catch (CoreException e) {
		    return e.getStatus();
		} finally {
		    monitor.done();
		}
	    }
	};
	// Listener in case job fails
	job.addJobChangeListener(new JobChangeAdapter() {
	    public void done(IJobChangeEvent event) {
		final IStatus result = event.getResult();
		if (!result.isOK()) {
		    Display.getDefault().asyncExec(new Runnable() {
			public void run() {
			    MessageDialog.openError(getShell(), //
				    org.universaal.tools.newwizard.plugin.wizards.Messages.getString("Project.4"), result //$NON-NLS-1$
					    .getMessage());
			}
		    });
		}
	    }
	});
	// Listener in case job fails
	job2.addJobChangeListener(new JobChangeAdapter() {
	    public void done(IJobChangeEvent event) {
		final IStatus result = event.getResult();
		if (!result.isOK()) {
		    Display.getDefault().asyncExec(new Runnable() {
			public void run() {
			    MessageDialog.openError(getShell(), //
				    org.universaal.tools.newwizard.plugin.wizards.Messages.getString("Project.5"), result //$NON-NLS-1$
					    .getMessage());
			}
		    });
		}
	    }
	});

	ProjectListener listener = new ProjectListener();
	workspace.addResourceChangeListener(listener,
		IResourceChangeEvent.POST_CHANGE);
	try {
	    // Execute the first job (create maven)
	    job.setRule(plugin.getProjectConfigurationManager().getRule());
	    job.schedule();

	    // MNGECLIPSE-766 wait until new project is created
	    while (listener.getNewProject() == null
		    && (job.getState() & (Job.WAITING | Job.RUNNING)) > 0) {
		try {
		    Thread.sleep(100L);
		} catch (InterruptedException ex) {
		    // ignore
		}
	    }
	    // Execute the second job (modify to uaal)
	    job2.setRule(plugin.getProjectConfigurationManager().getRule());
	    job2.schedule();

	    // MNGECLIPSE-766 wait until new project is created
	    while (listener.getNewProject() == null
		    && (job2.getState() & (Job.WAITING | Job.RUNNING)) > 0) {
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
     * This method parses a newly created Activator file to make it init, start
     * and stop as appropriate the rest of uaal-specific files. It also adapts
     * package name to all files.
     * @param filename The name of the file (without extension)
     * @param packname The name of package
     * @param checks Collection of checked options to browse all checked classes
     * @return
     */
    private InputStream customizeFileStream(String filename, String packname,
	    boolean[] checks) {
	try {
	    // TODO: Modify if necessary the rest of files, not only Activator.
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    this.getClass().getClassLoader().getResourceAsStream(
			    "files/" + filename+".java"))); //$NON-NLS-1$ //$NON-NLS-2$
	    StringBuilder output = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
		if (line.contains("/*TAG:PACKAGE*/")) { //$NON-NLS-1$
		    output.append("package " + packname + ";\n"); //$NON-NLS-1$ //$NON-NLS-2$
		} else if (line.contains("/*TAG:INIT*/")) { //$NON-NLS-1$
		    if (checks[4])
			output.append("	public static SCallee callee=null;\n"); //$NON-NLS-1$
		    if (checks[5])
			output.append("	public static SCaller caller=null;\n"); //$NON-NLS-1$
		    if (checks[2])
			output
				.append("	public static ISubscriber input=null;\n"); //$NON-NLS-1$
		    if (checks[3])
			output
				.append("	public static OPublisher output=null;\n"); //$NON-NLS-1$
		    if (checks[1])
			output
				.append("	public static CSubscriber csubscriber=null;\n"); //$NON-NLS-1$
		    if (checks[0])
			output
				.append("	public static CPublisher cpublisher=null;\n"); //$NON-NLS-1$
		    if (checks[6])
			output.append("	public static IPublisher ipublisher=null;\n"); //$NON-NLS-1$
		    if (checks[7])
			output.append("	public static OSubscriber osubscriber=null;\n"); //$NON-NLS-1$
		} else if (line.contains("/*TAG:START*/")) { //$NON-NLS-1$
		    if (checks[4])
			output.append("		callee=new SCallee(context);\n"); //$NON-NLS-1$
		    if (checks[5])
			output.append("		caller=new SCaller(context);\n"); //$NON-NLS-1$
		    if (checks[2])
			output.append("		input=new ISubscriber(context);\n"); //$NON-NLS-1$
		    if (checks[3])
			output.append("		output=new OPublisher(context);\n"); //$NON-NLS-1$
		    if (checks[1])
			output
				.append("		csubscriber=new CSubscriber(context);\n"); //$NON-NLS-1$
		    if (checks[0])
			output
				.append("		cpublisher=new CPublisher(context);\n"); //$NON-NLS-1$
		    if (checks[6])
			output.append("		ipublisher=new IPublisher(context);\n"); //$NON-NLS-1$
		    if (checks[7])
			output.append("		osubscriber=new OSubscriber(context);\n"); //$NON-NLS-1$
		} else if (line.contains("/*TAG:STOP*/")) { //$NON-NLS-1$
		    if (checks[4])
			output.append("		callee.close();\n"); //$NON-NLS-1$
		    if (checks[5])
			output.append("		caller.close();\n"); //$NON-NLS-1$
		    if (checks[2])
			output.append("		input.close();\n"); //$NON-NLS-1$
		    if (checks[3])
			output.append("		output.close();\n"); //$NON-NLS-1$
		    if (checks[1])
			output.append("		csubscriber.close();\n"); //$NON-NLS-1$
		    if (checks[0])
			output.append("		cpublisher.close();\n"); //$NON-NLS-1$
		    if (checks[6])
			output.append("		ipublisher.close();\n"); //$NON-NLS-1$
		    if (checks[7])
			output.append("		osubscriber.close();\n"); //$NON-NLS-1$
		} else if (line.contains("/*TAG:CLASSNAME*/")) { //$NON-NLS-1$
		    line = line.replace("/*TAG:CLASSNAME*/", filename); //$NON-NLS-1$
		    output.append(line + "\n"); //$NON-NLS-1$
		} else {
		    output.append(line + "\n"); //$NON-NLS-1$
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
     * 
     * @param checks
     */
    private InputStream customizePomStream(String packname,
	    InputStream instream, boolean[] checks) {
	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    instream));
	    StringBuilder output = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
		if (line.contains("</project>")) { //$NON-NLS-1$
		    output.append("  <packaging>bundle</packaging>\n"); //$NON-NLS-1$
		    output
			    .append("    <dependencies>\n" //$NON-NLS-1$
				    + "		<dependency>\n" //$NON-NLS-1$
				    + "			<groupId>org.apache.felix</groupId>\n" //$NON-NLS-1$
				    + "			<artifactId>org.osgi.core</artifactId>\n" //$NON-NLS-1$
				    + "			<version>1.0.1</version>\n" //$NON-NLS-1$
				    + "		</dependency>\n" //$NON-NLS-1$
				    + "		<dependency>\n" //$NON-NLS-1$
				    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
				    + "			<artifactId>mw.data.representation</artifactId>\n" //$NON-NLS-1$
				    + "			<version>0.3.0-SNAPSHOT</version>\n" //$NON-NLS-1$
				    + "		</dependency>\n"); //$NON-NLS-1$
		    if (checks[0] || checks[1]) {
			output
				.append("		<dependency>\n" //$NON-NLS-1$
					+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
					+ "			<artifactId>mw.bus.context</artifactId>\n" //$NON-NLS-1$
					+ "			<version>0.3.0-SNAPSHOT</version>\n" //$NON-NLS-1$
					+ "		</dependency>\n"); //$NON-NLS-1$
		    }
		    if (checks[2] || checks[3] || checks[6] || checks[7]) {
			output
				.append("		<dependency>\n" //$NON-NLS-1$
					+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
					+ "			<artifactId>mw.bus.io</artifactId>\n" //$NON-NLS-1$
					+ "			<version>0.3.0-SNAPSHOT</version>\n" //$NON-NLS-1$
					+ "		</dependency>\n"); //$NON-NLS-1$
		    }
		    if (checks[4] || checks[5]) {
			output
				.append("		<dependency>\n" //$NON-NLS-1$
					+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
					+ "			<artifactId>mw.bus.service</artifactId>\n" //$NON-NLS-1$
					+ "			<version>0.3.0-SNAPSHOT</version>\n" //$NON-NLS-1$
					+ "		</dependency>\n"); //$NON-NLS-1$
		    }
		    output.append("	</dependencies>\n"); //$NON-NLS-1$
		    output
			    .append("    <build>\n" //$NON-NLS-1$
				    + "		<plugins>\n" //$NON-NLS-1$
				    + "			<plugin>\n" //$NON-NLS-1$
				    + "				<groupId>org.apache.felix</groupId>\n" //$NON-NLS-1$
				    + "				<artifactId>maven-bundle-plugin</artifactId>\n" //$NON-NLS-1$
				    + "				<extensions>true</extensions>\n" //$NON-NLS-1$
				    + "				<configuration>\n" //$NON-NLS-1$
				    + "					<instructions>\n" //$NON-NLS-1$
				    + "						<Bundle-Name>${pom.name}</Bundle-Name>\n" //$NON-NLS-1$
				    + "						<Bundle-Activator>" //$NON-NLS-1$
				    + packname
				    + ".Activator</Bundle-Activator>\n" //$NON-NLS-1$
				    + "						<Bundle-Description>${pom.description}</Bundle-Description>\n" //$NON-NLS-1$
				    + "						<Bundle-SymbolicName>${pom.artifactId}</Bundle-SymbolicName>\n" //$NON-NLS-1$
				    + "					</instructions>\n" //$NON-NLS-1$
				    + "				</configuration>\n" //$NON-NLS-1$
				    + "			</plugin>\n" + "		</plugins>\n" //$NON-NLS-1$ //$NON-NLS-2$
				    + "	</build>\n"); //$NON-NLS-1$
		    output
			    .append("	<repositories>\n" //$NON-NLS-1$
				    + "		<repository>\n" //$NON-NLS-1$
				    + "			<id>central</id>\n" //$NON-NLS-1$
				    + "			<name>Central Maven Repository</name>\n" //$NON-NLS-1$
				    + "			<url>http://repo1.maven.org/maven2</url>\n" //$NON-NLS-1$
				    + "			<snapshots>\n" //$NON-NLS-1$
				    + "				<enabled>false</enabled>\n" //$NON-NLS-1$
				    + "			</snapshots>\n" //$NON-NLS-1$
				    + "		</repository>\n" //$NON-NLS-1$
				    + "		<repository>\n" //$NON-NLS-1$
				    + "			<id>apache-snapshots</id>\n" //$NON-NLS-1$
				    + "			<name>Apache Snapshots</name>\n" //$NON-NLS-1$
				    + "			<url>http://people.apache.org/repo/m2-snapshot-repository</url>\n" //$NON-NLS-1$
				    + "			<releases>\n" //$NON-NLS-1$
				    + "				<enabled>false</enabled>\n" //$NON-NLS-1$
				    + "			</releases>\n" //$NON-NLS-1$
				    + "			<snapshots>\n" //$NON-NLS-1$
				    + "				<updatePolicy>daily</updatePolicy>\n" //$NON-NLS-1$
				    + "			</snapshots>\n" //$NON-NLS-1$
				    + "		</repository>\n" //$NON-NLS-1$
				    + "		<repository>\n" //$NON-NLS-1$
				    + "			<id>uaal</id>\n" //$NON-NLS-1$
				    + "			<name>universAAL Repositories</name>\n" //$NON-NLS-1$
				    + "			<url>http://depot.universaal.org/maven-repo/releases/</url>\n" //$NON-NLS-1$
				    + "			<snapshots>\n" //$NON-NLS-1$
				    + "				<enabled>false</enabled>\n" //$NON-NLS-1$
				    + "			</snapshots>\n" //$NON-NLS-1$
				    + "		</repository>\n" //$NON-NLS-1$
				    + "		<repository>\n" //$NON-NLS-1$
				    + "			<id>uaal-snapshots</id>\n" //$NON-NLS-1$
				    + "			<name>universAAL Snapshot Repositories</name>\n" //$NON-NLS-1$
				    + "			<url>http://depot.universaal.org/maven-repo/snapshots/</url>\n" //$NON-NLS-1$
				    + "			<releases>\n" //$NON-NLS-1$
				    + "				<enabled>false</enabled>\n" //$NON-NLS-1$
				    + "			</releases>\n" + "		</repository>\n" //$NON-NLS-1$ //$NON-NLS-2$
				    + "	</repositories>\n"); //$NON-NLS-1$
		    output.append("</project>\n"); //$NON-NLS-1$
		} else {
		    output.append(line + "\n"); //$NON-NLS-1$
		}
	    }
	    return new ByteArrayInputStream(output.toString().getBytes());
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * We will accept the selection in the workbench to see if we can initialize
     * from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
	this.selection = selection;
    }

    public boolean canFinish() {
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
		    newProject = (IProject) resource;
		}
	    }
	}

	public IProject getNewProject() {
	    return newProject;
	}
    }

    // Returns the maven default folders
    public String[] getFolders() {
	ProjectFolder[] mavenDirectories = JAR_DIRS;
	String[] directories = new String[mavenDirectories.length];
	for (int i = 0; i < directories.length; i++) {
	    directories[i] = mavenDirectories[i].getPath();
	}
	return directories;
    }

    // Class for folder representation
    final static class ProjectFolder {
	/** Folder path */
	private String path = null;
	/** Output path */
	private String outputPath = null;

	ProjectFolder(String path, String outputPath) {
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
	    return this.getOutputPath() != null;
	}

    }

}