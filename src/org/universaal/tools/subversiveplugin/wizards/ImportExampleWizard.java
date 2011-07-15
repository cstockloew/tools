package org.universaal.tools.subversiveplugin.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.team.svn.core.connector.ISVNConnector;
import org.eclipse.team.svn.core.operation.CompositeOperation;
import org.eclipse.team.svn.core.operation.local.management.DisconnectOperation;
import org.eclipse.team.svn.core.operation.remote.CheckoutAsOperation;
import org.eclipse.team.svn.core.resource.IRepositoryResource;
import org.eclipse.team.svn.core.utility.ProgressMonitorUtility;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

public class ImportExampleWizard extends Wizard implements IImportWizard {

	private ImportExampleWizardPage page;
	private IRepositoryResource choice;
	private IWorkbenchWindow window;
	
	public ImportExampleWizard(){
		
	}
	
	public ImportExampleWizard(IWorkbenchWindow inputWindow){
		this.window = inputWindow;
		TrayDialog.setDialogHelpAvailable(false);
	}
	
	//Must override this to return true in order to get a progressbar.
	@Override
	public boolean needsProgressMonitor() {
		return true;
	}
	
	@Override
	public boolean isHelpAvailable(){
		return false;
	}
	
	@Override
	public boolean performFinish() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath dir = workspace.getRoot().getLocation();
		String directory = dir.toString();
		
		//The variable "choice" is set from ImportExampleWizardPage after the user
		//selects an object in the table.
		CheckoutAsOperation check = new CheckoutAsOperation(choice.getName(), choice, false, directory,ISVNConnector.Depth.INFINITY, false);
		CompositeOperation op = new CompositeOperation(check.getId(), check.getMessagesClass());
		IProject project[] = new IProject[1];
		project[0] = check.getProject();
		DisconnectOperation disc = new DisconnectOperation(project, true);
		
		//Checks the project out from SVN, then disconnects it from SVN.
		op.add(check);
		op.add(disc);
		Job job = ProgressMonitorUtility.doTaskScheduled(op);
		
		//Displays a progressbar inside the wizard.
		try {
			getContainer().run(true, true, new Progress(job));
		} catch (InvocationTargetException e) {
			//TODO
			e.printStackTrace();
		} catch (InterruptedException e) {
			job.cancel();
			e.printStackTrace();
		}

		return true;
	}
	
	public void addPages(){
		page = new ImportExampleWizardPage();
		addPage(page);
	}
	
	public void setResource(IRepositoryResource input){
		this.choice = input;
	}
	
	private class Progress implements IRunnableWithProgress{

		private Job job;
		
		public Progress(Job job){
			this.job = job;
		}
		
		@Override
		public void run(IProgressMonitor arg0)
				throws InvocationTargetException, InterruptedException {
			if(arg0==null){
				arg0 = new NullProgressMonitor();
			}
			arg0.beginTask("Test!", IProgressMonitor.UNKNOWN);
			arg0.setTaskName("Importing project. This might take some time.");
			while(job.getState()!=0){
				arg0.worked(1);
				if(arg0.isCanceled()){
					job.cancel();
				}
//				Thread.sleep(100);
			}
			arg0.done();
			
		}
		
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.window = workbench.getActiveWorkbenchWindow();
		TrayDialog.setDialogHelpAvailable(false);
	}

}
