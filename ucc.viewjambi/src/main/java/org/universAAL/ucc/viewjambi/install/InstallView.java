package org.universAAL.ucc.viewjambi.install;

import java.io.File;

import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_Install;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QFileDialog;
import com.trolltech.qt.gui.QMessageBox;


public class InstallView extends SubWindow {
	
	private static Ui_Install install_base = new Ui_Install();
	
	
	public InstallView() {
		super(InstallView.install_base);
		
		install_base.fileChoise.clicked.connect(this,"openFileChoise()");
		install_base.okButton.clicked.connect(this, "installFile()");
		install_base.cancelButton.clicked.connect(this, "cancel()");
	}
	
	protected void openFileChoise() {
		QFileDialog dialog = new QFileDialog(this, "Please choose a file to install!", ".", "universAAL (*.usrv)");
		int result = dialog.exec();
		
		if (result == QDialog.DialogCode.Accepted.value()) {
			String file = dialog.selectedFiles().get(0);
			if (file != null)
				install_base.fileName.setText(file);
				System.out.println("[InstsallView.openFileChoice] the file path: " + install_base.fileName.text());
		}
	}
	
	protected void installFile() {
		System.out.println("InstallFile started");
		String p=install_base.fileName.text();
		System.out.println("[InstallView.installFile] the install path: " + p);
		p=p.replace("/", "\\");
		String srvPath = Activator.getInstaller().installService(p);
		System.out.println("[InstallView.installFile] the installed/extracted path: " + srvPath);
		try {
			// install each .uapp file found			
			if(srvPath==null) 
				System.out.println("[InstallView.installFile] Error extracting .usrv Package");
			// convert "/" to "\"
			//TODO: update with the .usrv file structure	
			srvPath = srvPath + "\\config\\";
			System.out.println("[InstallView.installFile] the .uapp files contained in: " + srvPath);
			File appDir=new File(srvPath);
			String[] content = appDir.list();
			String appPath;
			for(int i=0;i<content.length;i++){
				if(content[i].endsWith(".uapp")) {				
					try {
						appPath = Activator.getInstaller().installApplication(srvPath + content[i]);
						MainWindow.getInstance().removeSubWindow(this);
						MainWindow.getInstance().showLicense(appPath);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
			}
			
			//String appDir = Activator.getInstaller().installApplication(p);
			//MainWindow.getInstance().removeSubWindow(this);
			//MainWindow.getInstance().showLicense(appDir);
		} catch (NullPointerException e){
			QMessageBox.critical(this, "Error", "Installation failed! The Application probably already is installed!");
		} catch (Exception e) {
			QMessageBox.critical(this, "Error", e.getMessage());
			MainWindow.getInstance().removeSubWindow(this);
		}
	}
	
	protected void cancel() {
		MainWindow.getInstance().removeSubWindow(this);
	}
	
}
