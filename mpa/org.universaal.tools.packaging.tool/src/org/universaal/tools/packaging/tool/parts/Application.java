package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;


public class Application {

	public static final String defaultURL = "http://www.webpage.com";
	public static final String defaultString = "asdf";
	public static final String defaultFile = "c:\file.txt";

	private App application;
	private ApplicationCapabilities capabilities;
	private ApplicationRequirements requirements;
	private ApplicationManagement management;
	private List<Part> parts;

	public Application(){
		this.application = new App();
		this.capabilities = new ApplicationCapabilities();
		this.requirements = new ApplicationRequirements();
		this.management = new ApplicationManagement();
	}

	public App getApplication() {
		return application;
	}
	public void setApplication(App app) {
		this.application = app;
	}
	public ApplicationCapabilities getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(ApplicationCapabilities capabilities) {
		this.capabilities = capabilities;
	}
	public ApplicationRequirements getRequirements() {
		return requirements;
	}
	public void setRequirements(ApplicationRequirements requirements) {
		this.requirements = requirements;
	}
	public ApplicationManagement getManagement() {
		return management;
	}
	public void setManagement(ApplicationManagement management) {
		this.management = management;
	}
	public List<Part> getParts() {
		if(this.parts == null)
			this.parts = new ArrayList<Part>();
		return parts;
	}

	public String getXML(){

		String r = "";

		r = r.concat("<app>"+application.getXML()+"</app>");
		r = r.concat("<applicationCapabilities>"+capabilities.getXML()+"</applicationCapabilities>");
		r = r.concat("<applicationRequirements>"+requirements.getXML()+"</applicationRequirements>");
		r = r.concat("<applicationManagement>"+management.getXML()+"</applicationManagement>");
		r = r.concat("<applicationPart>");
		for(int i = 0; i < getParts().size(); i++)
			r = r.concat(parts.get(i).getXML());
		r = r.concat("</applicationPart>");

		return r;
	}

	/*
	 <xs:element name="app">
	 ...
	 <xs:element minOccurs="0" name="applicationCapabilities">
	 ...
	 <xs:element minOccurs="0" name="applicationRequirements">
	 ...
	 <xs:element name="applicationManagement" minOccurs="0">
	 ...
	 <xs:element name="applicationPart">
	 ...
	 */
}