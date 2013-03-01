package org.universaal.tools.packaging.tool.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.karaf.xmlns.features.v1_0.Feature;
import org.apache.karaf.xmlns.features.v1_0.FeaturesRoot;
import org.apache.karaf.xmlns.features.v1_0.ObjectFactory;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.universaal.tools.packaging.tool.gui.GUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class KarafFeaturesGenerator {

	// add to pom.xml of project to add to Karaf container the following declaration:
	/*
	 * <plugin>
        <groupId>org.apache.karaf.tooling</groupId>
        <artifactId>features-maven-plugin</artifactId>
        <version>2.2.7</version>

        <executions>
          <!-- add execution definitions here -->
        </executions>
      </plugin>
	 */

	// run mvn:features-generate-xml

	// parse feature.xml in {project}/target/classes/ to obtain FeaturesRoot

	private GUI g = GUI.getInstance();

	public final String GROUP_ID = "org.apache.karaf.tooling";
	public final String ARTIFACT_ID = "features-maven-plugin";
	public final String VERSION = "2.2.7";

	public void generate(String partName){		

		//		List<Part> parts = g.mpa.getApplication().getParts();
		//		for(int i = 0; i < parts.size(); i++){
		//			List<DeploymentUnit> dus = parts.get(i).getDeploymentUnits();
		//			for(int j = 0; j < dus.size(); j++){
		//				if(dus.get(j).getCu().getContainer() == Container.KARAF){
		//
		//					verifyPreConditions(parts.get(i).getName());
		//					generateKarafFeatures(parts.get(i).getName());
		//				}
		//			}
		//		}

		verifyPreConditions(partName);
		generateKarafFeatures(partName);
	}

	private void verifyPreConditions(String projectName){

		try{
			// it verifies the presence of "features-maven-plugin" in the pom.xml
			IProject project = g.getPart(projectName);
			File pom = new File(project.getFile("pom.xml").getLocation()+"");

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document parsedPom = docBuilder.parse(pom);
			parsedPom.getDocumentElement().normalize();

			Element build, plugins, maven_features_plugin, groupdID, artifactID, version;

			NodeList plug_ins = parsedPom.getElementsByTagName("plugin");
			for(int i = 0; i < plug_ins.getLength(); i++){
				NodeList plugins_children = plug_ins.item(i).getChildNodes();
				for(int j = 0; j < plugins_children.getLength(); j++){
					if(plugins_children.item(j).getNodeName().equals("artifactId"))
						if(plugins_children.item(j).getNodeValue().equalsIgnoreCase("features-maven-plugin"))
							return;
				}
			}
			// add features-maven-plugin declaration

			maven_features_plugin = parsedPom.createElement("plugin");
			groupdID = parsedPom.createElement("groupId");
			groupdID.setTextContent(GROUP_ID);
			artifactID = parsedPom.createElement("artifactId");
			artifactID.setTextContent(ARTIFACT_ID);
			version = parsedPom.createElement("version");
			version.setTextContent(VERSION);

			maven_features_plugin.appendChild(groupdID);
			maven_features_plugin.appendChild(artifactID);
			maven_features_plugin.appendChild(version);

			plugins = parsedPom.createElement("plugins");
			plugins.appendChild(maven_features_plugin);

			build = parsedPom.createElement("build");
			build.appendChild(plugins);

			if(parsedPom.getElementById("plugins") != null)
				parsedPom.getElementById("plugins").appendChild(maven_features_plugin);
			else if(parsedPom.getElementById("build") != null)
				parsedPom.getElementById("build").appendChild(plugins);
			else
				parsedPom.getDocumentElement().appendChild(build);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(parsedPom);
			StreamResult result = new StreamResult(pom);

			transformer.transform(source, result);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void generateKarafFeatures(String projectName){

		try{
			IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();
			IFile pomResource = g.getPart(projectName).getFile(IMavenConstants.POM_FILE_NAME);
			IMavenProjectFacade projectFacade = projectManager.create(pomResource, true, null);

			IMaven maven = MavenPlugin.getMaven();
			if(pomResource != null && projectFacade != null){
				MavenExecutionRequest request = projectManager.createExecutionRequest(pomResource, projectFacade.getResolverConfiguration(), null);

				List<String> goals = new ArrayList<String>();

				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceDescription description = workspace.getDescription();
				if (!description.isAutoBuilding())
					goals.add("compiler:compile"); // compile it if autobuilding is off

				goals.add("features:generate-features-xml");
				request.setGoals(goals);
				MavenExecutionResult execution_result = maven.execute(request, null);
				if(execution_result.getExceptions() == null || execution_result.getExceptions().isEmpty())
					return;
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
