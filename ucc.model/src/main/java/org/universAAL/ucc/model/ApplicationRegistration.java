package org.universAAL.ucc.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.universAAL.ucc.api.model.IApplicationRegistration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ApplicationRegistration implements IApplicationRegistration {

	public boolean unregisterApplication(String appName) {
		Document doc = Model.getDocument();
		doc.removeChild(ApplicationManagment.getApplication(appName, doc));

		if(!doc.hasChildNodes()){
			File file = new File(Model.FILENAME);
			file.delete();
			return true;
		}else{
		
		 try {
			TransformerFactory.newInstance().newTransformer().transform(
			            new DOMSource(doc), new StreamResult(Model.FILENAME));
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
		}
	}
	
	public boolean unregisterBundle(String appName, String bundleName) {
		Document doc = Model.getDocument();
		Node appNode = ApplicationManagment.getApplication(appName, doc);
		Node bundleNode = ApplicationManagment.getBundle(bundleName, doc);
		appNode.removeChild(bundleNode);
		
		 try {
			TransformerFactory.newInstance().newTransformer().transform(
			            new DOMSource(doc), new StreamResult(Model.FILENAME));
		} catch (Exception e){
			e.printStackTrace();
			return false;
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
	
	public boolean writeToConfigFile(String appName, String rundir){
		String path=getConfPath(rundir);
		if(!new File(path+appName).mkdirs())
			return false;
		File conf = new File(path+appName+"/tta.risk.properties");
		Map<String,String> attributes=Activator.getModel().getApplicationManagment().getConfiguration(appName);
		Set<String> keys=attributes.keySet();
		Iterator<String> itr=keys.iterator();
		try{
			  // Create file 
			  FileWriter fstream = new FileWriter(conf);
			  BufferedWriter out = new BufferedWriter(fstream);
			  String key;
			  while(itr.hasNext()){
				  key=itr.next();
				  if(!key.equals("appName")){
					  if(!key.equals("install_base")){
						  if(!key.equals("name")){
							  out.write(key+"="+attributes.get(key));
							  out.newLine();
						  }
					  }
				  }
			  }
			  
			  out.write("RISK.Room@Bathroom=00\\:60,06\\:150,12\\:60");
			  out.newLine();
			  out.write("RISK.Room@Default=00\\:0,01\\:23");
			  out.newLine();
			  out.write("RISK.Room@Kitchen=00\\:1");
			  out.newLine();
			  out.write("RISK.delay=1");
			  out.close();
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		return true;
	}
	public boolean removeConfigFile(String appName, String rundir){
		String path=getConfPath(rundir);
		File folder=new File(path+appName);
		if(folder.exists()){
			deleteFolder(folder);
		}
		return true;
	}
	private void deleteFolder(File folder){
		
		String[] children=folder.list();
		if(!(children==null)){
			for(int i=0;i<children.length;i++){
				File child=new File(folder,children[i]);
				if(child.isFile())child.delete();
				if(child.isDirectory())deleteFolder(child);
			}
		}
		folder.delete();
		
	}
	private String getConfPath(String rundir){
		if(Activator.context.getBundle().getLocation().startsWith("file"))
			return "configurations\\";
		else{
			for(int i=0;i<3;i++)
				rundir=rundir.substring(0, rundir.lastIndexOf("\\"));
			return rundir+"\\confadmin\\";
		}
	}

	
	
}
