package org.universAAL.ucc.client.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.client.UStoreWSClient;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.model.preferences.Preferences;

public class UstoreUtil {
	private BundleContext bc;
	private ServiceReference ref;
	private UserAccountDB db;
	private Preferences pref;
	
	public UstoreUtil() {
		bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ref = bc.getServiceReference(UserAccountDB.class.getName());
		db = (UserAccountDB) bc.getService(ref);
		pref = db.getPreferencesData(System.getenv("systemdrive")+"/uccDB/preferences.xml");
		bc.ungetService(ref);
	}

	public void registerUser() {
		String usr = pref.getUsername2();
		String pwd = pref.getPassword2();
		String portNum = pref.getPort();
		String idAddr = "";
		
		try {
			idAddr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	
		//TODO Call the Ustore instance and the registerDeployManager(usr, pwd, idAddr, portNum); method with the given parameters
		//and return the return value of the method
	}
	
}
