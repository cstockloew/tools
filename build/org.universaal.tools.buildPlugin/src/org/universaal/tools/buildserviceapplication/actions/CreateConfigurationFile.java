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
			out.write("<stringAttribute key=\"configLocation\" value=\"${workspace_loc}/rundir/ts.persona\"/>\n");
			out.write("<booleanAttribute key=\"default_auto_start\" value=\"true\"/>\n");
			out.write("<intAttribute key=\"default_start_level\" value=\"8\"/>\n");
			out.write("<booleanAttribute key=\"includeOptional\" value=\"true\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.debug.core.source_locator_id\" value=\"org.eclipse.pde.ui.launcher.PDESourceLookupDirector\"/>\n");
			//out.write("<stringAttribute key=\"org.eclipse.debug.core.source_locator_memento\" value=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot; standalone=&quot;no&quot;?&gt;&#13;&#10;&lt;sourceLookupDirector&gt;&#13;&#10;&lt;sourceContainers duplicates=&quot;false&quot;&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;acl.bridge&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;conversion.jena&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;jena.osgi&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;ui.dm&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;casf.che&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;acl.upnp&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;demo.devices.ont&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;demo.stereosetserver&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;demo.tvserver&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;homelab.devices.ont&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;homelab.stereoset.server&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;homelab.tv.server&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;knx.server.blind&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;knx.xface&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;knx.ip&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;knx.server.light&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;knx.server.socket&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;knx.client.lighting&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;knx.server.window&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;ts.conventional&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;acl.interfaces&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;logger.console&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;sofa.client&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;casf.ont&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;middleware.upper&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;rdf.serializer.turtle&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;sodapop.osgi&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;hifi.server&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;home.app&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;home.ont&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;lighting.server&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;ts.base&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;javaProject name=&amp;quot;tv.server&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.jdt.launching.sourceContainer.javaProject&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;default/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.default&quot;/&gt;&#13;&#10;&lt;/sourceContainers&gt;&#13;&#10;&lt;/sourceLookupDirector&gt;&#13;&#10;\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.JRE_CONTAINER\" value=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/CDC-1.0%Foundation-1.0\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.PROGRAM_ARGUMENTS\" value=\"-console\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER\" value=\"org.eclipse.pde.ui.workbenchClasspathProvider\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.VM_ARGUMENTS\" value=\"-Dosgi.noShutdown=true -Dfelix.log.level=4 -Dorg.persona.middleware.peer.is_coordinator=true -Dorg.persona.middleware.peer.member_of=urn:org.persona.aal_space:tes_env -Dbundles.configuration.location=${workspace_loc}/rundir/confadmin\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.WORKING_DIRECTORY\" value=\"${workspace_loc}/rundir\"/>\n");
			out.write("<stringAttribute key=\"org.ops4j.pax.cursor.logLevel\" value=\"DEBUG\"/>\n");
			out.write("<booleanAttribute key=\"org.ops4j.pax.cursor.overwrite\" value=\"false\"/>\n");
			out.write("<booleanAttribute key=\"org.ops4j.pax.cursor.overwriteSystemBundles\" value=\"false\"/>\n");
			out.write("<booleanAttribute key=\"org.ops4j.pax.cursor.overwriteUserBundles\" value=\"false\"/>\n");
			out.write("<listAttribute key=\"org.ops4j.pax.cursor.profiles\">\n");
			out.write("<listEntry value=\"obr\"/>\n");
			out.write("</listAttribute>\n");
			out.write("<mapAttribute key=\"org.ops4j.pax.cursor.provisionItems\">\n");
			out.write("<mapEntry key=\"mvn:org.ops4j.pax.logging/pax-logging-api/1.4\" value=\"true@true@2@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.apache.felix/org.apache.felix.configadmin\" value=\"true@true@2@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.aal-persona.middleware/sodapop.osgi\" value=\"true@true@3@true\"/>\n");
			out.write("<mapEntry key=\"mvn:bundleHome/bundleHeating\" value=\"true@true@6@false\"/>\n");
			out.write("<mapEntry key=\"mvn:org.aal-persona.platform/casf.ont\" value=\"true@true@5@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.ops4j.pax.confman/pax-confman-propsloader\" value=\"true@true@3@true\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:jp.go.ipa/jgcl/1.0\" value=\"true@true@2@true\"/>\n");
			out.write("<mapEntry key=\"http://apache.prosite.de/felix/org.apache.felix.upnp.basedriver-0.8.0.jar\" value=\"true@true@2@true\"/>\n");
			out.write("<mapEntry key=\"http://gforge.aal-persona.org/nexus/content/repositories/snapshots/org/aal-persona/middleware/rdf.serializer.turtle/0.3.0-SNAPSHOT/rdf.serializer.turtle-0.3.0-20091218.173719-9.jar\" value=\"true@true@4@true\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:org.osgi/osgi_R4_compendium/1.0\" value=\"true@true@2@true\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:java3d/vecmath/1.3.1\" value=\"true@true@2@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.apache.felix/org.apache.felix.log/0.9.0-SNAPSHOT\" value=\"true@true@2@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.aal-persona.middleware/acl.upnp\" value=\"true@true@3@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.ops4j.pax.logging/pax-logging-service/1.4\" value=\"true@true@3@true\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:org.bouncycastle/jce.jdk13/144\" value=\"true@true@2@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.aal-persona.middleware/acl.interfaces\" value=\"true@true@2@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.aal-persona.middleware/middleware.upper/0.3.0-SNAPSHOT\" value=\"true@true@4@true\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:java3d/j3d-core/1.3.1\" value=\"true@true@2@true\"/>\n");
			out.write("</mapAttribute>\n");
			out.write("<listAttribute key=\"org.ops4j.pax.cursor.runArguments\">\n");
			out.write("<listEntry value=\"--overwrite=false\"/>\n");
			out.write("<listEntry value=\"--overwriteUserBundles=false\"/>\n");
			out.write("<listEntry value=\"--overwriteSystemBundles=false\"/>\n");
			out.write("<listEntry value=\"--log=DEBUG\"/>\n");
			out.write("<listEntry value=\"--profiles=obr\"/>\n");
			out.write("<listEntry value=\"mvn:org.ops4j.pax.logging/pax-logging-api/1.4@2@update\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:jp.go.ipa/jgcl/1.0@2@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.ops4j.pax.confman/pax-confman-propsloader@3@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.aal-persona.platform/casf.ont@5@update\"/>\n");
			out.write("<listEntry value=\"http://gforge.aal-persona.org/nexus/content/repositories/snapshots/org/aal-persona/middleware/rdf.serializer.turtle/0.3.0-SNAPSHOT/rdf.serializer.turtle-0.3.0-20091218.173719-9.jar@4@update\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:java3d/vecmath/1.3.1@2@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.aal-persona.middleware/acl.upnp@3@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.ops4j.pax.logging/pax-logging-service/1.4@3@update\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:org.bouncycastle/jce.jdk13/144@2@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.aal-persona.middleware/acl.interfaces@2@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.aal-persona.middleware/middleware.upper/0.3.0-SNAPSHOT@4@update\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:java3d/j3d-core/1.3.1@2@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.aal-persona.middleware/sodapop.osgi@3@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.apache.felix/org.apache.felix.configadmin@2@update\"/>\n");
			out.write("<listEntry value=\"http://apache.prosite.de/felix/org.apache.felix.upnp.basedriver-0.8.0.jar@2@update\"/>\n");
			out.write("<listEntry value=\"wrap:mvn:org.osgi/osgi_R4_compendium/1.0@2@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.apache.felix/org.apache.felix.log/0.9.0-SNAPSHOT@2@update\"/>\n");
			out.write("<listEntry value=\"mvn:bundleHome/bundleHeating@6\"/>\n");
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
