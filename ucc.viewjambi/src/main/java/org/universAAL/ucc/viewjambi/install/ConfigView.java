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

	
	
	public ConfigView(String configpath) {
		super(install_base);
		
		appDir = configpath;
		try {
			Activator.getConfigurator().performConfiguration(configpath+ File.separator +"config.owl", install_base.verticalLayout_4);
		} catch (Exception e) {
			QMessageBox.critical(this, "Error", e.getMessage());
			e.printStackTrace();
		}
		/*if(appInfo.get("id")==null||appInfo.get("name")==null){
			QMessageBox.critical(this, "Error", "Application Info is not properly set!");
			
		}*/
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

		if(!Activator.getConfigurator().checkEnteredValues()){
			QMessageBox.critical(this, "Error", "Please fill out all fields!");
	    	 return;
		}
		String completed =Activator.getConfigurator().finishConfiguration(Activator.getInstaller().getInstalledBundles());
		 if(completed==null){
			 Activator.getInstaller().resetBundles();
			 QMessageBox.information(this, "Installation", "Installation successfully completed!");
		 }else
			 QMessageBox.critical(this, "Error", completed);
		 MainWindow.getInstance().removeSubWindow(this);
		 
	}
	
	protected void cancel() {
		Activator.getInstaller().revertInstallation(new File(appDir));
		MainWindow.getInstance().removeSubWindow(this);
	}
}
