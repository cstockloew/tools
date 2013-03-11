package org.universAAL.ucc.service.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ucc.frontend.api.IWindow;
import org.universAAL.ucc.frontend.api.impl.InstallProcessImpl;

public class Activator implements BundleActivator {
	private ServiceRegistration reg;

	@Override
	public void start(BundleContext context) throws Exception {
		reg = context.registerService(IWindow.class.getName(), new InstallProcessImpl(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		reg.unregister();
		
	}

}
