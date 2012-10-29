/*
	Copyright 2007-2014 FZI, http://www.fzi.de
	Forschungszentrum Informatik - Information Process Engineering (IPE)

	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
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

package org.universAAL.ucc.core;

import java.io.File;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;

import org.universAAL.ucc.api.core.IConfigurator;
import org.universAAL.ucc.api.core.IDeinstaller;
import org.universAAL.ucc.api.core.IInformation;
import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.api.model.IModel;
import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.core.configuration.Configurator;
import org.universAAL.ucc.core.information.Information;
import org.universAAL.ucc.core.installation.Deinstaller;
import org.universAAL.ucc.core.installation.Installer;
import org.universAAL.ucc.core.installation.SocketListener;

/**
 * The uCCCore is the connection to the OSGi Framework and therefore need to have
 * an Activator. Other part of the uCC (View and Plug-Ins) not necessary need to
 * be valid bundles and maybe can get there information from the core (if needed).
 * @author amarinc
 * @author tzentek - <a href="mailto:zentek@fzi.de">Tom Zentek</a>
 * @version 1.0
 * @updated 27-Okt-2012
 */
public class Activator implements BundleActivator {
	private static BundleContext context = null;
	private static int max_retry = 10;
	
	
	private static MessageContentSerializer contentSerializer = null;
	
	private static IModel model = null;
	private static IMainWindow mainWindow = null;
	
	private IInstaller installer = null;
	private IDeinstaller deinstaller = null;
	private static IInformation information = null;
	private IConfigurator configurator = null;
	
	private SocketListener socketListener = null;
	
	public static IModel getModel() {
		return model;
	}
	public static IInformation getInformation(){
		return information;
	}
	public static IMainWindow getMainWindow(){
		if(mainWindow==null){
			try {
				Activator.mainWindow = (IMainWindow) getServiceObject(context, IMainWindow.class.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return mainWindow;
	}
	/*public static synchronized void testForm() {
		//if (Constants.debugMode()) {
			if (contentSerializer == null) {
				ServiceReference sr = context.getServiceReference(MessageContentSerializer.class.getName());
				if (sr == null)
					return;
				
				contentSerializer = (MessageContentSerializer) context.getService(sr);
			}

			Form f = Form.newDialog("TestForm", (String)null);
			Group controls = f.getIOControls();
			Group submits = f.getSubmits();
			
			new InputField(controls,new Label("Your Adress:",(String)null),new PropertyPath(null,false,new String[]{"http://myinput#InputField1"}),null,"");
			new InputField(controls,new Label("Your Phone-Number:",(String)null),new PropertyPath(null,false,new String[]{"http://myinput#InputField2"}),null,"");
			Select1 ms1=new Select1(controls,new Label("Enable SMS Messages:",(String)null),new PropertyPath(null, false, new String[]{"http://myinput#MySelect"}),null,"");
			ms1.generateChoices(new String[]{"Yes", "No"});
			new TextArea(controls,new Label("SMS message text (optional)",(String)null),new PropertyPath(null,false,new String[]{"http://myinput#MyTextArea"}),null,"");
			
			Label labelBoton = new Label("OK",null);
			new Submit(submits,labelBoton,"OK_TEST");
			
			String str = contentSerializer.serialize(f);
			System.out.println();
			System.out.println(str);
			System.out.println();
		//}
	}*/
	
	
	public void start(final BundleContext context) throws Exception {
		Activator.context = context;
		
		//Activator.loadPlugins();
		
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Activator.model = (IModel) getServiceObject(context, IModel.class.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (Activator.model == null) {
					System.err.println("Do not find uCC Model --> uCC Core not started");
					return;
				}
//				if (Activator.mainWindow == null) {
//					System.err.println("Do not find uCC MainWindow --> uCC Core not started");
//					return;
//				}
				
				installer = new Installer(context);
				deinstaller = new Deinstaller(context);
				information = new Information(context);
				configurator = new Configurator(context);
				
				socketListener=new SocketListener(installer,context);
				socketListener.startListening();
				
				
				
				context.registerService(new String[] { IInstaller.class.getName() }, installer, null);
				context.registerService(new String[] { IDeinstaller.class.getName() }, deinstaller, null);
				context.registerService(new String[] { IInformation.class.getName() }, information, null);
				context.registerService(new String[] { IConfigurator.class.getName() }, configurator, null);	
			}
		}).start();
		
	}

	public void stop(BundleContext arg0) throws Exception {
		socketListener.stopListening();
	}
	
	private static Object getServiceObject(BundleContext context, String name) throws Exception {
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