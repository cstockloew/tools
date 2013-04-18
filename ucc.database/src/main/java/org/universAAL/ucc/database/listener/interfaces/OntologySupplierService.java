package org.universAAL.ucc.database.listener.interfaces;

import java.util.ArrayList;

import org.universAAL.ucc.database.model.jaxb.OntologyInstance;

public interface OntologySupplierService {
	public void addListener(OntologyChangedListener listener);
	public void removeListener(OntologyChangedListener listener);
	public ArrayList<OntologyInstance> getOntology(String ontologyFile);

}
