package org.universAAL.ucc.viewjambi.install;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import org.osgi.framework.Bundle;
import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_Configure;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QBoxLayout;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class ConfigView extends SubWindow {
	
	private static Ui_Configure install_base=new Ui_Configure();
	private static String appDir;
	private HashMap<String,QObject> configdata= new HashMap<String, QObject>(); 
	private HashMap<String,String> appInfo=new HashMap<String,String>();
	
	
	public ConfigView(String configpath) {
		super(install_base);
		
		appDir = configpath;
		try {
			if(!performConfiguration(configpath+ File.separator +"config.xml", install_base)){
				QLabel info = new QLabel();
				info.setText("No Configuration necessary!");
				 install_base.verticalLayout_4.insertWidget(0,info);
			}
		} catch (XMLStreamException e) {
			QMessageBox.critical(this, "Error", "Error parsing config file!");
		}
		if(appInfo.get("id")==null||appInfo.get("name")==null){
			QMessageBox.critical(this, "Error", "Application Info is not properly set!");
			
		}
		install_base.okButton.clicked.connect(this, "saveConfiguration()");
		install_base.cancelButton.clicked.connect(this, "cancel()");
		//this.parent.adjustSize();
		//this.resize(800, 800);
		MainWindow.getInstance().activateWindow();
		this.activateWindow();
		this.update();
		this.updateGeometry();
		//this.setMaximumSize(300, 300);
		//this.adjustSize();
		
	}
	
	protected void saveConfiguration() {
		 Collection<QObject> c = configdata.values();
		 Iterator<QObject> itr = c.iterator();
		 HashMap<String,String> conf=new HashMap<String,String>();
		 while(itr.hasNext()){
			 String key;
			 String value = "";
			 QObject a = itr.next();
			 key = a.objectName();
		      if(a.getClass().getName().equals("com.trolltech.qt.gui.QComboBox")){
		    	 value=((QComboBox) a).currentText();
		      }
		      if(a.getClass().getName().equals("com.trolltech.qt.gui.QLineEdit")){
		    	  if(((QLineEdit) a).text().equals("")){
		    		  QMessageBox.critical(this, "Error", "Please fill out all fields!");
			    	  return;
		    	  }
		    	  value=((QLineEdit) a).text();  
		      }
		      if(a.getClass().getName().equals("com.trolltech.qt.gui.QCheckBox")){
		    	  System.out.print(": "+((QCheckBox) a).isChecked());
		      	  if(((QCheckBox) a).isChecked()){
		      		  value = "true";
		      	  }else{
		      		  value = "false";
		      	  }
		      }
		      System.out.println();
		      //returnMap.put(key, value);
		    	  //value=new Boolean(((QCheckBox) a).isChecked()).toString();
		      conf.put(key, value);    	  
		 }
		 ArrayList<Bundle> ib = Activator.getInstaller().getInstalledBundles();
		 String install_base = ib.get(0).getLocation();
		 install_base=install_base.substring(5, install_base.lastIndexOf(File.separator));
		 conf.put("install_base",install_base);
		 conf.put("appName", appInfo.get("name"));
		 String completed=Activator.getConfigurator().finishConfiguration(appInfo.get("id"), ib , conf);
		 if(completed==null){
			 Activator.getInstaller().resetBundles();
			 QMessageBox.information(this, "Installation", "Installation successfully completed!");
		 }else
			 QMessageBox.critical(this, "Error", completed);
		 MainWindow.getInstance().closeSubWindow(this);
		 
	}
	
	protected void cancel() {
		Activator.getInstaller().revertInstallation(new File(appDir));
		MainWindow.getInstance().closeSubWindow(this);
	}
	
	/** For convenience and test purposes the xml parsing is done here. 
	 *  In the future this method should be moved to the Configurator class.
	 *  
	 * @param Path
	 * @throws XMLStreamException 
	 */
	@SuppressWarnings("unchecked")
	public boolean performConfiguration(String Path, Ui_Configure conf) throws XMLStreamException{
		
		
		QBoxLayout parent = conf.verticalLayout_4;
		QWidget current = new QWidget();
		QLabel curLabel = new QLabel();
		String curid=null;
		int row =0;
		int panelrow=0;
		boolean added=false;
		
		
	      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	      XMLEventReader  evRd = inputFactory.createXMLEventReader( new StreamSource( Path ) );
	      Stack<String>   stck = new Stack<String>();
	      while( evRd.hasNext() ) {
	         XMLEvent ev = evRd.nextEvent();
	         if( ev.isStartElement() ) {
	            stck.push( ev.asStartElement().getName().getLocalPart() );
	            
	            Iterator<Attribute> iter = ev.asStartElement().getAttributes();
	            while( iter.hasNext() ) {
	               Attribute a = iter.next();
	               if(ev.asStartElement().getName().getLocalPart().equals("root"))
	            	   appInfo.put(a.getName().getLocalPart(), a.getValue());
	               curid=a.getValue();
	            }
	            if(ev.asStartElement().getName().getLocalPart().equals("panel")){
	            	QGroupBox panel= new QGroupBox();
	            	QVBoxLayout lay= new QVBoxLayout();
	            	panel.setLayout(lay);
	            	panel.setTitle(curid);
	            	parent.insertWidget(panelrow, panel);
	            	parent=lay;
	            	panelrow++;
	            }
	         }
	         if( ev.isCharacters() ) {
	            String s = ev.asCharacters().getData();
	            if( s.trim().length() > 0 ){
	            	if(stck.peek().equals("type")){
	            		if(s.equals("TEXTBOX"))current=new QLineEdit();
	        			if(s.equals("DROPDOWNLIST"))current=new QComboBox();
	        			if(s.equals("CHECKBOX"))current=new QCheckBox();
	        			
	        			current.setObjectName(curid);
	            	}
	            	if(stck.peek().equals("title")){
	            		curLabel=new QLabel();
	            		curLabel.setText(s);
	            	}
	            	if(stck.peek().equals("domain")){
	            		String[] entries=s.split(";");
	            		for(int i=0;i<entries.length;i++){
	            			((QComboBox) current).addItem(entries[i]);
	            		}
	            	
	            	}
	            }
	         }
	         if( ev.isEndElement()){
	        	 String stckitem=stck.pop();
	        	 if(stckitem.equals("element")){
	        		 QHBoxLayout temp = new QHBoxLayout();
	        		 current.setParent(conf.verticalLayout_4);
	        		 temp.insertWidget(0,curLabel);
	        		 temp.insertWidget(1,current);
	        		 parent.insertLayout(row, temp);
	        		 configdata.put(current.objectName(), current);
	        		 row++;
	        		 added=true;
	        	 }
	        	 if(stckitem.equals("panel")){
	        		 parent=conf.verticalLayout_4;
	        		 row=0;
	        	 }
	         }
	      }
	      conf.verticalLayout_4.activate();
	      return added;
	}
}
