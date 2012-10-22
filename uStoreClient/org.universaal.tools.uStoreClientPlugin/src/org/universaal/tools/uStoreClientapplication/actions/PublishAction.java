package org.universaal.tools.uStoreClientapplication.actions;

import java.text.DateFormat;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.universaal.commerce.ustore.tools.*;
import org.universaal.tools.uStoreClientapplication.wizzard.PublishWizard;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class PublishAction implements IWorkbenchWindowActionDelegate {


	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public PublishAction() {

	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try {

			
			PublishWizard publishWizard=new PublishWizard();
			WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					publishWizard);
			if (wizardDialog.open() == Window.OK) {
				AALApplicationManagerServiceLocator loc = new AALApplicationManagerServiceLocator();
				AALApplicationManager man = loc.getAALApplicationManagerPort();
				Metadata metadata=publishWizard.getMetadata();			
				
				
				
				System.out.println(man
						.addApplication(metadata.getUsername(),
								metadata.getPassword(),
								metadata.getApplicationName(),
								metadata.getApplicationShortDescription(),
								metadata.getApplicationFullDescription(),
								metadata.getKeywords(),
								metadata.getManufacturer(),
								metadata.getManufacturerPartNumber(),
								metadata.getApplicationURL(),
								metadata.getCategory(),
								metadata.getFullImage(),
								metadata.getThumbnail(),
								metadata.getListPrice(),
								metadata.getGroupId(),
								metadata.getArtifactId(),
								metadata.getVersion(),
								metadata.getFileName(),
								metadata.getFile(),
								metadata.getHardwareRequirements(),
								metadata.getSoftwareRequirements(),
								metadata.getDeveloperContactDetails(),
								metadata.getUploadTimeToNexus()));
								
			} else {
				System.out.println("Canceled");
			   }
//			MetadataDialog dia = new MetadataDialog(PlatformUI.getWorkbench()
//					.getActiveWorkbenchWindow().getShell(), 0);
//			dia.open();
//			if (!dia.isCanceled()) {
//				java.util.Calendar date = java.util.Calendar.getInstance();
//				AALApplicationManagerServiceLocator loc = new AALApplicationManagerServiceLocator();
//				AALApplicationManager man = loc.getAALApplicationManagerPort();
//				Metadata metadata=dia.getMetadata();			
//				
//				
//				
//				System.out.println(man
//						.addApplication(metadata.getUsername(),
//								metadata.getPassword(),
//								metadata.getApplicationName(),
//								metadata.getApplicationShortDescription(),
//								metadata.getApplicationFullDescription(),
//								metadata.getKeywords(),
//								metadata.getManufacturer(),
//								metadata.getManufacturerPartNumber(),
//								metadata.getApplicationURL(),
//								metadata.getCategory(),
//								metadata.getFullImage(),
//								metadata.getThumbnail(),
//								metadata.getListPrice(),
//								metadata.getGroupId(),
//								metadata.getArtifactId(),
//								metadata.getVersion(),
//								metadata.getFileName(),
//								metadata.getFile(),
//								metadata.getHardwareRequirements(),
//								metadata.getSoftwareRequirements(),
//								metadata.getDeveloperContactDetails(),
//								//metadata.getUploadTimeToNexus()));
//								date));
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

}