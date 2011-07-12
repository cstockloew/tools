package org.universAAL.ucc.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.ucc.model.api.IModel;
import org.universAAL.ucc.model.creator.ModelCreator;

/**
 * The uCCCore is the connection to the OSGi Framework and therefore need to have
 * an Activator. Other part of the uCC (View and Plug-Ins) not necessary need to
 * be valid bundles and maybe can get there information from the core (if needed).
 * @author amarinc
 * @version 1.0
 * @updated 11-Jul-2011 16:37:33
 */
public class Activator implements BundleActivator {
	
	public final static IModel model = ModelCreator.getModel();

	public Activator(){
		
	}

	public void finalize() throws Throwable {

	}

	public void start(BundleContext arg0) throws Exception {
		
	}

	public void stop(BundleContext arg0) throws Exception {
		
	}

	public void test(){

	}

}