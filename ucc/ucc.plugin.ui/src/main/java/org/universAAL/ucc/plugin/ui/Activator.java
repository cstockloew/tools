package org.universAAL.ucc.plugin.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.ucc.api.core.IInformation;
import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.plugin.ui.jambi.Renderer;

import com.trolltech.qt.QThread;

public class Activator implements BundleActivator {
	public static BundleContext context = null;
	private static int max_retry = 10;

	static UCCPlugin uCCPlugin = null;
	static IPluginBase uCCPluginBase = null;
	static IInformation information = null;

	static final String libraryNames[] = { "com_trolltech_research_qtjambiawtbridge_generated.dll" };

	static {
		for (int i = 0; i < libraryNames.length; i++)
			extractDll("/bin/", libraryNames[i]);
	}

	private static void extractDll(String path, String name) {
		try {
			InputStream inputStream = Activator.class.getClassLoader()
					.getResource(path + name).openStream();
			File libraryFile = new File(name);
			// libraryFile.deleteOnExit();
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
	
	private Renderer render;
	public BundleConfigHome home = null;

	public static IInformation getInformation() {
		return Activator.information;
	}

	public void start(BundleContext lcontext) throws Exception {
		Activator.context = lcontext;
		Activator.uCCPluginBase = (IPluginBase) this.getServiceObject(context,
				IPluginBase.class.getName());
		Activator.information = (IInformation) this.getServiceObject(context,
				IInformation.class.getName());
		
		home = new BundleConfigHome(Activator.context.getBundle().getSymbolicName());
        BundleContext[] bc = { Activator.context };
        Renderer.setModuleContext(uAALBundleContainer.THE_CONTAINER
                .registerModule(bc));
        Renderer.setHome(home.getAbsolutePath());

		new QThread(new Runnable() {
			public void run() {
				 
				render = Renderer.getInstance();
			        
				if (Activator.uCCPluginBase != null && information != null) {
					Activator.uCCPlugin = new UCCPlugin(Activator.uCCPluginBase);
					Activator.uCCPluginBase.registerPlugin(Activator.uCCPlugin);
					// Activator.uCCPlugin.createGridViewItem();
					System.out.println("Plugin started!");
				} else {
					if (Activator.uCCPluginBase == null)
						System.err
								.println("Can not start information-view: Do not found Plugin-Base!");
					if (Activator.information == null)
						System.err
								.println("Can not start information-view: Do not found Information Service!");
				}
			}
		}).start();
	}

	public void stop(BundleContext arg0) throws Exception {
	}

	private Object getServiceObject(BundleContext context, String name)
			throws Exception {
		ServiceReference sr = null;
		int retry_count = 0;
		while (sr == null && retry_count < max_retry) {
			sr = context.getServiceReference(name);
			if (sr == null)
				Thread.sleep(1000);
			retry_count++;
		}
		if (sr != null)
			return context.getService(sr);
		return null;
	}

}
