package org.universaal.tools.buildserviceapplication.actions;



import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;


public class ConfigurationLauncher extends Action{
	private String configurationName="";

	
	public ConfigurationLauncher(String configurationName) {
		this.configurationName=configurationName;
		
	}

	public void run() {
		try {
			ILaunchConfiguration config = findLaunchConfiguration();			
			if (config != null){
				config.launch(ILaunchManager.RUN_MODE, null);			
			}
			else{
				ResourcesPlugin.getWorkspace().getRoot()
				.refreshLocal(IResource.DEPTH_INFINITE, null);
				MessageDialog
				.openInformation(null,
						"BuildServiceApplication",
						"An error occured while running service.\n Please try again");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	
	public void debug() {
		try {
			ILaunchConfiguration config = findLaunchConfiguration();			
			if (config != null){
				config.launch(ILaunchManager.DEBUG_MODE, null);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public ILaunchConfiguration findLaunchConfiguration() throws CoreException {		
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		   ILaunchConfigurationType type =
		      manager.getLaunchConfigurationType("org.eclipse.pde.ui.EquinoxLauncher");	   
		   ILaunchConfiguration[] lcs = manager.getLaunchConfigurations(type);
		   for (int i = 0; i < lcs.length; ++i) {
		        if (lcs[i].getName().equals(configurationName)) {
		        	ILaunchConfiguration t=lcs[i];	
		        	return t;		        	
		        }
		   }
		 return null;		
	}


	


}
