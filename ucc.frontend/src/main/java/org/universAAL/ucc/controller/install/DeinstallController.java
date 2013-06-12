package org.universAAL.ucc.controller.install;

import org.universAAL.ucc.frontend.api.IFrontend;
import org.universAAL.ucc.frontend.api.impl.FrontendImpl;
import org.universAAL.ucc.service.manager.Activator;
import org.universAAL.ucc.windows.DeinstallWindow;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class DeinstallController implements Button.ClickListener {
	private DeinstallWindow win;
	private UccUI app;
	private IFrontend front;
	
	public DeinstallController(DeinstallWindow window, UccUI app) {
		this.win = window;
		this.app = app;
		this.win.getDel().addListener(this);
		this.win.getCancel().addListener(this);
		front = new FrontendImpl();
	}

	public void buttonClick(ClickEvent event) {
		if(win.getDel() == event.getButton()) {
			front.uninstallService(Activator.getSessionKey(), win.getList().getValue().toString());
		}
		
		if(win.getCancel() == event.getButton()) {
			app.getMainWindow().removeWindow(win);
			
		}
		
	}

}
