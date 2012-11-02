package org.universAAL.ucc.viewjambi.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.ucc.api.core.IConfigurator;
import org.universAAL.ucc.api.core.IDeinstaller;
import org.universAAL.ucc.api.core.IInformation;
import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.api.model.IModel;
import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.api.view.IUILauncher;
import org.universAAL.ucc.viewjambi.overview.GridView;

import com.trolltech.qt.gui.QApplication;

public class Activator implements BundleActivator {
	private static Thread thread = null;
	private static BundleContext context = null;

	private static IInstaller installer = null;
	private static IDeinstaller deinstaller = null;
	private static IInformation information = null;
	private static IModel model = null;
	private static IConfigurator configurator = null;
	private static PluginBase pluginBase = null;
	private static IUILauncher uiLauncher = null;
	private static final String plugins="ucc/ucc_plugins/";
	
	public static MainWindow mainWindow = null;
	private boolean startCheck = false;

	static final String libraryNames[] = { "qtjambi.dll",
			"com_trolltech_qt_core.dll", "com_trolltech_qt_gui.dll", "com_trolltech_qt_network.dll",
			"com_trolltech_qt_phonon.dll", "com_trolltech_qt_webkit.dll",
			"QtCore4.dll", "QtGui4.dll", "QtNetwork4.dll", "phonon4.dll" , "QtWebKit4.dll"};

	static {
		for (int i = 0; i < libraryNames.length; i++)
			extractDll("/bin/",libraryNames[i]);
	}

	private static void extractDll(String path, String name) {
		try {
			InputStream inputStream = Activator.class.getClassLoader().getResource(path+name).openStream();
			File libraryFile = new File(name);
			//libraryFile.deleteOnExit();
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

	public static BundleContext getContext() {
		return context;
	}

	public static IInstaller getInstaller() {
		Activator.getServices();
		return Activator.installer;
	}

	public static IDeinstaller getDeinstaller() {
		Activator.getServices();
		return Activator.deinstaller;
	}
	
	public static IInformation getInformation() {
		Activator.getServices();
		return Activator.information;
	}
	
	public static IModel getModel() {
		Activator.getServices();
		return Activator.model;
	}
	public static IConfigurator getConfigurator() {
		Activator.getServices();
		return Activator.configurator;
	}
	public static PluginBase getPluginBase(){
		return Activator.pluginBase;
	}
	public static void setPluginBase(PluginBase pluginbase){
		Activator.pluginBase=pluginbase;
	}
	public static IUILauncher getUILauncher(){
		return uiLauncher;
	}

	public void start(final BundleContext lcontext) throws Exception {
		Activator.context = lcontext;
		
		Properties props = System.getProperties();
		String path = ".;" + props.getProperty("java.library.path");
		props.setProperty("java.library.path", path);
		System.setProperties(props);
		
		System.err.println("started");

		uiLauncher=new UILauncher();
		Activator.context.registerService(IUILauncher.class.getName(), uiLauncher, null);
	}
	
	@SuppressWarnings("deprecation")
	public void stop(BundleContext context) throws Exception {
		thread.stop();
		pluginBase = null;
	}
	
	private static void getServices() {
		if (installer == null || deinstaller == null || configurator == null || information==null) {
			ServiceReference sr = context.getServiceReference(IInstaller.class
					.getName());
			if (sr != null)
				Activator.installer = (IInstaller) context.getService(sr);
			
			sr = context.getServiceReference(IDeinstaller.class.getName());
			if (sr != null)
				Activator.deinstaller = (IDeinstaller) context.getService(sr);
			
			sr = context.getServiceReference(IModel.class.getName());
			if (sr != null)
				Activator.model = (IModel) context.getService(sr);
			
			sr = context.getServiceReference(IInformation.class.getName());
			if (sr != null)
				Activator.information = (IInformation) context.getService(sr);
			sr = context.getServiceReference(IConfigurator.class.getName());
			if (sr != null)
				Activator.configurator = (IConfigurator) context.getService(sr);
		}
	}
	
	public static boolean loadPlugins(){
		if (Activator.getInformation() == null) {
			System.err.println("Could not load plugins! Information-Service not found!");
			return false;
		}
		
		File confHome = new File(new BundleConfigHome("ucc").getAbsolutePath());
        String separator = System.getProperty("file.separator");    
        File pluginFolder = new File(confHome.getPath()+separator+"plugins");
		if(pluginFolder.exists()){
		
			String[] pluginlist=pluginFolder.list();
			for(int i=0;i<pluginlist.length;i++){
				if(pluginlist[i].endsWith(".jar")){
					Bundle temp;
					try {
						temp = Activator.context.installBundle("file:"+pluginFolder.getAbsolutePath()+File.separator+pluginlist[i]);
						if(temp==null){
							System.err.println("Error loading plugin "+pluginlist[i]);
							return false;
						}else{
							System.out.println(pluginlist[i]+" successfully loaded");
							startPlugin(temp);
						}
					} catch (BundleException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
		}
		return true;
	}
	private static boolean startPlugin(Bundle b){
		try {
			b.start();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		switch (b.getState()) {
			case Bundle.ACTIVE:
				System.out.println("Bundle successfully started!");
				return true;
			case Bundle.INSTALLED:
				System.err.println("Bundle failed to start!");
				return false;
			case Bundle.RESOLVED:
				System.err.println("Bundle failed to start!");
				return false;
			}
		return false;
	}
}