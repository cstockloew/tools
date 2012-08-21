package org.universAAL.ontology.cleaning;

import org.universAAL.middleware.owl.ManagedIndividual;

public class RobotStatus extends ManagedIndividual {
  public static final String MY_URI = CleaningOntology.NAMESPACE
    + "RobotStatus";

  public static final int CHARGING = 0;
  public static final int IDLE = 1;
  public static final int CLEANING = 2;
  public static final int RETURNING = 3;
  public static final int STUCK = 4;
  public static final int FULL = 5;
  public static final int MAINTENANCE_NEEDED = 6;

  private static final String[] names = {
    "charging","idle","cleaning","returning","stuck","full","maintenanceNeeded" };

  public static final RobotStatus charging = new RobotStatus(CHARGING);
  public static final RobotStatus idle = new RobotStatus(IDLE);
  public static final RobotStatus cleaning = new RobotStatus(CLEANING);
  public static final RobotStatus returning = new RobotStatus(RETURNING);
  public static final RobotStatus stuck = new RobotStatus(STUCK);
  public static final RobotStatus full = new RobotStatus(FULL);
  public static final RobotStatus maintenanceNeeded = new RobotStatus(MAINTENANCE_NEEDED);

  private int order;

  private RobotStatus(int order) {
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

  public static RobotStatus getRobotStatusByOrder(int order) {
    switch (order) {
      case CHARGING:
        return charging;
      case IDLE:
        return idle;
      case CLEANING:
        return cleaning;
      case RETURNING:
        return returning;
      case STUCK:
        return stuck;
      case FULL:
        return full;
      case MAINTENANCE_NEEDED:
        return maintenanceNeeded;
    default:
      return null;    }
  }

  public static final RobotStatus valueOf(String name) {
	if (name == null)
	    return null;

	if (name.startsWith(CleaningOntology.NAMESPACE))
	    name = name.substring(CleaningOntology.NAMESPACE.length());

	for (int i = CHARGING; i <= MAINTENANCE_NEEDED; i++)
	    if (names[i].equals(name))
		return getRobotStatusByOrder(i);

	return null;
  }
}
