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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.universaal.uaalpax.maven.MavenDependencyResolver;

public class ArtifactGraph {
	// private List<ArtifactNode> buttomNodes;
		
	private Map<ArtifactURL, ArtifactNode> url2nodeMap;
	
	public ArtifactGraph() {
		// this.buttomNodes = new LinkedList<ArtifactNode>();
		this.url2nodeMap = new HashMap<ArtifactURL, ArtifactNode>();
	}
	
	public ArtifactNode insertDependencyNode(DependencyNode dNode, ArtifactNode parent) {
		Dependency d = dNode.getDependency();
		ArtifactNode aNode = null;
		
		if (d != null) {
			ArtifactURL url = BundleEntry.artifactUrlFromArtifact(d.getArtifact());
			
			aNode = url2nodeMap.get(url);
			if (aNode == null) {
				aNode = new ArtifactNode(url);
				url2nodeMap.put(url, aNode);
			}
			
			if (parent != null)
				parent.addChild(aNode);
		}
		
		if (aNode != null)
			parent = aNode;
		
		for (DependencyNode child : dNode.getChildren()) {
			insertDependencyNode(child, parent);
		}
		
		return aNode;
	}
	
	public void clear() {
		url2nodeMap.clear();
	}
	
	public void rebuildFromSetInBackground(BundleSet bs) {
		clear();
		
		for (BundleEntry be : bs) {
			if(!be.isMavenBundle())
				continue;
			
			Artifact a;
			try {
				a = be.toArtifact();
			} catch (UnknownBundleFormatException e1) {
				continue; // not mvn bundle
			}
			
			try {
				DependencyNode dNode = MavenDependencyResolver.getResolver().resolveDependenciesBlocking(a);
				
				// System.out.println("Tree of Artifact " + a);
				// ConsoleDependencyGraphDumper dumper = new ConsoleDependencyGraphDumper();
				// dNode.accept(dumper);
				
				insertDependencyNode(dNode, null);
			} catch (DependencyCollectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ArtifactNode getNode(String url) {
		return url2nodeMap.get(url);
	}
	
	public Set<ArtifactURL> checkCanRemove(Collection<BundleEntry> bes) {
		Map<ArtifactURL, BundleEntry> map = new HashMap<ArtifactURL, BundleEntry>();
		for (BundleEntry be : bes) {
			try {
				map.put(be.getArtifactUrl(), be);
			} catch (UnknownBundleFormatException e) {
				continue;
			}
		}
		
		Set<ArtifactURL> willBeRemoved = new HashSet<ArtifactURL>();
		
		for (BundleEntry be : bes) {
			ArtifactNode root;
			try {
				root = url2nodeMap.get(be.getArtifactUrl());
			} catch (UnknownBundleFormatException e) {
				continue;
			}
			
			if (root == null)
				continue; // no error here
				
			for (ArtifactNode parent : root.getParents())
				checkNodeAndParentsInSet(parent, map, willBeRemoved);
		}
		
		if (willBeRemoved.isEmpty())
			return null;
		else
			return willBeRemoved;
	}
	
	private void checkNodeAndParentsInSet(ArtifactNode node, Map<ArtifactURL, BundleEntry> map, Set<ArtifactURL> willBeRemoved) {
		if (!map.containsKey(node.getArtifact()))
			willBeRemoved.add(node.getArtifact());
		
		for (ArtifactNode parent : node.getParents())
			checkNodeAndParentsInSet(parent, map, willBeRemoved);
	}
	
	public Set<ArtifactURL> removeEntries(Collection<BundleEntry> bes, BundleSet versionBundles) {
		Map<ArtifactURL, BundleEntry> map = new HashMap<ArtifactURL, BundleEntry>();
		for (BundleEntry be : bes)
			try {
				map.put(be.getArtifactUrl(), be);
			} catch (UnknownBundleFormatException e1) {
			}
		
		if (versionBundles == null)
			versionBundles = new BundleSet();
		
		Set<ArtifactURL> versionSet = new HashSet<ArtifactURL>();
		for (BundleEntry be : versionBundles)
			try {
				versionSet.add(be.getArtifactUrl());
			} catch (UnknownBundleFormatException e1) {
			}
		
		Set<ArtifactURL> toRemove = new HashSet<ArtifactURL>();
		
		for (Map.Entry<ArtifactURL, BundleEntry> e : map.entrySet()) {
			toRemove.remove(e.getKey());
			
			ArtifactNode root = url2nodeMap.get(e.getKey());
			if (root == null)
				continue;
			
			removeNodeAndOnlyOwnChildren(root, toRemove, versionSet);
		}
		
		return toRemove;
	}
	
	private void removeNodeAndOnlyOwnChildren(ArtifactNode node, Set<ArtifactURL> toRemove, Set<ArtifactURL> versionSet) {
		if (node.getParents().size() > 0 || versionSet.contains(node.getArtifact())) // reached the bottom
			return;
		
		// create copy of node's children
		Set<ArtifactNode> children = new HashSet<ArtifactNode>();
		children.addAll(node.getChildren());
		
		toRemove.add(node.getArtifact());
		
		removeNode(node);
		
		for (ArtifactNode child : children)
			removeNodeAndOnlyOwnChildren(child, toRemove, versionSet);
	}
	
	private void removeNode(ArtifactNode node) {
		url2nodeMap.remove(node.getArtifact());
		node.removeSelf();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ArtifactNode n : url2nodeMap.values())
			sb.append(n.toString()).append("\n");
		
		return sb.toString();
	}
}
