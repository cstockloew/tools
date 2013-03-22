package org.universAAL.ucc.model;

import java.util.ArrayList;

import org.universAAL.middleware.interfaces.mpa.model.Part;



public class UAPP {
	private String name;
	private String appId;
	private String uappLocation;
	private int minor;
	private int major;
	private int micro;
	private String description;
	private boolean multipart;
	private Part part;
	
	public UAPP() { }
	
	public UAPP(String appId, String name, String location, int major, int minor, int micro, String description, boolean multipart, Part part) {
		this.appId = appId;
		this.name = name;
		this.uappLocation = location;
		this.description = description;
		this.major = major;
		this.micro = micro;
		this.minor = minor;
		this.multipart = multipart;
		this.part = part;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUappLocation() {
		return uappLocation;
	}
	public void setUappLocation(String uappLocation) {
		this.uappLocation = uappLocation;
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

	public boolean isMultipart() {
		return multipart;
	}

	public void setMultipart(boolean multipart) {
		this.multipart = multipart;
	}
	
	public String getAppId()  {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}
	
	

}
