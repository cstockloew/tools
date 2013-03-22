package org.universaal.uaalpax.maven;

import java.util.Set;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.graph.DependencyNode;

public class ResolutionResult {
	public final DependencyNode depNode;
	public final Set<Artifact> unresolvedArtifacts;
	
	public ResolutionResult(DependencyNode depNode, Set<Artifact> unresolvedArtifacts) {
		this.depNode = depNode;
		this.unresolvedArtifacts = unresolvedArtifacts;
	}
}
