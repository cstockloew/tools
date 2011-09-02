package helloworldplugin;

import model.xml.AnnotationsExtractor;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import utility.Constants;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "CE";

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
//	 
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("start plugin!");
		super.start(context);
		plugin = this;
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
//		System.out.println("AUS!");
//		AnnotationsExtractor ae= AnnotationsExtractor.getInstance();
//		ae.removeHistory();
//		ae=null;
//		System.out.println("ae=="+ae);
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
	
	
	
//	protected void initializeImageRegistry(ImageRegistry registry) {
//          super.initializeImageRegistry(registry);
//          Bundle bundle = Activator.getDefault().getBundle();
//
//          ImageDescriptor myImage = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(Constants.DELETE_ICON),null));
//          registry.put(Constants.DELETE_ICON, myImage);
//    }
	

}
