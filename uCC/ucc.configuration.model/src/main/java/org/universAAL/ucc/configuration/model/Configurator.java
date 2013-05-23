package org.universAAL.ucc.configuration.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.model.configurationdefinition.Configuration;
import org.universAAL.ucc.configuration.model.configurationinstances.ConfigurationInstance;
import org.universAAL.ucc.configuration.model.configurationinstances.ObjectFactory;

/**
 * 
 * This class holds both, the configuration definition and the configuration instance.
 * 
 * @author Sebastian.Schoebinge
 *
 */

public class Configurator {
	private Logger logger;
	private Configuration configDefinition;
	private ConfigurationInstance configInstance;
	
	public Configuration getConfigDefinition() {
		return configDefinition;
	}
	
	public ConfigurationInstance getConfigInstance() {
		return configInstance;
	}

	public Configurator(Configuration config) {
		logger = LoggerFactory.getLogger(Configurator.class);
		configDefinition = config;
		configInstance = new ObjectFactory().createConfigurationInstance();
	}

	public void setConfigurationInstance(ConfigurationInstance config) {
		if(config != null){
			configInstance = config;
		}
	}

}
