package org.universAAL.ucc.controller.install;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.universAAL.ucc.frontend.api.IWindow;
import org.universAAL.ucc.frontend.api.impl.InstallProcessImpl;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.install.License;
import org.universAAL.ucc.windows.LicenceWindow;
import org.universAAL.ucc.windows.OptionsWindow;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

public class LicenseController implements Property.ValueChangeListener, ClickListener {
	private final LicenceWindow win;
	private String base = "resources.ucc";
	private ResourceBundle res;
	private ArrayList<License> lix;
	private UccUI app;
	private AALService aal;
	
	public LicenseController(UccUI app, LicenceWindow win, ArrayList<License> lix, AALService aal) {
		res = ResourceBundle.getBundle(base);
		this.win = win;
		this.lix = lix;
		this.app = app;
		this.aal = aal;
		win.getGo().addListener((Button.ClickListener)this);
		win.getCancel().addListener((Button.ClickListener)this);
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		
		if(event.getProperty() instanceof Tree) {
			Panel panel = new Panel();
			for(License l : lix) {
			for(File f : l.getLicense()) {
				if(f.getName().contains(event.getProperty().getValue().toString())) {
					FileReader fr = null;
					try {
						fr = new FileReader(f);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					BufferedReader reader = new BufferedReader(fr);
					String line = null;
					 try {
						while((line = reader.readLine()) != null) {
							panel.addComponent(new Label(line));
						 }
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
			win.getVl().removeAllComponents();
			win.createSecondComponent(panel);
			
		} else {
			if(event.getProperty().getValue().toString().equals(res.getString("agree.radio"))) {
				win.getGo().setEnabled(true);
			} 
			if(event.getProperty().getValue().toString().equals(res.getString("dontAgree.radio"))) {
				win.getGo().setEnabled(false);
			}
		}
		
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == win.getCancel()) {
			app.getMainWindow().showNotification(res.getString("break.note"), Notification.TYPE_ERROR_MESSAGE);
			app.getMainWindow().removeWindow(win);
		}
		if(event.getButton() == win.getGo()) {
//			app.getMainWindow().showNotification(res.getString("installed.note"));
			app.getMainWindow().removeWindow(win);
			IWindow iw = new InstallProcessImpl();
			iw.getDeployStratgyView(aal.getName(), aal.getUaapList().get(0).getServiceId(), 
					aal.getUaapList().get(0).getUappLocation(), aal.getUaapList().get(0));
//			app.getMainWindow().addWindow(new OptionsWindow(app, aal));
			
			//ToDo: Call Configurator to personalize the uapps  of the AAL service
		}
		
	}

}
