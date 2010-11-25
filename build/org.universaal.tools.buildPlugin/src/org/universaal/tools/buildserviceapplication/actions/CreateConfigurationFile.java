package org.universaal.tools.buildserviceapplication.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.pde.internal.core.iproduct.IProduct;



public class CreateConfigurationFile {

public CreateConfigurationFile(){
	try {
		ConfigurationLauncher c=new ConfigurationLauncher();
		c.run();
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	
	
	
}
	
}
