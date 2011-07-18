package org.universAAL.ucc.viewjambi.install;

import org.universAAL.ucc.viewjambi.Activator;
import org.universAAL.ucc.viewjambi.MainWindow;
import org.universAAL.ucc.viewjambi.SubWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_Install;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QFileDialog;
import com.trolltech.qt.gui.QMessageBox;

public class InstallView extends SubWindow {
	
	private static Ui_Install install_base = new Ui_Install();
	
	
	public InstallView(MainWindow parent) {
		super(parent, InstallView.install_base);
		
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
		}
	}
	
	protected void installFile() {
		boolean result = Activator.getInstaller().installApplication(install_base.fileName.text());
		if (result)
			QMessageBox.information(this, "Sucess", "Application has been succesfull installed!");
		else
			QMessageBox.critical(this, "Error", "Application has not been installed!");
		this.parent.closeSubWindow(this);
	}
	
	protected void cancel() {
		this.parent.closeSubWindow(this);
	}
}
