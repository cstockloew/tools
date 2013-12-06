package org.universaal.tools.packaging.tool.gui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.universaal.tools.packaging.tool.impl.PageImpl;

public class ErrorPage extends PageImpl {

	protected ErrorPage(String pageName) {
		super(pageName, "WARNING!!");
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);		

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 1;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		Label label1 = new Label(container, SWT.NULL);
		label1.setText("Fatal Errors occurred. Application Packager Ended.");
		setPageComplete(true);
	}

	@Override
	public boolean nextPressed() {
		return true;
	}

}