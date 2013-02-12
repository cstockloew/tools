package org.universAAL.ucc.deploymanagerservice.impl;

import java.util.Dictionary;
import java.util.Hashtable;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ucc.api.core.IConfigurator;
import org.universAAL.ucc.api.core.IDeinstaller;
import org.universAAL.ucc.api.core.IInformation;
import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.api.model.IModel;
import org.universAAL.ucc.deploymanagerservice.DeployManagerService;

public class Activator implements BundleActivator {
    private ServiceRegistration registration;
    private static IInstaller installer = null;
    private static BundleContext context;

    public void start(BundleContext bc) throws Exception {
    	context = bc;
        Dictionary props = new Hashtable();

        props.put("service.exported.interfaces", "*");
        props.put("service.exported.configs", "org.apache.cxf.ws");
        props.put("org.apache.cxf.ws.address", "http://localhost:9090/deploymanager");
        
        registration = bc.registerService(DeployManagerService.class.getName(), 
                                          new DeployManagerServiceImpl(), props);
        
        getServices(context);
    }

    public void stop(BundleContext bc) throws Exception {
        registration.unregister();
    }
    
    private static void getServices(BundleContext bc) {
		if (installer == null) {
			ServiceReference sr = bc.getServiceReference(IInstaller.class
					.getName());
			if (sr != null)
				installer = (IInstaller) bc.getService(sr);
			
		}
	}
    
    public static IInstaller getInstaller() {
    	if (installer==null) getServices(context);
    	return installer;
    }
}
