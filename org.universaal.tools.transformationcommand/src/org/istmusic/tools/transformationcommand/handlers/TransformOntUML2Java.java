package org.istmusic.tools.transformationcommand.handlers;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.internal.wizards.preferences.PreferencesContentProvider;
import org.istmusic.tools.transformationcommand.activator.Activator;
import org.istmusic.tools.transformationcommand.preferences.PreferenceConstants;

public class TransformOntUML2Java extends TransformationHandler {
	static final String TRANSFORMATION_FILENAME = "ontUML2Java.m2t";
	static final String THIS_BUNDLE_NAME = "org.universaal.tools.transformationcommand";

	public TransformOntUML2Java() {
		setFileAndBundleName(TRANSFORMATION_FILENAME, THIS_BUNDLE_NAME);
	}

	@Override
	protected String getDirectoryFromPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String directory = store.getString(PreferenceConstants.P_UML2JAVA_PATH);
		return directory;
	}

	@Override
	protected boolean getAbsolutePathBooleanFromPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean absolutePath = store.getBoolean(PreferenceConstants.P_UML2JAVA_ABSOLUTE_BOOLEAN);
		return absolutePath;
	}
}
