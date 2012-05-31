/********************************************************************************
** Form generated from reading ui file 'DeployConfigView.jui'
**
** Created: to 31. mai 11:14:45 2012
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_DeployConfigView implements com.trolltech.qt.QUiForm<QWidget>
{
    public QVBoxLayout verticalLayout_4;
    public QGridLayout gridLayout;
    public QVBoxLayout verticalLayout_3;
    public QRadioButton radioButton_all;
    public QRadioButton radioButton_selected;
    public QVBoxLayout verticalLayout_2;
    public QLabel label;
    public QListView listView;
    public QVBoxLayout verticalLayout;
    public QSpacerItem verticalSpacer_4;
    public QPushButton pushButton_add;
    public QSpacerItem verticalSpacer;
    public QPushButton pushButton_remove;
    public QSpacerItem verticalSpacer_3;
    public QHBoxLayout horizontalLayout;
    public QLabel label_2;
    public QLineEdit lineEdit_appId;
    public QSpacerItem horizontalSpacer;
    public QLabel label_3;
    public QSplitter splitter;
    public QPushButton pushButton_previous;
    public QPushButton pushButton_next;
    public QPushButton pushButton_cancel;

    public Ui_DeployConfigView() { super(); }

    public void setupUi(QWidget ListTest)
    {
        ListTest.setObjectName("ListTest");
        ListTest.resize(new QSize(384, 422).expandedTo(ListTest.minimumSizeHint()));
        verticalLayout_4 = new QVBoxLayout(ListTest);
        verticalLayout_4.setObjectName("verticalLayout_4");
        gridLayout = new QGridLayout();
        gridLayout.setObjectName("gridLayout");
        verticalLayout_3 = new QVBoxLayout();
        verticalLayout_3.setObjectName("verticalLayout_3");
        radioButton_all = new QRadioButton(ListTest);
        radioButton_all.setObjectName("radioButton_all");

        verticalLayout_3.addWidget(radioButton_all);

        radioButton_selected = new QRadioButton(ListTest);
        radioButton_selected.setObjectName("radioButton_selected");

        verticalLayout_3.addWidget(radioButton_selected);


        gridLayout.addLayout(verticalLayout_3, 1, 1, 1, 2);

        verticalLayout_2 = new QVBoxLayout();
        verticalLayout_2.setObjectName("verticalLayout_2");
        label = new QLabel(ListTest);
        label.setObjectName("label");

        verticalLayout_2.addWidget(label);

        listView = new QListView(ListTest);
        listView.setObjectName("listView");

        verticalLayout_2.addWidget(listView);


        gridLayout.addLayout(verticalLayout_2, 2, 0, 1, 2);

        verticalLayout = new QVBoxLayout();
        verticalLayout.setObjectName("verticalLayout");
        verticalSpacer_4 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        verticalLayout.addItem(verticalSpacer_4);

        pushButton_add = new QPushButton(ListTest);
        pushButton_add.setObjectName("pushButton_add");

        verticalLayout.addWidget(pushButton_add);

        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        verticalLayout.addItem(verticalSpacer);

        pushButton_remove = new QPushButton(ListTest);
        pushButton_remove.setObjectName("pushButton_remove");

        verticalLayout.addWidget(pushButton_remove);

        verticalSpacer_3 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        verticalLayout.addItem(verticalSpacer_3);


        gridLayout.addLayout(verticalLayout, 2, 2, 1, 1);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        label_2 = new QLabel(ListTest);
        label_2.setObjectName("label_2");

        horizontalLayout.addWidget(label_2);

        lineEdit_appId = new QLineEdit(ListTest);
        lineEdit_appId.setObjectName("lineEdit_appId");

        horizontalLayout.addWidget(lineEdit_appId);

        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout.addItem(horizontalSpacer);


        gridLayout.addLayout(horizontalLayout, 0, 0, 1, 3);

        label_3 = new QLabel(ListTest);
        label_3.setObjectName("label_3");

        gridLayout.addWidget(label_3, 1, 0, 1, 1);


        verticalLayout_4.addLayout(gridLayout);

        splitter = new QSplitter(ListTest);
        splitter.setObjectName("splitter");
        splitter.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
        pushButton_previous = new QPushButton(splitter);
        pushButton_previous.setObjectName("pushButton_previous");
        splitter.addWidget(pushButton_previous);
        pushButton_next = new QPushButton(splitter);
        pushButton_next.setObjectName("pushButton_next");
        splitter.addWidget(pushButton_next);
        pushButton_cancel = new QPushButton(splitter);
        pushButton_cancel.setObjectName("pushButton_cancel");
        splitter.addWidget(pushButton_cancel);

        verticalLayout_4.addWidget(splitter);

        retranslateUi(ListTest);

        ListTest.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget ListTest)
    {
        ListTest.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "Deploy Configure", null));
        radioButton_all.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "On all deployable nodes (default)", null));
        radioButton_selected.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "On selected nodes", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "Nodes to deploy", null));
        pushButton_add.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "Add", null));
        pushButton_remove.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "Remove", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "Application Part", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\" \"http://www.w3.org/TR/REC-html40/strict.dtd\">\n"+
"<html><head><meta name=\"qrichtext\" content=\"1\" /><style type=\"text/css\">\n"+
"p, li { white-space: pre-wrap; }\n"+
"</style></head><body style=\" font-family:'MS Shell Dlg 2'; font-size:8.25pt; font-weight:400; font-style:normal;\">\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><span style=\" font-size:8pt;\">Deploy strategy</span></p></body></html>", null));
        pushButton_previous.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "previous", null));
        pushButton_next.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "next", null));
        pushButton_cancel.setText(com.trolltech.qt.core.QCoreApplication.translate("ListTest", "cancel", null));
    } // retranslateUi

}

