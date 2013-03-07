package org.universAAL.ucc.database;

import java.io.File;
import java.util.Locale;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.database.preferences.impl.UserAccountDBImpl;
import org.universAAL.ucc.model.preferences.Preferences;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration reg;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		File file = new File(System.getenv("systemdrive")+"/uccDB");
		if(!file.exists()) {
			file.mkdir();
			UserAccountDB db = new UserAccountDBImpl();
			Preferences p = new Preferences();
			System.err.println(Locale.getDefault().getLanguage());
			if(Locale.getDefault().getLanguage().equals(new Locale("de").getLanguage())) {
				p.setLanguage("de");
			} else {
				p.setLanguage("en");
			}
			p.setPassword("");
			p.setPassword2("");
			p.setPort("9988");
			p.setShopUrl("https://srv-ustore.haifa.il.ibm.com/webapp/wcs/stores/servlet/TopCategories_10001_10001");
			p.setUsername("");
			p.setUsername2("");
			db.saveStoreAccessData(p,file.getAbsolutePath()+ "/preferences.xml");
		}
		reg = context.registerService(UserAccountDB.class.getName(), new UserAccountDBImpl(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		reg.unregister();
	}

}
