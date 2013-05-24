package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class Application {

	public static final String defaultURL = "";
	public static final String defaultString = "";
	public static final String defaultFile = "";
	public static final String defaultVersion = "major.minor.micro.build";
	public static final String file_prefix = "file://../license/";

	private App application;
	private ApplicationCapabilities appCapabilities;
	private ApplicationRequirements appRequirements;
	private ApplicationManagement appManagement;
	private List<Part> appParts;

	public Application(){
		this.application = new App();
		this.appCapabilities = new ApplicationCapabilities();
		this.appRequirements = new ApplicationRequirements();
		this.appManagement = new ApplicationManagement();
	}

	public App getApplication() {
		return application;
	}
	public void setApplication(App application) {
		this.application = application;
	}
	public ApplicationCapabilities getAppCapabilities() {
		return appCapabilities;
	}
	public void setAppCapabilities(ApplicationCapabilities appCapabilities) {
		this.appCapabilities = appCapabilities;
	}
	public ApplicationRequirements getAppRequirements() {
		return appRequirements;
	}
	public void setAppRequirements(ApplicationRequirements appRequirements) {
		this.appRequirements = appRequirements;
	}
	public ApplicationManagement getAppManagement() {
		return appManagement;
	}
	public void setAppManagement(ApplicationManagement appManagement) {
		this.appManagement = appManagement;
	}
	public List<Part> getAppParts() {
		if(this.appParts == null)
			this.appParts = new ArrayList<Part>();
		return appParts;
	}

	public String getXML(){

		String r = "";

		r = r.concat("<app>"+application.getXML()+"</app>");
		r = r.concat("<applicationCapabilities>"+appCapabilities.getXML()+"</applicationCapabilities>");
		r = r.concat("<applicationRequirements>"+appRequirements.getXML()+"</applicationRequirements>");
		r = r.concat("<applicationManagement>"+appManagement.getXML()+"</applicationManagement>");
		r = r.concat("<applicationPart>");
		for(int i = 0; i < getAppParts().size(); i++)
			r = r.concat(appParts.get(i).getXML());
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