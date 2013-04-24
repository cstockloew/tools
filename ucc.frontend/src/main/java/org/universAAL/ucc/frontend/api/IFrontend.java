package org.universAAL.ucc.frontend.api;


public interface IFrontend {
	public void installService(String sessionkey, String usrvFileUri);

	public void uninstallService(String sessionkey, String serviceId);

	public void update(String sessionKey, String usrvfile, String serviceId);

	public String getInstalledServices(String sessionKey);

	public String getInstalledUnitsForService(String sessionKey,
			String serviceId);
}
