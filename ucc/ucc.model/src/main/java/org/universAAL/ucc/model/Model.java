package org.universAAL.ucc.model;

import org.universAAL.ucc.api.model.IApplicationRegistration;
import org.universAAL.ucc.api.model.IModel;

public class Model implements IModel {

	private IApplicationRegistration appReg;
	
	public Model() {
		appReg = (IApplicationRegistration)(new ApplicationRegistration());
	}

	public IApplicationRegistration getApplicationRegistration() {
		return appReg;
	}

}
