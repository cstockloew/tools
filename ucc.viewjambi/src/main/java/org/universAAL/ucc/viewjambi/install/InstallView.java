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
		MainWindow.getInstance().removeSubWindow(this);
		String srvPath = Activator.getInstaller().installService(p);
			//String appDir = Activator.getInstaller().installApplication(p);
			//MainWindow.getInstance().removeSubWindow(this);
			//MainWindow.getInstance().showLicense(appDir);

	}  
	
	protected void cancel() {
		MainWindow.getInstance().removeSubWindow(this);
	}
	
}
