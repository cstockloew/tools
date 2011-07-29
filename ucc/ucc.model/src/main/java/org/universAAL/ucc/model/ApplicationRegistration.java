package org.universAAL.ucc.model;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.universAAL.ucc.api.model.IApplicationRegistration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ApplicationRegistration implements IApplicationRegistration {

	public boolean unregisterApplication(String appName) {
		Document doc = Model.getDocument();
		doc.removeChild(ApplicationManagment.getApplication(appName, doc));

		 try {
			TransformerFactory.newInstance().newTransformer().transform(
			            new DOMSource(doc), new StreamResult(Model.FILENAME));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean unregisterBundle(String bundleName) {
		Document doc = Model.getDocument();
		doc.removeChild(ApplicationManagment.getBundle(bundleName, doc));

		 try {
			TransformerFactory.newInstance().newTransformer().transform(
			            new DOMSource(doc), new StreamResult(Model.FILENAME));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
}

	
	public boolean registerBundle(String appName, String bundleName) {
		if(Activator.getModel().getApplicationManagment().containsApplication(appName)
				&& !Activator.getModel().getApplicationManagment().getInstalledBundles(appName).contains(bundleName)){

		return addBundleToXML(appName, bundleName);
		}else{
			return false;
		}
	}
	
	public boolean registerApplication(String appName, Map<String, String> configuration) {
		return addApplicationToXML(appName, configuration);
	}
	
	private boolean addBundleToXML(String appName, String bundleName){
		boolean success = true;
		try {
			
			Document doc = Model.getDocument();

			Element element = ApplicationManagment.getApplication(appName, doc);
			Element bundleRoot = doc.createElement("bundle");
			element.appendChild(bundleRoot);
			
			bundleRoot.setAttribute("name", bundleName);
			
			 TransformerFactory.newInstance().newTransformer().transform(
		                new DOMSource(doc), new StreamResult(Model.FILENAME));

		} catch (Exception e){
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	private boolean addApplicationToXML(String appName,  Map<String, String> configuration){
		boolean success = true;
		try {
			Document doc = Model.getDocument();
			Element appRoot = doc.createElement("application");
			doc.appendChild(appRoot);
			appRoot.setAttribute("name", appName);
			
			for(Entry<String, String> entry : configuration.entrySet()){
				appRoot.setAttribute(entry.getKey(), entry.getValue());
			}

			 TransformerFactory.newInstance().newTransformer().transform(
		                new DOMSource(doc), new StreamResult(Model.FILENAME));
			
		} catch (Exception e){
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	
	
}
