package org.universaal.studio.sketch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

public class AALServiceInfoEditor extends MultiPageEditorPart {

	public static final String ID = "org.universaal.studio.sketch.AALServiceInfoEditor"; //$NON-NLS-1$

	public AALServiceInfoEditor() {
	}

	@Override
	protected void createPages() {
		try {
			addPage(new AALServiceDescriptionEditor(),
					(org.eclipse.ui.IEditorInput) null);
			addPage(new AALServiceApplicationDeploymentEditor(),
					(org.eclipse.ui.IEditorInput) null);
			addPage(new AALServiceProvidersEditor(),
					(org.eclipse.ui.IEditorInput) null);
		} catch (PartInitException e) {
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

}
