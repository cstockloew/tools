package org.universAAL.ucc.controller.install;

import java.util.ResourceBundle;

import org.universAAL.ucc.frontend.api.IWindow;
import org.universAAL.ucc.frontend.api.impl.InstallProcessImpl;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.windows.DeployConfigView;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

public class DeployConfigController implements Button.ClickListener {
	private DeployConfigView win;
	private UccUI app;
	private String base;
	private ResourceBundle bundle;
	private boolean lastUapp;
	private int index;
	private AALService aal;
	
	public DeployConfigController(UccUI app, DeployConfigView win, AALService aal, int index, boolean lastUapp) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.win = win;
		this.app = app;
		this.aal = aal;
		this.index = index;
		this.lastUapp = lastUapp;
		win.getNext().addListener(this);
//		win.getPrevious().addListener(this);
		win.getCancel().addListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == win.getNext()) {
			app.getMainWindow().removeWindow(win);
			if(lastUapp) {
				app.getMainWindow().showNotification(bundle.getString("success.install.msg"), Notification.TYPE_HUMANIZED_MESSAGE);
			} else {
				IWindow iw = new InstallProcessImpl();
				iw.getDeployConfigView(aal, index , lastUapp);
			}
			//ToDo: Installation of uapp file
		}

		if(event.getButton() == win.getCancel()) {
			app.getMainWindow().removeWindow(win);
			app.getMainWindow().showNotification(bundle.getString("break.note"));
		}
		
	}

}
