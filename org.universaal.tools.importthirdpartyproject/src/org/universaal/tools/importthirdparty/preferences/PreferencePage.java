package org.universaal.tools.importthirdparty.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.universaal.tools.importexternalproject.Activator;

public class PreferencePage 
	extends FieldEditorPreferencePage 
	implements IWorkbenchPreferencePage{

	private StringFieldEditor thirdPartyUrlField;
	private StringFieldEditor examplesUrlField;
	
	public PreferencePage(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please enter the URL to the official examples and " +
				"third party applications.");
	}
	
	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {
		examplesUrlField = new StringFieldEditor(
				PreferenceConstants.P_EXAMPLES_URL,
				"Examples URL",
				getFieldEditorParent());
		addField(examplesUrlField);
		
		thirdPartyUrlField = new StringFieldEditor(
				PreferenceConstants.P_THRD_PARTY_URL, 
				"Third Party URL", 
				getFieldEditorParent());
		addField(thirdPartyUrlField);
	}

}
