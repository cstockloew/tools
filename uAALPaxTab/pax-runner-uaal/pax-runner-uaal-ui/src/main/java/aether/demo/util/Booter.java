package aether.demo.util;

/*******************************************************************************
 * Copyright (c) 2010-2011 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.cli.MavenCli;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.repository.RepositoryPolicy;

import aether.demo.manual.ManualRepositorySystemFactory;

/**
 * A helper to boot the repository system and a repository system session.
 */
public class Booter {
	
	public static RepositorySystem newRepositorySystem() {
		return ManualRepositorySystemFactory.newRepositorySystem();
	}
	
	public static RepositorySystemSession newRepositorySystemSession(RepositorySystem system) {
		MavenRepositorySystemSession session = new MavenRepositorySystemSession();
		
		LocalRepository localRepo = new LocalRepository(MavenCli.userMavenConfigurationHome + File.separator + "repository");
		// LocalRepository localRepo = new LocalRepository("C:\\Users\\jgdo\\.m2\\repository");
		session.setLocalRepositoryManager(system.newLocalRepositoryManager(localRepo));
		
		session.setTransferListener(new ConsoleTransferListener());
		session.setRepositoryListener(new ConsoleRepositoryListener());
		
		// uncomment to generate dirty trees
		// dirty tree: every child node contains all dependencies; not dirty: every dependency is contained at most once
		session.setDependencyGraphTransformer(null);
		
		return session;
	}
	
	public static List<RemoteRepository> newRepositories() {
		List<RemoteRepository> repos = new ArrayList<RemoteRepository>();
		RemoteRepository r = new RemoteRepository("central", "default", "http://repo1.maven.org/maven2/");
		r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		repos.add(r);
		
		r = new RemoteRepository("uaal", "default", "http://depot.universaal.org/maven-repo/releases/");
		r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		repos.add(r);
		
		r = new RemoteRepository("uaal-snapshots", "default", "http://depot.universaal.org/maven-repo/snapshots/");
		r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		repos.add(r);
		
		r = new RemoteRepository("uaal-thirdparty", "default", "http://depot.universaal.org/maven-repo/thirdparty/");
		r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		repos.add(r);
		
		r = new RemoteRepository(">maven2-repository.java.net", "default", "http://download.java.net/maven/2");
		r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		repos.add(r);
		
		r = new RemoteRepository("apache-snapshots", "default", "http://people.apache.org/repo/m2-snapshot-repository");
		r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		repos.add(r);
		
		r = new RemoteRepository("ima-thirdparty", "default", "http://v2me.igd.fraunhofer.de/nexus/content/repositories/thirdparty/");
		r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		repos.add(r);
		
		r = new RemoteRepository("ima-release", "default", "http://v2me.igd.fraunhofer.de/nexus/content/repositories/releases/");
		r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		repos.add(r);
		
		r = new RemoteRepository("ima-snapshot", "default", "http://v2me.igd.fraunhofer.de/nexus/content/repositories/snapshots/");
		r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		repos.add(r);
		
		// r = new RemoteRepository("igd_releases", "default", "http://a1gforge.igd.fraunhofer.de/nexus/content/repositories/releases/");
		// r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		// r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		// repos.add(r);
		//
		// r = new RemoteRepository("igd_snapshots", "default", "http://a1gforge.igd.fraunhofer.de/nexus/content/repositories/snapshots/");
		// r.setPolicy(true, r.getPolicy(true).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		// r.setPolicy(false, r.getPolicy(false).setUpdatePolicy(RepositoryPolicy.UPDATE_POLICY_NEVER));
		// repos.add(r);
		
		return repos;
	}
}
