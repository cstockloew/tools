package org.universAAL.ucc.model;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.universAAL.ucc.api.model.IApplicationManagment;
import org.universAAL.ucc.api.model.IApplicationRegistration;
import org.universAAL.ucc.api.model.IModel;
import org.w3c.dom.Document;

public class Model implements IModel {

	private IApplicationRegistration appReg;
	private IApplicationManagment appMan;
	public final static String FILENAME = "applications.xml";
	
	public Model() {
		appReg = (IApplicationRegistration)(new ApplicationRegistration());
		appMan = (IApplicationManagment) (new ApplicationManagment());
	}

	public IApplicationRegistration getApplicationRegistration() {
		return appReg;
	}
	
	public IApplicationManagment getApplicationManagment() {
		return appMan;
	}
	
	static protected Document getDocument(){
		File file = new File(FILENAME);
		try {
			if(file.exists()){
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db;
				db = dbf.newDocumentBuilder();
				return db.parse(file);
			}else{
				return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}

		} catch (Exception e) {
			return null;
		}
	}

}
