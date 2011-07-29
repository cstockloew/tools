package org.universAAL.ucc.model;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.universAAL.ucc.api.model.IApplicationManagment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ApplicationManagment implements IApplicationManagment{

	public List<String> getInstalledApplications(){
		if(new File(Model.FILENAME).exists()){
			List<String> list = new ArrayList<String>();
			Document doc = Model.getDocument();
			NodeList nodeList = doc.getElementsByTagName("application");
			for(int i = 0; i < nodeList.getLength(); i++){
				Element element = (Element) nodeList.item(i);
				list.add(element.getAttribute("name"));
			}
			
			return list;
				
		}else{
			return new ArrayList<String>();
		}
	}
	
	public List<String> getAllInstalledBundles(){
		if(new File(Model.FILENAME).exists()){
			List<String> list = new ArrayList<String>();
			Document doc = Model.getDocument();
			NodeList nodeList = doc.getElementsByTagName("application");
			for(int i = 0; i < nodeList.getLength(); i++){
				NodeList nl = nodeList.item(i).getChildNodes();
				for(int j = 0; j < nl.getLength(); j++){
					Element element = (Element) nl.item(j);
					list.add(element.getAttribute("name"));
				}
			}
			
			return list;
		}else{
			return new ArrayList<String>();
		}
	}
	
	public List<String> getInstalledBundles(String appName){
		List<String> list = new ArrayList<String>();
		if(new File(Model.FILENAME).exists()){
			Document doc = Model.getDocument();
			NodeList nodeList = doc.getElementsByTagName("application");
			for(int i = 0; i < nodeList.getLength(); i++){
				Element el = (Element) nodeList.item(i);
				if(el.getAttribute("name").equals(appName)){
				NodeList nl = el.getChildNodes();
					for(int j = 0; j < nl.getLength(); j++){
						Element element = (Element) nl.item(j);
						list.add(element.getAttribute("name"));
					}
				}
			}
		}
		return list;
	}
	
	public boolean containsApplication(String appName){
		
		Document doc = Model.getDocument();
		NodeList nodeList = null;
		
		try{
			nodeList = doc.getChildNodes();
		}catch(Exception e){
			return false;
		}
		
		for(int i = 0; i< nodeList.getLength(); i++){
			Element el = (Element) nodeList.item(i);
			try{
				if(el.getAttribute("name").equals(appName)){
					return true;
				}
			}catch(Exception e){
			}
		}
		return false;
	}

	public boolean isEmpty(){
		try{
			Document doc = Model.getDocument();
			doc.getChildNodes();
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public static Element getApplication(String appName, Document doc){
		NodeList nodeList = doc.getChildNodes();
		for(int i = 0; i < nodeList.getLength() ; i++){
			Element el = (Element) nodeList.item(i);
			if(el.getAttribute("name").equals(appName)){
				return el;
			}
		}
		return null;
	}
	
	public static Element getBundle(String bundleName, Document doc){
		NodeList nodeList = doc.getChildNodes();
		
		for(int i = 0 ; i < nodeList.getLength() ; i++){
			NodeList nl = nodeList.item(i).getChildNodes();
			for(int j = 0 ; i < nl.getLength() ; j++){
				Element el = (Element) nl.item(j);
				if(el.getAttribute("name").equals(bundleName)){
					return el;
				}
			}
		}
		return null;

	}
	
	public Map<String, String> getConfiguration(String appName){
		Document doc = Model.getDocument();
		NamedNodeMap nodeMap = getApplication(appName, doc).getAttributes();
		Map<String, String> returnMap = new HashMap<String, String>();
		for(int i = 0; i < nodeMap.getLength(); i++){
			Node node =  nodeMap.item(i);
			returnMap.put(node.getNodeName(), node.getNodeValue());
		}
		return returnMap;
	}
	
	public List<String> getBundles(String appName){
		Document doc = Model.getDocument();
		NodeList nodeList = getApplication(appName, doc).getChildNodes();
		List<String> returnList = new ArrayList<String>();
		for(int i = 0; i < nodeList.getLength(); i++){
			Element el = (Element) nodeList.item(i);
			returnList.add(el.getAttribute("name"));
		}
		return returnList;
	}
}
