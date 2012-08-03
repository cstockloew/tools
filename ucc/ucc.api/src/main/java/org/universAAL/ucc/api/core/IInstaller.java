package org.universAAL.ucc.api.core;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.universAAL.middleware.managers.api.InstallationResults;

public interface IInstaller {
	
	public String installApplication(String path) throws Exception;
	public ArrayList<Bundle> getInstalledBundles();
	public void resetBundles();
	public void revertInstallation(File folder);
	public Map getPeers();
	public InstallationResults requestToInstall(URI deployFolder, Map layout);
}
