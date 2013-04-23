package org.universAAL.ucc.profile.agent.impl;

import java.util.Iterator;
import java.util.List;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.profile.AALSpaceProfile;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ontology.profile.service.ProfilingService;
import org.universAAL.ontology.profile.userid.UserIDProfile;
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
	  
	  public UserProfile getUserProfile(String userID) {
		    String userURI = USER_URI_PREFIX + userID;
		    User user = new User(userURI);

		    ServiceResponse sr = caller.call(userProfileRequest(user));

		    if (sr.getCallStatus() == CallStatus.succeeded) {
		      try {
		        List outputAsList = sr.getOutput(OUTPUT_GETPROFILE, true);

		        if ((outputAsList == null) || (outputAsList.size() == 0)) {
		          return null;
		        }
		        return (UserProfile)outputAsList.get(0);
		      } catch (Exception e) {
		        return null;
		      }
		    }
			return null;
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

	public String getUserSubprofiles(User user) {
		System.out.println("Profile agent: get all Subprofiles for user: " + user.getURI());
		ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
		req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS}, user);
		// TODO: remove this typeFilter to get all Subprofiles?
		req.addTypeFilter(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE}, UserIDProfile.MY_URI);
		req.addRequiredOutput(OUTPUT_GETSUBPROFILES, new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE});
		ServiceResponse resp=caller.call(req);
		if (resp.getCallStatus() == CallStatus.succeeded) {
		    Object out=getReturnValue(resp.getOutputs(),OUTPUT_GETSUBPROFILES);
		    if (out != null) {
		    	System.out.println(out.toString());
		    	return out.toString();
		    } else {
		    	System.out.println("NOTHING!");
		    	return "nothing";
		    }
		}else{
		    System.out.println("Other results: " + resp.getCallStatus().name());
		    return resp.getCallStatus().name();
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

	public String getUserSubprofiles(UserProfile profile) {
		System.out.println("Profile Agent: get Subprofiles for userprofile: " + profile.getURI());
		ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
	 	req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE}, profile);
		req.addRequiredOutput(OUTPUT_GETSUBPROFILES, new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE});
		ServiceResponse resp=caller.call(req);
		if (resp.getCallStatus() == CallStatus.succeeded) {
		    Object out=getReturnValue(resp.getOutputs(),OUTPUT_GETSUBPROFILES);
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

	public String addUserSubprofile(UserProfile userProfile, SubProfile subProfile) {
		System.out.println("Profile Agent: add subprofile for userProfile: " + userProfile.getURI() + " subprofile: " + subProfile.toString());
		ServiceRequest req=new ServiceRequest(new ProfilingService(),null);
	 	req.addValueFilter(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE}, userProfile);
	 	req.addAddEffect(new String[]{ProfilingService.PROP_CONTROLS,Profilable.PROP_HAS_PROFILE,Profile.PROP_HAS_SUB_PROFILE}, subProfile);
		ServiceResponse resp=caller.call(req);
		return resp.getCallStatus().name();
	}

}
