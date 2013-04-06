package org.universAAL.ucc.api;

import java.util.Map;
import org.universAAL.middleware.managers.api.InstallationResultsDetails;
import org.universAAL.middleware.managers.api.UAPPPackage;

public interface IInstaller {

    // interface with MW: call AALSpaceManager
    public Map getPeers();

    // interface with MW: call DeployManager
    public InstallationResultsDetails requestToInstall(UAPPPackage app);
    // public String requestToInstall(String app);

}
