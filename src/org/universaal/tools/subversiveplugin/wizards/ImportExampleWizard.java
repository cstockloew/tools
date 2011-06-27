package org.universaal.tools.subversiveplugin.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.team.svn.core.connector.ISVNConnector;
import org.eclipse.team.svn.core.operation.CompositeOperation;
import org.eclipse.team.svn.core.operation.local.management.DisconnectOperation;
import org.eclipse.team.svn.core.operation.remote.CheckoutAsOperation;
import org.eclipse.team.svn.core.resource.IRepositoryResource;
import org.eclipse.team.svn.core.utility.ProgressMonitorUtility;

public class ImportExampleWizard extends Wizard {

	private ImportExampleWizardPage page;
	private IRepositoryResource choice;
	
	@Override
	public boolean performFinish() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath dir = workspace.getRoot().getLocation();
		String directory = dir.toString();
		
		CheckoutAsOperation check = new CheckoutAsOperation(choice.getName(), choice, false, directory,ISVNConnector.Depth.INFINITY, false);
		CompositeOperation op = new CompositeOperation(check.getId(), check.getMessagesClass());
		
		IProject project[] = new IProject[1];
		project[0] = check.getProject();
		DisconnectOperation disc = new DisconnectOperation(project, true);
		
		op.add(check);
		op.add(disc);
		ProgressMonitorUtility.doTaskScheduled(op);
		return true;
	}
	
	public void addPages(){
		page = new ImportExampleWizardPage();
		addPage(page);
	}
	
	public void setResource(IRepositoryResource input){
		this.choice = input;
	}

}
