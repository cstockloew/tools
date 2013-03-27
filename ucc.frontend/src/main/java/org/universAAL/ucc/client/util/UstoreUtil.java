package org.universAAL.ucc.client.util;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import org.apache.cxf.frontend.ClientProxyFactoryBean;


import javax.xml.namespace.QName;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.model.preferences.Preferences;
import org.universAAL.commerce.ustore.tools.OnlineStoreManager;
import org.universAAL.commerce.ustore.tools.OnlineStoreManagerService;
import org.universAAL.commerce.ustore.tools.UAALException_Exception;


public class UstoreUtil {
	private BundleContext bc;
	private ServiceReference ref;
	private UserAccountDB db;
	private Preferences pref;
	private OnlineStoreManager client;
	private static final QName SERVICE_NAME = new QName("http://tools.ustore.commerce.universaal.org/", "OnlineStoreManagerService");
	
	public UstoreUtil() {
		bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ref = bc.getServiceReference(UserAccountDB.class.getName());
		db = (UserAccountDB) bc.getService(ref);
		pref = db.getPreferencesData(System.getenv("systemdrive")+"/uccDB/preferences.xml");
		bc.ungetService(ref);
		//Getting ustore webservice
		URL wsdlURL = OnlineStoreManagerService.WSDL_LOCATION;
		OnlineStoreManagerService ss = new OnlineStoreManagerService(wsdlURL, SERVICE_NAME);
        client = ss.getOnlineStoreManagerPort(); 
	}

	/**
	 * Registers user to uStore
	 * @return answer of the uStore registration
	 * @throws  
	 * @throws UAALException 
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

try {
	answer = client.registerDeployManager(usr, pwd, idAddr, portNum);
} catch (UAALException_Exception e) {
	e.printStackTrace();
}
		
		return answer;
	}
	
}
