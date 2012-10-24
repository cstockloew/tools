package org.universAAL.ucc.core.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.trolltech.qt.gui.QBoxLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QVBoxLayout;

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
