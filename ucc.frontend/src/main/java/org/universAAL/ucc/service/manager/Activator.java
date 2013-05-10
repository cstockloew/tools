package org.universAAL.ucc.service.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Properties;

import javax.xml.bind.JAXB;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.ucc.api.IDeinstaller;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.client.util.UstoreUtil;
import org.universAAL.ucc.configuration.configdefinitionregistry.interfaces.ConfigurationDefinitionRegistry;
import org.universAAL.ucc.database.aalspace.DataAccess;
import org.universAAL.ucc.frontend.api.IFrontend;
import org.universAAL.ucc.frontend.api.impl.FrontendImpl;
import org.universAAL.ucc.model.jaxb.EnumObject;
import org.universAAL.ucc.model.jaxb.OntologyInstance;
import org.universAAL.ucc.model.jaxb.StringValue;
import org.universAAL.ucc.model.jaxb.Subprofile;
import org.universAAL.ucc.profile.agent.ProfileAgent;
import org.universAAL.ucc.service.api.IServiceManagement;
import org.universAAL.ucc.service.api.IServiceModel;
import org.universAAL.ucc.service.api.IServiceRegistration;
import org.universAAL.ucc.service.impl.Model;
import org.universAAL.ucc.webconnection.WebConnector;
import org.universAAL.ucc.startup.model.UccUsers;
import org.universAAL.ucc.subscriber.SensorEventSubscriber;

import com.vaadin.ui.Window.Notification;

//import de.fzi.ipe.evaluation.api.core.IEvaluationEventReceiver;


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
	private static String sessionKey;
	private UstoreUtil client;
	private static DataAccess dataAccess;
//	private IEvaluationEventReceiver eventReceiver;

	public void start(BundleContext context) throws Exception {
		Activator.bc = context;
		client = new UstoreUtil();
		ServiceReference ref = bc.getServiceReference(DataAccess.class.getName());
		dataAccess = (DataAccess)bc.getService(ref);
		//Setting setup properties in etc/ucc directory
		File confHome = new File("file:///../etc/uCC");
		if(!confHome.exists()) {
			confHome.mkdir();
		}
		File temp = new File("file:///../etc/uCC/setup.properties");
		if(!temp.exists()) {
			//Setting default values for setup configuration
			Properties prop = new Properties();	
			prop.setProperty("admin", "admin");
			prop.setProperty("pwd", "uAAL");
			prop.setProperty("storePort", "9090");
			prop.setProperty("uccPort", "8080");
			prop.setProperty("uccUrl", "ucc-universaal.no-ip.org");
			prop.setProperty("shopUrl", "srv-ustore.haifa.il.ibm.com/webapp/wcs/stores/servlet/TopCategories_10001_10001");
			if(Locale.getDefault() == Locale.GERMAN) {
				System.err.println(Locale.getDefault());
				prop.setProperty("lang", "de");
			} else {
				prop.setProperty("lang", "en");
			}
			Writer in = new FileWriter(new File(confHome, "setup.properties"));
			prop.store(in, "Setup properties for initial setup of uCC");
			in.close();
		}
		
		//Write Techician/Deployer into AALSpace
		OntologyInstance ont = new OntologyInstance();
		ont.setId("Admin");
		ont.setType("User");
		Subprofile sub = new Subprofile();
		StringValue name = new StringValue();
		name.setId(false);
		name.setLabel("Username:");
		name.setName("username");
		name.setRequired(true);
		name.setValue("admin");
		sub.getSimpleObjects().add(name);
		
		StringValue pass = new StringValue();
		pass.setId(false);
		pass.setLabel("Password:");
		pass.setName("password");
		pass.setRequired(true);
		pass.setValue("uAAL");
		sub.getSimpleObjects().add(pass);
		
		EnumObject role = new EnumObject();
		role.setLabel("Role:");
		role.setRequired(true);
		role.setSelectedValue("DEPLOYER");
		role.setTreeParentNode(true);
		role.setType("userRole");
		sub.getEnums().add(role);
		ont.getSubprofiles().add(sub);
		dataAccess.saveUserDataInCHE(ont);
		
		File file = new File(System.getenv("systemdrive") + "/tempUsrvFiles/");
		if(!file.exists()) {
			file.mkdir();
		}
		
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
		SensorEventSubscriber ses = SensorEventSubscriber.getInstance(mContext, context);
		
//		ServiceReference ref = context.getServiceReference(IEvaluationEventReceiver.class.getName());
//		eventReceiver = (IEvaluationEventReceiver)context.getService(ref);
		
		//Get SessionKey from uStore
		sessionKey = client.getSessionKey();
		if (sessionKey == null || sessionKey.equals("")) {
			System.err.println("No Session key when trying to setup connection to uStore");

		} else {
			System.err.println("WS-ANSWER: " + sessionKey);
			client.registerUser(sessionKey);
		}
		
	}
	
	

	public static String getSessionKey() {
		return sessionKey;
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



	public static DataAccess getDataAccess() {
		return dataAccess;
	}
	
	

}
