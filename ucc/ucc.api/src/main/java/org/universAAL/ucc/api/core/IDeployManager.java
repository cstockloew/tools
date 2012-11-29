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
	 * install a service as specified in the .usrv file
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @param usrvfile: the link to download the .usrv file, serviceId from uStore is provided in .usrv file. 
	 */
	public void install(String sessionKey, URL usrvfile);
	
	/**
	 * update a service as specified in the .usrv file
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @param usrvfile: the link to download the .usrv file, serviceId from uStore is provided in .usrv file. 
	 */
	public void update(String username, String password, URL usrvfile);
	
	/**
	 * uninstall a service 
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @param serviceId: the uStore service id for the service to be uninstalled 
	 *  
	 */
	public void uninstall(String sessionKey, String serviceId);
	
	/**
	 * get all installed services 
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @return Map<serviceId, serviceVersion>: the list of services with uStore service Id (and its version?)
	 */
	public 	Map<String, String> getInstalledServices(String sessionKey);

	/**
	 * get the installed application units for a service 
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @param serviceId
	 * @return Map<bundleId, bundleVersion>: the list of application bundles with their version
	 * 
	 */
	public Map<String, String> getInstalledUnitsForService(String sessionKey, String serviceId);

	/**
	 * get the AAL space profile 
	 * @return
	 * TODO: decide what to return, whether to use AALSpaceCard or AALSpaceDescriptor as defined in 
	 * the mw.interfaces.aalspace.
	 * Suggest to return a list of properties of capabilities and functionalities in the form of <propertyName, propValue, criteria>
	 */
	public List getAALSpaceProfile();
	
	/**
	 * 
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * TODO: decide what to return, what info should be contained in a user profile 
	 */
	public List getUserProfile(String sessionKey);
}
