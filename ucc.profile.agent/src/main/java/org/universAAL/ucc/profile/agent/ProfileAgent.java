package org.universAAL.ucc.profile.agent;

import java.util.List;

import org.universAAL.ontology.profile.AALSpaceProfile;
import org.universAAL.ontology.profile.SubProfile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.UserProfile;

/**
 * Interface for the actual profile storage and retrieval.
 * 
 * Implementations of this interface go in {@link org.universAAL.ucc.profile.agent.impl} package; this way the
 * actual storage of profiles can be expanded to other methods and service providers can select from these methods the
 * one that fits best and has best performance, or they can implement their own storage method.
 * 
 * @author
 */

public interface ProfileAgent {

	public String addUser(User user);
	
	public User getUser(User user);
	
  /**
   * Returns an {@link org.universAAL.ontology.profile.UserProfile} object from the
   * profile log that are associated with the given user.
   * 
   * @param user
   * 
   * @return the user profile of the user
   */
  public UserProfile getUserProfile(User user);

  /**
   * Stores the new {@link org.universAAL.ontology.profile.UserProfile} for the user with userID.
   * 
   * @param user
   * 
   * @param userProfile
   * The user profile to be added
   * 
   * @return the result of the operation
   */
  public String addUserProfile(User user, UserProfile userProfile);
  
  /**
   * Changes the existing {@link org.universAAL.ontology.profile.UserProfile} for user with userID.
   * 
   * @param user
   * 
   * @param userProfile
   * The user profile to be changed
   */ 
  public void changeUserProfile(User user, UserProfile userProfile);
  
  /**
   * removes the existing {@link org.universAAL.ontology.profile.UserProfile} for user with userID.
   * 
   * @param user
   */ 
  public void removeUserProfile(User user);
  
  //********** below are methods for user subprofiles (to be defined), e.g., for username/passwords/ etc.
  //********** the subprofile is identified by MY_URI
  /**
   * Returns all {@link org.universAAL.ontology.profile.SubProfile} objects from the
   * profile log that are associated with the given user.
   * 
   * @param user
   * 
   * @return the user subprofiles of the user
   */
  //TODO: change to List<SubProfile>?
  public String getUserSubprofiles(User user);
  
  public String getUserSubprofiles(UserProfile profile);
  
  /**
   * Stores the new {@link org.universAAL.ontology.profile.SubProfile} for the user with userID.
   * 
   * @param user
   * 
   * @param subProfile
   * The user subprofile to be added
   * 
   * @return the result of the operation
   */
  public String addUserSubprofile(User user, SubProfile subProfile);
   
  public String addUserSubprofile(UserProfile profile, SubProfile subProfile);
  
  /**
   * Changes the existing {@link org.universAAL.ontology.profile.UserProfile} for user with userID.
   * 
   * @param user
   * 
   * @param subProfile
   * The user subprofile to be changed
   */ 
  public void changeUserSubprofile(User user, SubProfile subProfile);
  
  /**
   * removes the existing {@link org.universAAL.ontology.profile.UserProfile} for user with userID.
   * 
   * @param user
   */ 
  public void removeUserSubprofile(User user, String subprofile_URI);

  /**
   * Returns an {@link org.universAAL.ontology.profile.AALSpaceProfile} list from the
   * profile log that are associated with the given user.
   * 
   * @param user
   * The user who performed the operation
   * 
   * @return list of AALSpace profiles, which owner is the given user
   */
  public List<AALSpaceProfile> getAALSpaceProfiles(User user);
  
  
  /**
   * Stores the new {@link org.universAAL.ontology.profile.AALSpaceProfile} that was performed by the user.
   * 
   * @param user
   * The user who performed the action   
   * 
   * @param aalSpaceProfile
   * The AAL space profile for the user
   */
  public void addAALSpaceProfile(User user, AALSpaceProfile aalSpaceProfile);
  
  /**
   * Changes the existing {@link org.universAAL.ontology.profile.AALSpaceProfile} that was performed by the user.
   * 
   * @param user
   * 
   * @param aalSpaceProfile
   * The AAL space profile for the user to be changed
   */
  public void changeAALSpaceProfile(User user, AALSpaceProfile aalSpaceProfile);
  

  /**
   * Removes the existing {@link org.universAAL.ontology.profile.AALSpaceProfile} that was performed by the user.
   * 
   * @param user
   * The user whose AAL space profile is to be removed
   */
  public void removeAALSpaceProfile(User user);
}
