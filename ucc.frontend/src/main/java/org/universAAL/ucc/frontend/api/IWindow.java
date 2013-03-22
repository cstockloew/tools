package org.universAAL.ucc.frontend.api;

import org.universAAL.ucc.model.AALService;


public interface IWindow {
//	public void installProcess(String usrvPath);
	public void getDeployStratgyView(String name, String serviceId, String uappLocation, int index, AALService aal);
	public void getDeployConfigView(AALService aal, int index, boolean isLastPart);

}
