package org.universAAL.ucc.configuration.model.servicetracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.model.ConfigurationOption;
import org.universAAL.ucc.configuration.model.interfaces.OnConfigurationChangedListener;
import org.universAAL.ucc.configuration.model.interfaces.OnConfigurationChangedListenerFactory;

public class ListenerServiceTracker extends ServiceTracker {
	
	Logger logger;
	BundleContext context;
	ConfigurationOption option;
	OnConfigurationChangedListener listener;

	public ListenerServiceTracker(BundleContext context, String name, ConfigurationOption option) {
		super(context, name, null);
		this.option = option;
		this.context = context;
		logger = LoggerFactory.getLogger(getClass());
		logger.debug("new ServiceTracker for name:" + name);
	}
	
	@Override
	public Object addingService(ServiceReference reference) {
		logger.debug("Service added: " + reference.getClass().toString());
		try{
			Object o = context.getService(reference);
			if(o instanceof OnConfigurationChangedListener){
				listener = (OnConfigurationChangedListener)o;
				logger.debug("loaded: " + listener.getClass());
				option.addListener(listener);
			}else if(o instanceof OnConfigurationChangedListenerFactory){
				OnConfigurationChangedListenerFactory factory = (OnConfigurationChangedListenerFactory)o;
				listener = factory.create();
				logger.debug("loaded: " + listener.getClass());
				option.addExternalListener(listener);
			}
		}catch(ClassCastException e){
			logger.debug("Listener cannot casted to OnConfigurationModelChangedListener!");
		}
		return super.addingService(reference);
	}
	
	@Override
	public void removedService(ServiceReference reference, Object service) {
		option.removeExternalListener(listener);
		super.removedService(reference, service);
	}
	
}
