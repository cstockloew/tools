/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.service_bus_matching;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.ServiceRequest;

public class Matchmaking {

    // a single matching contains information about the matchmaking between a
    // service request and a specific service profile
    public class SingleMatching {
	public static final int REASON_INPUT = 0;
	public static final int REASON_OUTPUT = 1;
	public static final int REASON_EFFECT = 2;

	// true, iff this service matches the request
	public Boolean success = null;

	// URI of the service profile for this single matching
	public String profileURI = "";

	// the reason, one of REASON_INPUT, REASON_OUTPUT, REASON_EFFECT
	public int reason = -1;

	// the complete message as given in the log message
	public Object[] msgPart;

	// the code as given in the log message
	public int code = -1;

	// the short reason for a mismatch as given in the log message
	public String shortReason = "";

	// the detailed reason for a mismatch as given in the log message
	public String detailedReason = "";

	// detailed information if reason == REASON_INPUT
	public String restrictedProperty;

	// detailed information if reason == REASON_OUTPUT
	public String outputURI;
	public Resource output;

	// detailed information if reason == REASON_EFFECT
	public Resource effect;

	public void processStandardMessage(Object[] msgPart) {
	    this.msgPart = msgPart;
	    shortReason = (String) msgPart[1];
	    code = ((Integer) msgPart[msgPart.length - 4]).intValue();
	    detailedReason = (String) msgPart[msgPart.length - 2];
	}
    }

    // true, iff the request has some matches. In this case: numMatches > 0
    public Boolean success = null;

    // the number of matches
    public int numMatches = -1;

    // the service request
    public ServiceRequest request;

    // the serialized service request (e.g. turtle)
    public String serializedRequest = null;

    // URI of the requested service
    public String serviceURI;

    // the set of single matchings
    public LinkedList<SingleMatching> matchings = new LinkedList<SingleMatching>();

    // the exact time when the matchmaking starts
    public Date date;

    /**
     * Get the date as a string.
     * 
     * @return The date as a string.
     */
    public String getDateString() {
	Calendar c = new GregorianCalendar();
	c.setTime(date);
	String dateString = new String();
	dateString += c.get(Calendar.YEAR) + "-";
	dateString += c.get(Calendar.MONTH) + "-";
	dateString += c.get(Calendar.DAY_OF_MONTH) + " ";
	dateString += c.get(Calendar.HOUR_OF_DAY) + ":";
	dateString += c.get(Calendar.MINUTE) + ":";
	dateString += c.get(Calendar.SECOND) + ".";
	dateString += c.get(Calendar.MILLISECOND);
	return dateString;
    }
}
