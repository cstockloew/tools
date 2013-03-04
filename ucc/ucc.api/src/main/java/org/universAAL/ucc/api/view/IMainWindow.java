package org.universAAL.ucc.api.view;

import java.io.IOException;

/**
 * @author Alex
 * @version 1.0
 * @created 11-Jul-2011 16:45:44
 */
public interface IMainWindow {

	public void addSubWindow(ISubWindow subWindow);
	public void removeSubWindow(ISubWindow subWindow);
	
	public void showSubWindow(ISubWindow subWindow);
	public void hideSubWindow(ISubWindow subWindow);

	public boolean initialize();
	
	public void installApp(String path, String serviceId);
	public void showLicense(String path);
}