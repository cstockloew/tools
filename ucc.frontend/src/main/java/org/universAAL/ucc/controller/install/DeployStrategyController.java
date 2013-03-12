package org.universAAL.ucc.controller.install;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.frontend.api.IWindow;
import org.universAAL.ucc.frontend.api.impl.InstallProcessImpl;
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
	private AALService aal;
	private int index;
	private UccUI app;
	private static String base;
	private ResourceBundle bundle;
	private BundleContext bc;
	private IInstaller installer;
	
	public DeployStrategyController(UccUI app, DeployStrategyView view, int index, AALService aal) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.app = app;
		this.view = view;
		this.aal = aal;
		this.index = index;
		view.getOk().addListener((Button.ClickListener)this);
		view.getCancel().addListener((Button.ClickListener)this);
		bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ServiceReference ref = bc.getServiceReference(IInstaller.class.getName());
		installer = (IInstaller) bc.getService(ref);
	}


	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == view.getOk()) {
			IWindow iw = new InstallProcessImpl();
			if(view.getOptions().getValue().toString().equals(bundle.getString("opt.available.nodes"))) {
				UAPPPackage pack = null;
				try {
					pack = new UAPPPackage(aal.getUaapList().get(index).getServiceId(), new URI(aal.getUaapList().get(index).getUappLocation()), null);
				} 
				catch (URISyntaxException e) {
					app.getMainWindow().showNotification(bundle.getString("uri.error"), Notification.TYPE_ERROR_MESSAGE);
					e.printStackTrace();
				}
				InstallationResults res = installer.requestToInstall(pack);
				app.getMainWindow().showNotification(res.name().toString());
				if(index >= 1) {
					//If more than one app
					app.getMainWindow().removeWindow(view);
					iw.getDeployStratgyView(aal.getUaapList().get(index).getName(), aal.getUaapList().get(index).getServiceId(), aal.getUaapList().get(index).getUappLocation(), index, aal);
				} else {
					//ToDo: Show message that all apps are installed
					app.getMainWindow().removeWindow(view);
					app.getMainWindow().showNotification(bundle.getString("success.install.msg"), Notification.TYPE_HUMANIZED_MESSAGE);
				} 
				
			} 
			else if(view.getOptions().getValue().toString().equals(bundle.getString("opt.selected.nodes"))) {
				app.getMainWindow().removeWindow(view);
				if(index <=  1)
					iw.getDeployConfigView(aal, index, true);
				else
					iw.getDeployConfigView(aal, index, false);
			}
		}
		if(event.getButton() == view.getCancel()) {
			app.getMainWindow().removeWindow(view);
			app.getMainWindow().showNotification(bundle.getString("break.note"), Notification.TYPE_ERROR_MESSAGE);
		}
		
	}

}
