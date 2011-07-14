package org.universaal.tools.buildserviceapplication.actions;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import org.apache.maven.artifact.metadata.ArtifactMetadata;
import org.apache.maven.cli.MavenCli;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.Base64;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;

public class UploadArtifact {
	private String NEXUS_URL = "";
	private String NEXUS_USERNAME = "";
	private String NEXUS_PASSWORD = "";
	private IWorkbenchWindow window;
	private String repositoryPath = "";
	private boolean artifactUploaded = true;
	private boolean isArtifactRelease = true;
	static public String groupId = "";
	static public String artifactId = "";
	static public String artifactVersion = "";
	private Shell activeShell = null;

	public UploadArtifact(String nexusUrl, String nexusUserName,
			String nexusPassword) {
		this.NEXUS_URL = nexusUrl;
		this.NEXUS_USERNAME = nexusUserName;
		this.NEXUS_PASSWORD = nexusPassword;
	}

	public void upload() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				activeShell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
			}
		});
		if (!BuildAction.getSelectedProjectPath().equals("")) {
			if (BuildAction.buildedProjects.contains(BuildAction
					.getSelectedProjectPath())) {

				try {
					String selectedProject = BuildAction
							.getSelectedProjectPath();
					final String projectName = BuildAction
							.getSelectedProjectName();
					if (!selectedProject.equals("")) {
						isArtifactRelease = true;
						if (artifactVersion.contains("SNAPSHOT")) {
							isArtifactRelease = false;
						}
						Job job = new Job("AAL Studio") {
							protected IStatus run(IProgressMonitor monitor) {
								monitor.beginTask("Uploading application \""+projectName+"\"...", 50);
								setProperty(IProgressConstants.KEEP_PROPERTY,
										Boolean.FALSE);
								try {
									URL url = Platform.getBundle("org.universaal.tools.buildPlugin").getEntry("icons/upload.png");
									setProperty(IProgressConstants.ICON_PROPERTY, ImageDescriptor.createFromURL(url));
									postArtifact();
									monitor.worked(25);
									if (artifactUploaded) {
										postMetadata();
										monitor.worked(50);
										if (!artifactUploaded) {
											return Status.CANCEL_STATUS;
										} else {
											return Status.OK_STATUS;
										}
									} else {
										return Status.CANCEL_STATUS;
									}
								} catch (Exception ex) {
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
										if (event.getResult().isOK())
											MessageDialog.openInformation(
													activeShell,
													"BuildServiceApplication",
													"Uploading of application \""
															+ projectName
															+ "\" succeeded.");
										else
											MessageDialog.openInformation(
													activeShell,
													"BuildServiceApplication",
													"Uploading of application \""
															+ projectName
															+ "\" failed.");
									}
								});

							}
						});
					} else {
						MessageDialog
								.openInformation(null,
										"BuildServiceApplication",
										"Please select a project in the Project Explorer tab.");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					MessageDialog.openInformation(null,
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
	
	Reader reader=null;
	private void getBundleProperties() {
		try {			
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					try{
					 reader = new FileReader(BuildAction.getSelectedProjectPath()
							+ File.separator + "pom.xml");
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			});
			
			MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
			Model model = xpp3Reader.read(reader);
			groupId = model.getGroupId();
			artifactId = model.getArtifactId();
			artifactVersion = model.getVersion();

			// if groupId is null then search within its parent
			if (groupId == null && model.getParent() != null) {
				groupId = model.getParent().getGroupId();
			}
			// if artifactId is null then search within its parent
			if (artifactId == null && model.getParent() != null) {
				artifactId = model.getParent().getArtifactId();
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void postArtifact() {
		artifactUploaded = true;
		getBundleProperties();
		repositoryPath = MavenCli.userMavenConfigurationHome.getAbsolutePath();
		try {
			String webPage = "";
			if (isArtifactRelease) {
				webPage = NEXUS_URL + "releases/" + groupId.replace(".", "/")
						+ "/" + artifactId + "/" + artifactVersion + "/"
						+ BuildAction.artifactFileName;
			} else {
				webPage = NEXUS_URL + "snapshots/" + groupId.replace(".", "/")
						+ "/" + artifactId + "/" + artifactVersion + "/"
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

			OutputStreamWriter out = new OutputStreamWriter(
					urlConnection.getOutputStream());

			File file = new File(repositoryPath + File.separator + "repository"
					+ File.separator + groupId.replace(".", File.separator)
					+ File.separator + artifactId + File.separator
					+ artifactVersion + File.separator
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
						webPage = NEXUS_URL + "releases/"
								+ groupId.replace(".", "/") + "/" + artifactId
								+ "/" + artifactVersion + "/"
								+ metadata.getRemoteFilename();
					} else {
						webPage = NEXUS_URL + "snapshots/"
								+ groupId.replace(".", "/") + "/" + artifactId
								+ "/" + artifactVersion + "/"
								+ metadata.getRemoteFilename();
					}
				} else {
					if (isArtifactRelease) {
						webPage = NEXUS_URL + "releases/"
								+ groupId.replace(".", "/") + "/" + artifactId
								+ "/" + metadata.getRemoteFilename();
					} else {
						webPage = NEXUS_URL + "snapshots/"
								+ groupId.replace(".", "/") + "/" + artifactId
								+ "/" + metadata.getRemoteFilename();
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

				OutputStreamWriter out = new OutputStreamWriter(
						urlConnection.getOutputStream());
				File file = null;
				if (metadata.getRemoteFilename().endsWith(".pom")) {
					file = new File(repositoryPath + File.separator
							+ "repository" + File.separator
							+ groupId.replace(".", File.separator)
							+ File.separator + artifactId + File.separator
							+ artifactVersion + File.separator
							+ metadata.getRemoteFilename());
				} else {
					file = new File(repositoryPath + File.separator
							+ "repository" + File.separator
							+ groupId.replace(".", File.separator)
							+ File.separator + artifactId + File.separator
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
