package org.universaal.tools.packaging.tool.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class POMParser {

	private File pom;

	private String groupID, artifactID, packaging, version, name, url, description;

	public POMParser(File pom){

		this.pom = pom;
		analyzePom();
	}

	private void analyzePom(){

		try{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document parsedPom = docBuilder.parse(pom);
			parsedPom.getDocumentElement().normalize();

			NodeList groupIDs = parsedPom.getElementsByTagName("groupId");
			NodeList artifactIDs = parsedPom.getElementsByTagName("artifactId");
			NodeList packagings = parsedPom.getElementsByTagName("packaging");
			NodeList versions = parsedPom.getElementsByTagName("version");
			NodeList names = parsedPom.getElementsByTagName("name");
			NodeList urls = parsedPom.getElementsByTagName("url");
			NodeList descriptions = parsedPom.getElementsByTagName("description");

			groupID = "";
			if(groupIDs.getLength() == 1)
				groupID = groupIDs.item(0).getTextContent();
			if(groupIDs.getLength() == 2)
				if(!groupIDs.item(0).getParentNode().getTextContent().equalsIgnoreCase("parent"))
					groupID = groupIDs.item(0).getTextContent();
				else
					groupID = groupIDs.item(1).getTextContent();

			artifactID = "";
			if(artifactIDs.getLength() == 1)
				artifactID = artifactIDs.item(0).getTextContent();
			if(artifactIDs.getLength() == 2)
				if(!artifactIDs.item(0).getParentNode().getTextContent().equalsIgnoreCase("parent"))
					artifactID = artifactIDs.item(0).getTextContent();
				else
					artifactID = artifactIDs.item(1).getTextContent();

			packaging = "";
			if(packagings.getLength() == 1)
				packaging = packagings.item(0).getTextContent();
			if(packagings.getLength() == 2)
				if(!packagings.item(0).getParentNode().getTextContent().equalsIgnoreCase("parent"))
					packaging = packagings.item(0).getTextContent();
				else
					packaging = packagings.item(1).getTextContent();

			version = "";
			if(versions.getLength() == 1)
				version = versions.item(0).getTextContent();
			if(versions.getLength() == 2)
				if(!versions.item(0).getParentNode().getTextContent().equalsIgnoreCase("parent"))
					version = versions.item(0).getTextContent();
				else
					version = versions.item(1).getTextContent();

			name = "";
			if(names.getLength() == 1)
				name = names.item(0).getTextContent();
			if(names.getLength() == 2)
				if(!names.item(0).getParentNode().getTextContent().equalsIgnoreCase("parent"))
					name = names.item(0).getTextContent();
				else
					name = names.item(1).getTextContent();

			url = "";
			if(urls.getLength() == 1)
				url = urls.item(0).getTextContent();
			if(urls.getLength() == 2)
				if(!urls.item(0).getParentNode().getTextContent().equalsIgnoreCase("parent"))
					url = urls.item(0).getTextContent();
				else
					url = urls.item(1).getTextContent();

			description = "";
			if(descriptions.getLength() == 1)
				description = descriptions.item(0).getTextContent();
			if(descriptions.getLength() == 2)
				if(!descriptions.item(0).getParentNode().getTextContent().equalsIgnoreCase("parent"))
					description = descriptions.item(0).getTextContent();
				else
					description = descriptions.item(1).getTextContent();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public String getGroupID() {
		return groupID;
	}

	public String getArtifactID() {
		return artifactID;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}
}