package org.universaal.tools.codeassistantapplication.ontologyrepository.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.universaal.tools.codeassistantapplication.Activator;

public class PreferencePage 
	extends FieldEditorPreferencePage 
	implements IWorkbenchPreferencePage{

	private StringFieldEditor ontRepoUrlField;
	
	public PreferencePage(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please enter the URL to the ontology repository");
	}
	
	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {
		ontRepoUrlField = new StringFieldEditor(
				PreferenceConstants.P_ONTOLOGY_REPOSITORY_URL, 
				"Ontology Repository URL", 
				getFieldEditorParent());
		addField(ontRepoUrlField);
	}
}
