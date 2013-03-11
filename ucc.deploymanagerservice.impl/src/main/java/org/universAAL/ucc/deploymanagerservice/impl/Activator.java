package org.universAAL.ucc.deploymanagerservice.impl;

import java.util.Dictionary;
import java.util.Hashtable;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ucc.api.IDeinstaller;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.deploymanagerservice.DeployManagerService;

public class Activator implements BundleActivator {
    private ServiceRegistration registration;
    private static IInstaller installer = null;
    private static IDeinstaller deinstaller = null;
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
    	System.out.println("[DeployManagerServiceImpl.activator.getServices]");
		if (installer == null) {
			ServiceReference sr = bc.getServiceReference(IInstaller.class
					.getName());
			if (sr != null)
				installer = (IInstaller) bc.getService(sr);		
			else System.out.println("[DeployManagerServiceImpl.activator] service reference for IInstaller is null!");
			if (installer==null) System.out.println("[DeployManagerServiceImpl.activator] can not get installer! ");
	    	else System.out.println("[DeployManagerServiceImpl.activator.getServices] got installer ");
		}
		
		if (deinstaller == null) {
			ServiceReference sr = bc.getServiceReference(IDeinstaller.class
					.getName());
			if (sr != null)
				deinstaller = (IDeinstaller) bc.getService(sr);		
			else System.out.println("[DeployManagerServiceImpl.activator] service reference for IDeinstaller is null!");
			if (deinstaller==null) System.out.println("[DeployManagerServiceImpl.activator] can not get deinstaller! ");
	    	else System.out.println("[DeployManagerServiceImpl.activator.getServices] got deinstaller ");
		}
		
	}
    
    public static IInstaller getInstaller() {
    	System.out.println("[DeployManagerServiceImpl.activator.getInstaller]");
    	if (installer==null) getServices(context);
    	if (installer==null) System.out.println("[DeployManagerServiceImpl.activator] can not get installer! ");
    	else System.out.println("[DeployManagerServiceImpl.activator.getInstaller] got installer ");
     	return installer;
    }  
    
    public static IDeinstaller getDeinstaller() {
    	System.out.println("[DeployManagerServiceImpl.activator.getDeinstaller]");
    	if (deinstaller==null) getServices(context);
    	if (deinstaller==null) System.out.println("[DeployManagerServiceImpl.activator] can not get deinstaller! ");
    	else System.out.println("[DeployManagerServiceImpl.activator.getDeinstaller] got deinstaller ");
     	return deinstaller;
    }  
}
