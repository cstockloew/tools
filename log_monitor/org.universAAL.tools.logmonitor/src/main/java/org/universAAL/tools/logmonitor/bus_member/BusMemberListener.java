/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.bus_member;

import org.universAAL.middleware.bus.member.BusMember;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.tracker.IBusMemberRegistry;
import org.universAAL.middleware.tracker.IBusMemberRegistryListener;
import org.universAAL.middleware.tracker.IBusMemberRegistry.BusType;
import org.universAAL.tools.logmonitor.Activator;
import org.universAAL.tools.logmonitor.bus_member.gui.BusMemberGui;

/**
 * 
 * @author Carsten Stockloew
 * 
 */
public class BusMemberListener implements IBusMemberRegistryListener {
    private BusMemberGui gui;

    public BusMemberListener(BusMemberGui gui) {
	this.gui = gui;
    }

    public void start() {
	// register this BusMemberRegistryListener
	IBusMemberRegistry registry = (IBusMemberRegistry) Activator.mc
		.getContainer().fetchSharedObject(Activator.mc,
			IBusMemberRegistry.busRegistryShareParams);
	registry.addListener(this, true);
    }

    public void stop() {
	IBusMemberRegistry registry = (IBusMemberRegistry) Activator.mc
		.getContainer().fetchSharedObject(Activator.mc,
			IBusMemberRegistry.busRegistryShareParams);
	registry.removeListener(this);
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
}
