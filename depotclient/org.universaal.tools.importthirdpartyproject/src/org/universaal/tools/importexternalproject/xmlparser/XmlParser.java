package org.universaal.tools.importexternalproject.xmlparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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

	public boolean searchTags(ArrayList<File> input, 
			ArrayList<ProjectObject> result, String tag){

		Iterator<File> it = input.iterator();
		boolean match=false;
		Document doc;
		while(it.hasNext()){
			match = false;
			try {
				File current = it.next();
				doc = dBuilder.parse(current);
				NodeList nList = doc.getElementsByTagName("tag");

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
					String resName, resUrl, resDesc, resDev, resDate;
					resName = ((Element) doc.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
					resUrl = ((Element) doc.getElementsByTagName("url").item(0)).getFirstChild().getNodeValue();
					resDesc = ((Element) doc.getElementsByTagName("description").item(0)).getFirstChild().getNodeValue();
					resDev = ((Element) doc.getElementsByTagName("developer").item(0)).getFirstChild().getNodeValue();
					resDate = ((Element) doc.getElementsByTagName("date").item(0)).getFirstChild().getNodeValue();
					ProjectObject projObj = new ProjectObject(resName, resUrl, resDesc, resDev, resDate, false);
					for(int i=0; i<nList.getLength(); i++){
						Element node = (Element) nList.item(i);
						String currentTag = node.getFirstChild().getNodeValue();
						projObj.addTag(currentTag);
					}
					result.add(projObj);
				}
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return match;
	}

	public boolean searchNames(ArrayList<File> input, ArrayList<ProjectObject> result, String name){
		Iterator<File> it = input.iterator();

		boolean match;
		boolean foundName = false;
		Document doc;
		while(it.hasNext()){
			match = false;
			try {
				File current = it.next();
				doc = dBuilder.parse(current);
				NodeList nList = doc.getElementsByTagName("name");
				Element element = (Element) nList.item(0);
				String currentName = element.getFirstChild().getNodeValue();
				if(name==null || name.equalsIgnoreCase(currentName) ||  name.equals("")){
					match=true;
				}

				if(match){
					String resName, resUrl, resDesc, resDev, resDate;
					resName = ((Element) doc.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
					resUrl = ((Element) doc.getElementsByTagName("url").item(0)).getFirstChild().getNodeValue();
					resDesc = ((Element) doc.getElementsByTagName("description").item(0)).getFirstChild().getNodeValue();
					resDev = ((Element) doc.getElementsByTagName("developer").item(0)).getFirstChild().getNodeValue();
					resDate = ((Element) doc.getElementsByTagName("date").item(0)).getFirstChild().getNodeValue();
					ProjectObject projObj = new ProjectObject(resName, resUrl, resDesc, resDev, resDate, true);
					NodeList nTagList = doc.getElementsByTagName("tag");
					for(int i=0; i<nTagList.getLength(); i++){
						Element node = (Element) nTagList.item(i);
						String currentTag = node.getFirstChild().getNodeValue();
						projObj.addTag(currentTag);
					}
					result.add(projObj);
					foundName=true;
				}
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return foundName;
	}

	public ArrayList<ProjectObject> getAll(ArrayList<File> input){
		Iterator<File> it = input.iterator();
		ArrayList<ProjectObject> result = new ArrayList<ProjectObject>();
		Document doc;
		while(it.hasNext()){

			try{
				File current = it.next();
				doc = dBuilder.parse(current);

				String resName, resUrl, resDesc, resDev, resDate;
				resName = ((Element) doc.getElementsByTagName("name").item(0)).getFirstChild().getNodeValue();
				resUrl = ((Element) doc.getElementsByTagName("url").item(0)).getFirstChild().getNodeValue();
				resDesc = ((Element) doc.getElementsByTagName("description").item(0)).getFirstChild().getNodeValue();
				resDev = ((Element) doc.getElementsByTagName("developer").item(0)).getFirstChild().getNodeValue();
				resDate = ((Element) doc.getElementsByTagName("date").item(0)).getFirstChild().getNodeValue();
				result.add(new ProjectObject(resName, resUrl, resDesc, resDev, resDate, true));


			}catch(IOException e){
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}
}
