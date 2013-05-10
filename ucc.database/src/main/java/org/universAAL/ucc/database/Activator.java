package org.universAAL.ucc.database;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXB;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.profile.AALSpace;
import org.universAAL.ontology.profile.AALSpaceProfile;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ontology.profile.userid.UserIDProfile;
//import org.universAAL.ontology.profile.User;
//import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ucc.database.aalspace.DataAccess;
import org.universAAL.ucc.database.aalspace.DataAccessImpl;
import org.universAAL.ucc.database.listener.interfaces.OntologySupplierService;
import org.universAAL.ucc.database.listener.services.OntologySupplierServiceImpl;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.database.preferences.impl.UserAccountDBImpl;
import org.universAAL.ucc.model.jaxb.BooleanValue;
import org.universAAL.ucc.model.jaxb.CalendarValue;
import org.universAAL.ucc.model.jaxb.CollectionValues;
import org.universAAL.ucc.model.jaxb.DoubleValue;
import org.universAAL.ucc.model.jaxb.EnumObject;
import org.universAAL.ucc.model.jaxb.IntegerValue;
import org.universAAL.ucc.model.jaxb.OntologyInstance;
import org.universAAL.ucc.model.jaxb.SimpleObject;
import org.universAAL.ucc.model.jaxb.StringValue;
import org.universAAL.ucc.model.jaxb.Subprofile;
import org.universAAL.ucc.model.preferences.Preferences;
import org.universAAL.ucc.profile.agent.ProfileAgent;
import org.universAAL.ucc.startup.api.Setup;
import org.universAAL.ucc.startup.api.impl.SetupImpl;
import org.universAAL.ucc.startup.model.Role;
import org.universAAL.ucc.startup.model.UccUsers;
import org.universAAL.ucc.startup.model.UserAccountInfo;

public class Activator implements BundleActivator {

    private static BundleContext context;
    private ServiceRegistration reg;
    private ServiceRegistration registry;
    private static ProfileAgent pAgent;
    private final static String userURI = "EmptyUser";
    private final static String uProfileURI = "EmptyUserProfile";
    private final static String subProfURI = "User Identification";
    private static ServiceReference pRef;
    public static final String USER_SPACE = "urn:org.universAAL.aal_space:user_env#";
    public static final String HOME_SPACE = "urn:org.universAAL.aal.space:home_env#my_home_space";
    public static final String DEVICE_SPACE = "urn:org.universAAL.aal.space:home_env#";
    
    static BundleContext getContext() {
	return context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext bundleContext) throws Exception {
	Activator.context = bundleContext;
	File file = new File("file:///../etc/uCC");
	if (!file.exists()) {
	    file.mkdir();
	}

	File uf = new File("file:///../etc/uCC/users.xml");
	if(!uf.exists()) {
		uf.createNewFile();
		UserAccountInfo u = new UserAccountInfo();
		u.setName("");
		u.setPassword("");
		List<Role>roles = new ArrayList<Role>();
		roles.add(Role.ENDUSER);
		u.setRole(roles);
		u.setChecked(false);
		Setup set = new SetupImpl();
		List<UserAccountInfo>users = new ArrayList<UserAccountInfo>();
		users.add(u);
		set.saveUsers(users, "file:///../etc/uCC/users.xml");
	}

	reg = context.registerService(Setup.class.getName(), new SetupImpl(), null);
	reg = context.registerService(DataAccess.class.getName(), new DataAccessImpl(), null);
	reg = context.registerService(OntologySupplierService.class.getName(), new OntologySupplierServiceImpl(), null);
	
	//connection to profile agent and setting an empty user from xml definition
	System.err.println("Before profileagent");
	pRef = context.getServiceReference(ProfileAgent.class.getName());
	pAgent = (ProfileAgent)context.getService(pRef);
	createEmptyUser();
//	createEmptyDevice();
	
    }
    
    private void createEmptyUser() {
    	User enduser = new User(USER_SPACE+"User");
    	enduser.setProperty(USER_SPACE+"username", "");
    	enduser.setProperty(USER_SPACE+"password", "");
//    	enduser.setProperty(USER_SPACE+"confirmpassword", "");
    	enduser.setProperty(USER_SPACE+"userRole", new String("ENDUSER"));
    	
    	User deployer = new User(USER_SPACE+"Deployer");
    	deployer.setProperty(USER_SPACE+"username", "");
    	deployer.setProperty(USER_SPACE+"password", "");
//    	deployer.setProperty(USER_SPACE+"confirmpassword", "");
    	deployer.setProperty(USER_SPACE+"userRole", "DEPLOYER");
    	
    	User tec = new User(USER_SPACE+"Technician");
    	tec.setProperty(USER_SPACE+"username", "");
    	tec.setProperty(USER_SPACE+"password", "");
//    	tec.setProperty(USER_SPACE+"confirmpassword", "");
    	tec.setProperty(USER_SPACE+"userRole", "TECHNICIAN");
    	
    	User care = new User(USER_SPACE+"Caregiver");
    	care.setProperty(USER_SPACE+"username", "");
    	care.setProperty(USER_SPACE+"password", "");
//    	care.setProperty(USER_SPACE+"confirmpassword", "");
    	care.setProperty(USER_SPACE+"userRole", "CAREGIVER");
    	
    	User assisted = new User(USER_SPACE+"AssistedPerson");
    	assisted.setProperty(USER_SPACE+"username", "");
    	assisted.setProperty(USER_SPACE+"password", "");
//    	assisted.setProperty(USER_SPACE+"confirmpassword", "");
    	assisted.setProperty(USER_SPACE+"userRole", "ASSISTEDPERSON");
    
    	pAgent.addUser(enduser);
    	pAgent.addUser(deployer);
    	pAgent.addUser(tec);
    	pAgent.addUser(care);
    	pAgent.addUser(assisted);
    	
    }
    
    private void createEmptyDevice() {
    	AALSpace mySpace = null;
    	List<AALSpace> spaces = pAgent.getSpaces();
    	if(spaces == null) {
    		mySpace = new AALSpace(HOME_SPACE);
    		pAgent.addSpace(mySpace);
    	}
    	Device dev = new Device(DEVICE_SPACE+"Device");
    	dev.setProperty(DEVICE_SPACE+"deviceName", "Contact_Sensor");
    	dev.setProperty(DEVICE_SPACE+"room", "Kitchen");
    	dev.setProperty(DEVICE_SPACE+"deviceType", "FS20_Sensor");
    	dev.setProperty(DEVICE_SPACE+"deviceId", "");
    	dev.setProperty(DEVICE_SPACE+"hardwareSettingTime", new Date());
    	dev.setProperty(DEVICE_SPACE+"lastActivityTime", new Date());
    	pAgent.addDevice(dev, mySpace);
    	
    	Device dev2 = new Device(DEVICE_SPACE+"Motion_Sensor");
    	dev2.setProperty(DEVICE_SPACE+"deviceName", "Motion Sensor");
    	dev2.setProperty(DEVICE_SPACE+"room", "Bath");
    	dev2.setProperty(DEVICE_SPACE+"deviceType", "FS20_Actuator");
    	dev2.setProperty(DEVICE_SPACE+"deviceId", "");
    	dev2.setProperty(DEVICE_SPACE+"hardwareSettingTime", new Date());
    	dev2.setProperty(DEVICE_SPACE+"lastActivityTime", new Date());
    	pAgent.addDevice(dev2, mySpace);
    	
    	Device dev3 = new Device(DEVICE_SPACE+"OnOff_Actuator");
    	dev3.setProperty(DEVICE_SPACE+"deviceName", "On/Off_Actuator");
    	dev3.setProperty(DEVICE_SPACE+"room", "Dining_Room");
    	dev3.setProperty(DEVICE_SPACE+"deviceType", "FS20_Actuator");
    	dev3.setProperty(DEVICE_SPACE+"deviceId", "");
    	dev3.setProperty(DEVICE_SPACE+"hardwareSettingTime", new Date());
    	dev3.setProperty(DEVICE_SPACE+"lastActivityTime", new Date());
    	pAgent.addDevice(dev3, mySpace);
    	
    	Device dev4 = new Device(DEVICE_SPACE+"OnOff_Sensor");
    	dev4.setProperty(DEVICE_SPACE+"deviceName", "On/Off_Sensor");
    	dev4.setProperty(DEVICE_SPACE+"room", "Nursery");
    	dev4.setProperty(DEVICE_SPACE+"deviceType", "FS20_Sensor");
    	dev4.setProperty(DEVICE_SPACE+"deviceId", "");
    	dev4.setProperty(DEVICE_SPACE+"hardwareSettingTime", new Date());
    	dev4.setProperty(DEVICE_SPACE+"lastActivityTime", new Date());
    	pAgent.addDevice(dev4, mySpace);
    	
    	Device dev5 = new Device(DEVICE_SPACE+"Plug_PC");
    	dev5.setProperty(DEVICE_SPACE+"deviceName", "Plug_PC");
    	dev5.setProperty(DEVICE_SPACE+"room", "Living_Room");
    	dev5.setProperty(DEVICE_SPACE+"deviceType", "Other_Hardware");
    	dev5.setProperty(DEVICE_SPACE+"deviceId", "");
    	dev5.setProperty(DEVICE_SPACE+"hardwareSettingTime", new Date());
    	dev5.setProperty(DEVICE_SPACE+"lastActivityTime", new Date());
    	pAgent.addDevice(dev5, mySpace);
    	
    	Device dev6 = new Device(DEVICE_SPACE+"Humidity_Sensor");
    	dev6.setProperty(DEVICE_SPACE+"deviceName", "Humidity_Sensor");
    	dev6.setProperty(DEVICE_SPACE+"room", "Storage_Room");
    	dev6.setProperty(DEVICE_SPACE+"deviceType", "FS20_Sensor");
    	dev6.setProperty(DEVICE_SPACE+"deviceId", "");
    	dev6.setProperty(DEVICE_SPACE+"hardwareSettingTime", new Date());
    	dev6.setProperty(DEVICE_SPACE+"lastActivityTime", new Date());
    	pAgent.addDevice(dev6, mySpace);
    	
    	Device dev7 = new Device(DEVICE_SPACE+"Temperature");
    	dev7.setProperty(DEVICE_SPACE+"deviceName", "Tempearture");
    	dev7.setProperty(DEVICE_SPACE+"room", "Bed_Room");
    	dev7.setProperty(DEVICE_SPACE+"deviceType", "FS20_Sensor");
    	dev7.setProperty(DEVICE_SPACE+"deviceId", "");
    	dev7.setProperty(DEVICE_SPACE+"hardwareSettingTime", new Date());
    	dev7.setProperty(DEVICE_SPACE+"lastActivityTime", new Date());
    	pAgent.addDevice(dev7, mySpace);
    	
    	Device dev8 = new Device(DEVICE_SPACE+"Gesture_Sensor");
    	dev8.setProperty(DEVICE_SPACE+"deviceName", "Gesture_Sensor");
    	dev8.setProperty(DEVICE_SPACE+"room", "Corridor");
    	dev8.setProperty(DEVICE_SPACE+"deviceType", "FS20_Sensor");
    	dev8.setProperty(DEVICE_SPACE+"deviceId", "");
    	dev8.setProperty(DEVICE_SPACE+"hardwareSettingTime", new Date());
    	dev8.setProperty(DEVICE_SPACE+"lastActivityTime", new Date());
    	pAgent.addDevice(dev8, mySpace);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
	Activator.context = null;
	reg.unregister();
//	registry.unregister();
    }

    public static ProfileAgent getProfileAgent() {
		if(pAgent == null) {
			ServiceReference sr = context.getServiceReference(ProfileAgent.class.getName());
			pAgent = (ProfileAgent) context.getService(sr);
		}
		return pAgent;
	}
}
