package org.universaal.tools.buildserviceapplication.actions;


import java.io.File;
import java.text.Collator;
import java.util.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.pde.internal.core.*;
import org.eclipse.pde.internal.core.ifeature.*;
import org.eclipse.pde.internal.core.iproduct.*;
import org.eclipse.pde.internal.core.iproduct.IProduct;
import org.eclipse.pde.internal.core.util.CoreUtility;
import org.eclipse.pde.internal.ui.*;
import org.eclipse.pde.internal.ui.editor.PDELauncherFormEditor;
import org.eclipse.pde.ui.launcher.*;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class ConfigurationLauncher extends Action{
	private IProduct fProduct;
	private String fMode;
	private String fPath;
	private String configurationName="";

	public ConfigurationLauncher(IProduct product, String path, String mode) {
		fProduct = product;
		fMode = mode;
		fPath = path;
	}
	public ConfigurationLauncher(String configurationName) {
		this.configurationName=configurationName;
		
	}

	public void run() {
		try {
			ILaunchConfiguration config = findLaunchConfiguration();			
			if (config != null){
				config.launch(ILaunchManager.RUN_MODE, null);			
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
		        	ILaunchConfigurationWorkingCopy t=lcs[i].getWorkingCopy();	
		        	return t;		        	
		        }
		   }
		 return null;		
	}


	


}
