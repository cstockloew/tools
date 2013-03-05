package org.universAAL.ucc.model;

import java.util.ArrayList;
import org.universAAL.ucc.model.install.License;

public class AALService {
	private int minor = 0;
	private int major = 0;
	private int micro = 0;
	private String description = "";
	private String name = "";
	private String provider = "";
	private ArrayList<String>tags;
	private License licenses;
	
	public AALService() {
		tags = new ArrayList<String>();
	}
	
	public AALService(int minor, int major, int micro, String description, String name, String provider, ArrayList<String>tags,
			License licenses) {
		this.minor = minor;
		this.major = major;
		this.micro = micro;
		this.description = description;
		this.name = name;
		this.tags = tags;
		this.licenses = licenses;
		this.provider = provider;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMicro() {
		return micro;
	}

	public void setMicro(int micro) {
		this.micro = micro;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public License getLicenses() {
		return licenses;
	}

	public void setLicenses(License licenses) {
		this.licenses = licenses;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	

}
