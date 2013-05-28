package org.universAAL.ucc.configuration.storage.servicetracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.api.ConfigPreferences;

import org.universAAL.ucc.configuration.storage.interfaces.ConfigurationInstancesStorage;

public class StorageServiceTracker extends ServiceTracker {
	
	private Logger logger;
	private ConfigPreferences pref;

	public StorageServiceTracker(BundleContext context, Class<ConfigurationInstancesStorage> clazz,
			ServiceTrackerCustomizer customizer, ConfigPreferences pref) {
		super(context, clazz.getName(), customizer);
		logger = LoggerFactory.getLogger(getClass());
		logger.debug("new storage service tracker created!");
		this.pref = pref;
	}

	@Override
	public Object addingService(ServiceReference reference) {
		logger.debug("Service added: " + reference.getClass().toString());
		try{
			Object o = context.getService(reference);
			if(o instanceof ConfigurationInstancesStorage){
				pref.setStorage((ConfigurationInstancesStorage)o);
			}
		}catch(ClassCastException e){
			logger.debug("Service cannot casted to ConfigurationInstancesStorage!");
		}
		return super.addingService(reference);
	}
	
}
