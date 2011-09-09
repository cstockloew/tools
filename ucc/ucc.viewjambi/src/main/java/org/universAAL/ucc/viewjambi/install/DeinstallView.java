package org.universAAL.ucc.viewjambi.install;

import java.util.Iterator;
import java.util.List;

import org.universAAL.ucc.api.core.IDeinstaller;
import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_Deinstall;

import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QMessageBox;


public class DeinstallView extends SubWindow {
	
	private static Ui_Deinstall install_base = new Ui_Deinstall();
	private static QListWidgetItem currentItem=null;
	
	
	public DeinstallView() {
		super(DeinstallView.install_base);
		
		currentItem=null;
		install_base.okButton.clicked.connect(this, "deinstallFile()");
		install_base.cancelButton.clicked.connect(this, "cancel()");
		List<String> apps=Activator.getModel().getApplicationManagment().getInstalledApplications();
		Iterator<String> itr=apps.iterator();
		while(itr.hasNext()){
			QListWidgetItem item= new QListWidgetItem();
			item.setText(itr.next());
			install_base.listWidget.addItem(item);
		}
		install_base.listWidget.itemClicked.connect(this,"setCurrentItem(QListWidgetItem)");
		
	}
	protected void setCurrentItem(QListWidgetItem item){
		currentItem=item;
	}
	
	protected void deinstallFile() {
		if(currentItem==null){
			QMessageBox.critical(this, "Deinstall Application", "Please select an application!");
		}else{
			IDeinstaller deinstaller = Activator.getDeinstaller();
			if(deinstaller.deinstallAppication(currentItem.text())){
				QMessageBox.information(this, "Deinstall Application", "Deinstalltion of the application "+currentItem.text()+" completed!");
			}else{
				QMessageBox.critical(this, "Deinstall Application", "Could not deinstall the application!");
			}
			MainWindow.getInstance().closeSubWindow(this);
		}
	}
	
	protected void cancel() {
		MainWindow.getInstance().closeSubWindow(this);
	}
	
}
