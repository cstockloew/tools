package org.universAAL.ucc.api.view;

/**
 * @author Alex
 * @version 1.0
 * @created 11-Jul-2011 16:45:44
 */
public interface IMainWindow {

	/**
	 * 
	 * @param subWindow
	 */
	public void addSubWindow(ISubWindow subWindow);

	/**
	 * 
	 * @param subWindow
	 */
	public void closeSubWindow(ISubWindow subWindow);

	public boolean initialize();

}