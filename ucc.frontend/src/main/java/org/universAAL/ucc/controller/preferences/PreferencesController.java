package org.universAAL.ucc.controller.preferences;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.database.preferences.UserAccountDB;
import org.universAAL.ucc.model.preferences.Preferences;
import org.universAAL.ucc.windows.PreferencesWindow;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PreferencesController implements ClickListener {
    private PreferencesWindow win;
    private UccUI app;
    private String base;
    private ResourceBundle bundle;
    private UserAccountDB db;
    private Preferences oldPref;
    private final static String file = System.getenv("systemdrive")
	    + "/uccDB/preferences.xml";

    public PreferencesController(UccUI app, PreferencesWindow win) {
	base = "resources.ucc";
	bundle = ResourceBundle.getBundle(base);
	this.app = app;
	this.win = win;
	win.getSave().addListener(this);
	win.getReset().addListener(this);
	BundleContext bc = FrameworkUtil.getBundle(getClass())
		.getBundleContext();
	ServiceReference ref = bc.getServiceReference(UserAccountDB.class
		.getName());
	db = (UserAccountDB) bc.getService(ref);
	bc.ungetService(ref);
	oldPref = db.getPreferencesData(file);
    }

    public void buttonClick(ClickEvent event) {
	if (event.getButton() == win.getSave()) {
	    Preferences pref = new Preferences();
	    pref.setUsername(win.getUserTxt().getValue().toString());
	    pref.setUsername2(win.getUserTxt2().getValue().toString());
	    pref.setPassword(win.getPwdTxt().getValue().toString());
	    pref.setPassword2(win.getPwdTxt2().getValue().toString());
	    pref.setShopUrl(win.getUrlTxt().getValue().toString());
	    pref.setPort(win.getPortTxt().getValue().toString());
	    if (win.getLangSelect().getValue().toString().equals("German")
		    || win.getLangSelect().getValue().toString().equals(
			    "Deutsch")) {
		pref.setLanguage("de");
	    } else {
		pref.setLanguage("en");
	    }
	    // ToDo: save preferences in database
	    win.getSave().setVisible(false);
	    win.getReset().setVisible(true);
	    if (win.getLangSelect().getValue().toString().equals(
		    bundle.getString("german"))) {
		Locale.setDefault(Locale.GERMAN);
	    } else {
		Locale.setDefault(Locale.ENGLISH);
	    }
	    app.getMainWindow().removeWindow(win);
	    db.saveStoreAccessData(pref, file);
	}
	if (event.getButton() == win.getReset()) {
	    win.getUserTxt().setValue(oldPref.getUsername());
	    win.getUserTxt2().setValue(oldPref.getUsername2());
	    win.getPwdTxt().setValue(oldPref.getPassword());
	    win.getPwdTxt2().setValue(oldPref.getPassword2());
	    win.getUrlTxt().setValue(oldPref.getShopUrl());
	    win.getPortTxt().setValue(oldPref.getPort());
	    if (oldPref.getLanguage().equals("de"))
		win.getLangSelect().setValue(bundle.getString("german"));
	    else
		win.getLangSelect().setValue(bundle.getString("english"));
	}

    }

}
