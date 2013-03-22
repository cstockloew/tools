package org.universaal.tools.packaging.tool.parts;

import java.net.URI;

public class Android{

	private String name, description;
	private URI location;

	public Android(String name, String description, URI location){
		this.name = name;
		this.description = description;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URI getLocation() {
		return location;
	}

	public void setLocation(URI location) {
		this.location = location;
	}			

	public String getXML(){
		return "<name>"+name+"</name><description>"+description+"</description><location>"+location.toASCIIString()+"</location>";
	}
}