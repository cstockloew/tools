package org.universAAL.ucc.viewjambi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.trolltech.qt.gui.QApplication;

public class Activator implements BundleActivator{
	public static Thread thread = null;
	
	public void start(final BundleContext context) throws Exception {
		thread = new Thread(new Runnable() {
			public void run() {
				QApplication.initialize(new String[0]);
				
				MainWindow testMainWindow = new MainWindow(context);
		        testMainWindow.show();
		        
		        QApplication.exec();
			}
		});
		thread.start();
	}

	@SuppressWarnings("deprecation")
	public void stop(BundleContext context) throws Exception {
		thread.stop();
	}
}