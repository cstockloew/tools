package org.universAAL.tools.logmonitor.bus_member;

import org.universAAL.middleware.bus.member.BusMemberType;
import org.universAAL.middleware.bus.model.AbstractBus;
import org.universAAL.middleware.tracker.IBusMemberRegistry.BusType;

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
    public String type;
    public BusType busType;
    public int number;

    public MemberData(String id, BusType busType) {
	this.id = id;
	this.busType = busType;

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
	//System.out.println("--s: " + s);
	for (BusMemberType t : BusMemberType.values()) {
	    if (s.endsWith(t.name())) {
		type = t.name();
		s = s.substring(0, s.length()-type.length());
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
	System.out.println("  bus:    " + busType);
	System.out.println("  number: " + number);
    }
}