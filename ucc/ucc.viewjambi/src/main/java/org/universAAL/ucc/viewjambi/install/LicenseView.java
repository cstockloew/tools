package org.universAAL.ucc.viewjambi.install;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;


import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_License;

public class LicenseView extends SubWindow {
	
	private static Ui_License install_base = new Ui_License();
	private static String appDir;
	//private static boolean mpa=false;
	//private static String MPA_EXTENSION="-mpa";
	
	public LicenseView(String path) throws IOException {
		super(LicenseView.install_base);
		appDir=path;
		//mpa=isMPA();
		StringBuffer l=readLicense(path+ File.separator + "license" + File.separator +"ASL2.0.txt");
		
		install_base.license.setText(l.toString());
		
		install_base.okButton.clicked.connect(this, "accept()");
		install_base.cancelButton.clicked.connect(this, "cancel()");
	}
	
	
	protected void accept() {
		MainWindow.getInstance().removeSubWindow(this);
		MainWindow.getInstance().deployStrategy(appDir);
		// TODO: how to configure an application?
		// MainWindow.getInstance().configureApp(appDir);
	}

    private StringBuffer readLicense(String path) throws IOException {
    	System.out.println("[LicenseView.readLicense] the license file is: " + path);
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
		// TODO: do we need to revert the installation of MPA at this point? Maybe not, since nothing is really installed
		//	Activator.getInstaller().revertInstallation(new File(appDir));
		MainWindow.getInstance().removeSubWindow(this);
	}
	
/*	private boolean isMPA()  {
		File folder=new File(appDir);
		String[] content = folder.list();
		for(int i=0;i<content.length;i++){
			if(content[i].contains(MPA_EXTENSION)) 
				return true;
		}
		return false;
	}  */
}
