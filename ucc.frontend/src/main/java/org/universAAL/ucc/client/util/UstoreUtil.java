package org.universAAL.ucc.client.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.model.preferences.Preferences;
import org.universAAL.ucc.ustore.ws.client.OnlineStoreManager;
import org.universAAL.ucc.ustore.ws.client.OnlineStoreManagerService;
import org.universAAL.ucc.ustore.ws.client.UAALException_Exception;

public class UstoreUtil {
	private BundleContext bc;
	private ServiceReference ref;
	private UserAccountDB db;
	private Preferences pref;
	private OnlineStoreManager osm;
	
	public UstoreUtil() {
		bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ref = bc.getServiceReference(UserAccountDB.class.getName());
		db = (UserAccountDB) bc.getService(ref);
		pref = db.getPreferencesData(System.getenv("systemdrive")+"/uccDB/preferences.xml");
		bc.ungetService(ref);
		//Getting ustore webservice
//		osm = new OnlineStoreManagerService().getOnlineStoreManagerPort();
	}

	/**
	 * Registers user to uStore
	 * @return answer of the uStore registration
	 */
	public String registerUser() {
		String usr = pref.getUsername2();
		String pwd = pref.getPassword2();
		String portNum = /*pref.getPort()*/ "9090";
		String idAddr = "";
		String answer = "";
		try {
			idAddr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
//		try {
//			answer = osm.registerDeployManager(usr, pwd, idAddr, portNum);
//		} catch (UAALException_Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return answer;
	}
	
}
