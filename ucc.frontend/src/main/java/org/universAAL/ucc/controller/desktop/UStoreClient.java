package org.universAAL.ucc.controller.desktop;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

public class UStoreClient {
	private static final QName SERVICE_NAME = new QName("http://tools.ustore.commerce.universAAL.org", "DeploymentManagerService");
	private static final QName PORT_NAME = new QName("http://tools.ustore.commerce.universAAL.org", "DeploymentManagerPort");
	private static UStoreClient ustoreClient;
	
	private UStoreClient() {
		Service service = Service.create(SERVICE_NAME);
		String endpointaddress = "http://localhost:80/universAAL/DeploymentManagerService";
		service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointaddress);
//		DeploymentManager dm = service.getPort(DeploymentManager.class);
	}
	
	/**
	 * Generates a single UStoreClient for the DeploymentManager 
	 * WebService.
	 * @return a single UStoreClient
	 */
	public static UStoreClient getUStoreClientInstance() {
		if(ustoreClient == null) {
			ustoreClient = new UStoreClient();
		} 
		return ustoreClient;
	}
	
	/**
	 * Registers the DeploymentManager to the uStore.
	 * @param username Username of the deployer
	 * @param password Password of the deployer
	 * @param ipAddress IP-Address of the deployer
	 * @param port Port of the Webservice
	 * @return sessionKey of the deployer
	 */
	public String registerDeploymentManager(String username, String password, String ipAddress, String port) {
		return null;
	}
	
	/**
	 * Gets the profile of the user.
	 * @param sessionKey Key of the current session
	 * @return userprofile
	 */
	public String getUserProfile(String sessionKey) {
		return null;
	}
	
	/**
	 * Gets all free and available services in the uStore.
	 * @param sessionKey Sessionkey of the current session
	 * @param isFitToUser shows, if services are fitting to end user
	 * @return all free services in the uStore
	 */
	public String getFreeAALServices(String sessionKey, boolean isFitToUser) {
		return null;
	}
	
	/**
	 * Gets all purchased AAL services.
	 * @param sessionKey the key of the current session
	 * @return all purchased AAL Services
	 */
	public String getPurchasedAALServices(String sessionKey) {
		return null;
		
	}

}
