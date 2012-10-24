/********************************************************************************
** Form generated from reading ui file 'install.jui'
**
** Created: Mi 27. Jul 18:39:05 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.webkit.QWebView;

public class Ui_StoreView implements com.trolltech.qt.QUiForm<QWidget>
{
    public QHBoxLayout horizontalLayout;
    public QLabel label_2;
    public QWebView webView;

    public Ui_StoreView() { super(); }

    public void setupUi(QWidget Install)
    {
       horizontalLayout = new QHBoxLayout(Install);
       webView = new QWebView();
       webView.load(new QUrl("http://srv-ustore.haifa.il.ibm.com/webapp/wcs/stores/servlet/StoreView?storeId=10001"));
       horizontalLayout.addWidget(webView);

        retranslateUi(Install);
        Install.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget Install)
    {
        Install.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Install", "Install a new application", null));
    } // retranslateUi

}

