package org.universAAL.tools.logmonitor.bus_member;

import java.util.HashMap;
import java.util.Map;

import org.universAAL.middleware.bus.member.BusMemberType;
import org.universAAL.middleware.bus.model.AbstractBus;
import org.universAAL.middleware.context.ContextBusFacade;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.service.ServiceBusFacade;
import org.universAAL.middleware.ui.UIBusFacade;
import org.universAAL.tools.logmonitor.Activator;

/**
 * 
 * @author Carsten Stockloew
 * 
 */
public class MemberData {
    public String id;
    public String space;
    public String peer;
    public String module;
    public String type; // BusMemberType
    public int number;
    public String busNameReadable;
    public PeerCard origin;
    public String busName;
    public String label;
    public String comment;

    private static Map<String, String> busNames = new HashMap<String, String>();

    static {
	AbstractBus bus;
	bus = (AbstractBus) ServiceBusFacade.fetchBus(Activator.mc);
	busNames.put(bus.getBrokerName(), "Service");
	bus = (AbstractBus) ContextBusFacade.fetchBus(Activator.mc);
	busNames.put(bus.getBrokerName(), "Context");
	bus = (AbstractBus) UIBusFacade.fetchBus(Activator.mc);
	busNames.put(bus.getBrokerName(), "UI");
    }

    public MemberData(PeerCard origin, String busMemberID, String busName,
	    BusMemberType memberType, String label, String comment) {
	this.id = busMemberID;
	this.origin = origin;
	this.busName = busName;
	this.label = label;
	this.comment = comment;
	busNameReadable = busNames.get(busName);
	extractInfoFromID(busMemberID);
    }

    private void extractInfoFromID(String id) {
	String s = id;
	int idx = id.lastIndexOf('#');
	s = id.substring(0, idx);
	if (s.startsWith(AbstractBus.uAAL_OPTIONAL_URI_PREFIX))
	    s = s.substring(AbstractBus.uAAL_OPTIONAL_URI_PREFIX.length());
	int idx2 = s.lastIndexOf('/');
	space = s.substring(0, idx2);
	peer = s.substring(idx2 + 1);

	s = id.substring(idx);
	idx2 = s.indexOf('.');
	module = s.substring(idx2 + 1);

	s = s.substring(1, idx2);
	// System.out.println("--s: " + s);
	for (BusMemberType t : BusMemberType.values()) {
	    if (s.endsWith(t.name())) {
		type = t.name();
		s = s.substring(0, s.length() - type.length());
		number = Integer.parseInt(s);
		break;
	    }
	}
    }

    public void print() {
	System.out.println("---- MemberData");
	System.out.println("  ID:     " + id);
	System.out.println("  space:  " + space);
	System.out.println("  peer:   " + peer);
	System.out.println("  module: " + module);
	System.out.println("  type:   " + type);
	System.out.println("  bus:    " + busNameReadable);
	System.out.println("  number: " + number);
    }
}