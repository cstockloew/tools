package org.universaal.tools.configurationExtractor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.universaal.tools.configurationExtractor.view.CEView;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class ExtractorAction implements IWorkbenchWindowActionDelegate {
	
    public void init(IWorkbenchWindow window) {
    }
    
    public void dispose() {}

    public void run(IAction action) {
        try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(CEView.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
    }
    
    public void selectionChanged(IAction action, ISelection selection) {
    	
    }
	
	
}