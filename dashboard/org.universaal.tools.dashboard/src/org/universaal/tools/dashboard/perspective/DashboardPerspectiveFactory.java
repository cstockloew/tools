package org.universaal.tools.dashboard.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DashboardPerspectiveFactory implements IPerspectiveFactory {
	
	/**
	 * Just to keep it simple.
	 */
	private static final String OVERVIEW_VIEW = "org.universaal.tools.dashboard.views.OverviewView";
	private static final String MONITORING_VIEW = "org.universaal.tools.dashboard.views.MonitoringView";
	private static final String STATEMACHINE_VIEW = "org.universaal.tools.dashboard.views.StatemachineView";
	
	/**
	 * This method creates the initial layout of the perspective. Views are added to the perspective; around the editor, which is obligatory.
	 * Make sure that the different _VIEW strings correspond exactly to the id in the plugin.xml; or all hell breaks loose.
	 * 
	 * Also make sure that when compiling and running this plug-in via the button in the "overview" the "clean workspace" flag is set!
	 */
	
	public void createInitialLayout(IPageLayout myLayout) {
		//We might wish to fiddle around with the layout. I don't think that the ratio behaves as I would have expected.
		myLayout.addView(STATEMACHINE_VIEW, IPageLayout.BOTTOM, 0.80f, myLayout.getEditorArea());
//		myLayout.addView(IPageLayout.ID_PROJECT_EXPLORER, IPageLayout.LEFT, 0.20f, myLayout.getEditorArea());
		myLayout.addView(OVERVIEW_VIEW, IPageLayout.LEFT, 0.20f, myLayout.getEditorArea());
		myLayout.addView(MONITORING_VIEW, IPageLayout.RIGHT, 0.80f, myLayout.getEditorArea());
	}
}
