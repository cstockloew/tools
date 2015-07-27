package org.universAAL.tools.logmonitor.bus_member;

import org.universAAL.middleware.container.SharedObjectListener;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.interfaces.aalspace.AALSpaceDescriptor;
import org.universAAL.middleware.interfaces.aalspace.AALSpaceStatus;
import org.universAAL.middleware.managers.api.AALSpaceListener;
import org.universAAL.middleware.managers.api.AALSpaceManager;
import org.universAAL.tools.logmonitor.Activator;

public class SpaceListener implements AALSpaceListener, Runnable,
	SharedObjectListener {

    private AALSpaceManager theAALSpaceManager = null;

    public SpaceListener() {
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
	// TODO Auto-generated method stub

    }

    @Override
    public void aalSpaceLost(AALSpaceDescriptor spaceDescriptor) {
	// TODO Auto-generated method stub

    }

    @Override
    public void newPeerJoined(PeerCard peer) {
	// TODO Auto-generated method stub

    }

    @Override
    public void peerLost(PeerCard peer) {
	// TODO Auto-generated method stub

    }

    @Override
    public void aalSpaceStatusChanged(AALSpaceStatus status) {
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
