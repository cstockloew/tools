package org.universAAL.ucc.api.model;

import java.util.List;
import java.util.Map;

public interface IApplicationManagment {

	public List<String> getInstalledApplications();
	public List<String> getAllInstalledBundles();
	public List<String> getInstalledBundles(String appName);
	public boolean containsApplication(String appName);
	public List<String> getBundles(String appName);
	public boolean isEmpty();
	public Map<String, String> getConfiguration(String bundleName);


	
}
