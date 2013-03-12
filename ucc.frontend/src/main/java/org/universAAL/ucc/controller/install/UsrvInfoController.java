package org.universAAL.ucc.controller.install;

import java.util.ResourceBundle;

import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.windows.LicenceWindow;
import org.universAAL.ucc.windows.UccUI;
import org.universAAL.ucc.windows.UsrvInformationWindow;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class UsrvInfoController implements Button.ClickListener{
	private AALService usrv;
	private UsrvInformationWindow win;
	private LicenceWindow lWin;
	private UccUI app;
	private String base;
	private ResourceBundle bundle;
	
	public UsrvInfoController(AALService usrv, LicenceWindow lw, UccUI app) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.usrv = usrv;
		this.app = app;
		win = new UsrvInformationWindow();
		win.getOk().addListener(this);
		win.getCancel().addListener(this);
		
		win.getForm().getField(bundle.getString("name.label")).setValue(usrv.getName());
		win.getNameTxt().setReadOnly(true);
		win.getProvider().setValue(usrv.getProvider());
		win.getForm().getField(bundle.getString("provider.label")).setValue(usrv.getProvider());
		win.getProvider().setReadOnly(true);
		win.getForm().getField(bundle.getString("description.label")).setValue(usrv.getDescription());
		win.getUsrvDescription().setReadOnly(true);
		String version = String.valueOf(usrv.getMajor()+".");
		version = version.concat(String.valueOf(usrv.getMinor()+"."));
		version = version.concat(String.valueOf(usrv.getMicro()));
		win.getForm().getField(bundle.getString("version.label")).setValue(version);
		win.getVersion().setReadOnly(true);
//		for(String s : usrv.getTags()) {
//			win.getTags().addItem(s);
//		}
//		win.getTags().setReadOnly(true);
		app.getMainWindow().addWindow(win);
		this.lWin = lw;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == win.getOk()) {
			app.getMainWindow().removeWindow(win);
			app.getMainWindow().addWindow(lWin);
			
		}
		if(event.getButton() == win.getCancel()) {
			app.getMainWindow().removeWindow(win);
			app.getMainWindow().showNotification(bundle.getString("break.note"));
		}
		
	}
}
