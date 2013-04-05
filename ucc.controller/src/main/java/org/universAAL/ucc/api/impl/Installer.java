package org.universAAL.ucc.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.universAAL.ucc.api.IInstaller;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.middleware.managers.api.InstallationResultsDetails;
import org.universAAL.middleware.managers.api.UAPPPackage;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.interfaces.PeerRole;
import org.universAAL.middleware.managers.api.AALSpaceManager;
import org.universAAL.middleware.managers.api.DeployManager;
import org.universAAL.ucc.controller.Activator;

public class Installer implements IInstaller {

	/**
	 * get peers in AALSpace from the AALSpaceManager
	 * 
	 */
	public Map<String, PeerCard> getPeers() {	
		AALSpaceManager aalSpaceManager = Activator.getAALSpaceManager();
		Map peers = new HashMap();
		if (aalSpaceManager!=null) {
			peers = aalSpaceManager.getPeers();
			System.out.println("[Installer.getPeers()] " + peers.toString());
		} else {
			// use faked data to test without really connected to DeployManager
			PeerCard card= new PeerCard(PeerRole.PEER, "karaf", "Java");
			System.out.println("[Installer.getPeers] peerCard1 for testing: " + card.getPeerID() + "/"
					+ card.getOS() + "/" + card.getPLATFORM_UNIT() + "/" + card.getCONTAINER_UNIT() + "/" + card.getRole());
			peers.put("Node1", card);
			card= new PeerCard(PeerRole.PEER, "karaf", "Java");		// to have a different unique PeerId	
			peers.put("Node2", card);
			System.out.println("[Installer.getPeers] peerCard2 for testing: " + card.getPeerID() + "/"
					+ card.getOS() + "/" + card.getPLATFORM_UNIT() + "/" + card.getCONTAINER_UNIT() + "/" + card.getRole());
			card= new PeerCard(PeerRole.PEER, "karaf", "C++");		// to have a different unique PeerId	
			peers.put("Node3", card);
			System.out.println("[Installer.getPeers] peerCard3 for testing: " + card.getPeerID() + "/"
					+ card.getOS() + "/" + card.getPLATFORM_UNIT() + "/" + card.getCONTAINER_UNIT() + "/" + card.getRole());
		}
		return peers;
	}


	public InstallationResultsDetails requestToInstall(UAPPPackage app) {
		DeployManager deployManager = Activator.getDeployManager();
		if (deployManager==null) {
			System.out.println("[Installer.requestToInstall] DeployManager is null!");
			return null;
		}
		System.err.println("APP-ID: "+app.getId());
		System.err.println("SERVICE-ID: "+app.getServiceId());
		System.err.println("APP-LOcation: "+app.getFolder().getPath());
		InstallationResultsDetails results = deployManager.requestToInstall(app);
		System.out.println("[Installer.requestToInstall] the installation results: " + results.getGlobalResult().toString());
		return results;
	}


}
