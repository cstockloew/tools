/********************************************************************************
** Form generated from reading ui file 'SystemInformation.jui'
**
** Created: Mi 31. Aug 16:36:09 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_SystemInformation implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout_2;
    public QGridLayout gridLayout;
    public QLabel label;
    public QWidget widget;
    public QHBoxLayout horizontalLayout;
    public QSpacerItem horizontalSpacer;
    public QPushButton closeButton;
    public QWidget widget_2;
    public QVBoxLayout verticalLayout;
    public QLabel label_2;
    public QSpacerItem verticalSpacer;
    public QTableWidget bundleList;

    public Ui_SystemInformation() { super(); }

    public void setupUi(QWidget SystemInformation)
    {
        SystemInformation.setObjectName("SystemInformation");
        SystemInformation.resize(new QSize(640, 480).expandedTo(SystemInformation.minimumSizeHint()));
        gridLayout_2 = new QGridLayout(SystemInformation);
        gridLayout_2.setObjectName("gridLayout_2");
        gridLayout = new QGridLayout();
        gridLayout.setObjectName("gridLayout");
        label = new QLabel(SystemInformation);
        label.setObjectName("label");

        gridLayout.addWidget(label, 0, 0, 1, 1);

        widget = new QWidget(SystemInformation);
        widget.setObjectName("widget");
        horizontalLayout = new QHBoxLayout(widget);
        horizontalLayout.setObjectName("horizontalLayout");
        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout.addItem(horizontalSpacer);

        closeButton = new QPushButton(widget);
        closeButton.setObjectName("closeButton");

        horizontalLayout.addWidget(closeButton);


        gridLayout.addWidget(widget, 2, 0, 1, 2);

        widget_2 = new QWidget(SystemInformation);
        widget_2.setObjectName("widget_2");
        verticalLayout = new QVBoxLayout(widget_2);
        verticalLayout.setObjectName("verticalLayout");
        label_2 = new QLabel(widget_2);
        label_2.setObjectName("label_2");

        verticalLayout.addWidget(label_2);

        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        verticalLayout.addItem(verticalSpacer);


        gridLayout.addWidget(widget_2, 1, 1, 1, 1);

        bundleList = new QTableWidget(SystemInformation);
        bundleList.setObjectName("bundleList");

        gridLayout.addWidget(bundleList, 1, 0, 1, 1);


        gridLayout_2.addLayout(gridLayout, 0, 0, 1, 1);

        retranslateUi(SystemInformation);

        SystemInformation.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget SystemInformation)
    {
        SystemInformation.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("SystemInformation", "System Information", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("SystemInformation", "List of active OSGi bundles:", null));
        closeButton.setText(com.trolltech.qt.core.QCoreApplication.translate("SystemInformation", "Close", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("SystemInformation", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\" \"http://www.w3.org/TR/REC-html40/strict.dtd\">\n"+
"<html><head><meta name=\"qrichtext\" content=\"1\" /><style type=\"text/css\">\n"+
"p, li { white-space: pre-wrap; }\n"+
"</style></head><body style=\" font-family:'MS Shell Dlg 2'; font-size:8.25pt; font-weight:400; font-style:normal;\">\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><span style=\" font-size:8pt;\">universAAL Version:</span></p>\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><span style=\" font-size:8pt;\">1.0.0</span></p>\n"+
"<p style=\"-qt-paragraph-type:empty; margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px; font-size:8pt;\"></p>\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><span style=\" font-size:8pt;\">uCC Version:</span></p>\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><span style=\" font-size:8pt;\">1.0.0</span></p>\n"+
"<p style=\"-qt-paragraph-type:empty; margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px; font-size:8pt;\"></p>\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><a href=\"www.universAAL.org\"><span style=\" text-decoration: underline; color:#0000ff;\">www.universAAL.org</span></a></p></body></html>", null));
        bundleList.clear();
        bundleList.setColumnCount(4);

        QTableWidgetItem __colItem = new QTableWidgetItem();
        __colItem.setText(com.trolltech.qt.core.QCoreApplication.translate("SystemInformation", "Symbolic Name", null));
        bundleList.setHorizontalHeaderItem(0, __colItem);

        QTableWidgetItem __colItem1 = new QTableWidgetItem();
        __colItem1.setText(com.trolltech.qt.core.QCoreApplication.translate("SystemInformation", "Last Update", null));
        bundleList.setHorizontalHeaderItem(1, __colItem1);

        QTableWidgetItem __colItem2 = new QTableWidgetItem();
        __colItem2.setText(com.trolltech.qt.core.QCoreApplication.translate("SystemInformation", "Status", null));
        bundleList.setHorizontalHeaderItem(2, __colItem2);

        QTableWidgetItem __colItem3 = new QTableWidgetItem();
        __colItem3.setText(com.trolltech.qt.core.QCoreApplication.translate("SystemInformation", "Comments", null));
        bundleList.setHorizontalHeaderItem(3, __colItem3);
        bundleList.setRowCount(0);
    } // retranslateUi

}

