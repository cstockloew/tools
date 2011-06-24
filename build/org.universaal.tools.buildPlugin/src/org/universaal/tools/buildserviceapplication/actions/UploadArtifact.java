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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

public class UploadArtifact {
	private String NEXUS_URL = "";
	private String NEXUS_USERNAME = "";
	private String NEXUS_PASSWORD = "";
	private IWorkbenchWindow window;
	private String repositoryPath = "";
	private boolean artifactUploaded = true;
	private boolean isArtifactRelease = true;

	public UploadArtifact(String nexusUrl, String nexusUserName,
			String nexusPassword) {
		this.NEXUS_URL = nexusUrl;
		this.NEXUS_USERNAME = nexusUserName;
		this.NEXUS_PASSWORD = nexusPassword;
	}

	public void upload() {
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
						if (CreateFelixPropertiesFile.artifactVersion
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
										"Uploading of application \""
												+ projectName + "\" failed.");
							} else {
								MessageDialog
										.openInformation(null,
												"BuildServiceApplication",
												"Uploading of application \""
														+ projectName
														+ "\" succeeded.");
							}
						} else {
							MessageDialog.openInformation(window.getShell(),
									"BuildServiceApplication",
									"Uploading of application \"" + projectName
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
							"Application uploading failed");
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
				webPage = NEXUS_URL + "releases/"
						+ CreateFelixPropertiesFile.groupId.replace(".", "/")
						+ "/" + CreateFelixPropertiesFile.artifactId + "/"
						+ CreateFelixPropertiesFile.artifactVersion + "/"
						+ BuildAction.artifactFileName;
			} else {
				webPage = NEXUS_URL + "snapshots/"
						+ CreateFelixPropertiesFile.groupId.replace(".", "/")
						+ "/" + CreateFelixPropertiesFile.artifactId + "/"
						+ CreateFelixPropertiesFile.artifactVersion + "/"
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
					+ CreateFelixPropertiesFile.groupId.replace(".", "/") + "/"
					+ CreateFelixPropertiesFile.artifactId + "/"
					+ CreateFelixPropertiesFile.artifactVersion + "/"
					+ BuildAction.artifactFileName);

			FileInputStream fis = new FileInputStream(file);
			char current;
			while (fis.available() > 0) {
				current = (char) fis.read();
				out.write(current);
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
						webPage = NEXUS_URL
								+ "releases/"
								+ CreateFelixPropertiesFile.groupId.replace(".",
										"/") + "/"
								+ CreateFelixPropertiesFile.artifactId + "/"
								+ CreateFelixPropertiesFile.artifactVersion + "/"
								+ metadata.getRemoteFilename();
					} else {
						webPage = NEXUS_URL
								+ "snapshots/"
								+ CreateFelixPropertiesFile.groupId.replace(".",
										"/") + "/"
								+ CreateFelixPropertiesFile.artifactId + "/"
								+ CreateFelixPropertiesFile.artifactVersion + "/"
								+ metadata.getRemoteFilename();
					}
				} else {
					if (isArtifactRelease) {
						webPage = NEXUS_URL
								+ "releases/"
								+ CreateFelixPropertiesFile.groupId.replace(".",
										"/") + "/"
								+ CreateFelixPropertiesFile.artifactId + "/"
								+ metadata.getRemoteFilename();
					} else {
						webPage = NEXUS_URL
								+ "snapshots/"
								+ CreateFelixPropertiesFile.groupId.replace(".",
										"/") + "/"
								+ CreateFelixPropertiesFile.artifactId + "/"
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
				File file = null;
				if (metadata.getRemoteFilename().endsWith(".pom")) {
					file = new File(repositoryPath + "repository/"
							+ CreateFelixPropertiesFile.groupId.replace(".", "/")
							+ "/" + CreateFelixPropertiesFile.artifactId + "/"
							+ CreateFelixPropertiesFile.artifactVersion + "/"
							+ metadata.getRemoteFilename());
				} else {
					file = new File(repositoryPath + "repository/"
							+ CreateFelixPropertiesFile.groupId.replace(".", "/")
							+ "/" + CreateFelixPropertiesFile.artifactId + "/"
							+ "maven-metadata-local.xml");
				}

				FileInputStream fis = new FileInputStream(file);
				char current;
				while (fis.available() > 0) {
					current = (char) fis.read();
					out.write(current);
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

}
