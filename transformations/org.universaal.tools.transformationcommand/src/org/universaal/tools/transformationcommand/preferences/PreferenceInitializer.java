package org.universaal.tools.transformationcommand.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.universaal.tools.transformationcommand.activator.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer  {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_UML2JAVA_PATH, 
				"/src/main/java");
		store.setDefault(PreferenceConstants.P_UML2JAVA_ABSOLUTE_BOOLEAN, 
				false);
		
	}

}
