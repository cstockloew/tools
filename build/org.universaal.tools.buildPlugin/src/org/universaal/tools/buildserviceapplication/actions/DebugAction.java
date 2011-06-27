package org.universaal.tools.buildserviceapplication.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.maven.cli.MavenCli;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class DebugAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public DebugAction() {

	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		if (!BuildAction.getSelectedProjectPath().equals("")) {
			if (BuildAction.buildedProjects.contains(BuildAction
					.getSelectedProjectPath())) {
				try {

					if (checkIfFelixJarsExistInLocalRepo()) {

						ILaunchManager manager = DebugPlugin.getDefault()
								.getLaunchManager();
						ILaunchConfigurationType type = manager
								.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
						ILaunchConfigurationWorkingCopy wc = type.newInstance(
								null, CreateFelixPropertiesFile.artifactId);

						wc.setAttribute(
								"org.eclipse.debug.core.MAPPED_RESOURCE_PATHS",
								new ArrayList(
										Arrays.asList(new String[] { "/"
												+ CreateFelixPropertiesFile.artifactId })));
						wc.setAttribute(
								"org.eclipse.debug.core.MAPPED_RESOURCE_TYPES",
								new ArrayList(Arrays
										.asList(new String[] { "4" })));

						wc.setAttribute(
								"org.eclipse.jdt.debug.ui.INCLUDE_EXTERNAL_JARS",
								true);

						wc.setAttribute(
								IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,
								CreateFelixPropertiesFile.artifactId);
						wc.setAttribute(
								IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
								"org.apache.felix.main.Main");
						wc.setAttribute(
								IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS,
								"-Djava.protocol.handler.pkgs=org.ops4j.pax.url"
										+ " -Dfelix.config.properties=file:"
										+ Platform.getLocation().toString()
										+ "/.felix/conf/"
										+ CreateFelixPropertiesFile.artifactId
										+ "/config.properties"
										+ " -Dbundles.configuration.location=${workspace_loc}/rundir/confadmin");

						IPath toolsPath = new Path(
								MavenCli.userMavenConfigurationHome
										.getAbsolutePath()
										+ "\\repository\\org\\apache\\felix\\org.apache.felix.main\\3.2.2\\org.apache.felix.main-3.2.2.jar");
						IRuntimeClasspathEntry toolsEntry = JavaRuntime
								.newArchiveRuntimeClasspathEntry(toolsPath);

						IPath toolsPath2 = new Path(
								MavenCli.userMavenConfigurationHome
										.getAbsolutePath()
										+ "\\repository\\org\\ops4j\\pax\\url\\pax-url-mvn\\1.3.3\\pax-url-mvn-1.3.3.jar");
						IRuntimeClasspathEntry toolsEntry2 = JavaRuntime
								.newArchiveRuntimeClasspathEntry(toolsPath2);
						toolsEntry2
								.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);

						IPath toolsPath3 = new Path(
								MavenCli.userMavenConfigurationHome
										.getAbsolutePath()
										+ "\\repository\\org\\ops4j\\pax\\url\\pax-url-wrap\\1.3.3\\pax-url-wrap-1.3.3.jar");
						IRuntimeClasspathEntry toolsEntry3 = JavaRuntime
								.newArchiveRuntimeClasspathEntry(toolsPath3);
						toolsEntry3
								.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);

						IPath systemLibsPath = new Path(
								JavaRuntime.JRE_CONTAINER);
						IRuntimeClasspathEntry systemLibsEntry = JavaRuntime
								.newRuntimeContainerClasspathEntry(
										systemLibsPath,
										IRuntimeClasspathEntry.STANDARD_CLASSES);
						ArrayList classpath = new ArrayList();
						classpath.add(toolsEntry.getMemento());
						classpath.add(toolsEntry2.getMemento());
						classpath.add(toolsEntry3.getMemento());
						classpath.add(systemLibsEntry.getMemento());
						// wc.setAttribute("org.eclipse.jdt.launching.ATTR_CLASSPATH",
						// classpath);
						wc.setAttribute("org.eclipse.jdt.launching.CLASSPATH",
								classpath);

						IPath toolsPath5 = new Path(
								MavenCli.userMavenConfigurationHome
										.getAbsolutePath()
										+ "\\repository\\org\\apache\\felix\\org.apache.felix.main\\3.2.2\\org.apache.felix.main-3.2.2.jar");
						IRuntimeClasspathEntry toolsEntry5 = JavaRuntime
								.newArchiveRuntimeClasspathEntry(toolsPath5);

						ArrayList classpath2 = new ArrayList();
						classpath2.add(toolsEntry5.getMemento());
						classpath2.add(systemLibsEntry.getMemento());
						wc.setAttribute(
								"org.eclipse.jdt.launching.ATTR_CLASSPATH",
								classpath2);

						wc.setAttribute(
								"org.eclipse.jdt.launching.ATTR_DEFAULT_CLASSPATH",
								false);
						wc.setAttribute(
								"org.eclipse.jdt.launching.DEFAULT_CLASSPATH",
								false);

						ILaunchConfiguration config = wc.doSave();

						// refresh the eclipse workspace
						ResourcesPlugin.getWorkspace().getRoot()
								.refreshLocal(IResource.DEPTH_INFINITE, null);
						config.launch(ILaunchManager.DEBUG_MODE, null);

					}
				} catch (Exception ex) {
					ex.printStackTrace();
					MessageDialog.openInformation(null,
							"BuildServiceApplication",
							"Could not run application");
				}
			} else {
				MessageDialog.openInformation(null, "BuildServiceApplication",
						"Please build the project first.");
			}
		} else {
			MessageDialog.openInformation(null, "BuildServiceApplication",
					"Please select a project in the Project Explorer tab.");
		}
	}

	private boolean checkIfFelixJarsExistInLocalRepo() {
		File felixFile = new File(
				MavenCli.userMavenConfigurationHome.getAbsolutePath()
						+ "\\repository\\org\\apache\\felix\\org.apache.felix.main\\3.2.2\\org.apache.felix.main-3.2.2.jar");
		if (!felixFile.exists()) {
			MessageDialog
					.openInformation(null, "BuildServiceApplication",
							"Felix execution environment is missing from your local Maven repository.");
			return false;
		}
		File mvnFile = new File(
				MavenCli.userMavenConfigurationHome.getAbsolutePath()
						+ "\\repository\\org\\ops4j\\pax\\url\\pax-url-mvn\\1.3.3\\pax-url-mvn-1.3.3.jar");
		if (!mvnFile.exists()) {
			MessageDialog
					.openInformation(null, "BuildServiceApplication",
							"Mvn pax url protocol is missing from your local Maven repository.");
			return false;
		}
		File wrapFile = new File(
				MavenCli.userMavenConfigurationHome.getAbsolutePath()
						+ "\\repository\\org\\ops4j\\pax\\url\\pax-url-wrap\\1.3.3\\pax-url-wrap-1.3.3.jar");
		if (!wrapFile.exists()) {
			MessageDialog
					.openInformation(null, "BuildServiceApplication",
							"Wrap pax url protocol is missing from your local Maven repository.");
			return false;
		}
		return true;
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

}