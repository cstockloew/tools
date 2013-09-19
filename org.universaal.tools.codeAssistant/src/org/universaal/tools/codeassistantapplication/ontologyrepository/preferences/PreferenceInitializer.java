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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.universaal.tools.codeassistantapplication.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_ONTOLOGY_REPOSITORY_URL, 
				"http://155.207.85.53:8080/bioportal/ontologies");
		
		store.setDefault(PreferenceConstants.P_ONTOLOGY_REPOSITORY_APIKEY, 
				"990fff23-51f4-479e-863b-21554d863ef9");
	}

	
}
