package org.universAAL.ontology.cleaning;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;

import org.universAAL.middleware.service.owl.Service;


public abstract class Cleaning extends Service {
  public static final String MY_URI = CleaningOntology.NAMESPACE
    + "Cleaning";
  public static final String PROP_OPERATES = CleaningOntology.NAMESPACE
    + "operates";


  public Cleaning () {
    super();
  }
  
  public Cleaning (String uri) {
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
      && hasProperty(PROP_OPERATES);
  }

  public CleaningRobot[] getOperates() {
    Object propList = getProperty(PROP_OPERATES);
    if (propList instanceof List)
      return (CleaningRobot[]) ((List) propList).toArray(new CleaningRobot[0]);
    else if (propList != null)
      return new CleaningRobot[] {(CleaningRobot)propList}; // Handle special case of a single item not contained in a list
    return new CleaningRobot[0];
  }

  public void addOperates(CleaningRobot newValue) {
    Object propList = getProperty(PROP_OPERATES);
    List newList;
    if (propList instanceof List)
      newList = (List)propList;
    else {
      newList = new ArrayList();
      if (propList != null)
        newList.add(propList); // Handle special case of a single previous item not contained in a list
    }
    newList.add(newValue);
    changeProperty(PROP_OPERATES, newList);
  }
  

  public void setOperates(CleaningRobot[] propertyValue) {
    List propList = new ArrayList(propertyValue.length);
    for (int i = 0; i < propertyValue.length; i++) {
      propList.add(propertyValue[i]);
    }
    changeProperty(PROP_OPERATES, propList);
  }
}
