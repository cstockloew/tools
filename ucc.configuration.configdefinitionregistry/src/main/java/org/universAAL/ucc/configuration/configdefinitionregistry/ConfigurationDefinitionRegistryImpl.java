package org.universAAL.ucc.configuration.configdefinitionregistry;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.configdefinitionregistry.interfaces.ConfigurationDefinitionRegistry;
import org.universAAL.ucc.configuration.configdefinitionregistry.interfaces.ConfigurationDefinitionRegistryChanged;
import org.universAAL.ucc.configuration.model.configurationdefinition.Configuration;

/**
 * The implementation of the configuration definition registry interface.
 * @author Sebastian.Schoebinge
 *
 */

public class ConfigurationDefinitionRegistryImpl implements
		ConfigurationDefinitionRegistry {
	
	Logger logger = LoggerFactory.getLogger(ConfigurationDefinitionRegistryImpl.class);
	
	HashMap<String, Configuration> configDefs;
	
	LinkedList<ConfigurationDefinitionRegistryChanged> listeners;
	
	public ConfigurationDefinitionRegistryImpl() {
		configDefs = new HashMap<String, Configuration>();
		listeners = new LinkedList<ConfigurationDefinitionRegistryChanged>();
	}
	
	public void registerConfigurationDefinition(URL configURL) {
		logger.debug("register file: " + configURL);
		try{
			Configuration config = JAXB.unmarshal(configURL, Configuration.class);
			configDefs.put(config.getBundlename(), config);
			updateListeners();
		}catch(Exception e){
			logger.error("Failed to register configuration definition: " + e.getMessage());
		}
	}

	public List<Configuration> getAllConfigDefinitions() {
		return new LinkedList<Configuration>(configDefs.values());
	}

	public void unregisterConfigurationDefinition(URL configURL) {
		logger.debug("unregister file: " + configURL);
		try{
			Configuration config = JAXB.unmarshal(configURL, Configuration.class);
			configDefs.remove(config.getBundlename());
			updateListeners();
		}catch(Exception e){
			logger.error("Failed to unregister configuration definition: " + e.getMessage());
		}
	}

	public void addConfigurationDefinitionRegistryChanged(
			ConfigurationDefinitionRegistryChanged listener) {
		if(listener != null){
			listeners.add(listener);
		}
	}

	public void removeConfigurationDefinitionRegistryChanged(
			ConfigurationDefinitionRegistryChanged listener) {
		listeners.remove(listener);
	}
	
	private void updateListeners(){
		for(ConfigurationDefinitionRegistryChanged listener : listeners){
			listener.configurationDefinitionRegistryChanged();
		}
	}

}
