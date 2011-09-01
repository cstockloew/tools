/********************************************************************************
** Form generated from reading ui file 'Overview.jui'
**
** Created: Mi 27. Jul 18:39:05 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import java.util.ArrayList;
import java.util.List;

import org.universAAL.ucc.viewjambi.Activator;
import org.universAAL.ucc.viewjambi.layouts.OverviewGridLayout;
import org.universAAL.ucc.viewjambi.overview.LabeledIcon;

import com.sun.management.jmx.Trace;
import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPalette;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QScrollArea;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

public class Ui_GridView implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout_2;
    public QTreeView treeView;
    public QWidget parent;
    public QScrollArea scrollArea;
    public OverviewGridLayout currentLayout;
	public QAction overviewAction;
	public QAction installAction;
	public QAction uninstallAction;
	public QAction sysInformation;
	public QHBoxLayout boxLayout;
    
    public Ui_GridView() { super(); }

    public void setupUi(QWidget Overview)
    {
    	parent = Overview;
        Overview.setObjectName("Overview");
        
		
		QPalette palette= new QPalette();
	        palette.setColor(QPalette.ColorGroup.All, QPalette.ColorRole.Button, new QColor(255, 255, 255));
	        palette.setColor(QPalette.ColorGroup.All, QPalette.ColorRole.Midlight, new QColor(255, 255, 255));
	        palette.setColor(QPalette.ColorGroup.All, QPalette.ColorRole.Base, new QColor(255, 255, 255));
	        palette.setColor(QPalette.ColorGroup.All, QPalette.ColorRole.Window, new QColor(255, 255, 255));

	    Overview.setPalette(palette);
        Overview.setAutoFillBackground(true);
        
        Overview.setMinimumSize(new QSize(160,  200));
        Overview.resize(new QSize(160, 200).expandedTo(Overview.minimumSizeHint()));

        currentLayout = new OverviewGridLayout(Overview);
        
//        QWidget widget = new QWidget(scrollArea);
//        widget.setLayout(currentLayout);
//        widget.setSizePolicy(QSizePolicy.Policy.Ignored, QSizePolicy.Policy.Ignored);
//        widget.setMinimumSize(300, 200);
//        
//        scrollArea = new QScrollArea(Overview);
//        scrollArea.setWidget(widget);
//        scrollArea.adjustSize();
//        Overview.adjustSize();
		
        Overview.setAutoFillBackground(true);
        retranslateUi(Overview);

        Overview.connectSlotsByName();
    } // setupUi

    
    void retranslateUi(QWidget Overview)
    {
        Overview.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Overview", "Application Overview", null));
    } // retranslateUi

    
}

