package org.universaal.tools.uaalcreator.data;

import static org.universaal.tools.uaalcreator.data.ConfigItem.*;

/**
 * ItemType specifies the type of a ConfigItem 
 * 
 * @author schwende
 */
public enum ItemType {
	
	PANEL("panel"),
	VARIABLE("variable"),
	ONTOLOGY_PANEL("ontologypanel");
	
	private String text;
	
	ItemType(String text) {
		this.text = text;
	}
	
	/**
	 * @param s String to create an ItemType from
	 * @return an instance of ItemType for the given String
	 */
	public static ItemType fromString(String s) {
		if (s.equals("variable"))
				return VARIABLE;
		else if (s.equals("panel"))
			return PANEL;
		else if (s.equals("ontologypanel"))
			return ONTOLOGY_PANEL;
		else
			return null;
	}
	
	/**
	 * @return array containing the names of needed parameters for the ItemType
	 */
	public String[] getParameters() {
		switch (this) {
			case PANEL: return new String[] {CAPTION};
			case VARIABLE: return new String[] {NAME, TYPE, LABEL, HOVER};
			case ONTOLOGY_PANEL: return new String[] {CAPTION, NAME, DOMAIN, LABEL, HOVER};
			default: return null;
		}
	}
	
	@Override
	public String toString() {
		return text;
	}
	
}
