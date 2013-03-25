package org.universAAL.ucc.controller.install;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.universAAL.middleware.interfaces.mpa.model.DeploymentUnit;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.interfaces.PeerRole;
import org.universAAL.middleware.interfaces.mpa.model.Part;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.middleware.managers.api.UAPPPackage;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.UAPP;
import org.universAAL.ucc.service.api.IServiceRegistration;
import org.universAAL.ucc.service.manager.Activator;
import org.universAAL.ucc.windows.DeployConfigView;
import org.universAAL.ucc.windows.DeployStrategyView;
import org.universAAL.ucc.windows.DeploymentInformationView;
import org.universAAL.ucc.windows.UccUI;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.SystemError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.OptionGroup;
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
	private Map<String, PeerCard> peers;
	private IInstaller installer;
	private IServiceRegistration srvRegistration;
	
	public DeploymentInfoController(UccUI app, AALService aal, DeploymentInformationView win) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		installer = Activator.getInstaller();
		srvRegistration = Activator.getReg();
		this.aal = aal;
		this.win = win;
		this.app = app;
		dsvMap = new HashMap<String, DeployStrategyView>();
		dcvMap = new HashMap<String, DeployConfigView>();
		int i = 0;
		
		for(UAPP uapp : aal.getUaapList()) {
			i++;
			if(i == 1) {
				selected = uapp.getPart().getPartId();
			}
			System.err.println(uapp.getPart().getPartId());
			win.getTree().addItem(uapp.getPart().getPartId());
			win.getTree().setChildrenAllowed(uapp.getPart().getPartId(), false);
			DeployStrategyView dsv = new DeployStrategyView(aal.getName(), aal.getServiceId(), uapp.getUappLocation());
			dsv.getOptions().addListener(this);
			dsvMap.put(uapp.getPart().getPartId(), dsv);
			
			DeployConfigView dcv = new DeployConfigView(app, aal.getServiceId(), uapp.getUappLocation());
			dcv.getTxt().setValue(uapp.getPart().getPartId());
			dcv.getTxt().setEnabled(false);
			dcv.getSelect().setEnabled(false);
			dcv.setEnabled(false);
			dcvMap.put(uapp.getPart().getPartId(), dcv);
		}
		win.getTree().select(selected);
		for(UAPP ua : aal.getUaapList()) {
			if(ua.getPart().getPartId().equals(selected)) {
				DeployStrategyView dsv = dsvMap.get(ua.getPart().getPartId());
				DeployConfigView dcv = dcvMap.get(ua.getPart().getPartId());
				actVL = win.createSecondComponent(dsv, dcv);
			}
		}
		win.getTree().addListener(this);
		win.getOk().addListener((Button.ClickListener)this);
		win.getCancel().addListener((Button.ClickListener)this);
		win.createFirstComponent(win.getTree());
	}

	public void buttonClick(ClickEvent event) {
		if(event.getButton() == win.getOk()) {
			//TODO: Deployment
			peers = installer.getPeers();
			for(UAPP uapp : aal.getUaapList()) {
				System.err.println("Current UAPP-LOCATION: "+uapp.getUappLocation());
				System.err.println("Current APP-ID: "+uapp.getAppId());
				System.err.println("Current PART-ID: "+uapp.getPart().getPartId());
				System.err.println("Current Bundle-ID: "+uapp.getBundleId());
				System.err.println("Current Bundle-Version: "+uapp.getBundleVersion());
				
				if(uapp.getPart().getPartId().equals(selected)) {
					System.err.println("SELECTED: "+selected);
					Map<PeerCard, Part> config = null;
					if(dsvMap.get(selected).getOptions().getValue().toString().equals(bundle.getString("opt.available.nodes"))) {
						config = buildDefaultInstallationLayout(uapp);
					} 
					else if(dsvMap.get(selected).getOptions().getValue().toString().equals(bundle.getString("opt.selected.nodes"))) {
						config = buildUserInstallationLayout(uapp);
					}
					UAPPPackage uapack = null;
					String appLocation = uapp.getUappLocation();
					appLocation = System.getenv("systemdrive")+"/tempUsrvFiles"+appLocation.substring(appLocation.indexOf("./")+1);
					System.err.println("APP-Location URI: "+appLocation);
					System.err.println(uapp.getUappLocation().trim());
					File uf = uf = new File(appLocation);
					uapack = new UAPPPackage(aal.getServiceId(), uapp.getAppId(), uf.toURI(), config);
						
					InstallationResults res = installer.requestToInstall(uapack);
					// Shanshan: TODO: add app and bundles to "services.xml" file.
					if (res.equals(InstallationResults.SUCCESS)) {
						srvRegistration.registerApp(aal.getServiceId(), uapp.getAppId());
						// get bundles for each part in the appId;
						// for each bundle: 
						srvRegistration.registerBundle(aal.getServiceId(), uapp.getBundleId(), uapp.getBundleVersion());
					} else {
						app.getMainWindow().showNotification(res.name(), Notification.TYPE_ERROR_MESSAGE);
					}

				}
			}
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
			System.err.println("In OptionsGroup!");
			System.err.println("Options Value: "+event.getProperty().getValue().toString());
			if(event.getProperty().getValue().toString().equals(bundle.getString("opt.selected.nodes"))) {
				dcvMap.get(selected).setEnabled(true);
				dcvMap.get(selected).getTxt().setEnabled(true);
				dcvMap.get(selected).getSelect().setEnabled(true);
			}
			if(event.getProperty().getValue().toString().equals(bundle.getString("opt.available.nodes"))) {
				dcvMap.get(selected).setEnabled(false);
				dcvMap.get(selected).getTxt().setEnabled(false);
				dcvMap.get(selected).getSelect().setEnabled(false);
			}
		
		
	}
	
	/*
	 * TODO: this method needs to be updated with MW for the new .uapp file - currently use some pre-assigned values
	 * 
	 */
	private Map<PeerCard, Part> buildDefaultInstallationLayout(UAPP uapp ) {
    	// assign some values for testing
//    	ArrayList<Part> parts = new ArrayList();
//    	Part part1 = new Part();
//    	part1.setPartId("part1");
//    	Part part2 = new Part();
//    	part2.setPartId("part2");
//    	parts.add(part1);
//    	parts.add(part2);
//    	uapp.setPart(parts);
    	//TODO: Do we need to check AAL space first (aalSpaceCheck)?
    	Map<PeerCard, Part> mpaLayout = new HashMap<PeerCard, Part>();
    	Map<String, PeerCard> peersToCheck = new HashMap<String, PeerCard>();
		peersToCheck.putAll(peers);
		// Shanshan - TODO: update UAPP to return part info
    	for(UAPP ua : aal.getUaapList()){
    		//check: deployment units
    		for(String key: peersToCheck.keySet()){
    			PeerCard peer = peersToCheck.get(key);
    			if (ua.getPart().getDeploymentUnit()==null) { 
    				// use any peer for testing
    				System.out.println("[DeployStrategyController] DeploymentUnit is null, use any peer!");
    				mpaLayout.put(peer, ua.getPart());
    				peersToCheck.remove(key);
    				break;   				
    			} 
    			if(checkDeployementUnit(ua.getPart().getDeploymentUnit(), peer)){
    				System.err.println("IN CHECK DEPLOYMENT UNIT!");
    			 	mpaLayout.put(peer, ua.getPart());
    				peersToCheck.remove(key);
    				break;
    			} else {
    				app.getMainWindow().showNotification(bundle.getString("peer.available.not"), Notification.TYPE_WARNING_MESSAGE);
    			}
    		}
    	}
    	for (PeerCard key: mpaLayout.keySet()) {
    		   System.out.println("[DeployStrategyView.buildDefaultInstallationLayout] mpalayout: " + key.getPeerID() + "/" + mpaLayout.get(key).getPartId() );
    		}
    	return mpaLayout;
	}
	
	private Map<PeerCard, Part> buildUserInstallationLayout(UAPP uapp) {
		Map<PeerCard, Part> mapLayout = new HashMap<PeerCard, Part>();
		Map<String, PeerCard> peersToCheck = new HashMap<String, PeerCard>();
		//Create peer from user selection and test if peer fits for deployment
		peersToCheck.putAll(peers);
			//Extract Peer Info from user selection
			String selPeer = dcvMap.get(selected).getSelect().getValue().toString();
			System.err.println("The user selected peer info: "+selPeer);
			String key = selPeer.substring(0, selPeer.indexOf("="));
			String id = peers.get(key).getPeerID();
			PeerRole role = peers.get(key).getRole();
			System.err.println("Peer-ROLE: "+role);	
			System.err.println("ID: "+id);
			System.err.println(selPeer);
			PeerCard peer = new PeerCard(id, role);
			if(checkDeployementUnit(uapp.getPart().getDeploymentUnit(), peer)) {
				System.err.println("In CHECKDEPLOYMENTUNIT!");
				mapLayout.put(peer, uapp.getPart());
				peersToCheck.remove(key);
			} else {
				app.getMainWindow().showNotification(bundle.getString("peer.available.not"), Notification.TYPE_WARNING_MESSAGE);
			}
	
		return mapLayout;
	}

	public static boolean checkDeployementUnit(List<DeploymentUnit> list, PeerCard peer){
    	String osUnit;
    	String pUnit;
		for(DeploymentUnit deployementUnit: list){
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
