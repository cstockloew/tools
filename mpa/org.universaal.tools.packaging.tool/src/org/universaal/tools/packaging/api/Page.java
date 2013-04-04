package org.universaal.tools.packaging.api;

import org.universaal.tools.packaging.tool.parts.MPA;

public interface Page {

	public final String PAGE_START = "universAAL multipart application packager";
	public final String PAGE1 = "Application details";
	public final String PAGE2 = "Contacts";
	public final String PAGE3 = "Application capabilities";
	public final String PAGE4 = "Application requirements";
	public final String PAGE5 = "Application management";
	public final String PAGE_LICENSE = "SLA and licenses";
	public final String PAGE_PART_DU = "Application Part (Deployment Unit - 1/4): ";
	public final String PAGE_PART_EU = "Application Part (Execution Unit - 2/4): ";
	public final String PAGE_PART_PC = "Application Part (Part Capabilities - 3/4): ";
	public final String PAGE_PART_PR = "Application Part (Part Requirements - 4/4): ";
	public final String PAGE_END = "universAAL multipart application packager";
	
	public final String PAGE_ERROR = "Error Page";
	
	public final String DESCRIPTOR_FILENAME_SUFFIX = "uapp.xml";

	public final String KARAF_NAMESPACE = "krf";

	public void setMPA(MPA mpa);

	public boolean validate();
}