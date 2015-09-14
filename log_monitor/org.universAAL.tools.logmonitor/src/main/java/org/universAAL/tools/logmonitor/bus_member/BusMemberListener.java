/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.bus_member;

import org.universAAL.middleware.bus.member.BusMemberType;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.managers.distributedmw.api.DistributedBusMemberListener;
import org.universAAL.middleware.managers.distributedmw.api.DistributedBusMemberListenerManager;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.tools.logmonitor.Activator;
import org.universAAL.tools.logmonitor.bus_member.gui.BusMemberGui;

/**
 * 
 * @author Carsten Stockloew
 * 
 */
public class BusMemberListener implements DistributedBusMemberListener {
    private BusMemberGui gui;

    public BusMemberListener(BusMemberGui gui) {
	this.gui = gui;
    }

    public void start() {
	// register this BusMemberRegistryListener
	DistributedBusMemberListenerManager registry = (DistributedBusMemberListenerManager) Activator.mc
		.getContainer()
		.fetchSharedObject(
			Activator.mc,
			new Object[] { DistributedBusMemberListenerManager.class
				.getName() });
	registry.addListener(this, null);
    }

    public void stop() {
	DistributedBusMemberListenerManager registry = (DistributedBusMemberListenerManager) Activator.mc
		.getContainer()
		.fetchSharedObject(
			Activator.mc,
			new Object[] { DistributedBusMemberListenerManager.class
				.getName() });
	registry.removeListener(this, null);
    }

    @Override
    public void busMemberAdded(PeerCard origin, String busMemberID,
	    String busName, BusMemberType memberType, String label,
	    String comment) {
	System.out.println("  --  ADD: " + busMemberID);
	MemberData data = new MemberData(origin, busMemberID, busName,
		memberType, label, comment);
	gui.add(data);
    }

    @Override
    public void busMemberRemoved(PeerCard origin, String busMemberID) {
	System.out.println("  --  REM: " + busMemberID);
	gui.remove(busMemberID);
    }

    @Override
    public void regParamsAdded(PeerCard origin, String busMemberID,
	    Resource[] params) {
	// TODO Auto-generated method stub
    }

    @Override
    public void regParamsRemoved(PeerCard origin, String busMemberID,
	    Resource[] params) {
	// TODO Auto-generated method stub
    }
}
