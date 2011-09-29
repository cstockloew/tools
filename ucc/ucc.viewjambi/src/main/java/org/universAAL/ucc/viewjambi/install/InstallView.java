package org.universAAL.ucc.viewjambi.install;

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
		QFileDialog dialog = new QFileDialog(this, "Please choose a file to install!", ".", "universAAL (*.uaal)");
		int result = dialog.exec();
		
		if (result == QDialog.DialogCode.Accepted.value()) {
			String file = dialog.selectedFiles().get(0);
			if (file != null)
				install_base.fileName.setText(file);
				System.out.println(install_base.fileName.text());
		}
	}
	
	protected void installFile() {
		System.out.println("InstallFile started");
		String p=install_base.fileName.text();
		p=p.replace("/", "\\");
		try {
			String appDir = Activator.getInstaller().installApplication(p);
			MainWindow.getInstance().removeSubWindow(this);
			MainWindow.getInstance().showLicense(appDir);
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
