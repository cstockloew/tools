package org.universAAL.ucc.viewjambi.overview;

import org.universAAL.ucc.viewjambi.Activator;
import org.universAAL.ucc.viewjambi.juic.Ui_GridView;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QAbstractButton;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

public class LabeledIcon extends QAbstractButton{

	public String text;
	public String icon;
	public QVBoxLayout layout;
	public QLabel label;
	public QLabel image;
	public final String jarPath = "classpath:"+ Activator.getContext().getBundle().getLocation().substring(6);
	
	
	public LabeledIcon(String text, String icon){
		super();
		this.text = text;
		this.icon = icon;
		
		layout = new QVBoxLayout();
        layout.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
		label = new QLabel();
		image = new QLabel();
		
		label.setText(text);
//		label.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
    	image.setPixmap(new QPixmap(jarPath+"#icon\\"+icon));
//    	image.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
    	image.setMinimumSize(new QSize(48,48));
    	image.setMaximumSize(new QSize(48,48));
    	label.setMinimumSize(new QSize(70, 30));
    	label.setMaximumSize(new QSize(70, 30));
    	
    	layout.insertWidget(0, image);
    	layout.insertWidget(1, label);

    	layout.setSizeConstraint(SizeConstraint.SetFixedSize);
    	layout.setWidgetSpacing(0);
		this.setMinimumSize(new QSize(72,72));
		this.setMaximumSize(new QSize(72,72));
		this.setLayout(layout);
//		this.show();

		
	}
	
	public LabeledIcon(){
		this("","");
	}
	
	

	@Override
	protected void paintEvent(QPaintEvent arg0) {
		
	}

	public QSize sizeHint(){
		return new QSize(100, 100);
	}
	

	
}
