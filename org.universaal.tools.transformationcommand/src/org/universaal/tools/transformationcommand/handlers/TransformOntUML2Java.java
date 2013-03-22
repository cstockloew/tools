/*
	Copyright 2011 SINTEF, http://www.sintef.no
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.tools.transformationcommand.handlers;

import org.eclipse.jface.preference.IPreferenceStore;
import org.universaal.tools.transformationcommand.activator.Activator;
import org.universaal.tools.transformationcommand.preferences.PreferenceConstants;

public class TransformOntUML2Java extends TransformationHandler {
	static final String TRANSFORMATION_FILENAME = "transformations/ontUML2JavaV2.m2t";
	static final String THIS_BUNDLE_NAME = Activator.PLUGIN_ID;
	private static final String SOURCE_FILE_SUFFIX = ".uml";

	public TransformOntUML2Java() {
		setFileAndBundleName(TRANSFORMATION_FILENAME, THIS_BUNDLE_NAME);
	}

	@Override
	protected String getRootDirectoryFromPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String directory = store.getString(PreferenceConstants.P_UML2JAVA_ROOTPATH);
		return directory;
	}

	@Override
	protected String getJavaDirectoryFromPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String directory = store.getString(PreferenceConstants.P_UML2JAVA_JAVAPATH);
		return directory;
	}

	
	@Override
	protected boolean getAbsolutePathBooleanFromPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean absolutePath = store.getBoolean(PreferenceConstants.P_UML2JAVA_ABSOLUTE_BOOLEAN);
		return absolutePath;
	}

	@Override
	protected boolean dualMetamodel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getSourceFileSuffix() {
		// TODO Auto-generated method stub
		return SOURCE_FILE_SUFFIX;
	}
}
