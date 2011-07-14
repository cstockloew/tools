package org.universaal.tools.transformationcommand.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.universaal.tools.transformationcommand.activator.Activator;

public class TransformationsPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public TransformationsPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please see subcategories for options related to each transformation");
	}

	@Override
	public void init(IWorkbench workbench) {
		// Intentionally left blank
	}

	@Override
	protected void createFieldEditors() {
		// Intentionally left blank
	}

}