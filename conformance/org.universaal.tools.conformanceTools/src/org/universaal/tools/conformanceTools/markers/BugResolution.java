package org.universaal.tools.conformanceTools.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

public class BugResolution implements IMarkerResolutionGenerator2 {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		Solution s = new Solution("uAAL CT, double-click to ignore - "+marker.getAttribute(IMarker.MESSAGE, ""));
		IMarkerResolution[] ret = new IMarkerResolution[1];
		ret[0] = s;

		return ret;
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		if(marker != null)
			return true;

		return false;
	}

	public class Solution implements IMarkerResolution{

		private String label;

		public Solution(String label){
			this.label = label;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public void run(IMarker marker) {
			try {
				marker.delete();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}