package org.universAAL.ucc.core;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.io.rdf.Group;
import org.universAAL.middleware.io.rdf.InputField;
import org.universAAL.middleware.io.rdf.Label;
import org.universAAL.middleware.io.rdf.Select1;
import org.universAAL.middleware.io.rdf.Submit;
import org.universAAL.middleware.io.rdf.TextArea;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;

import org.universAAL.ucc.api.core.IConfigurator;
import org.universAAL.ucc.api.core.IDeinstaller;
import org.universAAL.ucc.api.core.IInformation;
import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.api.model.IModel;
import org.universAAL.ucc.core.configuration.Configurator;
import org.universAAL.ucc.core.information.Information;
import org.universAAL.ucc.core.installation.Deinstaller;
import org.universAAL.ucc.core.installation.Installer;

/**
 * The uCCCore is the connection to the OSGi Framework and therefore need to have
 * an Activator. Other part of the uCC (View and Plug-Ins) not necessary need to
 * be valid bundles and maybe can get there information from the core (if needed).
 * @author amarinc
 * @version 1.0
 * @updated 11-Jul-2011 16:37:33
 */
public class Activator implements BundleActivator {
	private static BundleContext context = null;
	private static String rundir = "c:"+File.separator;
	private static int max_retry = 10;
	
	private static MessageContentSerializer contentSerializer = null;
	
	private static IModel model = null;
	
	private IInstaller installer = null;
	private IDeinstaller deinstaller = null;
	private IInformation information = null;
	private IConfigurator configurator = null;
	
	public static IModel getModel() {
		return model;
	}
	
	public static synchronized void testForm() {
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
	}
	
	public static String getRundir() {
		return rundir;
	}
	
	public void start(final BundleContext context) throws Exception {
		Activator.context = context;
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
				String bundlePath = Activator.context.getBundle().getLocation();
				Activator.rundir = bundlePath.substring(bundlePath.indexOf("/")+1,bundlePath.lastIndexOf("/"));
				Activator.rundir= Activator.rundir.replace("/", "\\");
				
				installer = new Installer(context);
				deinstaller = new Deinstaller(context);
				information = new Information(context);
				configurator = new Configurator(context);
				
				context.registerService(new String[] { IInstaller.class.getName() }, installer, null);
				context.registerService(new String[] { IDeinstaller.class.getName() }, deinstaller, null);
				context.registerService(new String[] { IInformation.class.getName() }, information, null);
				context.registerService(new String[] { IConfigurator.class.getName() }, configurator, null);	
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