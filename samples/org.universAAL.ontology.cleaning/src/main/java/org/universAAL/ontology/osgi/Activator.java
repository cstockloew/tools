
package org.universAAL.ontology.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.ontology.cleaning.*;


public class Activator implements BundleActivator {

  static BundleContext context = null;
  
  CleaningOntology _cleaningontology = new CleaningOntology();
  
  

  public void start(BundleContext context) throws Exception {
    Activator.context = context;
    
    OntologyManagement.getInstance().register(_cleaningontology);
    
  }

  public void stop(BundleContext arg0) throws Exception {
    
    OntologyManagement.getInstance().unregister(_cleaningontology);
    
  }
}	
