package org.universAAL.ucc.model.impl;

import org.universAAL.ucc.model.api.IApplicationRegistration;
import org.universAAL.ucc.model.api.IModel;

public class Model implements IModel {

	private IApplicationRegistration appReg;
	
	public Model() {
		appReg = (IApplicationRegistration)(new ApplicationRegistration());
	}

	public IApplicationRegistration getApplicationRegistration() {
		return appReg;
	}

}
