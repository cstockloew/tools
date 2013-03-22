package org.universaal.tools.uStoreClientapplication.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PlatformUI;
import org.universaal.commerce.ustore.tools.AALApplicationManager;
import org.universaal.commerce.ustore.tools.AALApplicationManagerServiceLocator;
import org.universaal.tools.uStoreClientapplication.wizzard.PublishWizard;

public class ApplicationsDialog extends Dialog {

	protected Object result;
	protected Shell shlUstoreApplications;
	private java.util.List<Application> applications;
	private Shell parent;
	private String username;
	private String password;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public ApplicationsDialog(Shell parent, int style,
			java.util.List<Application> applications, String username,
			String password) {
		super(parent, style);
		this.applications = applications;
		this.parent = parent;
		this.username = username;
		this.password = password;
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlUstoreApplications.open();
		shlUstoreApplications.layout();
		Display display = getParent().getDisplay();
		while (!shlUstoreApplications.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlUstoreApplications = new Shell(getParent(), SWT.DIALOG_TRIM);
		shlUstoreApplications.setSize(393, 300);
		shlUstoreApplications.setText("uStore applications");
		shlUstoreApplications.setLayout(new BorderLayout(0, 0));

		Composite composite = new Composite(shlUstoreApplications, SWT.NONE);
		composite.setLayoutData(BorderLayout.SOUTH);
		composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		final Button btnUpdate = new Button(composite, SWT.NONE);

		btnUpdate.setText("Update");

		final Button btnDelete = new Button(composite, SWT.NONE);

		btnDelete.setText("Delete");

		Composite composite_1 = new Composite(shlUstoreApplications, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));

		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);

		final List list = new List(composite_1, SWT.BORDER);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
			}
		});

		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean result = MessageDialog.openConfirm(
						parent,
						"Application deletion",
						"Are you sure you want to delete application "
								+ list.getItem(list.getSelectionIndex()));
				if (result) {
					try {
						AALApplicationManagerServiceLocator loc = new AALApplicationManagerServiceLocator();
						AALApplicationManager man = loc
								.getAALApplicationManagerPort();
						man.deleteAALApplication(username, password,
								applications.get(list.getSelectionIndex())
										.getId());
						MessageDialog.openInformation(parent, "Success",
								"Application deleted successfully");
					} catch (Exception ex) {
						ex.printStackTrace();
						MessageDialog.openError(parent, "Error",
								"Error occured while deleting application\n "
										+ ex.toString());
					}
				} else {

				}
			}
		});

		// add applications to list

		for (int i = 0; i < applications.size(); i++) {
			list.add(applications.get(i).toString());
		}

		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					String selectedApplication = list.getItem(list
							.getSelectionIndex());
					String id = "";
					for (int i = 0; i < applications.size(); i++) {
						if (selectedApplication.equals(applications.get(i)
								.getName())) {
							id = applications.get(i).getId();
						}
					}

					PublishWizard publishWizard = new PublishWizard(id,username, password);
					WizardDialog wizardDialog = new WizardDialog(PlatformUI
							.getWorkbench().getActiveWorkbenchWindow()
							.getShell(), publishWizard);
					if (wizardDialog.open() == Window.OK) {
						AALApplicationManagerServiceLocator loc = new AALApplicationManagerServiceLocator();
						AALApplicationManager man = loc
								.getAALApplicationManagerPort();
						Metadata metadata = publishWizard.getMetadata();

						String uploadId = man.uploadAnyAALApplication(
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
								metadata.getURL(),
								metadata.getParentCategoryId(),
								metadata.getFullImageFileName(),
								metadata.getFullImage(),
								metadata.getThumbnailImageFileName(),
								metadata.getThumbnail(),
								metadata.getListPrice(), metadata.getVersion(),
								metadata.getVersionNotes(),
								metadata.getFileName(), metadata.getFile(),
								metadata.getServiceLevelAgreement(),
								metadata.getRequirements(),
								metadata.getLicenses(),
								metadata.getCapabilities(),
								metadata.isForPurchase);
						MessageDialog.openInformation(parent,
								"Success", "Application uploaded with id: \n"
										+ uploadId);

					} else {
						System.out.println("Canceled");
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}
