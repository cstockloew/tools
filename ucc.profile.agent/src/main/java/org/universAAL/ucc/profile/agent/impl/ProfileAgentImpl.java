package org.universAAL.ucc.profile.agent.impl;

import java.util.Iterator;
import java.util.List;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.profile.AALService;
import org.universAAL.ontology.profile.AALServiceProfile;
import org.universAAL.ontology.profile.AALSpace;
import org.universAAL.ontology.profile.AALSpaceProfile;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ontology.profile.service.ProfilingService;
import org.universAAL.ontology.profile.userid.UserIDProfile;
import org.universAAL.support.utils.service.Arg;
import org.universAAL.support.utils.service.Path;
import org.universAAL.support.utils.service.low.Request;
import org.universAAL.support.utils.service.mid.UtilEditor;
import org.universAAL.ucc.profile.agent.ProfileAgent;
import org.universaal.ontology.health.owl.HealthProfile;

public class ProfileAgentImpl implements ProfileAgent {
	  private static ServiceCaller caller = null;
	  
	  // TODO: to be updated according to what we need
	  private static final String PROFILE_AGENT_NAMESPACE = "http://ontology.itaca.es/ProfileAGENT.owl#";
	  
	  private static final String OUTPUT_GETPROFILABLE = PROFILE_AGENT_NAMESPACE
		    + "out1";
	  private static final String OUTPUT_GETPROFILE = PROFILE_AGENT_NAMESPACE
		    + "out2";
	  private static final String OUTPUT_GETUSERS = PROFILE_AGENT_NAMESPACE
		    + "out3";
	  private static final String OUTPUT_GETSUBPROFILES = PROFILE_AGENT_NAMESPACE
		    + "out4";
	  private static final String OUTPUT_GETSUBPROFILE = PROFILE_AGENT_NAMESPACE
		    + "out5";
	  // USER_URI=USER_URI_PREFIX + UserID
	  private static final String USER_URI_PREFIX = "urn:org.universAAL.aal_space:test_env#";
	  //static final String CONTEXT_HISTORY_HTL_IMPL_NAMESPACE = "http://ontology.universAAL.org/ContextHistoryHTLImpl.owl#";
	  //private static final String OUTPUT_QUERY_RESULT = CONTEXT_HISTORY_HTL_IMPL_NAMESPACE + "queryResult";
	  
	  private static final String OUTPUT = PROFILE_AGENT_NAMESPACE
			    + "outX";
	  
	  public ProfileAgentImpl(ModuleContext context) {
			caller = new DefaultServiceCaller(context);
	  }
	  
	  /**
	   * add user to CHE via Profiling server
	   * @param user
	   * @return ?
	   */
	  public String addUser(User user) {
	    	System.out.println("Profile Agent: add user with URI: " + user.getURI());
	    	ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
	    	req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS}, user);
	    	ServiceResponse resp = caller.call(req);
	    	return resp.getCallStatus().name();
	  }
	
	  /**
	   * get user from CHE
	   * @param user
	   * @return User instance, if exists; null, if not exists (registered)
	   */
	  public User getUser(User user) {
			System.out.println("Profile agent: get user with URI: " + user.getURI());
			ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
			req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
			req.addRequiredOutput(OUTPUT_GETPROFILABLE, new String[]{ProfilingService.PROP_CONTROLS});
			ServiceResponse resp = caller.call(req);
			if (resp.getCallStatus() == CallStatus.succeeded) {
			    Object out=getReturnValue(resp.getOutputs(), OUTPUT_GETPROFILABLE);
			    if (out != null) {
					System.out.println("Profile agent: result got is - " + out.toString());
					return (User)out;
			    } else {
			    	System.out.println("NOTHING!");
			    	return null;
			    }
			}else{
			    System.out.println("other results: " + resp.getCallStatus().name());
			    return null;
			}
	  }
	  
	    
	
	  private ServiceRequest userProfileRequest(User user) {
		        ServiceRequest req = new ServiceRequest(new ProfilingService(), null);
		        req.addValueFilter(new String[] {ProfilingService.PROP_CONTROLS}, user);

		        req.addTypeFilter(new String[] {ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE}, UserProfile.MY_URI);

		        req.addRequiredOutput(OUTPUT_GETPROFILE, new String[] {ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE});

		        return req;
		      }


	
    private Object getReturnValue(List outputs, String expectedOutput) {
    	Object returnValue = null;
    	if (outputs == null)
    		System.out.println("Profile Agent: No results found!");
    	else
    		for (Iterator i = outputs.iterator(); i.hasNext();) {
    			ProcessOutput output = (ProcessOutput) i.next();
    			if (output.getURI().equals(expectedOutput))
    				if (returnValue == null)
    					returnValue = output.getParameterValue();
    				else
    					System.out.println("Profile Agent: redundant return value!");
    			else
    				System.out.println("Profile Agent - output ignored: " + output.getURI());
    		}

    	return returnValue;
    }

	public UserProfile getUserProfile(User user) {
	 	System.out.println("Profile agent: get user profile for user: " + user.getURI());
	 	ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
	 	req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
		req.addRequiredOutput(OUTPUT_GETPROFILE, new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE});
	 	ServiceResponse resp=caller.call(req);
	 	if (resp.getCallStatus() == CallStatus.succeeded) {
	 	    Object out=getReturnValue(resp.getOutputs(), OUTPUT_GETPROFILE);
	 	    if (out != null) {
	 	    	System.out.println("The result: " + out.toString());
	 	    	return (UserProfile)out;
	 	    } else {
	 	    	System.out.println("NOTHING!");
	 	    	return null;
	 	    }
	 	}else{
	 	    System.out.println("Other result: " + resp.getCallStatus().name());
	 	    return null;
	 	}
	}


    public String addUserProfile(User user, UserProfile profile) {
    	System.out.println("Profile agent: addProfile to an user");
    	ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
    	req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
    	req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE}, profile);
    	ServiceResponse resp=caller.call(req);
    	return resp.getCallStatus().name();
    }
    
	public void changeUserProfile(User user, UserProfile userProfile) {
		// TODO Auto-generated method stub
		
	}

	public void removeUserProfile(User user) {
		// TODO Auto-generated method stub
		
	}

	public List getAALSpaceProfiles(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addAALSpaceProfile(User user, AALSpaceProfile aalSpaceProfile) {
		// TODO Auto-generated method stub
		
	}

	public void changeAALSpaceProfile(User user, AALSpaceProfile aalSpaceProfile) {
		// TODO Auto-generated method stub
		
	}

	public void removeAALSpaceProfile(User user) {
		// TODO Auto-generated method stub
		
	}
	
	public String addSubProfile(SubProfile profile) {
		System.out.println("Profile agent: add subProfile");
		ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
		req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE}, profile);
		ServiceResponse resp = caller.call(req);
		return resp.getCallStatus().name();
	}

	public SubProfile getSubProfile(SubProfile profile) {
		System.out.println("Profile Agent: Get SubProfile");
		ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
		req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE}, profile);
		req.addRequiredOutput(OUTPUT_GETSUBPROFILE, new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE});
		ServiceResponse resp = caller.call(req);
		if (resp.getCallStatus() == CallStatus.succeeded) {
		    Object out=getReturnValue(resp.getOutputs(),OUTPUT_GETSUBPROFILE);
		    if (out != null) {
		    	System.out.println(out.toString());
		    	return (SubProfile) out;
		    } else {
		    	System.out.println("NOTHING!");
		    	return null;
		    }
		}else{
		    System.out.println("The result is: " + resp.getCallStatus().name());
		    return null;
		}
	    }

	public List getUserSubprofiles(User user) {
		System.out.println("Profile agent: get all Subprofiles for user: " + user.getURI());
		ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
		req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
		// TODO: remove this typeFilter to get all Subprofiles?
		//req.addTypeFilter(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE}, UserIDProfile.MY_URI);
		req.addRequiredOutput(OUTPUT_GETSUBPROFILES, new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE});
		ServiceResponse resp=caller.call(req);
		if (resp.getCallStatus() == CallStatus.succeeded) {
		    Object out=getReturnValue(resp.getOutputs(), OUTPUT_GETSUBPROFILES);
		    if (out != null) {
		    	System.out.println(out.toString());
		    	return resp.getOutputs();
		    } else {
		    	System.out.println("NOTHING!");
		    	return null;
		    }
		}else{
		    System.out.println("Other results: " + resp.getCallStatus().name());
		    return null;
		}
	}

	public String addUserSubprofile(User user, SubProfile subProfile) {
		System.out.println("Profile agent: add subProfile for user: " + user.getURI() + " subProfile: " + subProfile.toString());
		ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
	 	req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
	 	req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE}, subProfile);
		ServiceResponse resp=caller.call(req);
		return resp.getCallStatus().name();
	}

	public void changeUserSubprofile(User user, SubProfile subProfile) {
		// TODO Auto-generated method stub
		
	}

	public void removeUserSubprofile(User user, String subprofile_URI) {
		// TODO Auto-generated method stub
		
	}

	public List getUserSubprofiles(UserProfile profile) {
		System.out.println("Profile Agent: get Subprofiles for userprofile: " + profile.getURI());
		ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
	 	req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE}, profile);
		req.addRequiredOutput(OUTPUT_GETSUBPROFILES, new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE});
		ServiceResponse resp=caller.call(req);
		if (resp.getCallStatus() == CallStatus.succeeded) {
			System.out.println(resp.getOutputs());
		    Object out=getReturnValue(resp.getOutputs(),OUTPUT_GETSUBPROFILES);
		    if (out != null) {
		    	System.out.println(out.toString());
		    	return resp.getOutputs();
		    } else {
		    	System.out.println("NOTHING!");
		    	return null;
		    }
		}else{
		    System.out.println("Results: " + resp.getCallStatus().name());
		    return null;
		}
	}

	public String addUserSubprofile(UserProfile userProfile, SubProfile subProfile) {
		System.out.println("Profile Agent: add subprofile for userProfile: " + userProfile.getURI() + " subprofile: " + subProfile.toString());
		ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
	 	req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE}, userProfile);
	 	req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE}, subProfile);
		ServiceResponse resp=caller.call(req);
		return resp.getCallStatus().name();
	}
	
	/*** use space server ********/
	public String addSpace(AALSpace space) {
		return genericAdd(space, Path.at(ProfilingService.PROP_CONTROLS).path);
	}

	public String getSpace(AALSpace space) {
		return genericGet(space, Path.at(ProfilingService.PROP_CONTROLS).path);
	}

	public String getSpaces() {
		Request req=new Request(new ProfilingService(null));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.out(OUTPUT));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.type(AALSpace.MY_URI)); //This only works if type is set as instance restriction in serv
		ServiceResponse resp=caller.call(req);
		return getListOfResults(resp);
	}
	
	public String addSpaceProfile(AALSpaceProfile aalSpaceProfile) {
		return genericAdd(aalSpaceProfile, Path.at(ProfilingService.PROP_CONTROLS).to(Profilable.PROP_HAS_PROFILE).path);
	}

	public String getDevice(Device device) {
		return genericGet(device, Path.at(ProfilingService.PROP_CONTROLS).to(Profilable.PROP_HAS_PROFILE).to(AALSpaceProfile.PROP_INSTALLED_HARDWARE).path);
	}

	public String addDevice(Device device) {
		return genericAdd(device, Path.at(ProfilingService.PROP_CONTROLS).to(Profilable.PROP_HAS_PROFILE).to(AALSpaceProfile.PROP_INSTALLED_HARDWARE).path);
	}

	public String getSpaceProfile(AALSpaceProfile aalSpaceProfile) {
		return genericGet(aalSpaceProfile, Path.at(ProfilingService.PROP_CONTROLS).to(Profilable.PROP_HAS_PROFILE).path);
	}

	public String addDevicesToSpace(AALSpace aalSpace, Device dev) {
		Request req=new Request(new ProfilingService(null));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.in(aalSpace));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.type(AALSpace.MY_URI));
		req.put(Path.at(ProfilingService.PROP_CONTROLS).to(Profilable.PROP_HAS_PROFILE).to(AALSpaceProfile.PROP_INSTALLED_HARDWARE), Arg.add(dev));
		ServiceResponse resp=caller.call(req);
		return resp.getCallStatus().name();
	}

	public String getDevicesOfSpace(AALSpace aalSpace) {
		Request req=new Request(new ProfilingService(null));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.in(aalSpace));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.type(AALSpace.MY_URI));
		req.put(Path.at(ProfilingService.PROP_CONTROLS).to(Profilable.PROP_HAS_PROFILE).to(AALSpaceProfile.PROP_INSTALLED_HARDWARE), Arg.out(OUTPUT));
		ServiceResponse resp=caller.call(req);
		return getListOfResults(resp);
	}

	public String addService(AALService aalService) {
		return genericAdd(aalService, Path.at(ProfilingService.PROP_CONTROLS).path);
	}

	public String getService(AALService aalService) {
		return genericGet(aalService, Path.at(ProfilingService.PROP_CONTROLS).path);
	}

	public String getServices() {
		Request req=new Request(new ProfilingService(null));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.out(OUTPUT));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.type(AALService.MY_URI)); //This only works if type set as instance restriction in serv
		ServiceResponse resp=caller.call(req);
		return getListOfResults(resp);
	}
	
	public String changeService(AALService aalService) {
		// TODO Auto-generated method stub
		return null;
	}

	public String removeService(AALService aalService) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addServiceProf(AALServiceProfile aalServiceProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServiceProf(AALServiceProfile aalServiceProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	public String changeServiceProf(AALServiceProfile aalServiceProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	public String removeServiceProf(AALServiceProfile aalServiceProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addServicesToSpace(AALSpace aalSpace, AALService serv) {
		Request req=new Request(new ProfilingService(null));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.in(aalSpace));
		req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.type(AALSpace.MY_URI));
		req.put(Path.at(ProfilingService.PROP_CONTROLS).to(Profilable.PROP_HAS_PROFILE).to(AALSpaceProfile.PROP_INSTALLED_SERVICES), Arg.add(serv));
		ServiceResponse resp=caller.call(req);
		return resp.getCallStatus().name();
	}

	public String getServicesOfSpace(AALSpace aalSpace) {
		Request req=new Request(new ProfilingService(null));
	 	req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.in(aalSpace));
	 	req.put(Path.at(ProfilingService.PROP_CONTROLS), Arg.type(AALSpace.MY_URI));
	 	req.put(Path.at(ProfilingService.PROP_CONTROLS).to(Profilable.PROP_HAS_PROFILE).to(AALSpaceProfile.PROP_INSTALLED_SERVICES), Arg.out(OUTPUT));
	 	ServiceResponse resp=caller.call(req);
	 	return getListOfResults(resp);
	}

	public String getHROfServ(AALService aalService) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHWOfServ(AALService aalService) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAppOfServ(AALService aalService) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*** For uCC/uStore Web service *****/
	public String getAALSpaceProfile() {
		return null;
	}
	
	/**
	 * get user profile for user with userId
	 * (use Profiling server)
	 * 
	 * @return String representation of the User profile
	 * TODO: check with ustore the format
	 * 
	 */
	public String getUserProfile(String userID) {
		    String userURI = USER_URI_PREFIX + userID;
		    User user = new User(userURI);

		    ServiceResponse sr = caller.call(userProfileRequest(user));

		    if (sr.getCallStatus() == CallStatus.succeeded) {
		      try {
		        List outputAsList = sr.getOutput(OUTPUT_GETPROFILE, true);

		        if ((outputAsList == null) || (outputAsList.size() == 0)) {
		          return null;
		        }
		        // TODO: convert the UserProfile result to some structure?
		        return outputAsList.get(0).toString();
		      } catch (Exception e) {
		        return null;
		      }
		    }
			return null;
	}
	
	/**** utility functions *****/
	private String genericAdd(Resource res, String[] path) {
		ServiceResponse resp = caller.call(UtilEditor.requestAdd(
			ProfilingService.MY_URI, path, Arg.add(res)));
		return resp.getCallStatus().name();
	}
	
	private String genericGet(Resource res, String[] path) {
		ServiceResponse resp = caller.call(UtilEditor.requestGet(
			ProfilingService.MY_URI, path, Arg.in(res),
			Arg.out(OUTPUT)));
		
		return getListOfResults(resp);
	}
	
	private String getListOfResults(ServiceResponse resp){
		if (resp.getCallStatus() == CallStatus.succeeded) {
		    Object out=getReturnValue(resp.getOutputs(),OUTPUT);
		    if (out != null) {
		    	System.out.println(out.toString());
		    	return out.toString();
		    } else {
		    	System.out.println("NOTHING!");
		    	return "nothing";
		    }
		}else{
		    return resp.getCallStatus().name();
		}
	   }
}
