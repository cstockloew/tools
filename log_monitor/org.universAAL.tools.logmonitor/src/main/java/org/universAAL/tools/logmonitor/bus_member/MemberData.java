package org.universAAL.tools.logmonitor.bus_member;

import org.universAAL.middleware.bus.member.BusMemberType;
import org.universAAL.middleware.bus.model.AbstractBus;

public class MemberData {
    public String id;
    public String space;
    public String peer;
    public String module;
    public String type;

    public MemberData(String id) {
	this.id = id;
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
	System.out.println("--s: " + s);
	for (BusMemberType t : BusMemberType.values()) {
	    if (s.endsWith(t.name()))
		type = t.name();
	}
    }

    public void print() {
	System.out.println("---- MemberData");
	System.out.println("  ID:     " + id);
	System.out.println("  space:  " + space);
	System.out.println("  peer:   " + peer);
	System.out.println("  module: " + module);
	System.out.println("  type:   " + type);
    }
}