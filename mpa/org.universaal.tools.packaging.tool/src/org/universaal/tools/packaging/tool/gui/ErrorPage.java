package org.universaal.tools.packaging.tool.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.universaal.tools.packaging.impl.PageImpl;

public class ErrorPage extends PageImpl {

	protected ErrorPage(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);		

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 3;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		setPageComplete(true);
	}

	@Override
	public boolean nextPressed() {
		return true;
	}
}