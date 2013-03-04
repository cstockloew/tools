package org.universAAL.ucc.api.model;

import java.util.List;
import java.util.Map;

public interface IServiceManagment {

	public List<String> getInstalledServices();
	public List<String> getInstalledUnitsForService(String serviceId);
		
}
