package org.universaal.tools.codeassistantapplication.ontologyrepository.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.universaal.tools.codeassistantapplication.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_ONTOLOGY_REPOSITORY_URL, 
				"http://155.207.85.53:8080/bioportal/ontologies");
	}

	
}
