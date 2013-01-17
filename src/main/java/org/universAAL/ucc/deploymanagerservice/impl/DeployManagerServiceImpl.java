package org.universAAL.ucc.deploymanagerservice.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.universAAL.ucc.deploymanagerservice.DeployManagerService;

/**
 * Implemenation of the DeployManager Web service
 * 
 * @author sji
 *
 */

public class DeployManagerServiceImpl implements DeployManagerService {

	public void install(String sessionKey, String usrvfile) {
		System.out.println("[DeployManagerService] Install with sessionKey: " + sessionKey + " for URL: " + usrvfile);
		try {
			URL usrvURL = new URL(usrvfile);
			//TODO download the files and call Installer.install(usrvURL)
		}
		catch(MalformedURLException e) 
		{
			System.out.println("[ERROR] Malformed URL Exception for " + usrvfile);			
		}
		// TODO what check should be done with the sessionKey?
		
	}

	public void update(String sessionKey, String usrvfile) {
		System.out.println("[DeployManagerService] Update with sessionKey: " + sessionKey + " for URL: " + usrvfile);
		try {
			URL usrvURL = new URL(usrvfile);
		}
		catch(MalformedURLException e) 
		{
			System.out.println("[ERROR] Malformed URL Exception for " + usrvfile);
		}
		// TODO what check should be done with the sessionKey?
	}

	public void uninstall(String sessionKey, String serviceId) {
		// TODO Auto-generated method stub
		
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