package org.universAAL.ucc.core.impl.installation;

public class ApplicationManager {

	public String[] getInstalledApps() {
		return new String[0];
	}
	
	protected String[] getRelatedFiles(String appName) {
		return new String[0];
	}
	
	protected String generateAppName(String path) {
		return path;
	}
	
}
