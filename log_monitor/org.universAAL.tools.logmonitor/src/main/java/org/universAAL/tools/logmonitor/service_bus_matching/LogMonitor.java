/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.service_bus_matching;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.universAAL.middleware.container.LogListener;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceBus;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.process.OutputBinding;
import org.universAAL.middleware.service.owls.process.ProcessEffect;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.tools.logmonitor.Activator;
import org.universAAL.tools.logmonitor.LogListenerEx;
import org.universAAL.tools.logmonitor.service_bus_matching.Matchmaking.SingleMatching;
import org.universAAL.tools.logmonitor.service_bus_matching.gui.Gui;

public class LogMonitor implements LogListenerEx {

    private Gui gui = new Gui();

    public static LogMonitor instance;

    // a service caller is needed to retrieve service profiles
    DefaultServiceCaller caller = new DefaultServiceCaller(Activator.mc);

    // list of Matchmaking
    private LinkedList<Matchmaking> matchmakings = new LinkedList<Matchmaking>();

    // maps ID of the thread (Long) to Matchmaking
    private Hashtable<Long, Matchmaking> threads = new Hashtable<Long, Matchmaking>();

    public class ProfileInfo {
	public ServiceProfile profile;
	public String serialized;

	public ProfileInfo(ServiceProfile profile) {
	    this.profile = profile;
	}
    }

    // maps URI of profile of service to ServiceProfile
    private Hashtable<String, ProfileInfo> profiles = new Hashtable<String, ProfileInfo>();

    public LogMonitor() {
	instance = this;
    }

    public Matchmaking getMatchmaking(int index) {
	return (Matchmaking) matchmakings.get(index);
    }

    /*-
     * @see org.universAAL.middleware.container.LogListener#log(int,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.Object[], java.lang.Throwable)
     * 
     * The general format that we assume:
     * 0:     a String containing something like "Mismatch detected: "
     * 1:     a reason (very short)
     * 2*n+2: information text      (for n=0 something like " error code: ")
     *        the text starts with "\n"
     * 2*n+3: information parameter (for n=0: integer with error ID)
     * l-3:   something like " detailed error message: "
     * l-2:   the detailed error message
     * l-1:   log ID
     * 
     * error code overview:
     * 0-999:     data representation
     * 1000-1999: service bus
     * 2000-2999: context bus
     * 
     * error code details:
     * 1000: ProcessResult "requested output not available" (none available)
     * 1001: ProcessResult "requested output not available"
     * 1010: ProcessResult "offered effect not requested" (none available)
     * 1011: ProcessResult "requested effect not offered" (none available)
     * 1012: ProcessResult "number of effects do not match"
     * 1013: ProcessResult "requested effect not offered"
     */
    public void log(int logLevel, String module, String pkg, String cls,
	    String method, Object[] msgPart, Throwable t) {
	if (logLevel == LogListener.LOG_LEVEL_TRACE) {
	    // System.out.println("--TRACE: " + module + " " + pkg + " " + cls
	    // + " " + method + " " + msgPart);

	    if (!"mw.bus.service.osgi".equals(module))
		return;

	    if ("ServiceStrategy".equals(cls))
		handleServiceStrategyMessage(msgPart);
	    else if ("ServiceRealization".equals(cls))
		handleServiceRealizationMessage(msgPart);
	    else if ("ProcessResult".equals(cls))
		handleProcessResultMessage(msgPart, method);
	}
    }

    private void handleProcessResultMessage(Object[] msgPart, String method) {
	Long id = (Long) msgPart[msgPart.length - 1];
	if (id == null)
	    return;

	if (ServiceBus.LOG_MATCHING_MISMATCH.equals(msgPart[0])) {
	    Matchmaking m = (Matchmaking) threads.get(id);
	    if (m == null) {
		System.out
			.println("ERROR in matching log tool: handleProcessResultMessage, id not available.");
		return;
	    }

	    if ("checkEffects".equals(method)) {
		SingleMatching single = (SingleMatching) m.matchings.getLast();
		single.processStandardMessage(msgPart);
		single.reason = SingleMatching.REASON_EFFECT;
		if (single.code == 1013)
		    single.effect = (Resource) msgPart[3];
	    } else if ("checkOutputBindings".equals(method)) {
		SingleMatching single = (SingleMatching) m.matchings.getLast();
		single.processStandardMessage(msgPart);
		single.reason = SingleMatching.REASON_OUTPUT;
		single.outputURI = (String) msgPart[3];
		single.output = m.request.getRequiredOutputs()[((Integer) msgPart[5])
			.intValue()];
	    } else {
		System.out
			.println("ERROR in matching log tool: handleProcessResultMessage, unknown method.");
	    }
	}
    }

    private void handleServiceRealizationMessage(Object[] msgPart) {
	Long id = (Long) msgPart[msgPart.length - 1];
	if (id == null)
	    return;

	if (ServiceBus.LOG_MATCHING_MISMATCH.equals(msgPart[0])) {
	    String restrictedProperty = (String) msgPart[2];
	    Matchmaking m = (Matchmaking) threads.get(id);
	    if (m == null) {
		System.out
			.println("ERROR in matching log tool: handleServiceRealizationMessage, but id not available.");
		return;
	    }
	    SingleMatching single = (SingleMatching) m.matchings.getLast();
	    single.reason = SingleMatching.REASON_INPUT;
	    single.restrictedProperty = restrictedProperty;
	    single.msgPart = msgPart;
	}
    }

    private void handleServiceStrategyMessage(Object[] msgPart) {
	Long id = (Long) msgPart[msgPart.length - 1];
	if (id == null)
	    return;

	if (ServiceBus.LOG_MATCHING_START.equals(msgPart[0])) {
	    // start matching a request
	    Resource request = (Resource) msgPart[1];
	    startMatching(id, request);
	} else if (ServiceBus.LOG_MATCHING_PROFILE.equals(msgPart[0])) {
	    // start matchmaking for one profile with the request
	    String profileServiceClassURI = (String) msgPart[1];
	    String profileServiceURI = (String) msgPart[2];
	    startMatching(id, profileServiceClassURI, profileServiceURI);
	} else if (ServiceBus.LOG_MATCHING_SUCCESS.equals(msgPart[0])) {
	    // matching for one profile with the request was successful
	    endMatching(id, true);
	} else if (ServiceBus.LOG_MATCHING_NOSUCCESS.equals(msgPart[0])) {
	    // matching for one profile with the request was not successful
	    endMatching(id, false);
	} else if (ServiceBus.LOG_MATCHING_END.equals(msgPart[0])) {
	    // matching done
	    endMatching(id, (Integer) msgPart[2]);
	}
    }

    private String getProfileURI(ServiceProfile profile) {
	return profile.getTheService().getURI();
    }

    private void startMatching(Long id, Resource request) {
	// start matching a request
	if (threads.containsKey(id)) {
	    System.out
		    .println("ERROR in matching log tool: id already available.");
	    return;
	}

	Matchmaking m = new Matchmaking();
	m.date = new Date();
	m.serializedRequest = Activator.serialize(request);
	m.request = (ServiceRequest) Activator.deserialize(m.serializedRequest);
	if (m.request == null)
	    return;
	m.serviceURI = m.request.getRequestedService().getType();
	matchmakings.add(m);
	threads.put(id, m);
    }

    private void startMatching(Long id, String profileServiceClassURI,
	    String profileServiceURI) {
	// start matchmaking for one profile with the request
	Matchmaking m = (Matchmaking) threads.get(id);
	if (m == null) {
	    System.out
		    .println("ERROR in matching log tool: start matching, but id not available.");
	    return;
	}

	// find the profile; if not available, query it
	if (!profiles.containsKey(profileServiceURI)) {
	    // this profile is new -> query all profiles
	    ServiceProfile[] allProfiles = caller
		    .getMatchingService(profileServiceClassURI);
	    for (int i = 0; i < allProfiles.length; i++)
		profiles.put(getProfileURI(allProfiles[i]), new ProfileInfo(
			allProfiles[i]));
	    if (!profiles.containsKey(profileServiceURI)) {
		System.out
			.println("ERROR in matching log tool: matching with a profile that does not exist.");
		return;
	    }
	}

	// add this matching to the list of matchings for a request
	SingleMatching single = m.new SingleMatching();
	single.profileURI = profileServiceURI;
	m.matchings.add(single);
    }

    private void endMatching(Long id, boolean success) {
	Matchmaking m = (Matchmaking) threads.get(id);
	SingleMatching single = (SingleMatching) m.matchings.getLast();
	single.success = Boolean.valueOf(success);
    }

    private void endMatching(Long id, Integer numMatches) {
	Matchmaking m = (Matchmaking) threads.get(id);
	if (m == null) {
	    System.out
		    .println("ERROR in matching log tool: endMatching, id not available.");
	    return;
	}

	m.numMatches = numMatches.intValue();
	if (m.numMatches != 0)
	    m.success = Boolean.TRUE;
	else
	    m.success = Boolean.FALSE;

	// print(m);
	threads.remove(id);

	gui.notify(m);
    }

    public ProfileInfo getProfile(String uri) {
	return (ProfileInfo) profiles.get(uri);
    }

    private Resource findInGraph(Resource root, String uri, HashMap visited) {
	System.out.println("findInGraph: " + root + "  " + uri);
	if (uri == null || root == null)
	    return null;
	if (uri.equals(root.getURI()))
	    return root;
	if (visited == null)
	    visited = new HashMap();

	Resource el = (Resource) visited.get(root.getURI());
	if (el != null)
	    return null;
	visited.put(root.getURI(), root);

	Enumeration e = root.getPropertyURIs();
	while (e.hasMoreElements()) {
	    String key = (String) e.nextElement();
	    Object val = root.getProperty(key);

	    if (val instanceof Resource) {
		Resource retval = findInGraph((Resource) val, uri, visited);
		if (retval != null)
		    return retval;
	    } else if (val instanceof List) {
		Iterator iter = ((List) val).iterator();
		while (iter.hasNext()) {
		    Object o = iter.next();
		    if (o instanceof Resource) {
			Resource retval = findInGraph((Resource) o, uri,
				visited);
			if (retval != null)
			    return retval;
		    }
		}
	    }
	}

	return null;
    }

    // -------------------------------------
    private void print(Matchmaking m) {
	System.out.println("Matchmaking for service " + m.serviceURI + ": "
		+ m.success);
	LinkedList l = m.matchings;
	for (Iterator it = l.iterator(); it.hasNext();) {
	    SingleMatching s = (SingleMatching) it.next();
	    System.out.println("   matching with offer " + s.profileURI);
	    System.out.print("      matching ");
	    if (s.success.booleanValue())
		System.out.println("successful");
	    else {
		System.out.println("NOT successful");

		switch (s.reason) {
		case SingleMatching.REASON_INPUT:
		    System.out
			    .println("      reason (input): input parameters do not match for property "
				    + s.restrictedProperty);
		    break;
		case SingleMatching.REASON_OUTPUT:
		    System.out.println("      reason (output): "
			    + getMessage(s.msgPart));
		    break;
		case SingleMatching.REASON_EFFECT:
		    System.out.println("      reason (effect): "
			    + getMessage(s.msgPart));
		    break;
		default:
		    System.out.println("      reason: unknown");
		}
	    }
	}
    }

    public static String getMessage(Object[] msgPart) {
	StringBuffer sb = new StringBuffer(256);
	if (msgPart != null)
	    for (int i = 0; i < msgPart.length - 1; i++)
		sb.append(msgPart[i]);
	return sb.toString();
    }

    // -------------------------------------

    // get service and instance level restrictions
    private String getServiceString(Service srv, boolean shortForm) {
	String s = "";

	s += URI.get(srv.getType(), shortForm) + "\n";
	if (srv != null) {
	    String[] props = srv.getRestrictedPropsOnInstanceLevel();
	    if (props.length != 0) {
		s += "  instance level restrictions:\n";
		for (int i = 0; i < props.length; i++) {
		    MergedRestriction instRestr = srv
			    .getInstanceLevelRestrictionOnProp(props[i]);
		    s += CEStringUtil.toString("    ", instRestr, shortForm);
		}
	    }
	}

	return s;
    }

    private String getEffectsString(Resource[] effects, boolean shortForm) {
	String s = "";

	if (effects.length != 0) {
	    s += "  effect:\n";
	    for (int i = 0; i < effects.length; i++) {
		Resource r = effects[i];
		PropertyPath path = (PropertyPath) r
			.getProperty(ProcessEffect.PROP_PROCESS_AFFECTED_PROPERTY);
		Object o = r
			.getProperty(ProcessEffect.PROP_PROCESS_PROPERTY_VALUE);

		if (ProcessEffect.TYPE_PROCESS_CHANGE_EFFECT
			.equals(r.getType())) {
		    s += "    change effect:\n";
		    s += "      on path: " + getPP(path, shortForm) + "\n";
		    s += "      for value: " + o.toString() + "\n";
		} else if (ProcessEffect.TYPE_PROCESS_ADD_EFFECT.equals(r
			.getType())) {
		    s += "    add effect:\n";
		    s += "      on path: " + getPP(path, shortForm) + "\n";
		    s += "      for value: " + o.toString() + "\n";
		} else if (ProcessEffect.TYPE_PROCESS_REMOVE_EFFECT.equals(r
			.getType())) {
		    s += "    remove effect:\n";
		    s += "      on path: " + getPP(path, shortForm) + "\n";
		} else {
		    s += "    unknown: "
			    + r.toStringRecursive("    ", false, null);
		}
	    }
	}

	return s;
    }

    private String getSingleIOString(Resource r, boolean shortForm) {
	String s = "";

	if (OutputBinding.TYPE_OWLS_OUTPUT_BINDING.equals(r.getType())) {
	    PropertyPath path = null;
	    Object o = r
		    .getProperty(OutputBinding.PROP_OWLS_BINDING_VALUE_FORM);
	    if (o instanceof PropertyPath)
		// SimpleBinding
		path = (PropertyPath) o;
	    if (path != null) {
		// the output info comes from
		// ServiceRequest.addRequiredOutput
		s += "    property path: " + getPP(path, shortForm) + "\n";
	    } else {
		s += "    unknown output binding: " + r.getURI() + "\n";
	    }
	} else {
	    s += "    unknown: " + r.toStringRecursive("    ", false, null);
	}

	return s;
    }

    private String getProfileString(ServiceProfile profile, boolean shortForm) {
	Iterator it;
	String s = "Service profile ";

	// get service info
	s += getServiceString(profile.getTheService(), shortForm);

	// get input info
	it = profile.getInputs();
	if (it.hasNext()) {
	    s += "  input:\n";
	    while (it.hasNext())
		s += getSingleIOString((Resource) it.next(), shortForm);
	}

	// get effect info
	s += getEffectsString(profile.getEffects(), shortForm);

	// get output info
	it = profile.getOutputs();
	if (it.hasNext()) {
	    s += "  output:\n";
	    while (it.hasNext())
		s += getSingleIOString((Resource) it.next(), shortForm);
	}

	return s;
    }

    private String getRequestString(ServiceRequest request, boolean shortForm) {
	String s = "Requesting Service ";

	// get service info
	s += getServiceString(request.getRequestedService(), shortForm);

	// get effect info
	s += getEffectsString(request.getRequiredEffects(), shortForm);

	// get output info
	Resource[] output = request.getRequiredOutputs();
	if (output.length != 0) {
	    s += "  output:\n";
	    for (int i = 0; i < output.length; i++)
		s += getSingleIOString(output[i], shortForm);
	}

	return s;
    }

    private String getPP(PropertyPath path, boolean shortForm) {
	String s = "";
	if (path == null)
	    return s;
	String[] p = path.getThePath();
	for (int i = 0; i < p.length; i++) {
	    s += URI.get(p[i], shortForm);
	    if (i < p.length - 1)
		s += ", ";
	}
	return s;
    }

    public JPanel getPanel() {
	return gui;
    }

    public String getTitle() {
	return "Service Matchmaking";
    }
}
