package org.universAAL.ucc.database.aalspace;

import java.util.ArrayList;
import java.util.HashMap;
import org.universAAL.ucc.model.jaxb.OntologyInstance;
import org.universAAL.ucc.model.jaxb.Subprofile;


public interface DataAccess {
	//For JAXB usage
	public void updateUserData(String file, String id, HashMap<String, ArrayList<Subprofile>>subprofiles);
	public ArrayList<OntologyInstance> getFormFields(String file);
	public boolean deleteUserData(String file, String instance);
	public boolean saveUserData(String file, OntologyInstance ontologyInstnce);
	public ArrayList<OntologyInstance>getEmptyProfile(String profile);
	
	//For CHE usage
	public ArrayList<OntologyInstance>getEmptyCHEProfile(String id);
	public boolean saveUserDataInCHE(OntologyInstance ont);
	public ArrayList<OntologyInstance>getEmptyCHEFormFields(String instance, String uri);
	
	
}
