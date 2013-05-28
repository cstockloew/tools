package org.universAAL.ucc.configuration.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.model.interfaces.ModelRegistryChangedListener;

/**
 * 
 * The registry class to manage the configuration options.
 * Every configuration option adds itself to this registry.
 * You can ask the registry for configuration options by its id's.
 * 
 * @author Sebastian.Schoebinge
 *
 */

public class ConfigOptionRegistry {
	
	Logger logger;
	HashMap<String, ConfigurationOption> mRegistry;
	LinkedList<ModelRegistryChangedListener> listeners;
	
	public ConfigOptionRegistry() {
		logger = LoggerFactory.getLogger(this.getClass());
		
		mRegistry = new HashMap<String, ConfigurationOption>();
		listeners = new LinkedList<ModelRegistryChangedListener>();
	}
	
	public void register(ConfigurationOption configOption){
		if(configOption != null){
			if(!mRegistry.containsKey(configOption.getId())){
				mRegistry.put(configOption.getId(),configOption);
				logger.debug("model registered: " + configOption.getId());
				updateListeners();
			}
		}
	}
	
	public ConfigurationOption getConfigOptionForId(String id){
		if(mRegistry.containsKey(id)){
			return mRegistry.get(id);
		}
		return null;
	}

	public void removeConfigOption(String id) {
		mRegistry.remove(id);
		logger.debug("model removed: " + id);
		updateListeners();
	}

	public Collection<ConfigurationOption> getAll() {
		ArrayList<ConfigurationOption> retList = new ArrayList<ConfigurationOption>(mRegistry.values());
		Collections.sort(retList);
		return retList;
	}

	public void removeAll() {
		mRegistry.clear();
		logger.debug("registry cleared");
		updateListeners();
	}
	
	public void addListener(ModelRegistryChangedListener listener){
		if(listener != null){
			listeners.add(listener);
		}
	}
	
	public void removeListener(ModelRegistryChangedListener listener){
		listeners.remove(listener);
	}
	
	public void removeAllListeners(){
		listeners.clear();
	}
	
	private void updateListeners(){
		for(ModelRegistryChangedListener listener: listeners){
			listener.modelRegistryChanged();
		}
	}

	public int size() {
		return mRegistry.size();
	}

	public boolean isEmpty() {
		return mRegistry.isEmpty();
	}
}
