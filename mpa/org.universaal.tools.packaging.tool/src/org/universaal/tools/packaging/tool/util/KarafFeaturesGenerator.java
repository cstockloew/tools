package org.universaal.tools.packaging.tool.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
import org.universaal.tools.packaging.api.Page;
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

	private final String GROUP_ID = "org.apache.karaf.tooling";
	private final String ARTIFACT_ID = "features-maven-plugin";
	private final String VERSION = "2.2.7";

	private final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	//private final String ENCODING = "UTF-8";

	public String generate(IProject part){		

		verifyPreConditions(part.getName());
		if(generateKarafFeatures(part.getName()))		
			return returnKrfFeat(part);
		else
			return "";
	}

	private boolean verifyPreConditions(String projectName){

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
						if(plugins_children.item(j).getTextContent().equalsIgnoreCase(ARTIFACT_ID))
							return true;
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

			NodeList pps, builds;
			if(parsedPom.getElementsByTagName("plugins") != null){
				pps = parsedPom.getElementsByTagName("plugins");
				if(pps.getLength() > 0)
					pps.item(0).appendChild(maven_features_plugin);
			}
			else if(parsedPom.getElementsByTagName("build") != null){
				builds = parsedPom.getElementsByTagName("build");
				if(builds.getLength() > 0)
					builds.item(0).appendChild(plugins);
			}
			else
				parsedPom.getDocumentElement().appendChild(build);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(parsedPom);
			StreamResult result = new StreamResult(pom);

			transformer.transform(source, result);

			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return false;
	}

	private boolean generateKarafFeatures(String projectName){

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
					return true;
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return false;
	}

	private String returnKrfFeat(IProject part){

		String xml = "";
		try {

			String path = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+part.getDescription().getName();
			File features = new File(path+"/target/classes/feature.xml");

			BufferedReader reader = new BufferedReader(new FileReader(features));
			String line = "", oldtext = "";
			while((line = reader.readLine()) != null)
				oldtext += line + "\r\n";

			reader.close(); 

			xml = oldtext.substring(XML_HEADER.length(), oldtext.length()); // remove XML header
			xml = xml.replaceAll("<", "<"+Page.KARAF_NAMESPACE+":"); // add KARAF namespace
			xml = xml.replaceAll("<"+Page.KARAF_NAMESPACE+":/", "</"+Page.KARAF_NAMESPACE+":"); // correct end tags

		} catch (Exception e) {
			e.printStackTrace();
		}

		return xml;
	}
}