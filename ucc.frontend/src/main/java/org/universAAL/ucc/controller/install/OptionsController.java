package org.universAAL.ucc.controller.install;

import java.io.File;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.UAPP;
import org.universAAL.ucc.windows.DeployStrategyView;
import org.universAAL.ucc.windows.NotificationWindow;
import org.universAAL.ucc.windows.OptionsWindow;
import org.universAAL.ucc.windows.UccUI;
import org.w3c.dom.Document;

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
	private AALService aal;
	private static int appCounter;

	public OptionsController(UccUI app, OptionsWindow win, AALService aal) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.app = app;
		this.win = win;
		this.aal = aal;
		appCounter = aal.getUaapList().size();
		win.getRadio().addListener(this);
		win.getInvoke().addListener((Button.ClickListener) this);
	}

	public void buttonClick(ClickEvent event) {
		if (event.getButton() == win.getInvoke()) {
			app.getMainWindow().removeWindow(win);
			//Deploy strategy windodw for every uapp in the usrv file
			if(appCounter > 0) {
				appCounter--;
				DeployStrategyView dsv = new DeployStrategyView(aal.getUaapList().get(appCounter).getName(), aal.getUaapList().get(appCounter).getServiceId(),
						aal.getUaapList().get(appCounter).getUappLocation());
				DeployStrategyController dsc = new DeployStrategyController(app, dsv, appCounter, aal);
				app.getMainWindow().addWindow(dsv);
				
			} 

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
