/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.service_bus_matching.gui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultCaret;

import org.universAAL.middleware.owl.OntClassInfo;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.process.OutputBinding;
import org.universAAL.middleware.service.owls.process.ProcessEffect;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.tools.logmonitor.Activator;
import org.universAAL.tools.logmonitor.service_bus_matching.LogMonitor;
import org.universAAL.tools.logmonitor.service_bus_matching.Matchmaking;
import org.universAAL.tools.logmonitor.service_bus_matching.URI;
import org.universAAL.tools.logmonitor.service_bus_matching.LogMonitor.ProfileInfo;
import org.universAAL.tools.logmonitor.service_bus_matching.Matchmaking.SingleMatching;

public class MatchmakingPane extends JTextPane {

    private static final long serialVersionUID = 1L;

    private boolean all = false;

    /**
     * Defines how the overview will look like: 0 means that the results are
     * grouped according to the service provider, 1 means no grouping
     */
    public int overviewMethod = 0;

    private HashMap<String, String> urlReplacement = new HashMap<String, String>();

    // Storage visible objects; objects that are not visible, are not
    // contained in this table. The value is ignored, only the key is used.
    private Hashtable<String, String> visible = new Hashtable<String, String>();
    private Matchmaking currentMatch = null;

    // private Sparul sparul = new Sparul();

    private class HTMLListener implements HyperlinkListener {
	public void hyperlinkUpdate(HyperlinkEvent e) {
	    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		String link = e.getDescription();
		togglevisibility(link);

		// test: print content of system clipboard
		// Clipboard systemClipboard;
		// systemClipboard = Toolkit.getDefaultToolkit()
		// .getSystemClipboard();
		// printClipboardContent(systemClipboard);

		setText(createHTML(currentMatch));
	    }
	}
    }

    public MatchmakingPane() {
	setEditable(false);
	setContentType("text/html");
	addHyperlinkListener(new HTMLListener());
	setCaret(new DefaultCaret());
	((DefaultCaret) getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

	// overwrite ctrl-c
	final MatchmakingPane pane = this;
	getInputMap()
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_C,
			InputEvent.CTRL_DOWN_MASK), "uaal_copy");
	getActionMap().put(
		"uaal_copy",
		new ClipboardHandling(urlReplacement, getTransferHandler(),
			pane));
    }

    public void show(Matchmaking m) {
	currentMatch = m;
	all = false;
	visible.clear();
	setText(createHTML(m));
    }

    private boolean isVisible(String uri) {
	if (all)
	    return true;
	Object o = visible.get(uri);
	if (o == null)
	    return false;
	else
	    return true;
    }

    private void makeVisible(String uri) {
	visible.put(uri, uri);
    }

    private void makeInvisible(String uri) {
	visible.remove(uri);
    }

    private void togglevisibility(String uri) {
	if ("all".equals(uri)) {
	    if (all)
		visible.clear();
	    all = !all;
	} else if (visible.get(uri) != null)
	    makeInvisible(uri);
	else
	    makeVisible(uri);
    }

    private URL getURL(String filename) {
	return Activator.getResource(filename);
    }

    private String getImageHTML(String name) {
	String filename = "/img/" + name;
	URL url = getURL(filename);

	urlReplacement.put(url.toString(),
		"http://depot.universaal.org/images/LogMonitor/" + name);

	return "<img src=\"" + url.toString() + "\">";
    }

    private String getURIHTML(String uri) {
	int idx = uri.lastIndexOf('#');
	if (idx == -1)
	    return uri;

	String start = uri.substring(0, idx + 1);
	String end = uri.substring(idx + 1);
	return start + "<b>" + end + "</b>";
    }

    private String getOutputHTML(Resource output) {
	StringBuilder s = new StringBuilder("");

	Object form = output
		.getProperty(OutputBinding.PROP_OWLS_BINDING_VALUE_FORM);

	s.append("output:");
	s.append(getTableStartHTML());
	s.append(getTableRowHTML("parameter binding:",
		getURIHTML(((Resource) output
			.getProperty(OutputBinding.PROP_OWLS_BINDING_TO_PARAM))
			.getURI())));
	if (form == null)
	    s.append(getTableRowHTML("--",
		    "- unknown value, perhaps a unit conversion? -"));
	else
	    s.append(getTableRowHTML("at the property path:",
		    getPropPathHTML((PropertyPath) form, false)));
	s.append(getTableEndHTML());

	return s.toString();
    }

    private String getEffectHTML(Resource effect) {
	StringBuilder s = new StringBuilder("");

	String type = effect.getType();
	if (ProcessEffect.TYPE_PROCESS_ADD_EFFECT.equals(type)) {
	    s.append("Add effect:");
	    s.append(getTableStartHTML());
	    s.append(getTableRowHTML(
		    "add the value:",
		    effect.getProperty(
			    ProcessEffect.PROP_PROCESS_PROPERTY_VALUE)
			    .toString()));
	    s.append(getTableRowHTML(
		    "to the property path:",
		    getPropPathHTML(
			    (PropertyPath) effect
				    .getProperty(ProcessEffect.PROP_PROCESS_AFFECTED_PROPERTY),
			    false)));
	    s.append(getTableEndHTML());
	} else if (ProcessEffect.TYPE_PROCESS_CHANGE_EFFECT.equals(type)) {
	    s.append("Change effect:");
	    s.append(getTableStartHTML());
	    s.append(getTableRowHTML("change the value to:", effect
		    .getProperty(ProcessEffect.PROP_PROCESS_PROPERTY_VALUE)
		    .toString()));
	    s.append(getTableRowHTML(
		    "at property path:",
		    getPropPathHTML(
			    (PropertyPath) effect
				    .getProperty(ProcessEffect.PROP_PROCESS_AFFECTED_PROPERTY),
			    false)));
	    s.append(getTableEndHTML());
	} else if (ProcessEffect.TYPE_PROCESS_REMOVE_EFFECT.equals(type)) {
	    s.append("Remove effect:");
	    s.append(getTableStartHTML());
	    s.append(getTableRowHTML(
		    "at property path:",
		    getPropPathHTML(
			    (PropertyPath) effect
				    .getProperty(ProcessEffect.PROP_PROCESS_AFFECTED_PROPERTY),
			    false)));
	    s.append(getTableEndHTML());
	}
	return s.toString();
    }

    private String getTableStartHTML() {
	return getTableStartHTML(0);
    }

    private String getTableStartHTML(int border) {
	return "<table border=\"0\"><tr><td width=\"20\"></td><td>"
		+ "<table valign=\"top\" border=\"" + border + "\">\n";
    }

    private String getTableRowHTML(String val1) {
	return "<tr><td>" + val1 + "</td></tr>\n";
    }

    private String getTableRowHTML(String val1, int colspan) {
	return "<tr><td colspan=\"" + colspan + "\" bgcolor=\"#EEEEEE\">"
		+ val1 + "</td></tr>\n";
    }

    private String getTableRowHTML(String val1, String val2) {
	return "<tr><td>" + val1 + "</td><td>" + val2 + "</td></tr>\n";
    }

    private String getTableRowHTML(String val1, String val2, String val3) {
	return "<tr>\n  <td>" + val1 + "</td>\n  <td>" + val2 + "</td>\n  <td>"
		+ val3 + "</td>\n</tr>\n";
    }

    private String getTableRowTitleHTML(String val1, String val2, String val3) {
	return "<tr>\n  <td  bgcolor=\"#DDDDDD\">" + val1
		+ "</td>\n  <td  bgcolor=\"#DDDDDD\">" + val2
		+ "</td>\n  <td bgcolor=\"#DDDDDD\">" + val3 + "</td>\n</tr>\n";
    }

    private String getTableEndHTML() {
	return "</table></td></tr></table>\n";
    }

    private String getPropPathHTML(PropertyPath path, boolean shortForm) {
	String s = "";
	if (path == null)
	    return s;
	String[] p = path.getThePath();
	for (int i = 0; i < p.length; i++) {
	    s += getURIHTML(URI.get(p[i], shortForm));
	    if (i < p.length - 1)
		s += "<br>";
	}
	return s;
    }

    private String getLinkHTML(String link, String text) {
	return "<a href=\"" + link + "\">" + text + "</a>\n";
    }

    private String turtle2HTML(String serialized) {
	return serialized.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    private void getRestrictionsHTML(StringBuilder s, List<?> res) {
	s.append("<br>\n- TODO ;-) but there are restrictions..<br>\n");
    }

    private HashSet<String> getNamedSuperClasses(Resource r) {
	HashSet<String> classes = new HashSet<String>();

	String types[] = r.getTypes();
	for (int i = 0; i < types.length; i++) {
	    String type = types[i];
	    classes.add(type);

	    OntClassInfo info = OntologyManagement.getInstance()
		    .getOntClassInfo(type);
	    if (info != null) {
		String superTypes[] = info.getNamedSuperClasses(true, false);
		for (int j = 0; j < superTypes.length; j++)
		    classes.add(superTypes[j]);
	    }
	}

	return classes;
    }

    private void getProfileRequestCommonHTML(StringBuilder s,
	    Resource[] effects, Resource[] outputs) {
	// get common parts of service profile and service request as HTML
	int i;

	s.append("<b>Effects:</b>");
	if (effects.length == 0) {
	    s.append(" <i>no effects defined</i><br>\n");
	} else {
	    s.append("<br>\n");
	    for (i = 0; i < effects.length; i++)
		s.append(getEffectHTML(effects[i]));
	    s.append("<br>\n");
	}

	s.append("<b>Outputs:</b>");
	if (outputs.length == 0) {
	    s.append(" <i>no outputs defined</i><br>\n");
	} else {
	    s.append("<br>\n");
	    for (i = 0; i < outputs.length; i++)
		s.append(getOutputHTML(outputs[i]));
	    s.append("<br>\n");
	}
    }

    private void getServiceProfileHTML(StringBuilder s, ServiceProfile prof) {
	// get service profile in an abstract view
	s.append("<b>Service profile:</b> ");
	s.append(getURIHTML(prof.getTheService().getURI()));

	s.append("<br><b>Service ontology class hierarchy:</b> ");
	s.append(getTableStartHTML());
	HashSet<String> types = getNamedSuperClasses(prof.getTheService());
	for (Iterator<String> i = types.iterator(); i.hasNext();)
	    s.append(getTableRowHTML(i.next()));
	s.append(getTableEndHTML());
	s.append("<br>\n");

	s.append("<b>Inputs:</b>");
	Iterator<?> it = prof.getInputs();
	if (!it.hasNext()) {
	    s.append(" <i>no inputs defined</i><br>\n");
	} else {
	    s.append("<br>\n");
	    while (it.hasNext()) {
		ProcessInput in = (ProcessInput) it.next();
		s.append("input:");
		s.append(getTableStartHTML());
		s.append(getTableRowHTML("URI:", in.getURI()));
		String type = in.getParameterType();
		s.append(getTableRowHTML("type:", type));
		int minCard = in.getMinCardinality();
		int maxCard = in.getMaxCardinality();
		if (minCard == maxCard)
		    s.append(getTableRowHTML("exact cardinality:", "" + maxCard));
		else {
		    s.append(getTableRowHTML("min cardinality:", "" + minCard));
		    s.append(getTableRowHTML("max cardinality:", "" + maxCard));
		}
		s.append(getTableEndHTML());
	    }
	    s.append("<br>\n");
	}

	Resource[] effects = prof.getEffects();
	Resource[] outputs = prof.getOutputBindings();
	getProfileRequestCommonHTML(s, effects, outputs);
    }

    private void getServiceRequestHTML(StringBuilder s, Matchmaking m) {
	// get service request in an abstract view
	ServiceRequest req = m.request;

	s.append("<b>Requested service:</b> ");
	s.append(getURIHTML(m.serviceURI));

	s.append("<br><b>Filtering input:</b>");
	Object o = req.getRequestedService().getProperty(
		Service.PROP_INSTANCE_LEVEL_RESTRICTIONS);
	if (o instanceof List<?>) {
	    getRestrictionsHTML(s, (List<?>) o);
	} else
	    s.append(" <i>no filtering input defined</i><br>\n");

	Resource effects[] = req.getRequiredEffects();
	Resource outputs[] = req.getRequiredOutputs();
	getProfileRequestCommonHTML(s, effects, outputs);
    }

    private void getOverviewHTML(StringBuilder s, List group) {
	for (Iterator<SingleMatching> it = group.iterator(); it.hasNext();) {
	    SingleMatching single = it.next();
	    String val2 = "-";
	    switch (single.reason) {
	    case SingleMatching.REASON_INPUT:
		val2 = "<i>input</i>";
		break;
	    case SingleMatching.REASON_OUTPUT:
		val2 = "<i>output</i>";
		break;
	    case SingleMatching.REASON_EFFECT:
		val2 = "<i>effect</i>";
		break;
	    }
	    String val3 = getURIHTML(single.profileURI);
	    if (single.success.booleanValue())
		s.append(getTableRowHTML(getImageHTML("OK_16.png"), val2, val3));
	    else
		s.append(getTableRowHTML(getImageHTML("ERROR_16.png"), val2,
			val3));
	}
    }

    private String createHTML(Matchmaking m) {
	ProfileInfo info;
	LinkedList<SingleMatching> l;

	StringBuilder s = new StringBuilder(
		"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"><html><body>\n");

	s.append("<h1>Results of Matchmaking process</h1>\n");
	if (m.success.booleanValue())
	    s.append(getImageHTML("OK_32.png"));
	else
	    s.append(getImageHTML("ERROR_32.png"));
	s.append("Service request: <b>" + m.serviceURI + "</b><br>");

	// overview of matchings
	s.append("<br><br>Matching with registered service profiles:<br>");
	s.append(getTableStartHTML(1));
	s.append(getTableRowTitleHTML("Result", "Reason", "Service URI"));
	if (overviewMethod == 0) {
	    l = m.matchings;
	    HashMap<String, ArrayList<SingleMatching>> groups = new HashMap<String, ArrayList<SingleMatching>>();

	    // create the grouping
	    for (SingleMatching single : l) {
		info = LogMonitor.instance.getProfile(single.profileURI);
		String providerURI = "<unknown>";
		if (!(info == null) && !(info.profileProviderURI == null))
		    providerURI = info.profileProviderURI;

		ArrayList<SingleMatching> group = groups.get(providerURI);
		if (group == null) {
		    group = new ArrayList<SingleMatching>();
		    groups.put(providerURI, group);
		}
		group.add(single);
	    }

	    // get a sorted set of provider URIs
	    ArrayList<String> sortedProviderURIs = new ArrayList<String>(
		    groups.keySet());
	    Collections.sort(sortedProviderURIs);

	    // get the html output
	    for (String providerURI : sortedProviderURIs) {
		ArrayList<SingleMatching> group = groups.get(providerURI);
		Collections.sort(group, new Comparator<SingleMatching>() {
		    public int compare(SingleMatching o1, SingleMatching o2) {
			return o1.profileURI.compareTo(o2.profileURI);
		    }
		});

		s.append(getTableRowHTML("Provider: " + providerURI, 3));
		getOverviewHTML(s, group);
	    }
	} else {
	    l = m.matchings;
	    getOverviewHTML(s, l);
	}
	s.append(getTableEndHTML());

	// details
	s.append("<br>");
	s.append(getLinkHTML("all", isVisible("all") ? "hide all" : "show all"));
	s.append("<br><br>");

	// details for request
	s.append("Request: ");

	// details for request: serialized
	if (isVisible("requestSerialized")) {
	    s.append(getLinkHTML("requestSerialized", "hide serialized"));
	    s.append("<pre>\n" + turtle2HTML(m.serializedRequest)
		    + "\n</pre>\n");
	} else {
	    s.append(getLinkHTML("requestSerialized", "show serialized"));
	}

	// details for request: abstract
	if (isVisible("requestAbstract")) {
	    s.append(getLinkHTML("requestAbstract", "hide abstract<br><br>\n"));
	    getServiceRequestHTML(s, m);
	} else {
	    s.append(getLinkHTML("requestAbstract", "show abstract"));
	}

	// details for request: query
	// if (isVisible("requestQuery")) {
	// s.append(getLink("requestQuery", "hide query"));
	// s.append("<pre>\n" + sparul.getSparul(m.request) + "\n</pre>\n");
	// } else {
	// s.append(getLink("requestQuery", "show query (experimentel)"));
	// }

	// details for each matchmaking
	l = m.matchings;
	for (Iterator<SingleMatching> it = l.iterator(); it.hasNext();) {
	    SingleMatching single = it.next();
	    s.append("<hr><h2>Details for " + URI.get(single.profileURI, true)
		    + "</h2>\n\n");

	    if (single.success.booleanValue()) {
		s.append(getImageHTML("OK_16.png") + "&#160;&#160;");
		s.append("successful");

	    } else {
		s.append(getImageHTML("ERROR_16.png") + "&#160;&#160;");

		switch (single.reason) {
		case SingleMatching.REASON_INPUT:
		    String prop = single.restrictedProperty;
		    if (prop == null)
			s.append("   input: number of input parameters do not match");
		    else
			s.append("   input: input parameters do not match for property "
				+ getURIHTML(prop));
		    break;
		case SingleMatching.REASON_OUTPUT:
		    s.append("   output: ");
		    s.append(single.code);
		    s.append(" - ");
		    s.append(single.shortReason);
		    s.append("<br>\n<i>Details:</i> ");
		    s.append(single.detailedReason);
		    s.append("<br>\n");
		    if (single.output != null)
			s.append(getOutputHTML(single.output));
		    else
			System.out.println("ERROR: no output found");
		    break;
		case SingleMatching.REASON_EFFECT:
		    s.append("   effect: ");
		    s.append(single.code);
		    s.append(" - ");
		    s.append(single.shortReason);
		    s.append("<br>\n<i>Details:</i> ");
		    s.append(single.detailedReason);
		    s.append("<br>\n");
		    s.append("<br>\n");
		    if (single.effect != null)
			s.append(getEffectHTML(single.effect));
		    break;
		default:
		    s.append("reason unknown");
		}
	    }

	    s.append("<br>\n");

	    s.append("ServiceProfile: ");
	    info = LogMonitor.instance.getProfile(single.profileURI);
	    if (info == null || info.profile == null)
		s.append("- unknown -<br>\n");
	    else {
		ServiceProfile profile = info.profile;
		String link = "ServiceProfile_" + single.profileURI;
		String link_abstract = link + "_abstract";
		String link_serialized = link + "_serialized";

		// ServiceProfile serialized
		if (isVisible(link_serialized)) {
		    s.append(getLinkHTML(link_serialized, "hide serialized"));

		    if (info.serialized == null) // create on first use
			info.serialized = Activator.serialize(profile);
		    s.append("<pre>\n" + turtle2HTML(info.serialized)
			    + "\n</pre>\n");
		} else {
		    s.append(getLinkHTML(link_serialized, "show serialized"));
		}

		// ServiceProfile abstract
		if (isVisible(link_abstract)) {
		    s.append(getLinkHTML(link_abstract,
			    "hide abstract<br><br>\n"));
		    getServiceProfileHTML(s, profile);
		} else {
		    s.append(getLinkHTML(link_abstract, "show abstract"));
		}
	    }
	}

	s.append("\n</body></html>");

	// System.out.println(s);

	return s.toString();
    }
}
