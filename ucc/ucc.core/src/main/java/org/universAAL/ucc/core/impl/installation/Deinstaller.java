package org.universAAL.ucc.core.impl.installation;

import org.universAAL.ucc.core.api.IDeinstaller;

public class Deinstaller extends ApplicationManager implements IDeinstaller {

	public boolean deinstallAppication(String appName) {
		return false;
	}
	
	private boolean deinstallBundle(String path) {
		return false;
	}
	
	private boolean unregisterApp() {
		return false;
	}

}
