package org.universaal.tools.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IFolderLayout;

public class AALStudioPerspective implements IPerspectiveFactory {

	/**
	 * Creates the initial layout for a page.
	 */
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		addFastViews(layout);
		addViewShortcuts(layout);
		addPerspectiveShortcuts(layout);
		{
			IFolderLayout folderLayout = layout.createFolder("folder", IPageLayout.LEFT, 0.28f, IPageLayout.ID_EDITOR_AREA);
			folderLayout.addView("org.eclipse.jdt.ui.PackageExplorer");
			folderLayout.addView("org.eclipse.ui.views.ContentOutline");
			folderLayout.addView("org.eclipse.papyrus.modelexplorer.modelexplorer");
		}
		{
			IFolderLayout folderLayout = layout.createFolder("folder_1", IPageLayout.BOTTOM, 0.65f, IPageLayout.ID_EDITOR_AREA);
			folderLayout.addView("org.universaal.tools.dashboard.views.DashboardView");
			folderLayout.addView("org.eclipse.ui.views.PropertySheet");
			folderLayout.addView("org.eclipse.ui.views.ProblemView");
			folderLayout.addView("org.eclipse.pde.runtime.LogView");
			folderLayout.addView("org.eclipse.ui.console.ConsoleView");
		}
	}

	/**
	 * Add fast views to the perspective.
	 */
	private void addFastViews(IPageLayout layout) {
	}

	/**
	 * Add view shortcuts to the perspective.
	 */
	private void addViewShortcuts(IPageLayout layout) {
	}

	/**
	 * Add perspective shortcuts to the perspective.
	 */
	private void addPerspectiveShortcuts(IPageLayout layout) {
	}

}
