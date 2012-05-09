package org.universaal.tools.conformance.verify.plugin.handlers;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.project.MavenProject;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.eclipse.m2e.core.ui.internal.Messages;
import org.eclipse.m2e.core.ui.internal.actions.OpenMavenConsoleAction;
import org.eclipse.m2e.core.ui.internal.console.MavenConsoleImpl;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.IProgressConstants2;
import org.universaal.tools.conformance.verify.plugin.Activator;

public class VerifierHandler extends AbstractHandler {

    // Field so it can be used from Job
    private IProject project;

    /* (non-Javadoc)
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
	// First, retrieve the current selection and check whether it is a project
	IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
	// ISelection selection = HandlerUtil.getCurrentSelection(event);
	// //TODO: Is this the same and faster?
	ISelection selection = window.getSelectionService().getSelection();
	if ((selection != null) && (selection instanceof StructuredSelection)) {
	    Object selected = ((StructuredSelection) selection)
		    .getFirstElement();
	    // If the selection is a project, start the transformation
	    IProject selectedPrj;
	    if (selected instanceof JavaProject) {
		// Because they must be Maven projects, they will most prob. be JavaProjects
		selectedPrj = ((JavaProject) selected).getProject();
	    } else if (selected instanceof IProject) {
		// But if you select them from project explorer they are IProjects
		selectedPrj = ((IProject) selected);
	    } else {
		MessageDialog.openInformation(window.getShell(),
			"Verify Conformance", "Selection is not a valid project. You must select a valid Maven project first.");
		return null;
	    }

	    // Main task - verify
	    verify(selectedPrj);

	} else {
	    MessageDialog.openInformation(window.getShell(),
		    "Verify Conformance", "No selection was made. You must select a valid Maven project first.");
	}
	return null;
    }

    /**
     * Execute Maven verifier plugin as workspace Job for the selected project.
     * 
     * @param selected
     *            The selected project to verify.
     * @throws CoreException
     */
    private void verify(IProject selected) {
	project=selected;
	
	Job job = new Job("AAL Studio") {
	    protected IStatus run(IProgressMonitor monitor) {
		try {
		    // Continuous progress bar
		    monitor.beginTask("Verifying conformance", IProgressMonitor.UNKNOWN);
		    
		    IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();

		    // Check that it is a Maven Project
		    if (!project.hasNature(IMavenConstants.NATURE_ID)) {
			monitor.done();
			return new Status(Status.ERROR, Activator.PLUGIN_ID,"");
		    }

		    // Check that there is a POM.xml
		    IFile pomResource = project.getFile(IMavenConstants.POM_FILE_NAME);
		    if (pomResource == null) {
			monitor.done();
			return new Status(Status.ERROR, Activator.PLUGIN_ID,"");
		    }

		    //TODO: Refresh/update config here?

		    //Get Maven project and plugin
		    IMavenProjectFacade projectFacade = projectManager.create(project, monitor);
		    MavenProject mavenProject = projectFacade.getMavenProject(monitor);
		    IMaven maven = MavenPlugin.getMaven();
		    
		    // Build command request: 
		    // mvn verifier:verify -Dverifier.failOnError=true -Dverifier.verificationFile=...
		    MavenExecutionRequest request = projectManager.createExecutionRequest(pomResource,
		        projectFacade.getResolverConfiguration(), monitor);
		    Properties props=new Properties();
		    props.put("verifier.verificationFile", Activator.absolutePath+"/files/verify.xml");
		    props.put("verifier.failOnError", "true");
		    request.setUserProperties(props);
		    request.setGoals(Arrays.asList("verifier:verify"));

		    // TODO: Open Maven console to see it run. Not Working?
		    IOConsole console=setConsole();
		    
		    // Execute
		    MavenExecutionResult result = maven.execute(request,monitor);
		    
		    // Check if there are errors (missing files or plugin error)
		    if(result.hasExceptions()) {
			String errors="The following errors where encountered: \n";
			for(Throwable e : result.getExceptions()) {
			    if(e.getCause().getCause() instanceof FileNotFoundException){
				errors="The verification guideline could not be found: "+e.getCause().getCause().getMessage()+"\n";
			    }else{
			        errors+="There are missing files, look at the console \n" +
			        	"(if it´s blank you must select the right console): "+
			        	e.getCause().getMessage()+"\n";
			    }
			}
			monitor.done();
			return new Status(Status.ERROR, Activator.PLUGIN_ID,errors);
		    }
		    
		} catch (Exception ex) {
		    ex.printStackTrace();
		    monitor.done();
		    return new Status(Status.ERROR, Activator.PLUGIN_ID,ex.getMessage());
		}
		monitor.done();
		//TODO: Elaborate a good result dialog
		return new Status(Status.OK, Activator.PLUGIN_ID, "Project has all files");
	    }
	};
	
	// TODO: Open (again) the Maven console to see it run. Not working?
	job.setProperty(IProgressConstants2.ACTION_PROPERTY, new OpenMavenConsoleAction());
	job.setProperty(IProgressConstants2.ICON_PROPERTY, Activator.getImageDescriptor("icons/uaal.gif"));
	job.setUser(true);
	job.schedule();	
    }
    
    /**
     * Gets the Maven console, or a new console if it´s not there. Currently
     * it´s not working as desired. It creates an empty console named Maven
     * Console, but Maven is somehow always executed in current console, so this
     * one gets on top of that.
     * 
     * @return The console
     */
    private IOConsole setConsole() {
	ConsolePlugin plugin = ConsolePlugin.getDefault();
	IConsoleManager conMan = plugin.getConsoleManager();
	IConsole[] existing = conMan.getConsoles();
	// Look for Maven Console
	for (int i = 0; i < existing.length; i++)
	    if (Messages.MavenConsoleImpl_title.equals(existing[i].getName()))
		return (IOConsole) existing[i];
	// no console found, so create a new (Maven) one
//	MessageConsole myConsole = new MessageConsole(
//		Messages.MavenConsoleImpl_title, null);
	MavenConsoleImpl myConsole = new MavenConsoleImpl(null);//Not working?
	conMan.addConsoles(new IConsole[] { myConsole });
	conMan.showConsoleView(myConsole);
	myConsole.clearConsole();
	myConsole.activate();
	return myConsole;
    }

}
