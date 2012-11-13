package org.universaal.tools.conformanceTools.markers;

import org.eclipse.core.resources.IMarker;

public interface MarkerListener {

	public void newMarker(IMarker marker);
	
	public void deletedMarker(IMarker marker);
}