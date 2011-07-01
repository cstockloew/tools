package org.istmusic.tools.transformationcommand.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.istmusic.tools.transformationcommand.activator.Activator;

public class PreferencePageRoot
		extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	
	public PreferencePageRoot() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please see subcategories for options related to different " +
				"kinds of transformation commands.");
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub

	}

}
