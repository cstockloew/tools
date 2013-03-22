package org.universaal.tools.codeassistantapplication;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.universaal.tools.codeAssistant"; 

	// The shared instance
	private static Activator plugin;
	private static BundleContext bc;
	
	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.bc = context;
		Bundle[] b = context.getBundles();
		for (int i=0; i<b.length; i++){
			BundleContext bc = b[i].getBundleContext();
			//long id = b[i].getBundleId();
			//String sname = b[i].getSymbolicName();
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

	public static BundleContext getBc() {
		return bc;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
