package org.universaal.tools.externalserviceintegrator.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



import org.apache.maven.model.Model;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.ui.internal.actions.OpenMavenConsoleAction;
import org.eclipse.m2e.core.project.ProjectImportConfiguration;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.ui.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.IProgressConstants;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.core.resources.*;

import org.universAAL.ri.wsdlToolkit.ioApi.ParsedWSDLDefinition;
import org.universaal.tools.externalserviceintegrator.Activator;




public class createOntologyProject {
	private static final ProjectFolder JAVA = new ProjectFolder(
			"src/main/java", "target/classes"); 
	private static final ProjectFolder JAVA_TEST = new ProjectFolder(
			"src/test/java", "target/test-classes"); 
	private static final ProjectFolder RESOURCES = new ProjectFolder(
			"src/main/resources", "target/classes"); 
	private static final ProjectFolder RESOURCES_TEST = new ProjectFolder(
			"src/test/resources", "target/test-classes"); 
	private static final ProjectFolder[] JAR_DIRS = { JAVA, JAVA_TEST,
			RESOURCES, RESOURCES_TEST };
	private ParsedWSDLDefinition theParsedDefinition;
	private String operationName = "";
	final Model model = new Model();

	public createOntologyProject(ParsedWSDLDefinition theParsedDefinition,
			String operationName) {
		this.theParsedDefinition = theParsedDefinition;
		this.operationName = operationName;
	}

	public boolean createIProject() {
		try {

			final ProjectImportConfiguration configuration = new ProjectImportConfiguration();
			;
			model.setModelVersion("4.0.0");
			model.setGroupId("gr.test");
			model.setArtifactId("ont." + operationName);
			model.setVersion("0.0.1");
			model.setName(operationName + " Ontology");
			model.setDescription("blah blah");
			IStatus nameStatus = configuration.validateProjectName(model);
			if (!nameStatus.isOK()) {
				System.out.println("Could not create project");
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
				System.out.println("project already exists");
				return false;
			}
			final Job job, job2;

			// This job creates a blank maven project with the POM as defined in
			// the
			// wizard
			job = new WorkspaceJob("Creating new uAAL project") {
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
			job2 = new WorkspaceJob("Shaping new uAAL project") { //$NON-NLS-1$
				public IStatus runInWorkspace(IProgressMonitor monitor) {
					setProperty(IProgressConstants.ACTION_PROPERTY,
							new OpenMavenConsoleAction());
					try {
						// Set the name & create the package
						IFolder src = project.getFolder(JAVA.getPath());

						// create the activator
						IFile f1 = src.getFile("Activator.java"); //$NON-NLS-1$
					   f1.create(createActivator(), true, monitor);
					  
						// Now edit the POM file
						IFile pom = project.getFile("pom.xml"); //$NON-NLS-1$
						pom.setContents(
								generatePomFile(pom.getContents()), true, true,
								monitor);
						// This is like refreshing, because we changed the pom
						MavenPlugin.getProjectConfigurationManager()
								.updateProjectConfiguration(project, monitor);
						return Status.OK_STATUS;
					} catch (CoreException e) {
						return e.getStatus();
					} catch (Exception e) {
						e.printStackTrace();
						return new Status(Status.ERROR, Activator.PLUGIN_ID,
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
												PlatformUI
														.getWorkbench()
														.getActiveWorkbenchWindow()
														.getShell(), //
												"Error creating new project: Could not create Maven project!", result //$NON-NLS-1$
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
												PlatformUI
														.getWorkbench()
														.getActiveWorkbenchWindow()
														.getShell(), //
												"Error creating new project: Could not create initial files or edit POM!", result //$NON-NLS-1$
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
				job.setRule(MavenPlugin.getProjectConfigurationManager()
						.getRule());
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
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
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

	private InputStream generatePomFile(InputStream instream)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				instream));
		StringBuilder output = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains("</project>")) { //$NON-NLS-1$
				output.append("  <packaging>bundle</packaging>\n"); //$NON-NLS-1$
				output.append("    <dependencies>\n" //$NON-NLS-1$
						+ "		<dependency>\n" //$NON-NLS-1$
						+ "			<groupId>org.apache.felix</groupId>\n" //$NON-NLS-1$
						+ "			<artifactId>org.osgi.core</artifactId>\n" //$NON-NLS-1$
						+ "			<version>1.0.1</version>\n" //$NON-NLS-1$
						+ "		</dependency>\n" //$NON-NLS-1$
						+ "		<dependency>\n" //$NON-NLS-1$
						+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
						+ "			<artifactId>mw.data.representation</artifactId>\n" //$NON-NLS-1$
						+ "			<version>1.0.1-SNAPSHOT</version>\n" //$NON-NLS-1$
						+ "		</dependency>\n"); //$NON-NLS-1$

				output.append("		<dependency>\n" //$NON-NLS-1$
						+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
						+ "			<artifactId>mw.bus.context</artifactId>\n" //$NON-NLS-1$
						+ "			<version>1.0.1-SNAPSHOT</version>\n" //$NON-NLS-1$
						+ "		</dependency>\n"); //$NON-NLS-1$

				output.append("		<dependency>\n" //$NON-NLS-1$
						+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
						+ "			<artifactId>mw.bus.io</artifactId>\n" //$NON-NLS-1$
						+ "			<version>1.0.1-SNAPSHOT</version>\n" //$NON-NLS-1$
						+ "		</dependency>\n"); //$NON-NLS-1$

				output.append("		<dependency>\n" //$NON-NLS-1$
						+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
						+ "			<artifactId>mw.bus.service</artifactId>\n" //$NON-NLS-1$
						+ "			<version>1.0.1-SNAPSHOT</version>\n" //$NON-NLS-1$
						+ "		</dependency>\n"); //$NON-NLS-1$

				output.append("		<dependency>\n" //$NON-NLS-1$
						+ "			<groupId>org.universAAL.ontology</groupId>\n" //$NON-NLS-1$
						+ "			<artifactId>ont.phWorld</artifactId>\n" //$NON-NLS-1$
						+ "			<version>1.0.1-SNAPSHOT</version>\n" //$NON-NLS-1$
						+ "		</dependency>\n"); //$NON-NLS-1$

				output.append("	</dependencies>\n"); //$NON-NLS-1$
				output.append("    <build>\n" //$NON-NLS-1$
						+ "		<plugins>\n" //$NON-NLS-1$
						+ "			<plugin>\n" //$NON-NLS-1$
						+ "				<groupId>org.apache.felix</groupId>\n" //$NON-NLS-1$
						+ "				<artifactId>maven-bundle-plugin</artifactId>\n" //$NON-NLS-1$
						+ "				<extensions>true</extensions>\n" //$NON-NLS-1$
						+ "				<configuration>\n" //$NON-NLS-1$
						+ "					<instructions>\n" //$NON-NLS-1$
						+ "						<Bundle-Name>${project.name}</Bundle-Name>\n" //$NON-NLS-1$
						+ "						<Bundle-Activator>" //$NON-NLS-1$
						+ model.getGroupId()
						+ ".Activator</Bundle-Activator>\n" //$NON-NLS-1$
						+ "						<Bundle-Description>${project.description}</Bundle-Description>\n" //$NON-NLS-1$
						+ "						<Bundle-SymbolicName>${project.artifactId}</Bundle-SymbolicName>\n" //$NON-NLS-1$
						+ "					</instructions>\n" //$NON-NLS-1$
						+ "				</configuration>\n" //$NON-NLS-1$
						+ "			</plugin>\n" + "		</plugins>\n" //$NON-NLS-1$ //$NON-NLS-2$
						+ "	</build>\n"); //$NON-NLS-1$
				output.append("	<repositories>\n" //$NON-NLS-1$
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

	}
	
	
	
	 protected static InputStream createActivator() throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					FileStreamUtils.class.getClassLoader().getResourceAsStream(
						 "Activator" + ".java")));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
			 
			
				    output.append("import org.universAAL.middleware.context.ContextPublisher;\n"); //$NON-NLS-1$
				    output.append("import org.universAAL.middleware.context.DefaultContextPublisher;\n"); //$NON-NLS-1$
				
				    output.append("import org.universAAL.middleware.service.ServiceCaller;\n"); //$NON-NLS-1$
				    output.append("import org.universAAL.middleware.service.DefaultServiceCaller;\n"); //$NON-NLS-1$
			
				    output.append("	public static SCallee scallee=null;\n"); //$NON-NLS-1$
				
					output.append("	public static ServiceCaller scaller=null;\n"); //$NON-NLS-1$
				
					output.append("	public static SCaller scaller=null;\n"); //$NON-NLS-1$
				
				    output.append("	public static ISubscriber isubscriber=null;\n"); //$NON-NLS-1$
			
				    output.append("	public static OPublisher opublisher=null;\n"); //$NON-NLS-1$
				
				    output.append("	public static CSubscriber csubscriber=null;\n"); //$NON-NLS-1$
				
					output.append("	public static ContextPublisher cpublisher=null;\n"); //$NON-NLS-1$
				 
					output.append("	public static CPublisher cpublisher=null;\n"); //$NON-NLS-1$
			
				    output.append("	public static IPublisher ipublisher=null;\n"); //$NON-NLS-1$
				
				    output.append("	public static OSubscriber osubscriber=null;\n"); //$NON-NLS-1$
			   
				    output.append("		scallee=new SCallee(context);\n"); //$NON-NLS-1$
				
					output.append("		scaller=new DefaultServiceCaller(context);\n"); //$NON-NLS-1$
				
					output.append("		scaller=new SCaller(context);\n"); //$NON-NLS-1$
			
				    output.append("		isubscriber=new ISubscriber(context);\n"); //$NON-NLS-1$
				
				    output.append("		opublisher=new OPublisher(context);\n"); //$NON-NLS-1$
				
				    output.append("		csubscriber=new CSubscriber(context);\n"); //$NON-NLS-1$
			
				 
					output.append("		cpublisher=new DefaultContextPublisher(context,null);\n"); //$NON-NLS-1$
				 
					output.append("		cpublisher=new CPublisher(context);\n"); //$NON-NLS-1$
			
				
				    output.append("		ipublisher=new IPublisher(context);\n"); //$NON-NLS-1$
				
				    output.append("		osubscriber=new OSubscriber(context);\n"); //$NON-NLS-1$
			 
			
				output.append(line + "\n"); //$NON-NLS-1$
			   
			}
			return new ByteArrayInputStream(output.toString().getBytes());
		 
	 }
	
	
	
	
	
	
	
}
