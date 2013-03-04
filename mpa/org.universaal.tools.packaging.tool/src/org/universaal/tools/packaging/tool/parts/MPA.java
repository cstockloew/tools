package org.universaal.tools.packaging.tool.parts;

import org.universaal.tools.packaging.api.Page;


public class MPA {

	private Application aal_uapp;

	public MPA(){
		aal_uapp = new Application();
	}

	public String getXML(){

		String r = "";

		r = r.concat("<?xml version='1.0' encoding='UTF-8'?>" +
				"<aal-uapp xmlns='http://universaal.org/aal-uapp/v1.0.0' " +
				"xmlns:"+Page.KARAF_NAMESPACE+"='http://karaf.apache.org/xmlns/features/v1.0.0' " +
				"xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " +
				"xsi:schemaLocation='http://universaal.org/aal-uapp/v1.0.0 AAL-UAPP.xsd'>");

		r = r.concat(aal_uapp.getXML());
		r = r.concat("</aal-uapp>");

		return r;
	}

	public Application getAAL_UAPP() {
		return aal_uapp;
	}

	public void setAAL_UAPP(Application application) {
		this.aal_uapp = application;
	}
}