package org.universAAL.ucc.api;

import org.universAAL.middleware.managers.api.InstallationResultsDetails;

public interface IDeinstaller {
    public InstallationResultsDetails requestToUninstall(String serviceId,
	    String id);

}
