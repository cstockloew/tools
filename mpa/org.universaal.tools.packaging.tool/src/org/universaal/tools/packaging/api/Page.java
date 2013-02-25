package org.universaal.tools.packaging.api;

import org.universaal.tools.packaging.tool.parts.MPA;

public interface Page {

	public final String PAGE1 = "Application details #";
	public final String PAGE2 = "Contacts #";
	public final String PAGE3 = "Application capabilities #";
	public final String PAGE4 = "Application requirements #";
	public final String PAGE5 = "Application management #";
	public final String PAGE_LICENSE = "SLA and licenses #";
	public final String PAGE_PART = "Part #";
	public final String PAGE_ERROR = "Error";

	public final String KARAF_NAMESPACE = "krf";

	public void setMPA(MPA mpa);

	public boolean validate();
}