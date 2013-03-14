package org.universAAL.ucc.api;

import org.universAAL.middleware.managers.api.InstallationResults;

public interface IDeinstaller {
	public InstallationResults requestToUninstall(String serviceId, String id);

}
