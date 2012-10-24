/********************************************************************************
** Form generated from reading ui file 'Overview.jui'
**
** Created: Mi 31. Aug 16:36:09 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Overview implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout_2;
    public QGridLayout gridLayout;
    public QTreeView treeView;
    public QLabel label;

    public Ui_Overview() { super(); }

    public void setupUi(QWidget Overview)
    {
        Overview.setObjectName("Overview");
        Overview.resize(new QSize(261, 592).expandedTo(Overview.minimumSizeHint()));
        gridLayout_2 = new QGridLayout(Overview);
        gridLayout_2.setObjectName("gridLayout_2");
        gridLayout = new QGridLayout();
        gridLayout.setObjectName("gridLayout");
        treeView = new QTreeView(Overview);
        treeView.setObjectName("treeView");
        treeView.setMinimumSize(new QSize(200, 400));
        treeView.setRootIsDecorated(true);
        treeView.setItemsExpandable(true);
        treeView.setExpandsOnDoubleClick(false);

        gridLayout.addWidget(treeView, 0, 0, 1, 1);


        gridLayout_2.addLayout(gridLayout, 1, 0, 1, 1);

        label = new QLabel(Overview);
        label.setObjectName("label");
        QFont font = new QFont();
        font.setPointSize(14);
        font.setBold(true);
        font.setWeight(75);
        label.setFont(font);
        label.setScaledContents(true);
        label.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));

        gridLayout_2.addWidget(label, 0, 0, 1, 1);

        retranslateUi(Overview);

        Overview.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget Overview)
    {
        Overview.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Overview", "Application Overview", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Overview", "Applications", null));
    } // retranslateUi

}

