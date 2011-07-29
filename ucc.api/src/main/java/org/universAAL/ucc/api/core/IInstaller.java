package org.universAAL.ucc.api.core;

import java.io.File;
import java.util.ArrayList;

import org.osgi.framework.Bundle;

public interface IInstaller {
	
	public String installApplication(String path) throws Exception;
	public ArrayList<Bundle> getInstalledBundles();
	public void resetBundles();
	public void revertInstallation(File folder);
	
}
