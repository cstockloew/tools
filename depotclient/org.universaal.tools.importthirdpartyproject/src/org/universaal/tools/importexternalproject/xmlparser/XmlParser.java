package org.universaal.tools.importexternalproject.xmlparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {

	ArrayList<File> resultList;
	DocumentBuilderFactory factory;
	DocumentBuilder dBuilder;

	public XmlParser(){
		this.factory = DocumentBuilderFactory.newInstance();
		try {
			this.dBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean searchTags(File input, 
			ArrayList<ProjectObject> result, String tag){

		boolean match=false;
		Document doc;
		try {
			doc = dBuilder.parse(input);
			NodeList projectList = doc.getElementsByTagName("project");

			for(int k=0;k<projectList.getLength(); k++){
				match=false;
				Element currentProject = (Element) projectList.item(k);
				NodeList nList = currentProject.getElementsByTagName("tag");

				search:
					for(int i=0; i<nList.getLength(); i++){
						Element node = (Element) nList.item(i);
						String currentTag = node.getFirstChild().getNodeValue();
						if(currentTag.equalsIgnoreCase(tag)){
							match=true;
							break search;
						}

					}

				if(match){
					String resName, resUrl, resSvnUrl, resDesc, resDev, resDate;
					resName = ((Element) currentProject.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
					resUrl = ((Element) currentProject.getElementsByTagName("url").item(0)).getFirstChild().getNodeValue();
					resSvnUrl = ((Element) currentProject.getElementsByTagName("svnurl").item(0)).getFirstChild().getNodeValue();
					resDesc = ((Element) currentProject.getElementsByTagName("description").item(0)).getFirstChild().getNodeValue();
					resDev = ((Element) currentProject.getElementsByTagName("developer").item(0)).getFirstChild().getNodeValue();
					resDate = ((Element) currentProject.getElementsByTagName("date").item(0)).getFirstChild().getNodeValue();
					ProjectObject projObj = new ProjectObject(resName, resUrl, resSvnUrl, resDesc, resDev, resDate, false);
					for(int i=0; i<nList.getLength(); i++){
						Element node = (Element) nList.item(i);
						String currentTag = node.getFirstChild().getNodeValue();
						projObj.addTag(currentTag);
					}
					result.add(projObj);
				}
			}
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return match;
	}

	public boolean searchNames(File input, ArrayList<ProjectObject> result, String name){

		boolean match;
		boolean foundName = false;
		Document doc;
		try {
			doc = dBuilder.parse(input);
			NodeList projectList = doc.getElementsByTagName("project");

			for(int k=0;k<projectList.getLength(); k++){
				match=false;
				Element currentProject = (Element) projectList.item(k);

				NodeList nList = currentProject.getElementsByTagName("name");
				Element element = (Element) nList.item(0);
				String currentName = element.getFirstChild().getNodeValue();
				if(name==null || name.equalsIgnoreCase(currentName) ||  name.equals("")){
					match=true;
				}

				if(match){
					String resName, resUrl, resSvnUrl, resDesc, resDev, resDate;
					resName = ((Element) currentProject.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
					resUrl = ((Element) currentProject.getElementsByTagName("url").item(0)).getFirstChild().getNodeValue();
					resSvnUrl = ((Element) currentProject.getElementsByTagName("svnurl").item(0)).getFirstChild().getNodeValue();
					resDesc = ((Element) currentProject.getElementsByTagName("description").item(0)).getFirstChild().getNodeValue();
					resDev = ((Element) currentProject.getElementsByTagName("developer").item(0)).getFirstChild().getNodeValue();
					resDate = ((Element) currentProject.getElementsByTagName("date").item(0)).getFirstChild().getNodeValue();
					ProjectObject projObj = new ProjectObject(resName, resUrl,resSvnUrl, resDesc, resDev, resDate, true);
					NodeList nTagList = currentProject.getElementsByTagName("tag");
					for(int i=0; i<nTagList.getLength(); i++){
						Element node = (Element) nTagList.item(i);
						String currentTag = node.getFirstChild().getNodeValue();
						projObj.addTag(currentTag);
					}
					result.add(projObj);
					foundName=true;
				}

			}

		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		return foundName;
	}


}
