/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.bus_member;

import javax.swing.JPanel;

import org.universAAL.middleware.bus.member.BusMember;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.tracker.IBusMemberRegistry;
import org.universAAL.middleware.tracker.IBusMemberRegistry.BusType;
import org.universAAL.middleware.tracker.IBusMemberRegistryListener;
import org.universAAL.tools.logmonitor.Activator;
import org.universAAL.tools.logmonitor.LogListenerEx;
import org.universAAL.tools.logmonitor.bus_member.gui.BusMemberGui;

/**
 * 
 * @author Carsten Stockloew
 *
 */
public class LogMonitor implements LogListenerEx, IBusMemberRegistryListener {

    private BusMemberGui gui = new BusMemberGui();
    private SpaceListener spaceListener = null;

    public LogMonitor() {
	// register this BusMemberRegistryListener
	IBusMemberRegistry registry = (IBusMemberRegistry) Activator.mc
		.getContainer().fetchSharedObject(Activator.mc,
			IBusMemberRegistry.busRegistryShareParams);
	registry.addListener(this, true);

	// start space listener
	spaceListener = new SpaceListener(gui);
	spaceListener.start();
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

    public void busMemberAdded(BusMember member, BusType type) {
	System.out.println("  --  ADD: " + member.getURI());
	MemberData data = new MemberData(member.getURI(), type);
	gui.add(data);
    }

    public void busMemberRemoved(BusMember member, BusType type) {
	System.out.println("  --  REM: " + member.getURI());
	MemberData data = new MemberData(member.getURI(), type);
	gui.remove(data);
    }

    public void regParamsAdded(String busMemberID, Resource[] params) {
	// TODO
    }

    public void regParamsRemoved(String busMemberID, Resource[] params) {
	// TODO
    }

    public void stop() {
	// TODO: call this method
	spaceListener.stop();
	IBusMemberRegistry registry = (IBusMemberRegistry) Activator.mc
		.getContainer().fetchSharedObject(Activator.mc,
			IBusMemberRegistry.busRegistryShareParams);
	registry.removeListener(this);
    }
}
