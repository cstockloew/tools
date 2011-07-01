package org.universaal.tools.subversiveplugin.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.universaal.tools.subversiveplugin.Activator;

public class PreferencePage 
		extends FieldEditorPreferencePage 
		implements IWorkbenchPreferencePage {
	
	StringFieldEditor urlField;
	StringFieldEditor folderField;

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please enter the URL for the desired SVN Repository.");
	}

	@Override
	public void init(IWorkbench arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createFieldEditors() {
		urlField = new StringFieldEditor(PreferenceConstants.P_URL, 
				"URL", 
				getFieldEditorParent());
		folderField = new StringFieldEditor(PreferenceConstants.P_FOLDER,
				"Folder that contains the tutorials. (Ex. /trunk/tutorials)",
				getFieldEditorParent());
		addField(urlField);
		addField(folderField);	
	}

}
