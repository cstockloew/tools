package org.universAAL.ucc.viewjambi;

import org.osgi.framework.BundleContext;

import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.api.view.ISubWindow;
import org.universAAL.ucc.viewjambi.install.InstallView;

import com.trolltech.qt.gui.*;

public class MainWindow extends QMainWindow implements IMainWindow {
    private QMenu fileMenu;
    private QMenu appMenu;

    private QAction exitAct;
    private QAction installAct;
    
    private QMdiArea mdi;

    public MainWindow(BundleContext context) {
        super();
        
        mdi = new QMdiArea();
        
        this.resize(1000, 800);
        
        createActions();
        createMenus();
        
        this.setCentralWidget(mdi);
    }
    
    @Override
    protected void resizeEvent(QResizeEvent event) {
    	super.resizeEvent(event);
    }

    private void createActions() {
        exitAct = new QAction(tr("E&xit"), this);
        exitAct.setShortcut(tr("Ctrl+Q"));
        exitAct.setStatusTip(tr("Exit the application"));
        exitAct.triggered.connect(this, "close()");
        
        installAct = new QAction(tr("&Install"), this);
        installAct.setShortcut(tr("Ctrl+I"));
        installAct.setStatusTip(tr("Start the installation of a new application"));
        installAct.triggered.connect(this, "installApp()");
    }

    private void createMenus() {
        fileMenu = menuBar().addMenu(tr("&File"));
        fileMenu.addAction(exitAct);
        appMenu = menuBar().addMenu(tr("&Applications"));
        appMenu.addAction(installAct);
    }

	public void addSubWindow(ISubWindow subWindow) {
		// TODO Auto-generated method stub
		
	}

	public void closeSubWindow(ISubWindow subWindow) {
		// TODO Auto-generated method stub
		
	}

	public boolean initialize() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void installApp() {
		InstallView subWindow = new InstallView(this);
        subWindow.setGeometry(100, 100, 500, 400);
        subWindow.setWindowTitle("Install a new application!");
        mdi.addSubWindow(subWindow);
        subWindow.show();
	}
}
