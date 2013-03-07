package org.universAAL.ucc.controller.install;

import java.util.ResourceBundle;

import org.universAAL.ucc.windows.NotificationWindow;
import org.universAAL.ucc.windows.OptionsWindow;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class OptionsController implements Button.ClickListener,
		Property.ValueChangeListener {
	private UccUI app;
	private OptionsWindow win;
	private String base;
	private ResourceBundle bundle;

	public OptionsController(UccUI app, OptionsWindow win) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.app = app;
		this.win = win;
		win.getRadio().addListener(this);
		win.getInvoke().addListener((Button.ClickListener) this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == win.getInvoke()) {
			app.getMainWindow().removeWindow(win);
			//Notification Window for no configuration available
			NotificationWindow w = new NotificationWindow(bundle.getString("installed.note"));
			app.getMainWindow().addWindow(w);
//			app.getMainWindow().showNotification(
//					bundle.getString("installed.note"));
			String sessionKey = "";
			String url = "";

			// Getting Values (Session-Key and Usrv File URL) from TextFields
			if (win.getRadio().getValue() != null) {
				if (win.getRadio().getValue().toString()
						.equals(bundle.getString("options.install.label"))) {
					sessionKey = win.getSessionKey1().getValue().toString();
					url = win.getUrl1().getValue().toString();
				} else if (win.getRadio().getValue().toString()
						.equals(bundle.getString("update.label"))) {
					sessionKey = win.getSessionKey2().getValue().toString();
					url = win.getUrl2().getValue().toString();
				}
			}
			// ToDo: Installation with IInstaller
		}

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if (event.getProperty().toString()
				.equals(bundle.getString("options.install.label"))) {
			win.getSessionKey2().setEnabled(false);
			win.getUrl2().setEnabled(false);
			win.getSessionKey1().setEnabled(true);
			win.getUrl1().setEnabled(true);
		}
		if (event.getProperty().toString()
				.equals(bundle.getString("update.label"))) {
			win.getSessionKey1().setEnabled(false);
			win.getSessionKey2().setEnabled(true);
			win.getUrl1().setEnabled(false);
			win.getUrl2().setEnabled(true);
		}

	}

}
