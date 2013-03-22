package org.universAAL.ucc.controller.install;

import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.UAPP;
import org.universAAL.ucc.windows.DeployConfigView;
import org.universAAL.ucc.windows.DeployStrategyView;
import org.universAAL.ucc.windows.DeploymentInformationView;
import org.universAAL.ucc.windows.UccUI;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class DeploymentInfoController implements Button.ClickListener, ValueChangeListener{
	private DeploymentInformationView win;
	private AALService aal;
	private UccUI app;
	private HashMap<String, DeployStrategyView> dsvMap;
	private HashMap<String, DeployConfigView> dcvMap;
	private String selected = "";
	private String base = "";
	private ResourceBundle bundle;
	private VerticalLayout actVL;
	
	public DeploymentInfoController(UccUI app, AALService aal, DeploymentInformationView win) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.aal = aal;
		this.win = win;
		this.app = app;
		dsvMap = new HashMap<String, DeployStrategyView>();
		dcvMap = new HashMap<String, DeployConfigView>();
		for(UAPP uapp : aal.getUaapList()) {
			System.err.println(uapp.getPart().getPartId());
			win.getTree().addItem(uapp.getPart().getPartId());
			win.getTree().setChildrenAllowed(uapp.getPart().getPartId(), false);
			DeployStrategyView dsv = new DeployStrategyView(aal.getName(), aal.getServiceId(), uapp.getUappLocation());
			dsvMap.put(uapp.getPart().getPartId(), dsv);
			DeployConfigView dcv = new DeployConfigView(app, aal.getServiceId(), uapp.getUappLocation());
			dcv.getTxt().setValue(uapp.getPart().getPartId());
			dcvMap.put(uapp.getPart().getPartId(), dcv);
		}
		win.getTree().addListener(this);
		win.getOk().addListener((Button.ClickListener)this);
		win.getCancel().addListener((Button.ClickListener)this);
		win.createFirstComponent(win.getTree());
	}

	public void buttonClick(ClickEvent event) {
		if(event.getButton() == win.getOk()) {
			//TODO: Deployment
			win.getHp().removeComponent(actVL);
			win.getTree().removeItem(selected);
			dsvMap.remove(selected);
			dcvMap.remove(selected);
			if(dsvMap.isEmpty() && dcvMap.isEmpty()) {
				app.getMainWindow().showNotification(bundle.getString("success.install.msg"), Notification.TYPE_HUMANIZED_MESSAGE);
				app.getMainWindow().removeWindow(win);
				File f = new File(System.getenv("systemdrive")+"/tempUsrvFiles/");
				deleteFiles(f);
			}
			
			
		}
		if(event.getButton() == win.getCancel()) {
			app.getMainWindow().removeWindow(win);
			app.getMainWindow().showNotification(bundle.getString("break.note"), Notification.TYPE_HUMANIZED_MESSAGE);
			File f = new File(System.getenv("systemdrive")+"/tempUsrvFiles/");
			deleteFiles(f);
		}
	}

	private void deleteFiles(File path) {
		File[] files = path.listFiles();
		for(File del : files) {
			if(del.isDirectory()) {
				deleteFiles(del);
			}
				System.err.println(del.getAbsolutePath());
				del.delete();
		} 
		
	}
	public void valueChange(ValueChangeEvent event) {
		for(UAPP ua : aal.getUaapList()) {
			System.err.println(aal.getUaapList().size());
			if(ua.getPart().getPartId().equals(event.getProperty().toString())) {
				selected = event.getProperty().toString();
				DeployStrategyView dsv = dsvMap.get(ua.getPart().getPartId());
				DeployConfigView dcv = dcvMap.get(ua.getPart().getPartId());
				actVL = win.createSecondComponent(dsv, dcv);
			}
		}
		
	}

}
