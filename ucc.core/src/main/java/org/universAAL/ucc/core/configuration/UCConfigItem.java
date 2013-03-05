package org.universAAL.ucc.core.configuration;

import java.util.HashMap;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class UCConfigItem {

	protected String value;
	protected int locationID;
	protected String hoverText, labelText;
	protected String name;
	protected String uid;
	protected String isInPanel;
	protected QWidget uiElement;
	protected String defaultValue;

	
	public UCConfigItem(String uid){
		this.uid=uid;
	}
	public void setDefaultValue(String defaultValue){
		this.defaultValue=defaultValue;
	}
	public void setValue(String value){
		this.value=value;
	}
	public String getValue(){
		return this.value;
	}
	
	public void setLocationID(int loc){
		this.locationID=loc;
	}
	public int getLocationID(){
		return this.locationID;
	}
	public void setHoverText(String ht){
		this.hoverText=ht;
	}
	public String getHoverText(){
		return this.hoverText;
	}
	public void setLabelText(String lt){
		this.labelText=lt;
	}
	public String getLabelText(){
		return this.labelText;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setPanel(String panel){
		this.isInPanel=panel;
	}
	public String getPanel(){
		return this.isInPanel;
	}
	public String getUID(){
		return this.uid;
	}
	
	public boolean checkEnteredValue(){
		if(this.uiElement.getClass().getName().equals("com.trolltech.qt.gui.QLineEdit")){
	    	  if(((QLineEdit) this.uiElement).text().equals("")){
	    		  return false;
	    	  }  
	      }
		return true;
	}
	public boolean isValid(){
		if(this.name.isEmpty()||this.uid.isEmpty()||this.isInPanel.isEmpty()||this.labelText.isEmpty()||this.value.isEmpty())
			return false;
		return true;
	}
	
	public void generateUI(QVBoxLayout parent, int row){
		QLabel curLabel= new QLabel();
		QWidget current=new QWidget();
		if(this.value.equals("int")||this.value.equals("String")){
			current = new QLineEdit();
			if(this.defaultValue!=null)
				((QLineEdit)current).setText(this.defaultValue);
		}
		if(this.value.equals("boolean")){
			current = new QCheckBox();
			if(this.defaultValue!=null)
				if(this.defaultValue.equals("TRUE"))
					((QCheckBox)current).setChecked(true);
		}
		
		current.setObjectName(this.name);
		curLabel.setText(this.labelText);
		curLabel.setToolTip(this.hoverText);
		current.setToolTip(this.hoverText);
		
		
		
		 QHBoxLayout temp = new QHBoxLayout();
		 //current.setParent(parent);
		 temp.insertWidget(0,curLabel);
		 temp.insertWidget(1,current);
		 parent.insertLayout(row, temp);
		 this.uiElement=current;
		
	}
	public void getConfiguration(HashMap<String, String> conf){
		if(this.uiElement.getClass().getName().equals("com.trolltech.qt.gui.QLineEdit"))
	    	  conf.put(this.getName(),((QLineEdit) this.uiElement).text());
		if(this.uiElement.getClass().getName().equals("com.trolltech.qt.gui.QCheckBox")){
			if(((QCheckBox) this.uiElement).isChecked())
				conf.put(this.getName(), "true");
			else
				conf.put(this.getName(), "false");
		}
	  
	}
	
}
