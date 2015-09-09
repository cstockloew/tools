package org.universAAL.tools.logmonitor.bus_member;

import java.util.Map;

import org.universAAL.middleware.container.SharedObjectListener;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.interfaces.aalspace.AALSpaceDescriptor;
import org.universAAL.middleware.interfaces.aalspace.AALSpaceStatus;
import org.universAAL.middleware.managers.api.AALSpaceListener;
import org.universAAL.middleware.managers.api.AALSpaceManager;
import org.universAAL.tools.logmonitor.Activator;
import org.universAAL.tools.logmonitor.bus_member.gui.BusMemberGui;

public class SpaceListener implements AALSpaceListener, Runnable,
	SharedObjectListener {

    private AALSpaceManager theAALSpaceManager = null;
    private BusMemberGui gui = null;

    public SpaceListener(BusMemberGui gui) {
	this.gui = gui;
    }

    public void start() {
	// get AAL Space Manager to register this listener
	Object o = Activator.mc.getContainer().fetchSharedObject(Activator.mc,
		new Object[] { AALSpaceManager.class.getName() });
	if (o instanceof AALSpaceManager) {
	    sharedObjectAdded(o, null);
	}
    }

    public void stop() {
	// remove me as AALSpaceListener
	synchronized (this) {
	    if (theAALSpaceManager != null) {
		theAALSpaceManager.removeAALSpaceListener(this);
	    }
	}
    }

    @Override
    public void aalSpaceJoined(AALSpaceDescriptor spaceDescriptor) {
	gui.setSpace(spaceDescriptor);
    }

    @Override
    public void aalSpaceLost(AALSpaceDescriptor spaceDescriptor) {
	gui.setSpace(spaceDescriptor);
    }

    @Override
    public void newPeerJoined(PeerCard peer) {
	gui.add(peer);
    }

    @Override
    public void peerLost(PeerCard peer) {
	gui.remove(peer);
    }

    @Override
    public void aalSpaceStatusChanged(AALSpaceStatus status) {
	// not needed
    }

    @Override
    public void run() {
	// TODO polling thread that looks for new spaces
    }

    @Override
    public void sharedObjectAdded(Object sharedObj, Object removeHook) {
	if (sharedObj instanceof AALSpaceManager) {
	    synchronized (this) {
		theAALSpaceManager = (AALSpaceManager) sharedObj;
		theAALSpaceManager.addAALSpaceListener(this);
		gui.setSpace(theAALSpaceManager.getAALSpaceDescriptor());

		Map<String, PeerCard> peers = theAALSpaceManager.getPeers();
		if (peers != null) {
		    for (PeerCard pc : peers.values()) {
			gui.add(pc);
		    }
		}
		gui.add(theAALSpaceManager.getMyPeerCard());
	    }
	}
    }

    @Override
    public void sharedObjectRemoved(Object removeHook) {
	if (removeHook instanceof AALSpaceManager) {
	    synchronized (this) {
		theAALSpaceManager = null;
	    }
	}
    }
}
