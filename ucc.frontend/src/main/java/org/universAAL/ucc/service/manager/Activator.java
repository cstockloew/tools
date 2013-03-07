package org.universAAL.ucc.service.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.webconnection.WebConnector;

public class Activator implements BundleActivator {
	private ServiceReference ref;
	private WebConnector webcon;

	@Override
	public void start(BundleContext context) throws Exception {
		ref = context.getServiceReference(IInstaller.class.getName());
		IInstaller installer = (IInstaller)context.getService(ref);
		webcon = new WebConnector(installer);
		webcon.startListening();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		context.ungetService(ref);
		webcon.stopListening();
		
	}

}
