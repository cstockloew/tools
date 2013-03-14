package org.universAAL.ucc.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import org.osgi.framework.Bundle;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.middleware.managers.api.UAPPPackage;

public interface IInstaller {
	
	// interface with MW: call AALSpaceManager
	public Map getPeers();
	
	// interface with MW: call DeployManager
	public InstallationResults requestToInstall(UAPPPackage app);
	//public String requestToInstall(String app);
	
	
}



