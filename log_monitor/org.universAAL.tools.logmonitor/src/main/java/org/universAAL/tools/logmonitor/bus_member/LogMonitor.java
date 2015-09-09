/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.bus_member;

import javax.swing.JPanel;

import org.universAAL.tools.logmonitor.LogListenerEx;
import org.universAAL.tools.logmonitor.bus_member.gui.BusMemberGui;

/**
 * 
 * @author Carsten Stockloew
 * 
 */
public class LogMonitor implements LogListenerEx {

    private BusMemberGui gui = new BusMemberGui();
    private SpaceListener spaceListener = null;
    private BusMemberListener busMemberListener = null;

    public LogMonitor() {
	// start space listener
	spaceListener = new SpaceListener(gui);
	spaceListener.start();

	// start bus member listener
	busMemberListener = new BusMemberListener(gui);
	busMemberListener.start();
    }

    // dummy method for integration in main gui, not used
    public void log(int logLevel, String module, String pkg, String cls,
	    String method, Object[] msgPart, Throwable t) {
    }

    public JPanel getPanel() {
	return gui;
    }

    public String getTitle() {
	return "Bus Member";
    }

    public void stop() {
	spaceListener.stop();
	busMemberListener.stop();
    }
}
