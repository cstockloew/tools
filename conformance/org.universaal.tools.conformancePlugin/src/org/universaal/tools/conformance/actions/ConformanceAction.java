package org.universaal.tools.conformance.actions;

import java.io.File;
import java.net.URL;
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
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;
import org.sonar.ide.eclipse.internal.ui.wizards.ConfigureProjectsWizard;
import org.sonar.ide.eclipse.internal.ui.wizards.ConfigureProjectsWizard.ConfigureProjectsPage.AssociateProjects;


/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class ConformanceAction implements IWorkbenchWindowActionDelegate {
	public static IWorkbenchWindow window;
	public static MavenExecutionRequestPopulator populator;
	public static DefaultPlexusContainer container;
	public static Maven maven;
	static public List<String> buildedProjects = new ArrayList<String>();
	static public SettingsBuilder settingsBuilder;
	static public String artifactFileName = "";
	static public Collection<ArtifactMetadata> artifactMetadata = null;
	private MavenExecutionResult installResult = null;
	private static Shell activeShell = null;
	private String selectedProjectName = "";
	private String selectedProjectPath = "";
	private static IProject iproject = null;

	/**
	 * The constructor.
	 */
	public ConformanceAction() {
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

	public static boolean isActiveView(final IWorkbenchPage activePage,
			final IViewPart view) { // obtain active page from WorkbenchWindow
		final IWorkbenchPart activeView = activePage.getActivePart();
		return activeView == null ? false : activeView.equals(view);
	}

	/**
	 * Returns the root path of the selected project within eclipse workspace.
	 */
	static public String getSelectedProjectPath() {
		try {

			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					activeShell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();

					IWorkbenchPage page = window.getActivePage();
					ISelection selection = null;
					boolean packageExplorerActive = false;
					boolean projectExplorerActive = false;
					page.getViewReferences();
					for (int i = 0; i < page.getViewReferences().length; i++) {
						IViewReference ref = page.getViewReferences()[i];
						if (ref.getPartName().equals("Package Explorer")) {
							IViewPart view = ref.getView(false);
							packageExplorerActive = isActiveView(page, view);
						} else if (ref.getPartName().equals("Project Explorer")) {
							IViewPart view = ref.getView(false);
							projectExplorerActive = isActiveView(page, view);
						}
					}
					if (projectExplorerActive && !packageExplorerActive) {
						selection = page
								.getSelection(IPageLayout.ID_PROJECT_EXPLORER);
					}
					if (!projectExplorerActive && packageExplorerActive
							|| selection == null
							|| ((IStructuredSelection) selection).isEmpty()) {
						selection = page
								.getSelection("org.eclipse.jdt.ui.PackageExplorer");
					}
					if (selection == null
							|| ((IStructuredSelection) selection).isEmpty()) {
						selection = page
								.getSelection(IPageLayout.ID_PROJECT_EXPLORER);
					}
					IResource res = extractSelection(selection);
					iproject = res.getProject();

				}
			});
			return iproject.getLocation().toOSString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	static public String getSelectedProjectName() {
		try {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					IWorkbenchPage page = window.getActivePage();
					ISelection selection = null;
					boolean packageExplorerActive = false;
					boolean projectExplorerActive = false;
					page.getViewReferences();
					for (int i = 0; i < page.getViewReferences().length; i++) {
						IViewReference ref = page.getViewReferences()[i];
						if (ref.getPartName().equals("Package Explorer")) {
							IViewPart view = ref.getView(false);
							packageExplorerActive = isActiveView(page, view);
						} else if (ref.getPartName().equals("Project Explorer")) {
							IViewPart view = ref.getView(false);
							projectExplorerActive = isActiveView(page, view);
						}
					}
					if (projectExplorerActive && !packageExplorerActive) {
						selection = page
								.getSelection(IPageLayout.ID_PROJECT_EXPLORER);
					}
					if (!projectExplorerActive && packageExplorerActive
							|| selection == null
							|| ((IStructuredSelection) selection).isEmpty()) {
						selection = page
								.getSelection("org.eclipse.jdt.ui.PackageExplorer");
					}
					if (selection == null
							|| ((IStructuredSelection) selection).isEmpty()) {
						selection = page
								.getSelection(IPageLayout.ID_PROJECT_EXPLORER);
					}
					IResource res = extractSelection(selection);
					iproject = res.getProject();

				}
			});
			return iproject.getName();
		} catch (Exception ex) {
			// ex.printStackTrace();
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
				selectedProjectName = getSelectedProjectName();
				selectedProjectPath = getSelectedProjectPath();
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
					public void run() {
						activeShell = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell();

					}
				});

				Job job = new Job("AAL Studio") {
					protected IStatus run(IProgressMonitor monitor) {
						try {
							monitor.beginTask("Testing conformance of application \""
									+ selectedProjectName + "\"...", 50);
							setProperty(IProgressConstants.KEEP_PROPERTY,
									Boolean.FALSE);
							URL url = Platform.getBundle(
									"org.universaal.tools.buildPlugin")
									.getEntry("icons/compile.png");
							setProperty(IProgressConstants.ICON_PROPERTY,
									ImageDescriptor.createFromURL(url));
							setUpMavenBuild();
							monitor.worked(15);
							installResult = runSonarGoal(selectedProjectPath);
							
							monitor.worked(50);
							if (installResult.hasExceptions()) {
								return Status.CANCEL_STATUS;
							} else {
								return Status.OK_STATUS;
							}
						} catch (Exception ex) {
							ex.printStackTrace();
							return Status.CANCEL_STATUS;
						}
					}
				};
				job.setUser(true);
				job.schedule();
				job.addJobChangeListener(new JobChangeAdapter() {
					public void done(final IJobChangeEvent event) {
						Display.getDefault().syncExec(new Runnable() {
							public void run() {
								if (event.getResult().isOK()) {
									MessageDialog.openInformation(activeShell,
											"Test conformance",
											"Testing conformance of application \""
													+ selectedProjectName
													+ "\" succeeded.");
									buildedProjects.add(selectedProject);
								} else {
									try {
										String exceptions = "Errors found:\n";
										for (int i = 0; i < installResult
												.getExceptions().size(); i++) {
											exceptions = exceptions
													+ installResult
															.getExceptions()
															.get(i)
															.getMessage()
													+ "\n\n";
										}
										MessageDialog.openInformation(
												activeShell,
												"Test conformance",
												"Testing conformance of project \""
														+ selectedProjectName
														+ "\" failed.\n\n"
														+ exceptions);
									} catch (Exception ex) {
										MessageDialog.openInformation(
												activeShell,
												"Test conformance",
												"Testing conformance of project \""
														+ selectedProjectName
														+ "\" failed.");
									}
								}
							}
						});

					}
				});

			} else {
				MessageDialog
						.openInformation(null, "Test conformance",
								"Please select a project in the Project/Package Explorer tab.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			MessageDialog.openInformation(window.getShell(),
					"Test conformance",
					"Testing conformance of conformance failed");
		}
	}

	
	
	
	
	
	
	

	protected Action getSonarWebView() {
		return new Action("Open Sonar web view") {
			public void run() {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().showView(
									"org.sonar.ide.eclipse.ui.views.WebView");
				} catch (PartInitException e) {

					e.printStackTrace();
				}
				// IWorkbenchWindow dw =
				// PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				// IWorkbenchPage page = dw.getActivePage();
				// try{
				// page.showView("WebView.ID");
				// }catch (Exception e){
				// e.printStackTrace();
				// }

			}
		};
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
	protected MavenExecutionResult runSonarGoal(String path) throws Exception {
		File basedir = new File(selectedProjectPath);
		MavenExecutionRequest request = createExecutionRequest();
		request.setPom(new File(basedir, "pom.xml"));		
		request.setGoals(Arrays.asList("sonar:sonar"));
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