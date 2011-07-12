package org.universAAL.ucc.model;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.ucc.model.api.IModel;
import org.universAAL.ucc.model.impl.Model;

public class Activator implements BundleActivator{
	public static BundleContext context=null;
	
	private static IModel model;

	public void start(BundleContext context) throws Exception {
		Activator.context=context;
		
		model = new Model();
		
		context.registerService(new String[] { IModel.class.getName() }, model, null);
	}

	public void stop(BundleContext arg0) throws Exception {
		
	}
	
	public IModel getModel() {
		return model;
	}

}
