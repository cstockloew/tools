package org.universAAL.ucc.deploymanagerservice.impl;

import java.util.Dictionary;
import java.util.Hashtable;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ucc.deploymanagerservice.DeployManagerService;

public class Activator implements BundleActivator {
    private ServiceRegistration registration;

    public void start(BundleContext bc) throws Exception {
        Dictionary props = new Hashtable();

        props.put("service.exported.interfaces", "*");
        props.put("service.exported.configs", "org.apache.cxf.ws");
        props.put("org.apache.cxf.ws.address", "http://localhost:9090/deploymanager");
        
        registration = bc.registerService(DeployManagerService.class.getName(), 
                                          new DeployManagerServiceImpl(), props);
    }

    public void stop(BundleContext bc) throws Exception {
        registration.unregister();
    }
}
