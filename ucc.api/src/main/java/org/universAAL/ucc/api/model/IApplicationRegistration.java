package org.universAAL.ucc.api.model;

import java.util.Map;

public interface IApplicationRegistration {

	public boolean unregisterApplication(String appName);
	public boolean unregisterBundle(String bundleName);
	public boolean registerBundle(String appName, String bundleName);
    public boolean registerApplication(String appName, Map<String, String> configuration);


	
}
