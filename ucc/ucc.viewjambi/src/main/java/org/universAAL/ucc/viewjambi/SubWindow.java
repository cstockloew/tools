package org.universAAL.ucc.viewjambi;

import org.universAAL.ucc.api.view.ISubWindow;

import com.trolltech.qt.core.QFile;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMdiSubWindow;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QVBoxLayout;

public class SubWindow extends QMdiSubWindow implements ISubWindow {
	
	private QVBoxLayout layout = new QVBoxLayout();
	private QLabel windowHeader = null;
	
	public SubWindow() {
		super();
		
		//String jarPath = "classpath:" + Activator.getContext().getBundle().getLocation().substring(6) + "#images/uaal.gif";
		String jarPath = "uaal.jpg";
		System.out.println(jarPath);
		
		windowHeader = new QLabel(this);
		windowHeader.setText("This is a test!");
		QFile file = new QFile(jarPath);
		if (file.exists())
			System.out.println("\n\n\n\n\n\njojojo\n\n\n\n\n\n");
		else
			System.out.println("\n\n\n\n\n\nnonono\n\n\n\n\n\n");
		windowHeader.setPixmap(new QPixmap(jarPath));
		layout.addWidget(windowHeader);
		windowHeader.show();
		
		this.layout().addItem(layout);
	}

	public void initialize() {
		this.show();
	}

}
