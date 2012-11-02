/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
 */

package org.universAAL.tools.logmonitor;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.LogListener;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;

/**
 * The bundle activator to create the log monitor and register this OSGi
 * service.
 * 
 * @author cstockloew
 * 
 */
public class Activator implements BundleActivator {

    public static ModuleContext mc;
    public static BundleContext bc;
    public static MessageContentSerializer contentSerializer = null;

    /**
     * Start this bundle. This is not done in a separate thread so that we can
     * get all messages even from the beginning.
     */
    public void start(BundleContext context) throws Exception {
	bc = context;
	mc = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { context });

	LogMonitor lm = new LogMonitor();

	context.registerService(new String[] { LogListener.class.getName() },
		lm, null);
    }

    public void stop(BundleContext arg0) throws Exception {
    }

    public static void main(String[] args) {
	LogMonitor lm = new LogMonitor();
	lm.log(0, "module", "pkg", "cls", "method", new Object[] { "" }, null);
    }

    /**
     * Get resource
     */
    public static URL getResource(String name) {
	URL r = null;
	if (Activator.bc != null) {
	    Bundle b = null;
	    try {
		b = Activator.bc.getBundle();
	    } catch (RuntimeException e) {
		e.printStackTrace();
	    }
	    if (b.getEntry(name) != null) {
		String path = b.getEntry(name).getPath();
		r = b.getResource(path);
	    }
	}
	return r;
    }

    public static String serialize(Resource r) {
	if (contentSerializer == null) {
	    contentSerializer = (MessageContentSerializer) mc.getContainer()
		    .fetchSharedObject(
			    mc,
			    new Object[] { MessageContentSerializer.class
				    .getName() });
	    if (contentSerializer == null)
		return "- not possible to get the serializer -";
	}

	return contentSerializer.serialize(r);
    }

    public static Resource deserialize(String s) {
	if (contentSerializer == null) {
	    contentSerializer = (MessageContentSerializer) mc.getContainer()
		    .fetchSharedObject(
			    mc,
			    new Object[] { MessageContentSerializer.class
				    .getName() });
	    if (contentSerializer == null)
		return null;
	}

	return (Resource) contentSerializer.deserialize(s);
    }
}
