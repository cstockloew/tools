package org.universAAL.ucc.deploymanagerservice.impl;

import org.universAAL.ucc.deploymanagerservice.DeployManagerService;

/**
 * Implemenation of the DeployManager Web service
 * 
 * @author sji
 * @modified by Nicole Merkle
 * 
 */

public class DeployManagerServiceImpl implements DeployManagerService {

	// private IInstaller installer;
	public void install(String sessionKey, String serviceId, String serviceLink) {
		System.out
				.println("[DeployManagerServiceImpl] Install with sessionKey: "
						+ sessionKey + " for Service-ID: " + serviceId
						+ " and URL: " + serviceLink);
		Activator.getFrontend().installService(sessionKey, serviceId,
				serviceLink);
		// TODO what check should be done with the sessionKey?
	}

	public void update(String sessionKey, String serviceId, String serviceLink) {
		System.out.println("[DeployManagerService] Update with sessionKey: "
				+ sessionKey + " for serviceId: " + serviceId);
		Activator.getFrontend().update(sessionKey, serviceId, serviceLink);
	}

	public void uninstall(String sessionKey, String serviceId) {
		System.out
				.println("[DeployManagerServiceImpl] uninstall of serviceId: "
						+ serviceId);
		Activator.getFrontend().uninstallService(sessionKey, serviceId);
	}

	public String getInstalledServices(String sessionKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInstalledUnitsForService(String sessionKey,
			String serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAALSpaceProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserProfile(String sessionKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSessionKey(String userName, String password) {
		String sessionKey = Activator.getFrontend().getSessionKey(userName,
				password);
		return sessionKey;
	}

	public void update(String sessionKey, String usrvfile) {
		// TODO Auto-generated method stub

	}

}