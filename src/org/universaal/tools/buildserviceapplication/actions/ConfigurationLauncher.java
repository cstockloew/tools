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
			if (config != null)
				config.launch(ILaunchManager.DEBUG_MODE, null);			
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
		        	return refreshConfiguration(t); 
		        }
		   }
		 return type.newInstance(null, "persona");		
	}

	private ILaunchConfiguration refreshConfiguration(ILaunchConfigurationWorkingCopy wc) throws CoreException {
	//	wc.setAttribute(IPDELauncherConstants.PRODUCT, fProduct.getId());
		
		
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, getVMArguments());
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, getProgramArguments());
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH, getJREContainer());
	//	wc.getAttribute("org.eclipse.debug.core.source_locator_id", "org.eclipse.pde.ui.launcher.PDESourceLookupDirector");
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_SOURCE_PATH_PROVIDER, "org.eclipse.pde.ui.workbenchClasspathProvider");
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, "${workspace_loc}/rundir");
//		wc.setAttribute("org.ops4j.pax.cursor.logLevel", "DEBUG");
//		wc.setAttribute("org.ops4j.pax.cursor.overwrite", false);
//		wc.setAttribute("org.ops4j.pax.cursor.overwriteSystemBundles", false);
//		wc.setAttribute("org.ops4j.pax.cursor.overwriteUserBundles", false);
//		wc.setAttribute("org.ops4j.pax.cursor.profiles", "obr");
////		
//////
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--overwrite=false");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--overwriteUserBundles=false");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--overwriteSystemBundles=false");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--log=DEBUG");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--profiles=obr");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.ops4j.pax.logging/pax-logging-api/1.4@2@update");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:bundleHome/bundleHeating@8@nostart@update");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.platform/casf.ont@5@update"	);
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.ops4j.pax.confman/pax-confman-propsloader@3@update");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:jp.go.ipa/jgcl/1.0@2@update");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "http://gforge.aal-persona.org/nexus/content/repositories/snapshots/org/aal-persona/middleware/rdf.serializer.turtle/0.3.0-SNAPSHOT/rdf.serializer.turtle-0.3.0-20091218.173719-9.jar@4@update");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:java3d/vecmath/1.3.1@2@update");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.middleware/acl.upnp@3@update");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.ops4j.pax.logging/pax-logging-service/1.4@3@update");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:org.bouncycastle/jce.jdk13/144@2@update");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.middleware/acl.interfaces@2@update");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.middleware/middleware.upper/0.3.0-SNAPSHOT@4@update");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:java3d/j3d-core/1.3.1@2@update");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.samples/lighting.client@7@nostart@update");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.apache.felix/org.apache.felix.configadmin@2@update");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.middleware/sodapop.osgi@3@update");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "http://apache.prosite.de/felix/org.apache.felix.upnp.basedriver-0.8.0.jar@2@update");
////		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.samples/lighting.server@6@nostart@update");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:org.osgi/osgi_R4_compendium/1.0@2@update");
//		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.apache.felix/org.apache.felix.log/0.9.0-SNAPSHOT@2@update");
////		
////		
//		
	//	wc.setAttribute("osgi_framework_id", "--platform=felix --version=1.4.0");
		//wc.setAttribute("pde.version", "3.6.1");
//		
		//wc.setAttribute("show_selected_only", false);
		//wc.setAttribute("tracing", false);
		//wc.setAttribute("useDefaultConfigArea", false);
//		
	//	IConfigurationElement[][] elements = getPDELauncherEditor().getLaunchers(true);
		//Map map=new HashMap();		
		//wc.setAttributes(map);		
//		StringBuffer wsplugins = new StringBuffer();
//		StringBuffer explugins = new StringBuffer();
//		IPluginModelBase[] models = getModels();
//		for (int i = 0; i < models.length; i++) {
//			IPluginModelBase model = models[i];
//			if (model.getUnderlyingResource() == null)
//				appendBundle(explugins, model);
//			else
//				appendBundle(wsplugins, model);
//		}
//		wc.setAttribute(IPDELauncherConstants.SELECTED_WORKSPACE_PLUGINS, wsplugins.toString());
//		wc.setAttribute(IPDELauncherConstants.SELECTED_TARGET_PLUGINS, explugins.toString());
	//	String configIni = getTemplateConfigIni();
		//wc.setAttribute(IPDELauncherConstants.CONFIG_GENERATE_DEFAULT, configIni == null);
		//if (configIni != null)
		//	wc.setAttribute(IPDELauncherConstants.CONFIG_TEMPLATE_LOCATION, configIni);
		return wc.doSave();
	}

	
	
	
	private void appendBundle(StringBuffer buffer, IPluginModelBase model) {
		buffer.append(model.getPluginBase().getId());
		buffer.append(model.getPluginBase().getVersion());
		buffer.append(',');
	}

	private String getProgramArguments() {

		return "-console";
	}

	private String getVMArguments() {

		return "-Dosgi.noShutdown=true -Dfelix.log.level=2 -Dorg.persona.middleware.peer.is_coordinator=true -Dorg.persona.middleware.peer.member_of=urn:org.persona.aal_space:tes_env -Dbundles.configuration.location=${workspace_loc}/rundir/confadmin";
	}

	private String getJREContainer() {

		return "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/CDC-1.0%Foundation-1.0";
	}



	


}
