package org.universAAL.ucc.viewjambi;

import org.universAAL.ucc.viewapi.interfaces.ISubWindow;

import com.trolltech.qt.gui.QMdiSubWindow;

public class SubWindow extends QMdiSubWindow implements ISubWindow {
	
	public SubWindow() {
		super();
	}

	public void initialize() {
		this.show();
	}

}
