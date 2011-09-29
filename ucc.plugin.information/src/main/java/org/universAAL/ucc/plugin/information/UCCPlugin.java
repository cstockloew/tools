package org.universAAL.ucc.plugin.information;

import org.universAAL.ucc.api.plugin.IPlugin;
import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.api.plugin.PluginMenu;
import org.universAAL.ucc.plugin.information.gui.InformationView;

public class UCCPlugin implements IPlugin {
	
	private PluginMenu menu = new PluginMenu("Plugin System Info");
	private InformationView informationView = null;
	private IPluginBase uCCPluginBase = null;
	
	public UCCPlugin(IPluginBase uCCPluginBase) {
		createMenu();
		this.uCCPluginBase = uCCPluginBase;
	}

	public void registerViews() {
		uCCPluginBase.addMenu(menu);
		
		informationView = new InformationView(uCCPluginBase);
		uCCPluginBase.getMainView().hideSubWindow(informationView);
	}
	
	public void createMenu() {
		menu.addEntry("Show System Information", new Runnable() {

			public void run() {
				uCCPluginBase.getMainView().showSubWindow(informationView);
			}
			
		});
	}
}
