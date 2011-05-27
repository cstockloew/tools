/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
*/
package org.universAAL.tools.logmonitor;

import java.util.LinkedList;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.util.LogListener;

/**
 * Implementation of the {@link org.universAAL.middleware.util.LogListener}
 * interface to be called from {@link org.universAAL.middleware.util.LogUtils}
 * in the bundle mw.data.representation.
 * 
 * @author cstockloew
 *
 */
public class LogMonitor implements LogListener {
	
	/**
	 * The main frame.
	 */
	private RDFVis vis = new RDFVis();

	/**
	 * @see org.universAAL.middleware.util.LogListener
	 */
	public void logDebug(String cls, String method, Object[] msgPart,
			Throwable t) {
		
		// TODO: put this in a separate thread?
		String msg = "";
		LinkedList<Resource> lst = new LinkedList<Resource>();
		for (int i=0; i<msgPart.length; i++) {
			if (i>0)
				msg += " ";
			Object o = msgPart[i];
			msg += o;
			if (o instanceof Resource)
				lst.add((Resource)o);
		}
		for (Resource r : lst)
			vis.addMessage(cls, method, msg, r);
	}
}
