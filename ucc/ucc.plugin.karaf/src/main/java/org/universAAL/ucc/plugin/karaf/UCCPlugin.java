package org.universAAL.ucc.plugin.karaf;

import org.universAAL.ucc.api.plugin.IPlugin;
import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.api.plugin.PluginGridViewItem;
import org.universAAL.ucc.api.plugin.PluginMenu;
import org.universAAL.ucc.plugin.karaf.gui.KarafDeployView;

/**
 * 
 * @author shanshan
 * @version 1.0
 * @created 14.05.2012
 * 
 *
 */
public class UCCPlugin implements IPlugin {
	
	private PluginMenu menu = new PluginMenu("Plugin Karaf");
	private KarafDeployView karafView = null;
	private IPluginBase uCCPluginBase = null;
	
	
	public UCCPlugin(IPluginBase uCCPluginBase) {
		this.uCCPluginBase = uCCPluginBase;
		createMenu();
		createGridViewItem();
	}

	public void registerViews() {
		uCCPluginBase.addMenu(menu);
		
		karafView = new KarafDeployView(uCCPluginBase);
		uCCPluginBase.getMainView().hideSubWindow(karafView);
	}
	
	public void createMenu() {
		menu.addEntry("Deploy To Karaf", new Runnable() {

			public void run() {
				uCCPluginBase.getMainView().showSubWindow(karafView);
			}
			
		});
	}
	public void createGridViewItem(){
		PluginGridViewItem gv= new PluginGridViewItem("Deploy To\nKaraf", "addressbook2.png",	new Runnable() {

			public void run() {
				uCCPluginBase.getMainView().showSubWindow(karafView);
			}
			
		});
		uCCPluginBase.addGridItem(gv);
	}
}
