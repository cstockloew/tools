package org.universAAL.ucc.viewjambi.install;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;


import org.universAAL.ucc.viewjambi.Activator;
import org.universAAL.ucc.viewjambi.MainWindow;
import org.universAAL.ucc.viewjambi.SubWindow;

import org.universAAL.ucc.viewjambi.juic.Ui_License;




public class LicenseView extends SubWindow {
	
	private static Ui_License install_base = new Ui_License();
	private static String appDir;
	
	public LicenseView(MainWindow parent,String path) throws IOException {
		super(parent, LicenseView.install_base);
		appDir=path;
		StringBuffer l=readLicense(path+ File.separator +"EULA.txt");
		
		install_base.license.setText(l.toString());
		
		install_base.okButton.clicked.connect(this, "accept()");
		install_base.cancelButton.clicked.connect(this, "cancel()");
	}
	
	
	protected void accept() {
		this.parent.closeSubWindow(this);
		this.parent.configureApp(appDir);
	}
	
	

	    private StringBuffer readLicense(String path) throws IOException {
	        File file = new File(path);
	        StringBuffer contents = new StringBuffer();
	        BufferedReader reader = null;

	        
	            reader = new BufferedReader(new FileReader(file));
	            String text = null;

	            // repeat until all lines is read
	            while ((text = reader.readLine()) != null) {
	                contents.append(text)
	                    .append(System.getProperty(
	                        "line.separator"));
	            }
	        
	                    reader.close();
	                
	       
	        
	        return contents;
	    }
	

	
	protected void cancel() {
		Activator.getInstaller().revertInstallation(new File(appDir));
		this.parent.closeSubWindow(this);
	}
	
}
