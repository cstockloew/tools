package org.universAAL.ucc.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import org.osgi.framework.Bundle;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.middleware.managers.api.UAPPPackage;

public interface IInstaller {
	public String installApplication(String path) throws Exception;
	
	public ArrayList<Bundle> getInstalledBundles();
	
	public void resetBundles();
	
	public void revertInstallation(File folder);
	
	// interface with MW: call AALSpaceManager
	public Map getPeers();
	
	// interface with MW: call DeployManager
	public InstallationResults requestToInstall(UAPPPackage app);
	/**
	 * 
	 * @param id: the unique id of uAPP, use uapp:appId.
	 * @return
	 */
	public InstallationResults requestToUninstall(String id);

	
	public String installService(String path);
	// interface with WS uStore --> uCC
	/**
	 * installing a service from uStore
	 * @param path: the path for the downloaded .usrv file
	 *  
	 */
	public String installServiceFromOnlineStore(String path);
	
	
}



