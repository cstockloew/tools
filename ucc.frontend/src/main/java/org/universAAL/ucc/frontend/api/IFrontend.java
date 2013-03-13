package org.universAAL.ucc.frontend.api;

import javax.jws.WebParam;

public interface IFrontend {
	public void installService(String sessionkey, String usrvfile);
	public void uninstallService(String sessionkey, String serviceId);
	public void update(String sessionKey, String usrvfile);
	public String getInstalledServices(String sessionKey);
	public String getInstalledUnitsForService(String sessionKey, String serviceId);
}
