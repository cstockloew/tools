/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor;

import org.universAAL.middleware.container.LogListener;

/**
 * Implementation of the {@link org.universAAL.middleware.util.LogListener}
 * interface to be called from
 * {@link org.universAAL.middleware.container.utils.LogUtils}
 * 
 * @author cstockloew
 * 
 */
public class LogMonitor implements LogListener {

    LogListenerEx listeners[] = new LogListenerEx[2];
    MainGui gui = new MainGui();

    LogMonitor() {
	listeners[0] = new org.universAAL.tools.logmonitor.all_log.LogMonitor();
	listeners[1] = new org.universAAL.tools.logmonitor.rdfvis.LogMonitor();

	for (int i = 0; i < listeners.length; i++)
	    gui.addPanel(listeners[i].getTitle(), listeners[i].getPanel());
    }

    public void log(int logLevel, String module, String pkg, String cls,
	    String method, Object[] msgPart, Throwable t) {
	for (int i = 0; i < listeners.length; i++)
	    listeners[i].log(logLevel, module, pkg, cls, method, msgPart, t);
    }
}
