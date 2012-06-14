/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
 */

package org.universAAL.tools.logmonitor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.LogListener;


/**
 * The bundle activator to create the log monitor and register this OSGi
 * service.
 * 
 * @author cstockloew
 * 
 */
public class Activator implements BundleActivator {

    /**
     * Start this bundle. This is not done in a separate thread so that we can
     * get all messages even from the beginning.
     */
    public void start(BundleContext context) throws Exception {
	LogMonitor lm = new LogMonitor();

	context.registerService(new String[] { LogListener.class.getName() },
		lm, null);
    }

    public void stop(BundleContext arg0) throws Exception {
    }
    
    public static void main(String[] args) {
	LogMonitor lm = new LogMonitor();
	lm.log(0, "module", "pkg", "cls", "method", new Object[] {""}, null);
    }
}
