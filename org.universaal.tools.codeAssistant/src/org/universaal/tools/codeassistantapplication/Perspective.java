package org.universaal.tools.codeassistantapplication;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class Perspective implements IPerspectiveFactory {

	private static final String ID = "org.universAAL.codeassistant.perspective";
	
	public void createInitialLayout(IPageLayout layout) {
		defineLayout(layout);
		//layout.setEditorAreaVisible(true);
		//layout.setFixed(false);
	}
	
	public void defineLayout(IPageLayout layout) {
		System.out.println("---DEFINE LAYOUT---");
		String editorArea = layout.getEditorArea();   
		IFolderLayout topLeft =layout.createFolder("topLeft", IPageLayout.LEFT, (float) 0.25f, editorArea);
		topLeft.addPlaceholder("org.universAAL.codeassistant.CodeAssistantView");
	}

}
