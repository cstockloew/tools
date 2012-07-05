package org.universaal.tools.conformanceTools.run;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.universaal.tools.conformanceTools.utils.Builder;
import org.universaal.tools.conformanceTools.utils.EssentialFiles;
import org.universaal.tools.conformanceTools.utils.ExitCodes;
import org.universaal.tools.conformanceTools.utils.RunPlugin;

public class ToolsRun {

	private static IWorkbenchWindow window;
	private static RunPlugin plugin;
	private static String project;

	public static ExitCodes run(IWorkbenchWindow window, RunPlugin plugin){

		ToolsRun.window = window;
		ToolsRun.plugin = plugin;

		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				window.getShell(), 
				ResourcesPlugin.getWorkspace().getRoot(), 
				false, 
				"Select your project:");	
		dialog.setTitle("Project selection");

		String project = null;
		Object[] result;
		if(dialog.open() == ContainerSelectionDialog.OK) {
			result = dialog.getResult();
			if(result.length == 1) {
				project = ((Path) result[0]).toOSString();			
				ToolsRun.project = project;
			}
		}
		if(project != null && !project.isEmpty()){

			try{				
				ProgressMonitorDialog progress = new ProgressMonitorDialog(window.getShell()); 
				progress.run(true, false, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException,
					InterruptedException {
						monitor.beginTask("Analysis in progres...", 100); 

						ToolsRun.execute(monitor, ToolsRun.window, ToolsRun.plugin, ToolsRun.project);

						monitor.done();
					}
				});
				ToolsRun.success();

				return ExitCodes.OK;
			} 
			catch(Exception e) {
				e.printStackTrace();
				ToolsRun.fail(null, "Generic error: "+e.getMessage());
				return ExitCodes.ERROR;
			}
		}
		else{
			ToolsRun.fail(null, "You must choose a project!");
			return ExitCodes.ERROR;
		}
	}

	private static void execute(IProgressMonitor monitor, IWorkbenchWindow window, RunPlugin plugin, String project){

		monitor.worked(0);

		IProject projectPath = ResourcesPlugin.getWorkspace().getRoot().getProject(project);
		File buildFile = null;

		switch(plugin){
		case CheckStyle:

			if(EssentialFiles.verify(project, RunPlugin.CheckStyle)){ 
				monitor.worked(25);
				if(Builder.create(project, RunPlugin.CheckStyle))
					buildFile = new File(projectPath.getLocation()+"/checkstyle_run.xml");
			}
			else{
				ToolsRun.fail(monitor, "Generic error (maybe I can't create files/folders).");
			}
			monitor.worked(30);

			break;

		case FindBugs:

			if(EssentialFiles.verify(project, RunPlugin.FindBugs)){
				monitor.worked(25);
				if(Builder.create(project, RunPlugin.FindBugs))
					buildFile = new File(projectPath.getLocation()+"/findbugs_run.xml");
			}
			else{
				ToolsRun.fail(monitor, "Generic error (maybe I can't create files/folders).");
			}

			monitor.worked(30);
			break;

		default:
			break;
		}

		if(buildFile != null && buildFile.isFile() && buildFile.length() > 0){
			Project p = new Project();
			p.setUserProperty("ant.file", buildFile.getAbsolutePath());
			p.init();

			ProjectHelper helper = ProjectHelper.getProjectHelper();
			p.addReference("ant.projectHelper", helper);
			try{
				helper.parse(p, buildFile);				
				monitor.worked(50);
			}	
			catch(Exception ex){
				ex.printStackTrace();
				ToolsRun.fail(monitor, "Generic error: "+ex.getMessage());					
			}
			try{
				p.executeTarget(p.getDefaultTarget());			
				monitor.worked(100);
			}	
			catch(Exception ex){
				ex.printStackTrace();
				ToolsRun.fail(monitor, "Generic error: "+ex.getMessage());					
			}
		}
		else{
			ToolsRun.fail(monitor, "Generic error (can't find Ant file?).");
		}
	}

	private static void success(){

		MessageDialog.openInformation(ToolsRun.window.getShell(), "Conformance tools plugin", "Check finished!");
	}

	private static void fail(IProgressMonitor monitor, String msg){

		if(monitor != null)
			monitor.done();

		MessageDialog.openInformation(ToolsRun.window.getShell(), "Conformance tools plugin", msg);
	}
}