/*
	Copyright 2007-2014 FZI, http://www.fzi.de
	Forschungszentrum Informatik - Information Process Engineering (IPE)

	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package org.universAAL.ucc.viewjambi.information;

import java.util.Date;

import org.osgi.framework.Bundle;
import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_SystemInformation;

import com.trolltech.qt.gui.QTableWidgetItem;

/**
 * 
 * @author tzentek - <a href="mailto:zentek@fzi.de">Tom Zentek</a>
 * 
 */

public class InformationView extends SubWindow {

	private static Ui_SystemInformation information_base = new Ui_SystemInformation();

	public InformationView() {
		super(InformationView.information_base);

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
		MainWindow.getInstance().hideSubWindow(this);
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
