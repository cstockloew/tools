package org.universaal.tools.conformanceTools.markers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.universaal.tools.conformanceTools.utils.RunPlugin;

public class Markers implements CTMarker {

	private Map<IProject, List<IMarker>> markers;
	private static Markers instance = null;
	private Collection<MarkerListener> listeners;

	private Markers(){
		markers = new HashMap<IProject, List<IMarker>>();
		listeners = new ArrayList<MarkerListener>();
	}

	public static synchronized Markers getInstance(){
		if(instance == null)
			instance = new Markers();

		return instance;
	}

	@Override
	public synchronized Integer createMarker(IProject p, IResource resource, Map<String, Object> attributes, boolean saveWithWorkspace) throws CoreException {

		if(!projectExist(p))
			addNewProject(p);

		IMarker marker = resource.createMarker(CTMarker.ID);

		try{
			System.out.println("uAAL CT - generating marker for "+resource.getName()+" at line "+attributes.get(IMarker.LINE_NUMBER)+" for "+attributes.get(IMarker.MESSAGE));
		}
		catch(Exception ex){}

		if(marker.exists()){
			Iterator<Entry<String, Object>> it = attributes.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Object> attribute = it.next();
				marker.setAttribute(attribute.getKey(), attribute.getValue());
			}
			marker.setAttribute(IMarker.TRANSIENT, !saveWithWorkspace); // not saved when the workspace is saved
			marker.setAttribute(IMarker.USER_EDITABLE, false);
			marker.setAttribute(IMarker.DONE, false);

			int id = markers.get(p).size();
			markers.get(p).add(id, marker);

			notifyNewMarker(marker);

			return id;
		}

		return null;
	}

	public synchronized Integer createMarker(IProject p, IResource resource, Map<String, Object> attributes) throws CoreException {
		return createMarker(p, resource, attributes, false);
	}

	private boolean projectExist(IProject p){
		if(markers.get(p) != null)
			return true;

		return false;
	}

	private void addNewProject(IProject p){
		markers.put(p, new ArrayList<IMarker>());
	}

	public synchronized IMarker[] getMarkers(IProject project) {
		if(projectExist(project))
			return markers.get(project).toArray(new IMarker[0]);
		else
			return null;
	}

	@Override
	public synchronized void deleteMarker(IProject project, int id) throws CoreException {
		if(projectExist(project)){
			if(id < markers.get(project).size()){
				notifyDeletedMarker(markers.get(project).get(id));
				markers.get(project).get(id).delete();
				markers.get(project).set(id, null);

				System.out.println("uAAL CT - deleted marker "+id);
			}
		}
	}

	@Override
	public synchronized void deleteMarker(IMarker marker) throws CoreException {

		if(marker.exists()){
			Iterator<Entry<IProject, List<IMarker>>> it = markers.entrySet().iterator();		
			while(it.hasNext()){
				Entry<IProject, List<IMarker>> current = it.next();
				for(int i = 0; i < current.getValue().size(); i++){
					if(current.getValue().get(i) != null && current.getValue().get(i).equals(marker)){
						current.getValue().set(i, null);
						break;
					}
				}
			}

			notifyDeletedMarker(marker);
			marker.delete();
			System.out.println("uAAL CT - deleted a marker.");
		}
	}

	public synchronized void deleteAll(IProject project) throws CoreException {

		int k = 0;
		if(projectExist(project)){
			List<IMarker> ms = markers.get(project);
			for(int i = 0; i < ms.size(); i++){
				if(ms.get(i) != null){
					ms.get(i).delete();
					notifyDeletedMarker(ms.get(i));
					k++;
				}
			}

			System.out.println("uAAL CT - deleted "+k+" markers for project "+project.getName());
			markers.get(project).clear();
		}
	}

	@Override
	public synchronized void deleteAll(RunPlugin plugin) throws CoreException {

		int k = 0;
		Iterator<Entry<IProject, List<IMarker>>> it = markers.entrySet().iterator();		
		while(it.hasNext()){
			Entry<IProject, List<IMarker>> current = it.next();
			for(int i = 0; i < current.getValue().size(); i++){
				if(current.getValue().get(i) != null && current.getValue().get(i).getAttribute(IMarker.SOURCE_ID).equals(plugin)){
					notifyDeletedMarker(current.getValue().get(i));
					current.getValue().get(i).delete();					
					current.getValue().set(i, null);
					k++;
				}
			}
		}

		System.out.println("uAAL CT - deleted "+k+" markers related to plugin "+plugin);
	}

	public synchronized void deleteAll() throws CoreException{
		Iterator<Entry<IProject, List<IMarker>>> it = markers.entrySet().iterator();
		while(it.hasNext()){
			Entry<IProject, List<IMarker>> projectMarkers = it.next();
			for(int i = 0; i < projectMarkers.getValue().size(); i++){
				if(projectMarkers.getValue().get(i) != null){
					projectMarkers.getValue().get(i).delete();				
					notifyDeletedMarker(projectMarkers.getValue().get(i));
				}
			}
		}

		markers = new HashMap<IProject, List<IMarker>>();
		System.out.println("uAAL CT - deleted all markers on all projects.");
	}

	@Override
	public synchronized boolean subscribe(MarkerListener listener) {
		return listeners.add(listener);
	}

	@Override
	public synchronized boolean unsubscribe(MarkerListener listener) {
		Iterator<MarkerListener> it = listeners.iterator();
		while(it.hasNext()){
			if(it.next().equals(listener)){
				it.remove();
				return true;
			}
		}
		return false;
	}

	private void notifyNewMarker(IMarker marker){
		Iterator<MarkerListener> it = listeners.iterator();
		while(it.hasNext())
			it.next().newMarker(marker);
	}

	private void notifyDeletedMarker(IMarker marker){
		Iterator<MarkerListener> it = listeners.iterator();
		while(it.hasNext())
			it.next().deletedMarker(marker);
	}
}