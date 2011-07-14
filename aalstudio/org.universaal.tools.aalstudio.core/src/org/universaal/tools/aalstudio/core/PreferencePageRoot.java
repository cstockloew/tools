package org.universaal.tools.aalstudio.core;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePageRoot
		extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	
	public PreferencePageRoot() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please see subcategories for options related to different " +
				"AAL Studio plugins.");
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
