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

package org.universaal.uaalpax.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.util.artifact.JavaScopes;
import org.universaal.uaalpax.model.BundleEntry;

import aether.demo.util.Booter;


public class MavenDependencyResolver {
	private RepositorySystem system;
	private RepositorySystemSession session;
	private List<RemoteRepository> repos;
	
	private Map<Object, DependencyNode> resolvingCache;
	
	public MavenDependencyResolver() {
		system = Booter.newRepositorySystem();
		session = Booter.newRepositorySystemSession(system);
		repos = Booter.newRepositories();
		
		resolvingCache = new HashMap<Object, DependencyNode>();
	}
	
	public DependencyNode resolve(Artifact artifact) throws DependencyCollectionException {
		String url = BundleEntry.urlFromArtifact(artifact);
		
		DependencyNode artifactResults = resolvingCache.get(url);
		if (artifactResults == null) {
			CollectRequest collectRequest = new CollectRequest();
			collectRequest.setRoot(new Dependency(artifact, JavaScopes.COMPILE));
			collectRequest.setRepositories(repos);
			
			artifactResults = system.collectDependencies(session, collectRequest).getRoot();
			cacheDependencies(artifactResults);
		}
		
		return artifactResults;
	}
	
	private void cacheDependencies(DependencyNode node) {
		Dependency d = node.getDependency();
		if(d != null && d.getArtifact() != null) 
			resolvingCache.put(BundleEntry.urlFromArtifact(d.getArtifact()), node);
		
		for(DependencyNode child: node.getChildren())
			cacheDependencies(child);
	}
	
	public DependencyNode resolve(Set<Artifact> artifacts) throws DependencyCollectionException {
		DependencyNode artifactResults = resolvingCache.get(artifacts);
		if (artifactResults == null) {
			CollectRequest collectRequest = new CollectRequest();
			for (Artifact a : artifacts)
				collectRequest.addDependency(new Dependency(a, JavaScopes.COMPILE));
			collectRequest.setRepositories(repos);
			
			artifactResults = system.collectDependencies(session, collectRequest).getRoot();
			
			cacheDependencies(artifactResults);
		}
		
		return artifactResults;
	}
}
