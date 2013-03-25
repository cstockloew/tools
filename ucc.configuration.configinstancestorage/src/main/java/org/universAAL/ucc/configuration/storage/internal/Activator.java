package org.universAAL.ucc.configuration.storage.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.universAAL.ucc.configuration.storage.ConfigurationInstancesStorageImpl;
import org.universAAL.ucc.configuration.storage.interfaces.ConfigurationInstancesStorage;

/**
 * 
 * This activator class registers the configuration instances storage service.
 * 
 * @author Sebastian.Schoebinge
 *
 */

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		context.registerService(ConfigurationInstancesStorage.class.getName(), new ConfigurationInstancesStorageImpl(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
