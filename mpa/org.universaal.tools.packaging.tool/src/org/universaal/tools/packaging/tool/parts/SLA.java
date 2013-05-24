package org.universaal.tools.packaging.tool.parts;

import java.net.URI;

public class SLA {

	private String name;		
	private URI link;

	public SLA(){

		name = Application.defaultString;
		link = URI.create(Application.defaultURL);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public URI getLink() {
		return link;
	}
	public void setLink(URI link) {
		this.link = link;
	}

	public String getXML(){

		String link_string = "";
		if(link.getScheme() != null && link.getScheme().equalsIgnoreCase("file")){
			String[] splitted = link.toASCIIString().split("/"); 
			link_string = Application.file_prefix+splitted[splitted.length-1];
		}
		else
			link_string = link.toASCIIString();

		return "<sla><name>"+name+"</name><link>"+link_string+"</link></sla>";
	}
}