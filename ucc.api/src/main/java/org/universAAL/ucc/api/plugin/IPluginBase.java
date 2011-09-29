package org.universAAL.ucc.api.plugin;

import org.universAAL.ucc.api.view.IMainWindow;

public interface IPluginBase {

	public void registerPlugin(final IPlugin plugin);
	public void unregisterPlugin(final IPlugin plugin);
	
	public void addMenu(final PluginMenu menu);
	
	public IMainWindow getMainView();
	
}
