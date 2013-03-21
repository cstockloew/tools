package org.universAAL.ucc.service.api;

import java.util.List;
import java.util.Map;

public interface IServiceManagement {

	public String getInstalledServices();
	public String getInstalledUnitsForService(String serviceId);
	public List<String> getInstalledApps(String serviceId);
}
