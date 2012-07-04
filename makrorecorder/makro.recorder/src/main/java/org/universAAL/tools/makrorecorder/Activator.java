/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
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

/**
 * 
 * @author Alexander Marinc, Mark Prediger
 *
 */
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
