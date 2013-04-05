package org.universaal.tools.packaging.tool.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.universaal.tools.packaging.impl.PageImpl;

public class EndPage extends PageImpl {

	protected EndPage(String pageName) {
		super(pageName, "This is the ending page for the Application Packager");
	}

	public void createControl(Composite parent) { 

		container = new Composite(parent, SWT.NULL);
		setControl(container);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 1;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		Label end = new Label(container, SWT.NULL);
		end.setText("Congratulations, you are a single step away from creating your Application, just press the Finish button to generate it!");		

		FontData[] fD = end.getFont().getFontData();
		fD[0].setStyle(SWT.BOLD);
		end.setFont(new Font(container.getDisplay(), fD[0]));		

		Label end0 = new Label(container, SWT.NULL);
		end0.setText("");	

		setPageComplete(true);
	}
}