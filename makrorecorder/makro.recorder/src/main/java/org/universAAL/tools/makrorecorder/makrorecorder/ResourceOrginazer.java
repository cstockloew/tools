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

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.tools.makrorecorder.Activator;

public class ResourceOrginazer {

	Vector<Pattern> patterns;
	String basedir;

	/**
	 * starts Orginazer and loads all existing patterns
	 **/
	public ResourceOrginazer() {
		patterns = new Vector<Pattern>();
		basedir = "Pattern";
		File file = new File(basedir);
		if (file.exists()) {
			String[] children = file.list(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					if (name.contains(".context")) {
						return true;
					}
					return false;
				}
			});
			for (int i = 0; i < children.length; i++) {
				String string = children[i];
				string = string.replace(".context", "");
				patterns.add(Pattern.loadPattern(basedir + "/" + string));
			}
		} else {
			file.mkdir();
		}
	}

	public void addPattern(Vector<ContextEvent> events, ArrayList<Object[]> requests, String Name, long duration) {
		String filename = basedir + "/" + (Name);
		patterns.add(Pattern.createPattern(filename, requests, events, Name, duration));
	}

	/**
	 * invokes the "check" Method for each pattern if true is returned, we will add the ServiceRequest to our list of
	 * ServiceRequests to be invoked.
	 **/
	public void check(ContextEvent event) {
		ArrayList<Object[]> requests = new ArrayList<Object[]>();
		for (Iterator iterator = patterns.iterator(); iterator.hasNext();) {
			Pattern pattern = (Pattern) iterator.next();
			if (pattern.getActive() && pattern.check(event)) {// pattern will only be check if active
				requests.addAll(pattern.getRequests());
			}
		}
		if (!requests.isEmpty()) {// only call the Requests if the list is not empty
			Activator.myServiceProvider.startCallingServices(requests, Activator.gui.keepTimeDependencies(), null);
		}
	}

	public Vector<Pattern> getPatterns() {
		return patterns;
	}

	public Vector<String> getPatternNames() {
		Vector<String> Names = new Vector<String>();
		for (Iterator iterator = patterns.iterator(); iterator.hasNext();) {
			Pattern pattern = (Pattern) iterator.next();
			Names.add(pattern.getName());
		}
		return Names;
	}

	public Vector<Boolean> getState() {
		Vector<Boolean> booleans = new Vector<Boolean>();
		for (Iterator iterator = patterns.iterator(); iterator.hasNext();) {
			Pattern pattern = (Pattern) iterator.next();
			booleans.add(pattern.getActive());
		}
		return booleans;
	}

	public Vector<String> getPatternDates() {
		Vector<String> Dates = new Vector<String>();
		for (Iterator iterator = patterns.iterator(); iterator.hasNext();) {
			Pattern pattern = (Pattern) iterator.next();
			Dates.add(pattern.getDate());
		}
		return Dates;
	}

	public Pattern getPatternByName(String Name) {
		for (Iterator iterator = patterns.iterator(); iterator.hasNext();) {
			Pattern pattern = (Pattern) iterator.next();
			if (pattern.getName().equalsIgnoreCase(Name)) {
				return pattern;
			}
		}
		return null;
	}

	public void deletePattern(Pattern pattern) {
		for (Iterator iterator = patterns.iterator(); iterator.hasNext();) {
			if (iterator.next().equals(pattern)) {
				pattern.deleteFiles();
				iterator.remove();
			}
		}
	}
}
