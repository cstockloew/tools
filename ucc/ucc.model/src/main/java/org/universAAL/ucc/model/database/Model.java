package org.universAAL.ucc.model.database;

import org.universAAL.ucc.modelapi.interfaces.IApplicationRegistration;
import org.universAAL.ucc.modelapi.interfaces.IModel;

public class Model implements IModel {
	
	static private IModel model = null;

	private IApplicationRegistration appReg;
	
	public static IModel getModel() {
		if (model == null)
			model = new Model();
		return model;
	}
	
	private Model() {
		appReg = (IApplicationRegistration)(new ApplicationRegistration());
	}

	public IApplicationRegistration getApplicationRegistration() {
		return appReg;
	}

}
