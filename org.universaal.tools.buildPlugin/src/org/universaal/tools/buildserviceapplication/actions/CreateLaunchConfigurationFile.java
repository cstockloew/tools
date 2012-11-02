package org.universaal.tools.buildserviceapplication.actions;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.cli.MavenCli;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;

public class CreateLaunchConfigurationFile {
	private List<Dependency> pomDependencies = new ArrayList<Dependency>();
	
	
	
	private String getArtifactId() {
		try {
			Reader reader = new FileReader(BuildAction.getSelectedProjectPath()
					+ File.separator + "pom.xml");
			MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
			Model model = xpp3Reader.read(reader);
			reader.close();
			return model.getArtifactId();
		} catch (Exception e) {
			e.printStackTrace();
			return "New_configuration";
		}
	}

	public MavenProject getSelectedMavenProject() {
		for (int i = 0; i < BuildAction.buildedProjects.size(); i++) {
			MavenProject project = BuildAction.buildedProjects.get(i);
			if (project.getArtifactId().equals(
					BuildAction.getSelectedProjectName())
					&& project.getBasedir().toString()
							.equals(BuildAction.getSelectedProjectPath())) {
				return project;
			}
		}
		return null;
	}

	private void getParentPomDependencies(List<Dependency> dep, String pomFile) {
		try {
			Reader reader = new FileReader(pomFile);
			MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
			Model model = xpp3Reader.read(reader);
			List<Dependency> dependencies = model.getDependencies();

			for (int i = 0; i < dependencies.size(); i++) {
				dep.add(dependencies.get(i));
			}
			Parent pr = model.getParent();
			if (pr != null) {

				String pathToParent = MavenCli.userMavenConfigurationHome
						+ File.separator + "repository" + File.separator
						+ pr.getGroupId().replace(".", File.separator)
						+ File.separator + pr.getArtifactId() + File.separator
						+ pr.getVersion() + File.separator + pr.getArtifactId()
						+ "-" + pr.getVersion() + ".pom";
				getParentPomDependencies(dep, pathToParent);
			}
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public List<Dependency> getProjectDependencies() {
		pomDependencies = new ArrayList<Dependency>();
		try {

			Reader reader = new FileReader(BuildAction.getSelectedProjectPath()
					+ File.separator + "pom.xml");
			try {
				Parent pr = null;
				MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
				Model model = xpp3Reader.read(reader);
				List<Dependency> dependencies = model.getDependencies();

				for (int i = 0; i < dependencies.size(); i++) {
					pomDependencies.add(dependencies.get(i));
				}

				pr = model.getParent();
				if (pr != null) {

					String pathToParent = MavenCli.userMavenConfigurationHome
							+ File.separator + "repository" + File.separator
							+ pr.getGroupId().replace(".", File.separator)
							+ File.separator + pr.getArtifactId()
							+ File.separator + pr.getVersion() + File.separator
							+ pr.getArtifactId() + "-" + pr.getVersion()
							+ ".pom";
					getParentPomDependencies(pomDependencies, pathToParent);
				}

				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				reader.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pomDependencies;

	}

	private Dependency getDependency(String artifactId) {
		for (int i = 0; i < pomDependencies.size(); i++) {
			if (pomDependencies.get(i).getArtifactId().equals(artifactId)) {
				return pomDependencies.get(i);
			}
		}
		return null;
	}

	private List<Dependency> getGroupDependencies(String groupId) {
		List<Dependency> list = new ArrayList<Dependency>();
		for (int i = 0; i < pomDependencies.size(); i++) {
			if (pomDependencies.get(i).getGroupId().equals(groupId)) {
				list.add(pomDependencies.get(i));
			}
		}
		return list;
	}

	public ILaunchConfiguration createLaunchConfiguration() {
		try {
			ILaunchManager manager = DebugPlugin.getDefault()
					.getLaunchManager();
			ILaunchConfigurationType type = manager
					.getLaunchConfigurationType("org.eclipse.pde.ui.EquinoxLauncher");
			ILaunchConfigurationWorkingCopy wc = type.newInstance(null,
					getArtifactId());

			wc.setAttribute("append.args", true);
			wc.setAttribute("automaticAdd", true);
			wc.setAttribute("automaticValidate", false);
			wc.setAttribute("bootstrap", "");
			wc.setAttribute("checked", "[NONE]");
			wc.setAttribute("clearConfig", false);
			wc.setAttribute("configLocation", "${workspace_loc}/rundir/"
					+ getArtifactId());
			wc.setAttribute("default", true);
			wc.setAttribute("default_auto_start", true);
			wc.setAttribute("default_start_level", 8);
			wc.setAttribute("includeOptional", true);
			wc.setAttribute("org.eclipse.debug.core.source_locator_id",
					"org.eclipse.pde.ui.launcher.PDESourceLookupDirector");
			// wc.setAttribute(
			// "org.eclipse.debug.core.source_locator_memento",
			// "&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot; standalone=&quot;no&quot;?&gt;&#13;&#10;&lt;sourceLookupDirector&gt;&#13;&#10;&lt;sourceContainers duplicates=&quot;false&quot;&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;folder nest=&amp;quot;false&amp;quot; path=&amp;quot;/mw.bus.context/src/main/java&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.folder&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;folder nest=&amp;quot;false&amp;quot; path=&amp;quot;/mw.bus.model/src/main/java&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.folder&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;folder nest=&amp;quot;false&amp;quot; path=&amp;quot;/mw.bus.service/src/main/java&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.folder&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;folder nest=&amp;quot;false&amp;quot; path=&amp;quot;/mw.data.representation/src/main/java&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.folder&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;folder nest=&amp;quot;false&amp;quot; path=&amp;quot;/mw.data.serialization/src/main/java&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.folder&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;folder nest=&amp;quot;false&amp;quot; path=&amp;quot;/ont.lighting/src/main/java&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.folder&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;folder nest=&amp;quot;false&amp;quot; path=&amp;quot;/ont.phWorld/src/main/java&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.folder&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;folder nest=&amp;quot;false&amp;quot; path=&amp;quot;/smp.lighting.client/src/main/java&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.folder&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;folder nest=&amp;quot;false&amp;quot; path=&amp;quot;/smp.lighting.server/src/main/java&amp;quot;/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.folder&quot;/&gt;&#13;&#10;&lt;container memento=&quot;&amp;lt;?xml version=&amp;quot;1.0&amp;quot; encoding=&amp;quot;UTF-8&amp;quot; standalone=&amp;quot;no&amp;quot;?&amp;gt;&amp;#13;&amp;#10;&amp;lt;default/&amp;gt;&amp;#13;&amp;#10;&quot; typeId=&quot;org.eclipse.debug.core.containerType.default&quot;/&gt;&#13;&#10;&lt;/sourceContainers&gt;&#13;&#10;&lt;/sourceLookupDirector&gt;&#13;&#10;");
			wc.setAttribute("includeOptional", true);
			wc.setAttribute(
					"org.eclipse.jdt.launching.JRE_CONTAINER",
					"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/J2SE-1.5");
			wc.setAttribute(
					"org.eclipse.jdt.launching.PROGRAM_ARGUMENTS",
					"-console --obrRepositories=http://depot.universaal.org/nexus/content/repositories/snapshots/repository.xml,http://depot.universaal.org/nexus/content/repositories/releases/repository.xml,http://bundles.osgi.org/obr/browse?_xml=1&amp;amp;cmd=repository --org.ops4j.pax.url.mvn.repositories=+http://depot.universaal.org/nexus/content/groups/public,http://depot.universaal.org/nexus/content/repositories/snapshots@snapshots@noreleases --log=DEBUG");
			wc.setAttribute("org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER",
					"org.eclipse.pde.ui.workbenchClasspathProvider");
			wc.setAttribute(
					"org.eclipse.jdt.launching.VM_ARGUMENTS",
					"-Dosgi.noShutdown=true -Dfelix.log.level=4 -Dorg.universAAL.middleware.peer.is_coordinator=true -Dorg.universAAL.middleware.peer.member_of=urn:org.universAAL.aal_space:test_env -Dbundles.configuration.location=${workspace_loc}/rundir/confadmin");
			wc.setAttribute("includeOptional", true);
			wc.setAttribute("org.eclipse.jdt.launching.WORKING_DIRECTORY",
					"${workspace_loc}/rundir/"
							+ getSelectedMavenProject().getArtifactId());
			wc.setAttribute("org.ops4j.pax.cursor.hotDeployment", false);
			wc.setAttribute("org.ops4j.pax.cursor.logLevel", "DEBUG");
			wc.setAttribute("org.ops4j.pax.cursor.overwrite", false);
			wc.setAttribute("org.ops4j.pax.cursor.overwriteSystemBundles",
					false);
			wc.setAttribute("org.ops4j.pax.cursor.overwriteUserBundles", false);
			ArrayList classpath = new ArrayList();
			classpath.add("obr");
			wc.setAttribute("org.ops4j.pax.cursor.profiles", classpath);

			List<Dependency> deps = getProjectDependencies();
			
			Map map = new HashMap();
			map.put("mvn:org.apache.felix/org.apache.felix.configadmin/1.2.4",
					"true@true@2@false");
			Dependency dep = getDependency("mw.acl.interfaces");
			if (dep == null) {
				map.put("mvn:org.universAAL.middleware/mw.acl.interfaces",
						"true@true@2@false");
			} else {
				map.put("mvn:org.universAAL.middleware/mw.acl.interfaces/"
						+ dep.getVersion(), "true@true@2@false");
				deps.remove(getDependency("mw.acl.interfaces"));
			}
			dep = getDependency("mw.bus.context");
			if (dep == null) {
				map.put("mvn:org.universAAL.middleware/mw.bus.context",
						"true@true@4@true");
			} else {
				map.put("mvn:org.universAAL.middleware/mw.bus.context/"
						+ dep.getVersion(), "true@true@4@true");
				deps.remove(getDependency("mw.bus.context"));
			}
			dep = getDependency("mw.bus.model");
			if (dep == null) {
				map.put("mvn:org.universAAL.middleware/mw.bus.model",
						"true@true@3@true");
			} else {
				map.put("mvn:org.universAAL.middleware/mw.bus.model/"
						+ dep.getVersion(), "true@true@3@true");
				deps.remove(getDependency("mw.bus.model"));
			}
			dep = getDependency("mw.bus.service");
			if (dep == null) {
				map.put("mvn:org.universAAL.middleware/mw.bus.service",
						"true@true@4@true");
			} else {
				map.put("mvn:org.universAAL.middleware/mw.bus.service/"
						+ dep.getVersion(), "true@true@4@true");
				deps.remove(getDependency("mw.bus.service"));
			}
			dep = getDependency("mw.data.representation");
			if (dep == null) {
				map.put("mvn:org.universAAL.middleware/mw.data.representation",
						"true@true@4@true");
			} else {
				map.put("mvn:org.universAAL.middleware/mw.data.representation/"
						+ dep.getVersion(), "true@true@4@true");
				deps.remove(getDependency("mw.data.representation"));
			}
			dep = getDependency("mw.data.serialization");
			if (dep == null) {
				map.put("mvn:org.universAAL.middleware/mw.data.serialization",
						"true@true@4@true");
			} else {
				map.put("mvn:org.universAAL.middleware/mw.data.serialization/"
						+ dep.getVersion(), "true@true@4@true");
				deps.remove(getDependency("mw.data.serialization"));
			}

			
		

			List<Dependency> ontDep = getGroupDependencies("org.universAAL.ontology");
			for (int i = 0; i < ontDep.size(); i++) {
				map.put("mvn:org.universAAL.ontology/"
						+ ontDep.get(i).getArtifactId() + "/"
						+ ontDep.get(i).getVersion(), "true@true@5@false");
				deps.remove(getDependency(ontDep.get(i).getArtifactId()));
			}
			
			for (int i = 0; i < deps.size(); i++) {
				if(deps.get(i).getGroupId().startsWith("org.universAAL.")){
					map.put("mvn:"+deps.get(i).getGroupId()+"/"
							+ deps.get(i).getArtifactId() + "/"
							+ deps.get(i).getVersion(), "true@true@4@true");
				}				
			}
			
			// map.put("mvn:org.universAAL.ontology/ont.lighting",
			// "true@true@5@false");
			// map.put("mvn:org.universAAL.ontology/ont.phWorld",
			// "true@true@5@false");
			MavenProject mavenProject = getSelectedMavenProject();
			map.put("mvn:" + mavenProject.getGroupId() + "/"
					+ mavenProject.getArtifactId() + "/"
					+ mavenProject.getVersion(), "true@false@7@true");

			map.put("wrap:mvn:java3d/j3d-core/1.3.1", "true@true@2@false");
			map.put("wrap:mvn:java3d/vecmath/1.3.1", "true@true@2@false");
			map.put("wrap:mvn:jp.go.ipa/jgcl/1.0", "true@true@2@false");
			map.put("wrap:mvn:org.bouncycastle/jce.jdk13/144",
					"true@true@2@false");
			map.put("wrap:mvn:org.ops4j.pax.confman/pax-confman-propsloader/0.2.2",
					"true@true@3@false");
			map.put("wrap:mvn:org.ops4j.pax.logging/pax-logging-api/1.6.0",
					"true@true@2@false");
			map.put("wrap:mvn:org.ops4j.pax.logging/pax-logging-service/1.6.0",
					"true@true@3@false");
			map.put("wrap:mvn:org.osgi/osgi_R4_compendium/1.0",
					"true@true@2@false");
			wc.setAttribute("org.ops4j.pax.cursor.provisionItems", map);

			
			deps = getProjectDependencies();
			
			classpath = new ArrayList();
			classpath.add("--overwrite=false");
			classpath.add("--overwriteUserBundles=false");
			classpath.add("--overwriteSystemBundles=false");
			classpath.add("--hotDeployment=false");
			classpath.add("--log=DEBUG");
			classpath.add("--profiles=obr");
			classpath
					.add("mvn:org.apache.felix/org.apache.felix.configadmin/1.2.4@2");
			dep = getDependency("mw.acl.interfaces");
			if (dep == null) {
				classpath
						.add("mvn:org.universAAL.middleware/mw.acl.interfaces@2");
			} else {
				classpath
						.add("mvn:org.universAAL.middleware/mw.acl.interfaces/"
								+ dep.getVersion() + "@2");
				deps.remove(getDependency("mw.acl.interfaces"));
			}
			classpath.add("wrap:mvn:jp.go.ipa/jgcl/1.0@2");
			classpath.add("wrap:mvn:java3d/vecmath/1.3.1@2");
			classpath.add("wrap:mvn:org.bouncycastle/jce.jdk13/144@2");
			classpath.add("wrap:mvn:java3d/j3d-core/1.3.1@2");
			classpath.add("wrap:mvn:org.osgi/osgi_R4_compendium/1.0@2");
			classpath
					.add("wrap:mvn:org.ops4j.pax.logging/pax-logging-api/1.4@2");
			classpath
					.add("wrap:mvn:org.ops4j.pax.confman/pax-confman-propsloader/0.2.2@3");
			classpath
					.add("wrap:mvn:org.ops4j.pax.logging/pax-logging-service/1.4@3");
			dep = getDependency("mw.bus.model");
			if (dep == null) {
				classpath
						.add("mvn:org.universAAL.middleware/mw.bus.model@3@update");
			} else {
				classpath.add("mvn:org.universAAL.middleware/mw.bus.model/"
						+ dep.getVersion() + "@3@update");
				deps.remove(getDependency("mw.bus.model"));
			}
			dep = getDependency("mw.bus.context");
			if (dep == null) {
				classpath
						.add("mvn:org.universAAL.middleware/mw.bus.context@4@update");
			} else {
				classpath.add("mvn:org.universAAL.middleware/mw.bus.context/"
						+ dep.getVersion() + "@4@update");
				deps.remove(getDependency("mw.bus.context"));
			}
			dep = getDependency("mw.bus.service");
			if (dep == null) {
				classpath
						.add("mvn:org.universAAL.middleware/mw.bus.service@4@update");
			} else {
				classpath.add("mvn:org.universAAL.middleware/mw.bus.service/"
						+ dep.getVersion() + "@4@update");
				deps.remove(getDependency("mw.bus.service"));
			}
			dep = getDependency("mw.data.serialization");
			if (dep == null) {
				classpath
						.add("mvn:org.universAAL.middleware/mw.data.serialization@4@update");
			} else {
				classpath
						.add("mvn:org.universAAL.middleware/mw.data.serialization/"
								+ dep.getVersion() + "@4@update");
				deps.remove(getDependency("mw.data.serialization"));
			}
			dep = getDependency("mw.data.representation");
			if (dep == null) {
				classpath
						.add("mvn:org.universAAL.middleware/mw.data.representation@4@update");
			} else {
				classpath
						.add("mvn:org.universAAL.middleware/mw.data.representation/"
								+ dep.getVersion() + "@4@update");
				deps.remove(getDependency("mw.data.representation"));
			}

			

			for (int i = 0; i < ontDep.size(); i++) {
				classpath.add("mvn:org.universAAL.ontology/"
						+ ontDep.get(i).getArtifactId() + "/"
						+ ontDep.get(i).getVersion() + "@5");
				deps.remove(getDependency(ontDep.get(i).getArtifactId()));
			}
			
			for (int i = 0; i < deps.size(); i++) {
				if(deps.get(i).getGroupId().startsWith("org.universAAL.")){
					classpath.add("mvn:"+deps.get(i).getGroupId()+"/"
							+ deps.get(i).getArtifactId() + "/"
							+ deps.get(i).getVersion()+ "@4@update");
				}				
			}
			
			classpath.add("mvn:" + mavenProject.getGroupId() + "/"
					+ mavenProject.getArtifactId() + "/"
					+ mavenProject.getVersion() + "@6@nostart@update");

			wc.setAttribute("org.ops4j.pax.cursor.runArguments", classpath);

			wc.setAttribute("osgi_framework_id",
					"--platform=felix --version=1.4.0");
			wc.setAttribute("pde.version", "3.3");
			wc.setAttribute("show_selected_only", false);
			wc.setAttribute("tracing", false);
			wc.setAttribute("useCustomFeatures", false);
			wc.setAttribute("useDefaultConfigArea", false);

			ILaunchConfiguration config = wc.doSave();
			return config;
		} catch (Exception ex) {
			ex.printStackTrace();

			return null;
		}
	}
	
	
	
}
