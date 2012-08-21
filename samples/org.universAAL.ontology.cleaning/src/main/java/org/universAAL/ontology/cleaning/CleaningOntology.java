
package org.universAAL.ontology.cleaning;

import org.universAAL.middleware.owl.BoundingValueRestriction;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.location.LocationOntology;

import org.universAAL.ontology.phThing.Device;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.service.owl.Service;


//import the factory for this ontology
import org.universAAL.ontology.CleaningFactory;


/**
 * @author AAL Studio: UML2Java transformation
 */
public final class CleaningOntology extends Ontology {

  private static CleaningFactory factory = new CleaningFactory();
  public static final String NAMESPACE ="http://ontology.universAAL.org/Cleaning#";
	
  public CleaningOntology() {
    super(NAMESPACE);
  }

  public void create() {
    Resource r = getInfo();
    r.setResourceComment("This ontology defines services for cleaning");
    r.setResourceLabel("cleaning");
    addImport(DataRepOntology.NAMESPACE);
    addImport(ServiceBusOntology.NAMESPACE);
    addImport(LocationOntology.NAMESPACE);
		
    


    // ******* Declaration of enumeration classes of the ontology ******* //

    OntClassInfoSetup oci_CleaningQuality = createNewAbstractOntClassInfo(CleaningQuality.MY_URI);
    OntClassInfoSetup oci_RobotStatus = createNewAbstractOntClassInfo(RobotStatus.MY_URI);


    // ******* Declaration of regular classes of the ontology ******* //
    OntClassInfoSetup oci_CleaningRobot = createNewOntClassInfo(CleaningRobot.MY_URI, factory, 0);
    OntClassInfoSetup oci_Washing = createNewOntClassInfo(Washing.MY_URI, factory, 1);
    OntClassInfoSetup oci_CleaningManagement = createNewOntClassInfo(CleaningManagement.MY_URI, factory, 2);
    OntClassInfoSetup oci_VacuumCleaning = createNewOntClassInfo(VacuumCleaning.MY_URI, factory, 3);
    OntClassInfoSetup oci_Cleaning = createNewAbstractOntClassInfo(Cleaning.MY_URI);


    // ******* Add content to enumeration classes of the ontology ******* //

    oci_CleaningQuality.setResourceComment("");
    oci_CleaningQuality.setResourceLabel("CleaningQuality");
    oci_CleaningQuality.toEnumeration(new ManagedIndividual[] {
       CleaningQuality.quick, CleaningQuality.normal, CleaningQuality.full });

    oci_RobotStatus.setResourceComment("");
    oci_RobotStatus.setResourceLabel("RobotStatus");
    oci_RobotStatus.toEnumeration(new ManagedIndividual[] {
       RobotStatus.charging, RobotStatus.idle, RobotStatus.cleaning, RobotStatus.returning, RobotStatus.stuck, RobotStatus.full, RobotStatus.maintenanceNeeded });



    // ******* Add content to regular classes of the ontology ******* //
    oci_CleaningRobot.setResourceComment("");
    oci_CleaningRobot.setResourceLabel("CleaningRobot");
    oci_CleaningRobot.addSuperClass(Device.MY_URI); 
    oci_CleaningRobot.addObjectProperty(CleaningRobot.PROP_CLEANING_QUALITY).setFunctional();
    oci_CleaningRobot.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(CleaningRobot.PROP_CLEANING_QUALITY, 
      CleaningQuality.MY_URI, 1, 1));
    
    oci_CleaningRobot.addObjectProperty(CleaningRobot.PROP_ROBOT_STATUS).setFunctional();
    oci_CleaningRobot.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(CleaningRobot.PROP_ROBOT_STATUS, 
      RobotStatus.MY_URI, 1, 1));
    
    oci_Washing.setResourceComment("");
    oci_Washing.setResourceLabel("Washing");
    oci_Washing.addSuperClass(Cleaning.MY_URI); 
    oci_CleaningManagement.setResourceComment("");
    oci_CleaningManagement.setResourceLabel("CleaningManagement");
    oci_CleaningManagement.addSuperClass(Service.MY_URI); 
    oci_CleaningManagement.addObjectProperty(CleaningManagement.PROP_MANAGES).setFunctional();
      	oci_CleaningManagement.addRestriction(MergedRestriction.getAllValuesRestriction(CleaningManagement.PROP_MANAGES,  
       	CleaningRobot.MY_URI));
	    
    oci_VacuumCleaning.setResourceComment("");
    oci_VacuumCleaning.setResourceLabel("VacuumCleaning");
    oci_VacuumCleaning.addSuperClass(Cleaning.MY_URI); 
    oci_Cleaning.setResourceComment("");
    oci_Cleaning.setResourceLabel("Cleaning");
    oci_Cleaning.addSuperClass(Service.MY_URI); 
    oci_Cleaning.addObjectProperty(Cleaning.PROP_OPERATES).setFunctional();
      	oci_Cleaning.addRestriction(MergedRestriction.getAllValuesRestriction(Cleaning.PROP_OPERATES,  
       	CleaningRobot.MY_URI));
	    
  }
}
