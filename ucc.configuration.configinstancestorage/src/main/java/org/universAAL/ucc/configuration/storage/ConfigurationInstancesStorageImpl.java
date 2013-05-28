package org.universAAL.ucc.configuration.storage;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.universAAL.ucc.configuration.model.configurationinstances.ConfigurationInstance;
import org.universAAL.ucc.configuration.storage.exceptions.NoConfigurationFoundException;
import org.universAAL.ucc.configuration.storage.interfaces.ConfigurationInstancesStorage;
import org.universAAL.ucc.configuration.storage.interfaces.StorageChangedListener;

/**
 * 
 * Implementation of the configuration instages storage interface.
 * On startup it loads all configuration instances from file system.
 * 
 * New configuration instances could be added at runtime, where all StorageChangedListener will be informed.
 * 
 * @author Sebastian.Schoebinge
 *
 */

public class ConfigurationInstancesStorageImpl implements ConfigurationInstancesStorage {
	
	Logger logger;
	
	String basedir;

	HashMap<String, ConfigurationInstance> instances;
	
	LinkedList<StorageChangedListener> listeners;
	
	/**
	 * load all instances.
	 */
	public ConfigurationInstancesStorageImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
		
		basedir = System.getenv("systemdrive")+"/tmpConfigFiles/";
		checkFolderOrCreate(basedir);
		instances = new HashMap<String, ConfigurationInstance>();
		listeners = new LinkedList<StorageChangedListener>();
		loadInstances();
	}
	
	private void checkFolderOrCreate(String dir) {
		File folder = new File(dir);
		if(!folder.exists()){
			folder.mkdir();
		}
	}

	/**
	 * load the instances from file system.
	 */
	
	public synchronized void loadInstances(){
		instances.clear();
		for(File dir: getOnlyDirectoryChildren(basedir)){
			for(File file : getOnlyFileChildren(dir.getPath())){
				addConfigurationInstance(JAXB.unmarshal(file, ConfigurationInstance.class));
			}
		}
		
	}
	
	
	public synchronized void addConfigurationInstance(ConfigurationInstance instance) {
		if(!instances.containsKey(getKey(instance))){
			instances.put(getKey(instance), instance);
			checkFolderOrCreate(basedir+instance.getUsecaseid()+"/");
			File file = new File(basedir+instance.getUsecaseid()+"/"+instance.getId()+instance.getVersion()+".xml");
			JAXB.marshal(instance, file);
			logger.info("Configuration saved in file: "+file.getPath());
			updateListeners();
		}
	}
	
	
	public synchronized void replaceConfigurationInstance(ConfigurationInstance instance) {
		if(instances.containsKey(getKey(instance))){
			removeConfigurationInstance(instance);
			addConfigurationInstance(instance);
		}
	}
	
	
	public synchronized boolean removeConfigurationInstance(ConfigurationInstance instance) {
		instances.remove(getKey(instance));
		File file = new File(basedir+instance.getUsecaseid()+"/"+instance.getId()+instance.getVersion()+".xml");
		logger.debug("delete file: " + file.getPath());
		updateListeners();
		return file.delete();
	}
	
	private File[] getOnlyFileChildren(String basedir){
		FileFilter fileFilter = new FileFilter() {
		    public boolean accept(File file) {
		        return file.isFile();
		    }
		};
		File dir = new File(basedir);
		return dir.listFiles(fileFilter);
	}
	
	private File[] getOnlyDirectoryChildren(String basedir){
		FileFilter fileFilter = new FileFilter() {
		    public boolean accept(File file) {
		        return file.isDirectory();
		    }
		};
		File dir = new File(basedir);
		return dir.listFiles(fileFilter);
	}
	
	
	public synchronized List<ConfigurationInstance> getAllInstancesForBundle(String bundlename){
		LinkedList<ConfigurationInstance> bundleInstances = new LinkedList<ConfigurationInstance>();
		if(bundlename != null && !"".equals(bundlename)){
			for(ConfigurationInstance instance : instances.values()){
				if(bundlename.equals(instance.getUsecaseid())){
					bundleInstances.add(instance);
				}
			}
		}
		return bundleInstances;
	}

	
	public boolean contains(ConfigurationInstance configInstance) {
		return instances.containsKey(getKey(configInstance));
	}
	
	private String getKey(ConfigurationInstance instance){
		return instance.getUsecaseid()+instance.getId();
	}
	
	/**
	 * returns the first primary configuration instance or if there is no primary instance then this method
	 * returns the first configuration instance for the given bundle. 
	 */
	
	public synchronized ConfigurationInstance getConfigurationForBundle(Bundle bundle)
			throws NoConfigurationFoundException {
		logger.debug("search configuration instance for bundle: " + bundle.getSymbolicName());
		ConfigurationInstance retInstance = null;
		if(bundle != null && !"".equals(bundle.getSymbolicName())){
			for(ConfigurationInstance instance : instances.values()){
				if(bundle.getSymbolicName().equals(instance.getUsecaseid())){
					retInstance = instance;
					if(retInstance.isIsPrimary() != null && retInstance.isIsPrimary()){
						logger.debug("return first primary configuration instance with id: " + instance.getId());
						return instance;
					}
				}
			}
		}
		if(retInstance != null){
			logger.debug("return configuration instance with id: " + retInstance.getId());
			return retInstance;
		}
		throw new NoConfigurationFoundException();
	}

	
	public synchronized void addListener(StorageChangedListener listener) {
		if(listener != null){
			listeners.add(listener);
		}
	}
	
	
	public synchronized void removeListener(StorageChangedListener listener) {
		if(listener != null){
			listeners.remove(listener);
		}
	}
	
	private synchronized void updateListeners(){
		for(StorageChangedListener listener : new LinkedList<StorageChangedListener>(listeners)){
			listener.storageChanged();
		}
		
	}

}
