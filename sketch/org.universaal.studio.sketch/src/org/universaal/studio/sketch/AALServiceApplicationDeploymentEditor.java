package org.universaal.studio.sketch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class AALServiceApplicationDeploymentEditor extends EditorPart {

	public static final String ID = "org.universaal.studio.sketch.AALServiceApplicationDeploymentEditor"; //$NON-NLS-1$

	public AALServiceApplicationDeploymentEditor() {
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblThisEditorProvides = new Label(container, SWT.WRAP);
		GridData gd_lblThisEditorProvides = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblThisEditorProvides.widthHint = 484;
		lblThisEditorProvides.setLayoutData(gd_lblThisEditorProvides);
		lblThisEditorProvides.setText("This editor provides facilities for desribing which applications the AAL service conists of, and how these are deployed on devices");

	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the Save operation
	}

	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// Initialize the editor part
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}
