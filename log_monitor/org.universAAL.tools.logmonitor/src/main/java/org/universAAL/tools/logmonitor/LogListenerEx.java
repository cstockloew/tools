/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor;

import javax.swing.JPanel;

import org.universAAL.middleware.container.LogListener;

public interface LogListenerEx extends LogListener {

    public JPanel getPanel();

    public String getTitle();
    
    public void stop();
}
