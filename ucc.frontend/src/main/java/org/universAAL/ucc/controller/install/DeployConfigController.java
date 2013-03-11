package org.universAAL.ucc.controller.install;

import java.util.ResourceBundle;

import org.universAAL.ucc.windows.DeployConfigView;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class DeployConfigController implements Button.ClickListener {
	private DeployConfigView win;
	private UccUI app;
	private String base;
	private ResourceBundle bundle;
	
	public DeployConfigController(UccUI app, DeployConfigView win) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.win = win;
		this.app = app;
		win.getNext().addListener(this);
		win.getPrevious().addListener(this);
		win.getCancel().addListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == win.getNext()) {
			
		}
		if(event.getButton() == win.getPrevious()) {
			
		}
		if(event.getButton() == win.getCancel()) {
			app.getMainWindow().removeWindow(win);
			app.getMainWindow().showNotification(bundle.getString("break.note"));
		}
		
	}

}
