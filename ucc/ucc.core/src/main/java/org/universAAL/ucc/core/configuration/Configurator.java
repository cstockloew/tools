package org.universAAL.ucc.core.configuration;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ucc.api.core.IConfigurator;
import org.universAAL.ucc.core.Activator;


/**
 * @author Alex
 * @version 1.0
 * @created 11-Jul-2011 15:57:26
 */
public class Configurator implements IConfigurator {
	private BundleContext context;

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

	public String finishConfiguration(String appName, ArrayList<Bundle> bundles, HashMap<String,String> conf){
		if(!Activator.getModel().getApplicationRegistration().registerApplication(appName, conf))
			return "App Registration failed!";
		if(!Activator.getModel().getApplicationRegistration().writeToConfigFile(appName,Activator.getRundir()))
			return "Error writing Configuration File!";
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
	 */
	public void performConfiguration(String Path){

	}
	
	public void testAMethod(){
		System.out.println("This is a Test!");
	}

}