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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.universaal.uaalpax.shared.Attribute;
import org.universaal.uaalpax.shared.MavenDependencyResolver;

public class BundleModel {
	/** Threshold for assuming that a particular version is used */
	private static final float VERSION_WEIGHT_THRESHOLD = 0.3f;
	
	public static final String UNKNOWN_VERSION = "Unknown";
	
	private BundleSet currentBundles;
	
	private List<BundlePresenter> allPresenters = new ArrayList<BundlePresenter>();
	
	private MavenDependencyResolver dependencyResolver;
	
	private UAALVersionProvider versionProvider;
	private String currentVersion;
	
	private ModelDialogProvider dialogProvider;
	
	private List<BundleChangeListener> changeListeners = new ArrayList<BundleChangeListener>();
	
	private ArtifactGraph artifactGraph;
	
	private ExecutorService graphExecutor;
	private Future<?> graphRebuildFuture;
	
	public BundleModel(MavenDependencyResolver depResolver, UAALVersionProvider versionProvider, ModelDialogProvider dialogProvider) {
		this.currentBundles = new BundleSet();
		this.dependencyResolver = depResolver;
		this.versionProvider = versionProvider;
		this.dialogProvider = dialogProvider;
		
		this.artifactGraph = new ArtifactGraph(depResolver);
		this.graphExecutor = Executors.newSingleThreadExecutor();
		this.graphRebuildFuture = null;
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
		cancelRebuildGraph();
		
		currentBundles.updateBundles(configuration);
		currentVersion = UNKNOWN_VERSION;
		for (String version : versionProvider.getAvailableVersions()) {
			if (containsAllBundlesOfVersion(currentBundles, version)) {
				currentVersion = version;
				break;
			}
		}
		
		rebuildGraphInBackground();
		
		updatePresenters();
	}
	
	private void rebuildGraphInBackground() {
		cancelRebuildGraph();
		
		graphRebuildFuture = graphExecutor.submit(new Runnable() {
			public void run() {
				System.out.println("rebuilding graph...");
				artifactGraph.rebuildFromSetInBackground(currentBundles);
				System.out.println("rebuilding graph finished");
			}
		});
	}
	
	private void cancelRebuildGraph() {
		if (graphRebuildFuture != null) {
			graphRebuildFuture.cancel(true);
			graphRebuildFuture = null;
		}
	}
	
	private void waitGraph() {
		if (graphRebuildFuture != null) {
			if (!graphRebuildFuture.isDone()) {
				ProgressMonitorDialog d = new ProgressMonitorDialog(dialogProvider.getShell());
				try {
					d.run(false, false, new IRunnableWithProgress() {
						
						public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
							monitor.beginTask("Dependency graph", IProgressMonitor.UNKNOWN);
							monitor.subTask("Building dependency graph of launch config bundles...");
							
							try {
								graphRebuildFuture.get();
							} catch (InterruptedException e) {
							} catch (ExecutionException e) {
							} catch (CancellationException e) {
							}
							
							monitor.done();
						}
					});
				} catch (InvocationTargetException e1) {
				} catch (InterruptedException e1) {
				}
			}
		}
		
		graphRebuildFuture = null;
	}
	
	public void removeAll(Set<BundleEntry> entries) {
		waitGraph();
		removeUnneededDependencies(entries);
		
		updatePresenters();
	}
	
	public void remove(BundleEntry be) {
		waitGraph();
		removeNoUpdate(be);
		updatePresenters();
	}
	
	public void removeNoUpdate(BundleEntry be) {
		waitGraph();
		Set<BundleEntry> bes = new HashSet<BundleEntry>();
		bes.add(be);
		removeUnneededDependencies(bes);
	}
	
	public void add(BundleEntry e) {
		waitGraph();
		insertBundleAndDeps(e);
		updatePresenters();
	}
	
	public void addAll(Collection<BundleEntry> entries) {
		waitGraph();
		for (BundleEntry be : entries)
			insertBundleAndDeps(be);
		updatePresenters();
	}
	
	public BundleSet getBundles() {
		return currentBundles;
	}
	
	private void insertBundleAndDeps(BundleEntry be) {
		waitGraph();
		
		while (true) {
			Artifact a = be.toArtifact();
			boolean insterted = false;
			
			if (a != null) {
				try {
					DependencyNode deps = dependencyResolver.resolveDependencies(a);
					// ConsoleDependencyGraphDumper dumper = new ConsoleDependencyGraphDumper();
					// deps.accept(dumper);
					
					Set<ArtifactURL> depList = listDependencies(deps, null);
					String approxVersion = checkVersion(depList);
					
					if (approxVersion != null) {
						// no version is set, ask if one should be set
						if (getCurrentVersion() == UNKNOWN_VERSION) {
							int sel = dialogProvider.openDialog("Set uAAL version",
									"You have many bundles which are used by universAAL version " + approxVersion
											+ ", but the version of this run config is not set. Do you want to set it to version "
											+ approxVersion + "?", new String[] { "Yes", "Ignore" });
							
							if (sel == 0) {
								// will always go right since no version is set yet
								changeToVersion(approxVersion);
							}
						}
						// version is set and differs from approximated one
						else if (!getCurrentVersion().equals(approxVersion)) {
							int sel = dialogProvider.openDialog("Version conflict", "The bundle \"" + be.getLaunchUrl()
									+ "\" which you want to add depends on " + "uAAL version " + approxVersion
									+ ", but the current run config version is " + getCurrentVersion()
									+ ". Do you really want to add this bundle with its all depencencies?", new String[] { "No", "Yes",
									"Yes, but without depencencies" });
							
							if (sel == 0) // No
								return;
							else if (sel == 2) { // without depencencies
								currentBundles.add(be);
								return;
							} // otherwise add with all depencencies
						}
					}
					
					insertDependencies(deps /* , 1 */); // deps contains already 'be' as root, so no need to insert it second time
					
					artifactGraph.insertDependencyNode(deps, null); // update graph
					insterted = true;
				} catch (DependencyCollectionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TimeoutException e) {
					System.out.println("Resolution timeout");
				}
			}
			
			if (!insterted) {
				int ret = dialogProvider.openDialog("Error during depencency resolution",
						"There was an error resolvin depencencies for bundle " + be.getLaunchUrl() + ". ", "Ignore", "Retry", "Cancel");
				
				if (ret == 0) // ignore
					currentBundles.add(be);
				else if (ret == 1) // retry
					continue;
				// else break and do nothing
			}
			
			break;
		}
	}
	
	private Set<ArtifactURL> listDependencies(DependencyNode node, Set<ArtifactURL> deps) {
		if (deps == null)
			deps = new HashSet<ArtifactURL>();
		
		Dependency d = node.getDependency();
		if (d != null)
			deps.add(BundleEntry.artifactUrlFromArtifact(d.getArtifact()));
		
		for (DependencyNode child : node.getChildren())
			listDependencies(child, deps);
		
		return deps;
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
	
	private Artifact checkCoreToOsgi(Artifact a) {
		if (a.getArtifactId().endsWith(".core")) {
			// System.out.println("renaming artifact from " + a);
			Artifact osgi = new DefaultArtifact(a.getGroupId(), a.getArtifactId().substring(0, a.getArtifactId().length() - 5)
					.concat(".osgi"), a.getExtension(), a.getBaseVersion());
			osgi = dependencyResolver.resolveArtifact(osgi);
			// System.out.println("to " + osgi);
			if (osgi != null)
				return osgi;
		}
		return a;
	}
	
	/**
	 * @param node
	 * @param minStartLevel
	 *            min start level of child bundles, begin at 1 on very upper call
	 * @return start level of the node, i.e. dependent bundles have to start at a higher level
	 */
	private int insertDependencies(DependencyNode node /* , int minStartLevel */) {
		int minStartLevel = 1;
		
		// traverse postorder
		for (DependencyNode child : node.getChildren())
			minStartLevel = Math.max(minStartLevel, insertDependencies(child /* , minStartLevel */));
		
		Dependency d = node.getDependency();
		if (d != null) {
			Artifact a = d.getArtifact();
			a = checkCoreToOsgi(a);
			ArtifactURL url = BundleEntry.artifactUrlFromArtifact(a);
			
			// check if bundle is already included
			BundleEntry be = currentBundles.find(url);
			if (be != null) {
				minStartLevel = Math.max(minStartLevel, be.getLevel());
			} else {
				minStartLevel++;
				
				ArtifactURL aurl = BundleEntry.artifactUrlFromArtifact(a);
				if (versionProvider.isIgnoreArtifactOfVersion(currentVersion, aurl))
					return minStartLevel;
				
				currentBundles.add(new BundleEntry(a, minStartLevel));
			}
		}
		
		return minStartLevel;
	}
	
	private void removeUnneededDependencies(Set<BundleEntry> entries) {
		// ArtifactNode aNode = artifactGraph.getNode(be.getStringUrl());
		// if (aNode == null)
		// return; // TODO ok???
		//
		// Set<String> urls = artifactGraph.removeArtifact(be, versionProvider.getBundlesOfVersion(currentVersion));
		//
		// Set<Artifact> arts = new HashSet<Artifact>();
		// for (BundleEntry be : bes) {
		// Artifact beArt = be.toArtifact();
		// if (beArt != null)
		// arts.add(beArt);
		// }
		
		while (true) {
			Set<ArtifactURL> willBeRemoved = artifactGraph.checkCanRemove(entries);
			
			if (willBeRemoved != null && !willBeRemoved.isEmpty()) {
				BundleSet toRemove = new BundleSet();
				
				StringBuilder sb = new StringBuilder("Following bundles depend on the bundles to remove:\n\n");
				
				for (BundleEntry be : currentBundles) {
					if (willBeRemoved.contains(be.getArtifactUrl())) {
						toRemove.add(be);
						sb.append(be.getLaunchUrl()).append("\n");
					}
				}
				
				sb.append("\nHow to proceed?");
				
				int ret = dialogProvider.openDialog("Error while removing the bundles", sb.toString(), "Remove them all", "Cancel");
				
				if (ret == 0) { // remove all
					entries = new HashSet<BundleEntry>(entries);
					for (BundleEntry be : toRemove)
						entries.add(be);
					
					continue;
				}
			} else {
				Set<ArtifactURL> removed = artifactGraph.removeArtifacts(entries, versionProvider.getBundlesOfVersion(currentVersion));
				for (Iterator<BundleEntry> iter = currentBundles.iterator(); iter.hasNext();) {
					BundleEntry be = iter.next();
					if (removed.contains(be.getArtifactUrl()) || entries.contains(be))
						iter.remove();
				}
			}
			
			break;
		}
	}
	
	// private void removeUnneededDependencies(Collection<BundleEntry> bes) {
	// Set<Artifact> arts = new HashSet<Artifact>();
	// for (BundleEntry be : bes) {
	// Artifact beArt = be.toArtifact();
	// if (beArt != null)
	// arts.add(beArt);
	// }
	//
	// try {
	// // find out all dependencies of be (-> beDeps)
	// Set<String> beDeps = listDependencies(dependencyResolver.resolve(arts), null);
	//
	// BundleSet mwBundles = getMiddlewareBundles();
	// if (mwBundles == null)
	// mwBundles = new BundleSet();
	//
	// // find out dependencies of all bundles in model except those of
	// // in beDeps but not in current version bundles (-> otherDeps)
	// Set<Artifact> otherArts = new HashSet<Artifact>();
	// for (BundleEntry oe : currentBundles) {
	// // !mwBundles.containsURL is to add mw bundles to otherDeps set,
	// // even if the seem only be needed for be
	// if (beDeps.contains(oe.getLaunchUrl()) && !mwBundles.containsURL(oe.getLaunchUrl()))
	// continue;
	//
	// Artifact a = oe.toArtifact();
	// if (a != null)
	// otherArts.add(a);
	// }
	//
	// Set<String> otherDeps = listDependencies(dependencyResolver.resolve(otherArts), null);
	//
	// // find out which bundles in model are only dependencies of be,
	// // but not of any other bundle (-> unneededBundles as url set)
	// Set<String> unneededBundles = new HashSet<String>();
	// for (String d : beDeps)
	// if (!otherDeps.contains(d))
	// unneededBundles.add(d);
	//
	// // remove unneeded bundles from model
	// for (String url : unneededBundles)
	// currentBundles.removeURL(url);
	// } catch (DependencyCollectionException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (TimeoutException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	
	public void updatePresenters() {
		BundleSet projects = currentBundles;
		for (BundlePresenter presenter : allPresenters)
			projects = presenter.updateProjectList(projects);
	}
	
	private boolean containsAllBundlesOfVersion(BundleSet launchProjects, String version) {
		BundleSet versionBundles = versionProvider.getBundlesOfVersion(version);
		if (versionBundles == null)
			return false;
		
		for (ArtifactURL url : versionBundles.allURLs())
			if (!launchProjects.containsURL(url))
				return false;
		
		return true;
	}
	
	public void changeToVersion(String newVersion) {
		waitGraph();
		
		BundleSet oldBS = versionProvider.getBundlesOfVersion(currentVersion);
		if (oldBS != null)
			for (BundleEntry be : oldBS)
				currentBundles.remove(be);
		
		for (Iterator<BundleEntry> iter = currentBundles.iterator(); iter.hasNext();) {
			BundleEntry be = iter.next();
			if (versionProvider.isIgnoreArtifactOfVersion(newVersion, be.getArtifactUrl()))
				iter.remove();
		}
		
		// assume that the levels fit
		BundleSet newBS = versionProvider.getBundlesOfVersion(newVersion);
		if (newBS != null)
			for (BundleEntry be : newBS)
				currentBundles.add(be);
		
		currentVersion = newVersion;
		
		rebuildGraphInBackground();
		notifyChanged();
		updatePresenters();
	}
	
	public boolean checkCompatibleWithVersion(String version, ArtifactURL url) {
		BundleSet bundles = versionProvider.getBundlesOfVersion(version);
		if (bundles == null || bundles.containsURL(url)) // url is in bundles for current version
			return true;
		
		for (String v : versionProvider.getAvailableVersions()) {
			if (version.equals(v))
				continue;
			
			BundleSet otherBundles = versionProvider.getBundlesOfVersion(v);
			if (otherBundles != null && otherBundles.containsURL(url))
				return false; // bundles of an other version contains this url
								// but not bundles of current version
		}
		
		return true;
	}
	
	public Set<ArtifactURL> getIncompatibleProjects(String newVersion) {
		// find out which projects have to be checked for compatibility
		Set<ArtifactURL> toCheck = new HashSet<ArtifactURL>();
		BundleSet versionBundles = versionProvider.getBundlesOfVersion(currentVersion);
		// fill toCheck with all bundles except of those in current version set
		if (versionBundles != null) {
			for (ArtifactURL url : currentBundles.allURLs())
				if (!versionBundles.containsURL(url))
					toCheck.add(url);
		} else
			for (ArtifactURL url : currentBundles.allURLs())
				toCheck.add(url);
		
		// check all toCheck project for compatibility with new version
		for (Iterator<ArtifactURL> iter = toCheck.iterator(); iter.hasNext();)
			if (checkCompatibleWithVersion(newVersion, iter.next()))
				iter.remove();
		
		return toCheck;
	}
	
	public boolean checkCompatibleWithCurrentVersion(ArtifactURL url) {
		return checkCompatibleWithVersion(currentVersion, url);
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
			toSave.put(be.getLaunchUrl().url, options.toString());
			
			if (be.isSelected()) {
				final StringBuffer provisionFrom = new StringBuffer(be.getLaunchUrl().url);
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
	
	private String checkVersion(Set<ArtifactURL> deps) {
		String maxVersion = null;
		
		float maxWeight = 0;
		for (String version : versionProvider.getAvailableVersions()) {
			float weight = versionProvider.getVersionScore(version, deps);
			if (weight > maxWeight) {
				maxWeight = weight;
				maxVersion = version;
			}
		}
		
		if (maxWeight < VERSION_WEIGHT_THRESHOLD)
			maxVersion = null;
		
		return maxVersion;
	}
}
