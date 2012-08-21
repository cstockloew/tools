package org.universAAL.ontology.cleaning;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;

import org.universAAL.ontology.phThing.Device;


public class CleaningRobot extends Device {
  public static final String MY_URI = CleaningOntology.NAMESPACE
    + "CleaningRobot";
  public static final String PROP_CLEANING_QUALITY = CleaningOntology.NAMESPACE
    + "cleaningQuality";
  public static final String PROP_ROBOT_STATUS = CleaningOntology.NAMESPACE
    + "robotStatus";


  public CleaningRobot () {
    super();
  }
  
  public CleaningRobot (String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	// TODO Implement or if for Device subclasses: remove 
	return 0;
  }

  public boolean isWellFormed() {
	return true 
      && hasProperty(PROP_CLEANING_QUALITY)
      && hasProperty(PROP_ROBOT_STATUS);
  }

  public RobotStatus getRobotStatus() {
    return (RobotStatus)getProperty(PROP_ROBOT_STATUS);
  }		

  public void setRobotStatus(RobotStatus newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP_ROBOT_STATUS, newPropValue);
  }		

  public CleaningQuality getCleaningQuality() {
    return (CleaningQuality)getProperty(PROP_CLEANING_QUALITY);
  }		

  public void setCleaningQuality(CleaningQuality newPropValue) {
    if (newPropValue != null)
      changeProperty(PROP_CLEANING_QUALITY, newPropValue);
  }		
}
