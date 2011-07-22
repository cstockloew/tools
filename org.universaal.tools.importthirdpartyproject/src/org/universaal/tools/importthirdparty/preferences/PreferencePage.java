package org.universaal.tools.importthirdparty.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.universaal.tools.importexternalproject.Activator;

public class PreferencePage 
	extends FieldEditorPreferencePage 
	implements IWorkbenchPreferencePage{

	private StringFieldEditor urlField;
	
	public PreferencePage(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please enter the URL to the list of third party applications.");
	}
	
	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createFieldEditors() {
		urlField = new StringFieldEditor(PreferenceConstants.P_URL, 
				"URL", 
				getFieldEditorParent());
		addField(urlField);
	}

}
