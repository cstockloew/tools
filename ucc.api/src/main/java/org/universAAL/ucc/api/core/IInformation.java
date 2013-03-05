package org.universAAL.ucc.api.core;

import org.osgi.framework.Bundle;

public interface IInformation {
	
	public String[] activeBundles();
	public Bundle[] bundles();
	public void setRunDir(String path);
	public String getRunDir();
	public String getBundleDir();
	
}
