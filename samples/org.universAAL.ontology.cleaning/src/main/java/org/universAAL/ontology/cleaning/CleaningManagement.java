package org.universAAL.ontology.cleaning;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;

import org.universAAL.middleware.service.owl.Service;


public class CleaningManagement extends Service {
  public static final String MY_URI = CleaningOntology.NAMESPACE
    + "CleaningManagement";
  public static final String PROP_MANAGES = CleaningOntology.NAMESPACE
    + "manages";


  public CleaningManagement () {
    super();
  }
  
  public CleaningManagement (String uri) {
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
      && hasProperty(PROP_MANAGES);
  }

  public CleaningRobot[] getManages() {
    Object propList = getProperty(PROP_MANAGES);
    if (propList instanceof List)
      return (CleaningRobot[]) ((List) propList).toArray(new CleaningRobot[0]);
    else if (propList != null)
      return new CleaningRobot[] {(CleaningRobot)propList}; // Handle special case of a single item not contained in a list
    return new CleaningRobot[0];
  }

  public void addManages(CleaningRobot newValue) {
    Object propList = getProperty(PROP_MANAGES);
    List newList;
    if (propList instanceof List)
      newList = (List)propList;
    else {
      newList = new ArrayList();
      if (propList != null)
        newList.add(propList); // Handle special case of a single previous item not contained in a list
    }
    newList.add(newValue);
    changeProperty(PROP_MANAGES, newList);
  }
  

  public void setManages(CleaningRobot[] propertyValue) {
    List propList = new ArrayList(propertyValue.length);
    for (int i = 0; i < propertyValue.length; i++) {
      propList.add(propertyValue[i]);
    }
    changeProperty(PROP_MANAGES, propList);
  }
}
