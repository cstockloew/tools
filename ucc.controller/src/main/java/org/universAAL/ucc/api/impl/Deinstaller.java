package org.universAAL.ucc.api.impl;

import org.universAAL.middleware.managers.api.DeployManager;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.ucc.api.IDeinstaller;
import org.universAAL.ucc.controller.Activator;

public class Deinstaller implements IDeinstaller {

	public InstallationResults requestToUninstall(String serviceId, String id) {
		DeployManager deployManager = Activator.getDeployManager();
		if (deployManager==null) {
			System.out.println("[Deinstaller.requestToUninstall] DeployManager is null!");
			return null;
		}
		
		InstallationResults results = deployManager.requestToUninstall(serviceId, id);
		System.out.println("[Deinstaller.requestToInstall] the uninstallation results: " + results.toString());
		return results;

	}
	
}
