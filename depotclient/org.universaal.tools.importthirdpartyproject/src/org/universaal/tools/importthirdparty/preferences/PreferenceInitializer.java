package org.universaal.tools.importthirdparty.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.universaal.tools.importexternalproject.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_THRD_PARTY_URL, 
				"http://depot.universaal.org/projectrequest/projects.xml");
		store.setDefault(PreferenceConstants.P_EXAMPLES_URL,
				"http://depot.universaal.org/projectrequest/official.xml");
		
	}

	
}
