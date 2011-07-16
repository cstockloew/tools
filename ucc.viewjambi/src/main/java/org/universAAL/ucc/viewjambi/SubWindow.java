package org.universAAL.ucc.viewjambi;

import org.universAAL.ucc.api.view.ISubWindow;

import com.trolltech.qt.core.QFile;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMdiSubWindow;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QVBoxLayout;

public class SubWindow extends QMdiSubWindow implements ISubWindow {
	
	private QVBoxLayout layout = new QVBoxLayout();
	private QLabel windowHeader = null;
	
	public SubWindow() {
		super();
		
		String jarPath = "classpath:" + Activator.getContext().getBundle().getLocation().substring(6) + "#images/uaal.bmp";		
		windowHeader = new QLabel(this);		
		QPixmap pixmap = new QPixmap(jarPath);
		
		windowHeader.setPixmap(pixmap);
		layout.addWidget(windowHeader);
		windowHeader.show();
		
		this.layout().addItem(layout);
	}

	public void initialize() {
		this.show();
	}

}
