package org.universAAL.tools.makrorecorder.makrorecorder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.tools.makrorecorder.Activator;

public class myContextSubscriber extends ContextSubscriber {

	static Long startTime;
	static Long stopTime;

	public static Long getStartTime() {
		return startTime;
	}

	static boolean recording;
	static Vector<String> subscribedUris;

	public myContextSubscriber(ModuleContext context) {
		super(context, null);
		subscribedUris = new Vector<String>();
	}

	@Override
	public void communicationChannelBroken() {
	}

	@Override
	public void handleContextEvent(ContextEvent arg0) {
		if (!recording) {
			Activator.resourceOrginazer.check(arg0);
		}
	}

	/**
	 * creates the subscribtion pattern for the ContextSubscriber from a Resource which should be a ContextEvent
	 **/
	private ContextEventPattern createSubscription(Resource event) {
		ContextEventPattern contextEventPattern = new ContextEventPattern();
		Resource subject = (Resource) event.getProperty(ContextEvent.PROP_RDF_SUBJECT);
		String subjectUri = subject.getURI();
		// contextEventPattern.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT,
		// subjectUri));//29.03.2012@maxim
		contextEventPattern.addRestriction(MergedRestriction.getFixedValueRestriction(ContextEvent.PROP_RDF_SUBJECT,
				subject));
		return contextEventPattern;
	}

	/**
	 * creates the subscribtion pattern for the ContextSubscriber form a SubjectUri of a ContextEvent
	 **/

	private ContextEventPattern createSubscription(String uri) {
		ContextEventPattern contextEventPattern = new ContextEventPattern();
		contextEventPattern.addRestriction(MergedRestriction
				.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT, uri));
		return contextEventPattern;
	}

	/**
	 * adds a new contextEventPattern to the Contextsubscriber from a ContextEvent.
	 **/
	public void addSubscription(Resource resource) {
		String subjectUri = ((Resource) resource.getProperty(ContextEvent.PROP_RDF_SUBJECT)).getURI();
		if (!subscribedUris.contains(subjectUri)) {
			subscribedUris.add(subjectUri);
			addNewRegParams(new ContextEventPattern[] { createSubscription(resource) });
		}
	}

	/**
	 * removes a contextEventPattern to the Contextsubscriber from a ContextEvent.
	 **/

	public void removeSubscription(Resource resource) {
		String subjectUri = ((Resource) resource.getProperty(ContextEvent.PROP_RDF_SUBJECT)).getURI();
		if (subscribedUris.contains(subjectUri)) {
			subscribedUris.remove(subjectUri);
			removeMatchingRegParams(new ContextEventPattern[] { createSubscription(resource) });
		}
	}

	/**
	 * switch the Programm into Recording state.
	 **/
	public void startRecording() {
		recording = true;
		startTime = new Date().getTime();
	}

	/**
	 * Leaves Recordingstate and invokes the reading of the Service Requests and ContextEvents
	 **/
	public void stopRecording(String Name) {
		recording = false;
		stopTime = new Date().getTime();
		long duration = stopTime - startTime;

		try {
			Vector<ContextEvent> events = (Activator.myServiceProvider.callChe(startTime, stopTime));
			ArrayList<Object[]> requests = (Activator.myServiceProvider.readServiceRequests(startTime, stopTime));

			if (events == null) {
				events = new Vector<ContextEvent>();
				Activator.logger.error("Che returned null, is the Che started?");
			} /*
			 * else { int i=1; while (i<events.size()) { if
			 * (events.get(i).getProperty(ContextEvent.PROP_CONTEXT_TIMESTAMP
			 * ).equals(events.get(i-1).getProperty(ContextEvent.PROP_CONTEXT_TIMESTAMP))) events.remove(i); else i++; }
			 * }
			 */
			if (requests.isEmpty())
				Activator.logger.error("no ServiceCalls where read, is the Middleware modified?");

			for (ContextEvent event : events)
				System.err.println(event.toString());

			// create new pattern.
			Activator.resourceOrginazer.addPattern(events, requests, Name, duration);
		} catch (FileNotFoundException e) {
			Activator.logger.error("Error: Request-Log not found: " + e.toString());
		} catch (IOException e) {
			Activator.logger.error("Error: while reading Request-Log: " + e.toString());
		} catch (Exception e) {
			Activator.logger.error("Error: " + e.toString());
		}
	}
}
