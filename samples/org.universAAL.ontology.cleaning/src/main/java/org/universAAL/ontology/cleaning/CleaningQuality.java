package org.universAAL.ontology.cleaning;

import org.universAAL.middleware.owl.ManagedIndividual;

public class CleaningQuality extends ManagedIndividual {
  public static final String MY_URI = CleaningOntology.NAMESPACE
    + "CleaningQuality";

  public static final int QUICK = 0;
  public static final int NORMAL = 1;
  public static final int FULL = 2;

  private static final String[] names = {
    "quick","normal","full" };

  public static final CleaningQuality quick = new CleaningQuality(QUICK);
  public static final CleaningQuality normal = new CleaningQuality(NORMAL);
  public static final CleaningQuality full = new CleaningQuality(FULL);

  private int order;

  private CleaningQuality(int order) {
    super(CleaningOntology.NAMESPACE + names[order]);
    this.order = order;
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_OPTIONAL;
  }

  public boolean isWellFormed() {
    return true;
  }

  public String name() {
    return names[order];
  }

  public int ord() {
    return order;
  }

  public String getClassURI() {
    return MY_URI;
  }

  public static CleaningQuality getCleaningQualityByOrder(int order) {
    switch (order) {
      case QUICK:
        return quick;
      case NORMAL:
        return normal;
      case FULL:
        return full;
    default:
      return null;    }
  }

  public static final CleaningQuality valueOf(String name) {
	if (name == null)
	    return null;

	if (name.startsWith(CleaningOntology.NAMESPACE))
	    name = name.substring(CleaningOntology.NAMESPACE.length());

	for (int i = QUICK; i <= FULL; i++)
	    if (names[i].equals(name))
		return getCleaningQualityByOrder(i);

	return null;
  }
}
