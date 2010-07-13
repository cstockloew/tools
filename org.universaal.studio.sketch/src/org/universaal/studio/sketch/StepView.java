package org.universaal.studio.sketch;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ProgressBar;

public class StepView extends Composite {
	private Button btnCreate;
	private Button btnEdit;
	private Label label;
	private ProgressBar progressBar;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StepView(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setLayout(new GridLayout(1, false));
		
		label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label.setText("<< step title >>");
		
		btnCreate = new Button(this, SWT.BORDER);
		btnCreate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnCreate.setText("Create...");
		
		btnEdit = new Button(this, SWT.NONE);
		btnEdit.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnEdit.setText("Edit...");
		
		progressBar = new ProgressBar(this, SWT.NONE);
		GridData gd_progressBar = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_progressBar.widthHint = 87;
		progressBar.setLayoutData(gd_progressBar);
		progressBar.setSelection(20);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public Button getBtnCreate() {
		return btnCreate;
	}
	public Button getBtnEdit() {
		return btnEdit;
	}
	public String getStepTitle() {
		return label.getText();
	}
	public void setStepTitle(String text) {
		label.setText(text);
	}

	public int getProgress() {
		return progressBar.getSelection();
	}
	public void setProgress(int selection) {
		progressBar.setSelection(selection);
	}
}
