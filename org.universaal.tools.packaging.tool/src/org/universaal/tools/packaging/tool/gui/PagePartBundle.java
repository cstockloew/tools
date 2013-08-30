package org.universaal.tools.packaging.tool.gui;

import org.eclipse.core.resources.IProject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.impl.PageImpl.FullListener;
import org.universaal.tools.packaging.tool.validators.AlphabeticV;
import org.universaal.tools.packaging.tool.validators.IntegerV;

public class PagePartBundle extends PageImpl {

	//private IProject artifact;
	//private POMParser p;
	private int partNumber;

	private Text bundleId, bundleVersion;
	
	protected PagePartBundle(String pageName, int pn) {
		super(pageName, "Part "+(pn+1)+"/"+GUI.getInstance().getPartsCount()+
				" - Specify Bundle Id and Version");
		this.partNumber = pn;
	}

	public void createControl(final Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		Label l1 = new Label(container, SWT.NULL);
		bundleId = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(bundleId);
		l1.setText("* Bundle Id");
		bundleId.addVerifyListener(new AlphabeticV());
		bundleId.setLayoutData(gd);

		Label l2 = new Label(container, SWT.NULL);
		bundleVersion = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(bundleVersion);
		l2.setText("* Bundle Version");
		bundleVersion.addVerifyListener(new AlphabeticV());
		bundleVersion.setLayoutData(gd);	
		
		bundleId.addKeyListener(new FullListener());
		bundleVersion.addKeyListener(new FullListener());
	}

	@Override
	public boolean nextPressed() {

		try{
			app.getAppParts().get(partNumber).setPartBundle(bundleId.getText(),bundleVersion.getText());
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return true;
	}

}