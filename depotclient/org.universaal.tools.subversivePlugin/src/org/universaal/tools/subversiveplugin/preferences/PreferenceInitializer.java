package org.universaal.tools.subversiveplugin.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.universaal.tools.subversiveplugin.Activator;
import org.universaal.tools.subversiveplugin.preferences.PreferenceConstants;

public class PreferenceInitializer extends AbstractPreferenceInitializer{

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_URL, 
				"http://forge.universaal.org/svn/support");
		store.setDefault(PreferenceConstants.P_FOLDER,
				"/trunk/samples");
		
	}
}
