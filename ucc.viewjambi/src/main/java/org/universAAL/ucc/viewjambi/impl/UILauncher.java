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

package org.universAAL.ucc.viewjambi.impl;

import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.api.view.IUILauncher;
import org.universAAL.ucc.viewjambi.overview.GridView;

import com.trolltech.qt.gui.QApplication;

/**
 * 
 * @author mfrigge
 * 
 */

public class UILauncher implements IUILauncher{

	private boolean isUIOpen=false;
	
	public void showUi() {
		new Thread(new Runnable() {
			public void run() {
				try {
					QApplication.initialize(new String[0]);
	
					Activator.mainWindow = MainWindow.getInstance();
					Activator.mainWindow.show();
					
					Activator.setPluginBase(new PluginBase(new GridView()));
					isUIOpen = true;
					Activator.loadPlugins();
					QApplication.exec();
				}
				finally {
					isUIOpen = true;
				}
			}
		}).start();

		while (!isUIOpen){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			

		if (Activator.mainWindow != null) {
			System.err.println("Jambi-Main-View started");
			Activator.getContext().registerService(IPluginBase.class.getName(), Activator.getPluginBase(), null);
			Activator.getContext().registerService(IMainWindow.class.getName(), Activator.mainWindow, null);
		}
		else 
			System.err.println("Jambi-Main-View NOT started");
		
	}

	public boolean isUiOpen() {
		return isUIOpen;
	}

}
