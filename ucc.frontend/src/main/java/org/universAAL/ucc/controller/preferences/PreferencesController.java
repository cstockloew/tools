package org.universAAL.ucc.controller.preferences;

import java.util.Locale;
import java.util.ResourceBundle;

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
	
	public PreferencesController(UccUI app, PreferencesWindow win) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.app = app;
		this.win = win;
		win.getSave().addListener(this);
		win.getReset().addListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == win.getSave()) {
			Preferences pref = new Preferences();
			pref.setUsername(win.getUserTxt().getValue().toString());
			pref.setPassword(win.getPwdTxt().getValue().toString());
			pref.setShopUrl(win.getUrlTxt().getValue().toString());
			pref.setPort(win.getPortTxt().getValue().toString());
			pref.setLanguage(win.getLangSelect().getValue().toString());
//			ToDo: save preferences in database
			win.getSave().setVisible(false);
			win.getReset().setVisible(true);
			win.getUserTxt().setEnabled(false);
			win.getPwdTxt().setEnabled(false);
			win.getUrlTxt().setEnabled(false);
			win.getPortTxt().setEnabled(false);
			win.getLangSelect().setEnabled(false);
			if(win.getLangSelect().getValue().toString().equals(bundle.getString("german"))) {
				Locale.setDefault(Locale.GERMAN);
			} else {
				Locale.setDefault(Locale.ENGLISH);
			}
			app.getMainWindow().removeWindow(win);
		}
		if(event.getButton() == win.getReset()) {
			win.getSave().setVisible(true);
			win.getReset().setVisible(false);
			win.getUserTxt().setEnabled(true);
			win.getPwdTxt().setEnabled(true);
			win.getUrlTxt().setEnabled(true);
			win.getPortTxt().setEnabled(true);
			win.getLangSelect().setEnabled(true);
		}
		
	}

}
