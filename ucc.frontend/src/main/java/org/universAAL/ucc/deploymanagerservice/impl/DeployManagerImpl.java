package org.universAAL.ucc.deploymanagerservice.impl;

import org.universAAL.ucc.deploymanagerservice.DeployManagerService;

public class DeployManagerImpl implements DeployManagerService{

	@Override
	public void install(String sessionKey, String usrvfile) {
		//ToDo installation process. Showing license agreement etc.
		//after usrv file was downloaded then calling IInstaller Service to
		//install to nodes
		
	}

	@Override
	public void update(String sessionKey, String usrvfile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uninstall(String sessionKey, String serviceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInstalledServices(String sessionKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInstalledUnitsForService(String sessionKey,
			String serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAALSpaceProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserProfile(String sessionKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
