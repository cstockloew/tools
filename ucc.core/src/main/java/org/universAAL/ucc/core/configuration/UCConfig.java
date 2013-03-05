package org.universAAL.ucc.core.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.trolltech.qt.gui.QBoxLayout;
import com.trolltech.qt.gui.QLabel;

public class UCConfig {
	private String versionNumber, name, author, uid;
	
	private ArrayList<ConfigPanel> confPanels;
	private ArrayList<OntologyPanel> ontPanels;
	
	public UCConfig(String uid){
		this.uid=uid;
		confPanels=new ArrayList<ConfigPanel>();
		ontPanels=new ArrayList<OntologyPanel>();
	}
	
	public void addConfigPanel(ConfigPanel panel){
		this.confPanels.add(panel);
	}

	public void addOntologyPanel(OntologyPanel panel){
		this.ontPanels.add(panel);
	}
	
	public void addConfigItem(UCConfigItem ci){
		Iterator<ConfigPanel> conf_itr=this.confPanels.iterator();
		while(conf_itr.hasNext()){
			ConfigPanel cur=conf_itr.next();
			if(cur.getUID().equals(ci.getPanel())){
				cur.addConfigItem(ci);
				return;
			}
		}
		
		Iterator<OntologyPanel> ont_itr=this.ontPanels.iterator();
		while(ont_itr.hasNext()){
			OntologyPanel cur=ont_itr.next();
			if(cur.getUID().equals(ci.getPanel())){
				cur.addConfigItem(ci);
				return;
			}
		}
	}
	
	public void setVersionNumber(String version){
		this.versionNumber=version;
	}
	public String getVersionNumber(){
		return this.versionNumber;
	}
	

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	

	public void setAuthor(String author){
		this.author=author;
	}
	public String getAuthor(){
		return this.author;
	}
	

	public String getUID(){
		return this.uid;
	}
	
	public boolean isValid(){
		if(this.author.isEmpty()||this.versionNumber.isEmpty()||this.name.isEmpty()||this.uid.isEmpty())
			return false;
		return true;
	}

	public void generateUI(QBoxLayout parent){
		if(confPanels.size()==0&&ontPanels.size()==0){
			QLabel info = new QLabel();
			info.setText("No Configuration necessary!");
			 parent.insertWidget(0,info);
		}else{
			int panelrow=0;
			Iterator<ConfigPanel> itr=confPanels.iterator();
			while(itr.hasNext()){
				itr.next().generateUI(parent, panelrow);
				panelrow++;
			}
			Iterator<OntologyPanel> ont_itr=ontPanels.iterator();
			while(ont_itr.hasNext()){
				ont_itr.next().generateUI(parent, panelrow);
				panelrow++;
			}
		}
	}
	
	public boolean checkEnteredValues(){
		Iterator<ConfigPanel> conf_itr=this.confPanels.iterator();
		while(conf_itr.hasNext()){
			if(!conf_itr.next().checkEnteredValues()) return false;
		}
		
		Iterator<OntologyPanel> ont_itr=this.ontPanels.iterator();
		while(ont_itr.hasNext()){
			if(!ont_itr.next().checkEnteredValues()) return false;
		}
		return true;
	}
	
	public HashMap<String, String> getConfiguraton(){
		HashMap<String,String> conf = new HashMap<String,String>();
		
		Iterator<ConfigPanel> conf_itr=this.confPanels.iterator();
		while(conf_itr.hasNext())
			conf_itr.next().getConfiguration(conf);
		
		Iterator<OntologyPanel> ont_itr=this.ontPanels.iterator();
		while(ont_itr.hasNext())
			ont_itr.next().getConfiguration(conf);
		
		return conf;
	}
}
