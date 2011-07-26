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

/**
 * Parses the downloaded projects.xml-file.
 * @author Adrian
 *
 */
public class XmlParser {

	public static final String FIELD_EMPTY = "Not given.";
	private DocumentBuilderFactory factory;
	private DocumentBuilder dBuilder;

	public XmlParser(){
		this.factory = DocumentBuilderFactory.newInstance();
		try {
			this.dBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds all the projects listed in the xml-string given as input, and creates
	 * ProjectObjects for each of them.
	 * @param xml - The xml-string that is to be parsed.
	 * @param result - The ArrayList where the results will be placed.
	 */
	public void getAll(String xml, ArrayList<ProjectObject> result){

		Document doc;
		try {
			InputSource source = new InputSource(new StringReader(xml));
			doc = dBuilder.parse(source);
			NodeList projectList = doc.getElementsByTagName("project");

			for(int k=0;k<projectList.getLength(); k++){

				Element currentProject = (Element) projectList.item(k);

					String resName, resUrl, resSvnUrl, resDesc, resDev, resDate,
					resLicense, resLicenseUrl, resSubProjects;
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
					try{
						resLicense = ((Element) currentProject.getElementsByTagName("license").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resLicense = FIELD_EMPTY;
					}
					try{
						resLicenseUrl = ((Element) currentProject.getElementsByTagName("licenseurl").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resLicenseUrl = FIELD_EMPTY;
					}
					try{
						resSubProjects = ((Element) currentProject.getElementsByTagName("subprojects").item(0)).getFirstChild().getNodeValue();
					}catch(Exception e){
						resSubProjects = FIELD_EMPTY;
					}
					
					boolean containsSubProjects = resSubProjects.equalsIgnoreCase("true");
					ProjectObject projObj = new ProjectObject(resName, resUrl,
							resSvnUrl, resDesc, resDev, resDate, resLicense,
							resLicenseUrl, containsSubProjects, true);
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

		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}


}
