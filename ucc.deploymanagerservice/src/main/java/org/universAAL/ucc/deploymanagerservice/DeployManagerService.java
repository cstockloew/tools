package org.universAAL.ucc.deploymanagerservice;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * The interface for uStore to interact with uCC (the local deploy manager)
 * @author sji
 *
 */
@WebService(serviceName = "DeployManagerService", portName = "DeployManagerServicePort")
public interface DeployManagerService {
	/**
	 * install a service as specified in the .usrv file
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @param usrvfile: the link to download the .usrv file, serviceId from uStore is provided in .usrv file. 
	 */
	public void install(@WebParam(name = "sessionKey") String sessionKey, @WebParam(name = "usrvfile") URL usrvfile);
	
	/**
	 * update a service as specified in the .usrv file
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @param usrvfile: the link to download the .usrv file, serviceId from uStore is provided in .usrv file. 
	 */
	public void update(@WebParam(name = "sessionKey") String sessionKey, @WebParam(name = "usrvfile") URL usrvfile);
	
	/**
	 * uninstall a service 
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @param serviceId: the uStore service id for the service to be uninstalled 
	 *  
	 */
	public void uninstall(@WebParam(name = "sessionKey") String sessionKey, @WebParam(name = "serviceId") String serviceId);
	
	/**
	 * get all installed services 
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @return List<serviceId>: list of uStore service Ids
	 */
	public 	List<String> getInstalledServices(@WebParam(name = "sessionKey") String sessionKey);

	/**
	 * get the installed application units for a service 
	 * @param sessionKey: the sessionKey for the interaction obtained when uCC registers with uStore 
	 * @param serviceId
	 * @return Map<bundleId, bundleVersion>: the list of application bundles with their version
	 * 
	 */
	public Map<String, String> getInstalledUnitsForService(@WebParam(name = "sessionKey") String sessionKey, @WebParam(name = "serviceId") String serviceId);

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
	public List getUserProfile(@WebParam(name = "sessionKey") String sessionKey);
}
