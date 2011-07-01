package org.universaal.tools.buildserviceapplication.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.codehaus.plexus.DefaultPlexusContainer;
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.apache.maven.Maven;
import org.apache.maven.cli.CLIManager;
import org.apache.maven.cli.MavenCli;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulator;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingRequest;

public class RunFelix {
	public static MavenExecutionRequestPopulator populator;
	public static DefaultPlexusContainer container;
	public static Maven maven;
	static public SettingsBuilder settingsBuilder;

	public void runFelix(boolean isDebugMode) {

		if (!BuildAction.getSelectedProjectPath().equals("")) {
			if (BuildAction.buildedProjects.contains(BuildAction
					.getSelectedProjectPath())) {
				try {
					// create configurations for launching on Eclipse 3.6
					CreateFelixPropertiesFile fel = new CreateFelixPropertiesFile();
					fel.createFile();

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
						IPath toolsPath4 = new Path(
								"D:\\org.apache.felix.fileinstall-3.1.10.jar");
						IRuntimeClasspathEntry toolsEntry4 = JavaRuntime
								.newArchiveRuntimeClasspathEntry(toolsPath4);
						toolsEntry4
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
						classpath.add(toolsEntry4.getMemento());
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
						if (!isDebugMode) {
							config.launch(ILaunchManager.RUN_MODE, null);
						} else {
							config.launch(ILaunchManager.DEBUG_MODE, null);
						}

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

	protected MavenExecutionResult downloadArtifact(String artifact)
			throws Exception {
		File basedir = new File(BuildAction.getSelectedProjectPath());
		setUpMavenBuild();
		MavenExecutionRequest request = createExecutionRequest();
		request.setPom(new File(basedir, "pom.xml"));
		request.setGoals(Arrays
				.asList("org.apache.maven.plugins:maven-dependency-plugin:2.1:get"));
		java.util.Properties prop = new java.util.Properties();
		prop.setProperty("artifact", artifact);
		prop.setProperty("repoUrl", "http://download.java.net/maven/2/");
		request.setUserProperties(prop);
		BuildAction.populator.populateDefaults(request);
		MavenExecutionResult result = BuildAction.maven.execute(request);
		return result;
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
			// ex.printStackTrace();
			maven = BuildAction.getMaven();
			populator = BuildAction.getMavenPopulator();
			settingsBuilder = BuildAction.getSettingsBuilder();
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
		File basedir = new File(BuildAction.getSelectedProjectPath());
		setUpMavenBuild();
		MavenExecutionRequest request = createExecutionRequest();
		request.setPom(new File(basedir, "pom.xml"));
		request.setGoals(Arrays.asList("clean"));
		request.setGoals(Arrays.asList("install"));
		populator.populateDefaults(request);
		MavenExecutionResult result = maven.execute(request);
		return result;

	}

	private boolean checkIfFelixJarsExistInLocalRepo() {
		File felixFile = new File(
				MavenCli.userMavenConfigurationHome.getAbsolutePath()
						+ File.separator + "repository" + File.separator
						+ "org" + File.separator + "apache" + File.separator
						+ "felix" + File.separator + "org.apache.felix.main"
						+ File.separator + "3.2.2" + File.separator
						+ "org.apache.felix.main-3.2.2.jar");
		if (!felixFile.exists()) {
			MavenExecutionResult installResult = null;
			try {
				installResult = downloadArtifact("org.apache.felix:org.apache.felix.main:3.2.2");
			} catch (Exception ex) {
				MessageDialog
						.openInformation(null, "BuildServiceApplication",
								"Felix execution environment is missing from your local Maven repository.");
				return false;
			}
			if (installResult.hasExceptions() || !felixFile.exists()) {
				MessageDialog
						.openInformation(null, "BuildServiceApplication",
								"Felix execution environment is missing from your local Maven repository.");
				return false;
			}

		}
		File mvnFile = new File(
				MavenCli.userMavenConfigurationHome.getAbsolutePath()
						+ File.separator + "repository" + File.separator
						+ "org" + File.separator + "ops4j" + File.separator
						+ "pax" + File.separator + "url" + File.separator
						+ "pax-url-mvn" + File.separator + "1.3.3"
						+ File.separator + "pax-url-mvn-1.3.3.jar");
		if (!mvnFile.exists()) {
			MavenExecutionResult installResult = null;
			try {
				installResult = downloadArtifact("org.ops4j.pax.url:pax-url-mvn:1.3.3");
			} catch (Exception ex) {
				MessageDialog
						.openInformation(null, "BuildServiceApplication",
								"Mvn pax url protocol is missing from your local Maven repository.");
				return false;
			}
			if (installResult.hasExceptions() || !mvnFile.exists()) {
				MessageDialog
						.openInformation(null, "BuildServiceApplication",
								"Mvn pax url protocol is missing from your local Maven repository.");
				return false;
			}
		}
		File wrapFile = new File(
				MavenCli.userMavenConfigurationHome.getAbsolutePath()
						+ File.separator + "repository" + File.separator
						+ "org" + File.separator + "ops4j" + File.separator
						+ "pax" + File.separator + "url" + File.separator
						+ "pax-url-wrap" + File.separator + "1.3.3"
						+ File.separator + "pax-url-wrap-1.3.3.jar");

		MavenExecutionResult installResult = null;
		try {
			installResult = downloadArtifact("org.ops4j.pax.url:pax-url-wrap:1.3.3");
		} catch (Exception ex) {
			MessageDialog
					.openInformation(null, "BuildServiceApplication",
							"Wrap pax url protocol is missing from your local Maven repository.");
			return false;
		}
		if (installResult.hasExceptions() || !wrapFile.exists()) {
			MessageDialog
					.openInformation(null, "BuildServiceApplication",
							"Wrap pax url protocol is missing from your local Maven repository.");
			return false;
		}
		return true;
	}

}
