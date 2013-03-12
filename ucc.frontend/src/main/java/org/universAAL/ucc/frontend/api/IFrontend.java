package org.universAAL.ucc.frontend.api;

public interface IFrontend {
	public void installService(String downloadUri, String usrvName);
	public void deinstallService(String serviceId);

}
