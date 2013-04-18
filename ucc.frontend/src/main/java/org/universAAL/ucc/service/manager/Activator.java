package org.universAAL.ucc.service.manager;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.ucc.api.IDeinstaller;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.configuration.configdefinitionregistry.interfaces.ConfigurationDefinitionRegistry;
import org.universAAL.ucc.frontend.api.IFrontend;
import org.universAAL.ucc.frontend.api.impl.FrontendImpl;
import org.universAAL.ucc.service.api.IServiceManagement;
import org.universAAL.ucc.service.api.IServiceModel;
import org.universAAL.ucc.service.api.IServiceRegistration;
import org.universAAL.ucc.service.impl.Model;
import org.universAAL.ucc.webconnection.WebConnector;
import org.universAAL.ucc.subscriber.SensorEventSubscriber;


public class Activator implements BundleActivator {
	private static IInstaller installer;
	private static IDeinstaller deinstaller;
	private static ServiceReference ref;
	private static ServiceReference dRef;
	private static BundleContext bc;
	private static ServiceRegistration regis;
	private static IServiceManagement mgmt;
	private static IServiceModel model;
	private static IServiceRegistration reg;
	private ModuleContext mContext;

	public void start(BundleContext context) throws Exception {
		Activator.bc = context;
		ref = context.getServiceReference(IInstaller.class.getName());
		installer = (IInstaller) context.getService(ref);
		// Later uncomment, when Deinstaller is implemented in the
		// ucc.controller
		dRef = context.getServiceReference(IDeinstaller.class.getName());
		deinstaller = (IDeinstaller) context.getService(dRef);

		regis = bc.registerService(IFrontend.class.getName(),
				new FrontendImpl(), null);

		model = new Model();
		context.registerService(new String[] { IServiceModel.class.getName() },
				model, null);
		mgmt = model.getServiceManagment();
		reg = model.getServiceRegistration();
		
		mContext = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		SensorEventSubscriber sub = SensorEventSubscriber.getInstance(mContext, context);

	}

	public static IInstaller getInstaller() {
		if (installer == null) {
			installer = (IInstaller) bc.getService(ref);
		}
		return installer;
	}

	public static IDeinstaller getDeinstaller() {
		if (deinstaller == null) {
			deinstaller = (IDeinstaller) bc.getService(dRef);
		}
		return deinstaller;
	}

	public static IServiceManagement getMgmt() {
		return mgmt;
	}

	public static void setMgmt(IServiceManagement mgmt) {
		Activator.mgmt = mgmt;
	}

	public static IServiceModel getModel() {
		return model;
	}

	public static void setModel(IServiceModel model) {
		Activator.model = model;
	}

	public static IServiceRegistration getReg() {
		return reg;
	}

	public static void setReg(IServiceRegistration reg) {
		Activator.reg = reg;
	}

	public void stop(BundleContext context) throws Exception {
		context.ungetService(ref);
		context.ungetService(dRef);
		regis.unregister();
		File file = new File(System.getenv("systemdrive") + "/tempUsrvFiles/");
		deleteFiles(file);
		WebConnector.getInstance().stopListening();
	}

	private void deleteFiles(File path) {
		File[] files = path.listFiles();
		for (File del : files) {
			if (del.isDirectory()) {
				deleteFiles(del);
			}
			if (!del.getPath().substring(del.getPath().indexOf(".") + 1)
					.equals("usrv"))
				del.delete();
		}

	}

}
