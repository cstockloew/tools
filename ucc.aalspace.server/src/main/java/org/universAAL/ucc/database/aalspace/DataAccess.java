package org.universAAL.ucc.database.aalspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.universAAL.ucc.model.jaxb.OntologyInstance;
import org.universAAL.ucc.model.jaxb.Subprofile;


public interface DataAccess {
	public void updateUserData(String file, String id, HashMap<String, List<Subprofile>>subprofiles);
	public ArrayList<OntologyInstance> getFormFields(String file);
	public boolean deleteUserData(String file, String instance);
	public boolean saveUserData(String file, OntologyInstance ontologyInstnce);
	public ArrayList<OntologyInstance>getEmptyProfile(String profile);
	
}
