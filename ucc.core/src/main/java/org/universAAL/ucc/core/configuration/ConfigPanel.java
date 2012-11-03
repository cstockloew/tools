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

package org.universAAL.ucc.core.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.trolltech.qt.gui.QBoxLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QVBoxLayout;

/**
 * 
 * @author tzentek - <a href="mailto:zentek@fzi.de">Tom Zentek</a>
 * 
 */


public class ConfigPanel extends UCConfigItem{

	private String caption;
	private ArrayList<UCConfigItem> configItems;
	
	public ConfigPanel(String uid){
		super(uid);
		configItems=new ArrayList<UCConfigItem>();
	}
	
	public void addConfigItem(UCConfigItem ci){
		this.configItems.add(ci);
	}
	
	public void setCaption(String caption){
		this.caption=caption;
	}
	public String getCaption(){
		return this.caption;
	}
	
	public boolean isValid(){
		if(this.caption.isEmpty()||this.name.isEmpty()||this.uid.isEmpty())
			return false;
		return true;
	}
	
	public void generateUI(QBoxLayout parent, int panelrow){
		QGroupBox panel= new QGroupBox();
    	QVBoxLayout lay= new QVBoxLayout();
    	panel.setLayout(lay);
    	panel.setTitle(this.getCaption());
    	parent.insertWidget(panelrow, panel);
    	
    	int row=0;
    	
    	Iterator<UCConfigItem> itr=configItems.iterator();
    	while(itr.hasNext()){
    		itr.next().generateUI(lay, row);
    		row++;
    	}
     	
    	
	}
	
	public boolean checkEnteredValues(){
		Iterator<UCConfigItem> itr=configItems.iterator();
		while(itr.hasNext()){
			if(!itr.next().checkEnteredValue()) return false;
		}
		return true;
	}
	public void getConfiguration(HashMap<String,String> conf){
		Iterator<UCConfigItem> itr=configItems.iterator();
		while(itr.hasNext())
			itr.next().getConfiguration(conf);
			
	}
}
