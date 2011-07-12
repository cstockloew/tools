package org.universAAL.ucc.viewjambi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.ucc.model.api.IModel;
import org.universAAL.ucc.view.api.IMainWindow;

import com.trolltech.qt.gui.QApplication;

public class Activator implements BundleActivator{
	public static Thread thread = null;
	
	private IMainWindow mainWindow = null;
	
	public void start(final BundleContext context) throws Exception {
		thread = new Thread(new Runnable() {
			public void run() {
				QApplication.initialize(new String[0]);
				
				MainWindow newWindow = new MainWindow(context);
				newWindow.show();
				
				mainWindow = newWindow;
		        
		        QApplication.exec();
			}
		});
		thread.start();
		
		while (mainWindow == null);
		
		context.registerService(new String[] { IMainWindow.class.getName() }, mainWindow, null);
	}

	@SuppressWarnings("deprecation")
	public void stop(BundleContext context) throws Exception {
		thread.stop();
	}
}