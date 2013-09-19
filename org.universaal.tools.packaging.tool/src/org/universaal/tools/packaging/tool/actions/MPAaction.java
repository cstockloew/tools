package org.universaal.tools.packaging.tool.actions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

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
import org.universaal.tools.packaging.tool.util.Configurator;

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
	private Boolean recovered = false;
	
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchWindow w = HandlerUtil.getActiveWorkbenchWindow(event);
		List<IProject> parts = new ArrayList<IProject>();
		String recFile = org.universaal.tools.packaging.tool.Activator.tempDir + Configurator.local.getRecoveryFileName();
		String recParts = org.universaal.tools.packaging.tool.Activator.tempDir + Configurator.local.getRecoveryPartsName();
		
		if ( Configurator.local.isPersistanceEnabled()) {
			System.out.println("Searching for recovery file "+ recFile);
			File recovery = new File(recFile);
			if(recovery.exists()){
				System.out.println("Found It");
				System.out.println("Searching for recovery parts file "+ recParts);
				File recoveryParts = new File(recParts);
				if(recoveryParts.exists()){
					System.out.println("Found It");
					Boolean tryRecover = MessageDialog.openConfirm(w.getShell(),
							"Recovery", "A previous operation has been cancelled.\n\nWould you like to recover it ?");
					if(tryRecover){
					
						try{
							FileInputStream fis = new FileInputStream(recParts);
				            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
				         
				            String line = reader.readLine();
				            
				            while(line != null){
				                if(!line.trim().isEmpty()){
									System.out.println("Importing part "+line);
									IContainer container = ResourcesPlugin.getWorkspace().getRoot().getProject(line);
									parts.add(container.getProject());
								}
				                line = reader.readLine();
							}
							this.recovered = true;
						} catch (IOException e){
							e.printStackTrace();
						}
					} else {
						this.recovered = false;
					}
				}
			}

		}
		
		if(!this.recovered){
			FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(w.getShell(), true, ResourcesPlugin.getWorkspace().getRoot(), IResource.PROJECT);
			dialog.setTitle("Resources Selection");
			dialog.setMessage("Please select the universAAL projects you want to include in the UAPP container");
			dialog.setInitialPattern("?");
			dialog.open();
			
			String partsFileContent = "";
			
			if(dialog.getResult() != null){
				for(int i = 0; i < dialog.getResult().length; i++){
					String[] segments = dialog.getResult()[i].toString().split("/");
					//System.out.println(segments[segments.length-1]);
					IContainer container = ResourcesPlugin.getWorkspace().getRoot().getProject(segments[segments.length-1]);
					parts.add(container.getProject());
					partsFileContent = partsFileContent + segments[segments.length-1] + System.getProperty("line.separator");
				}
	
				if(Configurator.local.isPersistanceEnabled()){
					try {
						File f = new File(org.universaal.tools.packaging.tool.Activator.tempDir);
						if(!f.exists()) f.mkdir();
						BufferedWriter bw = new BufferedWriter(new FileWriter(recParts));
						bw.write(partsFileContent);
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			} else{
				MessageDialog.openInformation(w.getShell(),
						"Application Packager", "Please verify the selection of parts.");
			}
		}
		
		if(parts.size()>0){
			gui = new GUI(parts, this.recovered);		
			WizardDialogMod wizardDialog = new WizardDialogMod(w.getShell(), gui);
			wizardDialog.open();
		}
		
		return null;
	}
	
}