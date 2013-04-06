package org.universAAL.ucc.deploymanagerservice.impl;

import org.universAAL.ucc.deploymanagerservice.DeployManagerService;
import org.universAAL.ucc.frontend.api.IFrontend;
import org.universAAL.ucc.frontend.api.impl.FrontendImpl;

public class DeployManagerImpl implements DeployManagerService {
    private IFrontend front;

    public DeployManagerImpl() {
	front = new FrontendImpl();

    }

    public void install(String sessionKey, String usrvfile) {
	// ToDo installation process. Showing license agreement etc.
	// after usrv file was downloaded then calling IInstaller Service to
	// install to nodes
	front.installService(sessionKey, usrvfile);
    }

    public void update(String sessionKey, String usrvfile) {
	// TODO Auto-generated method stub

    }

    public void uninstall(String sessionKey, String serviceId) {
	// TODO Auto-generated method stub

    }

    public String getInstalledServices(String sessionKey) {
	// TODO Auto-generated method stub
	return null;
    }

    public String getInstalledUnitsForService(String sessionKey,
	    String serviceId) {
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
