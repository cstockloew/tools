package org.universaal.tools.buildserviceapplication.actions;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchGroupExtension;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.apache.maven.cli.MavenCli;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class RunAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	

	/**
	 * The constructor.
	 */
	public RunAction() {

	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try {
			IHandlerService handlerService = (IHandlerService) (IHandlerService) PlatformUI
					.getWorkbench().getService(IHandlerService.class);

			ICommandService commandService = (ICommandService) (ICommandService) PlatformUI
					.getWorkbench().getService(ICommandService.class);
			Command showElement = commandService
					.getCommand("org.universaal.uaalpax.command.newRunConfig");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(
					"org.universaal.uaalpax.commandparameters.runDebugMode",
					"");
			ParameterizedCommand paramShowElement = ParameterizedCommand
					.generateCommand(showElement, params);

			ExecutionEvent execEvent = handlerService.createExecutionEvent(
					paramShowElement, new Event());
			try {
				showElement.executeWithChecks(execEvent);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			
			// handlerService.executeCommand(commandId, new Event());
		} catch (Exception ex) {
			ex.printStackTrace();
			// Give message
		}
//		CreateLaunchConfigurationFile launchFile=new CreateLaunchConfigurationFile();
//		if (BuildAction.buildedProjects.contains(launchFile.getSelectedMavenProject())) {
//			launchFile.getProjectDependencies();
//			ILaunchConfiguration launch = launchFile.createLaunchConfiguration();
//			if (launch == null) {
//				MessageDialog.openInformation(
//						Display.getCurrent().getActiveShell(),
//						"BuildServiceApplication",
//						"An error occured while creating launch file.");
//			} else {
//				ILaunchGroup[] group = DebugUIPlugin.getDefault()
//					.getLaunchConfigurationManager().getLaunchGroups();				
//				DebugUITools.openLaunchConfigurationDialog(Display.getDefault()
//						.getActiveShell(), launch,
//						"org.eclipse.debug.ui.launchGroup.run", null);
//	
//			}
//		} else {
//			MessageDialog.openInformation(
//					Display.getCurrent().getActiveShell(),
//					"BuildServiceApplication",
//					"Please build the selected project first.");
//		}
	}

	

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {

	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

}