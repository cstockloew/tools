package org.universAAL.ucc.plugin.information;

import org.universAAL.ucc.api.plugin.IPlugin;
import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.api.plugin.PluginGridViewItem;
import org.universAAL.ucc.api.plugin.PluginMenu;
import org.universAAL.ucc.plugin.information.gui.InformationView;

public class UCCPlugin implements IPlugin {
	
	private PluginMenu menu = new PluginMenu("Plugin System Info");
	private InformationView informationView = null;
	private IPluginBase uCCPluginBase = null;
	
	
	public UCCPlugin(IPluginBase uCCPluginBase) {
		this.uCCPluginBase = uCCPluginBase;
		createMenu();
		createGridViewItem();
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
	public void createGridViewItem(){
		PluginGridViewItem gv= new PluginGridViewItem("System\nInformation", "addressbook.png",	new Runnable() {

			public void run() {
				uCCPluginBase.getMainView().showSubWindow(informationView);
			}
			
		});
		uCCPluginBase.addGridItem(gv);
	}
}
