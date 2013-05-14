package org.universAAL.ucc.database.aalspace;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXB;

import org.universAAL.ucc.model.jaxb.OntologyInstance;
import org.universAAL.ucc.model.jaxb.Profiles;
import org.universAAL.ucc.model.jaxb.Subprofile;
import org.universAAL.ucc.model.jaxb.Profiles.OntologyInstances;

public class DataAccessImpl implements DataAccess{
	
    public ArrayList<OntologyInstance> getFormFields(String file) {
		ArrayList<OntologyInstance>ontologies = new ArrayList<OntologyInstance>();
		Profiles profiles = JAXB.unmarshal(new File(file), Profiles.class);
		OntologyInstances onts = profiles.getOntologyInstances();
		for(OntologyInstance o : onts.getOntologyInstance()) {
			ontologies.add(o);
		}
				return ontologies;
	}
	
    public void updateUserData( String file, String id, HashMap<String, List<Subprofile>> subprofiles) {
		File f = new File(file);
		Profiles profiles = JAXB.unmarshal(f, Profiles.class);
		ArrayList<OntologyInstance> list = new ArrayList<OntologyInstance>();
		for(OntologyInstance ont : profiles.getOntologyInstances().getOntologyInstance()) {
			if(!ont.getId().equals(id)) {
				list.add(ont);
			} else {
				OntologyInstance upToDate = new OntologyInstance();
				upToDate.setId(id);
				List<Subprofile> temp = subprofiles.get(id);
				for(Subprofile s : temp) {
					upToDate.getSubprofiles().getSubprofile().add(s);
				}
				upToDate.setType(ont.getType());
				list.add(upToDate);
			}
		}
		Profiles p = new Profiles();
		OntologyInstances os = new OntologyInstances();
		for(OntologyInstance o : list) {
		os.getOntologyInstance().add(o);
		}

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
	
	public boolean saveUserData(String path, OntologyInstance ontologyInstance) {
		File file = new File(path);
		Profiles profiles = JAXB.unmarshal(file, Profiles.class);
		profiles.getOntologyInstances().getOntologyInstance().add(ontologyInstance);
//		for(OntologyChangedListener l : OntologySupplierServiceImpl.getListeners()) {
//			l.ontologySaved(ontologyInstance);
//		}
		file.delete();
		JAXB.marshal(profiles, new File(path));
		
		return true;
	}
	
	
	public  boolean deleteUserData(String file, String instance) {
		File f = new File(file);
		Profiles profiles = JAXB.unmarshal(f, Profiles.class);
		for(OntologyInstance o : profiles.getOntologyInstances().getOntologyInstance()) {
			if(o.getId().equals(instance)) {
				profiles.getOntologyInstances().getOntologyInstance().remove(o);
//				for(OntologyChangedListener l : OntologySupplierServiceImpl.getListeners()) {
//					l.ontologyDeleted(o);
//				}
				JAXB.marshal(profiles, new File(file));
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<OntologyInstance> getEmptyProfile(String profile) {
		ArrayList<OntologyInstance>ontologies = new ArrayList<OntologyInstance>();
		Profiles profiles = (Profiles)JAXB.unmarshal(new File(profile), Profiles.class);
		for(OntologyInstance o : profiles.getOntologyInstances().getOntologyInstance()) {
			ontologies.add(o);
		}
				return ontologies;
	}	

}
