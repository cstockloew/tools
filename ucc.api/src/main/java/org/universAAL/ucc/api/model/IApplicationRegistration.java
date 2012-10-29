package org.universAAL.ucc.api.model;

import java.util.Map;

public interface IApplicationRegistration {

	public boolean unregisterApplication(String appName);
	public boolean unregisterBundle(String appName, String bundleName);
	public boolean registerBundle(String appName, String bundleName);
    public boolean registerApplication(String appName, Map<String, String> configuration);
    public boolean writeToConfigFile(String appName, String rundir);
    public boolean removeConfigFile(String appName, String rundir);
    public void removeFromBundlesFolder(String appName, String bundleDir);

	
}
