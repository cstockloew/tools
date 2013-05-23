package org.universAAL.ucc.configuration.model.servicetracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.model.ConfigurationOption;
import org.universAAL.ucc.configuration.model.interfaces.ConfigurationValidator;
import org.universAAL.ucc.configuration.model.interfaces.ConfigurationValidatorFactory;


public class ValidationServiceTracker extends ServiceTracker {
	
	Logger logger;
	BundleContext context;
	ConfigurationOption option;
	ConfigurationValidator validator;
	String[] attributes;
	
	public ValidationServiceTracker(BundleContext context,
			String name, ConfigurationOption option,
			String[] attributes) {
		super(context, name, null);
		this.option = option;
		this.context = context;
		this.attributes = attributes;
		logger = LoggerFactory.getLogger(getClass());
		logger.debug("new validation service tracker created!");
	}

	@Override
	public Object addingService(ServiceReference reference) {
		logger.debug("Service added: " + reference.getClass().toString());
		try{
			Object o = context.getService(reference);
			if(o instanceof ConfigurationValidator){
				validator = (ConfigurationValidator)o;
				validator.setAttributes(attributes);
				logger.debug("loaded: " + validator.getClass());
				option.addValidator(validator.getClass().getName(), validator);
			}else if(o instanceof ConfigurationValidatorFactory){
				ConfigurationValidatorFactory factory = (ConfigurationValidatorFactory)o; 
				validator = factory.create();
				validator.setAttributes(attributes);
				logger.debug("loaded: " + validator.getClass());
				option.addValidator(validator.getClass().getName(), validator);
			}
		}catch(ClassCastException e){
			logger.debug("Listener cannot casted to ConfigurationValidator!");
		}
		return super.addingService(reference);
	}
	
	@Override
	public void removedService(ServiceReference reference, Object service) {
		option.removeValidator(validator);
		super.removedService(reference, service);
	}
	
}
