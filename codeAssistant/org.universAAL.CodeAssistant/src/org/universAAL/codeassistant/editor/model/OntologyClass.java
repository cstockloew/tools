package org.universAAL.codeassistant.editor.model;

import java.util.Hashtable;
import java.util.List;

public class OntologyClass {

    private String className;
    private List<OntologyClass> children;
    private Hashtable properties;
    private String URI;
    
	public List<OntologyClass> getChildren() {
        return children;
    }

    public void setChildren(List<OntologyClass> children) {
        this.children = children;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Hashtable getProperties() {
		return properties;
	}

	public void setProperties(Hashtable properties) {
		this.properties = properties;
	}
	
	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}
}
