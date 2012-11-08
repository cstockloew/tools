package org.universaal.tools.conformanceTools.markers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class Markers implements CTMarker {

	private Map<IProject, List<IMarker>> markers;

	public Markers(){
		markers = new HashMap<IProject, List<IMarker>>();
	}

	@Override
	public Integer createMarker(IProject p, IResource resource,	Map<String, Object> attributes, String type, boolean saveWithWorkspace) throws CoreException {

		if(!projectExist(p))
			addNewProject(p);

		IMarker marker = resource.createMarker(type);
		if(marker.exists()){
			Iterator<Entry<String, Object>> it = attributes.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Object> attribute = it.next();
				marker.setAttribute(attribute.getKey(), attribute.getValue());
			}
			marker.setAttribute(IMarker.TRANSIENT, !saveWithWorkspace); // not saved when the workspace is saved
			marker.setAttribute(IMarker.USER_EDITABLE, false);

			marker.setAttribute(IMarker.SOURCE_ID, "uAAL_CT");

			int id = markers.get(p).size();
			markers.get(p).add(id, marker);
			return id;
		}

		return null;
	}

	public Integer createMarker(IProject p, IResource resource, Map<String, Object> attributes, String type) throws CoreException {
		return createMarker(p, resource, attributes, type, false);
	}

	private boolean projectExist(IProject p){
		if(markers.get(p) != null)
			return true;

		return false;
	}

	private void addNewProject(IProject p){
		markers.put(p, new ArrayList<IMarker>());
	}

	@Override
	public void deleteMarker(IProject project, int id) throws CoreException {
		if(projectExist(project)){
			if(id < markers.get(project).size()){
				markers.get(project).get(id).delete();
				markers.get(project).remove(id);
			}
		}
	}

	public IMarker[] getMarkers(IProject project) {
		if(projectExist(project))
			return markers.get(project).toArray(new IMarker[0]);
		else
			return null;
	}

	public void deleteAll(IProject project) throws CoreException {

		if(projectExist(project)){
			List<IMarker> ms = markers.get(project);
			for(int i = 0; i < ms.size(); i++)
				ms.get(i).delete();
			markers.get(project).clear();
		}
	}

	public void deleteAll() throws CoreException{
		Iterator<Entry<IProject, List<IMarker>>> it = markers.entrySet().iterator();
		while(it.hasNext()){
			Entry<IProject, List<IMarker>> projectMarkers = it.next();
			for(int i = 0; i < projectMarkers.getValue().size(); i++)
				projectMarkers.getValue().get(i).delete();
		}

		markers = new HashMap<IProject, List<IMarker>>();
	}	
}