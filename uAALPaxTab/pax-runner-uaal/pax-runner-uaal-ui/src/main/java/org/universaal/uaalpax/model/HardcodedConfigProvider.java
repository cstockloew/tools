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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HardcodedConfigProvider implements UAALVersionProvider {
	private static Map<String, BundleSet> middlewares = new HashMap<String, BundleSet>();
	private static Map<String, Map<String, BundleSet>> features = new HashMap<String, Map<String, BundleSet>>();
	private static BundleSet ignoreSet;
	
	private static final BundleEntry[] m111 = new BundleEntry[] {
			new BundleEntry("mvn:org.apache.felix/org.apache.felix.configadmin/1.2.4", "true@true@2@false"),
			new BundleEntry("mvn:org.osgi/org.osgi.compendium/4.2.0", "true@true@2@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.acl.interfaces/1.1.1-SNAPSHOT", "true@true@2@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.bus.context/1.1.1-SNAPSHOT", "true@true@5@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.bus.model/1.1.1-SNAPSHOT", "true@true@3@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.bus.service/1.1.1-SNAPSHOT", "true@true@5@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.container.osgi/1.1.1-SNAPSHOT", "true@true@3@true"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.container.xfaces/1.1.1-SNAPSHOT", "true@true@2@true"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.data.representation/1.1.1-SNAPSHOT", "true@true@4@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.data.serialization/1.1.1-SNAPSHOT", "true@true@4@false"),
			
			new BundleEntry("wrap:mvn:java3d/j3d-core/1.3.1", "true@true@2@false"),
			new BundleEntry("wrap:mvn:java3d/vecmath/1.3.1", "true@true@2@false"),
			new BundleEntry("wrap:mvn:jp.go.ipa/jgcl/1.0", "true@true@2@false"),
			new BundleEntry("wrap:mvn:org.bouncycastle/jce.jdk13/144", "true@true@2@false"),
			new BundleEntry("wrap:mvn:org.ops4j.pax.confman/pax-confman-propsloader/0.2.2", "true@true@3@false"),
			
			new BundleEntry("wrap:mvn:org.ops4j.pax.logging/pax-logging-api/1.6.2", "true@true@2@false"),
			new BundleEntry("wrap:mvn:org.ops4j.pax.logging/pax-logging-service/1.6.2", "true@true@3@false"),
	
	// new BundleEntry("mvn:org.apache.felix/org.osgi.compendium/1.0.0", "true@true@3@true"),
	// new BundleEntry("mvn:org.apache.felix/javax.servlet/1.0.0", "true@true@2@true"),
	
	// new BundleEntry("mvn:org.ops4j.pax.logging/pax-logging-api", "true@true@2@true"),
	// new BundleEntry("mvn:org.ops4j.pax.logging/pax-logging-service", "true@true@2@true"),
	};
	
	private static final BundleEntry[] m121 = new BundleEntry[] {
			new BundleEntry("mvn:org.universAAL.middleware/mw.container.xfaces.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.acl.interfaces.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("mvn:org.ops4j.pax.logging/pax-logging-api/1.6.2", "true@true@1@false"),
			new BundleEntry("mvn:org.ops4j.pax.logging/pax-logging-service/1.6.2", "true@true@1@false"),
			new BundleEntry("mvn:org.apache.felix/org.osgi.compendium/1.4.0", "true@true@1@false"),
			new BundleEntry("mvn:org.apache.felix/org.apache.felix.log/1.0.1", "true@true@1@false"),
			new BundleEntry("mvn:org.apache.felix/org.apache.felix.configadmin/1.2.4", "true@true@1@false"),
			new BundleEntry("mvn:org.apache.felix/org.apache.felix.fileinstall/3.1.10", "true@true@1@false"),
			new BundleEntry("mvn:org.ops4j.pax.confman/pax-confman-propsloader/0.2.2", "true@true@1@false"),
			new BundleEntry("mvn:org.apache.felix/org.apache.felix.bundlerepository/1.4.2", "true@true@1@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.container.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("wrap:mvn:org.bouncycastle/jce.jdk13/144", "true@true@1@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.acl.upnp.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("mvn:org.apache.felix/org.apache.felix.upnp.basedriver/0.8.0", "true@true@1@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.bus.model.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.data.representation.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.bus.service.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.bus.context.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.bus.ui.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("mvn:org.universAAL.middleware/mw.data.serialization.osgi/1.2.1-SNAPSHOT", "true@true@1@false"),
			new BundleEntry("wrap:mvn:java3d/vecmath/1.3.1", "true@true@1@false"),
			new BundleEntry("wrap:mvn:java3d/j3d-core/1.3.1", "true@true@1@false"),
			new BundleEntry("wrap:mvn:jp.go.ipa/jgcl/1.0", "true@true@1@false"), };
	
	static {
		// register version 1.1.1
		BundleSet v_1_1_1 = new BundleSet();
		for (BundleEntry pu : m111)
			v_1_1_1.add(pu.getURL(), pu.getOptions());
		middlewares.put("1.1.1", v_1_1_1);
		BundleSet v_1_2_1 = new BundleSet();
		
		// register version 1.2.1
		for (int i = 0; i < m121.length; i++) {
			m121[i].setLevel(i + 2);
			v_1_2_1.add(m121[i].getURL(), m121[i].getOptions());
		}
		middlewares.put("1.2.1", v_1_2_1);
		
		// create ignore set
		ignoreSet = new BundleSet();
		ignoreSet.add(new BundleEntry("mvn:org.apache.felix/org.apache.felix.main", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.apache.felix/org.apache.felix.framework", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.apache.felix/org.osgi.core", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.apache.felix/org.apache.felix.bundlerepository", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.apache.felix/org.apache.felix.shell", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.apache.felix/org.apache.felix.shell.tui", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.apache.felix/org.osgi.compendium", true, true, 1, true));
		// ignoreSet.add(new BundleEntry("mvn:org.ops4j.pax.logging/pax-logging-api/1.6.2", true, true, 1, true));
		// ignoreSet.add(new BundleEntry("mvn:org.ops4j.pax.logging/pax-logging-service/1.6.2", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.ops4j.pax.logging/pax-logging-api", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.ops4j.pax.logging/pax-logging-service", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.apache.felix/javax.servlet", true, true, 1, true));
		
		ignoreSet.add(new BundleEntry("mvn:java3d/j3d-core/1.3.1", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:java3d/vecmath/1.3.1", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:jp.go.ipa/jgcl/1.0", true, true, 1, true));
		ignoreSet.add(new BundleEntry("mvn:org.bouncycastle/jce.jdk13/144", true, true, 1, true));
		
		ignoreSet.add(new BundleEntry("mvn:org.universAAL.middleware/mw.composite", true, true, 1, true));
	}
	
	public Set<String> getAvailableVersions() {
		return middlewares.keySet();
	}
	
	public BundleSet getBundlesOfVersion(String version) {
		return middlewares.get(version);
	}
	
	public Set<String> getAdditionalFeatures(String version) {
		Map<String, BundleSet> f = features.get(version);
		if (f == null)
			return new HashSet<String>();
		else
			return f.keySet();
	}
	
	public BundleSet getBundlesOfFeature(String version, String feature) {
		Map<String, BundleSet> f = features.get(version);
		if (f == null)
			return null;
		else
			return f.get(feature);
	}
	
	public BundleSet getIgnoreBundlesOfVersion(String version) {
		return ignoreSet;
	}
	
	public boolean isIgnoreBundleOfVersion(BundleEntry be, String version) {
		for (BundleEntry ignore : ignoreSet)
			if (be.getURL().startsWith(ignore.getURL()))
				return true;
		
		// check if it is a .core bundle and the corresponding .osgi bundle is already contained in version
		int pos = be.getURL().lastIndexOf(".core");
		if (pos >= 0
				&& getBundlesOfVersion(version).containsURL(
						be.getURL().substring(0, pos).concat(".osgi").concat(be.getURL().substring(pos + 5, be.getURL().length()))))
			return true;
		
		return false;
	}
}
