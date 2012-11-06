package org.universAAL.ucc.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.service.ProfilingService;
import org.universAAL.ontology.profile.ui.mainmenu.MenuEntry;
import org.universAAL.ontology.profile.ui.mainmenu.MenuProfile;
import org.universAAL.ucc.api.model.IApplicationRegistration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ApplicationRegistration implements IApplicationRegistration {

	private String[] naBundles = new String[]{
			"com.itextpdf_5.1.1.jar",
			"jaxrpc-api-1.1.jar",
			"nutritional.uiclient.basic_0.0.1.SNAPSHOT.jar",
			"ont.nutrition_0.1.2.SNAPSHOT.jar"
	}; 
	private String[] infoframeBundles = new String[]{
			"com.springsource.org.jdom_1.1.0.jar",
			"infoframe-1.0.0-SNAPSHOT.jar",
			"javax.servlet_2.5.0.v201103041518.jar",
			"jetty-6.1.26.jar",
			"jetty-util-6.1.26.jar",
			"net.java.dev.rome_1.0.1.SNAPSHOT.jar",
			"org.apache.felix.dependencymanager_2.0.1.jar",
			"org.eclipse.equinox.http.jetty_2.0.100.v20110502.jar",
			"org.eclipse.equinox.http.servlet_1.1.200.v20110502.jar"
	}; 
	
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
		if(appName.equals("TTA"))
			return writeTTA(appName, rundir);
		if(appName.equals("Infoframe"))
			return writeInfoFrame(appName);
		if(appName.equals("Nutritional Advisor"))
			return writeNutrionalAdvisor(appName, rundir);
		return false;
			
	}
		
	
	private boolean writeInfoFrame(String appName){
		Map<String,String> attributes=Activator.getModel().getApplicationManagment().getConfiguration(appName);
		Set<String> keys=attributes.keySet();
		Iterator<String> itr=keys.iterator();
		String path=attributes.get("install_base");
		copyToBundlesFolder(path);
		new File(path+"/config").mkdir();
		File conf= new File(path+"/config/config.ini");
		
		
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
							  out.write(key+" = "+attributes.get(key));
							  out.newLine();
						  }
					  }
				  }
			  }
			  
			  out.close();
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		return true;
		
	}
	private boolean writeNutrionalAdvisor(String appName, String rundir){
		Map<String,String> attributes=Activator.getModel().getApplicationManagment().getConfiguration(appName);
		Set<String> keys=attributes.keySet();
		Iterator<String> itr=keys.iterator();
		copyToBundlesFolder(attributes.get("install_base"));
		new File("configurations/nutritional.uiclient/NutritionalAdvisor").mkdirs();
		File conf= new File("configurations/nutritional.uiclient/NutritionalAdvisor/setup.properties");	

		
		
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
							  out.write(key+" = "+attributes.get(key));
							  out.newLine();
						  }
					  }
				  }
			  }
			  out.write("max_temperature=25");							out.newLine();
			  out.write("avoid_ami=yes");								out.newLine();
			  out.write("nutritional_folder=NutritionalAdvisor");		out.newLine();
			  out.write("empty_cache=no");								out.newLine();
			  out.write("ami_user_name=David_Shopland");				out.newLine();
			  out.write("printer_conf_file=C\\:/Program Files/EPSON/JavaPOS/SetupPOS/jpos.xml");out.newLine();
			  out.write("check_advises_delay_minutes=60");				out.newLine();
			  out.write("scheduler_active=yes");						out.newLine();
			  out.write("oasis_path=C\\:\\\\OASIS\\\\");				out.newLine();
			  out.write("download_profile_on_start=yes");				out.newLine();
			  out.write("social_comunity_use_web_services=no");			out.newLine();
			  out.write("language_interface=EN");						out.newLine();
			  out.write("TSF_active=yes");								out.newLine();
			  out.close();
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
//		uncomment to enable menu creation
		writeNAMenuEntry();
		return true;
		
	}

//	uncomment to enable menu creation
	private void writeNAMenuEntry(){
		addEntry(
		         Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied",
		         "Nutritional Advisor",
		         "http://www.tsb.upv.es",
		         "http://ontology.persona.ima.igd.fhg.de/Nutritional.owl#Nutritional",
		         null);
	}
	
	private void addEntry(String userID, String entryName, String vendor,
	         String serviceClass, String iconURL) {

	     MenuEntry me = new MenuEntry(null);
	     me.setVendor(new Resource(vendor));
	     me.setServiceClass(new Resource(serviceClass));
	     Resource pathElem = new Resource(iconURL);
	     pathElem.setResourceLabel(entryName);
	     me.setPath(new Resource[] { pathElem });

	     ServiceRequest sr = new ServiceRequest(new ProfilingService(), null);
	     sr.addValueFilter(new String[] { ProfilingService.PROP_CONTROLS },
	         new User(userID));
	     sr.addAddEffect(new String[] { ProfilingService.PROP_CONTROLS,
	         Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE,
	         MenuProfile.PROP_ENTRY }, me);

	     ServiceResponse res = Activator.sc.call(sr);
	     if (res.getCallStatus() == CallStatus.succeeded) {
	         LogUtils.logDebug(Activator.mc, Activator.class, "addEntry",
	             new Object[] { "new user ", userID, " added." }, null);
	     } else {
	         LogUtils.logDebug(Activator.mc, Activator.class, "addEntry",
	             new Object[] { "callstatus is not succeeded" }, null);
	     }
	     }

	
	
	private boolean writeTTA(String appName, String rundir){
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
	private void copyToBundlesFolder(String appDir){
		String bundleDir = appDir.substring(0, appDir.lastIndexOf("\\"));
		File[] files = new File(appDir).listFiles();
		for(File file : files){
			if(file.isFile()&&file.getName().endsWith(".jar")){
				try {
					copyFile(file, new File(bundleDir+"/"+file.getName()));
				} catch (IOException e) {
					
				}	
			}
		}
	}
	
	public void removeFromBundlesFolder(String appDir){
		String bundleDir = appDir.substring(0, appDir.lastIndexOf("\\"));
		File[] appFiles = new File(appDir).listFiles();
		File[] bundleFiles = new File(bundleDir).listFiles();
		for(File appFile : appFiles){
			for(File bundleFile : bundleFiles){
				if(appFile.isFile()&&bundleFile.isFile()&&bundleFile.getName().equals(appFile.getName())){
					bundleFile.delete();		
				}
			}
		}
	}
	private static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;
	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        long count = 0;
	        long size = source.size();              
	        while((count += destination.transferFrom(source, count, size-count))<size);
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
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
