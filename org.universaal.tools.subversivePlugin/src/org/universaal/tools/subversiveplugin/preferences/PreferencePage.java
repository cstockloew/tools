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
package org.universaal.tools.subversiveplugin.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.universaal.tools.subversiveplugin.Activator;

public class PreferencePage 
		extends FieldEditorPreferencePage 
		implements IWorkbenchPreferencePage {
	
	StringFieldEditor urlField;
	StringFieldEditor folderField;

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please enter the URL for the desired SVN Repository.");
	}

	@Override
	public void init(IWorkbench arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createFieldEditors() {
		urlField = new StringFieldEditor(PreferenceConstants.P_URL, 
				"URL", 
				getFieldEditorParent());
		folderField = new StringFieldEditor(PreferenceConstants.P_FOLDER,
				"Folder that contains the tutorials. (Ex. /trunk/tutorials)",
				getFieldEditorParent());
		addField(urlField);
		addField(folderField);	
	}

}
