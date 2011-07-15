package uploadopensourceplugin.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.team.svn.core.operation.local.management.ShareProjectOperation;
import org.eclipse.team.svn.core.resource.IRepositoryLocation;
import org.eclipse.team.svn.core.svnstorage.SVNRemoteStorage;
import org.eclipse.team.svn.core.utility.ProgressMonitorUtility;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

public class UploadOpenSourceWizard extends Wizard {
	
	UploadOpenSourceWizardPage1 page;
	IProject project;
	IProject[] projects;
	
	public UploadOpenSourceWizard(){
		page = new UploadOpenSourceWizardPage1();
		
		ISelectionService service = PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getSelectionService();
		IStructuredSelection structured = (IStructuredSelection) service
                .getSelection("org.eclipse.jdt.ui.PackageExplorer");
		
		Object element = structured.getFirstElement();
		if(element instanceof IResource){
			project = ((IResource)element).getProject();
		}else if (element instanceof PackageFragment){
			IJavaProject jProject = ((PackageFragment)element).getJavaProject();
			project = jProject.getProject();
		}else if (element instanceof IJavaElement){
			IJavaProject jProject = ((IJavaElement)element).getJavaProject();
			project = jProject.getProject();
		}
		
		projects = new IProject[]{project};
		
	}
	
	public void addPages(){
		addPage(page);
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
		loc.setUrl(page.getText());
		
		ShareProjectOperation op = new ShareProjectOperation(projects, loc, new FolderNamer());
		Job job = ProgressMonitorUtility.doTaskScheduled(op);
		
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
	
	private class FolderNamer implements ShareProjectOperation.IFolderNameMapper{
		@Override
		public String getRepositoryFolderName(IProject arg0) {
			return arg0.getName();
		}
	}

	private class Progress implements IRunnableWithProgress{

		Job job;
		
		public Progress(Job input){
			job = input;
		}
		
		@Override
		public void run(IProgressMonitor arg0)
				throws InvocationTargetException, InterruptedException {
			if(arg0==null){
				arg0 = new NullProgressMonitor();
			}
			arg0.beginTask("Test!", IProgressMonitor.UNKNOWN);
			arg0.setTaskName("Exporting project. This might take some time.");
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
}
