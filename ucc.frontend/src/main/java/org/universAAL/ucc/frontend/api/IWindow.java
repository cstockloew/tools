package org.universAAL.ucc.frontend.api;

import org.universAAL.ucc.model.UAPP;

public interface IWindow {
	public void getLicenseView(String licensePath);
	public void getDeployStratgyView(String name, String serviceId, String uappLocation, UAPP uapp);
	public void getDeployConfigView(UAPP uapp, boolean isLastPart);

}
