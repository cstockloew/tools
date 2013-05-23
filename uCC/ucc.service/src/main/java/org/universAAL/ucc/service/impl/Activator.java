package org.universAAL.ucc.service.impl;

import java.util.HashMap;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.ucc.service.api.IServiceModel;
import org.universAAL.ucc.service.api.IServiceManagement;
import org.universAAL.ucc.service.api.IServiceRegistration;

public class Activator implements BundleActivator {
	public static BundleContext context=null;
	
	private static IServiceModel model;

	public void start(BundleContext context) throws Exception {
		Activator.context=context;
		
		model = new Model();
		
		context.registerService(new String[] { IServiceModel.class.getName() }, model, null);
		
		// testing
		IServiceManagement mgmt = model.getServiceManagment();
		IServiceRegistration reg = model.getServiceRegistration();
		// register a service with app 
		//HashMap bundles1 = new HashMap();
		//bundles1.put("bundle1", "0.0.1");		
		//reg.registerAppAndBundles("service1", "appA", bundles1);
		//reg.registerApp("service1", "appA");
		//HashMap bundles2=new HashMap();
		//bundles2.put("bundle2", "0.0.1");
		//reg.registerAppAndBundles("service1", "appB", bundles2);
		reg.registerApp("service1", "appA");
		reg.registerBundle("service1", "bundle1", "0.0.1");
		reg.registerApp("service1", "appB");
		reg.registerBundle("service1", "bundle2", "0.0.1");
		reg.registerApp("service2", "appC");
		reg.registerBundle("service2", "bundle3", "1.0.0");
		reg.registerBundle("service2", "bundle4", "1.0.0");
		//HashMap bundles3=new HashMap();
		//bundles3.put("bundle3", "1.0.0");
		//bundles3.put("bundle4", "1.0.0");
		//reg.registerAppAndBundles("service2", "appC", bundles3);
		
		String services = mgmt.getInstalledServices();
		System.out.println("display - installed services: " + services);
		String bundles = mgmt.getInstalledUnitsForService("service2");
		System.out.println("display - installed bundles: " + bundles);
		List apps = mgmt.getInstalledApps("service1");
		System.out.println("display - installed apps: " + apps);
		reg.unregisterService("service2");
	}

	public void stop(BundleContext arg0) throws Exception {
		
	}
	
	public static IServiceModel getModel() {
		return model;
	}

}
