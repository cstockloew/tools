/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.tools.makrorecorder.makrorecorder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.util.ResourceComparator;
import org.universAAL.tools.makrorecorder.Activator;

public class Pattern {
	private BufferedReader reader;
	private BufferedWriter writer;
	private File Contextfile;
	private File Requestfile;
	private File Configfile;
	private int position; // list position
	private Vector<Resource> resources;
	private Long duration;
	private Date date;
	private String Name;
	private Boolean active;
	
	public synchronized Boolean getActive() {
		return active;
	}
	
	public synchronized void setActive(Boolean active) {
		this.active = active;
	}
	
	public synchronized Boolean toggleActive() {
		this.active = !this.active;
		return active;
	}
	
	public static final boolean HIGHER = true;
	public static final boolean LOWER = false;
	
	/**
	 * creates a new Pattern from scratch
	 **/
	private Pattern(String filename, String Name, Date date, long duration) {
		this.Name = Name;
		this.date = date;
		this.duration = new Long(duration);
		Contextfile = new File(filename + ".context");
		Requestfile = new File(filename + ".request");
		Configfile = new File(filename + ".config");
		resources = new Vector<Resource>();
		position = 0;
		active = true;
	}
	
	/**
	 * reads a Pattern from file
	 **/
	private Pattern(String filename) {
		Contextfile = new File(filename + ".context");
		Requestfile = new File(filename + ".request");
		Configfile = new File(filename + ".config");
		resources = new Vector<Resource>();
		position = 0;
		active = true;
	}
	
	/**
	 * creates a new Pattern and fills it with the ServiceRequest and the ContextEvents
	 **/
	public static Pattern createPattern(String filename, ArrayList<Object[]> requests, Vector<ContextEvent> events,
			String Name, Long duration) {
		Date date = new Date();
		Pattern pattern = new Pattern(filename, Name, date, duration);
		if (pattern.writePattern(events, requests)) {
			pattern.resources.addAll(events);
			pattern.subscribe();
			return pattern;
		} else
			return null;
	}
	
	/**
	 * loads a Pattern from file
	 **/
	public static Pattern loadPattern(String string) {
		Pattern pattern = new Pattern(string);
		if (pattern.loadContextEvents()) {
			pattern.loadConfig();
			pattern.subscribe();
			return pattern;
		}
		return null;
	}
	
	/**
	 * adds the ContextEvent from this pattern to the subscribtion of the ContextSubscriber
	 **/
	private void subscribe() {
		if (resources.size() == 0) {
			return;
		}
		for (Iterator iterator = resources.iterator(); iterator.hasNext();) {
			ContextEvent event = (ContextEvent) iterator.next();
			Activator.myContextSubscriber.addSubscription(event);
		}
	}
	
	/**
	 * saves ContextEvents to the a file
	 **/
	private void writeFile(Vector<? extends Resource> resources, File file) throws IOException {
		writer = new BufferedWriter(new FileWriter(file));
		
		if (resources.size() == 0) {
			writer.write(" ");
			writer.close();
			return;
		}
		for (Iterator iterator = resources.iterator(); iterator.hasNext();) {
			Resource resource = (Resource) iterator.next();
			writer.write("NewResource:");
			writer.write(Activator.contentSerializer.serialize(resource));
		}
		writer.close();
	}
	
	/**
	 * saves the Service Requests to a file
	 **/
	private void writeFile(ArrayList<Object[]> resources, File file) throws IOException {
		writer = new BufferedWriter(new FileWriter(file));
		
		if (resources.size() == 0) {
			writer.write(" ");
			writer.close();
			return;
		}
		for (Iterator iterator = resources.iterator(); iterator.hasNext();) {
			Object[] entry = (Object[]) iterator.next();
			writer.write("NewResource:" + (Long) entry[0]);
			String srString = Activator.contentSerializer.serialize((ServiceRequest) entry[1]);
			writer.write(srString);
		}
		writer.close();
	}
	
	/**
	 * save the Pattern to on the Harddisk in the Folder "Pattern"
	 **/
	private boolean writePattern(Vector<ContextEvent> events, ArrayList<Object[]> requests) {
		try {
			writeFile(events, Contextfile);
			writeFile(requests, Requestfile);
			writeConfig();
			return true;
		} catch (FileNotFoundException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
		} catch (IOException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
		}
		return false;
	}
	
	/**
	 * reads all ContextEvents from file
	 **/
	private boolean loadContextEvents() {
		try {
			resources = loadResource(Contextfile);
			return true;
		} catch (FileNotFoundException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
		} catch (IOException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
		}
		return false;
	}
	
	/**
	 * reads the configfile The ConfigFile consits of: Name of the Pattern date on which the pattern was recorded
	 * duration of the recording
	 **/
	private void loadConfig() {
		try {
			reader = new BufferedReader(new FileReader(Configfile));
			Name = reader.readLine();
			date = new Date(new Long(reader.readLine()));
			duration = new Long(reader.readLine());
		} catch (FileNotFoundException e) {
			Activator.logger.error("Error: while reading Configfile: " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Activator.logger.error("Error: while reading Configfile: " + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * writes Configfile The ConfigFile consits of: Name of the Pattern date on which the pattern was recorded duration
	 * of the recording
	 **/
	private void writeConfig() {
		try {
			writer = new BufferedWriter(new FileWriter(Configfile));
			writer.write(Name + "\n");
			writer.write(new Long(date.getTime()).toString() + "\n");
			writer.write(duration.toString() + "\n");
			writer.close();
		} catch (IOException e) {
			Activator.logger.error("Error: while writing Configfile: " + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the ContextEvents from file
	 **/
	private Vector<Resource> loadResource(File file) throws FileNotFoundException, IOException {
		Vector<Resource> requests = new Vector<Resource>();
		
		reader = new BufferedReader(new FileReader(file));
		String line;
		String text = new String("");
		while (null != (line = reader.readLine())) {
			if (line.contains("NewResource:")) {
				if (!text.equalsIgnoreCase("")) {
					requests.add((Resource) Activator.contentSerializer.deserialize(text));
				}
				text = new String("");
			} else
				text += line;
		}
		text = text.trim();
		if (!text.equalsIgnoreCase("") && (Resource) Activator.contentSerializer.deserialize(text) != null)
			requests.add((Resource) Activator.contentSerializer.deserialize(text)); // for the last Resource or if the
		// file only contains one Resource
		
		return requests;
	}
	
	/**
	 * Reads the ServiceRequests from file
	 **/
	private ArrayList<Object[]> loadRequests(File file) throws FileNotFoundException, IOException {
		ArrayList<Object[]> requests = new ArrayList<Object[]>();
		
		reader = new BufferedReader(new FileReader(file));
		String line;
		StringBuilder text = new StringBuilder();
		Long time = null;
		
		while (null != (line = reader.readLine())) {
			if (line.contains("NewResource:")) {
				if (text.length() > 0) {
					ServiceRequest request = (ServiceRequest) Activator.contentSerializer.deserialize(text.toString());
					Object[] entry = { time, request };
					requests.add(entry);
				}
				time = new Long(line.split("\\:")[1]);
				text = new StringBuilder();
			} else
				text.append(line);
		}
		
		String last = text.toString().trim();
		if (!last.isEmpty()) {
			ServiceRequest request = (ServiceRequest) Activator.contentSerializer.deserialize(last);
			if (request != null) {
				Object[] entry = { time, request };
				requests.add(entry);// for the last Resource or if the file only contains one Resource
			}
		}
		
		return requests;
	}
	
	/**
	 * loads Service Requests
	 **/
	public ArrayList<Object[]> getRequests() {
		try {
			ArrayList<Object[]> requests = loadRequests(Requestfile);
			return requests;
		} catch (FileNotFoundException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
		} catch (IOException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
		} catch (ClassCastException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
		}
		return null;
		
	}
	
	/**
	 * extracts only the important parts of a context event into a Resource else we can't compare it with a other
	 * ContextEvent important parts are: Subject predicate Object
	 **/
	static public Resource convertResource(ContextEvent ce, String newUri) {
		if (ce == null)
			return null;
		
		// first prepare the subject
		Resource subj_orig = (Resource) ce.getProperty(ContextEvent.PROP_RDF_SUBJECT);
		Resource subj_copy = ManagedIndividual.getInstance(subj_orig.getType(), subj_orig.getURI());
		if (subj_copy == null) {
			subj_copy = Resource.getResource(subj_orig.getType(), subj_orig.getURI());
			if (subj_copy == null) {
				subj_copy = new Resource(subj_orig.getURI());
				subj_copy.addType(subj_orig.getType(), true);
			}
		}
		subj_copy.setProperty(ce.getRDFPredicate(), ce.getRDFObject());
		
		// now prepare the converted resource
		Resource result = new Resource(newUri);
		result.setProperty(ContextEvent.PROP_RDF_SUBJECT, subj_copy);
		result.setProperty(ContextEvent.PROP_RDF_PREDICATE, ce.getProperty(ContextEvent.PROP_RDF_PREDICATE));
		result.setProperty(ContextEvent.PROP_RDF_OBJECT, ce.getProperty(ContextEvent.PROP_RDF_OBJECT));
		// result.setProperty(ContextEvent.PROP_CONTEXT_PROVIDER, ce.getProperty(ContextEvent.PROP_CONTEXT_PROVIDER));
		// do the same for any other props that you think you might need in your
		// record
		// props currently not copied: ContextEvent.PROP_CONTEXT_CONFIDENCE,
		// ContextEvent.PROP_CONTEXT_EXPIRATION_TIME,
		// ContextEvent.PROP_CONTEXT_TIMESTAMP, ContextEvent.PROP_RDFS_COMMENT,
		// ContextEvent.PROP_RDFS_LABEL
		// remember that from props currently not copied, only the timestamp is
		// always not-null
		
		// return the result
		return result;
	}
	
	/**
	 * converts a set of Context into a set of compareable Resources
	 **/
	static public Vector<Resource> convertResources(Vector<ContextEvent> events) {
		Vector<Resource> resources = new Vector<Resource>();
		for (Iterator iterator = events.iterator(); iterator.hasNext();) {
			ContextEvent event = (ContextEvent) iterator.next();
			resources.add(convertResource(event, "test"));
		}
		return resources;
	}
	
	/**
	 * compares a ContextEvent with the ContextEvents of actual list position if they are not equel we will reset the
	 * list position and return false.
	 **/
	public boolean check(ContextEvent event) {
		if(resources.isEmpty())
			return false;
		
		Resource r1 = convertResource(event, "test");
		ContextEvent savedEvent = (ContextEvent) resources.elementAt(position);
		Resource r2 = convertResource(savedEvent, "test");
		if (new ResourceComparator().areEqual(r1, r2)) {
			position++;
			if (position >= resources.size()) {
				position = 0;
				return true;
			}
		} else {
			position = 0;
			new ResourceComparator().printDiffs(r1, r2);
		}
		return false;
	}
	
	public void deleteContextEvent(int index) {
		resources.remove(index);
		try {
			writeFile(resources, Contextfile);
		} catch (IOException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
			e.printStackTrace();
		}
	}
	
	public void deleteServiceRequest(int index) {
		ArrayList<Object[]> requests = getRequests();
		requests.remove(index);
		try {
			writeFile(requests, Requestfile);
		} catch (IOException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
			e.printStackTrace();
		}
	}
	
	public void changePositionOfContextEvent(int index, boolean direction) {
		if (direction) {
			if (index == 0)
				return;
			Resource temp = resources.get(index);
			resources.add(index - 1, temp);
			resources.remove(index + 1);
		} else {
			if (index + 1 == resources.size())
				return;
			Resource temp = resources.get(index);
			resources.add(index + 2, temp);
			resources.remove(index);
		}
		try {
			writeFile(resources, Contextfile);
		} catch (IOException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
			e.printStackTrace();
		}
	}
	
	public void changePositionOfServiceRequest(int index, boolean direction) {
		
		ArrayList<Object[]> requests = getRequests();
		if (direction) {
			if (index == 0)
				return;
			Object[] temp = requests.get(index);
			requests.add(index - 1, temp);
			requests.remove(index + 1);
		} else {
			if (index + 1 == requests.size())
				return;
			Object[] temp = requests.get(index);
			requests.add(index + 2, temp);
			requests.remove(index);
		}
		try {
			writeFile(requests, Requestfile);
		} catch (IOException e) {
			Activator.logger.error(e.toString() + "\n" + e.getStackTrace());
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return this.Name;
	}
	
	public String getDate() {
		return this.date.toString();
	}
	
	public Vector<Resource> getEvents() {
		return resources;
	}
	
	public Long getDuration() {
		return duration;
	}
	
	public void deleteFiles() {
		Configfile.delete();
		Contextfile.delete();
		Requestfile.delete();
	}
	
	public ContextEvent getEvent(int index) {
		return (ContextEvent) resources.get(index);
	}
	
	public ServiceRequest getRequest(int index) {
		Object[] entry = getRequests().get(index);
		return (ServiceRequest) entry[1];
	}
}
