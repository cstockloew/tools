
package org.universAAL.ontology;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;


import org.universAAL.ontology.cleaning.CleaningRobot;
import org.universAAL.ontology.cleaning.Washing;
import org.universAAL.ontology.cleaning.CleaningManagement;
import org.universAAL.ontology.cleaning.VacuumCleaning;
import org.universAAL.ontology.cleaning.Cleaning;

public class CleaningFactory extends ResourceFactoryImpl {


  public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {

	switch (factoryIndex) {
     case 0:
       return new CleaningRobot(instanceURI);
     case 1:
       return new Washing(instanceURI);
     case 2:
       return new CleaningManagement(instanceURI);
     case 3:
       return new VacuumCleaning(instanceURI);

	}
	return null;
  }
}
