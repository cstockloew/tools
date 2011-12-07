package org.universAAL.ucc.api.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.osgi.framework.Bundle;
import org.universAAL.middleware.owl.ManagedIndividual;

import com.trolltech.qt.gui.QBoxLayout;

public interface IConfigurator {

	public ManagedIndividual[] getOntologyContent(String uri);
	public void performConfiguration(String Path, QBoxLayout parent) throws Exception;
	public String finishConfiguration(ArrayList<Bundle> bundles);
	public boolean checkEnteredValues();
}

