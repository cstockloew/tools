package org.universAAL.ucc.viewjambi.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.universAAL.ucc.api.core.IDeinstaller;
import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.api.view.ISubWindow;
import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.information.InformationView;
import org.universAAL.ucc.viewjambi.install.ConfigView;
import org.universAAL.ucc.viewjambi.install.DeinstallView;
import org.universAAL.ucc.viewjambi.install.DeployConfigView;
import org.universAAL.ucc.viewjambi.install.DeployStrategyView;
import org.universAAL.ucc.viewjambi.install.InstallView;
import org.universAAL.ucc.viewjambi.install.LicenseView;
import org.universAAL.ucc.viewjambi.install.MpaParser;
import org.universAAL.ucc.viewjambi.juic.Ui_MainWindow;
import org.universAAL.ucc.viewjambi.overview.OverviewView;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.QtBlockedSlot;
import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QMessageBox.StandardButton;

public class MainWindow extends QMainWindow implements IMainWindow {

	private InstallSignal installSignal;
	private Ui_MainWindow ui_base = new Ui_MainWindow();
	private HashMap<ISubWindow,QMdiSubWindow> subWindows = new HashMap<ISubWindow,QMdiSubWindow>();
	public static OverviewView overview;
	private static MainWindow instance = null;
	
	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}
		return instance;
	}
	
	
    protected MainWindow() {
        super();
        
        ui_base.setupUi(this);
        
        ui_base.actionExit.triggered.connect(this, "close()");
        ui_base.actionInstall.triggered.connect(this, "installApp()");
        ui_base.actionDeinstall.triggered.connect(this, "uninstallApp()");
        ui_base.actionOverview.triggered.connect(this, "overviewApp()");
        ui_base.actionSystem_Information.triggered.connect(this, "showInformation()");
        
        installSignal=new InstallSignal();
        installSignal.valueChanged.connect(this, "showLicense(String, String)");
    }
    
    @Override
    protected void resizeEvent(QResizeEvent event) {
    	super.resizeEvent(event);
    }

	public void addSubWindow(ISubWindow subWindow) {
		if (subWindow instanceof SubWindow) {
			
			QMdiSubWindow mdiWindow = ui_base.mdiArea.addSubWindow((QWidget)subWindow);
			subWindows.put(subWindow, mdiWindow);
			mdiWindow.show();
		}
	}

	public void removeSubWindow(ISubWindow subWindow) {
		QMdiSubWindow mdiChild = subWindows.remove(subWindow);
		if (mdiChild != null)
			mdiChild.close();
	}
	
	public void showSubWindow(ISubWindow subWindow) {
		QMdiSubWindow child = this.subWindows.get(subWindow);
		if (child == null) {
			System.err.println("Can not find SubWindow --> can not show it!");
			return;
		}
		child.show();
	}


	public void hideSubWindow(ISubWindow subWindow) {
		QMdiSubWindow child = this.subWindows.get(subWindow);
		if (child == null) {
			System.err.println("Can not find SubWindow --> can not hide it!");
			return;
		}
		child.hide();
	}

	public boolean initialize() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void installApp() {
		new InstallView();
	}
	public void installApp(String path, String serviceId){
		installSignal.setValue(path);
	} 
	public void configureApp(String path){
		new ConfigView(path);
	}
	public void showLicense(String path, String serviceId) {
		new LicenseView(path, serviceId);
	}
	public void deinstallApp(){
		new DeinstallView();
	}
	public void deployConfigure(String path, MpaParser mpa, Map peers, String serviceId){
		new DeployConfigView(path, mpa, peers, serviceId);
	}
	public void deployStrategy(String path, String serviceId){
		new DeployStrategyView(path, serviceId);
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
			overview = new OverviewView();
		}else if(!overview.isWindow())
			overview = new OverviewView();
		
	}
	
	protected void showInformation() {
		new InformationView();
	}
	
	class InstallSignal extends QSignalEmitter 
	{
	   String value;
	   public Signal1<String> valueChanged = new Signal1<String>();
	   @QtBlockedSlot
	   public String value()
	   {
	      return value;
	    }
	    public void setValue(String val)
	    {
	        if (value != val) 
	        {
	           value = val;
	            valueChanged.emit(value);
	        }
	    }
	    public InstallSignal()
	    {
	        value = "";
	    }
	}


}
