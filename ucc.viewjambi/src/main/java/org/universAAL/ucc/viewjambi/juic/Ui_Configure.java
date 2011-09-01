/********************************************************************************
** Form generated from reading ui file 'configure.jui'
**
** Created: Mi 31. Aug 16:36:09 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Configure implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout_2;
    public QGridLayout gridLayout;
    public QWidget widget;
    public QHBoxLayout horizontalLayout_2;
    public QHBoxLayout horizontalLayout;
    public QSpacerItem horizontalSpacer;
    public QPushButton okButton;
    public QPushButton cancelButton;
    public QVBoxLayout verticalLayout_4;
    public QSpacerItem verticalSpacer;

    public Ui_Configure() { super(); }

    public void setupUi(QWidget Configure)
    {
        Configure.setObjectName("Configure");
        Configure.resize(new QSize(800, 600).expandedTo(Configure.minimumSizeHint()));
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);
        sizePolicy.setHorizontalStretch((byte)1);
        sizePolicy.setVerticalStretch((byte)1);
        sizePolicy.setHeightForWidth(Configure.sizePolicy().hasHeightForWidth());
        Configure.setSizePolicy(sizePolicy);
        Configure.setMinimumSize(new QSize(250, 350));
        gridLayout_2 = new QGridLayout(Configure);
        gridLayout_2.setObjectName("gridLayout_2");
        gridLayout = new QGridLayout();
        gridLayout.setObjectName("gridLayout");
        widget = new QWidget(Configure);
        widget.setObjectName("widget");
        widget.setMinimumSize(new QSize(0, 20));
        horizontalLayout_2 = new QHBoxLayout(widget);
        horizontalLayout_2.setMargin(0);
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        horizontalLayout.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetFixedSize);
        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout.addItem(horizontalSpacer);

        okButton = new QPushButton(widget);
        okButton.setObjectName("okButton");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Fixed, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed);
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(okButton.sizePolicy().hasHeightForWidth());
        okButton.setSizePolicy(sizePolicy1);
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

        verticalLayout_4 = new QVBoxLayout();
        verticalLayout_4.setObjectName("verticalLayout_4");
        verticalLayout_4.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetMinAndMaxSize);
        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        verticalLayout_4.addItem(verticalSpacer);


        gridLayout_2.addLayout(verticalLayout_4, 0, 0, 1, 1);

        retranslateUi(Configure);

        Configure.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget Configure)
    {
        Configure.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Configure", "Install a new application", null));
        okButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Configure", "OK", null));
        cancelButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Configure", "Cancel", null));
    } // retranslateUi

}

