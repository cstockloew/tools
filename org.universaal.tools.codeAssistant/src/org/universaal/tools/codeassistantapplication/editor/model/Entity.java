/*****************************************************************************************
	Copyright 2012-2014 CERTH-HIT, http://www.hit.certh.gr/
	Hellenic Institute of Transport (HIT)
	Centre For Research and Technology Hellas (CERTH)
	
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 *****************************************************************************************/

package org.universaal.tools.codeassistantapplication.editor.model;

/*
 *  Entity Class. Entity can be a class, subclass or property 
 *  of an Owl structure.
 *
 */

public class Entity {
	private static int counter = 0;
	private int id;				// unique id of an entity
	private String text;   		// name of class, subclass or property
	private String uri;     	// the uniform resource identifier
	private String property;    // if the entity is a property, then the value can be object, functional, datatype.  
								// If the is a class then the value is equal to "".
	private String RDFType;     // the RDF type
	private String range;     	// the property range

	
	public Entity(String text) {
	    id = counter++;
	    this.text = text;
	}

	public Entity(String text, String uri) {
		id = counter++;
	    this.text = text;
	    this.uri = uri;
	}

	public Entity(String text, String uri, String property) {
		id = counter++;
	    this.text = text;
	    this.uri = uri;
	    this.property = property;
	}

	public Entity(String text, String uri, String property, String RDFtype, String range) {
		id = counter++;
	    this.text = text;
	    this.uri = uri;
	    this.property = property;
	    this.RDFType = RDFtype;
	    this.range = range;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		if (uri==null)
			uri = "";
		if (property==null)
			property = "";
	    //return text + " " + property;
		return text;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
	        + ((text == null) ? 0 : text.hashCode());
	    result = prime * result
	            + ((uri == null) ? 0 : uri.hashCode());
	    result = prime * result
	            + ((property == null) ? 0 : property.hashCode());

	    return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
	    if (obj == null)
	    	return false;
	    if (getClass() != obj.getClass())
	    	return false;
	    Entity other = (Entity) obj;
	    if (text == null) {
	    	if (other.text != null)
	    		return false;
	    } 
	    else if (!text.equals(other.text))
	    	return false;
	    if (uri == null) {
	        if (other.uri != null)
	          return false;
	    } 
	    else if (!uri.equals(other.uri))
	        return false;
	    if (property == null) {
	        if (other.property != null)
	          return false;
	    } 
	    else if (!property.equals(other.property))
	        return false;
	    
	    return true;
	}
	
	public String getRDFType() {
		return RDFType;
	}

	public void setRDFType(String rDFType) {
		this.RDFType = rDFType;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}
}