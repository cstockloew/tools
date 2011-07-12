package org.universAAL.ucc.viewjambi;

import org.osgi.framework.BundleContext;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;

public class MainWindow extends QMainWindow{
    private QMenu fileMenu;

    private QAction exitAct;

    public MainWindow(BundleContext context) {
        super();
        
        this.resize(1000, 800);
        
        createActions();
        createMenus();
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
    }

    private void createMenus() {
        fileMenu = menuBar().addMenu(tr("&File"));
        fileMenu.addAction(exitAct);
    }
}
