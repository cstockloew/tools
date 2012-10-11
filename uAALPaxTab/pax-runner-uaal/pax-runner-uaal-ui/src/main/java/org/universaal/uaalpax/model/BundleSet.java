/*	
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
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

package org.universaal.uaalpax.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.universaal.uaalpax.shared.Attribute;

public class BundleSet implements Iterable<BundleEntry> {
	private Map<String, String> bundles;
	
	public BundleSet() {
		bundles = new HashMap<String, String>();
	}
	
	public BundleSet(Map<String, String> bundles) {
		this.bundles = new HashMap<String, String>(bundles);
	}
	
	public BundleSet(ILaunchConfiguration configuration) {
		updateBundles(configuration);
	}
	
	public BundleSet(BundleSet bundleset) {
		this.bundles = new HashMap<String, String>(bundleset.bundles);
	}

	public Iterator<BundleEntry> iterator() {
		return new BundleIterator(bundles.entrySet().iterator());
	}
	
	public void updateBundles(ILaunchConfiguration configuration) {
		try {
			@SuppressWarnings("unchecked")
			Map<String, String> launch = configuration.getAttribute(Attribute.PROVISION_ITEMS, new HashMap<Object, Object>());
			bundles = launch;
		} catch (CoreException e) {
			bundles = new HashMap<String, String>();
		}
	}
	
	public void add(BundleEntry e) {
		add(e.getURL(), e.getOptions());
	}
	
	public void add(String url, String options) {
		bundles.put(url, options);
	}
	
	public boolean containsURL(String url) {
		return bundles.containsKey(url);
	}
	
	public BundleEntry find(String url) {
		String options = bundles.get(url);
		if(options != null)
			return new BundleEntry(url, options);
		else
			return null;
	}
	
	public Set<String> allURLs() {
		return bundles.keySet();
	}
	
	private static class BundleIterator implements Iterator<BundleEntry> {
		Iterator<Map.Entry<String, String>> iter;
		
		public BundleIterator(Iterator<Map.Entry<String, String>> iter) {
			this.iter = iter;
		}
		
		public boolean hasNext() {
			return iter.hasNext();
		}
		
		public BundleEntry next() {
			Map.Entry<String, String> e = iter.next();
			return new BundleEntry(e.getKey(), e.getValue());
		}
		
		public void remove() {
			iter.remove();
		}
		
	}
	
	public int size() {
		return bundles.size();
	}

	public void removeURL(String url) {
		bundles.remove(url);
	}
	
	@Override
	public String toString() {
		return bundles.toString();
	}

	public void removeAll(Collection<BundleEntry> entries) {
		for(BundleEntry e: entries)
			bundles.remove(e.getURL());
	}

	public boolean remove(BundleEntry be) {
		return bundles.remove(be.getURL()) != null;
	}

	public void addAll(Collection<BundleEntry> entries) {
		for(BundleEntry e: entries)
			bundles.put(e.getURL(), e.getOptions());
	}
	
	public void updateBundleOptions(BundleEntry be) {
		if(!bundles.containsKey(be.getURL())) // to be sure 
			throw new IllegalArgumentException("can only update options of already existing bundle");
		
		bundles.put(be.getURL(), be.getOptions());
	}
}
