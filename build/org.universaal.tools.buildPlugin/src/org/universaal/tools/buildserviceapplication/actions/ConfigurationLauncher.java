package org.universaal.tools.buildserviceapplication.actions;


import java.io.File;
import java.util.*;
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

	public ConfigurationLauncher(IProduct product, String path, String mode) {
		fProduct = product;
		fMode = mode;
		fPath = path;
	}
	public ConfigurationLauncher() {
		
	}

	public void run() {
		try {
			ILaunchConfiguration config = findLaunchConfiguration();
			if (config != null)
				DebugUITools.launch(config, org.eclipse.debug.core.ILaunchManager.RUN_MODE);
			System.out.println(config.getAttributes().toString());
		} catch (CoreException e) {
		}
	}

	public ILaunchConfiguration findLaunchConfiguration() throws CoreException {
		ILaunchConfiguration[] configs = new ILaunchConfiguration[0];

		if (configs.length == 0)
			return createConfiguration();

		ILaunchConfiguration config = null;
		if (configs.length == 1) {
			config = configs[0];
		} else {
			// Prompt the user to choose a config. 
			config = chooseConfiguration(configs);
		}

		if (config != null) {
			config = refreshConfiguration(config.getWorkingCopy());
		}
		return config;
	}

	private ILaunchConfiguration refreshConfiguration(ILaunchConfigurationWorkingCopy wc) throws CoreException {
	//	wc.setAttribute(IPDELauncherConstants.PRODUCT, fProduct.getId());
		String os = Platform.getOS();
		
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, getVMArguments());
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, getProgramArguments());
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH, getJREContainer());
		wc.getAttribute("org.eclipse.debug.core.source_locator_id", "org.eclipse.pde.ui.launcher.PDESourceLookupDirector");
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_SOURCE_PATH_PROVIDER, "org.eclipse.pde.ui.workbenchClasspathProvider");
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, "${workspace_loc}/rundir");
		wc.setAttribute("org.ops4j.pax.cursor.logLevel", "DEBUG");
		wc.setAttribute("org.ops4j.pax.cursor.overwrite", false);
		wc.setAttribute("org.ops4j.pax.cursor.overwriteSystemBundles", false);
		wc.setAttribute("org.ops4j.pax.cursor.overwriteUserBundles", false);
		wc.setAttribute("org.ops4j.pax.cursor.profiles", "obr");
		

		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--overwrite=false");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--overwriteUserBundles=false");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--overwriteSystemBundles=false");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--log=DEBUG");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "--profiles=obr");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.ops4j.pax.logging/pax-logging-api/1.4@2@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:bundleHome/bundleHeating@8@nostart@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.platform/casf.ont@5@update"	);
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.ops4j.pax.confman/pax-confman-propsloader@3@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:jp.go.ipa/jgcl/1.0@2@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "http://gforge.aal-persona.org/nexus/content/repositories/snapshots/org/aal-persona/middleware/rdf.serializer.turtle/0.3.0-SNAPSHOT/rdf.serializer.turtle-0.3.0-20091218.173719-9.jar@4@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:java3d/vecmath/1.3.1@2@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.middleware/acl.upnp@3@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.ops4j.pax.logging/pax-logging-service/1.4@3@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:org.bouncycastle/jce.jdk13/144@2@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.middleware/acl.interfaces@2@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.middleware/middleware.upper/0.3.0-SNAPSHOT@4@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:java3d/j3d-core/1.3.1@2@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.samples/lighting.client@7@nostart@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.apache.felix/org.apache.felix.configadmin@2@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.middleware/sodapop.osgi@3@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "http://apache.prosite.de/felix/org.apache.felix.upnp.basedriver-0.8.0.jar@2@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.aal-persona.samples/lighting.server@6@nostart@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "wrap:mvn:org.osgi/osgi_R4_compendium/1.0@2@update");
		wc.setAttribute("org.ops4j.pax.cursor.runArguments", "mvn:org.apache.felix/org.apache.felix.log/0.9.0-SNAPSHOT@2@update");
		
		
		
		wc.setAttribute("osgi_framework_id", "--platform=felix --version=1.4.0");
		wc.setAttribute("pde.version", "3.3");
		
		wc.setAttribute("show_selected_only", false);
		wc.setAttribute("tracing", false);
		wc.setAttribute("useDefaultConfigArea", false);
		
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
		
		

	//	wc.set
		return wc.doSave();
	}

	
	
	
	private void appendBundle(StringBuffer buffer, IPluginModelBase model) {
		buffer.append(model.getPluginBase().getId());
	//	buffer.append(BundleLauncherHelper.VERSION_SEPARATOR);
		buffer.append(model.getPluginBase().getVersion());
		buffer.append(',');
	}

	private String getProgramArguments() {
	//	StringBuffer buffer = new StringBuffer(LaunchArgumentsHelper.getInitialProgramArguments());
	//	IArgumentsInfo info = fProduct.getLauncherArguments();
	//	String userArgs = (info != null) ? CoreUtility.normalize(info.getCompleteProgramArguments(os)) : ""; //$NON-NLS-1$
	//	if (userArgs.length() > 0) {
	//		buffer.append(" "); //$NON-NLS-1$
	//		buffer.append(userArgs);
	//	}
	//	return buffer.toString();
		return "-console";
	}

	private String getVMArguments() {
//		IArgumentsInfo info = fProduct.getLauncherArguments();
//		return (info != null) ? CoreUtility.normalize(info.getCompleteVMArguments(os)) : ""; //$NON-NLS-1$
		return "-Dosgi.noShutdown=true -Dfelix.log.level=4 -Dorg.persona.middleware.peer.is_coordinator=true -Dorg.persona.middleware.peer.member_of=urn:org.persona.aal_space:tes_env -Dbundles.configuration.location=${workspace_loc}/rundir/confadmin";
	}

	private String getJREContainer() {
//		IJREInfo info = fProduct.getJREInfo();
//		if (info != null) {
//			IPath jrePath = info.getJREContainerPath(os);
//			if (jrePath != null) {
//				return jrePath.toPortableString();
//			}
//		}
//		return null;
		return "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/CDC-1.0%Foundation-1.0";
	}

//	private IPluginModelBase[] getModels() {
//		HashMap map = new HashMap();
//		if (fProduct.useFeatures()) {
//			IFeatureModel[] features = getUniqueFeatures();
//			for (int i = 0; i < features.length; i++) {
//				addFeaturePlugins(features[i].getFeature(), map);
//			}
//		} else {
//			IProductPlugin[] plugins = fProduct.getPlugins();
//			for (int i = 0; i < plugins.length; i++) {
//				String id = plugins[i].getId();
//				if (id == null || map.containsKey(id))
//					continue;
//				IPluginModelBase model = PluginRegistry.findModel(id);
//				if (model != null && TargetPlatformHelper.matchesCurrentEnvironment(model))
//					map.put(id, model);
//			}
//		}
//		return (IPluginModelBase[]) map.values().toArray(new IPluginModelBase[map.size()]);
//	}

	private IFeatureModel[] getUniqueFeatures() {
		ArrayList list = new ArrayList();
		IProductFeature[] features = fProduct.getFeatures();
		for (int i = 0; i < features.length; i++) {
			String id = features[i].getId();
			String version = features[i].getVersion();
			addFeatureAndChildren(id, version, list);
		}
		return (IFeatureModel[]) list.toArray(new IFeatureModel[list.size()]);
	}

	private void addFeatureAndChildren(String id, String version, List list) {
		FeatureModelManager manager = PDECore.getDefault().getFeatureModelManager();
		IFeatureModel model = manager.findFeatureModel(id, version);
		if (model == null || list.contains(model))
			return;

		list.add(model);

		IFeatureChild[] children = model.getFeature().getIncludedFeatures();
		for (int i = 0; i < children.length; i++) {
			addFeatureAndChildren(children[i].getId(), children[i].getVersion(), list);
		}
	}

	private void addFeaturePlugins(IFeature feature, HashMap map) {
		IFeaturePlugin[] plugins = feature.getPlugins();
		for (int i = 0; i < plugins.length; i++) {
			String id = plugins[i].getId();
			if (id == null || map.containsKey(id))
				continue;
			IPluginModelBase model = PluginRegistry.findModel(id);
			if (model != null && TargetPlatformHelper.matchesCurrentEnvironment(model))
				map.put(id, model);
		}
	}

//	private String getTemplateConfigIni() {
//		IConfigurationFileInfo info = fProduct.getConfigurationFileInfo();
//		if (info != null && info.getUse().equals("custom")) { //$NON-NLS-1$
//			String path = getExpandedPath(info.getPath());
//			if (path != null) {
//				File file = new File(path);
//				if (file.exists() && file.isFile())
//					return file.getAbsolutePath();
//			}
//		}
//		return null;
//	}

	private String getExpandedPath(String path) {
		if (path == null || path.length() == 0)
			return null;
		IResource resource = PDEPlugin.getWorkspace().getRoot().findMember(new Path(path));
		if (resource != null) {
			IPath fullPath = resource.getLocation();
			return fullPath == null ? null : fullPath.toOSString();
		}
		return null;
	}

	private ILaunchConfiguration chooseConfiguration(ILaunchConfiguration[] configs) {
		IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(PDEPlugin.getActiveWorkbenchShell(), labelProvider);
		dialog.setElements(configs);
		dialog.setTitle(PDEUIMessages.RuntimeWorkbenchShortcut_title);
		if (fMode.equals(ILaunchManager.DEBUG_MODE)) {
			dialog.setMessage(PDEUIMessages.RuntimeWorkbenchShortcut_select_debug);
		} else {
			dialog.setMessage(PDEUIMessages.RuntimeWorkbenchShortcut_select_run);
		}
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		return null;
	}

	private ILaunchConfiguration createConfiguration() throws CoreException {
		ILaunchConfigurationType configType = getWorkbenchLaunchConfigType();
		String computedName = getComputedName(new Path("test").lastSegment());
		ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, computedName);
	//	wc.setAttribute(IPDELauncherConstants.LOCATION, LaunchArgumentsHelper.getDefaultWorkspaceLocation(computedName));
		wc.setAttribute(IPDELauncherConstants.USEFEATURES, false);
		wc.setAttribute(IPDELauncherConstants.USE_DEFAULT, false);
		wc.setAttribute(IPDELauncherConstants.DOCLEAR, false);
		wc.setAttribute(IPDEUIConstants.DOCLEARLOG, false);
		wc.setAttribute(IPDEUIConstants.APPEND_ARGS_EXPLICITLY, true);
		wc.setAttribute(IPDELauncherConstants.ASKCLEAR, true);
		wc.setAttribute(IPDELauncherConstants.USE_PRODUCT, true);
		wc.setAttribute(IPDELauncherConstants.AUTOMATIC_ADD, false);
		wc.setAttribute(IPDELauncherConstants.PRODUCT_FILE, fPath);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_SOURCE_PATH_PROVIDER, PDESourcePathProvider.ID);
		return refreshConfiguration(wc);
	}

	private String getComputedName(String prefix) {
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		return lm.generateUniqueLaunchConfigurationNameFrom(prefix);
	}

//	 public ILaunchConfiguration[] getLaunchConfigurations() throws CoreException {
//		ArrayList result = new ArrayList();
//		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
//		ILaunchConfigurationType type = manager.getLaunchConfigurationType(EclipseLaunchShortcut.CONFIGURATION_TYPE);
//		ILaunchConfiguration[] configs = manager.getLaunchConfigurations(type);
//		for (int i = 0; i < configs.length; i++) {
//			if (!DebugUITools.isPrivate(configs[i])) {
//				String path = configs[i].getAttribute(IPDELauncherConstants.PRODUCT_FILE, ""); //$NON-NLS-1$
//				if (new Path(fPath).equals(new Path(path))) {
//					result.add(configs[i]);
//				}
//			}
//		}
//		return (ILaunchConfiguration[]) result.toArray(new ILaunchConfiguration[result.size()]);
//	}

	protected ILaunchConfigurationType getWorkbenchLaunchConfigType() {
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		return lm.getLaunchConfigurationType(EclipseLaunchShortcut.CONFIGURATION_TYPE);
	}

}
