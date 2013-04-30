package org.universAAL.ucc.database;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXB;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ontology.phThing.Device;
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
    public static final String USER_SPACE = "urn:org.universAAL.aal_space:test_env#";
    
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
	File prefFile = new File("file:///../etc/uCC/preferences.xml");
	UserAccountDB db = new UserAccountDBImpl();
	if (!file.exists()) {
	    file.mkdir();
	}
	if (!prefFile.exists()) {
	    prefFile.createNewFile();
	    Preferences p = new Preferences();
	    if (Locale.getDefault().getLanguage().equals(
		    new Locale("de").getLanguage())) {
		p.setLanguage("de");
	    } else {
		p.setLanguage("en");
	    }
	    p.setPassword("");
	    p.setPassword2("");
	    p.setPort("9988");
	    p
		    .setShopUrl("https://srv-ustore.haifa.il.ibm.com/webapp/wcs/stores/servlet/TopCategories_10001_10001");
	    p.setUsername("");
	    p.setUsername2("");
	    db.saveStoreAccessData(p, "file:///../etc/uCC/preferences.xml");
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
	reg = context.registerService(UserAccountDB.class.getName(),
		new UserAccountDBImpl(), null);
	reg = context.registerService(Setup.class.getName(), new SetupImpl(), null);
	reg = context.registerService(DataAccess.class.getName(), new DataAccessImpl(), null);
	reg = context.registerService(OntologySupplierService.class.getName(), new OntologySupplierServiceImpl(), null);
	
	//connection to profile agent and setting an empty user from xml definition
	System.err.println("Before profileagent");
	pRef = context.getServiceReference(ProfileAgent.class.getName());
	pAgent = (ProfileAgent)context.getService(pRef);
	createEmptyUser();
	createEmptyDevice();
	
    }
    
    private void createEmptyUser() {
    	User user = new User(USER_SPACE+"User");
    	UserProfile uProfile = new UserProfile(USER_SPACE+"UserProfile");
    	
    	pAgent.addUser(user);
    	pAgent.addUserProfile(uProfile);
    	
    	pAgent.addUserProfile(user, uProfile);
    	
    	UserIDProfile userId = new UserIDProfile(USER_SPACE+"UserIdProfile");
    	userId.setPASSWORD("pass");
    	userId.setUSERNAME("nic");
    	userId.setProperty(USER_SPACE+"role", "ENDUSER");
    	pAgent.addSubProfile(userId);
    	
    	//Strange thing, have also to add another Subprofile, UserIDProfile does not work without
    	SubProfile su = new SubProfile(USER_SPACE+"UserIdProfile");
    	su.setProperty(USER_SPACE+"password", "");
    	su.setProperty(USER_SPACE+"username", "");
    	su.setProperty(USER_SPACE+"role", "");
    	
    	pAgent.addSubProfile(su);
    	pAgent.addUserSubprofile(user, su);
    	pAgent.addUserSubprofile(uProfile, su);
    	
    	pAgent.addUserSubprofile(user, userId);
    	pAgent.addUserSubprofile(uProfile, userId);
    	
    	
    	

    	
    	
//    	pAgent.addUserSubprofile(user, userId);
        
    	
    
    	//Create Dummy User from JAXB Structure
//    	DataAccess da = new DataAccessImpl();
    
//    	ArrayList<OntologyInstance>onts = da.getEmptyProfile(System.getenv("systemdrive")+"/uccDB/EmptyUser.xml");
//    	for(OntologyInstance o : onts) {
//    		for(Subprofile sub : o.getSubprofiles()) {
//    			System.err.println(sub.getName());
//    			SubProfile sp = new SubProfile(USER_SPACE+sub.getName().replace(" ", "_").replace("-", "_"));
//    			for(SimpleObject sim : sub.getSimpleObjects()) {
//    				if(sim instanceof StringValue) {
//    					StringValue s = (StringValue)sim;
//    					sp.setProperty(USER_SPACE+s.getName().replace(" ", "_"), s.getValue());
//    					
//    				}
//    				if(sim instanceof IntegerValue) {
//    					IntegerValue in = new IntegerValue();
//    					sp.setProperty(USER_SPACE+in.getName().replace(" ", "_"), in.getValue());
//    				}
//    			}
//    			
//    			pAgent.addUserSubprofile(uProfile, sp);
//    		}
//    	}
//    	user.setProperty(USER_SPACE+"username", "");
//    	user.setProperty(USER_SPACE+"password", "");
//    	user.setProperty(USER_SPACE+"confirmPassword", "");
//    	user.setProperty(USER_SPACE+"role", "");
    	
    }
    
    private void createEmptyDevice() {
    	Device dev = new Device(USER_SPACE+"Device");
    	dev.setProperty(USER_SPACE+"deviceName", "");
    	dev.setProperty(USER_SPACE+"room", "");
    	dev.setProperty(USER_SPACE+"deviceType", "");
    	dev.setProperty(USER_SPACE+"deviceId", "");
    	pAgent.addDevice(dev);
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
