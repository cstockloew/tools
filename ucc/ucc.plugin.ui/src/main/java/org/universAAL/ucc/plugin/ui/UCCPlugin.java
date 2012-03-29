package org.universAAL.ucc.plugin.ui;

import org.universAAL.ucc.api.plugin.IPlugin;
import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.api.plugin.PluginGridViewItem;
import org.universAAL.ucc.api.plugin.PluginMenu;
import org.universAAL.ucc.plugin.ui.gui.UIWrapper;

public class UCCPlugin implements IPlugin {
	
	private PluginMenu menu = new PluginMenu("Plugin System Info");
	private UIWrapper informationView = null;
	private IPluginBase uCCPluginBase = null;
	
	
	public UCCPlugin(IPluginBase uCCPluginBase) {
		this.uCCPluginBase = uCCPluginBase;
		createMenu();
		createGridViewItem();
	}

	public void registerViews() {
		uCCPluginBase.addMenu(menu);
		
		informationView = new UIWrapper();
		uCCPluginBase.getMainView().hideSubWindow(informationView);
	}
	
	public void createMenu() {
		menu.addEntry("Show System Information", new Runnable() {

			public void run() {
				uCCPluginBase.getMainView().showSubWindow(informationView);
			}
			
		});
	}
	public void createGridViewItem(){
		PluginGridViewItem gv= new PluginGridViewItem("universAAL\nMain Menu", "nokia s60 2.png",	new Runnable() {

			public void run() {
				uCCPluginBase.getMainView().showSubWindow(informationView);
			}
			
		});
		uCCPluginBase.addGridItem(gv);
	}
}
