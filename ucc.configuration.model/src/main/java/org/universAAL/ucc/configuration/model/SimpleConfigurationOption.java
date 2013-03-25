package org.universAAL.ucc.configuration.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.model.configurationdefinition.Category;
import org.universAAL.ucc.configuration.model.configurationdefinition.SimpleConfigItem;
import org.universAAL.ucc.configuration.model.configurationinstances.ObjectFactory;
import org.universAAL.ucc.configuration.model.configurationinstances.Value;
import org.universAAL.ucc.configuration.model.exceptions.ValidationException;

/**
 * 
 * This class represents the the simple configuration item of xml configuration definition.
 * 
 * @author Sebastian.Schoebinge
 *
 */

public class SimpleConfigurationOption extends ConfigurationOption {
	
	Logger logger;
	
	public SimpleConfigurationOption(SimpleConfigItem configItem, Category category, ConfigOptionRegistry configOptionRegestry){
		super(configItem, category, configOptionRegestry);
		logger = LoggerFactory.getLogger(this.getClass().getName());
	}

	public String getType() {
		return ((SimpleConfigItem)configItem).getType();
	}
	
	/**
	 * Set the default value from configuration definition.
	 */
	public void setDefaultValue() {
		Value v = new ObjectFactory().createValue();
		v.setValue(((SimpleConfigItem)configItem).getDefaultValue());
		if(v.getValue() != null){
			try {
				setValue(v);
			} catch (ValidationException e) {
				logger.debug("Value isn't valid!");
			}
		}	
	}
	
}
