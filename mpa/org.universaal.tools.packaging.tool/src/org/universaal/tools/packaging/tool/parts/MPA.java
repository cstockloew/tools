package org.universaal.tools.packaging.tool.parts;

import org.universaal.tools.packaging.tool.api.Page;


public class MPA {

	private Application aal_uapp;

	public MPA(){
		aal_uapp = new Application();
	}

	public String getXML(){

		String r = "";

		r = r.concat(Page.HEADER_DESCRIPTOR);

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