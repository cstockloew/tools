package org.universAAL.ucc.configuration.controller;

import java.io.File;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.Window;

import org.universAAL.ucc.configuration.beans.ConfigurationSaveOptions;
import org.universAAL.ucc.configuration.exception.ConfigurationInstanceAlreadyExistsException;
import org.universAAL.ucc.configuration.internal.Activator;
import org.universAAL.ucc.configuration.model.ConfigOptionRegistry;
import org.universAAL.ucc.configuration.model.ConfigurationOption;
import org.universAAL.ucc.configuration.model.Configurator;
import org.universAAL.ucc.configuration.model.MapConfigurationOption;
import org.universAAL.ucc.configuration.model.SimpleConfigurationOption;
import org.universAAL.ucc.configuration.model.configurationdefinition.Category;
import org.universAAL.ucc.configuration.model.configurationdefinition.Configuration;
import org.universAAL.ucc.configuration.model.configurationdefinition.MapConfigItem;
import org.universAAL.ucc.configuration.model.configurationdefinition.SimpleConfigItem;
import org.universAAL.ucc.configuration.model.configurationinstances.ConfigOption;
import org.universAAL.ucc.configuration.model.configurationinstances.ConfigurationInstance;
import org.universAAL.ucc.configuration.model.configurationinstances.ObjectFactory;
import org.universAAL.ucc.configuration.model.exceptions.ValidationException;
import org.universAAL.ucc.configuration.storage.interfaces.ConfigurationInstancesStorage;
import org.universAAL.ucc.configuration.view.ConfigurationOverviewWindow;

/**
 * 
 * This class controls the configurator.
 * 
 * @author Sebastian Schoebinger
 *
 */

public class VaadinConfigurationController {
	
	private Logger logger;
	private ConfigurationOverviewWindow view;
	private Configurator configurator;
	private ConfigOptionRegistry modelRegistry;
	private String configFileFolder;
	private ConfigurationInstancesStorage storage;
//	private String flatId; 
	
	/**
	 * Create the configuration option registry and the directory for the given configuration definition. 
	 * @param view
	 * @param config
	 */
	public VaadinConfigurationController(ConfigurationOverviewWindow view, Configuration config) {
		logger = LoggerFactory.getLogger(VaadinConfigurationController.class);
		this.view = view;
//		this.flatId = view.getFlatId();
		modelRegistry = new ConfigOptionRegistry();
		configurator = new Configurator(config);
		configFileFolder = Activator.getModuleConfigHome().getAbsolutePath() +"/"+config.getBundlename()+"/";
		File file = new File(configFileFolder);
		if(!file.isDirectory()){
			try{
				file.mkdir();
			}catch(Exception e){
				logger.error("Could not create directory: " + configFileFolder);
			}
		}
		
		BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		ServiceReference reference = context.getServiceReference(ConfigurationInstancesStorage.class.getName());
		storage = (ConfigurationInstancesStorage) context.getService(reference);
	}
	
	public String getConfigFileFolder() {
		return configFileFolder;
	}
	
	public Configurator getConfigurator() {
		return configurator;
	}
	
	/**
	 * Load the configuration option from configuration definition and create the models.
	 */
	public void loadConfigurationItems(){
		Configuration config = configurator.getConfigDefinition();
		List<Category> categories = config.getCategory();
		logger.debug("Starting loop over all categories: " + categories.size());
		for(Category cat: categories){
			logger.debug("Category: " + cat.getLabel());
			List<Object> configItems = cat.getSPARQLConfigItemAndMapConfigItemAndSimpleConfigItem();
			for(Object item: configItems){
				if(item instanceof SimpleConfigItem){
					new SimpleConfigurationOption((SimpleConfigItem)item, cat, modelRegistry);
				}else if(item instanceof MapConfigItem){
					new MapConfigurationOption((MapConfigItem)item, cat, modelRegistry);
				}
			}
		}
		setDefaultValues();
	}
	
	/**
	 * Set the default values for all configuration options which are in the registry.
	 */
	private void setDefaultValues(){
		logger.debug("set default values.");
		for(ConfigurationOption option: modelRegistry.getAll()){
			if(option instanceof SimpleConfigurationOption){
				SimpleConfigurationOption sOption = (SimpleConfigurationOption) option;
				sOption.setDefaultValue();
			}
		}
	}
	
	
	/**
	 * Save the configuration instance.
	 * @param configOptions
	 * @param saveOptions
	 * @param force
	 * @throws ConfigurationInstanceAlreadyExistsException
	 */
	public void saveConfiguration(List<ConfigurationOption> configOptions, ConfigurationSaveOptions saveOptions, boolean force) throws ConfigurationInstanceAlreadyExistsException {
		
		ConfigurationInstance instance = new ObjectFactory().createConfigurationInstance();
		instance.setId(saveOptions.getId());
		instance.setUsecaseid(saveOptions.getUseCaseId());
		instance.setIsPrimary(saveOptions.isPrimary());
		instance.setIsSecondary(saveOptions.isSecondary());
		instance.setAuthor(saveOptions.getAuthor());
		instance.setVersion(saveOptions.getVersion());
		
		BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		ServiceReference reference = context.getServiceReference(ConfigurationInstancesStorage.class.getName());
		ConfigurationInstancesStorage storage = (ConfigurationInstancesStorage) context.getService(reference);
				
		if(storage.contains(instance) && !force){
			throw new ConfigurationInstanceAlreadyExistsException();
		}
		
		configurator.getConfigInstance().getConfigOption().clear();
		for(ConfigurationOption cOpt : configOptions){
			instance.getConfigOption().add(cOpt.getConfigOption());
		}
		
		if(storage.contains(instance)){
			storage.replaceConfigurationInstance(instance);
		}else{
			storage.addConfigurationInstance(instance);
		}
			view.removeWindow(view.configurationWindow);
	}
	
	/**
	 * Check the dependencies of all configuration options which are in the registry.
	 */
	public void checkDependencies() {
		for(ConfigurationOption option:  modelRegistry.getAll()){
			option.checkDependencies();
		}
	}

	public ConfigOptionRegistry getModelRegistry() {
		return modelRegistry;
	}
	
	/**
	 * Set the values of the configuration instance to the configuration options.
	 */
	public void initializeValues() {
		logger.debug("initialize values.");
		for(ConfigOption option: configurator.getConfigInstance().getConfigOption()){
			ConfigurationOption model = modelRegistry.getConfigOptionForId(option.getId());
			if(model != null){
				logger.debug("set value for: " + model.getId());
				try {
					model.setValue(option.getValue());
				} catch (ValidationException e) {
					logger.debug("Value isn't valid!");
				}
			}else{
				logger.debug("model for id: " + option.getId() + " not found!");
			}
		}
	}

	public void setConfigInstance(ConfigurationInstance value) {
		configurator.setConfigurationInstance(value);
	}

	public void deleteConfigurationInstance() {		
		if (!storage.removeConfigurationInstance(configurator.getConfigInstance())) {
		   view.showNotification("Deletion failed!", Window.Notification.TYPE_ERROR_MESSAGE);
			
		}
	}

	public void closeConfiguration() {
		modelRegistry.removeAll();
	}

	public ConfigurationOverviewWindow getView() {
		return view;
	}

	public void setView(ConfigurationOverviewWindow view) {
		this.view = view;
	}

//	public String getFlatId() {
//		return flatId;
//	}
//
//	public void setFlatId(String flatId) {
//		this.flatId = flatId;
//	}
//	
	

}
