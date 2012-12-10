package org.universAAL.tools.makrorecorder;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;



public class Activator implements BundleActivator{

	private static BundleContext bundleContext = null;
	private static ModuleContext moduleContext = null;
	
	
	public void start(BundleContext context) throws Exception {
		bundleContext = context;
		moduleContext = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { bundleContext });
		MakroRecorder.getInstance().showGUI();
	}
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub		
	}

	public static BundleContext getBundleContext() {
		return bundleContext;
	}
	
	public static ModuleContext getModuleContext() {
		return moduleContext;
	}

}
