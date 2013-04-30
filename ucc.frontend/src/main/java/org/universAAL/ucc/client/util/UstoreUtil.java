package org.universAAL.ucc.client.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import org.universAAL.ucc.controller.desktop.DesktopController;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.model.preferences.Preferences;
import org.universAAL.ucc.startup.api.Setup;
import org.universAAL.ucc.startup.model.UserAccountInfo;
import org.universAAL.commerce.ustore.tools.OnlineStoreManager;
import org.universAAL.commerce.ustore.tools.OnlineStoreManagerService;
import org.universAAL.commerce.ustore.tools.UAALException_Exception;

public class UstoreUtil {
	private BundleContext bc;
	private ServiceReference ref;
	private UserAccountDB db;
	private Preferences pref;
	private Setup setup;
	private OnlineStoreManager client;
	private static final QName SERVICE_NAME = new QName(
			"http://tools.ustore.commerce.universaal.org/",
			"OnlineStoreManagerService");

	public UstoreUtil() {
		bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ref = bc.getServiceReference(UserAccountDB.class.getName());
		db = (UserAccountDB) bc.getService(ref);
		pref = db.getPreferencesData(System.getenv("systemdrive")
				+ "/uccDB/preferences.xml");
		bc.ungetService(ref);
		ref = bc.getServiceReference(Setup.class.getName());
		setup = (Setup)bc.getService(ref);
		bc.ungetService(ref);
		// Getting ustore webservice
		URL wsdlURL = OnlineStoreManagerService.WSDL_LOCATION;
		OnlineStoreManagerService ss = new OnlineStoreManagerService(wsdlURL,
				SERVICE_NAME);
		client = ss.getOnlineStoreManagerPort();
	}

	/**
	 * Registers user to uStore
	 * 
	 * @return answer of the uStore registration
	 * @throws
	 * @throws UAALException
	 */
	public void registerUser(String sessionKey) {
		Reader reader = null;
		try {
			reader = new FileReader("file:///../etc/uCC/setup.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(reader);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String adminUserName = prop.getProperty("admin");
		String adminPassword = prop.getProperty("pwd");
		
		String portNum = prop.getProperty("storePort");
		String idAddr = prop.getProperty("url");
		
		//TODO: Delete when have a Dynamic Adress
		try {
			idAddr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
//		if (adminUserName == null || adminUserName.equals("") || adminPassword == null || adminPassword.equals("")) {
//			return;
//		}
		System.err.println(adminUserName+" "+adminPassword+" "+sessionKey+" "+portNum+ " "+" "+idAddr);
		try {
			client.registerDeployManager(sessionKey, adminUserName, adminPassword, idAddr, portNum);
		} catch (UAALException_Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getSessionKey() {
		//Calling registered Users from Database
		List<UserAccountInfo>list = setup.getUsers("file:///../etc/uCC/users.xml");
		String sessionKey = "";
		//Connection to uStore to get a session key for one user
		for(UserAccountInfo info : list) {
			if(info.getName() != null && !info.getName().equals("") && info.getPassword() != null && !info.getPassword().equals("")) {
				try {
					sessionKey = client.getSessionKey(info.getName(), info.getPassword());
				} catch (UAALException_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sessionKey;
	}

}
