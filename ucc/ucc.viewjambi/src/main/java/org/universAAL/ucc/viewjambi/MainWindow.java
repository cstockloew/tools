package org.universAAL.ucc.viewjambi;

import java.io.IOException;
import java.util.HashMap;

import org.osgi.framework.BundleContext;

import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.api.view.ISubWindow;
import org.universAAL.ucc.viewjambi.information.InformationView;
import org.universAAL.ucc.viewjambi.install.ConfigView;
import org.universAAL.ucc.viewjambi.install.InstallView;
import org.universAAL.ucc.viewjambi.install.LicenseView;
import org.universAAL.ucc.viewjambi.juic.Ui_MainWindow;
import org.universAAL.ucc.viewjambi.overview.OverviewView;

import com.trolltech.qt.gui.*;

public class MainWindow extends QMainWindow implements IMainWindow {

	private Ui_MainWindow ui_base = new Ui_MainWindow();
	private HashMap<ISubWindow,QMdiSubWindow> subWindows = new HashMap<ISubWindow,QMdiSubWindow>();

    public MainWindow(BundleContext context) {
        super();
        
        ui_base.setupUi(this);
//        
//        System.out.println("Installing Bundle..");
//        try{
//        	context.installBundle("file:C:\\plugins\\de.bla.testBundle_1.0.0.201107232348.jar");
//        }catch(Exception e){
//        	e.printStackTrace();
//        	System.out.println("Installing failed..");
//        }
        
        ui_base.actionExit.triggered.connect(this, "close()");
        ui_base.actionInstall.triggered.connect(this, "installApp()");
//        ui_base.actionUninstall.triggered.connect(this, "uninstallApp()");
        ui_base.actionOverview.triggered.connect(this, "overviewApp()");
        ui_base.actionSystem_Information.triggered.connect(this, "showInformation()");
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
	
	protected void uninstallApp() {
		new InstallView(this);
	}
	
	protected void overviewApp() {
		new OverviewView(this);
	}
	
	protected void showInformation() {
		new InformationView(this);
	}
}
