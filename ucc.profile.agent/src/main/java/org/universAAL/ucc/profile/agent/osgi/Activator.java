package org.universAAL.ucc.profile.agent.osgi;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
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
import org.universAAL.middleware.serialization.MessageContentSerializerEx;

public class Activator implements BundleActivator, ServiceListener {

  public static BundleContext context = null;

  private static ModuleContext moduleContext = null;
  protected MessageContentSerializerEx ser;
  public MessageContentSerializerEx parser = null;

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
    List gotUserIdProfile = agent.getUserSubprofiles(maria);
    System.out.println("[TEST] gotten user ID profiles:" + gotUserIdProfile.toString());
    
    /*test add/get user subprofile via user profile - doesn't seem to work for get!!!! */
    System.out.println("[TEST] adding user ID subprofile...");
    
    System.out.println("Result is: " + agent.addUserSubprofile(userProfile, userIDProfile));

    System.out.println("[TEST] getting user ID subprofile...");
    SubProfile gotUserIdProfile2 = (SubProfile) agent.getUserSubprofiles(userProfile);
    System.out.println("[TEST] gotten user ID profiles:" + gotUserIdProfile2.getURI());

    /**** TESTING SPACE SERVER *************/
    /*test add/get space*/
    System.out.println("[TEST] adding AAL space ..." + space.getURI());
    System.out.println("Result is: " + agent.addSpace(space));
    System.out.println("[TEST] getting AAL space..." + space.getURI());
    System.out.println("Result is: " + agent.getSpace(space));
    System.out.println("[TEST] getting all AAL spaces...");
    System.out.println("Result is: " + agent.getSpaces());
    
    
    /*test add/get space profile*/
    System.out.println("[TEST] adding aalspace profile..." + aalSpaceProfile.getURI());
    System.out.println("Result is: " + agent.addSpaceProfile(aalSpaceProfile));
    System.out.println("[TEST] getting AAL space profile ..." + aalSpaceProfile.getURI());
    System.out.println("Result is: " + agent.getSpaceProfile(aalSpaceProfile));
    
    /*test add/get devices*/
    System.out.println("[TEST] adding device 1..." + device1.getURI());
    System.out.println("Result is: " + agent.addDevice(device1));
    System.out.println("[TEST] adding device 2..." + device2.getURI());
    System.out.println("Result is: " + agent.addDevice(device2));
    System.out.println("[TEST] getting device 1..." + device1.getURI());
    System.out.println("Result is: " + agent.getDevice(device1));
    System.out.println("[TEST] adding device 1 to space..." + device1.getURI() + " " + space.getURI());
    System.out.println("Result is: " + agent.addDevicesToSpace(space, device1));
    System.out.println("[TEST] adding device 2 to space..." + device2.getURI() + " " + space.getURI());
    System.out.println("Result is: " + agent.addDevicesToSpace(space, device2));
    /** This does not work !!!!**/
    System.out.println("[TEST] getting devices of space..." + space.getURI());
    System.out.println("Result is: " + agent.getDevicesOfSpace(space));
    
    /*test add/get services*/
    System.out.println("[TEST] adding service 1..." + service1.getURI());
    System.out.println("Result is: " + agent.addService(service1));
    System.out.println("[TEST] adding service 2..." + service2.getURI());
    System.out.println("Result is: " + agent.addService(service2));
    System.out.println("[TEST] getting service 1..." + service1.getURI());
    System.out.println("Result is: " + agent.getService(service1));
    System.out.println("[TEST] adding service 1 to space..." + service1.getURI() + " " + space.getURI());
    System.out.println("Result is: " + agent.addServicesToSpace(space, service1));
    System.out.println("[TEST] adding service 2 to space..."  + service2.getURI() + " " + space.getURI());
    System.out.println("Result is: " + agent.addServicesToSpace(space, service2));
    /** This does not work !!!!**/
    System.out.println("[TEST] getting services of space..." + space.getURI());
    System.out.println("Result is: " + agent.getServicesOfSpace(space));
  }


public void serviceChanged(ServiceEvent event) {
	// Update the MessageContentSerializer
		switch (event.getType()) {
		case ServiceEvent.REGISTERED:
		case ServiceEvent.MODIFIED:
		    this.parser = ((MessageContentSerializerEx) context.getService(event
			    .getServiceReference()));
		    break;
		case ServiceEvent.UNREGISTERING:
		    this.parser = (null);
		    break;
		}
}

}
