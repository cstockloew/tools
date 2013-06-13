package org.universAAL.ucc.controller.install;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.interfaces.PeerRole;
import org.universAAL.middleware.deploymanager.uapp.model.Part;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.middleware.managers.api.InstallationResultsDetails;
import org.universAAL.middleware.managers.api.MatchingResult;
import org.universAAL.middleware.managers.api.UAPPPackage;
import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.configuration.configdefinitionregistry.interfaces.ConfigurationDefinitionRegistry;
import org.universAAL.ucc.configuration.model.configurationdefinition.Configuration;
import org.universAAL.ucc.configuration.view.ConfigurationOverviewWindow;
import org.universAAL.ucc.frontend.api.impl.FrontendImpl;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.UAPPPart;
import org.universAAL.ucc.model.UAPPReqAtom;
import org.universAAL.ucc.service.api.IServiceRegistration;
import org.universAAL.ucc.service.manager.Activator;
import org.universAAL.ucc.windows.DeployConfigView;
import org.universAAL.ucc.windows.DeployStrategyView;
import org.universAAL.ucc.windows.DeploymentInformationView;
import org.universAAL.ucc.windows.NoConfigurationWindow;
import org.universAAL.ucc.windows.UccUI;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class DeploymentInfoController implements Button.ClickListener,
		ValueChangeListener {
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
	private static IInstaller installer;
	private IServiceRegistration srvRegistration;
	private BundleContext bc;
//	private List<Part>parts;
	private Map<Part, List<PeerCard>> mapLayout;

	public DeploymentInfoController(UccUI app, AALService aal,
			DeploymentInformationView win) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		installer = Activator.getInstaller();
		srvRegistration = Activator.getReg();
		bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		this.aal = aal;
		this.win = win;
		this.app = app;
//		parts = new ArrayList<Part>();
		dsvMap = new HashMap<String, DeployStrategyView>();
		dcvMap = new HashMap<String, DeployConfigView>();
		mapLayout = new HashMap<Part, List<PeerCard>>();
		int i = 0;

		for (UAPPPart uapp : aal.getUaapList()) {
			i++;
			if (i == 1) {
				selected = uapp.getPart().getPartId();
			}
			System.err.println(uapp.getPart().getPartId() + " "+aal.getUaapList().size());
			win.getTree().addItem(uapp.getPart().getPartId());
			win.getTree().setChildrenAllowed(uapp.getPart().getPartId(), false);
			DeployStrategyView dsv = new DeployStrategyView(aal.getName(),
					aal.getServiceId(), uapp.getUappLocation());
			dsv.getOptions().addListener(this);
			dsvMap.put(uapp.getPart().getPartId(), dsv);

			DeployConfigView dcv = new DeployConfigView(app,
					aal.getServiceId(), uapp.getUappLocation());
			dcv.getTxt().setValue(uapp.getPart().getPartId());
			dcv.getTxt().setEnabled(false);
			System.err.println("Befor getValidPeers()");
			//Insert valid PeerNodes to dropbox of DeployConfigView
			List<PeerCard> validpeers = getValidPeers(uapp.getReqAtoms(), uapp.getPart().getPartId());
			System.err.println("In validpeers size: "+validpeers.size());
			for(PeerCard pc : validpeers) {
				if (pc != null) {
					System.err.println("Valid peers are available");
					String all = pc.toString();
					String item = all.substring(all.indexOf("=") + 1);
					dcv.getPeerNodes().put(item, all);
					dcv.getSelect().addItem(item);
				}
			}
			
			dcv.getSelect().setEnabled(false);
			dcv.setEnabled(false);
			dcvMap.put(uapp.getPart().getPartId(), dcv);
		}
		win.getTree().select(selected);
		for (UAPPPart ua : aal.getUaapList()) {
			if (ua.getPart().getPartId().equals(selected)) {
				DeployStrategyView dsv = dsvMap.get(ua.getPart().getPartId());
				DeployConfigView dcv = dcvMap.get(ua.getPart().getPartId());
				actVL = win.createSecondComponent(dsv, dcv);
			}
		}
		win.getTree().addListener(this);
		win.getOk().addListener((Button.ClickListener) this);
		win.getCancel().addListener((Button.ClickListener) this);
		win.createFirstComponent(win.getTree());
	}

	public void buttonClick(ClickEvent event) {
		if (event.getButton() == win.getOk()) {
			Map<Part, List<PeerCard>> config = null;
			// TODO: Deployment
			peers = installer.getPeers();
			for (UAPPPart uapp : aal.getUaapList()) {

				// Selected part in tree
				if (uapp.getPart().getPartId().equals(selected)) {
					
					// Default Deployment Strategy
					if (dsvMap.get(selected).getOptions().getValue().toString()
							.equals(bundle.getString("opt.available.nodes"))) {
						config = buildDefaultInstallationLayout(uapp);
					}
					// User selection strategy
					else if (dsvMap.get(selected).getOptions().getValue()
							.toString()
							.equals(bundle.getString("opt.selected.nodes"))) {
						System.err.println("User Installation for part: "+uapp.getPart().getPartId());
						config = buildUserInstallationLayout(uapp);
						if (config.isEmpty())
							return;
					}

				}
			}
			// Remove installed part view and item from tree

				win.getHp().removeComponent(actVL);
				win.getTree().removeListener(this);
				win.getTree().removeItem(selected);
				win.getTree().addListener(this);
				System.err.println("Selected and removed node is: " + selected);
				System.err.println("Tree node was removed");
				dsvMap.remove(selected);
				dcvMap.remove(selected);

			//All parts are processed
			if (dsvMap.isEmpty() && dcvMap.isEmpty()) {
			
				//Change Map<Part, List<PeerCard>> to Map<PeerCard, List<Part>>
				Map<PeerCard, List<Part>> peerMap = new HashMap<PeerCard, List<Part>>();
				for(Part key : config.keySet()) {
					List<PeerCard> val = config.get(key);
					for(PeerCard pc : val) {
						System.err.println("PEER-ID: "+pc.getPeerID());
						if(!peerMap.containsKey(pc)) {
							peerMap.put(pc, new ArrayList<Part>());
						} peerMap.get(pc).add(key);
					}
					
				}
				// Deploy to MW
				for(UAPPPart uapp : aal.getUaapList()) {
				UAPPPackage uapack = null;
				// Get uapp location uri
				String appLocation = uapp.getUappLocation();
				System.err.println("THE UAPP_LOCATION: "+uapp.getUappLocation());
				String p = appLocation.substring(appLocation.indexOf("bin/"));
				appLocation = FrontendImpl.getUappURI();
				System.err.println("LOCATION URI: "+appLocation);
				File uf  = new File(appLocation.trim());
				for (PeerCard pc: peerMap.keySet()) {
					List<Part> parts = peerMap.get(pc);
					
					System.out.println("[deploymentInfoController.peerMap] for peer with id: " + pc.getPeerID());
					for (int i=0; i<parts.size(); i++) 
						System.out.println("[deploymentInfoController.peerMap] has part: " + parts.get(i).getPartId());
				}
				
				uapack = new UAPPPackage(aal.getServiceId(),
						uapp.getAppId(), uf.toURI(), peerMap);
				// Not work with uri, MW not implemented yet
				 InstallationResultsDetails res = installer.requestToInstall(uapack);
				 // add app and bundles to "services.xml" file.
				 System.err.println("The GLOBAL RESULT: "+res.getGlobalResult().toString());
				 System.err.println("Service ID: "+aal.getServiceId()+ " App ID: "+uapp.getAppId()+" Bundle ID: "+uapp.getBundleId()+ "Bundle Version: "+uapp.getBundleId());
				if (res.getGlobalResult().toString().equals(InstallationResults.SUCCESS.name())) {
					srvRegistration.registerApp(aal.getServiceId(), 
							uapp.getAppId());
					// get bundles for each part in the appId;
					// for each bundle:
					srvRegistration.registerBundle(aal.getServiceId(),
							uapp.getBundleId(), uapp.getBundleVersion());
					// TODO: Call configurator to configure the uapps, after
					// uapp is running (for every uapp)
					ServiceReference configRef = bc
							.getServiceReference(ConfigurationDefinitionRegistry.class
									.getName());
					ConfigurationDefinitionRegistry reg = (ConfigurationDefinitionRegistry) bc
							.getService(configRef);
					Configuration conf = null;
					System.err.println("Size of all APP-Configurations: "
							+ reg.getAllConfigDefinitions().size());
					for (Configuration configurator : reg
							.getAllConfigDefinitions()) {
						System.err.println(uapp.getBundleId()+" + "+configurator.getBundlename());
						if (configurator.getBundlename() != null
								&& configurator.getBundlename().equals(
										uapp.getBundleId())) {
							conf = configurator;
						}
					}

					if (conf != null) {
						ConfigurationOverviewWindow cow = new ConfigurationOverviewWindow(
								conf);
						cow.center();
						app.getMainWindow().addWindow(cow);
					} else {
						NoConfigurationWindow ncw = new NoConfigurationWindow(
								bundle.getString("installed.note"));
						app.getMainWindow().addWindow(ncw);
					}
					bc.ungetService(configRef);

				} else if(res.getGlobalResult().toString().equals(InstallationResults.APPLICATION_ALREADY_INSTALLED.name())){
					// get parts mapping from config
					System.out.println("[DeploymentInfoController] global result: " + res.getGlobalResult().toString());
					for(Part key : config.keySet()) {
						List<PeerCard> pcards = config.get(key);
						for(PeerCard card: pcards) {
							System.out.println("[DeploymentInfoController] detailed results for part-" + key.getPartId()
									+ "/peer-" + card.getPeerID() + " is: " + res.getDetailedResult(card, key));
						}
					}
					NoConfigurationWindow ncw = new NoConfigurationWindow(
							bundle.getString("srv.already.exists"));
					app.getMainWindow().addWindow(ncw);
					
					
				} else {
					for(Part key : config.keySet()) {
						List<PeerCard> pcards = config.get(key);
						for(PeerCard card: pcards) {
							System.out.println("[DeploymentInfoController] detailed results for part-" + key.getPartId()
									+ "/peer-" + card.getPeerID() + " is: " + res.getDetailedResult(card, key));
						}
					}
					NoConfigurationWindow ncw = new NoConfigurationWindow(
							bundle.getString("install.error"));
					app.getMainWindow().addWindow(ncw);
					
					
				}
				app.getMainWindow().removeWindow(win);
				File f = new File(Activator.getModuleConfigHome().getAbsolutePath()
						+ "/tempUsrvFiles/");
				deleteFiles(f);

			}
			}

			selected = (String) win.getTree().getItemIds().iterator().next();
			win.getTree().select(selected);
		}

		// Installation was canceled by user
		if (event.getButton() == win.getCancel()) {
			app.getMainWindow().removeWindow(win);
			app.getMainWindow().showNotification(
					bundle.getString("break.note"),
					Notification.TYPE_HUMANIZED_MESSAGE);
			File f = new File(Activator.getModuleConfigHome().getAbsolutePath() + "/tempUsrvFiles/");
			deleteFiles(f);
		}
	}

	private void deleteFiles(File path) {
		File[] files = path.listFiles();
		for (File del : files) {
			if (del.isDirectory()
					&& !del.getPath().substring(del.getPath().lastIndexOf(".") + 1)
							.equals("usrv")) {
				deleteFiles(del);
			}
			if (!del.getPath().substring(del.getPath().lastIndexOf(".") + 1)
					.equals("usrv"))
				del.delete();
		}

	}

	public void valueChange(ValueChangeEvent event) {
		for (UAPPPart ua : aal.getUaapList()) {
			System.err.println(aal.getUaapList().size());
			if (ua.getPart().getPartId().equals(event.getProperty().toString())) {
				selected = event.getProperty().toString();
				DeployStrategyView dsv = dsvMap.get(ua.getPart().getPartId());
				DeployConfigView dcv = dcvMap.get(ua.getPart().getPartId());
				actVL = win.createSecondComponent(dsv, dcv);
			}
		}

		if (event.getProperty().getValue().toString()
				.equals(bundle.getString("opt.selected.nodes"))) {
			dcvMap.get(selected).setEnabled(true);
			dcvMap.get(selected).getTxt().setEnabled(true);
			dcvMap.get(selected).getSelect().setEnabled(true);
		}
		if (event.getProperty().getValue().toString()
				.equals(bundle.getString("opt.available.nodes"))) {
			dcvMap.get(selected).setEnabled(false);
			dcvMap.get(selected).getTxt().setEnabled(false);
			dcvMap.get(selected).getSelect().setEnabled(false);
		}

	}

	/*
	 * using the MW getMatchingPeers method
	 * 
	 */
	private Map<Part, List<PeerCard>> buildDefaultInstallationLayout(UAPPPart uapp) {
		System.out.println("[DeployInfoController] build default layout for: " + uapp.getPart().getPartId());
			List<PeerCard> validpeers = getValidPeers(uapp.getReqAtoms(), uapp.getPart().getPartId());
			if (validpeers.size()==0 || validpeers==null) {
				app.getMainWindow().showNotification(
						bundle.getString("peer.available.not"),
						Notification.TYPE_WARNING_MESSAGE);
				
			} else {
				// use any validpeer
				List<PeerCard> peerList = mapLayout.get(uapp.getPart());
				if (peerList==null)
					peerList = new ArrayList<PeerCard>();
				peerList.add(validpeers.get(0));
				System.out.println("[DeploymentInfoController] add peer " + validpeers.get(0).getPeerID() + " to " + uapp.getPart().getPartId());					
				mapLayout.put(uapp.getPart(), peerList);
			 
			}
		return mapLayout;
	}


	
	private Map<Part, List<PeerCard>> buildUserInstallationLayout(UAPPPart uapp) {
		Map<String, PeerCard> peersToCheck = new HashMap<String, PeerCard>();
		List<PeerCard> peerList = new ArrayList<PeerCard>();
		// Create peer from user selection and test if peer fits for deployment
		// Extract Peer Info from user selection
		if (dcvMap.get(selected).getSelect().getValue() != null
				&& !(dcvMap.get(selected).getSelect().getValue().toString()
						.equals(""))) {
			Collection<String> selPeer = (Collection<String>) dcvMap
					.get(selected).getSelect().getValue();
			System.out.println("[DeploymentInfoController.buildUserLayout] user has selected " + selPeer.toArray().length + " peers");
			for (int i = 0; i < selPeer.toArray().length; i++) {
				String value = dcvMap.get(selected).getPeerNodes()
						.get(selPeer.toArray()[i]);
				System.err.println("The user selected value: " + value);
				String key = value.substring(value.indexOf(":")+1, value.lastIndexOf("-")).trim();
				System.err.println("KEY is: " + key);
				//Only testing
				for(String set : peers.keySet()) {
					System.err.println("Key in PEER: "+set);
				}
				//Only testing end
				String id = peers.get(key).getPeerID();
				PeerRole role = peers.get(key).getRole();
				System.err.println("Peer-ROLE: " + role);
				System.err.println("ID: " + id);
				PeerCard peer = new PeerCard(id, role);				
					System.err.println("[buildUserInstallationLayout] In CHECKDEPLOYMENTUNIT!");
					peerList.add(peer);
					System.out.println("[DeploymentInfoController.buildUserLayout] add one peer " + peer.getPeerID() + " to part " + uapp.getPart().getPartId());
					peersToCheck.remove(key);
			}
			mapLayout.put(uapp.getPart(), peerList);
			
			
		} else {
			app.getMainWindow().showNotification(
					bundle.getString("select.node.msg"),
					Notification.TYPE_ERROR_MESSAGE);

		}
		
		return mapLayout;
	}

	//TODO: update UAPPPart part info according to UAPP schema, to enable advanced check
	// Currently only simplified checks
	/**
	 * 
	 * @param reqs - a list of ReqAtom for a part
	 * @param partId - the partId to check
	 * @return
	 */
	public static List<PeerCard> getValidPeers(List<UAPPReqAtom> reqs, String PartId) {		
		// convert reqs to filter to call MW/AALSpaceManager		
		// the map for reqAtom value that is set other than equal
		List<UAPPReqAtom> toCheck = new ArrayList<UAPPReqAtom>();
		// the map to be sent to MW for fast equal checking
		Map<String, Serializable> filter = new HashMap<String, Serializable>();
		for (int i=0; i<reqs.size(); i++) {
			String reqname = reqs.get(i).getName();
			System.err.println("Name: "+reqs.get(i).getName() + "VALUE: "+reqs.get(i).getValue()+"Criteria: "+reqs.get(i).getCriteria());
			for(String s : reqs.get(i).getValue()) {
				String reqvalue = s;
				String reqcriteria = reqs.get(i).getCriteria();
				if (reqcriteria==null) {
					filter.put(reqname, reqvalue);
				} else {
					if (!reqcriteria.equals("equal")) {
						filter.put(reqname, null);
						// put the reqAtom for checking later;
						toCheck.add(reqs.get(i));	
					}
				}
			}
		}
		System.err.println("Before MATCHING PEERS");
		MatchingResult result = installer.getMatchingPeers(filter);
		System.err.println(result.getPeers().toString());
		PeerCard[] peers = result.getPeers();
		System.err.println("[[DeploymentInfoController: Peers-Length: ]] "+peers.length);
		List<PeerCard> validPeers = new ArrayList<PeerCard>();
		// check for non-equal criteria
		for (int i=0; i<peers.length; i++)  {
			boolean valid = true;
			Map<String, Serializable> attr = result.getPeerAttribute(peers[i]);
			System.err.println("[[DeploymentInfoController]] result.getPeerAttribute() size: "+attr.size());
			for (int j=0; j<toCheck.size(); j++)  {
				UAPPReqAtom req = toCheck.get(j);
				System.err.println("[[DeploymentInfoController]] Criteria: "+req.getCriteria());
				System.err.println("[[DeploymentInfoController]] Name: "+req.getName());
				System.err.println("[[DeploymentInfoController]] Values: "+req.getValue().toString());
				String reqname = reqs.get(i).getName();
				//one atom can have more than one value
				
				for(String sr : reqs.get(i).getValue()) {
					String reqvalue = sr;
					System.err.println("[[DeploymentInfoController]] atom value: "+sr);
					String reqcriteria = reqs.get(i).getCriteria();
					System.err.println("[[DeploymentInfoController]] current Criteria: "+reqcriteria);
					if(reqcriteria != null) {
					if (reqcriteria.equals("greater-equal"))  {
						System.err.println("[[DeploymentInfoController]] in greater-equal criteria ReqValue: "+reqvalue);
						int rvalue = Integer.parseInt(reqvalue);
						int peervalue = Integer.parseInt((String) attr.get(reqname));
						System.out.println("[checkPartRequirements] criteria is: " + reqcriteria + " part has req: " 
							+ rvalue + " the peer has req: " + peervalue);
						if (peervalue < rvalue) {
							// this peer is not eligible
							valid = false;
						}
					}
				}
				}
				// TODO: extend the list according to LogicalCriteriaType to have a complete check
					
			}
			if (valid)  {
				// this peer can be used
				validPeers.add(peers[i]);
				System.out.println("[checkPartRequirements] has a valid peer: " + peers[i].getPeerID());
			}
		}
		System.err.println("Out of getValidPeers()");
		return validPeers;
	}

}
