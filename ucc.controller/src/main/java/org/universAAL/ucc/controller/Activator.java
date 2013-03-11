package org.universAAL.ucc.controller;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ucc.api.IDeinstaller;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.api.impl.Deinstaller;
import org.universAAL.ucc.api.impl.Installer;

public class Activator implements BundleActivator {

	private static BundleContext context;
	//private static IInstaller installer;
	//private ServiceRegistration registration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		context.registerService(IInstaller.class.getName(), new Installer(), null);
		context.registerService(IDeinstaller.class.getName(), new Deinstaller(), null);
	}
	


	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		
		registration.unregister();
	}

}
