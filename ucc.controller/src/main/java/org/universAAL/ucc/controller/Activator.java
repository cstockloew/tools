package org.universAAL.ucc.controller;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.api.impl.Installer;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static IInstaller installer;
	private ServiceRegistration registration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		registration = context.registerService(IInstaller.class.getName(), new Installer(), null);
//		Dictionary props = new Hashtable();
//
//        props.put("service.exported.interfaces", "*");
//        props.put("service.exported.configs", "org.apache.cxf.ws");
//        props.put("org.apache.cxf.ws.address", "http://localhost:9090/deploymanager");
//        
//		reg = context.registerService(DeployManagerService.class.getName(), new DeployManagerImpl(), props);
		getService(context);
		
	}
	
	private static void getService(BundleContext bc) {
		if(installer == null) {
			ServiceReference sr = bc.getServiceReference(IInstaller.class.getName());
			if(sr != null)
				installer = (IInstaller)bc.getService(sr);
		}
	}
	
	public static IInstaller getInstaller() {
		if(installer == null) 
			getService(context);
		return installer;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
