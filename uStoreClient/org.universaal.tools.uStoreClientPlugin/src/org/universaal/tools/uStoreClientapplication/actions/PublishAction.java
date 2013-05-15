package org.universaal.tools.uStoreClientapplication.actions;

import java.text.DateFormat;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
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

			PublishWizard publishWizard = new PublishWizard(null, null, null);
			WizardDialog wizardDialog = new WizardDialog(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getShell(),
					publishWizard);
			if (wizardDialog.open() == Window.OK) {
				AALApplicationManagerServiceLocator loc = new AALApplicationManagerServiceLocator();
				AALApplicationManager man = loc.getAALApplicationManagerPort();
				Metadata metadata = publishWizard.getMetadata();

				// check if it is for UAAP file
				if (publishWizard.isUAAP()) {
					String id = man.uploadUaapAALApplication(
							metadata.getUsername(), metadata.getPassword(),
							metadata.getApplicationId(),
							metadata.getApplicationFullDescription(),
							metadata.getURL(), metadata.getParentCategoryId(),
							metadata.getFullImageFileName(),
							metadata.getFullImage(),
							metadata.getThumbnailImageFileName(),
							metadata.getThumbnail(), metadata.getListPrice(),
							metadata.getVersion(), metadata.getVersionNotes(),
							metadata.getFileName(),
							metadata.getFile(),
							metadata.isForPurchase());
					MessageDialog.openInformation(window.getShell(), "Success",
							"uAAP file uploaded with id: \n" + id);
				} else {

					String id = man.uploadAnyAALApplication(
							metadata.getUsername(), metadata.getPassword(),
							metadata.getApplicationId(),
							metadata.getApplicationName(),
							metadata.getApplicationShortDescription(),
							metadata.getApplicationFullDescription(),
							metadata.getKeywords(),
							metadata.getDeveloperName(),
							metadata.getDeveloperEmail(),
							metadata.getDeveloperPhone(),
							metadata.getOrganizationName(),
							metadata.getOrganizationURL(),
							metadata.getOrganizationCertificate(),
							metadata.getURL(), metadata.getParentCategoryId(),
							metadata.getFullImageFileName(),
							metadata.getFullImage(),
							metadata.getThumbnailImageFileName(),
							metadata.getThumbnail(), metadata.getListPrice(),
							metadata.getVersion(), metadata.getVersionNotes(),
							metadata.getFileName(), metadata.getFile(),
							metadata.getServiceLevelAgreement(),
							metadata.getRequirements(), metadata.getLicenses(),
							metadata.getCapabilities(), metadata.isForPurchase);

					MessageDialog.openInformation(window.getShell(), "Success",
							"Application uploaded with id: \n" + id);
				}

			} else {
				System.out.println("Canceled");
			}

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