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

package org.universAAL.ucc.viewjambi.overview;

import java.io.File;

import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.ucc.viewjambi.impl.Activator;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QAbstractButton;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

/**
 * 
 * @author tzentek - <a href="mailto:zentek@fzi.de">Tom Zentek</a>
 * @author mfrigge
 * 
 */
public class LabeledIcon extends QAbstractButton{

	public String text;
	public String icon;
	public QVBoxLayout layout;
	public QLabel label;
	public QLabel image;
	
	private String dataDir;
	private String separator;
	
	
	public LabeledIcon(String text, String icon){
		super();
		this.text = text;
		this.icon = icon;
		
		layout = new QVBoxLayout();
        layout.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
		label = new QLabel();
		image = new QLabel();
		
		label.setText(text);
//		label.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
		//String test=Activator.getContext().getBundle().getLocation();
		//if(test.startsWith("file"))
		File confHome = new File(new BundleConfigHome("ucc").getAbsolutePath());
		dataDir = confHome.getPath();
		separator = System.getProperty("file.separator");
		
		System.err.println("DATA DIR " + confHome.getPath()+separator+"icons"+separator+icon);
		image.setPixmap(new QPixmap(confHome.getPath()+separator+"icons"+separator+icon));
	//		image.setPixmap(new QPixmap(Activator.getInformation().getRunDir()+"icons/"+icon));
//			System.out.println("---->Icon location: " + Activator.getInformation().getRunDir()+"icons/"+icon);
		//This does not work with the runner
//		java.net.URL imgURL = (Activator.class.getClassLoader().getResource("/icons/"+icon));
//		if (imgURL!=null) {
//			String jarPath = "classpath:"
//				+ Activator.getContext().getBundle().getLocation().substring(6)
//				+ "#icon/" + icon;
			
			
			
//			image.setPixmap(new QPixmap(jarPath));
			//else
				//image.setPixmap(new QPixmap(jarPath+"#icon\\"+icon));
	//    	image.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
	    	
			image.setMinimumSize(new QSize(48,48));
	    	image.setMaximumSize(new QSize(48,48));
	    	layout.insertWidget(0, image);
//		}
//		else
//			System.err.println("Icon " + icon + " not found!");
    	label.setMinimumSize(new QSize(70, 30));
    	label.setMaximumSize(new QSize(70, 30));
    	
    	layout.insertWidget(1, label);

    	layout.setSizeConstraint(SizeConstraint.SetFixedSize);
    	layout.setWidgetSpacing(0);
		this.setMinimumSize(new QSize(72,72));
		this.setMaximumSize(new QSize(72,72));
		this.setLayout(layout);
		this.show();

		
	}
	
	public LabeledIcon(){
		this("","");
	}
	
	

	@Override
	protected void paintEvent(QPaintEvent arg0) {
		
	}

	public QSize sizeHint(){
		return new QSize(100, 100);
	}
	

	
}
