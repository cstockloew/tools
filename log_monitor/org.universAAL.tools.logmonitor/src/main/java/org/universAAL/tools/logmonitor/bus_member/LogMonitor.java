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

public class LogMonitor implements LogListenerEx, IBusMemberRegistryListener {

    private BusMemberGui gui = new BusMemberGui();

    public LogMonitor() {
	IBusMemberRegistry registry = (IBusMemberRegistry) Activator.mc
		.getContainer().fetchSharedObject(Activator.mc,
			IBusMemberRegistry.busRegistryShareParams);
	registry.addListener(this, true);
    }

    /*-
     * @see org.universAAL.middleware.container.LogListener#log(int,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.Object[], java.lang.Throwable)
     */
    public void log(int logLevel, String module, String pkg, String cls,
	    String method, Object[] msgPart, Throwable t) {
	// if (logLevel == LogListener.LOG_LEVEL_DEBUG) {
	// System.out.println(module);
	// // if (!"mw.bus.service.osgi".equals(module))
	// // return;
	// String pkg2 = AbstractBus.class.getPackage().getName();
	// String cls2 = AbstractBus.class.getName().substring(
	// pkg.length() + 1);
	// if (!cls2.equals(cls))
	// return;
	// if (!pkg2.equals(pkg))
	// return;
	//
	// if (msgPart != null && msgPart.length == 2
	// && msgPart[1] instanceof String) {
	// String id = (String) msgPart[1];
	// if ("register".equals(method))
	// handleRegister(id);
	// if ("unregister".equals(method))
	// handleUnregister(id);
	// }
	// }
    }

    public JPanel getPanel() {
	return gui;
    }

    public String getTitle() {
	return "Bus Member";
    }

    public void busMemberAdded(BusMember member, BusType type) {
	MemberData data = new MemberData(member.getURI(), type);
	gui.add(data);
    }

    public void busMemberRemoved(BusMember member, BusType type) {
	// TODO
    }

    public void regParamsAdded(String busMemberID, Resource[] params) {
    }

    public void regParamsRemoved(String busMemberID, Resource[] params) {
    }
}
