package org.universaal.tools.buildserviceapplication.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import org.apache.maven.artifact.metadata.ArtifactMetadata;
import org.apache.maven.cli.MavenCli;
import org.codehaus.plexus.util.Base64;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
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
public class UploadAction implements IWorkbenchWindowActionDelegate {
	static private String NEXUS_URL="http://a1gforge.igd.fraunhofer.de/nexus/content/repositories/";
	static private String NEXUS_USERNAME="deployment";
	static private String NEXUS_PASSWORD="uaal_49_nexus";		
	private IWorkbenchWindow window;	
	private String repositoryPath = "";
	private boolean artifactUploaded = true;
	private boolean isArtifactRelease = true;
	
	
	/**
	 * The constructor.
	 */
	public UploadAction() {

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
					String selectedProject = BuildAction
							.getSelectedProjectPath();
					String projectName = selectedProject.split("/")[selectedProject
							.split("/").length - 1];
					if (!selectedProject.equals("")) {
						isArtifactRelease = true;
						if (CreateConfigurationFile.artifactVersion
								.contains("SNAPSHOT")) {
							isArtifactRelease = false;
						}
						postArtifact();
						if (artifactUploaded) {
							postMetadata();
							if (!artifactUploaded) {
								MessageDialog.openInformation(
										window.getShell(),
										"BuildServiceApplication",
										"Uploading of artifact \""
												+ projectName + "\" failed.");
							} else {
								MessageDialog
										.openInformation(null,
												"BuildServiceApplication",
												"Uploading of artifact \""
														+ projectName
														+ "\" succeeded.");
							}
						} else {
							MessageDialog.openInformation(window.getShell(),
									"BuildServiceApplication",
									"Uploading of artifact \"" + projectName
											+ "\" failed.");
						}
					} else {
						MessageDialog
								.openInformation(null,
										"BuildServiceApplication",
										"Please select a project in the Project Explorer tab.");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					MessageDialog.openInformation(window.getShell(),
							"BuildServiceApplication",
							"Service/Application artifact uploading failed");
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

	private void postArtifact() {
		artifactUploaded = true;
		String[] tempString = MavenCli.DEFAULT_USER_SETTINGS_FILE
				.getAbsolutePath().trim().replace("\\", "/").split("/");
		repositoryPath = "";
		for (int i = 0; i < tempString.length - 1; i++) {
			repositoryPath = repositoryPath + tempString[i] + "\\";
		}
		try {
			String webPage = "";
			if (isArtifactRelease) {
				webPage = NEXUS_URL+"releases/"
						+ CreateConfigurationFile.groupId.replace(".", "/")
						+ "/"
						+ CreateConfigurationFile.artifactId
						+ "/"
						+ CreateConfigurationFile.artifactVersion
						+ "/"
						+ BuildAction.artifactFileName;
			} else {
				webPage = NEXUS_URL+"snapshots/"
						+ CreateConfigurationFile.groupId.replace(".", "/")
						+ "/"
						+ CreateConfigurationFile.artifactId
						+ "/"
						+ CreateConfigurationFile.artifactVersion
						+ "/"
						+ BuildAction.artifactFileName;
			}
		

			String authString = NEXUS_USERNAME + ":" + NEXUS_PASSWORD;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			
			URL url = new URL(webPage);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic "
					+ authStringEnc);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("PUT");

			OutputStreamWriter out = new OutputStreamWriter(urlConnection
					.getOutputStream());
			
			File file = new File(repositoryPath + "repository/"
					+ CreateConfigurationFile.groupId.replace(".", "/") + "/"
					+ CreateConfigurationFile.artifactId + "/"
					+ CreateConfigurationFile.artifactVersion + "/"
					+ BuildAction.artifactFileName);
			try {
				FileInputStream fis = new FileInputStream(file);
				char current;
				while (fis.available() > 0) {
					current = (char) fis.read();
					out.write(current);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.close();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			int numCharsRead = 0;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
		
		} catch (Exception ex) {
			artifactUploaded = false;
			ex.printStackTrace();
		}
	}

	private void postMetadata() {
		Iterator<ArtifactMetadata> it = BuildAction.artifactMetadata.iterator();
		while (it.hasNext()) {
			ArtifactMetadata metadata = it.next();
			metadata.getRemoteFilename();
			try {
				String webPage = "";
				if (metadata.getRemoteFilename().endsWith(".pom")) {
					if (isArtifactRelease) {
						webPage = NEXUS_URL+"releases/"
								+ CreateConfigurationFile.groupId.replace(".", "/")
								+ "/"
								+ CreateConfigurationFile.artifactId
								+ "/"
								+ CreateConfigurationFile.artifactVersion
								+ "/"
								+ metadata.getRemoteFilename();
					} else {
						webPage = NEXUS_URL+"snapshots/"
								+ CreateConfigurationFile.groupId.replace(".", "/")
								+ "/"
								+ CreateConfigurationFile.artifactId
								+ "/"
								+ CreateConfigurationFile.artifactVersion
								+ "/"
								+ metadata.getRemoteFilename();
					}
				} else {
					if (isArtifactRelease) {
						webPage = NEXUS_URL+"releases/"
								+ CreateConfigurationFile.groupId.replace(".", "/")
								+ "/"
								+ CreateConfigurationFile.artifactId
								+ "/"
								+ metadata.getRemoteFilename();
					} else {
						webPage = NEXUS_URL+"snapshots/"
								+ CreateConfigurationFile.groupId.replace(".", "/")
								+ "/"
								+ CreateConfigurationFile.artifactId
								+ "/"
								+ metadata.getRemoteFilename();
					}
				}
			

				String authString = NEXUS_USERNAME + ":" + NEXUS_PASSWORD;
				byte[] authEncBytes = Base64
						.encodeBase64(authString.getBytes());
				String authStringEnc = new String(authEncBytes);
				
				URL url = new URL(webPage);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestProperty("Authorization", "Basic "
						+ authStringEnc);
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("PUT");

				OutputStreamWriter out = new OutputStreamWriter(urlConnection
						.getOutputStream());
				File file =null;
				if (metadata.getRemoteFilename().endsWith(".pom")) {
					file = new File(repositoryPath + "repository/"
							+ CreateConfigurationFile.groupId.replace(".", "/") + "/"
							+ CreateConfigurationFile.artifactId + "/"
							+ CreateConfigurationFile.artifactVersion
							+ "/"
							+ metadata.getRemoteFilename());
				}
				else{
				file = new File(repositoryPath + "repository/"
						+ CreateConfigurationFile.groupId.replace(".", "/") + "/"
						+ CreateConfigurationFile.artifactId + "/"
						+ "maven-metadata-local.xml");
				}
				try {
					FileInputStream fis = new FileInputStream(file);
					char current;
					while (fis.available() > 0) {
						current = (char) fis.read();
						out.write(current);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				out.close();
				InputStream is = urlConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				int numCharsRead = 0;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
			} catch (Exception ex) {
				artifactUploaded = false;
				ex.printStackTrace();
			}
		}
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