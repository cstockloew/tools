package org.universAAL.ucc.deploymanagerservice.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

//import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.deploymanagerservice.DeployManagerService;

/**
 * Implemenation of the DeployManager Web service
 * 
 * @author sji
 *
 */

public class DeployManagerServiceImpl implements DeployManagerService {

	//private IInstaller installer;
	public void install(String sessionKey, String usrvfile) {
		System.out.println("[DeployManagerServiceImpl] Install with sessionKey: " + sessionKey + " for URL: " + usrvfile);
		String fileOnHardDrive = "C:/tempUsrvFiles/&filename=corrected_hwo_usrv.usrv";
			fileOnHardDrive = fileOnHardDrive.replace("/", "\\"); 
			System.out.println("[DeployManagerServiceImpl] the file on the hard drive: " + fileOnHardDrive);
			Activator.getFrontend().installService(sessionKey, fileOnHardDrive);
			//System.out.println("[DeployManagerServiceImpl] the result for installation is: " + results);
	/*	}
		catch(MalformedURLException e) 
		{
			System.out.println("[ERROR] Malformed URL Exception for " + usrvfile);			
		} */
		// TODO what check should be done with the sessionKey?
		
	}

	public void update(String sessionKey, String usrvfile) {
		System.out.println("[DeployManagerService] Update with sessionKey: " + sessionKey + " for URL: " + usrvfile);
/*		try {
			URL usrvURL = new URL(usrvfile);
		}
		catch(MalformedURLException e) 
		{
			System.out.println("[ERROR] Malformed URL Exception for " + usrvfile);
		} */
		// TODO what check should be done with the sessionKey?
		// get serviceId
		// uninstall service first
		// just for testing
		uninstall(sessionKey, usrvfile);
		// install the updated service
	}

	public void uninstall(String sessionKey, String serviceId) {
		System.out.println("[DeployManagerServiceImpl] uninstall of serviceId: " + serviceId);
		Activator.getFrontend().uninstallService(sessionKey, serviceId);
		//System.out.println("[DeployManagerServiceImpl] the result for uninstallation is: " + results);
	}

	public String getInstalledServices(String sessionKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInstalledUnitsForService(String sessionKey, String serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAALSpaceProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserProfile(String sessionKey) {
		// TODO Auto-generated method stub
		return null;
	}
    
}