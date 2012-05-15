package org.universaal.tools.modelling.ontology.wizard.wizards;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Repository;
import org.apache.maven.model.RepositoryPolicy;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.ProjectImportConfiguration;
import org.eclipse.m2e.core.ui.internal.actions.OpenMavenConsoleAction;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.progress.IProgressConstants;
import org.universaal.tools.modelling.ontology.wizard.Activator;
import org.codehaus.plexus.util.xml.Xpp3Dom;

public class OntologyProjectWizard extends Wizard implements INewWizard {
	
	OntologyProjectModel ontologyProjectModel = new OntologyProjectModel();
	
	OntologyMainPage mainPage;
	OntologyImportPage importPage;
	//MavenDetailsPage mavenPage;
	IWorkbench workbench;
	

	public OntologyProjectWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		mainPage = new OntologyMainPage();
		mainPage.setModel(ontologyProjectModel);
		importPage = new OntologyImportPage();
		importPage.setModel(ontologyProjectModel);
		//mavenPage = new MavenDetailsPage();
		//mavenPage.setModel(ontologyProjectModel.mavenModel);
		addPage(mainPage);
		addPage(importPage);
		//addPage(mavenPage);
	}

	@Override
	public boolean performFinish() {
/* Possible code to check project validity		
	    String projectName = getProjectName(model); 
	    IWorkspace workspace = ResourcesPlugin.getWorkspace();

	    // check if the project name is valid
	    IStatus nameStatus = workspace.validateName(projectName, IResource.PROJECT);
	    if(!nameStatus.isOK()) {
	      return nameStatus;
	    }

	    // check if project already exists
	    if(workspace.getRoot().getProject(projectName).exists()) {
	      return new Status(IStatus.ERROR, IMavenConstants.PLUGIN_ID, 0,
	          NLS.bind(Messages.importProjectExists, projectName), null); //$NON-NLS-1$
	    }
	    
	    return Status.OK_STATUS;		
*/
		//mavenPage.updateModel();
		
	    final ProjectImportConfiguration configuration;
	    final Model model = ontologyProjectModel.getMavenModel();
		configuration = new ProjectImportConfiguration();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		//final IPath location = null;
		final IWorkspaceRoot root = workspace.getRoot();
		final IProject project = configuration.getProject(root, model);

		// If there is already a pom there we cannot create the project
		boolean pomExists = (root.getLocation().append(project.getName()))
			.append(IMavenConstants.POM_FILE_NAME).toFile().exists();
		if (pomExists) {
		    MessageDialog.openError(getShell(),
			    Messages.getString("Project.2"), //$NON-NLS-1$
			    Messages.getString("Project.3")); //$NON-NLS-1$
		    return false;
		}

		// Now we are ready to start the real work
		return performFactoryStuffToBeRefactored(project, model, configuration, workspace);
		
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
	}

	
	protected static Dependency dep(String groupId, String artifactId, String version) {
		Dependency d = new Dependency();
		d.setGroupId(groupId);
		d.setArtifactId(artifactId);
		d.setVersion(version);
		return d;
	}
	
	
	Dependency[] dependencies = new Dependency[] {
			dep("org.apache.felix", "org.osgi.core", "1.0.1"),
			dep("org.universAAL.middleware","mw.data.representation", "1.1.0"),
			dep("org.universAAL.middleware", "mw.bus.model", "1.1.0"),
			dep("org.universAAL.middleware", "mw.container.xfaces", "1.1.0"),
			dep("org.universAAL.middleware", "mw.container.osgi","1.1.0" ),
			dep("org.universAAL.middleware", "mw.bus.service","1.1.0" ),
			dep("org.universAAL.middleware", "mw.bus.context", "1.1.0"),
			dep("org.universAAL.middleware", "mw.bus.ui", "1.1.0"),
			dep("org.universAAL.ontology", "ont.phWorld", "1.1.0"),
			dep("org.universAAL.ontology", "ont.profile", "1.1.0")
					
	};	
	
	Repository[] repositories = new Repository[] {
			rep("central","Central Maven Repository", "http://repo1.maven.org/maven2", true, false, null ),
			rep("apache-snapshots", "Apache Snapshots","http://people.apache.org/repo/m2-snapshot-repository", false, true, "daily" ),
			rep("uaal", "universAAL Repositories", "http://depot.universaal.org/maven-repo/releases/", true, false, null ),
			rep("uaal-snapshots", "universAAL Snapshot Repositories", "http://depot.universaal.org/maven-repo/snapshots/", false, true, null)
	};
	
	protected static Repository rep(String id, String name, String url, boolean releases, boolean snapshots, String snapshotPolicy ) {
		Repository rep = new Repository();
		rep.setId(id);
		rep.setName(name);
		rep.setUrl(url);
		if (!releases) {
			RepositoryPolicy pol = new RepositoryPolicy();
			pol.setEnabled(false);
			rep.setReleases(pol);
		}
		if ((!snapshots) || (snapshotPolicy != null)) {
			RepositoryPolicy pol = new RepositoryPolicy();
			pol.setEnabled(snapshots);
			if (snapshotPolicy != null)
				pol.setUpdatePolicy(snapshotPolicy);
			rep.setSnapshots(pol);
		}
		return rep;
	}
	
	
	protected static Xpp3Dom dom(String name, String value) {
		Xpp3Dom dom = new Xpp3Dom(name);
		dom.setValue(value);
		return dom;
	}
	
	
	protected Build createBuild() {
		Build build = new Build();
		Plugin plugin = new Plugin();
		plugin.setGroupId("org.apache.felix");
		plugin.setArtifactId("maven-bundle-plugin");
		plugin.setExtensions(true);
		Xpp3Dom conf = new Xpp3Dom("configuration");
		Xpp3Dom instr = new Xpp3Dom("instructions");
		instr.addChild(dom("Bundle-Name", "${project.name}"));
		instr.addChild(dom("Bundle-Activator", ontologyProjectModel.getParentPackageName() + ".osgi.Activator")); 
		instr.addChild(dom("Bundle-Description", "${project.description}"));
		instr.addChild(dom("Bundle-SymbolicName", "${project.artifactId}"));
		instr.addChild(dom("Export-Package", ontologyProjectModel.getPackageName() + ".*")); 
		instr.addChild(dom("Private-Package", ontologyProjectModel.getParentPackageName() + ".*")); 
		conf.addChild(instr);
		plugin.setConfiguration(conf);
		build.addPlugin(plugin);
		return build;
	}
		
	
	static final String[] folders = new String[] {
		"src/main/java", "src/test/java", "src/main/resources", "src/test/resources" };

	/** The content of this method will later be refactored to use factory class
	 * 
	 * @return
	 */
	public boolean performFactoryStuffToBeRefactored(final IProject project, final Model model, final ProjectImportConfiguration configuration, IWorkspace workspace ) {
		final Job job, job2;

		// This job creates a blank maven project with the POM as defined in the
		// wizard
		job = new WorkspaceJob(
			Messages.getString("Project.8")) { //$NON-NLS-1$
		    public IStatus runInWorkspace(IProgressMonitor monitor) {
			setProperty(IProgressConstants.ACTION_PROPERTY,
				new OpenMavenConsoleAction());
			try {
			    // Here we use the maven plugin to create and shape the
			    // project
				for (Dependency dep : dependencies) {
					model.addDependency(dep);
				}
				for (Repository rep : repositories) {
					model.addRepository(rep);
				}
				model.setBuild(createBuild());
			    MavenPlugin.getProjectConfigurationManager()
				    .createSimpleProject(project, null,//was: location
				    		model,
					    folders, //
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
			Messages.getString("Project.9")) { //$NON-NLS-1$
		    public IStatus runInWorkspace(IProgressMonitor monitor) {
			setProperty(IProgressConstants.ACTION_PROPERTY,
				new OpenMavenConsoleAction());
			try {
				OntologyUMLArtefactFactory.createUMLArtefacts(ontologyProjectModel);
				
			    // This is like refreshing, because we changed the pom
			    //MavenPlugin.getProjectConfigurationManager()
				//    .updateProjectConfiguration(project, monitor);
			    return Status.OK_STATUS;
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
						    Messages
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
						    Messages
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
	
}
