package org.universaal.tools.packaging.tool.parts;


public class MPA {

	private Application application;

	public MPA(){
		application = new Application();
	}

	public String getXML(){

		String r = "";
		//TODO header xml
		//<mpa:aal-mpa xmlns:mpa="http://universaal.org/aal-mpa/v1.0.0"
		//xmlns="http://karaf.apache.org/xmlns/features/v1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		//		xsi:schemaLocation="http://universaal.org/aal-mpa/v1.0.0 AAL-MPA.xsd ">

		r = r.concat("<aal-uapp>"+application.getXML()+"</aal-uapp>");

		return r;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
}