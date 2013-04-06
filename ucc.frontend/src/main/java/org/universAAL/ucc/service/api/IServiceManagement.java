package org.universAAL.ucc.service.api;

import java.util.List;

public interface IServiceManagement {

    public String getInstalledServices();

    public String getInstalledUnitsForService(String serviceId);

    public List<String> getInstalledApps(String serviceId);
}
