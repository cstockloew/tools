package org.universAAL.ucc.profile.agent.osgi;

import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.profile.AALService;
import org.universAAL.ontology.profile.AALSpace;
import org.universAAL.ontology.profile.AALSpaceProfile;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;
import org.universAAL.ontology.profile.userid.UserIDProfile;
import org.universAAL.ucc.profile.agent.ProfileAgent;
import org.universAAL.ucc.profile.agent.impl.ProfileAgentImpl;
import org.universaal.ontology.health.owl.HealthProfile;
import org.universaal.ontology.health.owl.TakeMeasurementActivity;
import org.universaal.ontology.health.owl.Treatment;

public class Activator implements BundleActivator {

  public static BundleContext context = null;

  private static ModuleContext moduleContext = null;
  
  private final static org.slf4j.Logger log = LoggerFactory.getLogger(Activator.class);
  
  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext )
   */
  public void start(BundleContext context) throws Exception {
	  Activator.context = context;
		Activator.moduleContext = uAALBundleContainer.THE_CONTAINER
			.registerModule(new Object[] { context });
		
		log.info("starting Activator");
    this.context.registerService(ProfileAgent.class.getName(), new ProfileAgentImpl(moduleContext), null);

    log.info("start testing...");
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
    String healthProfileURI = "urn:org.universAAL.aal_space:test_env#Maria_health_profile";
    String aalSpaceProfileURI = "urn:org.universAAL.aal_space:test_env#some_space_profile";
    String device1URI = "urn:org.universAAL.aal_space:test_env#Device1";
    String device2URI = "urn:org.universAAL.aal_space:test_env#Device2";
    String spaceURI = "urn:org.universAAL.aal_space:test_env#space";
    String service1URI = "urn:org.universAAL.aal_space:test_env#Service1";
    String service2URI = "urn:org.universAAL.aal_space:test_env#Service2";
    
    User maria = new User(userURI);
    UserProfile userProfile = new UserProfile(userProfileURI);
    AALSpaceProfile aalSpaceProfile = new AALSpaceProfile(aalSpaceProfileURI);
    
    Device device1 = new Device(device1URI);
    Device device2 = new Device(device2URI);
    
    AALSpace space = new AALSpace(spaceURI);
    
    AALService service1 = new AALService(service1URI);
    AALService service2 = new AALService(service2URI);
    
    /**** TESTING PROFILING SERVER *************/
    /*test add/get user*/
    System.out.println("[TEST] adding user ..."+maria.getURI());
    System.out.println("Result is: " + agent.addUser(maria));
    System.out.println("[TEST] getting user...");
    System.out.println("Result is: " + agent.getUser(maria));
    
    /*test add/get user profile*/
    System.out.println("[TEST] adding user profile..." + userProfile.toString());
    System.out.println("Result is: " + agent.addUserProfile(userProfile));

    System.out.println("[TEST] adding user profile to user ...");
    System.out.println("Result is: " + agent.addUserProfile(maria, userProfile));

    System.out.println("[TEST] getting user profile...");
    UserProfile gottenUserProfile = agent.getUserProfile(maria);
    System.out.println("[TEST] gotten user profile:" + gottenUserProfile.toString());  

    /*test get user profile using userID*/
    System.out.println("[TEST] getting user profile with ID..." + userID);
    System.out.println("Result is: " + agent.getUserProfile(userID)); 
    
    /*test add/get user subprofile via User */
    System.out.println("[TEST] adding user ID subprofile...");
    UserIDProfile userIDProfile = new UserIDProfile(userIDProfileURI);
    userIDProfile.setUSERNAME("Maria");
    userIDProfile.setPASSWORD("Pass");
    System.out.println("Add userId subprofile: " + agent.addSubProfile(userIDProfile));
    System.out.println("Add subprofile to user Maria. " + userIDProfile.getUSERNAME() + "/" + userIDProfile.getPASSWORD());
    System.out.println("Add subprofile to user Maria. Result is: " + agent.addUserSubprofile(maria, userIDProfile));

    HealthProfile healthProfile = new HealthProfile(healthProfileURI);
    healthProfile.addTreatment(new TakeMeasurementActivity(healthProfileURI+"treatment"));
    System.out.println("Add userId subprofile: " + agent.addSubProfile(healthProfile));
    System.out.println("Add health subprofile to user Maria. " + healthProfile.getPropertyURIs());
    System.out.println("Add subprofile to user Maria. Result is: " + agent.addUserSubprofile(maria, healthProfile));
    
    
    System.out.println("[TEST] getting user subprofiles...");
    List gotUserIdProfile = agent.getUserSubprofiles(maria);
    System.out.println("[TEST] gotten user ID profiles:" + gotUserIdProfile.toString());
    
    /*test add/get user subprofile via user profile  */
    System.out.println("[TEST] adding user ID subprofile...");
    
    System.out.println("Result is: " + agent.addUserSubprofile(userProfile, userIDProfile));
    
    System.out.println("[TEST] adding health subprofile...");
    
    System.out.println("Result is: " + agent.addUserSubprofile(userProfile, healthProfile));

    System.out.println("[TEST] getting user subprofiles...");
    log.info("[TEST] getting user subprofiles...");
    List gotUserIdProfile2 = agent.getUserSubprofiles(userProfile);
    if (gotUserIdProfile2==null) log.info("[TEST] the result is null");
    else {
    	log.info("[TEST] gotten user subprofiles:" + gotUserIdProfile2.toString());
    	System.out.println("[TEST] gotten user subprofiles:" + gotUserIdProfile2.toString());
    }
 
  }



}
