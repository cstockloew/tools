package org.universAAL.ucc.profile.agent.osgi;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.ontology.profile.AALSpaceProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ontology.profile.userid.UserIDProfile;
import org.universAAL.ucc.profile.agent.ProfileAgent;
import org.universAAL.ucc.profile.agent.impl.ProfileAgentImpl;
import org.universAAL.middleware.serialization.MessageContentSerializerEx;

public class Activator implements BundleActivator, ServiceListener {

  public static BundleContext context = null;

  private static ModuleContext moduleContext = null;

  private ProfileAgentImpl profileAgent;

  protected static MessageContentSerializerEx ser;
  public static MessageContentSerializerEx parser = null;

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext )
   */
  public void start(BundleContext context) throws Exception {
	  Activator.context = context;
		Activator.moduleContext = uAALBundleContainer.THE_CONTAINER
			.registerModule(new Object[] { context });
		ser = (MessageContentSerializerEx) context.getService(context
			.getServiceReference(MessageContentSerializerEx.class.getName()));
		// Look for MessageContentSerializer of mw.data.serialization
		String filter = "(objectclass="
			+ MessageContentSerializerEx.class.getName() + ")";
		context.addServiceListener(this, filter);
		ServiceReference references[] = context.getServiceReferences(null,
			filter);
		for (int i = 0; references != null && i < references.length; i++)
		    this.serviceChanged(new ServiceEvent(ServiceEvent.REGISTERED,
			    references[i]));

    profileAgent = new ProfileAgentImpl(moduleContext);
    this.context.registerService(ProfileAgent.class.getName(), new ProfileAgentImpl(moduleContext), null);

    test();
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {

  }

  public void test() {
    ProfileAgentImpl agent = new ProfileAgentImpl(moduleContext);

    String userID = "Maria";
    String userURI = "urn:org.universAAL.aal_space:test_env#"+userID;
    String userProfileURI = "urn:org.universAAL.aal_space:test_env#Maria_user_profile";
    String userIDProfileURI = "urn:org.universAAL.aal_space:test_env#Maria_userID_profile";
    String aalSpaceProfileURI = "urn:org.universAAL.aal_space:test_env#some_space_profile";
    User maria = new User(userURI);
    UserProfile userProfile = new UserProfile(userProfileURI);
    AALSpaceProfile aalSpaceProfile = new AALSpaceProfile(aalSpaceProfileURI);

    /*test add/get user*/
    System.out.println("[TEST] adding user ...");
    System.out.println("Result is: " + agent.addUser(maria));
    System.out.println("[TEST] getting user...");
    System.out.println("Result is: " + agent.getUser(maria));
    
   
    /*test add/get user profile*/
    System.out.println("[TEST] adding user profile...");
    System.out.println("Result is: " + agent.addUserProfile(maria, userProfile));

    System.out.println("[TEST] getting user profile...");
    UserProfile gottenUserProfile = agent.getUserProfile(maria);
    System.out.println("[TEST] gotten user profile:" + gottenUserProfile.toString());

    /*test get user profile using userID*/
    System.out.println("[TEST] getting user profile with ID..." + userID);
    System.out.println("Result is: " + agent.getUserProfile(userID));
    
    /*test add/get user subprofile --- doesn't seem to work!!!*/
    System.out.println("[TEST] adding user ID subprofile...");
    UserIDProfile userIDProfile = new UserIDProfile(userIDProfileURI);
    userIDProfile.setUSERNAME("Maria");
    userIDProfile.setPASSWORD("Pass");
    System.out.println("Result is: " + agent.addUserSubprofile(maria, userIDProfile));

    System.out.println("[TEST] getting user ID subprofile...");
    String gotUserIdProfile = agent.getUserSubprofiles(maria);
    System.out.println("[TEST] gotten user ID profiles:" + gotUserIdProfile);
    
    /*test add/get user subprofile via user profile - doesn't seem to work for get!!!! */
    System.out.println("[TEST] adding user ID subprofile...");
    
    System.out.println("Result is: " + agent.addUserSubprofile(userProfile, userIDProfile));

    System.out.println("[TEST] getting user ID subprofile...");
    String gotUserIdProfile2 = agent.getUserSubprofiles(userProfile);
    System.out.println("[TEST] gotten user ID profiles:" + gotUserIdProfile2);

    /*test add/get space profile*/
    System.out.println("[TEST] adding aalspace profile...");
    //agent.addAALSpaceProfile(userID, aalSpaceProfile);

//    System.out.println("[TEST] getting aalspace profiles...");
//    ArrayList gottenAALSpaceProfiles = agent.getAALSpaceProfiles(userID);
//    for (Object pr : gottenAALSpaceProfiles) {
//      System.out.println("[TEST] gotten aalspace profile:" + pr);
//    }          
  }


public void serviceChanged(ServiceEvent arg0) {
	// TODO Auto-generated method stub
	
}
}
