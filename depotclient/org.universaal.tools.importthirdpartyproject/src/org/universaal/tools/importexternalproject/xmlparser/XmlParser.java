/*
	Copyright 2011 SINTEF, http://www.sintef.no
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.tools.importexternalproject.xmlparser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlParser {

	public static final String FIELD_EMPTY = "Not given.";
//	private ArrayList<File> resultList;
	private DocumentBuilderFactory factory;
	private DocumentBuilder dBuilder;

	public XmlParser(){
		this.factory = DocumentBuilderFactory.newInstance();
		try {
			this.dBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean searchTags(String xml, 
			ArrayList<ProjectObject> result, String tag){

		boolean match=false;
		Document doc;
		try {
			InputSource source = new InputSource(new StringReader(xml));
			doc = dBuilder.parse(source);
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
//					String resName, resUrl, resSvnUrl, resDesc, resDev, resDate;
//					resName = ((Element) currentProject.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
//					resUrl = ((Element) currentProject.getElementsByTagName("url").item(0)).getFirstChild().getNodeValue();
//					resSvnUrl = ((Element) currentProject.getElementsByTagName("svnurl").item(0)).getFirstChild().getNodeValue();
//					resDesc = ((Element) currentProject.getElementsByTagName("description").item(0)).getFirstChild().getNodeValue();
//					resDev = ((Element) currentProject.getElementsByTagName("developer").item(0)).getFirstChild().getNodeValue();
//					resDate = ((Element) currentProject.getElementsByTagName("date").item(0)).getFirstChild().getNodeValue();
//					ProjectObject projObj = new ProjectObject(resName, resUrl, resSvnUrl, resDesc, resDev, resDate, false);
//					for(int i=0; i<nList.getLength(); i++){
//						Element node = (Element) nList.item(i);
//						String currentTag = node.getFirstChild().getNodeValue();
//						projObj.addTag(currentTag);
//					}
//					result.add(projObj);
					String resName, resUrl, resSvnUrl, resDesc, resDev, resDate;
					try{
						resName = ((Element) currentProject.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resName = FIELD_EMPTY;
					}
					try{
						resUrl = ((Element) currentProject.getElementsByTagName("url").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resUrl = FIELD_EMPTY;
					}
					try{
						resSvnUrl = ((Element) currentProject.getElementsByTagName("svnurl").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resSvnUrl = FIELD_EMPTY;
					}
					try{
						resDesc = ((Element) currentProject.getElementsByTagName("description").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resDesc = FIELD_EMPTY;
					}
					try{
						resDev = ((Element) currentProject.getElementsByTagName("developer").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resDev = FIELD_EMPTY;
					}
					try{
						resDate = ((Element) currentProject.getElementsByTagName("date").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resDate = "FIELD_EMPTY";
					}
					
					ProjectObject projObj = new ProjectObject(resName, resUrl,resSvnUrl, resDesc, resDev, resDate, false);
					try{
						NodeList nTagList = currentProject.getElementsByTagName("tag");
						for(int i=0; i<nTagList.getLength(); i++){
							Element node = (Element) nTagList.item(i);
							String currentTag = node.getFirstChild().getNodeValue();
							projObj.addTag(currentTag);
						}
					}catch(Exception e){
						projObj.addTag(FIELD_EMPTY);
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

	public boolean searchNames(String xml, ArrayList<ProjectObject> result, String name){

		boolean match;
		boolean foundName = false;
		Document doc;
		try {
			InputSource source = new InputSource(new StringReader(xml));
			doc = dBuilder.parse(source);
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
					try{
						resName = ((Element) currentProject.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resName = FIELD_EMPTY;
					}
					try{
						resUrl = ((Element) currentProject.getElementsByTagName("url").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resUrl = FIELD_EMPTY;
					}
					try{
						resSvnUrl = ((Element) currentProject.getElementsByTagName("svnurl").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resSvnUrl = FIELD_EMPTY;
					}
					try{
						resDesc = ((Element) currentProject.getElementsByTagName("description").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resDesc = FIELD_EMPTY;
					}
					try{
						resDev = ((Element) currentProject.getElementsByTagName("developer").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resDev = FIELD_EMPTY;
					}
					try{
						resDate = ((Element) currentProject.getElementsByTagName("date").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resDate = FIELD_EMPTY;
					}
					
					ProjectObject projObj = new ProjectObject(resName, resUrl,resSvnUrl, resDesc, resDev, resDate, true);
					try{
						NodeList nTagList = currentProject.getElementsByTagName("tag");
						for(int i=0; i<nTagList.getLength(); i++){
							Element node = (Element) nTagList.item(i);
							String currentTag = node.getFirstChild().getNodeValue();
							projObj.addTag(currentTag);
						}
					}catch(Exception e){
						projObj.addTag(FIELD_EMPTY);
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
