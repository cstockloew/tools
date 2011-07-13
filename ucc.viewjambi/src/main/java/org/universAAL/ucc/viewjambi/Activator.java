package org.universAAL.ucc.viewjambi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.universAAL.ucc.view.api.IMainWindow;

import com.trolltech.qt.gui.QApplication;

public class Activator implements BundleActivator {
	public static Thread thread = null;

	private IMainWindow mainWindow = null;

	static final String libraryNames[] = { "qtjambi.dll",
			"com_trolltech_qt_core.dll", "com_trolltech_qt_gui.dll",
			"QtCore4.dll", "QtGui4.dll" };

	static {
		for (int i=0; i<libraryNames.length; i++)
			extractDll(libraryNames[i]);
	}

	private static void extractDll(String name) {
		InputStream inputStream = Activator.class.getClassLoader()
				.getResourceAsStream(name);
		File libraryFile = new File(name);
		libraryFile.deleteOnExit();
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(
					libraryFile);
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, bytesRead);
			}
			fileOutputStream.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(final BundleContext context) throws Exception {

		Properties props = System.getProperties();
		String path = ".;" + props.getProperty("java.library.path");
		props.setProperty("java.library.path", path);
		System.setProperties(props);

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

		while (mainWindow == null)
			;

		context.registerService(new String[] { IMainWindow.class.getName() },
				mainWindow, null);
	}

	@SuppressWarnings("deprecation")
	public void stop(BundleContext context) throws Exception {
		thread.stop();
	}
}