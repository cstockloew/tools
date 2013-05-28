package org.universaal.tools.packaging.tool;

import java.io.File;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.universaal.tools.packaging.tool"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	public static String tempDir;  

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;

		SecureRandom random = new SecureRandom();			
		tempDir = System.getProperty("java.io.tmpdir")+"MPA_"+new BigInteger(130, random).toString(32);
		new File(tempDir).mkdirs();
		
		System.out.println("*** [Application Packager] - The log file is available at "+tempDir+" ***");
		System.setOut(new PrintStream(new File(tempDir+"/log.txt")));
		System.setErr(new PrintStream(new File(tempDir+"/errlog.txt")));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}