/********************************************************************************
** Form generated from reading ui file 'deinstall.jui'
**
** Created: Mi 31. Aug 16:36:09 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Deinstall implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout_2;
    public QGridLayout gridLayout;
    public QWidget widget;
    public QHBoxLayout horizontalLayout_2;
    public QHBoxLayout horizontalLayout;
    public QSpacerItem horizontalSpacer;
    public QPushButton okButton;
    public QPushButton cancelButton;
    public QVBoxLayout verticalLayout_2;
    public QLabel label;
    public QListWidget listWidget;

    public Ui_Deinstall() { super(); }

    public void setupUi(QWidget Deinstall)
    {
        Deinstall.setObjectName("Deinstall");
        Deinstall.resize(new QSize(400, 300).expandedTo(Deinstall.minimumSizeHint()));
        gridLayout_2 = new QGridLayout(Deinstall);
        gridLayout_2.setObjectName("gridLayout_2");
        gridLayout = new QGridLayout();
        gridLayout.setObjectName("gridLayout");
        widget = new QWidget(Deinstall);
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


        gridLayout.addWidget(widget, 0, 0, 1, 2);


        gridLayout_2.addLayout(gridLayout, 1, 0, 1, 1);

        verticalLayout_2 = new QVBoxLayout();
        verticalLayout_2.setObjectName("verticalLayout_2");
        label = new QLabel(Deinstall);
        label.setObjectName("label");

        verticalLayout_2.addWidget(label);

        listWidget = new QListWidget(Deinstall);
        listWidget.setObjectName("listWidget");

        verticalLayout_2.addWidget(listWidget);


        gridLayout_2.addLayout(verticalLayout_2, 0, 0, 1, 1);

        retranslateUi(Deinstall);

        Deinstall.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget Deinstall)
    {
        Deinstall.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Deinstall", "Deinstall an application", null));
        okButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Deinstall", "Deinstall", null));
        cancelButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Deinstall", "Cancel", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Deinstall", "Select an application: ", null));
    } // retranslateUi

}

