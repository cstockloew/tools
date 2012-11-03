/********************************************************************************
** Form generated from reading ui file 'install.jui'
**
** Created: Mi 31. Aug 16:36:09 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Install implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout_2;
    public QGridLayout gridLayout;
    public QLabel label;
    public QLineEdit fileName;
    public QSpacerItem verticalSpacer;
    public QSpacerItem verticalSpacer_2;
    public QPushButton fileChoise;
    public QWidget widget;
    public QHBoxLayout horizontalLayout_2;
    public QHBoxLayout horizontalLayout;
    public QSpacerItem horizontalSpacer;
    public QPushButton okButton;
    public QPushButton cancelButton;
    public QLabel label_2;

    public Ui_Install() { super(); }

    public void setupUi(QWidget Install)
    {
        Install.setObjectName("Install");
        Install.resize(new QSize(400, 300).expandedTo(Install.minimumSizeHint()));
        gridLayout_2 = new QGridLayout(Install);
        gridLayout_2.setObjectName("gridLayout_2");
        gridLayout = new QGridLayout();
        gridLayout.setObjectName("gridLayout");
        label = new QLabel(Install);
        label.setObjectName("label");

        gridLayout.addWidget(label, 1, 0, 1, 1);

        fileName = new QLineEdit(Install);
        fileName.setObjectName("fileName");

        gridLayout.addWidget(fileName, 2, 0, 1, 1);

        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer, 0, 0, 1, 1);

        verticalSpacer_2 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer_2, 4, 0, 1, 1);

        fileChoise = new QPushButton(Install);
        fileChoise.setObjectName("fileChoise");
        fileChoise.setMaximumSize(new QSize(40, 16777215));

        gridLayout.addWidget(fileChoise, 2, 1, 1, 1);

        widget = new QWidget(Install);
        widget.setObjectName("widget");
        widget.setMinimumSize(new QSize(0, 20));
        horizontalLayout_2 = new QHBoxLayout(widget);
        horizontalLayout_2.setMargin(0);
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout.addItem(horizontalSpacer);

        okButton = new QPushButton(widget);
        okButton.setObjectName("okButton");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Fixed, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(okButton.sizePolicy().hasHeightForWidth());
        okButton.setSizePolicy(sizePolicy);
        okButton.setMinimumSize(new QSize(75, 0));
        okButton.setMaximumSize(new QSize(75, 16777215));
        okButton.setBaseSize(new QSize(100, 0));

        horizontalLayout.addWidget(okButton);

        cancelButton = new QPushButton(widget);
        cancelButton.setObjectName("cancelButton");
        cancelButton.setMinimumSize(new QSize(75, 0));
        cancelButton.setMaximumSize(new QSize(75, 16777215));

        horizontalLayout.addWidget(cancelButton);


        horizontalLayout_2.addLayout(horizontalLayout);


        gridLayout.addWidget(widget, 5, 0, 1, 2);

//        label_2 = new QLabel(Install);
//        label_2.setObjectName("label_2");

        gridLayout.addWidget(label_2, 3, 0, 1, 1);


        gridLayout_2.addLayout(gridLayout, 0, 0, 1, 1);

        retranslateUi(Install);

        Install.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget Install)
    {
        Install.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Install", "Install a new application", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Install", "Please select an AAL application to install:", null));
        fileChoise.setText(com.trolltech.qt.core.QCoreApplication.translate("Install", "...", null));
        okButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Install", "OK", null));
        cancelButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Install", "Cancel", null));
//        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("Install", "I want to try this out to!", null));
    } // retranslateUi

}

