/*****************************************************************************************
	Copyright 2012-2014 CERTH-HIT, http://www.hit.certh.gr/
	Hellenic Institute of Transport (HIT)
	Centre For Research and Technology Hellas (CERTH)
	
	
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
 *****************************************************************************************/

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
	private StringFieldEditor ontRepoApikeyField;
	
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
		
		ontRepoApikeyField = new StringFieldEditor(
				PreferenceConstants.P_ONTOLOGY_REPOSITORY_APIKEY, 
				"Ontology Repository APIKEY", 
				getFieldEditorParent());
		addField(ontRepoApikeyField);
	}
}
