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
	private Map<ArtifactURL, BundleEntry> bundles;
	
	public BundleSet() {
		bundles = new HashMap<ArtifactURL, BundleEntry>();
	}
	
	public BundleSet(Map<ArtifactURL, BundleEntry> bundles) {
		this.bundles = new HashMap<ArtifactURL, BundleEntry>(bundles);
	}
	
	public BundleSet(ILaunchConfiguration configuration) {
		updateBundles(configuration);
	}
	
	public BundleSet(BundleSet bundleset) {
		this.bundles = new HashMap<ArtifactURL, BundleEntry>(bundleset.bundles);
	}
	
	public Iterator<BundleEntry> iterator() {
		return bundles.values().iterator();
	}
	
	public void updateBundles(ILaunchConfiguration configuration) {
		bundles = new HashMap<ArtifactURL, BundleEntry>();
		try {
			@SuppressWarnings("unchecked")
			Map<String, String> launch = configuration.getAttribute(Attribute.PROVISION_ITEMS, new HashMap<Object, Object>());
			
			for (Map.Entry<String, String> e : launch.entrySet()) {
				BundleEntry be = new BundleEntry(new LaunchURL(e.getKey()), e.getValue());
				bundles.put(be.getArtifactUrl(), be);
			}
		} catch (CoreException e) {
		}
	}
	
	public void add(BundleEntry e) {
		bundles.put(e.getArtifactUrl(), e);
	}
	
	public boolean containsURL(ArtifactURL url) {
		return bundles.containsKey(url);
	}
	
	public BundleEntry find(ArtifactURL url) {
		return bundles.get(url);
	}
	
	public Set<ArtifactURL> allURLs() {
		return bundles.keySet();
	}
	
	public int size() {
		return bundles.size();
	}
	
	public boolean removeURL(ArtifactURL url) {
		return bundles.remove(url) != null;
	}
	
	@Override
	public String toString() {
		return bundles.toString();
	}
	
	public void removeAll(Collection<BundleEntry> entries) {
		for (BundleEntry e : entries)
			remove(e);
	}
	
	public boolean remove(BundleEntry be) {
		return removeURL(be.getArtifactUrl());
	}
	
	public void addAll(Collection<BundleEntry> entries) {
		for (BundleEntry e : entries)
			add(e);
	}
	
	public void updateBundleOptions(BundleEntry be) {
		if (!bundles.containsKey(be.getLaunchUrl())) // to be sure
			throw new IllegalArgumentException("can only update options of already existing bundle");
		
		bundles.put(be.getArtifactUrl(), be); // be is immutable, so its ok
	}
}
