package org.universAAL.ucc.api.core;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * The interface for uStore to interact with uCC (the local deploy manager)
 * @author sji
 *
 */
public interface IDeployManager {
	/**
	 * install a service for a user as specified in the .usrv file
	 * @param username
	 * @param password
	 * @param usrvfile: the link to download the .usrv file 
	 */
	public void install(String username, String password, URL usrvfile);
	
	/**
	 * update a service for a user as specified in the .usrv file
	 * @param username
	 * @param password
	 * @param usrvfile: the link to download the .usrv file
	 */
	public void update(String username, String password, URL usrvfile);
	
	/**
	 * uninstall a service 
	 * @param username
	 * @param password
	 * @param serviceId: the uStore service id for the service to be uninstalled 
	 * 
	 */
	public void uninstall(String username, String password, String serviceId, String serviceVersion);
	
	/**
	 * get all installed services for a user
	 * @param username
	 * @param password
	 * @return Map<serviceId, serviceVersion>: the list of services with uStore service Id and its version
	 */
	public 	Map<String, String> getInstalledServices(String username, String password);

	/**
	 * get the installed applications for a service 
	 * @param username
	 * @param password
	 * @param serviceId
	 * @return Map<applicationId, appVersion>: the list of applications with their version
	 */
	public Map<String, String> getInstalledApplications(String username, String password, String serviceId);

	/**
	 * get the AAL space profile 
	 * @return
	 * TODO: decide what to return, whether to use AALSpaceCard or AALSpaceDescriptor as defined in 
	 * the mw.interfaces.aalspace
	 */
	public List getAALSpaceProfile();
	
	/**
	 * 
	 * @param username
	 * @param password
	 * TODO: decide what to return, what info should be contained in a user profile 
	 */
	public List getUserProfile(String username, String password);
}
