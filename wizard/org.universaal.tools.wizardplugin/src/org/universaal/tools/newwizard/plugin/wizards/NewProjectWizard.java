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
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.ui.internal.actions.OpenMavenConsoleAction;
import org.eclipse.m2e.core.project.ProjectImportConfiguration;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import org.eclipse.ui.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.IProgressConstants;
import org.universaal.tools.newwizard.plugin.Activator;

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

    private static final String[] classNames = { "CPublisher", "CSubscriber",
	    "ISubscriber", "OPublisher", "SCallee", "SCaller", "IPublisher",
	    "OSubscriber" };
    private static final ProjectFolder[] JAR_DIRS = { JAVA, JAVA_TEST,
	    RESOURCES, RESOURCES_TEST };
    private NewProjectWizardPage1 page1;
    private NewProjectWizardPage2 page2;
    private ISelection selection;
    ProjectImportConfiguration configuration;
    short[][] templateMatrix100 = { { 1, 1, 1, 1, 1, 1, 0, 0, 1 },
		{ 1, 1, 0, 0, 2, 1, 0, 0, 2 }, 
		{ 3, 0, 0, 0, 0, 0, 0, 0, 0 },
		{ 1, 0, 0, 0, 2, 0, 0, 0, 2 }, 
		{ 4, 4, 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
    short[][] templateMatrix110 = { { 1, 1, 0, 1, 1, 1, 0, 0, 1 },
		{ 1, 1, 0, 0, 2, 1, 0, 0, 2 }, 
		{ 3, 0, 0, 0, 0, 0, 0, 0, 0 },
		{ 1, 0, 0, 0, 2, 0, 0, 0, 2 }, 
		{ 4, 4, 0, 0, 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

    public NewProjectWizard() {
	// Some details about the wizard...
	super();
	setNeedsProgressMonitor(true);
	ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(
		"org.universaal.tools.newwizard.plugin", //$NON-NLS-1$
		"icons/ic-uAAL-hdpi.png"); //$NON-NLS-1$
	setDefaultPageImageDescriptor(image);
	setWindowTitle(Messages.getString("Project.6"));
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
	final boolean[] checks = { false, false, false, false, false, false,
		false, false, false, false };
	checks[0] = page2.getCpublisher().getSelection();
	checks[1] = page2.getCsubscriber().getSelection();
	checks[2] = page2.getIsubscriber().getSelection();
	checks[3] = page2.getOpublisher().getSelection();
	checks[4] = page2.getScallee().getSelection();
	checks[5] = page2.getScaller().getSelection();
	checks[6] = page2.getIpublisher().getSelection();
	checks[7] = page2.getOsubscriber().getSelection();
	checks[8] = page2.getDefCpublisher().getSelection();
	checks[9] = page2.getDefScaller().getSelection();
	final boolean template=page2.getTemplate().getSelection();
	final int templateIndex=page2.getTemplateDropDown().getSelectionIndex();
	final int mwVer=page2.getVersionDropDown().getSelectionIndex();
	final String mwVersion=page2.getVersionDropDown().getItem(mwVer);

	// I use deprecated methods because I haven´t found the new way to
	// create a new project
	// TODO: Use the latest methods -> Latest version of Maven plugin keeps
	// using them!
	// final String projectName = configuration.getProjectName(model);
	IStatus nameStatus = configuration.validateProjectName(model);
	if (!nameStatus.isOK()) {
	    MessageDialog.openError(getShell(),
		    org.universaal.tools.newwizard.plugin.wizards.Messages
			    .getString("Project.1"), //$NON-NLS-1$
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
	    MessageDialog.openError(getShell(),
		    org.universaal.tools.newwizard.plugin.wizards.Messages
			    .getString("Project.2"), //$NON-NLS-1$
		    org.universaal.tools.newwizard.plugin.wizards.Messages
			    .getString("Project.3")); //$NON-NLS-1$
	    return false;
	}

	final Job job, job2;

	// This job creates a blank maven project with the POM as defined in the
	// wizard
	job = new WorkspaceJob(
		org.universaal.tools.newwizard.plugin.wizards.Messages
			.getString("Project.8")) { //$NON-NLS-1$
	    public IStatus runInWorkspace(IProgressMonitor monitor) {
		setProperty(IProgressConstants.ACTION_PROPERTY,
			new OpenMavenConsoleAction());
		try {
		    // Here we use the maven plugin to create and shape the
		    // project
		    MavenPlugin.getProjectConfigurationManager()
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
	// uaal-compliant
	job2 = new WorkspaceJob(
		org.universaal.tools.newwizard.plugin.wizards.Messages
			.getString("Project.9")) { //$NON-NLS-1$
	    public IStatus runInWorkspace(IProgressMonitor monitor) {
		setProperty(IProgressConstants.ACTION_PROPERTY,
			new OpenMavenConsoleAction());
		try {
		    // Set the name & create the package
		    IFolder src = project.getFolder(JAVA.getPath());
		    String[] folders = pack.replace(".", "#").split("#"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		    for (int i = 0; i < folders.length; i++) {
			IFolder packFold = src.getFolder(folders[i]);
			packFold.create(true, true, monitor);
			src = packFold;
		    }
		    // create the activator
		    IFile f1 = src.getFile("Activator.java"); //$NON-NLS-1$
		    f1.create(FileStreamUtils.customizeFileStream(
			    mwVersion + "/" + "Activator", pack, //$NON-NLS-1$
			    checks), true, monitor);
		    if (template) {//Copy the template classes. Watch for indices
			for (int i = 0; i < classNames.length; i++) {
			    String folder = getTemplateFolder(templateIndex, i, mwVer);// Get origin folder for this file
			    if (!folder.isEmpty()) {// If folder is "" means there is no such file for this template
				IFile f = src.getFile(classNames[i] + ".java"); //$NON-NLS-1$
				f.create(FileStreamUtils.customizeFileStream(
					 mwVersion + "/templates/" + folder + "/" + classNames[i], pack, //$NON-NLS-1$
					checks), true, monitor);//This copies template file from right folder
				if (i == 4) {//If SCee, add ProvidedServ
				    IFile faux = src
					    .getFile("SCalleeProvidedService.java"); //$NON-NLS-1$
				    faux.create(FileStreamUtils
					    .customizeFileStream(mwVersion + "/templates/" +  folder + "/"
						    + "SCalleeProvidedService", //$NON-NLS-1$
						    pack, checks), true,
					    monitor);
				}
			    }

			}
		    } else {
			// Watch out here for indices
			for (int i = 0; i < 8; i++) {
			    if (checks[i] && !((i == 0) && (checks[8]))// check default CPer
				    && !((i == 5) && (checks[9]))) {// check default SCer
				IFile f = src.getFile(classNames[i] + ".java"); //$NON-NLS-1$
				f.create(FileStreamUtils.customizeFileStream(
					mwVersion + "/" + classNames[i], pack, //$NON-NLS-1$
					checks), true, monitor);
				if (i == 4) {// If SCee, add ProvidedServ
				    IFile faux = src
					    .getFile("SCalleeProvidedService.java"); //$NON-NLS-1$
				    faux.create(FileStreamUtils
					    .customizeFileStream(
						    mwVersion + "/" + "SCalleeProvidedService", //$NON-NLS-1$
						    pack, checks), true,
					    monitor);
				}
			    }
			}
		    }
		    // Now edit the POM file
		    IFile pom = project.getFile("pom.xml"); //$NON-NLS-1$
		    if (pom.exists()) {
			//This is to add phWorld and/or ont.profile deps, if template
			boolean[] templateDeps={template, templateIndex==0};
			// Modify the pom to be uaal-compliant
			pom.setContents(
				FileStreamUtils.customizePomStream(pack,
					pom.getContents(), checks, templateDeps, mwVersion), true, true,
				monitor);
		    } else {
			return new Status(
				    Status.ERROR,
				    Activator.PLUGIN_ID,
				    org.universaal.tools.newwizard.plugin.wizards.Messages
					    .getString("Project.7"));
		    }
		    // This is like refreshing, because we changed the pom
		    MavenPlugin.getProjectConfigurationManager()
			    .updateProjectConfiguration(project, monitor);
		    return Status.OK_STATUS;
		} catch (CoreException e) {
		    return e.getStatus();
		} catch (Exception e) {
		    e.printStackTrace();
		    return new Status(
			    Status.ERROR,
			    Activator.PLUGIN_ID,
			    e.getMessage());
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
			    MessageDialog
				    .openError(
					    getShell(), //
					    org.universaal.tools.newwizard.plugin.wizards.Messages
						    .getString("Project.4"), result //$NON-NLS-1$
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
			    MessageDialog
				    .openError(
					    getShell(), //
					    org.universaal.tools.newwizard.plugin.wizards.Messages
						    .getString("Project.5"), result //$NON-NLS-1$
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
	    job.setRule(MavenPlugin.getProjectConfigurationManager().getRule());
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
	    // job2.setRule(MavenPlugin.getProjectConfigurationManager().getRule());
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
    
    /**
     * Returns the template folder from where to get the right template file for
     * a desired wrapper class <code>colClass</code> for a full template
     * project, which type is identified by <code>rowTemplate</code>.
     * 
     * @param rowTemplate
     *            The index of type of full template project, according to the
     *            dropdown combo of the second page of wizard
     * @param colClass
     *            The index of the desired wrapper class, according to
     *            <code>classNames</code> vector.
     * @return The name of the folder so it can be used with
     *         <code>FileStreamUtils.customizeFileStream</code>. Returns empty
     *         string if there is not supposed to be any <code>colClass</code>
     *         wrapper class for this type of template.
     */
    private String getTemplateFolder(int rowTemplate,int colClass,int mwVer){
	int k;
	if(mwVer>1){
	    k=templateMatrix110[rowTemplate][colClass];
	}else{
	    k=templateMatrix100[rowTemplate][colClass];
	}
	switch (k) {
	case 1:
	    return "generic_and_app";
	case 2:
	    return "actuator_and_appnogui";
	case 3:
	    return "gauge";
	case 4:
	    return "reasoner";
	default:
	    return "";
	}
    }

}