package org.universAAL.ucc.aalspace.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ucc.database.aalspace.DataAccess;
import org.universAAL.ucc.database.aalspace.DataAccessImpl;

public class Activator implements BundleActivator {
	private static ServiceRegistration reg;
	private static BundleContext bc;

	public void start(BundleContext context) throws Exception {
		bc = context;
		reg = bc.registerService(DataAccess.class.getName(), new DataAccessImpl(), null);
		
	}

	public void stop(BundleContext context) throws Exception {
		reg.unregister();
		
	}

}
