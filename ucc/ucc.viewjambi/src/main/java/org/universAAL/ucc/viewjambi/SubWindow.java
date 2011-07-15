package org.universAAL.ucc.viewjambi;

import org.universAAL.ucc.api.view.ISubWindow;

import com.trolltech.qt.QThread;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMdiSubWindow;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QVBoxLayout;

public class SubWindow extends QMdiSubWindow implements ISubWindow {
	
	private QVBoxLayout layout = new QVBoxLayout();
	private QLabel windowHeader = null;
	
	public SubWindow() {
		super();
		
		windowHeader = new QLabel(this);
		windowHeader.setText("This is a test!");
		windowHeader.setPixmap(new QPixmap("classpath:images/uaal.png"));
		layout.addWidget(windowHeader);
		windowHeader.show();
		
		this.layout().addItem(layout);
	}

	public void initialize() {
		this.show();
	}

}
