package org.universAAL.ucc.api;

public interface IDeinstaller {
	/**
	 * uninstall a service from uStore
	 * @param serviceId
	 * @return
	 */
	public String uninstallServiceFromOnlineStore(String serviceId);
}
