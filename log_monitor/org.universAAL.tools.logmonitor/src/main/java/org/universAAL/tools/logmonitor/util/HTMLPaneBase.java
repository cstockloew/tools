/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.util;

import java.net.URL;
import java.util.HashMap;

import javax.swing.JTextPane;

import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.tools.logmonitor.Activator;
import org.universAAL.tools.logmonitor.service_bus_matching.URI;

public class HTMLPaneBase extends JTextPane {
    private static final long serialVersionUID = 1L;

    protected HashMap<String, String> urlReplacement = new HashMap<String, String>();

    protected URL getURL(String filename) {
	return Activator.getResource(filename);
    }

    protected String getImageHTML(String name) {
	String filename = "/img/" + name;
	URL url = getURL(filename);

	urlReplacement.put(url.toString(),
		"http://depot.universaal.org/images/LogMonitor/" + name);

	return "<img src=\"" + url.toString() + "\">";
    }

    protected String getURIHTML(String uri) {
	int idx = uri.lastIndexOf('#');
	if (idx == -1)
	    return uri;

	String start = uri.substring(0, idx + 1);
	String end = uri.substring(idx + 1);
	return start + "<b>" + end + "</b>";
    }

    protected String getTableStartHTML() {
	return getTableStartHTML(0);
    }

    protected String getTableStartHTML(int border) {
	return "<table border=\"0\"><tr><td width=\"20\"></td><td>"
		+ "<table valign=\"top\" border=\"" + border + "\">\n";
    }

    protected String getTableRowHTML(String val1) {
	return "<tr><td>" + val1 + "</td></tr>\n";
    }

    protected String getTableRowHTML(String val1, int colspan) {
	return "<tr><td colspan=\"" + colspan + "\" bgcolor=\"#EEEEEE\">"
		+ val1 + "</td></tr>\n";
    }

    protected String getTableRowHTML(String val1, String val2) {
	return "<tr><td>" + val1 + "</td><td>" + val2 + "</td></tr>\n";
    }

    protected String getTableRowHTML(String val1, String val2, String val3) {
	return "<tr>\n  <td>" + val1 + "</td>\n  <td>" + val2 + "</td>\n  <td>"
		+ val3 + "</td>\n</tr>\n";
    }

    protected String getTableRowTitleHTML(String val1, String val2, String val3) {
	return "<tr>\n  <td  bgcolor=\"#DDDDDD\">" + val1
		+ "</td>\n  <td  bgcolor=\"#DDDDDD\">" + val2
		+ "</td>\n  <td bgcolor=\"#DDDDDD\">" + val3 + "</td>\n</tr>\n";
    }

    protected String getVTableRowWithTitleHTML(String val1, String val2) {
	return "<tr>\n  <td  bgcolor=\"#DDDDDD\">" + val1 + "</td>\n  <td>"
		+ val2 + "</td>\n</tr>\n";
    }

    protected String getTableEndHTML() {
	return "</table></td></tr></table>\n";
    }

    protected String getPropPathHTML(PropertyPath path, boolean shortForm) {
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

    protected String getLinkHTML(String link, String text) {
	return "<a href=\"" + link + "\">" + text + "</a>\n";
    }

    protected String turtle2HTML(String serialized) {
	return serialized.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
