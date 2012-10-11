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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.universaal.uaalpax.shared.Attribute;
import org.universaal.uaalpax.shared.MavenDependencyResolver;

import aether.demo.util.ConsoleDependencyGraphDumper;

public class BundleModel {
	private BundleSet currentBundles;
	
	private List<BundlePresenter> allPresenters = new ArrayList<BundlePresenter>();
	
	private MavenDependencyResolver dependencyResolver;
	
	private UAALVersionProvider versionProvider;
	private String currentVersion;
	
	private List<BundleChangeListener> changeListeners = new ArrayList<BundleChangeListener>();
	
	public BundleModel(MavenDependencyResolver depResolver, UAALVersionProvider versionProvider) {
		currentBundles = new BundleSet(new HashMap<String, String>());
		this.dependencyResolver = depResolver;
		this.versionProvider = versionProvider;
	}
	
	public void addChangeListener(BundleChangeListener listener) {
		if (listener == null)
			throw new NullPointerException("listeners is null");
		
		changeListeners.add(listener);
	}
	
	private void notifyChanged() {
		for (BundleChangeListener l : changeListeners)
			l.notifyChanged();
	}
	
	public void addPresenter(BundlePresenter presenter) {
		allPresenters.add(presenter);
	}
	
	public void updateModel(ILaunchConfiguration configuration) {
		currentBundles.updateBundles(configuration);
		currentVersion = "Unknown";
		for (String version : versionProvider.getAvailableVersions()) {
			if (containsAllBundles(currentBundles, version)) {
				currentVersion = version;
				break;
			}
		}
		
		updatePresenters();
	}
	
	public void removeAll(Collection<BundleEntry> entries) {
		for (BundleEntry be : entries) {
			if (!currentBundles.containsURL(be.getURL()))
				continue; // nothing to do
				
			removeUnneededDependencies(be);
			currentBundles.remove(be);
		}
		updatePresenters();
	}
	
	public void remove(BundleEntry be) {
		removeNoUpdate(be);
		updatePresenters();
	}
	
	public void removeNoUpdate(BundleEntry be) {
		if (!currentBundles.containsURL(be.getURL()))
			return; // nothing to do
			
		removeUnneededDependencies(be);
		currentBundles.remove(be);
	}
	
	public void add(BundleEntry e) {
		insertBundleAndDeps(e);
		updatePresenters();
	}
	
	public void addAll(Collection<BundleEntry> entries) {
		for (BundleEntry be : entries)
			insertBundleAndDeps(be);
		updatePresenters();
	}
	
	public void addNoCheck(BundleEntry be) {
		currentBundles.add(be);
	}
	
	public void removeNoCheck(BundleEntry be) {
		currentBundles.remove(be);
	}
	
	public BundleSet getBundles() {
		return currentBundles;
	}
	
	private void insertBundleAndDeps(BundleEntry be) {
		Artifact a = be.toArtifact();
		boolean insterted = false;
		if (a != null) {
			try {
				DependencyNode deps = dependencyResolver.resolve(a);
				ConsoleDependencyGraphDumper dumper = new ConsoleDependencyGraphDumper();
				deps.accept(dumper);
				
				insertDependencies(deps, 1); // deps contains already be as root, so no need to insert it second time
				insterted = true;
			} catch (DependencyCollectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (!insterted)
			currentBundles.add(be);
	}
	
	public Set<String> getAvailableVersion() {
		return versionProvider.getAvailableVersions();
	}
	
	public String getCurrentVersion() {
		return currentVersion;
	}
	
	public BundleSet getMiddlewareBundles() {
		return versionProvider.getBundlesOfVersion(currentVersion);
	}
	
	/**
	 * @param node
	 * @param minStartLevel
	 *            min start level of child bundles, begin at 1 on very upper call
	 * @return start level of the node, i.e. dependent bundles have to start at a higher level
	 */
	private int insertDependencies(DependencyNode node, int minStartLevel) {
		// int minStartLevel = 1;
		
		// traverse postorder
		for (DependencyNode child : node.getChildren())
			minStartLevel = Math.max(minStartLevel, insertDependencies(child, minStartLevel));
		
		Dependency d = node.getDependency();
		if (d != null) {
			Artifact a = d.getArtifact();
			String url = BundleEntry.urlFromArtifact(a);
			
			// check if bundle is already included
			BundleEntry be = currentBundles.find(url);
			if (be != null) {
				minStartLevel = Math.max(minStartLevel, be.getLevel());
			} else {
				minStartLevel++;
				be = new BundleEntry(a);
				if(versionProvider.isIgnoreBundleOfVersion(be, currentVersion))
					return minStartLevel;
				
				be.setLevel(minStartLevel);
				currentBundles.add(be);
			}
		}
		
		return minStartLevel;
	}
	
	private void removeUnneededDependencies(BundleEntry be) {
		Artifact beArt = be.toArtifact();
		if (beArt != null) {
			try {
				// find out all dependencies of be (-> beDeps)
				Set<String> beDeps = new HashSet<String>();
				collectDepURLs(dependencyResolver.resolve(beArt), beDeps);
				
				BundleSet mwBundles = getMiddlewareBundles();
				if (mwBundles == null)
					mwBundles = new BundleSet();
				
				// find out dependencies of all bundles in model except those of in beDeps but not in current version bundles (-> otherDeps)
				Set<Artifact> otherArts = new HashSet<Artifact>();
				for (BundleEntry oe : currentBundles) {
					if (beDeps.contains(oe.getURL()) && !mwBundles.containsURL(oe.getURL()))
						continue;
					
					Artifact c = oe.toArtifact();
					if (c != null)
						otherArts.add(c);
				}
				
				Set<String> otherDeps = new HashSet<String>();
				collectDepURLs(dependencyResolver.resolve(otherArts), otherDeps);
				
				// find out which bundles in model are only dependencies of be, but not of any other bundle (-> unneededBundles as url set)
				Set<String> unneededBundles = new HashSet<String>();
				for (String d : beDeps)
					if (!otherDeps.contains(d))
						unneededBundles.add(d);
				
				// remove unneeded bundles from model
				for (String url : unneededBundles)
					currentBundles.removeURL(url);
			} catch (DependencyCollectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private void collectDepURLs(DependencyNode node, Set<String> urls) {
		if (node.getDependency() != null)
			urls.add(BundleEntry.urlFromArtifact(node.getDependency().getArtifact()));
		
		for (DependencyNode child : node.getChildren())
			collectDepURLs(child, urls);
	}
	
	public void updatePresenters() {
		BundleSet projects = currentBundles;
		for (BundlePresenter presenter : allPresenters)
			projects = presenter.updateProjectList(projects);
	}
	
	private boolean containsAllBundles(BundleSet launchProjects, String version) {
		BundleSet bundles = versionProvider.getBundlesOfVersion(version);
		if (bundles == null)
			return false;
		
		for (String url : bundles.allURLs())
			if (!launchProjects.containsURL(url))
				return false;
		
		return true;
	}
	
	public void changeToVersion(String newVersion) {
		BundleSet oldBS = versionProvider.getBundlesOfVersion(currentVersion);
		if (oldBS != null)
			for (BundleEntry be : oldBS)
				currentBundles.remove(be);
		
		// assume that the levels fit
		BundleSet newBS = versionProvider.getBundlesOfVersion(newVersion);
		if (newBS != null)
			for (BundleEntry be : newBS)
				currentBundles.add(be);
		
		currentVersion = newVersion;
		
		notifyChanged();
		updatePresenters();
	}
	
	public boolean checkCompatibleWithVersion(String url, String version) {
		BundleSet bundles = versionProvider.getBundlesOfVersion(version);
		if (bundles == null || bundles.containsURL(url)) // url is in bundles for current version
			return true;
		
		for (String v : versionProvider.getAvailableVersions()) {
			if (version.equals(v))
				continue;
			
			BundleSet otherBundles = versionProvider.getBundlesOfVersion(v);
			if (otherBundles != null && otherBundles.containsURL(url))
				return false; // bundles of an other version contains this url but not bundles of current version
		}
		
		return true;
	}
	
	public Set<String> getIncompatibleProjects(String newVersion) {
		// find out which projects have to be checked for compatibility
		Set<String> toCheck = new HashSet<String>();
		BundleSet versionBundles = versionProvider.getBundlesOfVersion(currentVersion);
		// fill toCheck with all bundles except of those in current version set
		if (versionBundles != null) {
			for (String url : currentBundles.allURLs())
				if (!versionBundles.containsURL(url))
					toCheck.add(url);
		} else
			for (String url : currentBundles.allURLs())
				toCheck.add(url);
		
		// check all toCheck project for compatibility with new version
		for (Iterator<String> iter = toCheck.iterator(); iter.hasNext();)
			if (checkCompatibleWithVersion(iter.next(), newVersion))
				iter.remove();
		
		return toCheck;
	}
	
	public boolean checkCompatibleWithCurrentVersion(String url) {
		return checkCompatibleWithVersion(url, currentVersion);
	}
	
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		// finally, save arguments list
		List<Object> arguments = new LinkedList<Object>();
		arguments.add("--overwrite=true");
		arguments.add("--overwriteUserBundles=true");
		arguments.add("--overwriteSystemBundles=true");
		arguments.add("--log=DEBUG");
		arguments.add("--profiles=obr");
		
		Map<Object, Object> toSave = new HashMap<Object, Object>();
		
		for (BundleEntry be : currentBundles) {
			StringBuffer options = new StringBuffer().append(be.isSelected()).append("@").append(be.isStart()).append("@")
					.append(be.getLevel()).append("@").append(be.isUpdate());
			toSave.put(be.getURL(), options.toString());
			
			if (be.isSelected()) {
				final StringBuffer provisionFrom = new StringBuffer(be.getURL());
				if (be.getLevel() >= 0) {
					provisionFrom.append("@").append(be.getLevel());
				}
				if (!be.isStart()) {
					provisionFrom.append("@nostart");
				}
				if (be.isUpdate()) {
					provisionFrom.append("@update");
				}
				arguments.add(provisionFrom.toString());
			}
		}
		
		configuration.setAttribute(Attribute.PROVISION_ITEMS, toSave);
		configuration.setAttribute(Attribute.RUN_ARGUMENTS, arguments);
		
		try {
			if (!configuration.hasAttribute("osgi_framework_id"))
				configuration.setAttribute("osgi_framework_id", "--platform=felix --version=2.0.1");
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			trySetAttribute(configuration, "append.args", true);
			trySetAttribute(configuration, "automaticAdd", true);
			trySetAttribute(configuration, "automaticValidate", false);
			trySetAttribute(configuration, "bootstrap", "");
			trySetAttribute(configuration, "checked", "");
			trySetAttribute(configuration, "default_start_level", 40);
			trySetAttribute(configuration, "clearConfig", false);
			// TODO
			trySetAttribute(configuration, "configLocation", "${workspace_loc}/rundir/smp.lighting");
			trySetAttribute(configuration, "default", true);
			trySetAttribute(configuration, "default_auto_start", true);
			trySetAttribute(configuration, "includeOptional", true);
			trySetAttribute(configuration, "org.eclipse.debug.core.source_locator_id",
					"org.eclipse.pde.ui.launcher.PDESourceLookupDirector");
			
			trySetAttribute(configuration, "org.eclipse.jdt.launching.JRE_CONTAINER",
					"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/J2SE-1.5");
			trySetAttribute(
					configuration,
					"org.eclipse.jdt.launching.PROGRAM_ARGUMENTS",
					"-console --obrRepositories=http://depot.universaal.org/nexus/content/repositories/snapshots/repository.xml,http://depot.universaal.org/nexus/content/repositories/releases/repository.xml,http://bundles.osgi.org/obr/browse?_xml=1&amp;amp;cmd=repository --org.ops4j.pax.url.mvn.repositories=+http://depot.universaal.org/nexus/content/groups/public,http://depot.universaal.org/nexus/content/repositories/snapshots@snapshots@noreleases --log=DEBUG");
			trySetAttribute(
					configuration,
					"org.eclipse.jdt.launching.VM_ARGUMENTS",
					"-Dosgi.noShutdown=true -Dfelix.log.level=4 -Dorg.universAAL.middleware.peer.is_coordinator=true -Dorg.universAAL.middleware.peer.member_of=urn:org.universAAL.aal_space:test_env -Dbundles.configuration.location=${workspace_loc}/rundir/confadmin");
			trySetAttribute(configuration, "org.eclipse.jdt.launching.WORKING_DIRECTORY", "${workspace_loc}/rundir/demo.config");
			trySetAttribute(configuration, "org.ops4j.pax.cursor.hotDeployment", false);
			trySetAttribute(configuration, "org.ops4j.pax.cursor.logLevel", "DEBUG");
			trySetAttribute(configuration, "org.ops4j.pax.cursor.overwrite", false);
			trySetAttribute(configuration, "org.ops4j.pax.cursor.overwriteSystemBundles", false);
			trySetAttribute(configuration, "org.ops4j.pax.cursor.overwriteUserBundles", false);
			trySetAttribute(configuration, "default", true);
			trySetAttribute(configuration, "default", true);
			
			if (!configuration.hasAttribute("org.ops4j.pax.cursor.profiles")) {
				ArrayList<Object> classpath = new ArrayList<Object>();
				classpath.add("obr");
				configuration.setAttribute("org.ops4j.pax.cursor.profiles", classpath);
			}
			
			trySetAttribute(configuration, "pde.version", "3.3");
			trySetAttribute(configuration, "show_selected_only", false);
			trySetAttribute(configuration, "tracing", false);
			trySetAttribute(configuration, "useCustomFeatures", false);
			trySetAttribute(configuration, "useDefaultConfigArea", false);
			
			configuration.removeAttribute("target_bundles");
			configuration.removeAttribute("workspace_bundles");
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private static void trySetAttribute(ILaunchConfigurationWorkingCopy configuration, String attribute, int value) throws CoreException {
		if (!configuration.hasAttribute(attribute))
			configuration.setAttribute(attribute, value);
	}
	
	private static void trySetAttribute(ILaunchConfigurationWorkingCopy configuration, String attribute, String value) throws CoreException {
		if (!configuration.hasAttribute(attribute))
			configuration.setAttribute(attribute, value);
	}
	
	private static void trySetAttribute(ILaunchConfigurationWorkingCopy configuration, String attribute, boolean value)
			throws CoreException {
		if (!configuration.hasAttribute(attribute))
			configuration.setAttribute(attribute, value);
	}
}
