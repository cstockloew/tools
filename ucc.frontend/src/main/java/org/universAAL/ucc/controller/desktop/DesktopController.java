package org.universAAL.ucc.controller.desktop;

import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.client.util.UstoreUtil;
import org.universAAL.ucc.controller.Activator;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.deploymanagerservice.DeployManagerService;
import org.universAAL.ucc.frontend.api.IFrontend;
import org.universAAL.ucc.frontend.api.impl.FrontendImpl;
import org.universAAL.ucc.model.preferences.Preferences;
import org.universAAL.ucc.webconnection.WebConnector;
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
	private static String sessionKey;

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
		// this.app.getSearchButton().addListener(this);
		// this.app.getStartButton().addListener(this);
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
		if (event.getButton() == app.getAdminButton()) {
			Preferences pref = db.getPreferencesData(System
					.getenv("systemdrive") + "/uccDB/preferences.xml");
			PreferencesWindow p = new PreferencesWindow(app, pref);
			main.addWindow(p);
		}

	}

	public static String getSessionKey() {
		return sessionKey;
	}

}
