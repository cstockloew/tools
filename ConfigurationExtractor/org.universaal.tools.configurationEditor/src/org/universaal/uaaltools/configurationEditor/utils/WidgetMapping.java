package org.universaal.uaaltools.configurationEditor.utils;

import java.util.HashMap;

import org.eclipse.swt.widgets.Widget;
import org.jdom2.Attribute;
import org.jdom2.Element;

public class WidgetMapping {
	
	public static final int ELEMENT = 1;
	public static final int ATTRIBUTE = 0;
	
	
	private static HashMap<Widget, Element> elementMap = new HashMap<>();
	private static HashMap<Widget, Attribute> attributeMap = new HashMap<>();
	
	public static void put(Widget wi, Object ob){
		if(ob instanceof Attribute){
			attributeMap.put(wi, (Attribute) ob);
		} else if(ob instanceof Element){
			elementMap.put(wi, (Element) ob);
		}
	}
	
	
	public static int get(Widget wi){
		if(elementMap.containsKey(wi)){
			return 1;
		} else if(attributeMap.containsKey(wi)){
			return 0;
		} else {
			return -1;
		}
		
	}
	
	public static Attribute getAttribute(Widget wi){
		return attributeMap.get(wi);
	}
	
	public static Element getElement(Widget wi){
		return elementMap.get(wi);
	}
}
