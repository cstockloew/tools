package org.universaal.tools.buildserviceapplication.actions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class CreateConfigurationFile {

	private String groupId = "";
	static public String artifactId = "";

	public CreateConfigurationFile() {
		getBundleProperties();
	}

	private void getBundleProperties() {
		try {
			FileInputStream fstream = new FileInputStream(
					getSelectedProjectPath() + "/pom.xml");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			boolean groupFound = false;
			boolean artifactFound = false;
			while ((strLine = br.readLine()) != null) {
				if (strLine.contains("<groupId>")
						&& strLine.contains("</groupId>")) {
					groupId = strLine.split(">")[1].split("<")[0];
					groupFound = true;
					if (artifactFound) {
						return;
					}
				}
				if (strLine.contains("<artifactId>")
						&& strLine.contains("</artifactId>")) {
					artifactId = strLine.split(">")[1].split("<")[0];
					artifactFound = true;
					if (groupFound) {
						return;
					}
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the root path of the selected project within eclipse workspace.
	 */
	private String getSelectedProjectPath() {

		try {
			String projectPath = "";
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			TreeSelection selection = (TreeSelection) window.getActivePage()
					.getSelection();
			if (selection.getPaths().length != 0) {
				TreePath path = selection.getPaths()[0];
				Object sel = path.getSegment(0);
				if (sel instanceof org.eclipse.core.internal.resources.Project) {
					org.eclipse.core.internal.resources.Project selectedProject = (org.eclipse.core.internal.resources.Project) sel;
					projectPath = selectedProject.getLocation().toString();
				}
				projectPath = projectPath.replace("file:/", "");
				// System.out.println("Path to project: "+projectPath);
				return projectPath;
			} else {
				return "";
			}
		} catch (Exception ex) {
			return "";
		}
	}

	public boolean createFile() {
		String pathToAddConf = Platform.getLocation()
				+ "\\.metadata\\.plugins\\org.eclipse.debug.core\\.launches\\";
		File dir=new File(pathToAddConf);
		dir.mkdirs();
		File launchFile=new File(pathToAddConf+artifactId+".launch");
		if(launchFile.exists()){
			launchFile.delete();
		}
		try {
			FileWriter fstream = new FileWriter(pathToAddConf
					+ artifactId+".launch");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
			out.write("<launchConfiguration type=\"org.eclipse.pde.ui.EquinoxLauncher\">\n");
			out.write("<booleanAttribute key=\"append.args\" value=\"true\"/>\n");
			out.write("<booleanAttribute key=\"automaticAdd\" value=\"true\"/>\n");
			out.write("<booleanAttribute key=\"automaticValidate\" value=\"false\"/>\n");
			out.write("<stringAttribute key=\"bootstrap\" value=\"\"/>\n");
			out.write("<stringAttribute key=\"checked\" value=\"[NONE]\"/>\n");
			out.write("<booleanAttribute key=\"clearConfig\" value=\"false\"/>\n");
			out.write("<stringAttribute key=\"configLocation\" value=\"${workspace_loc}/rundir/."+artifactId+"\"/>\n");
			out.write("<booleanAttribute key=\"default_auto_start\" value=\"true\"/>\n");
			out.write("<intAttribute key=\"default_start_level\" value=\"8\"/>\n");
			out.write("<booleanAttribute key=\"includeOptional\" value=\"true\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.debug.core.source_locator_id\" value=\"org.eclipse.pde.ui.launcher.PDESourceLookupDirector\"/>\n");

			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.JRE_CONTAINER\" value=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/J2SE-1.5\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.PROGRAM_ARGUMENTS\" value=\"-console --obrRepositories=http://a1gforge.igd.fraunhofer.de/nexus/content/repositories/snapshots/repository.xml,http://a1gforge.igd.fraunhofer.de/nexus/content/repositories/releases/repository.xml,http://bundles.osgi.org/obr/browse?_xml=1&amp;amp;cmd=repository --org.ops4j.pax.url.mvn.repositories=+http://a1gforge.igd.fraunhofer.de/nexus/content/groups/public,http://a1gforge.igd.fraunhofer.de/nexus/content/repositories/snapshots@snapshots@noreleases --log=DEBUG\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER\" value=\"org.eclipse.pde.ui.workbenchClasspathProvider\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.VM_ARGUMENTS\" value=\"-Dosgi.noShutdown=true -Dfelix.log.level=4 -Dorg.universAAL.middleware.peer.is_coordinator=true -Dorg.universAAL.middleware.peer.member_of=urn:org.universAAL.aal_space:test_env -Dbundles.configuration.location=${workspace_loc}/rundir/confadmin\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.WORKING_DIRECTORY\" value=\"${workspace_loc}/rundir/smp."+artifactId+"\"/>\n");
			out.write("<stringAttribute key=\"org.ops4j.pax.cursor.logLevel\" value=\"DEBUG\"/>\n");
			out.write("<booleanAttribute key=\"org.ops4j.pax.cursor.overwrite\" value=\"false\"/>\n");
			out.write("<booleanAttribute key=\"org.ops4j.pax.cursor.overwriteSystemBundles\" value=\"false\"/>\n");
			out.write("<booleanAttribute key=\"org.ops4j.pax.cursor.overwriteUserBundles\" value=\"false\"/>\n");
			out.write("<listAttribute key=\"org.ops4j.pax.cursor.profiles\">\n");
			out.write("<listEntry value=\"obr\"/>\n");
			out.write("</listAttribute>\n");
			out.write("<mapAttribute key=\"org.ops4j.pax.cursor.provisionItems\">\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.middleware/mw.data.serialization\" value=\"true@true@4@false\"/>\n");
			out.write("<mapEntry key=\"mvn:org.apache.felix/org.apache.felix.configadmin/1.2.4\" value=\"true@true@2@false\"/>\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.ontology/ont.phWorld\" value=\"true@true@5@false\"/>\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.middleware/mw.acl.interfaces\" value=\"true@true@2@false\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:jp.go.ipa/jgcl/1.0\" value=\"true@true@2@false\"/>\n");
			out.write("<mapEntry key=\"mvn:"+groupId+"/"+artifactId+"\" value=\"true@false@6@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.middleware/mw.bus.model\" value=\"true@true@3@false\"/>\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.middleware/mw.bus.context\" value=\"true@true@4@false\"/>\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.ontology/ont.weather\" value=\"true@true@5@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.middleware/mw.data.representation\" value=\"true@true@4@false\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:org.osgi/osgi_R4_compendium/1.0\" value=\"true@true@2@false\"/>\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.middleware/mw.bus.service\" value=\"true@true@4@false\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:java3d/vecmath/1.3.1\" value=\"true@true@2@false\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:org.ops4j.pax.confman/pax-confman-propsloader/0.2.2\" value=\"true@true@3@false\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:org.bouncycastle/jce.jdk13/144\" value=\"true@true@2@false\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:org.ops4j.pax.logging/pax-logging-api/1.4\" value=\"true@true@2@false\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:org.ops4j.pax.logging/pax-logging-service/1.4\" value=\"true@true@3@false\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:java3d/j3d-core/1.3.1\" value=\"true@true@2@false\"/>\n");
			out.write("</mapAttribute>\n");
			out.write("<listAttribute key=\"org.ops4j.pax.cursor.runArguments\">\n");
			out.write("<listEntry value=\"--overwrite=false\"/>\n");
			out.write("<listEntry value=\"--overwriteUserBundles=false\"/>\n");
			out.write("<listEntry value=\"--overwriteSystemBundles=false\"/>\n");
			out.write("<listEntry value=\"--log=DEBUG\"/>\n");
			out.write("<listEntry value=\"--profiles=obr\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.ontology/ont.phWorld@5\"/>\n");
			out.write("<listEntry value=\"mvn:org.apache.felix/org.apache.felix.configadmin/1.2.4@2\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.middleware/mw.acl.interfaces@2\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:jp.go.ipa/jgcl/1.0@2\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.middleware/mw.bus.context@4\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.middleware/mw.bus.service@4\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:java3d/vecmath/1.3.1@2\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:org.ops4j.pax.confman/pax-confman-propsloader/0.2.2@3\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:org.bouncycastle/jce.jdk13/144@2\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:org.ops4j.pax.logging/pax-logging-service/1.4@3\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:java3d/j3d-core/1.3.1@2\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.middleware/mw.data.serialization@4\"/>\n");
			out.write("<listEntry value=\"mvn:"+groupId+"/"+artifactId+"@6@nostart@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.middleware/mw.bus.model@3\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.middleware/mw.data.representation@4\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.ontology/ont.weather@5@update\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:org.osgi/osgi_R4_compendium/1.0@2\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:org.ops4j.pax.logging/pax-logging-api/1.4@2\"/>\n");
			out.write("</listAttribute>\n");
			out.write("<stringAttribute key=\"osgi_framework_id\" value=\"--platform=felix --version=1.4.0\"/>\n");
			out.write("<stringAttribute key=\"pde.version\" value=\"3.3\"/>\n");
			out.write("<booleanAttribute key=\"show_selected_only\" value=\"false\"/>\n");
			out.write("<booleanAttribute key=\"tracing\" value=\"false\"/>\n");
			out.write("<booleanAttribute key=\"useDefaultConfigArea\" value=\"false\"/>\n");
			out.write("</launchConfiguration>\n");



			// Close the output stream
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
