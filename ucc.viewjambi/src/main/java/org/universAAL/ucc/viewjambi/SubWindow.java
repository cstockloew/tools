package org.universAAL.ucc.viewjambi;

import org.universAAL.ucc.api.view.ISubWindow;

import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMdiSubWindow;
import com.trolltech.qt.gui.QPalette;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QLayout.SizeConstraint;
import com.trolltech.qt.gui.QPalette.ColorRole;
import com.trolltech.qt.gui.QSizePolicy.Policy;

public class SubWindow extends QMdiSubWindow implements ISubWindow {
	
	private QVBoxLayout layout = new QVBoxLayout();
	private QLabel windowHeader = null;
	protected QWidget content = null;
	
	public SubWindow(MainWindow parent) {
		super(parent);
		
		QWidget main = new QWidget();
		main.setLayout(layout);
		
		/*QWidget headerParent = new QWidget(main);
		
		headerParent.setAutoFillBackground(true);
		QPalette palette = headerParent.palette();
		//palette.setColor(QPalette.ColorRole.Base, new QColor(204,204,244));
		//palette.setBrush(ColorRole.Base, new QBrush(new QColor(204,204,244)));
		//palette.setBrush(ColorRole.NoRole, new QBrush(new QColor(204,204,244)));
		//palette.setBrush(ColorRole.AlternateBase, new QBrush(new QColor(204,204,244)));
		//palette.setBrush(ColorRole.Button, new QBrush(new QColor(204,204,244)));
		palette.setBrush(ColorRole.Window, new QBrush(new QColor(204,204,204)));
		headerParent.setPalette(palette);
		headerParent.setGeometry(0, 0, 100, 40);
		headerParent.setSizePolicy(Policy.Expanding, Policy.Fixed);
		layout.addWidget(headerParent);
		
		/*String jarPath = "classpath:" + Activator.getContext().getBundle().getLocation().substring(6) + "#images/uaal.bmp";		
		windowHeader = new QLabel(headerParent);		
		QPixmap pixmap = new QPixmap(jarPath);
		windowHeader.setPixmap(pixmap);
		windowHeader.show();
		layout.addWidget(headerParent);
		
		content = new QWidget(main);
		content.setSizePolicy(Policy.Expanding, Policy.Expanding);
		layout.addWidget(content);
		
		layout.setSizeConstraint(SizeConstraint.SetMaximumSize);
		
		main.setLayout(layout);*/
		
		QWidget headerParent = new QWidget(main);
		headerParent.setGeometry(0,0,100,100);
		headerParent.setSizePolicy(Policy.Expanding, Policy.Fixed);
		QPalette palette = headerParent.palette();
		palette.setBrush(ColorRole.Window, new QBrush(new QColor(204,204,204)));
		headerParent.setPalette(palette);
		
		QPushButton button = new QPushButton(headerParent);
		layout.addWidget(headerParent);
		
		this.setWidget(main);
	}

	public void initialize() {
		this.show();
	}

}
