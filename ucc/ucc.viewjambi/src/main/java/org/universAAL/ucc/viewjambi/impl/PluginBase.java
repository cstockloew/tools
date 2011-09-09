package org.universAAL.ucc.viewjambi.impl;

import java.util.ArrayList;

import org.universAAL.ucc.api.plugin.IPlugin;
import org.universAAL.ucc.api.plugin.IPluginBase;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMenu;

public class PluginBase extends QSignalEmitter implements IPluginBase {
	
	ArrayList<IPlugin> plugins = new ArrayList<IPlugin>();
	
	public PluginBase() {
		
	}

	public void registerPlugin(final IPlugin plugin) {
		QApplication.invokeLater(new Runnable() {
			public void run() {
				plugin.registerViews();
				//QMenu menu = new QMenu("Test");
				//menu.addAction(new QAction("Click Me", menu));
			}});
		this.plugins.add(plugin);
	}

	public void unregisterPlugin(final IPlugin plugin) {
		this.plugins.remove(plugin);
	}
}
