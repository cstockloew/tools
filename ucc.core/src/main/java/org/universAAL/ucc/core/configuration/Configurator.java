package org.universAAL.ucc.core.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ucc.api.core.IConfigurator;
import org.universAAL.ucc.core.Activator;

/* remove jena dependency to temporarily avoid runner issue
import com.hp.hpl.jena.ontology.AllValuesFromRestriction;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Alt;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
*/
import com.trolltech.qt.gui.QBoxLayout;


/**
 * @author Alex
 * @version 1.0
 * @created 11-Jul-2011 15:57:26
 */
public class Configurator implements IConfigurator {
	private BundleContext context;
	protected static final String PREFIX = "http://ontology.universAAL.org/usecases#";
	private UCConfig configuration;

	public Configurator(BundleContext con){
		context=con;
	}

	public void finalize() throws Throwable {

	}

	/**
	 * Path = Path to configuration file
	 * 
	 * @param Path    Path to Configuration-File is needed
	 */
	private void checkIfConfigurationNeeded(String Path){

	}

	/**
	 * Solve meta-information for concepts (like "this field should contains all
	 * instances of oven")
	 * 
	 * @param uri
	 */
	public ManagedIndividual[] getOntologyContent(String uri){
		return null;
	}
	

	public String finishConfiguration(ArrayList<Bundle> bundles){
		String appName =configuration.getName();
		 String install_base = bundles.get(0).getLocation();
		 install_base=install_base.substring(5, install_base.lastIndexOf(File.separator));
		 HashMap<String, String> conf= configuration.getConfiguraton();
		 
		 conf.put("install_base",install_base);
		 conf.put("appName", configuration.getName());
		 
		 
		
		
		if(!Activator.getModel().getApplicationRegistration().registerApplication(appName, conf))
			return "App Registration failed!";
		if(!Activator.getModel().getApplicationRegistration().writeToConfigFile(appName,Activator.getInformation().getRunDir()))
			return "Error writing Configuration File!";
		String[] order=readStartupOrder(install_base);
		if(order!=null){
			ArrayList<Bundle> orderedbundles=sortBundles(bundles, order);
			if(orderedbundles!=null) bundles=orderedbundles;
		}
		
		Iterator<Bundle> itr=bundles.iterator();
		while(itr.hasNext()){
			Bundle b=itr.next();
			if(!Activator.getModel().getApplicationRegistration().registerBundle(appName, generateBundleName(b)))
				return "Bundle Registration failed!";
			if(b.getState()==Bundle.INSTALLED){
				try {
					b.start();
				} catch (BundleException e) {
					
					return "Error starting Bundle "+b.getSymbolicName()+": "+e.getMessage();					
				}
			}
		}

		return null;
	}
	private String generateBundleName(Bundle bundle){
		Dictionary dict = bundle.getHeaders();
		return(bundle.getSymbolicName()+"_"+dict.get("Bundle-Version"));
	}
	
	/**
	 * 
	 * @param Path
	 * @throws Exception 
	 */
	public void performConfiguration(String path, QBoxLayout parent) throws Exception{
		configuration=readOntology(path);
		configuration.generateUI(parent);
		
	}
	
	/*
	private static UCConfig readOntology(String path) throws Exception {
		// read the ontology from the file
		OntModel m = readOntologyFromFile(path);
		UCConfig ucconfig=null;
		int index=path.lastIndexOf("\\")+1;
		path=path.substring(0,index);
		HashMap<String, String> defaultValues=readDefaultValues(path+"defaultValues.txt");
		
		// get UCConfig
		ExtendedIterator<? extends OntResource> instances = getInstances(m,"UCConfigItem");
		int i=0;
		while(instances.hasNext()){
			Individual thisInstance = (Individual) instances.next();
			if(thisInstance.getOntClass().getURI().equals(PREFIX + "UCConfig")){
				DatatypeProperty pr = m.getDatatypeProperty(PREFIX + "hasUID");
				ucconfig=new UCConfig(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasVersionNumber");
				if(thisInstance.getProperty(pr)!=null)
				ucconfig.setVersionNumber(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasName");
				if(thisInstance.getProperty(pr)!=null)
				ucconfig.setName(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasAuthor");
				if(thisInstance.getProperty(pr)!=null)
				ucconfig.setAuthor(thisInstance.getProperty(pr).getLiteral().toString());
				
				if(!ucconfig.isValid()) throw new Exception("Malformed owl file: UCConfig not proberly specified!");
				
				i++;
			}
			if(i==2) throw new Exception("Malformed owl file: The owl contains more than 1 UCConfig-element!");
		}
		instances.close();
		if(ucconfig==null)
			throw new Exception("Malformed owl file: The owl does not contain a UCConfig-element!");
		
		//Get Panels
		instances = getInstances(m,"UCConfigItem");
		while(instances.hasNext()){
			Individual thisInstance = (Individual) instances.next();
			if(thisInstance.getOntClass().getURI().equals(PREFIX + "OntologyPanel")){
				
				
				OntologyPanel temp= new OntologyPanel(thisInstance.getLocalName());
				
				DatatypeProperty pr = m.getDatatypeProperty(PREFIX + "hasDomain");
				if(thisInstance.getProperty(pr)!=null)
				temp.setDomain(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasCaption");
				if(thisInstance.getProperty(pr)!=null)
				temp.setCaption(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasLocationID");
				if(thisInstance.getProperty(pr)!=null)
				temp.setLocationID(thisInstance.getProperty(pr).getLiteral().getInt());
				
				pr = m.getDatatypeProperty(PREFIX + "hasHoverText");
				if(thisInstance.getProperty(pr)!=null)
				temp.setHoverText(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasLabelText");
				if(thisInstance.getProperty(pr)!=null)
				temp.setLabelText(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasName");
				if(thisInstance.getProperty(pr)!=null)
				temp.setName(thisInstance.getProperty(pr).getLiteral().toString());
				
				//if(!temp.isValid()) throw new Exception("Malformed owl file: An OntologyPanel is not proberly specified!");
				
				ucconfig.addOntologyPanel(temp);
				
			}
			if(thisInstance.getOntClass().getURI().equals(PREFIX + "ConfigPanel")){
				
				
				ConfigPanel temp= new ConfigPanel(thisInstance.getLocalName());
				
				DatatypeProperty pr = m.getDatatypeProperty(PREFIX + "hasCaption");
				temp.setCaption(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasLocationID");
				if(thisInstance.getProperty(pr)!=null)
				temp.setLocationID(thisInstance.getProperty(pr).getLiteral().getInt());
				
				pr = m.getDatatypeProperty(PREFIX + "hasHoverText");
				if(thisInstance.getProperty(pr)!=null)
				temp.setHoverText(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasLabelText");
				if(thisInstance.getProperty(pr)!=null)
				temp.setLabelText(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasName");
				if(thisInstance.getProperty(pr)!=null)
				temp.setName(thisInstance.getProperty(pr).getLiteral().toString());
				
				//if(!temp.isValid()) throw new Exception("Malformed owl file: A ConfigPanel is not proberly specified!");
				
				ucconfig.addConfigPanel(temp);
				
			}
		}
		
		// This temporarily replaces the original readOntotloy (see above)
		private static UCConfig readOntology(String path) throws Exception {
			
		}
		
		//get ConfigItems
		instances.close();
		instances = getInstances(m,"UCConfigItem");
		while(instances.hasNext()){
			Individual thisInstance = (Individual) instances.next();
			if(thisInstance.getOntClass().getURI().equals(PREFIX + "UCConfigItem")){
				
				UCConfigItem temp=new UCConfigItem(thisInstance.getLocalName());
				
				
				DatatypeProperty pr = m.getDatatypeProperty(PREFIX + "hasLocationID");
				if(thisInstance.getProperty(pr)!=null)
					temp.setLocationID(thisInstance.getProperty(pr).getLiteral().getInt());
				
				pr = m.getDatatypeProperty(PREFIX + "hasHoverText");
				if(thisInstance.getProperty(pr)!=null)
					temp.setHoverText(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasLabelText");
				if(thisInstance.getProperty(pr)!=null)
					temp.setLabelText(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasName");
				if(thisInstance.getProperty(pr)!=null)
					temp.setName(thisInstance.getProperty(pr).getLiteral().toString());
				
				pr = m.getDatatypeProperty(PREFIX + "hasValue");
				if(thisInstance.getProperty(pr)!=null)
					temp.setValue(thisInstance.getProperty(pr).getLiteral().toString());

				
				Property p=m.getProperty(PREFIX + "isInPanel");
				if(thisInstance.getProperty(p)!=null)
					temp.setPanel(thisInstance.getProperty(p).getObject().asResource().getLocalName());
				
				if(defaultValues.containsKey(temp.getName()))
					temp.setDefaultValue(defaultValues.get(temp.getName()));
				//if(!temp.isValid()) throw new Exception("Malformed owl file: A ConfigItem is not proberly specified!");
				
				ucconfig.addConfigItem(temp);
			}
		}
		instances.close();
		return ucconfig;
	}*/
	
	// This temporarily replaces the original readOntotloy (see above)
			private static UCConfig readOntology(String path) throws Exception {
				int index=path.lastIndexOf("\\")+1;
				path=path.substring(0,index);
				HashMap<String, String> defaultValues=readDefaultValues(path+"defaultValues.txt");
				
				//set ucconfig
				UCConfig ucconfig= new UCConfig("059badd0-beca-4bf6-9233-331f6ebbc8fb");	
				ucconfig.setVersionNumber("0.0.1-SNAPSHOT");
				ucconfig.setName("Nutritional Advisor");
				ucconfig.setAuthor("Gema");
						
				//add panels
				ConfigPanel item0= new ConfigPanel("Item_0");
				item0.setCaption("Server configuration");
				item0.setLocationID(1);
				ucconfig.addConfigPanel(item0);
				
				ConfigPanel item4= new ConfigPanel("Item_4");
				item4.setCaption("End user preferences for Nutritional Advisor");
				item4.setLocationID(5);
				ucconfig.addConfigPanel(item4);
				
				//add items
				UCConfigItem item1=new UCConfigItem("Item_1");
				item1.setLocationID(2);
				item1.setHoverText("Insert the IP of your Nutritional Advisor web server.");
				item1.setLabelText("IP:");
				item1.setName("web_server_ip_address");
				item1.setValue("String");
				item1.setPanel("Item_0");
				if(defaultValues.containsKey(item1.getName()))
					item1.setDefaultValue(defaultValues.get(item1.getName()));				
				ucconfig.addConfigItem(item1);
				
				UCConfigItem item5=new UCConfigItem("Item_5");
				item5.setLocationID(6);
				item5.setHoverText("Should the Nutritional Advisor start in fullscreen. [true|false]");
				item5.setLabelText("Fullscreen:");
				item5.setName("maximize_window");
				item5.setValue("String");
				item5.setPanel("Item_4");
				if(defaultValues.containsKey(item5.getName()))
					item5.setDefaultValue(defaultValues.get(item5.getName()));				
				ucconfig.addConfigItem(item5);
				
				UCConfigItem item3=new UCConfigItem("Item_3");
				item3.setLocationID(4);
				item3.setHoverText("Insert the web service address of your Nutritional Advisor.");
				item3.setLabelText("Service Address:");
				item3.setName("web_server_service_address");
				item3.setValue("String");
				item3.setPanel("Item_0");
				if(defaultValues.containsKey(item3.getName()))
					item3.setDefaultValue(defaultValues.get(item3.getName()));				
				ucconfig.addConfigItem(item3);
				
				UCConfigItem item7=new UCConfigItem("Item_7");
				item7.setLocationID(8);
				item7.setHoverText("Please set the screensaver delay in seconds.");
				item7.setLabelText("Set screensaver delay:");
				item7.setName("screensaver_delay_second");
				item7.setValue("String");
				item7.setPanel("Item_4");
				if(defaultValues.containsKey(item7.getName()))
					item7.setDefaultValue(defaultValues.get(item7.getName()));				
				ucconfig.addConfigItem(item7);
				
				UCConfigItem item2=new UCConfigItem("Item_2");
				item2.setLocationID(3);
				item2.setHoverText("Insert the Port of your Nutritional Advisor web server.");
				item2.setLabelText("Port:");
				item2.setName("web_server_port");
				item2.setValue("int");
				item2.setPanel("Item_0");
				if(defaultValues.containsKey(item2.getName()))
					item2.setDefaultValue(defaultValues.get(item2.getName()));				
				ucconfig.addConfigItem(item2);
				
				UCConfigItem item6=new UCConfigItem("Item_6");
				item6.setLocationID(7);
				item6.setHoverText("Activate screensaver for Nutritional Advisor. [true|false]");
				item6.setLabelText("Enable Screensaver:");
				item6.setName("screensaver_enabled");
				item6.setValue("String");
				item6.setPanel("Item_4");
				if(defaultValues.containsKey(item6.getName()))
					item6.setDefaultValue(defaultValues.get(item6.getName()));				
				ucconfig.addConfigItem(item6);
				
			return ucconfig;
			}
	
	/**
	 * @param filename file to read
	 * @return OntModel for the ontology in a given file
	 */
	/* remove jena dependency to temporarily avoid runner issue
	private static OntModel readOntologyFromFile(String filename) {
		OntModel m = ModelFactory.createOntologyModel();

		InputStream in = FileManager.get().open(filename);
		if (in == null) {
		    System.err.println("File: " + filename + " not found");
		    return null;
		}        
		m.read(in, null);
		
		return m;
	}*/

	public boolean checkEnteredValues(){
		return configuration.checkEnteredValues();
	}
	private static HashMap<String, String> readDefaultValues(String path){
	 	HashMap<String, String> defaultValues=new HashMap<String, String>();
	    
	    
	    FileInputStream fis=null;
		try {
			fis=new FileInputStream(path);
		} catch (FileNotFoundException e) {
				System.out.println("Couldn't find defaultValues.txt");
				return defaultValues;
		}
		Scanner scanner = new Scanner(fis, "UTF-8");
		
	    try {
	      while (scanner.hasNextLine()){
	        String current[]=scanner.nextLine().split("=");
	        defaultValues.put(current[0], current[1]);
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    return defaultValues;
}
	
	private String[] readStartupOrder(String path){
		 	String[] order = new String[9];
		    
		    
		    FileInputStream fis=null;
			try {
				fis=new FileInputStream(path+"/startup.txt");
			} catch (FileNotFoundException e) {
					System.out.println("Couldn't find startup.txt");
					return null;
			}
			Scanner scanner = new Scanner(fis, "UTF-8");
			int i=0;
		    try {
		      while (scanner.hasNextLine()){
		        order[i]=scanner.nextLine();
		        i++;
		      }
		    }
		    finally{
		      scanner.close();
		    }
		    return order;
	}
	private ArrayList<Bundle> sortBundles(ArrayList<Bundle> bundles, String[] order){
		ArrayList<Bundle> orderedBundles = new ArrayList<Bundle>();
		if(order.length!=bundles.size()) return null;
		Bundle current;
		for(int i=0;i<order.length;i++){
			current=findBySymbolicName(order[i], bundles);
			if(current==null) return null;
			orderedBundles.add(current);
		}
		return orderedBundles;
	}
	private Bundle findBySymbolicName(String sn, ArrayList<Bundle> bundles){
		Iterator<Bundle>itr=bundles.iterator();
		Bundle current;
		while(itr.hasNext()){
			current=itr.next();
			if(current.getSymbolicName().equals(sn)) return current;
		}
		return null;
	}
}