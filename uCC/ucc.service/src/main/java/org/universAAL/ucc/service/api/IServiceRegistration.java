package org.universAAL.ucc.service.api;

public interface IServiceRegistration {

	public boolean unregisterService(String serviceId);
	public boolean registerApp(String sericeId, String appId);
	public boolean registerBundle(String serviceId, String bundleId, String bundleVersion);
	//public boolean registerAppAndBundles(String sericeId, String appId, Map bundles);
	
}
