package org.universaal.tools.configurationExtractor.data;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * ConfigItem contains data about a single config item, which can be a panel, variable or ontology panel.
 * 
 * @author schwende
 */
public class ConfigItem {
	
	public static final String NAME = "name";
	public static final String LABEL = "label";
	public static final String HOVER = "hover";
	public static final String CAPTION = "caption";
	public static final String DOMAIN = "domain";
	public static final String TYPE = "type";

	/**
	 * array containing the allowed variable types
	 */
	private static final String[] allowedTypesArray = new String[] {"String", "int", "boolean"};
	/**
	 * list containing the allowed variable types
	 */
	private static List<String> allowedTypes = Arrays.asList(allowedTypesArray);
	
	private ItemType itemType;
	private String name, label, hover, caption, domain, type;
	
	/**
	 * constructor of a ConfigItem of type t
	 * @param t ItemType of this ConfigItem
	 */
	public ConfigItem(ItemType t) {
		setItemType(t);
	}
	
	/**
	 * constructor of a ConfigItem whose ItemType is not specified yet
	 */
	public ConfigItem() {};
	
	
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}
	private void setLabel(String label) {
		this.label = label;
	}
	
	public String getHover() {
		return hover;
	}
	private void setHover(String hover) {
		this.hover = hover;
	}
	
	public String getCaption() {
		return caption;
	}
	private void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getDomain() {
		return domain;
	}
	private void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getType() {
		return type;
	}
	private void setType(String type) {
		this.type = type;
	}


	/**
	 * set a parameter in this ConfigItem
	 * @param param parameter to set
	 * @param value value to set the parameter to
	 * @return true if successful
	 */
	public boolean setParameter(String param, String value) {
		if (param.equals(NAME)) {
			setName(value);
		} else if (param.equals(LABEL)) {
			setLabel(value);
		} else if (param.equals(HOVER)) {
			setHover(value);
		} else if (param.equals(CAPTION)) {
			setCaption(value);
		} else if (param.equals(DOMAIN)) {
			setDomain(value);
		} else if (param.equals(TYPE)) {
			setType(value);
		} else {
			return false;
		}
		
		return true;
	}

	/**
	 * @param param parameter to get the value from
	 * @return value of the given parameter
	 */
	public String getParameter(String param) {
		if (param.equals(NAME)) {
			return getName();
		} else if (param.equals(LABEL)) {
			return getLabel();
		} else if (param.equals(HOVER)) {
			return getHover();
		} else if (param.equals(CAPTION)) {
			return getCaption();
		} else if (param.equals(DOMAIN)) {
			return getDomain();
		} else if (param.equals(TYPE)) {
			return getType();
		} else {
			return null;
		}
	}


	/**
	 * validates the config item
	 * @return an error message when an error is found, or null if no error is found
	 */
	public String validate(Set<String> varNames) {
		
		switch (getItemType()) {
			case VARIABLE:
				// check if the name is set
				if (getName() == null || getName().isEmpty()) {
					return "The name for a variable is not set!";
					
				// check if the name already exists
				} else if (varNames.contains(getName())) {
					return "The name '" + getName() + "' already exists!";
					
				// check if the variable type exists
				} else if (! allowedTypes.contains(getType())) {
					return "The variable type is unknown: " + getType();
				}
				varNames.add(getName());
				break;
			case ONTOLOGY_PANEL:
				// check if the name is set
				if (getName() == null || getName().isEmpty()) {
					return "The name for an ontology concept is not set!";

					// check if the name already exists
				} else if (varNames.contains(getName())) {
					return "The name '" + getName() + "' already exists!";
					
				}
				varNames.add(getName());
				break;
		}
				
		return null;
	}
	
	/**
	 * @return a List containing the allowed types for a variable
	 */
	public static String[] getAllowedTypes() {
		return allowedTypesArray;
	}
	
	/**
	 * @return index of the variable type configured in this item
	 */
	public int getTypeIndex() {
		for (int i = 0; i < allowedTypesArray.length; i++) {
			if (allowedTypesArray[i].equals(type)) {
				return i;
			}
		}
		
		return -1;
	}
	
}
