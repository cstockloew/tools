package org.universAAL.ucc.plugin.ustore;

import org.universAAL.ucc.api.plugin.IPlugin;
import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.api.plugin.PluginGridViewItem;
import org.universAAL.ucc.api.plugin.PluginMenu;
import org.universAAL.ucc.plugin.ustore.gui.StoreView;

public class UCCPlugin implements IPlugin {
	
	private PluginMenu menu = new PluginMenu("Plugin uStore");
	private StoreView storeView = null;
	private IPluginBase uCCPluginBase = null;
	
	
	public UCCPlugin(IPluginBase uCCPluginBase) {
		this.uCCPluginBase = uCCPluginBase;
		createMenu();
		createGridViewItem();
	}

	public void registerViews() {
		uCCPluginBase.addMenu(menu);
		
		storeView = new StoreView(uCCPluginBase);
		uCCPluginBase.getMainView().hideSubWindow(storeView);
	}
	
	public void createMenu() {
		menu.addEntry("Open uStore", new Runnable() {

			public void run() {
				uCCPluginBase.getMainView().showSubWindow(storeView);
			}
			
		});
	}
	public void createGridViewItem(){
		PluginGridViewItem gv= new PluginGridViewItem("uStore", "ustore.png",	new Runnable() {

			public void run() {
				uCCPluginBase.getMainView().showSubWindow(storeView);
			}
			
		});
		uCCPluginBase.addGridItem(gv);
	}
}
