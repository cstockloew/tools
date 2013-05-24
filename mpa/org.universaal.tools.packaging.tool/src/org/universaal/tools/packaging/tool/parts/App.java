package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class App {

	private String name, appID, description, applicationProfile = "org.universAAL.ontology.profile.AALAppSubProfile";
	private Version version;
	private boolean multipart;
	private String tags;
	private Contact applicationProvider;
	private List<LicenseSet> licenses;

	public App(){
		name  = Application.defaultString;
		appID = Application.defaultString;
		description = Application.defaultString;
		tags = Application.defaultString;

		version = new Version();
		applicationProvider = new Contact();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplicationProfile() {
		return applicationProfile;
	}

	public void setApplicationProfile(String applicationProfile) {
		this.applicationProfile = applicationProfile;
	}

	public Version getVersion() {
		return version;
	}

	public boolean isMultipart() {
		return multipart;
	}

	public void setMultipart(boolean multipart) {
		this.multipart = multipart;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Contact getApplicationProvider() {
		return applicationProvider;
	}

	public List<LicenseSet> getLicenses() {
		if(licenses == null)
			licenses = new ArrayList<LicenseSet>();
		return licenses;
	}

	public String getXML(){

		String r = "";
		//r = r.concat("<app>");
		r = r.concat("<name>"+name+"</name>");
		r = r.concat("<version>"+version.getXML()+"</version>");
		r = r.concat("<appId>"+appID+"</appId>");
		r = r.concat("<description>"+description+"</description>");
		r = r.concat("<multipart>"+multipart+"</multipart>");
		r = r.concat("<tags>"+tags+"</tags>");
		r = r.concat("<applicationProvider>"+applicationProvider.getXML()+"</applicationProvider>");
		for(int i = 0; i < getLicenses().size(); i++)
			r = r.concat(licenses.get(i).getXML());
		r = r.concat("<applicationProfile>"+applicationProfile+"</applicationProfile>");
		//r = r.concat("</app>");

		return r;
	}
}