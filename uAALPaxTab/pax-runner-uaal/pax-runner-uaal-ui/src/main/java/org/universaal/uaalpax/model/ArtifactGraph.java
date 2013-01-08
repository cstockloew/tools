package org.universaal.uaalpax.model;

import java.awt.Container;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ContinueStatement;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.universaal.uaalpax.shared.MavenDependencyResolver;

public class ArtifactGraph {
	// private List<ArtifactNode> buttomNodes;
		
	private Map<ArtifactURL, ArtifactNode> url2nodeMap;
	
	public ArtifactGraph() {
		// this.buttomNodes = new LinkedList<ArtifactNode>();
		this.url2nodeMap = new HashMap<ArtifactURL, ArtifactNode>();
	}
	
	// public void addBundle(BundleEntry be, String currentVersion, UAALVersionProvider versionProvider) {
	// Set<Artifact> bundleArtifacts = getArtifactsOfUrl(be.getLaunchUrl());
	// // System.out.println("composite contains ");
	// // for(Artifact a: bundleArtifacts)
	// // System.out.println("  " + a);
	//
	// Set<ArtifactNode> newNodes = new HashSet<ArtifactNode>();
	//
	// long resTime = 0;
	// long lastTime = System.currentTimeMillis();
	// Set<String> bundleUrls = new HashSet<String>();
	//
	// for (Artifact a : bundleArtifacts) {
	// bundleUrls.add(BundleEntry.stringFromArtifact(a));
	//
	// try {
	// long last = System.currentTimeMillis();
	// DependencyNode dNode = dependencyResolver.resolve(a);
	// resTime += (System.currentTimeMillis() - last);
	//
	// System.out.println("Tree of Artifact " + a);
	// ConsoleDependencyGraphDumper dumper = new ConsoleDependencyGraphDumper();
	// dNode.accept(dumper);
	//
	// insertDependencyTree(dNode, null, be, newNodes);
	// } catch (DependencyCollectionException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (TimeoutException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// // for(ArtifactNode aNode: newNodes) {
	// // if(!bundleUrls.contains(aNode.getArtifact()))
	// // System.out.println("new node " + aNode.getArtifact());
	// // else
	// // System.out.println("contained node " + aNode.getArtifact());
	// // if(!bundleUrls.contains(aNode.getArtifact()) && versionProvider.isIgnoreArtifactOfVersion(launchUrl, version)) {
	// //
	// // }
	// // }
	//
	// long compTime = System.currentTimeMillis() - lastTime - resTime;
	//
	// System.out.println("resolution took " + resTime);
	// System.out.println("Comp took " + compTime);
	// }
	//
	// private ArtifactNode insertDependencyTree(DependencyNode dNode, ArtifactNode parent, BundleEntry be, Set<ArtifactNode> newNodes) {
	// Dependency d = dNode.getDependency();
	// ArtifactNode aNode = null;
	//
	// if (d != null) {
	// String url = BundleEntry.stringFromArtifact(d.getArtifact());
	//
	// aNode = url2nodeMap.get(url);
	// if (aNode == null) {
	// aNode = new ArtifactNode(url);
	// url2nodeMap.put(url, aNode);
	// newNodes.add(aNode);
	// }
	//
	// if (parent != null)
	// parent.addChild(aNode);
	// }
	//
	// if (aNode != null)
	// parent = aNode;
	//
	// for (DependencyNode child : dNode.getChildren()) {
	// insertDependencyTree(child, parent, null, newNodes);
	// }
	//
	// if (aNode != null && be != null)
	// aNode.addBundleEntry(be);
	//
	// return aNode;
	// }
	//
	// private Set<Artifact> getArtifactsOfUrl(String url) {
	// Artifact a = BundleEntry.artifactFromURL(url);
	//
	// if (BundleEntry.isCompositeURL(url)) {
	// a = dependencyResolver.resolveArtifact(a);
	// if (a == null)
	// return new HashSet<Artifact>();
	//
	// return readArtifactsFromComposite(a.getFile());
	// } else {
	// Set<Artifact> arts = new HashSet<Artifact>(1);
	// if (a != null)
	// arts.add(a);
	// return arts;
	// }
	// }
	//
	// private Set<Artifact> readArtifactsFromComposite(File file) {
	// Set<Artifact> arts = new HashSet<Artifact>();
	// if (!file.exists() || !file.canRead())
	// return arts; // TODO error message
	//
	// try {
	// BufferedReader br = new BufferedReader(new FileReader(file));
	// String url;
	//
	// while ((url = br.readLine()) != null) {
	// if (!url.isEmpty())
	// arts.addAll(getArtifactsOfUrl(url));
	// }
	// } catch (FileNotFoundException e) {
	// return arts;
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// return arts;
	// }
	//
	// return arts;
	// }
	
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
	
	public Set<ArtifactURL> checkCanRemove(Set<BundleEntry> bes) {
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
	
	public Set<ArtifactURL> removeEntries(Set<BundleEntry> bes, BundleSet versionBundles) {
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
