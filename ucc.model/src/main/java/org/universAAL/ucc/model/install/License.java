package org.universAAL.ucc.model.install;

import java.io.File;
import java.util.ArrayList;

public class License {
	private String appName;
	private ArrayList<File> license;
	private ArrayList<File> slaList;
	
	public License() { 
		license = new ArrayList<File>();
		slaList = new ArrayList<File>();
	}
	
	public License(String appName, String licenseName) {
		this.appName = appName;
		license = new ArrayList<File>();
		slaList = new ArrayList<File>();
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void addLicense(File licenseFile) {
		license.add(licenseFile);
	}
	
	public void addSlaLicense(File sla) {
		slaList.add(sla);
	}

	public ArrayList<File> getLicense() {
		return license;
	}

	public void setLicense(ArrayList<File> license) {
		this.license = license;
	}

	public ArrayList<File> getSlaList() {
		return slaList;
	}

	public void setSlaList(ArrayList<File> slaList) {
		this.slaList = slaList;
	}
	
	

}
