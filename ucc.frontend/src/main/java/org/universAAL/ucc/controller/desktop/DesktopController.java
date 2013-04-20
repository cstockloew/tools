package org.universAAL.ucc.controller.desktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.xml.bind.JAXB;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.client.util.UstoreUtil;
import org.universAAL.ucc.controller.Activator;
import org.universAAL.ucc.database.aalspace.DataAccess;
import org.universAAL.ucc.database.aalspace.DataAccessImpl;
import org.universAAL.ucc.database.model.jaxb.CollectionValues;
import org.universAAL.ucc.database.model.jaxb.OntologyInstance;
import org.universAAL.ucc.database.model.jaxb.Profiles;
import org.universAAL.ucc.database.model.jaxb.SimpleObject;
import org.universAAL.ucc.database.model.jaxb.Subprofile;
import org.universAAL.ucc.database.model.jaxb.CollectionValues.Values;
import org.universAAL.ucc.database.model.jaxb.Subprofile.Collections;
import org.universAAL.ucc.database.model.jaxb.Subprofile.EnumObjects;
import org.universAAL.ucc.database.model.jaxb.Subprofile.SimpleObjects;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.deploymanagerservice.DeployManagerService;
import org.universAAL.ucc.frontend.api.IFrontend;
import org.universAAL.ucc.frontend.api.impl.FrontendImpl;
import org.universAAL.ucc.model.preferences.Preferences;
import org.universAAL.ucc.startup.api.Setup;
import org.universAAL.ucc.startup.model.User;
import org.universAAL.ucc.webconnection.WebConnector;
import org.universAAL.ucc.windows.AccountWindow;
import org.universAAL.ucc.windows.PreferencesWindow;
import org.universAAL.ucc.windows.SearchWindow;
import org.universAAL.ucc.windows.ToolWindow;
import org.universAAL.ucc.windows.UccUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

public class DesktopController implements Button.ClickListener {
	private UccUI app;
	private Window main;
	private UserAccountDB db;
//	private DeployManagerService dms;
	private UstoreUtil client;
	private String base;
	private ResourceBundle bundle;
	private Setup setup;
	private static String sessionKey;
	private BundleContext bc;
	private String user;
	private String pwd;

	public DesktopController(UccUI app) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.app = app;
		this.main = app.getMainWindow();
		BundleContext context = FrameworkUtil.getBundle(getClass())
				.getBundleContext();
		ServiceReference ref = context.getServiceReference(UserAccountDB.class
				.getName());
		db = (UserAccountDB) context.getService(ref);
		context.ungetService(ref);
		client = new UstoreUtil();
		Properties prop = new Properties();
		Reader reader = null;
		try {
			reader = new FileReader(new File("file:///../etc/uCC/setup.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prop.load(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user = prop.getProperty("admin");
		pwd = prop.getProperty("pwd");
		// this.app.getSearchButton().addListener(this);
		// this.app.getStartButton().addListener(this);
		bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ServiceReference refer = bc.getServiceReference(Setup.class.getName());
		setup = (Setup) bc.getService(refer);
		bc.ungetService(refer);
	}

	public void buttonClick(ClickEvent event) {
		if (event.getButton() == app.getStartButton()) {
			if (main.getChildWindows().size() > 0) {
				for (Window w : main.getChildWindows()) {
					if (w instanceof ToolWindow) {
						main.removeWindow(w);
					} else {
						ToolWindow startWindow = new ToolWindow(app);
						main.addWindow(startWindow);
					}
				}
			} else {
				ToolWindow startWin = new ToolWindow(app);
				main.addWindow(startWin);
			}
		}
		if (event.getButton() == app.getSearchButton()) {
			SearchWindow sWin = new SearchWindow(app);
			main.addWindow(sWin);
		}
		if (event.getButton() == app.getLogin()) {
			if(app.getUser().getValue().equals("") || app.getPwd().getValue().equals("")) {
				app.getMainWindow().showNotification(bundle.getString("input.empty"));
			} else {
			if(app.getUser().getValue().equals(user) && app.getPwd().getValue().equals(pwd)) {
				adminLogin();
			} else {
				boolean in = false;
				List<User> users = setup.getUsers("file:///../etc/uCC/users.xml");
				for(User u : users) {
					if(u.getName().equals(app.getUser().getValue()) && u.getPassword().equals(app.getPwd().getValue())) {
						adminLogin();
						in = true;
					}
				}
					if(!in) {
						app.getMainWindow().showNotification(bundle.getString("login.account.fail"), Notification.TYPE_ERROR_MESSAGE);
						app.getUser().setValue("");
						app.getPwd().setValue("");
					}
				}
			}

		}
		if (event.getButton() == app.getAdminButton()) {
			Preferences pref = db.getPreferencesData(System
					.getenv("systemdrive") + "/uccDB/preferences.xml");
			PreferencesWindow p = new PreferencesWindow(app, pref);
			main.addWindow(p);
		}
		
		if(event.getButton() == app.getLink()) {
			AccountWindow aw = new AccountWindow();
			AccountWindowController awc = new AccountWindowController(aw, app);
			app.getMainWindow().addWindow(aw);
		}

	}

	public static String getSessionKey() {
		return sessionKey;
	}
	
	private void adminLogin() {
		main.removeWindow(app.getLoginWindow());
		main.removeComponent(app.getVLog());
		main.setContent(app.createContent(this));
		// Register to uStore
		sessionKey = client.registerUser();
		if (sessionKey == null || sessionKey.equals("")) {
			app.getMainWindow().showNotification(
					bundle.getString("login.fail"),
					Notification.TYPE_ERROR_MESSAGE);

		} else {
			app.getMainWindow().showNotification(
					bundle.getString("login.success"),
					Notification.TYPE_HUMANIZED_MESSAGE);
			System.err.println("WS-ANSWER: " + sessionKey);
		}
		WebConnector web = WebConnector.getInstance();
		web.startListening();
		
	}

}
