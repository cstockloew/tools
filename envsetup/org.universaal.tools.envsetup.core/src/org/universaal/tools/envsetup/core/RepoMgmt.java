/*	
	Copyright 2007-2016 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institute for Computer Graphics Research
	
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
package org.universaal.tools.envsetup.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Carsten Stockloew
 *
 */
public class RepoMgmt {
	public static class Repo {

		/** Human-readable name of the repo */
		public String name;

		/** URL in git */
		public String url;

		/** The relative folder in which the pom file file is */
		public String pom;

		Repo(String name, String url, String pom) {
			this.name = name;
			this.url = url;
			this.pom = pom;
		}
	}

	public static final String samples = "Samples";

	// name of group -> list of repos
	public static Map<String, List<Repo>> groups = new LinkedHashMap<String, List<Repo>>();

	// name of repo -> Repo
	public static Map<String, Repo> repos = new HashMap<String, Repo>();

	private static void add(String group, List<Repo> lst) {
		groups.put(group, lst);
		for (Repo r : lst) {
			repos.put(r.name, r);
		}
	}

	static {
		List<Repo> repos;

		repos = new ArrayList<Repo>();
		repos.add(new Repo("Middleware", "https://github.com/universAAL/middleware.git", "pom"));
		repos.add(new Repo("Ontology", "https://github.com/universAAL/ontology.git", "ont.pom"));
		repos.add(new Repo("Security and Privacy-Awareness", "https://github.com/universAAL/security.git",
				"security.pom"));
		repos.add(new Repo("Remote Interoperability", "https://github.com/universAAL/remote.git", "ri.pom"));
		repos.add(new Repo("Context", "https://github.com/universAAL/context.git", "ctxt.pom"));
		repos.add(new Repo("User Interaction", "https://github.com/universAAL/ui.git", "ui.pom"));
		repos.add(new Repo("Service", "https://github.com/universAAL/service.git", "srvc.pom"));
		repos.add(new Repo("Local Device Discovery and Integration (lddi)", "https://github.com/universAAL/lddi.git",
				"lddi.pom"));
		repos.add(new Repo("uAAL Super POM", "https://github.com/universAAL/platform.git", "uAAL.pom"));
		add("Platform", repos);

		repos = new ArrayList<Repo>();
		repos.add(new Repo(samples, "https://github.com/universAAL/samples.git", "samples.pom"));
		repos.add(new Repo("Utility Libraries", "https://github.com/universAAL/utilities.git", "utilities.pom"));
		repos.add(new Repo("Maven", "https://github.com/universAAL/maven.git", "maven.pom"));
		repos.add(new Repo("Integration Tests", "https://github.com/universAAL/itests.git", ""));
		add("Extras", repos);

		repos = new ArrayList<Repo>();
		repos.add(new Repo("Apache Karaf Distribution", "https://github.com/universAAL/distro.karaf.git", null));
		repos.add(new Repo("Pax Runner Distribution", "https://github.com/universAAL/distro.pax.git", null));
		add("Distributions", repos);
	}
}
