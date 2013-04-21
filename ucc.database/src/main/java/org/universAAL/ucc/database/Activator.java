package org.universAAL.ucc.database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXB;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.universAAL.ucc.database.aalspace.DataAccess;
import org.universAAL.ucc.database.aalspace.DataAccessImpl;
import org.universAAL.ucc.database.listener.interfaces.OntologySupplierService;
import org.universAAL.ucc.database.listener.services.OntologySupplierServiceImpl;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.database.preferences.impl.UserAccountDBImpl;
import org.universAAL.ucc.model.preferences.Preferences;
import org.universAAL.ucc.startup.api.Setup;
import org.universAAL.ucc.startup.api.impl.SetupImpl;
import org.universAAL.ucc.startup.model.Role;
import org.universAAL.ucc.startup.model.UccUsers;
import org.universAAL.ucc.startup.model.User;

public class Activator implements BundleActivator {

    private static BundleContext context;
    private ServiceRegistration reg;
//    private ServiceRegistration registry;

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
		User u = new User();
		u.setName("");
		u.setPassword("");
		List<Role>roles = new ArrayList<Role>();
		roles.add(Role.ENDUSER);
		u.setRole(roles);
		u.setChecked(false);
		Setup set = new SetupImpl();
		List<User>users = new ArrayList<User>();
		users.add(u);
		set.saveUsers(users, "file:///../etc/uCC/users.xml");
	}
	reg = context.registerService(UserAccountDB.class.getName(),
		new UserAccountDBImpl(), null);
	reg = context.registerService(Setup.class.getName(), new SetupImpl(), null);
	reg = context.registerService(DataAccess.class.getName(), new DataAccessImpl(), null);
	reg = context.registerService(OntologySupplierService.class.getName(), new OntologySupplierServiceImpl(), null);
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

}
