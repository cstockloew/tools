/********************************************************************************
** Form generated from reading ui file 'subwindow.jui'
**
** Created: Mi 27. Jul 18:39:05 2011
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.universAAL.ucc.viewjambi.juic;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_SubWindow implements com.trolltech.qt.QUiForm<QWidget>
{
    public QVBoxLayout verticalLayout_2;
    public QVBoxLayout mainLayou;
    public QWidget header;
    public QHBoxLayout headerLayou;
    public QHBoxLayout horizontalLayout;
    public QLabel headerLogo;
    public QWidget content;

    public Ui_SubWindow() { super(); }

    public void setupUi(QWidget SubWindow)
    {
        SubWindow.setObjectName("SubWindow");
        SubWindow.resize(new QSize(402, 326).expandedTo(SubWindow.minimumSizeHint()));
        verticalLayout_2 = new QVBoxLayout(SubWindow);
        verticalLayout_2.setSpacing(6);
        verticalLayout_2.setMargin(0);
        verticalLayout_2.setObjectName("verticalLayout_2");
        mainLayou = new QVBoxLayout();
        mainLayou.setSpacing(0);
        mainLayou.setObjectName("mainLayou");
        header = new QWidget(SubWindow);
        header.setObjectName("header");
        QPalette palette= new QPalette();
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Button, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Midlight, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Base, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Window, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Button, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Midlight, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Base, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Window, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Button, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Midlight, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Base, new QColor(204, 204, 204));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Window, new QColor(204, 204, 204));
        header.setPalette(palette);
        header.setAutoFillBackground(true);
        headerLayou = new QHBoxLayout(header);
        headerLayou.setMargin(4);
        headerLayou.setObjectName("headerLayou");
        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        headerLogo = new QLabel(header);
        headerLogo.setObjectName("headerLogo");

        horizontalLayout.addWidget(headerLogo);


        headerLayou.addLayout(horizontalLayout);


        mainLayou.addWidget(header);

        content = new QWidget(SubWindow);
        content.setObjectName("content");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Preferred, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(content.sizePolicy().hasHeightForWidth());
        content.setSizePolicy(sizePolicy);
        content.setMinimumSize(new QSize(150, 50));

        mainLayou.addWidget(content);


        verticalLayout_2.addLayout(mainLayou);

        retranslateUi(SubWindow);

        SubWindow.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget SubWindow)
    {
        SubWindow.setWindowTitle("");
        headerLogo.setText(com.trolltech.qt.core.QCoreApplication.translate("SubWindow", "TextLabel", null));
    } // retranslateUi

}

