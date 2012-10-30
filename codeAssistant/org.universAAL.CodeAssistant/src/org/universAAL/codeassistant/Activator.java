package org.universAAL.codeassistant;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.universAAL.codeassistant"; 

	// The shared instance
	private static Activator plugin;
	
	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		Bundle[] b = context.getBundles();
		for (int i=0; i<b.length; i++){
			BundleContext bc = b[i].getBundleContext();
			//System.out.println(b[i].getSymbolicName());
			long id = b[i].getBundleId();
			//System.out.println("---");
			//System.out.println(id);
			
		}
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
