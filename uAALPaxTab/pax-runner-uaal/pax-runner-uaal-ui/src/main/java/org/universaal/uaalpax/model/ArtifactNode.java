package org.universaal.uaalpax.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ArtifactNode {
	private ArtifactURL artifact;
	
	private Set<ArtifactNode> children;
	private Set<ArtifactNode> parents;
	
	private List<BundleEntry> bundleEntries;
	
	public ArtifactNode(ArtifactURL artifact) {
		this.artifact = artifact;
		this.children = new HashSet<ArtifactNode>();
		this.parents = new HashSet<ArtifactNode>();
		
		this.bundleEntries = new LinkedList<BundleEntry>();
	}
	
	public List<BundleEntry> getBundleEntries() {
		return bundleEntries;
	}
	
	public ArtifactURL getArtifact() {
		return artifact;
	}
	
	public Set<ArtifactNode> getChildren() {
		return children;
	}
	
	public Set<ArtifactNode> getParents() {
		return parents;
	}
	
	public void addBundleEntry(BundleEntry be) {
		bundleEntries.add(be);
	}
	
	public void addChild(ArtifactNode child) {
		if (children.add(child))
			child.addParent(this);
	}
	
	private void addParent(ArtifactNode parent) {
		parents.add(parent);
	}
	
	private void removeChild(ArtifactNode c) {
		children.remove(c);
	}
	
	private void removeParent(ArtifactNode p) {
		parents.remove(p);
	}
	
	public void removeSelf() {
		for(ArtifactNode parent: getParents())
			parent.removeChild(this);
		
		for(ArtifactNode child: getChildren())
			child.removeParent(this);
		
		parents.clear();
		children.clear();
		bundleEntries.clear();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getArtifact()).append("\n");
		
		sb.append("parents\n");
		for (ArtifactNode p : getParents())
			sb.append("  ").append(p.getArtifact()).append("\n");
		
		sb.append("children\n");
		for (ArtifactNode c : getChildren())
			sb.append("  ").append(c.getArtifact()).append("\n");
		
		sb.append("bundles\n");
		for (BundleEntry b : getBundleEntries())
			sb.append("  ").append(b).append("\n");
		
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return artifact.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj; // TODO
	}
}
