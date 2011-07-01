package org.istmusic.tools.transformationcommand.handlers;

import org.eclipse.jface.preference.IPreferenceStore;
import org.istmusic.tools.transformationcommand.activator.Activator;
import org.istmusic.tools.transformationcommand.preferences.PreferenceConstants;

public class TransformToAdaptationModelHandler extends TransformationHandler {
	static final String TRANSFORMATION_FILENAME = "SmallTest.m2t";
	static final String THIS_BUNDLE_NAME = "org.istmusic.tools.transformationcommand";
	

	public TransformToAdaptationModelHandler() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String directory = store.getString(PreferenceConstants.P_UML2JAVA_PATH);
		boolean absolutePath = store.getBoolean(PreferenceConstants.P_UML2JAVA_ABSOLUTE_BOOLEAN);
		setFileAndBundleName(TRANSFORMATION_FILENAME, THIS_BUNDLE_NAME);
	}


	@Override
	protected String getDirectoryFromPreferences() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected boolean getAbsolutePathBooleanFromPreferences() {
		// TODO Auto-generated method stub
		return false;
	}
}
