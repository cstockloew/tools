package org.universAAL.ucc.plugin.information.gui;

import java.util.Date;

import org.osgi.framework.Bundle;
import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.plugin.information.Activator;
import org.universAAL.ucc.plugin.information.gui.juic.Ui_SystemInformation;
import org.universAAL.ucc.viewjambi.common.SubWindow;

import com.trolltech.qt.gui.QTableWidgetItem;

public class InformationView extends SubWindow {

	private static Ui_SystemInformation information_base = new Ui_SystemInformation();
	
	private IPluginBase uCCPluginBase = null;

	public InformationView(IPluginBase uCCPluginBase) {
		super(InformationView.information_base);
		this.uCCPluginBase = uCCPluginBase;

		Bundle[] bundles = Activator.getInformation().bundles();

		for (int i = 0; i < bundles.length; i++) {
			information_base.bundleList.insertRow(i);
			information_base.bundleList.setItem(i, 0, new QTableWidgetItem(
					bundles[i].getSymbolicName()));
			information_base.bundleList.setItem(i, 1, new QTableWidgetItem(
					(new Date(bundles[i].getLastModified())).toString()));
			information_base.bundleList.setItem(i, 2, new QTableWidgetItem(
					statusToString(bundles[i].getState())));
			information_base.bundleList.setItem(i, 3, new QTableWidgetItem(
					bundles[i].getLocation()));
		}

		information_base.closeButton.clicked.connect(this, "closeMe()");
	}

	protected void closeMe() {
		this.uCCPluginBase.getMainView().hideSubWindow(this);
	}

	protected String statusToString(int status) {
		switch (status) {
		case Bundle.ACTIVE:
			return "Active";
		case Bundle.INSTALLED:
			return "Installed";
		case Bundle.RESOLVED:
			return "Resolved";
		case Bundle.STARTING:
			return "Starting";
		case Bundle.STOPPING:
			return "Stopping";
		case Bundle.UNINSTALLED:
			return "Uninstalled";
		}
		return "Unknown";
	}
}
