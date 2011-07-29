/********************************************************************************
** Form generated from reading ui file 'MainWindow.jui'
**
** Created: Mi 27. Jul 18:39:05 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_MainWindow implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QAction actionExit;
    public QAction actionInstall;
    public QAction actionSystem_Information;
    public QAction actionDeinstall;
    public QAction actionOverview;
    public QWidget centralwidget;
    public QVBoxLayout verticalLayout;
    public QMdiArea mdiArea;
    public QMenuBar menubar;
    public QMenu menuFile;
    public QMenu menuApplications;
    public QMenu menuSystem;
    public QStatusBar statusbar;

    public Ui_MainWindow() { super(); }

    public void setupUi(QMainWindow MainWindow)
    {
        MainWindow.setObjectName("MainWindow");
        MainWindow.resize(new QSize(640, 480).expandedTo(MainWindow.minimumSizeHint()));
        actionExit = new QAction(MainWindow);
        actionExit.setObjectName("actionExit");
        actionInstall = new QAction(MainWindow);
        actionInstall.setObjectName("actionInstall");
        actionSystem_Information = new QAction(MainWindow);
        actionSystem_Information.setObjectName("actionSystem_Information");
        actionDeinstall = new QAction(MainWindow);
        actionDeinstall.setObjectName("actionDeinstall");
        actionOverview = new QAction(MainWindow);
        actionOverview.setObjectName("actionOverview");
        centralwidget = new QWidget(MainWindow);
        centralwidget.setObjectName("centralwidget");
        verticalLayout = new QVBoxLayout(centralwidget);
        verticalLayout.setMargin(0);
        verticalLayout.setObjectName("verticalLayout");
        mdiArea = new QMdiArea(centralwidget);
        mdiArea.setObjectName("mdiArea");

        verticalLayout.addWidget(mdiArea);

        MainWindow.setCentralWidget(centralwidget);
        menubar = new QMenuBar(MainWindow);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 640, 21));
        menuFile = new QMenu(menubar);
        menuFile.setObjectName("menuFile");
        menuApplications = new QMenu(menubar);
        menuApplications.setObjectName("menuApplications");
        menuSystem = new QMenu(menubar);
        menuSystem.setObjectName("menuSystem");
        MainWindow.setMenuBar(menubar);
        statusbar = new QStatusBar(MainWindow);
        statusbar.setObjectName("statusbar");
        MainWindow.setStatusBar(statusbar);

        menubar.addAction(menuFile.menuAction());
        menubar.addAction(menuApplications.menuAction());
        menubar.addAction(menuSystem.menuAction());
        menuFile.addAction(actionExit);
        menuApplications.addAction(actionOverview);
        menuApplications.addSeparator();
        menuApplications.addAction(actionInstall);
        menuApplications.addAction(actionDeinstall);
        menuSystem.addAction(actionSystem_Information);
        retranslateUi(MainWindow);

        MainWindow.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow MainWindow)
    {
        MainWindow.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "universAAL Control Center (UCC)", null));
        actionExit.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Exit", null));
        actionInstall.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Install", null));
        actionSystem_Information.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "System Information", null));
        actionDeinstall.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Deinstall", null));
        actionOverview.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Overview", null));
        menuFile.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "File", null));
        menuApplications.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Applications", null));
        menuSystem.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "System", null));
    } // retranslateUi

}

