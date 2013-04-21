package org.universAAL.ucc.database.aalspace;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.JAXB;
import org.universAAL.ucc.model.jaxb.*;

public class DataAccessImpl implements DataAccess{

	public synchronized ArrayList<OntologyInstance> getFormFields(String file) {
		ArrayList<OntologyInstance>ontologies = new ArrayList<OntologyInstance>();
		Profiles profiles = (Profiles)JAXB.unmarshal(new File(file), Profiles.class);
		for(OntologyInstance o : profiles.getOntologyInstances()) {
			ontologies.add(o);
		}
				return ontologies;
	}
	

	public synchronized void updateUserData( String file, String id, HashMap<String, ArrayList<Subprofile>> subprofiles) {
		File f = new File(file);
		Profiles profiles = JAXB.unmarshal(f, Profiles.class);
		ArrayList<OntologyInstance> list = new ArrayList<OntologyInstance>();
		for(OntologyInstance ont : profiles.getOntologyInstances()) {
			if(!ont.getId().equals(id)) {
				list.add(ont);
			} else {
				OntologyInstance upToDate = new OntologyInstance();
				upToDate.setId(id);
				upToDate.setSubprofiles(subprofiles.get(id));
				upToDate.setType(ont.getType());
				list.add(upToDate);
			}
		}
		Profiles p = new Profiles();
		p.setOntologyInstances(list);
		f.delete();
		JAXB.marshal(p, new File(file));
//		ArrayList<OntologyInstance>del = new ArrayList<OntologyInstance>();
//		ArrayList<OntologyInstance>upToDate = new ArrayList<OntologyInstance>();
//		for(OntologyInstance o : profiles.getOntologyInstances()) {
//			System.err.println("UPDATE");
//			if(o.getId().equals(id)) {
//				OntologyInstance oi = new OntologyInstance();
//				oi.setId(o.getId());
//				for(Subprofile temp : subprofiles.get(o.getId())) {
//					oi.getSubprofiles().add(temp);
//				}
//				del.add(o);
//				upToDate.add(oi);
//			}
//		} //f.delete(); 
//		for(OntologyInstance d : del) {
//			profiles.getOntologyInstances().remove(d);
//		}
//		for(OntologyInstance up : upToDate) {
//			profiles.getOntologyInstances().add(up);
//		}
//		JAXB.marshal(profiles, new File(file));	
	}
	

	public synchronized boolean saveUserData(String path, OntologyInstance ontologyInstance) {
		File file = new File(path);
		Profiles profiles = JAXB.unmarshal(file, Profiles.class);
		profiles.getOntologyInstances().add(ontologyInstance);
//		for(OntologyChangedListener l : OntologySupplierServiceImpl.getListeners()) {
//			l.ontologySaved(ontologyInstance);
//		}
		file.delete();
		JAXB.marshal(profiles, new File(path));
		
		return true;
	}
	
	


	public synchronized boolean deleteUserData(String file, String instance) {
		File f = new File(file);
		Profiles profiles = JAXB.unmarshal(f, Profiles.class);
		for(OntologyInstance o : profiles.getOntologyInstances()) {
			if(o.getId().equals(instance)) {
				profiles.getOntologyInstances().remove(o);
//				for(OntologyChangedListener l : OntologySupplierServiceImpl.getListeners()) {
//					l.ontologyDeleted(o);
//				}
				JAXB.marshal(profiles, new File(file));
				return true;
			}
		}
		return false;
	}




	public synchronized ArrayList<OntologyInstance> getEmptyProfile(String profile) {
		ArrayList<OntologyInstance>ontologies = new ArrayList<OntologyInstance>();
		Profiles profiles = (Profiles)JAXB.unmarshal(new File(profile), Profiles.class);
		for(OntologyInstance o : profiles.getOntologyInstances()) {
			ontologies.add(o);
		}
				return ontologies;
	}	

}
