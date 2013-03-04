package org.universAAL.ucc.api.model;

import java.util.Map;

public interface IServiceRegistration {

	public boolean unregisterService(String serviceId);
	public boolean registerApp(String sericeId, String appId);
    public boolean registerService(String appName, Map<String, String> configuration);
	
}
