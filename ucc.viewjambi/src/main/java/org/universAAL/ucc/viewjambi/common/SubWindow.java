/*
	Copyright 2007-2014 FZI, http://www.fzi.de
	Forschungszentrum Informatik - Information Process Engineering (IPE)

	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package org.universAAL.ucc.viewjambi.common;

import java.io.File;

import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.api.view.ISubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_SubWindow;

import com.trolltech.qt.QUiForm;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

/**
 * 
 * @author tzentek - <a href="mailto:zentek@fzi.de">Tom Zentek</a>
 * 
 */

public class SubWindow extends QWidget implements ISubWindow {

	private Ui_SubWindow ui_base = new Ui_SubWindow();
	protected IMainWindow parent = null;
	
	private String separator;

	protected SubWindow(QUiForm<QWidget> ui_content) {
		super(MainWindow.getInstance());
		this.parent = MainWindow.getInstance();
		
		ui_base.setupUi(this);

        File confHome = new File(new BundleConfigHome("ucc").getAbsolutePath());
        separator = System.getProperty("file.separator");    
        String jarPath = confHome.getPath()+separator+"icons"+separator+"uaal.bmp";

		
//		String jarPath = "classpath:" + Activator.getContext().getBundle().getLocation().substring(6) + "#images/uaal.bmp";
		QPixmap pixmap = new QPixmap(jarPath);
		ui_base.headerLogo.setPixmap(pixmap);
		
		ui_content.setupUi(ui_base.content);
		
		this.parent.addSubWindow(this);
	}

	public void initialize() {
		this.show();
	}
	
	protected QWidget getContentWidget() {
		return ui_base.content;
	}

}

