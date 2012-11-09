package org.universaal.tools.conformanceTools.markers;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public interface CTMarker {

	public final String ID = "org.universaal.tools.conformanceTools.ctMarker"; // as in plugin.xml

	public Integer createMarker(IProject p, IResource resource, Map<String, Object> attributes/*, String type*/) throws CoreException;

	public Integer createMarker(IProject p, IResource resource, Map<String, Object> attributes, /*String type,*/ boolean saveWithWorkspace) throws CoreException;

	public void deleteMarker(IProject project, int id) throws CoreException;

	public IMarker[] getMarkers(IProject project);

	public void deleteAll(IProject project) throws CoreException;

	public void deleteAll() throws CoreException;

	public boolean subscribe(NewCTmarkerListener listener);

	public boolean unsubscribe(NewCTmarkerListener listener);
}