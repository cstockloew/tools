package org.universaal.tools.packaging.tool.gui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.universaal.tools.packaging.tool.util.XSDParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RequirementsDefinitions {
	private static RequirementsDefinitions instance = null;
	
	private Document document;
	private XPathFactory xpf = null;
    private XPath xp = null;
    
    private RequirementsDefinitions(){
    	try{
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            InputStream is = getClass().getResourceAsStream("/org/universaal/tools/packaging/tool/xml/requirements.xml");
            
            document = db.parse(is);
            xpf = XPathFactory.newInstance();
            xp = xpf.newXPath();
            
    	} catch (Exception e) {
    		e.printStackTrace();
    	}    	
    }
    
    public static synchronized RequirementsDefinitions get() {
		if (instance == null) {
			instance = new RequirementsDefinitions();
		}
		return instance;
	}
   
    public List<String> listRequirements(String name){
    	List<String> returnList = new ArrayList<String>();
    	
    	try {
			NodeList nodes = (NodeList) xp.evaluate("//requirements/requirement[@name='"+name+"']/values/value",document.getDocumentElement(),XPathConstants.NODESET);
			if(nodes != null){
				for (int i = 0; i < nodes.getLength(); i++) {
					returnList.add(nodes.item(i).getTextContent());
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return returnList;
    }
}
