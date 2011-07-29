package org.universAAL.ucc.api.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.osgi.framework.Bundle;
import org.universAAL.middleware.owl.ManagedIndividual;

public interface IConfigurator {

	public ManagedIndividual[] getOntologyContent(String uri);
	public void performConfiguration(String Path);
	public String finishConfiguration(String appName, ArrayList<Bundle> bundles, HashMap<String,String> conf);
	public void testAMethod();
}
