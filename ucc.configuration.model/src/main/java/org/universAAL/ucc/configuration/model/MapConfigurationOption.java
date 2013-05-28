package org.universAAL.ucc.configuration.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.universAAL.ucc.configuration.model.configurationdefinition.Category;
import org.universAAL.ucc.configuration.model.configurationdefinition.MapConfigItem;
import org.universAAL.ucc.configuration.model.configurationdefinition.Option;


/**
 * 
 * This class represents the map configuration item of the xml configuration definition.
 * 
 * @author Sebastian.Schoebinge
 *
 */

public class MapConfigurationOption extends ConfigurationOption{
	
	Logger logger;

	public MapConfigurationOption(MapConfigItem configItem, Category category, ConfigOptionRegistry configOptionRegestry){
		super(configItem, category, configOptionRegestry);
		logger = LoggerFactory.getLogger(this.getClass().getName());
	}
	
	public boolean allowMultiselection() {
		return new Cardinality(configItem.getCardinality()).allowMultiselection();
	}

	public List<Option> getOptions() {
		return ((MapConfigItem)configItem).getOptions().getOption();
	}
	
}
