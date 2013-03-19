package org.universAAL.ucc.controller.install;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.frontend.api.IWindow;
import org.universAAL.ucc.frontend.api.impl.InstallProcessImpl;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.UAPP;
import org.universAAL.ucc.windows.DeployStrategyView;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

import org.universAAL.middleware.deploymaneger.uapp.model.DeploymentUnit;
import org.universAAL.middleware.deploymaneger.uapp.model.Part;
import org.universAAL.middleware.interfaces.PeerCard;
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
	private Map<String, PeerCard> peers;
	
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


	public void buttonClick(ClickEvent event) {
		if(event.getButton() == view.getOk()) {
			IWindow iw = new InstallProcessImpl();
			//Installation on default nodes
			if(view.getOptions().getValue().toString().equals(bundle.getString("opt.available.nodes"))) {
				UAPPPackage pack = null;
				try {
					// Shanshan: get deploy configuration
					peers = installer.getPeers();
					Map config = buildDefaultInstallationLayout(aal.getUaapList().get(index));
					pack = new UAPPPackage(aal.getUaapList().get(index).getServiceId(), aal.getUaapList().get(index).getAppId(),
							new URI(aal.getUaapList().get(index).getUappLocation()), config);
				} 
				catch (URISyntaxException e) {
					app.getMainWindow().showNotification(bundle.getString("uri.error"), Notification.TYPE_ERROR_MESSAGE);
					e.printStackTrace();
				}
				//Showing installation results
				InstallationResults res = installer.requestToInstall(pack);
				if(res != null) {
					System.err.println("[DeployStrategyController] Request to install was successful");
					app.getMainWindow().showNotification(res.name().toString());
				}
				
				app.getMainWindow().removeWindow(view);
				//If more than one app
				if(index >= 1) {
					iw.getDeployStratgyView(aal.getUaapList().get(index).getName(), aal.getUaapList().get(index).getServiceId(), aal.getUaapList().get(index).getUappLocation(), index, aal);
				} else {
					//Show message that all apps are installed
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

    /**
	 * Method to find the set of target peers according to the multipart application manifest
	 * @param mpa the MPA 
	 * @return map of PeerCard of the target peers
	 */
    private Map<PeerCard, Part> buildDefaultInstallationLayout(UAPP uapp ) {
    	//TODO: Do we need to check AAL space first (aalSpaceCheck)?
    	Map<PeerCard, Part> mpaLayout = new HashMap<PeerCard, Part>();
    	Map<String, PeerCard> peersToCheck = new HashMap<String, PeerCard>();
		peersToCheck.putAll(peers);
		// Shanshan - TODO: update UAPP to return part info
    	for(Part part : uapp.getParts()){
    		//check: deployment units
    		for(String key: peersToCheck.keySet()){
    			PeerCard peer = peersToCheck.get(key);
    			if(checkDeployementUnit(part.getDeploymentUnit(), peer)){
    				mpaLayout.put(peer, part);
    				peersToCheck.remove(key);
    				break;
    			}
    		}
    	}
    	for (PeerCard key: mpaLayout.keySet()) {
    		   System.out.println("[DeployStrategyView.buildDefaultInstallationLayout] mpalayout: " + key.getPeerID() + "/" + mpaLayout.get(key).getPartId() );
    		}
    	return mpaLayout;
	}

    public static boolean checkDeployementUnit(List<DeploymentUnit> depoyementUnits, PeerCard peer){
    	String osUnit;
    	String pUnit;
		for(DeploymentUnit deployementUnit: depoyementUnits){
			//check the existence of: osUnit and platformUnit
			if(deployementUnit.getOsUnit()!= null){
				osUnit = deployementUnit.getOsUnit().value();
				if(osUnit == null || osUnit.isEmpty()){
					System.out.println("[DeployStrategyView.checkDeploymentUnit] OSunit is present but not consistent. OSUnit is null or empty");
					return false;
				}
				//Check if compatible?
				if (!osUnit.equals("any")) {
					// only considers equal definition
					//if (!osUnit.equalsIgnoreCase(peer.getOS())) return false;
					System.out.println("osUnit: " + osUnit);
					if (!(peer.getOS().contains(osUnit))) return false;
				}
			}else if (deployementUnit.getPlatformUnit() != null){
				pUnit = deployementUnit.getPlatformUnit().value();
				if(pUnit == null || pUnit.isEmpty()){
					System.out.println("[DeployStrategyView.checkDeploymentUnit] PlatformUnit is present but not consistent. Plaform is null or empty");
					return false;

				}
				//check if compatible?
				if (!pUnit.equalsIgnoreCase(peer.getPLATFORM_UNIT())) return false;
			}
			//TODO: check containerUnit?
		}
		return true;
	}
}
