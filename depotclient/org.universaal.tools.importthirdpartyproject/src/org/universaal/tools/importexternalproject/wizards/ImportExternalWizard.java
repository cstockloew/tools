package org.universaal.tools.importexternalproject.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.team.svn.core.connector.ISVNConnector;
import org.eclipse.team.svn.core.connector.SVNRevision;
import org.eclipse.team.svn.core.operation.CompositeOperation;
import org.eclipse.team.svn.core.operation.local.management.DisconnectOperation;
import org.eclipse.team.svn.core.operation.remote.CheckoutAsOperation;
import org.eclipse.team.svn.core.operation.remote.management.AddRepositoryLocationOperation;
import org.eclipse.team.svn.core.operation.remote.management.DiscardRepositoryLocationsOperation;
import org.eclipse.team.svn.core.operation.remote.management.SaveRepositoryLocationsOperation;
import org.eclipse.team.svn.core.resource.IRepositoryLocation;
import org.eclipse.team.svn.core.svnstorage.SVNRemoteStorage;
import org.eclipse.team.svn.core.svnstorage.SVNRepositoryFolder;
import org.eclipse.team.svn.core.utility.ProgressMonitorUtility;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.universaal.tools.importexternalproject.xmlparser.ProjectObject;

public class ImportExternalWizard extends Wizard implements IImportWizard {
	
	private ArrayList<File> files;
	private ProjectObject chosenProject;
	private ImportExternalWizardPage page;
	
	public ImportExternalWizard(){
		page = new ImportExternalWizardPage(true);
		initFiles();
	}
	
	/**
	 * Added another constructor to be able to know if the wizard was started 
	 * by the command or from the Import-extension.
	 */
	public ImportExternalWizard(IWorkbenchWindow window){
		page = new ImportExternalWizardPage(false);
		initFiles();
		
	}
	
	private void initFiles(){
		//TODO Rewrite this when it is possible to get files from the site.
		ArrayList<File> files = new ArrayList<File>();
		files.add(new File("C:/src/text.xml"));
		files.add(new File("C:/src/text2.xml"));
		files.add(new File("C:/src/text3.xml"));
		this.files = files;;
	}
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {	
	}
	
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
		IRepositoryLocation loc = SVNRemoteStorage.instance().newRepositoryLocation();
		loc.setUrl(chosenProject.getUrl());
		loc.reconfigure();
		AddRepositoryLocationOperation op = new AddRepositoryLocationOperation(loc);
		SaveRepositoryLocationsOperation savOp = new SaveRepositoryLocationsOperation();
		CompositeOperation comOp = new CompositeOperation(op.getId(), op.getMessagesClass());
		comOp.add(op);
		comOp.add(savOp);
		ProgressMonitorUtility.doTaskScheduledDefault(comOp);
		
		SVNRepositoryFolder fold = new SVNRepositoryFolder(loc, 
				chosenProject.getUrl(),
				SVNRevision.HEAD );
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath dir = workspace.getRoot().getLocation();
		String directory = dir.toString();
		
		CheckoutAsOperation check = new CheckoutAsOperation(fold.getName(), fold, false, directory,ISVNConnector.Depth.INFINITY, false);
		CompositeOperation op2 = new CompositeOperation(check.getId(), check.getMessagesClass());
		
		IProject project[] = new IProject[1];
		project[0] = check.getProject();
		DisconnectOperation disc = new DisconnectOperation(project, true);
		IRepositoryLocation[] discardLoc = new IRepositoryLocation[1];
		discardLoc[0] = loc;
		DiscardRepositoryLocationsOperation discard = new DiscardRepositoryLocationsOperation(discardLoc);
		
		//Checks the project out from SVN, then disconnects it from SVN.
		op2.add(check);
		op2.add(disc);
		op2.add(discard);
		Job job = ProgressMonitorUtility.doTaskScheduled(op2);
		try {
			getContainer().run(true, true, new Progress(job));
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
			}
			System.out.println("Jobstate: "+ job.getState());
			arg0.done();
		}
	}
	
	@Override
	public void addPages(){
		setWindowTitle("Import External Project");
		addPage(page);
	}
	
	public ArrayList<File> getFiles(){
		return files;
	}
	
	public void setChosen(ProjectObject input){
		this.chosenProject = input;
	}

}
