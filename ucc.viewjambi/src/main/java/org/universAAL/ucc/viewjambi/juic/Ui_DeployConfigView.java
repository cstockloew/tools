/********************************************************************************
** Form generated from reading ui file 'DeployConfigView.jui'
**
** Created: fr 3. aug 12:15:16 2012
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_DeployConfigView implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout_2;
    public QGridLayout gridLayout;
    public QLabel label_2;
    public QLabel label;
    public QLabel label_3;
    public QSpacerItem horizontalSpacer;
    public QLineEdit lineEdit;
    public QComboBox comboBox;
    public QHBoxLayout horizontalLayout;
    public QPushButton pushButton_previous;
    public QPushButton pushButton_next;
    public QPushButton pushButton_cancel;
    public QSpacerItem verticalSpacer;
    public QSpacerItem verticalSpacer_2;

    public Ui_DeployConfigView() { super(); }

    public void setupUi(QWidget DeployConfigView)
    {
        DeployConfigView.setObjectName("DeployConfigView");
        DeployConfigView.resize(new QSize(340, 206).expandedTo(DeployConfigView.minimumSizeHint()));
        gridLayout_2 = new QGridLayout(DeployConfigView);
        gridLayout_2.setObjectName("gridLayout_2");
        gridLayout = new QGridLayout();
        gridLayout.setObjectName("gridLayout");
        label_2 = new QLabel(DeployConfigView);
        label_2.setObjectName("label_2");

        gridLayout.addWidget(label_2, 0, 0, 1, 2);

        label = new QLabel(DeployConfigView);
        label.setObjectName("label");

        gridLayout.addWidget(label, 2, 0, 1, 1);

        label_3 = new QLabel(DeployConfigView);
        label_3.setObjectName("label_3");

        gridLayout.addWidget(label_3, 2, 1, 1, 1);

        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout.addItem(horizontalSpacer, 2, 2, 1, 1);

        lineEdit = new QLineEdit(DeployConfigView);
        lineEdit.setObjectName("lineEdit");

        gridLayout.addWidget(lineEdit, 3, 0, 1, 1);

        comboBox = new QComboBox(DeployConfigView);
        comboBox.setObjectName("comboBox");

        gridLayout.addWidget(comboBox, 3, 1, 1, 2);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        pushButton_previous = new QPushButton(DeployConfigView);
        pushButton_previous.setObjectName("pushButton_previous");

        horizontalLayout.addWidget(pushButton_previous);

        pushButton_next = new QPushButton(DeployConfigView);
        pushButton_next.setObjectName("pushButton_next");

        horizontalLayout.addWidget(pushButton_next);

        pushButton_cancel = new QPushButton(DeployConfigView);
        pushButton_cancel.setObjectName("pushButton_cancel");

        horizontalLayout.addWidget(pushButton_cancel);


        gridLayout.addLayout(horizontalLayout, 5, 0, 1, 3);

        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer, 4, 0, 1, 1);

        verticalSpacer_2 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer_2, 1, 1, 1, 1);


        gridLayout_2.addLayout(gridLayout, 0, 0, 1, 1);

        retranslateUi(DeployConfigView);

        DeployConfigView.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget DeployConfigView)
    {
        DeployConfigView.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("DeployConfigView", "Deploy configure", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployConfigView", "Please select a node to deploy an application part:", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployConfigView", "Application Part", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployConfigView", "Node to deploy", null));
        pushButton_previous.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployConfigView", "previous", null));
        pushButton_next.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployConfigView", "next", null));
        pushButton_cancel.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployConfigView", "cancel", null));
    } // retranslateUi

}

