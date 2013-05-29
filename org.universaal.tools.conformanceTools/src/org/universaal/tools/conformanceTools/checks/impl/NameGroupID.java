package org.universaal.tools.conformanceTools.checks.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.universaal.tools.conformanceTools.checks.api.CheckImpl;
import org.universaal.tools.conformanceTools.checks.api.SubInterfaces;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NameGroupID extends CheckImpl {

	private final String AALapp = "org.universAAL.AALaaplication";
	private final String AAL = "org.universAAL";
	private final String noAAL = "";

	@Override
	public String getCheckName() {
		return "Is the naming convention respected?";
	}

	@Override
	public String getCheckDescription() {
		return "This test will verify if name and group ID for the selected project complies to uAAL guidelines.";
	}

	@Override
	public String check(IResource resource) throws Exception {

		if(SubInterfaces.isProject(resource)){
			IProject p = (IProject) resource;

			if(p.getFile(IMavenConstants.POM_FILE_NAME) != null){

				File pom = getPOMfile(resource);
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document results = docBuilder.parse(pom);

				// groupId artifactId
				NodeList groupId = results.getElementsByTagName("groupId");		
				NodeList artifactId = results.getElementsByTagName("artifactId");		

				String groupID = null, artifactID = null;
				if(groupId != null && groupId.item(0) != null){
					if(groupId.item(0).getNodeType() == Node.ELEMENT_NODE){
						Element groupId_ = (Element) groupId.item(0);
						groupID = checkNamingConvention(groupId_.getTextContent());
					}					 	
				}

				if(artifactId != null && artifactId.item(0) != null){
					if(artifactId.item(0).getNodeType() == Node.ELEMENT_NODE){
						Element artifactId_ = (Element) artifactId.item(0);
						artifactID = checkNamingConvention(artifactId_.getTextContent());
					}					 	
				}

				if(groupID != null && artifactID != null){
					if(groupID.equals(AALapp) && artifactID.equals(AALapp))
						result = "Selected project is a uAAL application from WP4?";
					if(groupID.equals(AAL) && artifactID.equals(AAL))
						result = "Selected project is a uAAL code package?";
					if(groupID.equals(noAAL) && artifactID.equals(noAAL))
						result = "Are you a developer outside uAAL?";

					return unknown;
				}
				else{
					result = "Selected project has not valid descriptors.";
					return ko;
				}
			}
		}

		result = "Selected project has not a valid POM file.";
		return ko;
	}

	private String checkNamingConvention(String tag){
		if(tag != null && !tag.isEmpty()){
			if(tag.startsWith("org.universAAL.AALaaplication"))
				return AALapp;

			if(tag.startsWith("org.universAAL"))
				return AAL;
		}

		return noAAL;
	}

	private File getPOMfile(IResource resource) throws Exception{

		return new File(((IProject)resource).getFile(IMavenConstants.POM_FILE_NAME).getLocationURI());
	}
}
