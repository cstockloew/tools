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
	private static boolean mpa=false;
	
	public LicenseView(String path) throws IOException {
		super(LicenseView.install_base);
		appDir=path;
		mpa=isMPA();
		StringBuffer l=readLicense(path+ File.separator +"EULA.txt");
		
		install_base.license.setText(l.toString());
		
		install_base.okButton.clicked.connect(this, "accept()");
		install_base.cancelButton.clicked.connect(this, "cancel()");
	}
	
	
	protected void accept() {
		MainWindow.getInstance().removeSubWindow(this);
		// check if this is MPA
		if (mpa) MainWindow.getInstance().deployStrategy(appDir);
		else MainWindow.getInstance().configureApp(appDir);
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
		if (!mpa)
			Activator.getInstaller().revertInstallation(new File(appDir));
		MainWindow.getInstance().removeSubWindow(this);
	}
	
	private boolean isMPA()  {
		File folder=new File(appDir);
		String[] content = folder.list();
		for(int i=0;i<content.length;i++){
			if(content[i].endsWith(".mpa")) 
				return true;
		}
		return false;
	}
}
