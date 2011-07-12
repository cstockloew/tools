package org.universAAL.ucc.core.api;

import org.universAAL.middleware.owl.ManagedIndividual;

public interface IConfigurator {

	public ManagedIndividual[] getOntologyContent(String uri);
	public void performConfiguration(String Path);
	
}
