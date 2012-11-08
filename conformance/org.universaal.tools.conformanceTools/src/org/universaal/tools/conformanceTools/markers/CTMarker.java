package org.universaal.tools.conformanceTools.markers;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public interface CTMarker {

	// allowed ones
	public final String BOOKMARK = "IMarker.BOOKMARK";
	public final String TASK = "IMarker.TASK";
	public final String PROBLEM = "IMarker.PROBLEM";

	public Integer createMarker(IProject p, IResource resource, Map<String, Object> attributes, String type) throws CoreException;

	public Integer createMarker(IProject p, IResource resource, Map<String, Object> attributes, String type, boolean saveWithWorkspace) throws CoreException;

	public void deleteMarker(IProject project, int id) throws CoreException;

	public IMarker[] getMarkers(IProject project);

	public void deleteAll(IProject project) throws CoreException;

	public void deleteAll() throws CoreException;
}