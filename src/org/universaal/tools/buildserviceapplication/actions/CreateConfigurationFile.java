package org.universaal.tools.buildserviceapplication.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.eclipse.core.runtime.Platform;

public class CreateConfigurationFile {

	static public String groupId = "";
	static public String artifactId = "";
	static public String artifactVersion = "";

	public CreateConfigurationFile() {
		getBundleProperties();
	}

	private void getBundleProperties() {
		try {			
			Reader reader = new FileReader(BuildAction.getSelectedProjectPath()
					+ "/pom.xml");			
				MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
				Model model = xpp3Reader.read(reader);
				groupId=model.getGroupId();
				artifactId=model.getArtifactId();
				artifactVersion=model.getVersion();
				
				//if groupId is null then search within its parent
				if(groupId==null&&model.getParent()!=null){
					groupId=model.getParent().getGroupId();
				}
				//if artifactId is null then search within its parent
				if(artifactId==null&&model.getParent()!=null){
					artifactId=model.getParent().getArtifactId();
				}
				reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public boolean createFile() {
		List<Dependency> ontologyDependencies = new ArrayList<Dependency>();
		try {
			// Path to your local Maven repository
			Reader reader = new FileReader(BuildAction.getSelectedProjectPath()
					+ "/pom.xml");
			try {
				MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
				Model model = xpp3Reader.read(reader);
				List<Dependency> dependencies = model.getDependencies();
				System.out.println("Project dependencies:\n");
				for (int i = 0; i < dependencies.size(); i++) {
					Dependency dependency = dependencies.get(i);
					if (dependency.getGroupId().equals(
							"org.universAAL.ontology")) {
						ontologyDependencies.add(dependency);
					}
				}
				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				reader.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String pathToAddConf = Platform.getLocation()
				+ "\\.metadata\\.plugins\\org.eclipse.debug.core\\.launches\\";
		File dir = new File(pathToAddConf);
		dir.mkdirs();
		File launchFile = new File(pathToAddConf + artifactId + ".launch");
		if (launchFile.exists()) {
			launchFile.delete();
		}
		try {
			FileWriter fstream = new FileWriter(pathToAddConf + artifactId
					+ ".launch");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
			out.write("<launchConfiguration type=\"org.eclipse.pde.ui.EquinoxLauncher\">\n");
			out.write("<booleanAttribute key=\"append.args\" value=\"true\"/>\n");
			out.write("<booleanAttribute key=\"automaticAdd\" value=\"true\"/>\n");
			out.write("<booleanAttribute key=\"automaticValidate\" value=\"false\"/>\n");
			out.write("<stringAttribute key=\"bootstrap\" value=\"\"/>\n");
			out.write("<stringAttribute key=\"checked\" value=\"[NONE]\"/>\n");
			out.write("<booleanAttribute key=\"clearConfig\" value=\"false\"/>\n");
			out.write("<stringAttribute key=\"configLocation\" value=\"${workspace_loc}/rundir/"
							+ artifactId + "\"/>\n");
			out.write("<booleanAttribute key=\"default_auto_start\" value=\"true\"/>\n");
			out.write("<intAttribute key=\"default_start_level\" value=\"8\"/>\n");
			out.write("<booleanAttribute key=\"includeOptional\" value=\"true\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.debug.core.source_locator_id\" value=\"org.eclipse.pde.ui.launcher.PDESourceLookupDirector\"/>\n");

			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.JRE_CONTAINER\" value=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/J2SE-1.5\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.PROGRAM_ARGUMENTS\" value=\"-console --obrRepositories=http://a1gforge.igd.fraunhofer.de/nexus/content/repositories/snapshots/repository.xml,http://a1gforge.igd.fraunhofer.de/nexus/content/repositories/releases/repository.xml,http://bundles.osgi.org/obr/browse?_xml=1&amp;amp;cmd=repository --org.ops4j.pax.url.mvn.repositories=+http://a1gforge.igd.fraunhofer.de/nexus/content/groups/public,http://a1gforge.igd.fraunhofer.de/nexus/content/repositories/snapshots@snapshots@noreleases --log=DEBUG\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER\" value=\"org.eclipse.pde.ui.workbenchClasspathProvider\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.VM_ARGUMENTS\" value=\"-Dosgi.noShutdown=true -Dfelix.log.level=4 -Dorg.universAAL.middleware.peer.is_coordinator=true -Dorg.universAAL.middleware.peer.member_of=urn:org.universAAL.aal_space:test_env -Dbundles.configuration.location=${workspace_loc}/rundir/confadmin\"/>\n");
			out.write("<stringAttribute key=\"org.eclipse.jdt.launching.WORKING_DIRECTORY\" value=\"${workspace_loc}/rundir/"
							+ artifactId + "\"/>\n");
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
			for (int i = 0; i < ontologyDependencies.size(); i++) {
				out.write("<mapEntry key=\"mvn:"
						+ ontologyDependencies.get(i).getGroupId() + "/"
						+ ontologyDependencies.get(i).getArtifactId()
						+ "\" value=\"true@true@5@false\"/>\n");
			}
			
			out.write("<mapEntry key=\"mvn:org.universAAL.middleware/mw.acl.interfaces\" value=\"true@true@2@false\"/>\n");
			out.write("<mapEntry key=\"wrap:mvn:jp.go.ipa/jgcl/1.0\" value=\"true@true@2@false\"/>\n");
			out.write("<mapEntry key=\"mvn:" + groupId + "/" + artifactId
					+ "\" value=\"true@false@6@true\"/>\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.middleware/mw.bus.model\" value=\"true@true@3@false\"/>\n");
			out.write("<mapEntry key=\"mvn:org.universAAL.middleware/mw.bus.context\" value=\"true@true@4@false\"/>\n");
			
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
			for (int i = 0; i < ontologyDependencies.size(); i++) {
				out.write("<listEntry value=\"mvn:"
						+ ontologyDependencies.get(i).getGroupId() + "/"
						+ ontologyDependencies.get(i).getArtifactId()
						+ "@5\"/>\n");
			}
			
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
			out.write("<listEntry value=\"mvn:" + groupId + "/" + artifactId
					+ "@6@nostart@update\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.middleware/mw.bus.model@3\"/>\n");
			out.write("<listEntry value=\"mvn:org.universAAL.middleware/mw.data.representation@4\"/>\n");
			
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
