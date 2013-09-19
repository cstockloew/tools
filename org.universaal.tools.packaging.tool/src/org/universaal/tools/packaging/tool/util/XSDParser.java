package org.universaal.tools.packaging.tool.util;
  
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;    

import org.w3c.dom.Document;

public class XSDParser{

	private static XSDParser instance = null;
	
    private Document document = null;
    private XPathFactory xpf = null;
    private XPath xp = null;
    
    private XSDParser(String XSD){
    	try{
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(XSD.substring(1,XSD.length()-1)+"/AAL-UAPP.xsd");
            xpf = XPathFactory.newInstance();
            xp = xpf.newXPath();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static synchronized XSDParser get(String XSD) {
		if (instance == null) {
			instance = new XSDParser(XSD);
		}
		return instance;
	}
    
    public String find(String what){
    	if(instance != null){
	    	String model = "";
	    	String[] segments = what.split("\\.");
	    	if(segments.length <= 1){
	    		model = model + "//element[@name='"+what+"']//annotation/documentation/text()";
	    		model = model + "|//complexType[@name='"+what+"']//annotation/documentation/text()";
	    		model = model + "|//simpleType[@name='"+what+"']//annotation/documentation/text()";
				
			} else {
		    	model = model + "//element[@name='"+segments[0]+"']//element[@name='"+segments[1]+"']//annotation/documentation/text()";
		    	model = model + "|//complexType[@name='"+segments[0]+"']//element[@name='"+segments[1]+"']//annotation/documentation/text()";
		    	model = model + "|//simpleType[@name='"+segments[0]+"']//element[@name='"+segments[1]+"']//annotation/documentation/text()";
			}
	    	
	    	//System.out.println("Looking for " + prefix);
	       
	    	try {
				String text = xp.evaluate(model, document.getDocumentElement());
				return text;
			} catch (XPathExpressionException e) {
				e.printStackTrace();
				return "";
			}
	        
    	} else return "";
    }
}