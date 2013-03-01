package org.universaal.tools.packaging.tool.parts;

import org.universaal.tools.packaging.api.Page;


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

		//		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
		//		  <modelVersion>4.0.0</modelVersion>
		//
		//		  
		//		  <?xml version="1.0" encoding="UTF-8"?>
		//		  <uapp:aal-uapp 
		//		  	xmlns:uapp="http://www.venstar.de" 
		//		  	xmlns="http://karaf.apache.org/xmlns/features/v1.0.0"
		//		  	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
		//		  	 xsi:schemaLocation="http://www.venstar.de AAL-UAPP.xsd">
		//		  <!--	xsi:schemaLocation="http://universaal.org/aal-uapp/v1.0.0 AAL-UAPP.xsd"-->

		r = r.concat("<?xml version='1.0' encoding='UTF-8'?><aal-uapp xmlns='http://universaal.org/aal-uapp/v1.0.0' xmlns:"+Page.KARAF_NAMESPACE+"='http://karaf.apache.org/xmlns/features/v1.0.0' " +
				"xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://universaal.org/aal-uapp/v1.0.0 AAL-UAPP.xsd'>");

		r = r.concat(application.getXML());
		
		r = r.concat("</aal-uapp>");

		return r;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
}