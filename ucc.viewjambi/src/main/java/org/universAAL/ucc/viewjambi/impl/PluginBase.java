package org.universAAL.ucc.viewjambi.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.universAAL.ucc.api.plugin.IPlugin;
import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.api.plugin.PluginGridViewItem;
import org.universAAL.ucc.api.plugin.PluginMenu;
import org.universAAL.ucc.api.plugin.PluginMenuEntry;
import org.universAAL.ucc.api.plugin.PluginMenuSimpleEntry;
import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.viewjambi.overview.GridView;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMenu;

public class PluginBase extends QObject implements IPluginBase {
	
	private GridView gv=null;
	private ArrayList<IPlugin> plugins = new ArrayList<IPlugin>();
	private Hashtable<QAction, Runnable> pluginMenuActions = new Hashtable<QAction, Runnable>();
	
	public PluginBase(GridView gv) {
		this.gv=gv;
	}

	public void registerPlugin(final IPlugin plugin) {
		QApplication.invokeLater(new Runnable() {
			public void run() {
				plugin.registerViews();
			}});
		this.plugins.add(plugin);
	}

	public void unregisterPlugin(final IPlugin plugin) {
		this.plugins.remove(plugin);
	}

	public void addMenu(final PluginMenu menu) {
		final PluginBase base = this;
		QApplication.invokeLater(new Runnable() {
			public void run() {
				MainWindow mainWindow = MainWindow.getInstance();
				QMenu newMenu = new QMenu(menu.getCaption());
				for (Iterator<PluginMenuEntry> i = menu.getEntries(); i.hasNext(); ) {
					PluginMenuEntry entry = i.next();
					switch (entry.getType()) {
						case SEPERATOR : newMenu.addSeparator();
						case SIMPLE_TEXT :  {
							PluginMenuSimpleEntry sEntry = (PluginMenuSimpleEntry)entry;
							QAction action = new QAction(sEntry.getCaption(), newMenu);
							pluginMenuActions.put(action, sEntry.getToBeDoneAtClick());
							action.triggered.connect(base, "pluginActionClicked(Boolean)");
							newMenu.addAction(action);
						}
					}
				}
				mainWindow.menuBar().addMenu(newMenu);
			}}
		);
	}
	public void addGridItem(final PluginGridViewItem item){
		QApplication.invokeLater(new Runnable() {
			public void run() {
				gv.addItem(item);
			}}
		);
		
	}
	
	public IMainWindow getMainView() {
		return MainWindow.getInstance();
	}
	
	protected void pluginActionClicked(Boolean value) {
		Object object = QObject.signalSender();
		if (object == null || !(object instanceof QAction)) {
			System.err.println("Object have to be of type QAction!");
			return;
		}
		Runnable toExec = this.pluginMenuActions.get(object);
		if (toExec == null) {
			System.err.println("Action not registered!");
			return;
		}
		toExec.run();
	}
}
