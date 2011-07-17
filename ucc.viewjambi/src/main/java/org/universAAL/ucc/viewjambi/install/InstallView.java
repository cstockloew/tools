package org.universAAL.ucc.viewjambi.install;

import org.universAAL.ucc.viewjambi.MainWindow;
import org.universAAL.ucc.viewjambi.SubWindow;

import com.trolltech.qt.gui.QLabel;

public class InstallView extends SubWindow {
	
	public InstallView(MainWindow parent) {
		super(parent);
		QLabel test = new QLabel(content);
	}
}
