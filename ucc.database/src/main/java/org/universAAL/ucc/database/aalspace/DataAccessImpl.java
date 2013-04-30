package org.universAAL.ucc.database.aalspace;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXB;

//import org.universAAL.ontology.profile.SubProfile;
//import org.universAAL.ontology.profile.User;
//import org.universAAL.ontology.profile.UserProfile;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ontology.profile.userid.UserIDProfile;
import org.universAAL.ucc.database.Activator;
import org.universAAL.ucc.model.jaxb.*;
import org.universAAL.ucc.profile.agent.ProfileAgent;

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
	}
	

	public synchronized boolean saveUserData(String path, OntologyInstance ontologyInstance) {
		File file = new File(path);
		Profiles profiles = JAXB.unmarshal(file, Profiles.class);
		profiles.getOntologyInstances().add(ontologyInstance);
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


	public ArrayList<OntologyInstance> getEmptyCHEProfile(String id) {
		ArrayList<OntologyInstance>ontologies = new ArrayList<OntologyInstance>();
		ArrayList<Subprofile> subprofiles = new ArrayList<Subprofile>();
		ArrayList<SimpleObject>sims = new ArrayList<SimpleObject>();
		ArrayList<CollectionValues>colList = new ArrayList<CollectionValues>();
		ArrayList<EnumObject>enums = new ArrayList<EnumObject>();
		ProfileAgent pAgent = Activator.getProfileAgent();
		User t = new User(Activator.USER_SPACE+id);
		User user = pAgent.getUser(t);
		UserProfile uP = pAgent.getUserProfile(user);
		List<SubProfile> sList = pAgent.getUserSubprofiles(uP);
//		Device d = pAgent.getDevice(new Device(Activator.USER_SPACE+id));
		OntologyInstance ont = new OntologyInstance();
		ont.setId(Activator.USER_SPACE+id);
//	    String up = pAgent.getUserProfile(Activator.USER_SPACE+"Marias_user_profile");
	    System.err.println("USER: "+user.getURI());
	    System.err.println("USER_PROFILE: "+uP.getURI());
//	    System.err.println(u.getProperty(Activator.USER_SPACE+"username"));
//	    System.err.println(u.getProperty(Activator.USER_SPACE+"password"));
//	    System.err.println(u.getProperty(Activator.USER_SPACE+"role"));
	    
	   
//	   List<SubProfile> list = pAgent.getUserSubprofiles(user);
	   if(sList != null) {
	   for(SubProfile sub : sList) {
		   System.err.println(sList.size());
		   Subprofile s = new Subprofile();
//		   if(sub instanceof UserIDProfile) {
			   UserIDProfile up = (UserIDProfile)sub;
			   s.setName(up.getURI().substring(up.getURI().indexOf("#")+1));
			   
			   StringValue st = new StringValue();
			   st.setName("username");
			   System.err.println((String)sub.getProperty(Activator.USER_SPACE+"username"));
			   st.setValue((String)sub.getProperty(Activator.USER_SPACE+"username"));
			   st.setLabel("Username: ");
			   s.getSimpleObjects().add(st);
			   
			   StringValue pwd = new StringValue();
			   pwd.setName("password");
			   pwd.setLabel("Password:");
			   System.err.println((String)sub.getProperty(Activator.USER_SPACE+"password"));
			   pwd.setValue((String)sub.getProperty(Activator.USER_SPACE+"password"));
			   s.getSimpleObjects().add(pwd);
			   
			   StringValue role = new StringValue();
			   role.setName("role");
			   role.setLabel("Role:");
			   role.setValue((String)sub.getProperty(Activator.USER_SPACE+"role"));
			   s.getSimpleObjects().add(role);
//		   }
//		   s.setName(sub.getURI());
//		   for(Enumeration<String> prop = sub.getPropertyURIs(); prop.hasMoreElements();) {
//			   String uri = prop.nextElement().toString();
//			   System.err.println(sub.getProperty(uri).toString());
//			   if(sub.getProperty(uri) instanceof String){
//			   System.err.println("DATA_ACCESS_IMPL: "+uri);
//			   StringValue str = new StringValue();
//			   str.setName(Activator.USER_SPACE+uri);
//			   str.setLabel(uri);
//			   str.setValue((String)sub.getProperty(uri));
//			   s.getSimpleObjects().add(str);
//			   }
//		   }
		  ont.getSubprofiles().add(s); 
	   } 
		  
	   }
	   else {
		   System.err.println("CRAPPP");
//		   System.err.println(list.toString());
	   }
//			Subprofile sub = new Subprofile();
//			
//			for(Enumeration en = u.getPropertyURIs(); en.hasMoreElements();) {
//				String name = en.nextElement().toString();
//				System.err.println(name);
//				//SimpleObjects
//				
//				if(u.getProperty(name) instanceof String) {
//					System.err.println("Is a STRINGVALUE"+u.getProperty(name).toString());
					
//					if(name.contains("deviceName")) {
//						sv.setLabel("Name: ");
//					}
//					if(name.contains("deviceType")) {
//						sv.setLabel("Device Type:");
//					}
//					if(name.contains("room")) {
//						sv.setLabel("Room:");
//					}
//					if(name.contains("deviceId")) {
//						sv.setLabel("Device-Address:");
//					}
//					if(name.contains("username")) {
//						StringValue sv = new StringValue();
//						sv.setName("username");
//						sv.setLabel("Username:");
//						sims.add(sv);
//					}
//					if(name.contains("password")) {
//						StringValue sv = new StringValue();
//						sv.setName("password");
//						sv.setLabel("Password:");
//						sims.add(sv);
//					}
//					if(name.contains("confirm")) {
//						StringValue sv = new StringValue();
//						sv.setLabel("Confirm Password:");
//						sv.setName("confirmPassword");
//						sims.add(sv);
//					}
//					if(name.contains("role")) {
//						StringValue sv = new StringValue();
//						sv.setName("role");
//						sv.setLabel("Role:");
//						sims.add(sv);
//					}
//					
//				}
//				
//			}
//			sub.setName(u.getURI().substring(u.getURI().indexOf("#")+1));
//			sub.setCollections(colList);
//			sub.setEnums(enums);
//			sub.setSimpleObjects(sims);
//			subprofiles.add(sub);
//			
//
//		ont.setSubprofiles(subprofiles);
		ontologies.add(ont);
		return ontologies;
	}


	public boolean saveUserDataInCHE(OntologyInstance ont) {
		ProfileAgent pAgent = Activator.getProfileAgent();
		//Create new user with userprofile
		User user = new User(Activator.USER_SPACE+ont.getId());
		UserProfile uProfile = new UserProfile(Activator.USER_SPACE+"UserProfile");
		//Add user and userProfile
		pAgent.addUser(user);
		pAgent.addUserProfile(uProfile);
		//Connect each other
		pAgent.addUserProfile(user, uProfile);
		//Create new UserIDProfile
		
		
		//Set new values of UserIDProfile
		for(Subprofile sub : ont.getSubprofiles()) {
			UserIDProfile up = new UserIDProfile(Activator.USER_SPACE+sub.getName());
			SubProfile helper = new SubProfile(Activator.USER_SPACE+sub.getName());
			for(SimpleObject sim : sub.getSimpleObjects()) {
				StringValue s = (StringValue)sim;
				if(s.getName().contains("username")) {
					System.err.println("ADD username: "+s.getValue());
					up.setUSERNAME(s.getValue());
					helper.setProperty(Activator.USER_SPACE+"username", s.getValue());
				}
				if(s.getName().contains("password")) {
					System.err.println("ADD password: "+s.getValue());
					up.setPASSWORD(s.getValue());
					helper.setProperty(Activator.USER_SPACE+"password", s.getValue());
				}
				if(s.getName().contains("role")) {
					System.err.println("ADD role: "+s.getValue());
					up.setProperty(Activator.USER_SPACE+"role", s.getValue());
					helper.setProperty(Activator.USER_SPACE+"role", s.getValue());
				}
				
			}
			pAgent.addSubProfile(up);
			pAgent.addSubProfile(helper);
			
			pAgent.addUserSubprofile(uProfile, up);
			pAgent.addUserSubprofile(user, up);
			pAgent.addUserSubprofile(user, helper);
			pAgent.addUserSubprofile(uProfile, helper);
		}
		
		return true;
	}


	public ArrayList<OntologyInstance> getEmptyCHEFormFields(String instance, String uri) {
		ProfileAgent pAgent = Activator.getProfileAgent();
		ArrayList<OntologyInstance>ontologies = new ArrayList<OntologyInstance>();
		if(instance.equals("User")) {
			User get = new User(Activator.USER_SPACE+uri);
			User user = pAgent.getUser(get);
			UserProfile uP = pAgent.getUserProfile(user);
			List<SubProfile> sList = pAgent.getUserSubprofiles(uP);
			OntologyInstance ont = new OntologyInstance();
			ont.setId(Activator.USER_SPACE+uri);

		   if(sList != null) {
		   for(SubProfile sub : sList) {
			   
			   Subprofile s = new Subprofile();
//			   if(sub instanceof UserIDProfile) {
				   UserIDProfile up = (UserIDProfile)sub;
				   s.setName(up.getURI().substring(up.getURI().indexOf("#")+1));
				   
				   StringValue st = new StringValue();
				   st.setName("username");
				   if(up.getUSERNAME() == null)
					   st.setValue("");
				   else
					   st.setValue(up.getUSERNAME());
				   st.setLabel("Username: ");
				   s.getSimpleObjects().add(st);
				   
				   StringValue pwd = new StringValue();
				   pwd.setName("password");
				   pwd.setLabel("Password:");
				   if(up.getPASSWORD() == null)
					   pwd.setValue("");
				   else
					   pwd.setValue(up.getPASSWORD());
				   s.getSimpleObjects().add(pwd);
				   
				   StringValue role = new StringValue();
				   role.setName("role");
				   role.setLabel("Role:");
				   if((String)up.getProperty(Activator.USER_SPACE+"role") == null) 
					   role.setValue("");
				   else
					   role.setValue((String)up.getProperty(Activator.USER_SPACE+"role"));
				   s.getSimpleObjects().add(role);
//			   }

			  ont.getSubprofiles().add(s); 
		   } 
			  
		   } ontologies.add(ont);
		}
		
		
		if(instance.equals("Device")) {
			
		}
		
	return ontologies;
	
	}	
	
	

}
