/*****************************************************************************************
	Copyright 2012-2014 CERTH-HIT, http://www.hit.certh.gr/
	Hellenic Institute of Transport (HIT)
	Centre For Research and Technology Hellas (CERTH)
	
	
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
 *****************************************************************************************/

package org.universaal.tools.codeassistantapplication.ontologyrepository.client;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.universaal.tools.codeassistantapplication.Activator;
import org.universaal.tools.codeassistantapplication.ontologyrepository.preferences.PreferenceConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RepositoryClient {

	//static String REPOSITORY_URL = "http://155.207.85.53:8080/bioportal/ontologies";
	//static String APIKey = "990fff23-51f4-479e-863b-21554d863ef9";
	static String REPOSITORY_URL = "";
	static String APIKey = "";
	
	static String pathToSaveFiles = "./";

	private static void RepositoryClient() {		
	}

	private static void initFiles(){

		try {
			String p_url = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ONTOLOGY_REPOSITORY_URL);
			REPOSITORY_URL=p_url;
			String p_apikey = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ONTOLOGY_REPOSITORY_APIKEY);
			APIKey=p_apikey;
			
			//System.out.println("repo url="+REPOSITORY_URL);
			//System.out.println("APIKey="+APIKey);
		} catch (Exception e) {
			//e.printStackTrace();
		} 
	}

	
	static public boolean getLatestOntologiesMetadata() {
		if (RestClient.sendGetRequest(REPOSITORY_URL, "apikey=" + APIKey,
				pathToSaveFiles + "latest_Ontologies.xml")) {
			return true;
		} else
			return false;
	}

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0)
				.getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	static public boolean downloadOntologyFile(String versionID) {
		//System.out.println("pathToSaveFiles"+pathToSaveFiles);
		if (getLatestOntologiesMetadata()) {
			String ontologyName = "";
			// find ontology name in metadata by parsing the xml file
			try {
				File onts = new File(pathToSaveFiles + "latest_Ontologies.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(onts);
				doc.getDocumentElement().normalize();
				NodeList nodes = doc.getElementsByTagName("ontologyBean");
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						if (getValue("id", element).equals(versionID)) {
							ontologyName = getValue("string", element);
						}						
					}
				}
			} catch (Exception ex) {
				//ex.printStackTrace();
				return false;
			}
			if (RestClient.sendGetRequest(REPOSITORY_URL + "/download/"
					+ versionID, "apikey=" + APIKey, pathToSaveFiles
					+ ontologyName)) {
				return true;
			} else
				return false;
		}
		return false;

	}

	static public boolean downloadOntologyFile(String versionID, String name) {
		if (RestClient.sendGetRequest(
				REPOSITORY_URL + "/download/" + versionID, "apikey=" + APIKey,
				pathToSaveFiles + name + ".owl")) {
			return true;
		} else
			return false;
	}

	private static String renameFile(String directory, String fileToBeRenamed){
		String newFileName = null;
		File dir = new File(directory);
		if (dir.isDirectory()){
			File[] list = dir.listFiles();
			if (list!=null){
				for (int i=0; i<list.length; i++){
					String filename = list[i].getName();
					if (filename.equals(fileToBeRenamed)){
						File oldXmlFile = new File(pathToSaveFiles + fileToBeRenamed.substring(0, fileToBeRenamed.indexOf("."))+"_2.xml");
						list[i].renameTo(oldXmlFile);
						newFileName=oldXmlFile.toString();
					}
				}
			}
		}
		return newFileName;
	}
	
	static public boolean downloadAllOntologies(String directory) {
		initFiles();
		boolean res=false;
		pathToSaveFiles = directory + "/";
		//pathToSaveFiles = directory + File.separator;
		
		
		//System.out.println("pathToSaveFiles="+pathToSaveFiles);
		// Rename the old Ontologies.xml
		String previousXmlFile = renameFile(directory, "latest_Ontologies.xml");
		// Read the new Ontologies.xml 
		//System.out.println("pathToSaveFiles="+pathToSaveFiles);
		if (getLatestOntologiesMetadata()) {
			// parse xml to get all ontology ids and download ontologies
			try {
				File onts = new File(pathToSaveFiles + "latest_Ontologies.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(onts);
				doc.getDocumentElement().normalize();
				NodeList nodes = doc.getElementsByTagName("ontologyBean");
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						// Read the previous xml file if exists
						if (previousXmlFile!=null){
							File onts2 = new File(previousXmlFile);
							DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
							DocumentBuilder dBuilder2 = dbFactory.newDocumentBuilder();
							Document doc2 = dBuilder.parse(onts2);
							doc2.getDocumentElement().normalize();
							NodeList nodes2 = doc2.getElementsByTagName("ontologyBean");
							boolean found=false;
							for (int j=0; j<nodes2.getLength(); j++) {
								Node node2 = nodes2.item(j);
								if (node2.getNodeType() == Node.ELEMENT_NODE) {
									Element element2 = (Element) node2;
									if ((getValue("id", element)).equals(getValue("id", element2)))
										found=true;
								}
							}
							if (!found){
								//System.out.println("1. Not Found="+getValue("id", element));
								downloadOntologyFile(getValue("id", element));
							}
						}
						else{
							//System.out.println("2. Not Found="+getValue("id", element));
							downloadOntologyFile(getValue("id", element));
						}
					}
				}
			} 
			catch (Exception ex) {
				//ex.printStackTrace();
				if (previousXmlFile!=null){
					File onts2 = new File(previousXmlFile);
					onts2.delete();
				}
				return false;
			}
		}
		else
			return false;

		if (previousXmlFile!=null){
			File onts2 = new File(previousXmlFile);
			onts2.delete();
		}
		return true;
	}
	
	public static void main(String[] args) {
		downloadAllOntologies(pathToSaveFiles);
	}

	public static String getAPIKey() {
		return APIKey;
	}
	public static void setAPIKey(String aPIKey) {
		APIKey = aPIKey;
	}

}
