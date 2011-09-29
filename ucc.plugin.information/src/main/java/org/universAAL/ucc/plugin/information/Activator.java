package org.universAAL.ucc.plugin.information;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.api.core.IInformation;
import org.universAAL.ucc.api.plugin.IPluginBase;

import com.trolltech.qt.QThread;

public class Activator implements BundleActivator{
	public static BundleContext context=null;
	private static int max_retry = 10;
	
	static UCCPlugin uCCPlugin = null;
	static IPluginBase uCCPluginBase = null;
	static IInformation information = null;
	
	public static IInformation getInformation() {
		return Activator.information;
	}

	public void start(BundleContext context) throws Exception {
		Activator.context=context;
		Activator.uCCPluginBase = (IPluginBase) this.getServiceObject(context, IPluginBase.class.getName());
		Activator.information = (IInformation) this.getServiceObject(context, IInformation.class.getName());
		
		new QThread(new Runnable () {
			public void run() {				
				if (Activator.uCCPluginBase != null && information != null) {
					Activator.uCCPlugin = new UCCPlugin(Activator.uCCPluginBase);
					Activator.uCCPluginBase.registerPlugin(Activator.uCCPlugin);
					System.out.println("Plugin started!");
				}
				else
					System.err.println("Can not start information-view: Do not found all needed OSGi Services!");				
			}
		}).start();
	}

	public void stop(BundleContext arg0) throws Exception {
	}
	
	private Object getServiceObject(BundleContext context, String name) throws Exception {
		ServiceReference sr = null;
		int retry_count = 0;
		while (sr == null && retry_count<max_retry) {
			sr = context.getServiceReference(name);
			if (sr == null)
				Thread.sleep(1000);
			retry_count++;
		}
		if (sr != null)
			return context.getService(sr);
		return null;
	}

}
