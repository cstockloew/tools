/********************************************************************************
** Form generated from reading ui file 'DeployStrategyView.jui'
**
** Created: on 20. jun 15:30:02 2012
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_DeployStrategyView implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout_2;
    public QGridLayout gridLayout;
    public QHBoxLayout horizontalLayout;
    public QLabel label_appName;
    public QLineEdit lineEdit_appName;
    public QLabel label_strategy;
    public QVBoxLayout verticalLayout;
    public QRadioButton radioButton_all;
    public QRadioButton radioButton_selected;
    public QHBoxLayout horizontalLayout_2;
    public QSpacerItem verticalSpacer;
    public QSpacerItem horizontalSpacer_2;
    public QPushButton pushButton_ok;
    public QPushButton pushButton_cancel;
    public QSpacerItem horizontalSpacer;

    public Ui_DeployStrategyView() { super(); }

    public void setupUi(QWidget DeployStrategyView)
    {
        DeployStrategyView.setObjectName("DeployStrategyView");
        DeployStrategyView.resize(new QSize(301, 157).expandedTo(DeployStrategyView.minimumSizeHint()));
        gridLayout_2 = new QGridLayout(DeployStrategyView);
        gridLayout_2.setObjectName("gridLayout_2");
        gridLayout = new QGridLayout();
        gridLayout.setObjectName("gridLayout");
        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        label_appName = new QLabel(DeployStrategyView);
        label_appName.setObjectName("label_appName");

        horizontalLayout.addWidget(label_appName);

        lineEdit_appName = new QLineEdit(DeployStrategyView);
        lineEdit_appName.setObjectName("lineEdit_appName");

        horizontalLayout.addWidget(lineEdit_appName);


        gridLayout.addLayout(horizontalLayout, 0, 0, 1, 2);

        label_strategy = new QLabel(DeployStrategyView);
        label_strategy.setObjectName("label_strategy");

        gridLayout.addWidget(label_strategy, 2, 0, 1, 1);

        verticalLayout = new QVBoxLayout();
        verticalLayout.setObjectName("verticalLayout");
        radioButton_all = new QRadioButton(DeployStrategyView);
        radioButton_all.setObjectName("radioButton_all");

        verticalLayout.addWidget(radioButton_all);

        radioButton_selected = new QRadioButton(DeployStrategyView);
        radioButton_selected.setObjectName("radioButton_selected");

        verticalLayout.addWidget(radioButton_selected);


        gridLayout.addLayout(verticalLayout, 2, 1, 1, 1);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        horizontalLayout_2.addItem(verticalSpacer);

        horizontalSpacer_2 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout_2.addItem(horizontalSpacer_2);

        pushButton_ok = new QPushButton(DeployStrategyView);
        pushButton_ok.setObjectName("pushButton_ok");

        horizontalLayout_2.addWidget(pushButton_ok);

        pushButton_cancel = new QPushButton(DeployStrategyView);
        pushButton_cancel.setObjectName("pushButton_cancel");

        horizontalLayout_2.addWidget(pushButton_cancel);

        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout_2.addItem(horizontalSpacer);


        gridLayout.addLayout(horizontalLayout_2, 5, 0, 1, 2);


        gridLayout_2.addLayout(gridLayout, 0, 0, 1, 1);

        retranslateUi(DeployStrategyView);

        DeployStrategyView.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget DeployStrategyView)
    {
        DeployStrategyView.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("DeployStrategyView", "Select deploy strategy", null));
        label_appName.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployStrategyView", "Application name", null));
        label_strategy.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployStrategyView", "Deploy strategy", null));
        radioButton_all.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployStrategyView", "On all available nodes (default)", null));
        radioButton_selected.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployStrategyView", "On selected nodes", null));
        pushButton_ok.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployStrategyView", "OK", null));
        pushButton_cancel.setText(com.trolltech.qt.core.QCoreApplication.translate("DeployStrategyView", "Cancel", null));
    } // retranslateUi

}

