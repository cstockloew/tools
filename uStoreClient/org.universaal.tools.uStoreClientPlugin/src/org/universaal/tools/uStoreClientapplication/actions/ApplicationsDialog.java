package org.universaal.tools.uStoreClientapplication.actions;

import org.eclipse.jface.dialogs.MessageDialog;
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
import org.universaal.commerce.ustore.tools.AALApplicationManager;
import org.universaal.commerce.ustore.tools.AALApplicationManagerServiceLocator;

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
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
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
						man.deleteApplication(username, password, applications
								.get(list.getSelectionIndex()).getId());
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
			list.add(applications.get(i).getName());
		}

	}
}
