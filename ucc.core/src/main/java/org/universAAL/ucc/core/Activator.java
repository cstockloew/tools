package org.universAAL.ucc.core;

import java.util.Properties;

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
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;
import org.universAAL.middleware.util.Constants;
import org.universAAL.middleware.util.LogUtils;
import org.universAAL.middleware.util.ResourceComparator;
import org.universAAL.ucc.model.api.IModel;

/**
 * The uCCCore is the connection to the OSGi Framework and therefore need to have
 * an Activator. Other part of the uCC (View and Plug-Ins) not necessary need to
 * be valid bundles and maybe can get there information from the core (if needed).
 * @author amarinc
 * @version 1.0
 * @updated 11-Jul-2011 16:37:33
 */
public class Activator implements BundleActivator {
	
	private static IModel model = null;
	
	private static BundleContext context = null;
	private static MessageContentSerializer contentSerializer = null;
	
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
	
	public void start(BundleContext arg0) throws Exception {
		context = arg0;
		Activator.testForm();
		
		ServiceReference sr = context.getServiceReference(IModel.class.getName());
		if (sr == null)
			return;
		
		Activator.model = (IModel) context.getService(sr);
		
		Activator.model.getApplicationRegistration().registerApplicaton("testApp");
	}

	public void stop(BundleContext arg0) throws Exception {
		
	}

}