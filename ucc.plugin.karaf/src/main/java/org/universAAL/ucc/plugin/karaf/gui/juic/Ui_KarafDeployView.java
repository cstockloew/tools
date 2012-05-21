/********************************************************************************
** Form generated from reading ui file 'KarafDeployView.jui'
**
** Created: on 16. mai 11:11:05 2012
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.plugin.karaf.gui.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_KarafDeployView implements com.trolltech.qt.QUiForm<QWidget>
{
    public QVBoxLayout verticalLayout;
    public QGridLayout gridLayout;
    public QLabel label;
    public QLineEdit lineEdit_karaf;
    public QPushButton pushButton_karaf;
    public QGridLayout gridLayout_2;
    public QLabel label_2;
    public QLineEdit lineEdit_app;
    public QPushButton pushButton_app;
    public QGridLayout gridLayout_3;
    public QPushButton pushButton_ok;
    public QPushButton pushButton_cancel;
    public QSpacerItem horizontalSpacer;

    public Ui_KarafDeployView() { super(); }

    public void setupUi(QWidget KarafDeployView)
    {
        KarafDeployView.setObjectName("KarafDeployView");
        KarafDeployView.resize(new QSize(600, 202).expandedTo(KarafDeployView.minimumSizeHint()));
        verticalLayout = new QVBoxLayout(KarafDeployView);
        verticalLayout.setObjectName("verticalLayout");
        gridLayout = new QGridLayout();
        gridLayout.setObjectName("gridLayout");
        label = new QLabel(KarafDeployView);
        label.setObjectName("label");

        gridLayout.addWidget(label, 0, 0, 1, 1);

        lineEdit_karaf = new QLineEdit(KarafDeployView);
        lineEdit_karaf.setObjectName("lineEdit_karaf");

        gridLayout.addWidget(lineEdit_karaf, 0, 1, 1, 1);

        pushButton_karaf = new QPushButton(KarafDeployView);
        pushButton_karaf.setObjectName("pushButton_karaf");

        gridLayout.addWidget(pushButton_karaf, 0, 2, 1, 1);


        verticalLayout.addLayout(gridLayout);

        gridLayout_2 = new QGridLayout();
        gridLayout_2.setObjectName("gridLayout_2");
        label_2 = new QLabel(KarafDeployView);
        label_2.setObjectName("label_2");

        gridLayout_2.addWidget(label_2, 0, 0, 1, 1);

        lineEdit_app = new QLineEdit(KarafDeployView);
        lineEdit_app.setObjectName("lineEdit_app");

        gridLayout_2.addWidget(lineEdit_app, 0, 1, 1, 1);

        pushButton_app = new QPushButton(KarafDeployView);
        pushButton_app.setObjectName("pushButton_app");

        gridLayout_2.addWidget(pushButton_app, 0, 2, 1, 1);


        verticalLayout.addLayout(gridLayout_2);

        gridLayout_3 = new QGridLayout();
        gridLayout_3.setObjectName("gridLayout_3");
        pushButton_ok = new QPushButton(KarafDeployView);
        pushButton_ok.setObjectName("pushButton_ok");

        gridLayout_3.addWidget(pushButton_ok, 0, 1, 1, 1);

        pushButton_cancel = new QPushButton(KarafDeployView);
        pushButton_cancel.setObjectName("pushButton_cancel");

        gridLayout_3.addWidget(pushButton_cancel, 0, 2, 1, 1);

        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout_3.addItem(horizontalSpacer, 0, 0, 1, 1);


        verticalLayout.addLayout(gridLayout_3);

        retranslateUi(KarafDeployView);

        KarafDeployView.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget KarafDeployView)
    {
        KarafDeployView.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("KarafDeployView", "Karaf Deploy Window", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("KarafDeployView", "Karaf Installation Path", null));
        pushButton_karaf.setText(com.trolltech.qt.core.QCoreApplication.translate("KarafDeployView", "Browse...", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("KarafDeployView", "Application To Be Installed", null));
        pushButton_app.setText(com.trolltech.qt.core.QCoreApplication.translate("KarafDeployView", "Browse...", null));
        pushButton_ok.setText(com.trolltech.qt.core.QCoreApplication.translate("KarafDeployView", "OK", null));
        pushButton_cancel.setText(com.trolltech.qt.core.QCoreApplication.translate("KarafDeployView", "Cancel", null));
    } // retranslateUi

}

