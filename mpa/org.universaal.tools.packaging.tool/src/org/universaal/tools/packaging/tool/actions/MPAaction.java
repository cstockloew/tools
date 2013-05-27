package org.universaal.tools.packaging.tool.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.universaal.tools.packaging.tool.api.WizardDialogMod;
import org.universaal.tools.packaging.tool.gui.GUI;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate 
 */
public class MPAaction extends AbstractHandler {

	public GUI gui;

	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchWindow w = HandlerUtil.getActiveWorkbenchWindow(event);
		List<IProject> parts = new ArrayList<IProject>();

		FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(w.getShell(), true, ResourcesPlugin.getWorkspace().getRoot(), IResource.PROJECT);
		dialog.setTitle("Resources Selection");
		dialog.setMessage("Please select the universAAL projects you want to include in the UAPP container");
		dialog.setInitialPattern("?");
		dialog.open();

		if(dialog.getResult() != null){
			for(int i = 0; i < dialog.getResult().length; i++){
				String[] segments = dialog.getResult()[i].toString().split("/");
				IContainer container = ResourcesPlugin.getWorkspace().getRoot().getProject(segments[segments.length-1]);
				parts.add(container.getProject());
			}

			gui = new GUI(parts);		
			WizardDialogMod wizardDialog = new WizardDialogMod(w.getShell(), gui);
			wizardDialog.open();
		}
		else{
			MessageDialog.openInformation(w.getShell(),
					"Application Packager", "Please verify the selection of parts.");
		}

		return null;
	}
}