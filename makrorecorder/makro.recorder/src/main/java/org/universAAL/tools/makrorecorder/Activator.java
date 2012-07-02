package org.universAAL.tools.makrorecorder;

import javax.swing.UIManager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;
import org.universAAL.tools.makrorecorder.makrorecorder.ResourceOrginazer;
import org.universAAL.tools.makrorecorder.makrorecorder.myContextSubscriber;
import org.universAAL.tools.makrorecorder.makrorecorder.myLogger;
import org.universAAL.tools.makrorecorder.makrorecorder.myServiceProvider;
import org.universAAL.tools.makrorecorder.swinggui.MainFrame;



public class Activator implements BundleActivator {

	public static myLogger logger;
	
	public static MainFrame gui;
	public static myServiceProvider myServiceProvider;
	public static MessageContentSerializer contentSerializer;
	public static myContextSubscriber myContextSubscriber;
	public static ResourceOrginazer resourceOrginazer;
	public static Object[] contentSerializerParams = new Object[] { MessageContentSerializer.class.getName() };
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext context) throws Exception {
		UIManager.put("ClassLoader", this.getClass().getClassLoader()); 
		ServiceReference sr = context.getServiceReference(MessageContentSerializer.class.getName());
		final ModuleContext moduleContext = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		if (contentSerializer == null) {
			contentSerializer = (MessageContentSerializer) moduleContext
				.getContainer().fetchSharedObject(moduleContext,
					contentSerializerParams);
			if (contentSerializer == null)
				contentSerializer = (MessageContentSerializer) context.getService(sr);
		    }
		
		new Thread() {
			public void run() {
				logger = new myLogger(context);
				myServiceProvider = new myServiceProvider(moduleContext);
				myContextSubscriber = new myContextSubscriber(moduleContext);
				resourceOrginazer = new ResourceOrginazer();
				gui = new MainFrame();
			}
		}.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
