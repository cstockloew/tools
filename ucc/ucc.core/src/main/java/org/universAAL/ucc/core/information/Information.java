package org.universAAL.ucc.core.information;

import java.util.ArrayList;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.universAAL.ucc.api.core.IInformation;

public class Information implements IInformation {
	
	private BundleContext context = null;
	
	public Information(BundleContext context) {
		this.context = context;
	}

	public String[] activeBundles() {
		Bundle[] bundles = context.getBundles();
		ArrayList<String> activeBundles = new ArrayList<String>();
		
		for (int i = 0; i<bundles.length; i++)
			if (bundles[i].getState() == Bundle.ACTIVE)
				activeBundles.add(bundles[i].getSymbolicName());
		
		String[] result = new String[activeBundles.size()];
		
		return activeBundles.toArray(result);
	}

	public Bundle[] bundles() {
		return context.getBundles();
	}

}
