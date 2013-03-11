package org.universAAL.ucc.api.impl;

import org.universAAL.ucc.api.IDeinstaller;

public class Deinstaller implements IDeinstaller {

	@Override
	public String uninstallServiceFromOnlineStore(String serviceId) {
		System.out.println("[Deinstaller.uninstallServiceFromOnlineStore] start uninstallation of service from OnlineStore with serviceId: " + serviceId);
		// TODO: parse .usrv file and call MW DeployManager
		
		return "Uninstallation finished!";
	}

}
