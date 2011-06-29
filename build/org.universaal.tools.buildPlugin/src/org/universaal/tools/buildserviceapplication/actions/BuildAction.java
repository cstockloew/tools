package org.universaal.tools.buildserviceapplication.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.maven.Maven;
import org.apache.maven.artifact.metadata.ArtifactMetadata;
import org.apache.maven.cli.MavenCli;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulator;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingRequest;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class BuildAction implements IWorkbenchWindowActionDelegate {
	private static IWorkbenchWindow window;
	private static MavenExecutionRequestPopulator populator;
	private static DefaultPlexusContainer container;
	private static Maven maven;
	static public List<String> buildedProjects = new ArrayList<String>();
	private SettingsBuilder settingsBuilder;
	static public String artifactFileName = "";
	static public Collection<ArtifactMetadata> artifactMetadata = null;
	private MavenExecutionResult installResult = null;

	/**
	 * The constructor.
	 */
	public BuildAction() {
	}

	static IResource extractSelection(ISelection sel) {
		if (!(sel instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) sel;
		if (ss.getFirstElement() instanceof IProject) {
			IProject element = (IProject) ss.getFirstElement();
			if (element instanceof IResource)
				return (IResource) element;
			if (!(element instanceof IAdaptable))
				return null;
			IAdaptable adaptable = (IAdaptable) element;
			Object adapter = adaptable.getAdapter(IResource.class);
			return (IResource) adapter;
		} else if (ss.getFirstElement() instanceof JavaProject) {
			JavaProject element = (JavaProject) ss.getFirstElement();
			if (element instanceof IResource)
				return (IResource) element;
			if (!(element instanceof IAdaptable))
				return null;
			IAdaptable adaptable = (IAdaptable) element;
			Object adapter = adaptable.getAdapter(IResource.class);
			return (IResource) adapter;
		} else if (ss.getFirstElement() instanceof PackageFragmentRoot) {
			PackageFragmentRoot root = (PackageFragmentRoot) ss
					.getFirstElement();
			IJavaProject element = root.getJavaProject();
			if (element instanceof IResource)
				return (IResource) element;
			if (!(element instanceof IAdaptable))
				return null;
			IAdaptable adaptable = (IAdaptable) element;
			Object adapter = adaptable.getAdapter(IResource.class);
			return (IResource) adapter;
		} else if (ss.getFirstElement() instanceof CompilationUnit) {
			CompilationUnit root = (CompilationUnit) ss.getFirstElement();
			IJavaProject element = root.getJavaProject();
			if (element instanceof IResource)
				return (IResource) element;
			if (!(element instanceof IAdaptable))
				return null;
			IAdaptable adaptable = (IAdaptable) element;
			Object adapter = adaptable.getAdapter(IResource.class);
			return (IResource) adapter;
		} else if (ss.getFirstElement() instanceof org.eclipse.core.internal.resources.File) {
			org.eclipse.core.internal.resources.File root = (org.eclipse.core.internal.resources.File) ss
					.getFirstElement();
			IProject element = root.getProject();
			if (element instanceof IResource)
				return (IResource) element;
			if (!(element instanceof IAdaptable))
				return null;
			IAdaptable adaptable = (IAdaptable) element;
			Object adapter = adaptable.getAdapter(IResource.class);
			return (IResource) adapter;
		} else if (ss.getFirstElement() instanceof org.eclipse.jdt.internal.ui.packageview.ClassPathContainer) {
			org.eclipse.jdt.internal.ui.packageview.ClassPathContainer root = (org.eclipse.jdt.internal.ui.packageview.ClassPathContainer) ss
					.getFirstElement();
			IJavaProject element = root.getJavaProject();
			if (element instanceof IResource)
				return (IResource) element;
			if (!(element instanceof IAdaptable))
				return null;
			IAdaptable adaptable = (IAdaptable) element;
			Object adapter = adaptable.getAdapter(IResource.class);
			return (IResource) adapter;
		} else if (ss.getFirstElement() instanceof PackageFragment) {
			PackageFragment root = (PackageFragment) ss.getFirstElement();
			IJavaProject element = root.getJavaProject();
			if (element instanceof IResource)
				return (IResource) element;
			if (!(element instanceof IAdaptable))
				return null;
			IAdaptable adaptable = (IAdaptable) element;
			Object adapter = adaptable.getAdapter(IResource.class);
			return (IResource) adapter;
		} else if (ss.getFirstElement() instanceof Folder) {
			Folder root = (Folder) ss.getFirstElement();
			IProject element = root.getProject();
			if (element instanceof IResource)
				return (IResource) element;
			if (!(element instanceof IAdaptable))
				return null;
			IAdaptable adaptable = (IAdaptable) element;
			Object adapter = adaptable.getAdapter(IResource.class);
			return (IResource) adapter;
		}
		try {
			JavaProject element = (JavaProject) ss.getFirstElement();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the root path of the selected project within eclipse workspace.
	 */
	static public String getSelectedProjectPath() {
		try {
			IWorkbenchPage page = window.getActivePage();
			ISelection selection = page
					.getSelection(IPageLayout.ID_PROJECT_EXPLORER);
			IResource res = extractSelection(selection);

			IProject iproject = res.getProject();
			return iproject.getLocation().toOSString();
		} catch (Exception ex) {
			return "";
		}
	}

	static public String getSelectedProjectName() {
		try {
			IWorkbenchPage page = window.getActivePage();
			ISelection selection = page
					.getSelection(IPageLayout.ID_PROJECT_EXPLORER);
			IResource res = extractSelection(selection);
			IProject iproject = res.getProject();
			return iproject.getName();
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try {
			final String selectedProject = getSelectedProjectPath();
			if (!selectedProject.equals("")) {

				setUpMavenBuild();
				installResult = install(selectedProject);
				artifactFileName = installResult.getProject().getArtifact()
						.getFile().getName();
				artifactMetadata = installResult.getProject().getArtifact()
						.getMetadataList();
				Iterator<ArtifactMetadata> it = artifactMetadata.iterator();
				while (it.hasNext()) {
					ArtifactMetadata metadata = it.next();
					metadata.getRemoteFilename();
				}
				if (installResult.hasExceptions()) {
					String exceptions = "Errors found:\n";
					for (int i = 0; i < installResult.getExceptions().size(); i++) {
						exceptions = exceptions
								+ installResult.getExceptions().get(i)
										.getMessage() + "\n\n";
					}
					MessageDialog.openInformation(window.getShell(),
							"BuildServiceApplication", "Building of project \""
									+ getSelectedProjectName()
									+ "\" failed.\n\n" + exceptions);
				} else {
					MessageDialog.openInformation(window.getShell(),
							"BuildServiceApplication", "Building of project \""
									+ getSelectedProjectName()
									+ "\" succeeded.");
					buildedProjects.add(selectedProject);
				}

			} else {
				MessageDialog.openInformation(null, "BuildServiceApplication",
						"Please select a project in the Project Explorer tab.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			MessageDialog.openInformation(window.getShell(),
					"BuildServiceApplication",
					"Service/Application building failed");
		}
	}

	/**
	 * Sets up Maven embedder for execution.
	 */
	protected void setUpMavenBuild() {
		try {
			container = new DefaultPlexusContainer();
			maven = container.lookup(Maven.class);
			populator = container.lookup(MavenExecutionRequestPopulator.class);
			settingsBuilder = container.lookup(SettingsBuilder.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Maven Excecution request.
	 */
	public MavenExecutionRequest createExecutionRequest() throws Exception {
		SettingsBuildingRequest settingsRequest = new DefaultSettingsBuildingRequest();
		settingsRequest
				.setUserSettingsFile(MavenCli.DEFAULT_USER_SETTINGS_FILE);
		settingsRequest
				.setGlobalSettingsFile(MavenCli.DEFAULT_GLOBAL_SETTINGS_FILE);
		MavenExecutionRequest request = new DefaultMavenExecutionRequest();
		request.setUserSettingsFile(settingsRequest.getUserSettingsFile());
		request.setGlobalSettingsFile(settingsRequest.getGlobalSettingsFile());
		request.setSystemProperties(System.getProperties());
		populator.populateFromSettings(request,
				settingsBuilder.build(settingsRequest).getEffectiveSettings());
		return request;
	}

	/**
	 * Runs maven -clean/install command which builds the project and installs
	 * artifact to the local repository
	 */
	protected MavenExecutionResult install(String path) throws Exception {
		File basedir = new File(getSelectedProjectPath());
		MavenExecutionRequest request = createExecutionRequest();
		request.setPom(new File(basedir, "pom.xml"));
		request.setGoals(Arrays.asList("clean"));
		request.setGoals(Arrays.asList("install"));
		populator.populateDefaults(request);
		MavenExecutionResult result = maven.execute(request);
		return result;

	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	static public MavenExecutionRequestPopulator getMavenPopulator() {
		return populator;
	}

	static public DefaultPlexusContainer getMavenPlexusContainer() {
		return container;
	}

	static public Maven getMaven() {
		return maven;
	}

}