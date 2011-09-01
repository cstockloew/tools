package org.universAAL.ucc.viewjambi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.osgi.framework.BundleContext;

import org.universAAL.ucc.api.core.IDeinstaller;
import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.api.view.ISubWindow;
import org.universAAL.ucc.viewjambi.information.InformationView;
import org.universAAL.ucc.viewjambi.install.ConfigView;
import org.universAAL.ucc.viewjambi.install.DeinstallView;
import org.universAAL.ucc.viewjambi.install.InstallView;
import org.universAAL.ucc.viewjambi.install.LicenseView;
import org.universAAL.ucc.viewjambi.juic.Ui_MainWindow;
import org.universAAL.ucc.viewjambi.layouts.OverviewGridLayout;
import org.universAAL.ucc.viewjambi.overview.GridView;
import org.universAAL.ucc.viewjambi.overview.LabeledIcon;
import org.universAAL.ucc.viewjambi.overview.OverviewView;

import com.trolltech.qt.QUiForm;
import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Global.QtMsgType;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QMessageBox.StandardButton;

public class MainWindow extends QMainWindow implements IMainWindow {

	private Ui_MainWindow ui_base = new Ui_MainWindow();
	private HashMap<ISubWindow,QMdiSubWindow> subWindows = new HashMap<ISubWindow,QMdiSubWindow>();
	public static OverviewView overview;
	
	
    public MainWindow(BundleContext context) {
        super();
        
        ui_base.setupUi(this);
        
        ui_base.actionExit.triggered.connect(this, "close()");
        ui_base.actionInstall.triggered.connect(this, "installApp()");
        ui_base.actionDeinstall.triggered.connect(this, "uninstallApp()");
        ui_base.actionOverview.triggered.connect(this, "overviewApp()");
        ui_base.actionSystem_Information.triggered.connect(this, "showInformation()");
        new GridView(this);
       
        
        
    }
    
    @Override
    protected void resizeEvent(QResizeEvent event) {
    	super.resizeEvent(event);
    }

    @Override
	public void addSubWindow(ISubWindow subWindow) {
		if (subWindow instanceof SubWindow) {
			QMdiSubWindow mdiWindow = ui_base.mdiArea.addSubWindow((QWidget)subWindow);
			subWindows.put(subWindow, mdiWindow);
			mdiWindow.show();
		}
	}

    @Override
	public void closeSubWindow(ISubWindow subWindow) {
		QMdiSubWindow mdiChild = subWindows.remove(subWindow);
		if (mdiChild != null)
			mdiChild.close();
	}

    @Override
	public boolean initialize() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void installApp() {
		new InstallView(this);
	}
	public void configureApp(String path){
		new ConfigView(this, path);
	}
	public void showLicense(String path) throws IOException{
		new LicenseView(this, path);
	}
	public void deinstallApp(){
		new DeinstallView(this);
	}
	
	protected void uninstallApp() {
		if(overview != null){
			if(overview.isVisible()){
				QModelIndex index = OverviewView.treeView.currentIndex();
					if(Activator.getModel().getApplicationManagment().containsApplication((String) index.data())){
						if(QMessageBox.question(this, "Deinstall Application", "Do you want to delete the application "+index.data()+"?", QMessageBox.StandardButton.Yes, QMessageBox.StandardButton.No) == StandardButton.Yes.value()){
						IDeinstaller deinstaller = Activator.getDeinstaller();
						if(deinstaller.deinstallAppication((String) index.data())){
							QMessageBox.information(this, "Deinstall Application", "Deinstalltion of the application "+index.data()+" completed!");
						}else{
							QMessageBox.critical(this, "Deinstall Application", "Could not deinstall the application!");
						}
					}
				}
			}
		}
	}
	
	protected void overviewApp() {
		if(overview == null){
			overview = new OverviewView(this);
		}else if(!overview.isWindow())
			overview = new OverviewView(this);
		
	}
	
	protected void showInformation() {
		new InformationView(this);
	}
}
