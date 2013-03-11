package org.universAAL.ucc.controller.install;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.UAPP;
import org.universAAL.ucc.windows.DeployConfigView;
import org.universAAL.ucc.windows.DeployStrategyView;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.middleware.managers.api.UAPPPackage;

public class DeployStrategyController implements Button.ClickListener {
	private DeployStrategyView view;
	private UAPP uapp;
	private UccUI app;
	private static String base;
	private ResourceBundle bundle;
	private BundleContext bc;
	private IInstaller installer;
	
	public DeployStrategyController(UccUI app, DeployStrategyView view, UAPP uapp) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.uapp = uapp;
		this.app = app;
		this.view = view;
		view.getOk().addListener((Button.ClickListener)this);
		view.getCancel().addListener((Button.ClickListener)this);
		bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ServiceReference ref = bc.getServiceReference(IInstaller.class.getName());
		installer = (IInstaller) bc.getService(ref);
	}


	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == view.getOk()) {
			if(view.getOptions().getValue().toString().equals(bundle.getString("opt.available.nodes"))) {
				UAPPPackage pack = null;
				try {
					pack = new UAPPPackage(uapp.getServiceId(), new URI(uapp.getUappLocation()), null);
				} 
				catch (URISyntaxException e) {
					app.getMainWindow().showNotification(bundle.getString("uri.error"), Notification.TYPE_ERROR_MESSAGE);
					e.printStackTrace();
				}
				InstallationResults res = installer.requestToInstall(pack);
				System.err.println(res.name().toString());
				app.getMainWindow().showNotification(res.name().toString());
			} else if(view.getOptions().getValue().toString().equals(bundle.getString("opt.selected.nodes"))) {
				DeployConfigView dcv = new DeployConfigView(app, uapp.getServiceId(), uapp.getUappLocation());
				app.getMainWindow().removeWindow(view);
				app.getMainWindow().addWindow(dcv);
				DeployConfigController dcc = new DeployConfigController(app, dcv);
			}
		}
		if(event.getButton() == view.getCancel()) {
			app.getMainWindow().removeWindow(view);
			app.getMainWindow().showNotification(bundle.getString("break.note"), Notification.TYPE_ERROR_MESSAGE);
		}
		
	}

}
