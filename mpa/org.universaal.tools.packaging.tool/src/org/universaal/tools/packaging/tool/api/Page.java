package org.universaal.tools.packaging.tool.api;

import org.universaal.tools.packaging.tool.parts.MPA;

public interface Page {

	public final String PAGE_START = "universAAL Application Packager";
	public final String PAGE1 = "Application details";
	public final String PAGE2 = "Contacts";
	public final String PAGE3 = "Application capabilities";
	public final String PAGE4 = "Application requirements";
	public final String PAGE5 = "Application management";
	public final String PAGE_DU = "Deployment Unit";
	public final String PAGE_APP_RESOURCES = "Application Resources";
	public final String PAGE_LICENSE = "SLA and licenses";
	public final String PAGE_PART_BUNDLE = "Application Part (Bundle Id and version - 1/5): ";
	public final String PAGE_PART_DU = "Application Part (Deployment Unit - 2/5): ";
	public final String PAGE_PART_EU = "Application Part (Execution Unit - 3/5): ";
	public final String PAGE_PART_PC = "Application Part (Part Capabilities - 4/5): ";
	public final String PAGE_PART_PR = "Application Part (Part Requirements - 5/5): ";
	public final String PAGE_END = "universAAL Application Packager";
	
	public final String KARAF_NAMESPACE = "krf";
	
	//public final String XSD = "'http://www.universaal.org/aal-uapp/v1.0.2'"; // "'http://www.universaal.org/aal-uapp/v1.0.0'" - //"http://www.universaal.org/aal-uapp/v1.0.0/AAL-UAPP.xsd";
	public final String XSD_REPOSITORY = "http://www.universaal.org/aal-uapp/";
	public final String XSD_VERSION = "1.0.2";
	public final String Karaf = "'http://karaf.apache.org/xmlns/features/v1.0.0'";
	public final String w3c = "'http://www.w3.org/2001/XMLSchema'";
	
	public final String HEADER_DESCRIPTOR = "<?xml version='1.0' encoding='UTF-8'?>" +
				"<aal-uapp xmlns='http://www.universaal.org/aal-uapp/v"+XSD_VERSION+"' " +
				"xmlns:"+KARAF_NAMESPACE+"="+Karaf+" " +
				"xmlns:xsi="+w3c+" " +
				"xsi:schemaLocation='http://www.universaal.org/aal-uapp/v"+XSD_VERSION+"'>";
	
	public final String PAGE_ERROR = "Error Page";
	
	public final String DESCRIPTOR_FILENAME_SUFFIX = "uapp.xml";

	public void setMPA(MPA mpa);

	public boolean validate();
}