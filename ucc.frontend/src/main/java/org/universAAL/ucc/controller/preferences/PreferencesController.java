package org.universAAL.ucc.controller.preferences;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.model.preferences.Preferences;
import org.universAAL.ucc.service.manager.Activator;
import org.universAAL.ucc.windows.PreferencesWindow;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PreferencesController implements ClickListener {
	private PreferencesWindow win;
	private UccUI app;
	private String base;
	private ResourceBundle bundle;
//	private UserAccountDB db;
	private Preferences oldPref;
//	private final static String file = System.getenv("systemdrive")
//			+ "/uccDB/preferences.xml";
	private static String propFile;

	public PreferencesController(UccUI app, PreferencesWindow win) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		propFile = Activator.getModuleConfigHome().getAbsolutePath()+"/setup/setup.properties";
		this.app = app;
		this.win = win;
		win.getSave().addListener(this);
		win.getReset().addListener(this);
//		BundleContext bc = FrameworkUtil.getBundle(getClass())
//				.getBundleContext();
//		ServiceReference ref = bc.getServiceReference(UserAccountDB.class
//				.getName());
//		db = (UserAccountDB) bc.getService(ref);
//		bc.ungetService(ref);
//		oldPref = db.getPreferencesData(file);
		
		//Getting preferences from properties file
		Properties prop = new Properties();
		Reader reader = null;
		try {
			reader = new FileReader(propFile);
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
		String admin = prop.getProperty("admin");
		String pwd = prop.getProperty("pwd");
		String uccIp = prop.getProperty("uccUrl");
		String storeIp = prop.getProperty("shopUrl");
		String uccPort = prop.getProperty("uccPort");
		String storePort = prop.getProperty("storePort");
		String lang = prop.getProperty("lang");
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldPref = new Preferences();
		oldPref.setAdmin(admin);
		oldPref.setPwd(pwd);
		oldPref.setShopIp(storeIp);
		oldPref.setUccIp(uccIp);
		oldPref.setUccPort(uccPort);
		oldPref.setWsPort(storePort);
		oldPref.setLanguage(lang);
	}

	public void buttonClick(ClickEvent event) {
		if (event.getButton() == win.getSave()) {
			Preferences pref = new Preferences();
			pref.setAdmin(win.getUserTxt().getValue().toString());
			pref.setUccIp(win.getUserTxt2().getValue().toString());
			pref.setPwd(win.getPwdTxt().getValue().toString());
			pref.setUccPort(win.getUccPortTxt().getValue().toString());
			pref.setShopIp(win.getUrlTxt().getValue().toString());
			pref.setWsPort(win.getPortTxt().getValue().toString());
			if (win.getLangSelect().getValue().toString().equals("German")
					|| win.getLangSelect().getValue().toString()
							.equals("Deutsch")) {
				pref.setLanguage("de");
			} else {
				pref.setLanguage("en");
			}
			// ToDo: save preferences in database
			win.getSave().setVisible(false);
			win.getReset().setVisible(true);
			if (win.getLangSelect().getValue().toString()
					.equals(bundle.getString("german"))) {
				Locale.setDefault(Locale.GERMAN);
			} else {
				Locale.setDefault(Locale.ENGLISH);
			}
			app.getMainWindow().removeWindow(win);
			Properties prop = new Properties();
			Writer writer = null;
			try {
				writer = new FileWriter(propFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prop.setProperty("admin", pref.getAdmin());
			prop.setProperty("pwd", pref.getPwd());
			prop.setProperty("uccUrl", pref.getUccIp());
			prop.setProperty("uccPort", pref.getUccPort());
			prop.setProperty("shopUrl", pref.getShopIp());
			prop.setProperty("storePort", pref.getWsPort());
			prop.setProperty("lang", pref.getLanguage());
			try {
				prop.store(writer, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			db.saveStoreAccessData(pref, file);
		}
		if (event.getButton() == win.getReset()) {
			win.getUserTxt().setValue(oldPref.getAdmin());
			win.getUserTxt2().setValue(oldPref.getUccIp());
			win.getPwdTxt().setValue(oldPref.getPwd());
			win.getUccPortTxt().setValue(oldPref.getUccPort());
			win.getUrlTxt().setValue(oldPref.getShopIp());
			win.getPortTxt().setValue(oldPref.getWsPort());
			if (oldPref.getLanguage().equals("de"))
				win.getLangSelect().setValue(bundle.getString("german"));
			else
				win.getLangSelect().setValue(bundle.getString("english"));
		}

	}

}
