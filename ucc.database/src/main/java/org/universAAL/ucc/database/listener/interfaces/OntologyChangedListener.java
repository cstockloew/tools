package org.universAAL.ucc.database.listener.interfaces;

import org.universAAL.ucc.database.model.jaxb.OntologyInstance;

public interface OntologyChangedListener {
	public void ontologyDeleted(OntologyInstance ont);
	public void ontologyChanged(OntologyInstance ont);
	public void ontologySaved(OntologyInstance ont);
}
