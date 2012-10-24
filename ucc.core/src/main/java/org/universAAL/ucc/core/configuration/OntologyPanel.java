package org.universAAL.ucc.core.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.trolltech.qt.gui.QBoxLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QListView;
import com.trolltech.qt.gui.QStandardItemModel;
import com.trolltech.qt.gui.QVBoxLayout;

public class OntologyPanel extends UCConfigItem{
	
	private String domain, caption;
	private ArrayList<UCConfigItem> configItems;
	
	public OntologyPanel(String uid){
		super(uid);
		configItems=new ArrayList<UCConfigItem>();
	}
	
	public void addConfigItem(UCConfigItem ci){
		this.configItems.add(ci);
	}
	
	public void setDomain(String domain){
		this.domain=domain;
	}
	public String getDomain(){
		return this.domain;
	}
	public void setCaption(String caption){
		this.caption=caption;
	}
	public String getCaption(){
		return this.caption;
	}
	
	public boolean isValid(){
		if(this.uid.isEmpty()||this.domain.isEmpty())
			return false;
		return true;
	}
	public void generateUI(QBoxLayout parent, int panelrow){
		QGroupBox panel= new QGroupBox();
		QHBoxLayout lay0=new QHBoxLayout();
    	QVBoxLayout lay= new QVBoxLayout();
    	
    	
    	QStandardItemModel model = new QStandardItemModel(3,1);
    	model.setData(0, 0, "Michel");
    	model.setData(1, 0, "Can");
    	model.setData(2, 0, "Tom");
    	QListView ont_view= new QListView();
    	ont_view.setModel(model);
    	//ont_view.model().re
    	//ont_view.ins
    	panel.setLayout(lay0);
    	lay0.insertWidget(0, ont_view);
    	lay0.insertLayout(1, lay);
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
