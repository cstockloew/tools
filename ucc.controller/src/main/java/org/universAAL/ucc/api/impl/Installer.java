package org.universAAL.ucc.api.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.osgi.framework.Bundle;
//import org.universAAL.middleware.managers.api.InstallationResults;
//import org.universAAL.middleware.managers.api.UAPPPackage;
import org.universAAL.ucc.api.IInstaller;

public class Installer implements IInstaller {

	@Override
	public String installApplication(String path) throws Exception {
		//
		return null;
	}

	@Override
	public ArrayList<Bundle> getInstalledBundles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetBundles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revertInstallation(File folder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map getPeers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String installService(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String installServiceFromOnlineStore(String path) {
		System.out.println("[Installer.installServiceFromOnlineStore] start installation of service from OnlineStore");
		// TODO: parse .usrv file and call MW DeployManager
		
		return "Installation finished!";
	}

//	@Override
//	public InstallationResults requestToInstall(UAPPPackage app) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public InstallationResults requestToUninstall(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
