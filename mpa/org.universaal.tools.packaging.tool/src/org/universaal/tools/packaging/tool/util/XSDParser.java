package org.universaal.tools.packaging.tool.util;
  
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;    

import org.universaal.tools.packaging.tool.api.Page;
import org.w3c.dom.Document;

public class XSDParser{

	private static XSDParser instance = null;
	
    private Document document = null;
    private XPathFactory xpf = null;
    private XPath xp = null;
    private static boolean online = true;
    
    private XSDParser(String XSD){
    	try{
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            if(Page.XSD_REPOSITORY.contains("http")){
            	if(checkOnline(XSD)){
            		System.out.println("WEB");
            		document = db.parse(Page.XSD_REPOSITORY+"v"+XSD+"/AAL-UAPP.xsd");
            	}
            	else {
            		System.out.println("RESOURCE");
            		InputStream is = getClass().getResourceAsStream("/org/universaal/tools/packaging/tool/schemas/"+XSD+"/AAL-UAPP.xsd");
		            document = db.parse(is);
            	}
            } else {
            	System.out.println("Reading XSD from Resource");
                InputStream is = getClass().getResourceAsStream(Page.XSD_REPOSITORY+XSD+"/AAL-UAPP.xsd");
	            document = db.parse(is);
            }
            xpf = XPathFactory.newInstance();
            xp = xpf.newXPath();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private boolean checkOnline(String XSD){
    	try {
    		URL url = new URL(Page.XSD_REPOSITORY+"v"+XSD+"/AAL-UAPP.xsd");
        	String inputLine = "";
        	URLConnection con = url.openConnection();
    	    con.setReadTimeout( 5000 ); //5 seconds
    	    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    	    in.close();
    	    online = true;
    	} catch (IOException e) {
    		online = false;
    	}
    	return online;
    }
    
    public static synchronized XSDParser get(String XSD) {
		if (instance == null) {
			instance = new XSDParser(XSD);
		} else {
			if(online == false){
				if(instance.checkOnline(XSD))
					instance = new XSDParser(XSD);
			}
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