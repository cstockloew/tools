package org.universaal.tools.newwizard.plugin.wizards;

import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * This is a sample new wizard. Its role is to create a new file project
 */

public class NewItemWizard extends Wizard implements INewWizard {

    private NewItemWizardPage page;
    private IStructuredSelection selection;

    public NewItemWizard() {
	super();
	setNeedsProgressMonitor(true);
	ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(
		"org.universaal.tools.newwizard.plugin", //$NON-NLS-1$
		"icons/ic-uAAL-hdpi.png"); //$NON-NLS-1$
	setDefaultPageImageDescriptor(image);
	setWindowTitle(Messages.getString("Item.8"));
    }

    public void init(IWorkbench arg0, IStructuredSelection arg1) {
	selection = arg1;
    }

    public void addPages() {
	page = new NewItemWizardPage(selection);
	page.init(selection);
	addPage(page);
	page.setPageComplete(false);
    }
    
    public boolean canFinish() {
	return page.isPageComplete();
    }

    /**
     * This method is called when 'Finish' button is pressed in the wizard. We
     * will create an operation and run it using wizard as execution context.
     */
    public boolean performFinish() {
	// get info from the wizard
	final String clsname = page.getClasname().getText();
	final String clstype = page.getFileTemplateName();
	final int clsnumber = page.getDrop().getSelectionIndex();
	final String mwVersion=page.getVersionDropDown().getItem(page.getVersionDropDown().getSelectionIndex());

	// this job performs the creationof the item
	Job job = new WorkspaceJob("wizard.item.job") { //$NON-NLS-1$
	    public IStatus runInWorkspace(IProgressMonitor monitor)
		    throws CoreException {
		try {
		    // Get the place where we put the new file
		    IPackageFragmentRoot root = page.getPackageFragmentRoot();
		    IPackageFragment pack = page.getPackageFragment();
		    if (pack == null) {
			root.getPackageFragment(""); //$NON-NLS-1$
		    }
		    if (!pack.exists()) {
			String packName = pack.getElementName();
			pack = root.createPackageFragment(packName, true,
				monitor);
		    } else {
			monitor.worked(1);
		    }
		    IResource resource = pack.getCorrespondingResource();
		    IContainer container = (IContainer) resource;
		    // now create the new file
		    final IFile file = container.getFile(new Path(clsname
			    + ".java")); //$NON-NLS-1$
		    InputStream stream = FileStreamUtils.customizeFileStream(mwVersion+"/"+clstype, pack
			    .getElementName(), clsname);
		    if (file.exists()) {
			MessageDialog.openError(getShell(),
				Messages.getString("Item.1"), //$NON-NLS-1$
				Messages.getString("Item.2")); //$NON-NLS-1$
		    } else {
			file.create(stream, true, monitor);
		    }
		    stream.close();
		    // If it was a SCallee we must create as well the profiles
		    if (clsnumber == 2) {
			final IFile fileaux = container.getFile(new Path(
				clsname + "ProvidedService.java")); //$NON-NLS-1$
			InputStream streamaux = FileStreamUtils.customizeFileStream(
				mwVersion+"/"+"SCalleeProvidedService.java", pack.getElementName(), //$NON-NLS-1$
				clsname + "ProvidedService"); //$NON-NLS-1$
			if (fileaux.exists()) {
			    MessageDialog.openError(getShell(),
				    Messages.getString("Item.3"), //$NON-NLS-1$
				    Messages.getString("Item.4")); //$NON-NLS-1$
			} else {
			    fileaux.create(streamaux, true, monitor);
			}
			streamaux.close();
		    }
		    // Now update dependencies in pom
		    IFile pom = pack.getJavaProject().getProject().getFile(
			    "pom.xml"); //$NON-NLS-1$
		    if (pom.exists()) {
			// Modify the pom to be add dependencies
			pom.setContents(FileStreamUtils.modifyPomStream(pom.getContents(),
				clsnumber, mwVersion), true, true, monitor);
		    } else {
			MessageDialog.openError(getShell(),
				Messages.getString("Item.5"), //$NON-NLS-1$
				Messages.getString("Item.6")); //$NON-NLS-1$
		    }
		} catch (Exception e) {
		    throw new OperationCanceledException(e.getMessage());
		}
		return Status.OK_STATUS;
	    }
	};

	// Listener in case job fails
	job.addJobChangeListener(new JobChangeAdapter() {
	    public void done(IJobChangeEvent event) {
		final IStatus result = event.getResult();
		if (!result.isOK()) {
		    Display.getDefault().asyncExec(new Runnable() {
			public void run() {
			    MessageDialog.openError(getShell(), //
				    Messages.getString("Item.7"), result //$NON-NLS-1$
					    .getMessage());
			}
		    });
		}
	    }
	});

	// Because we don´t need to monitor changes in workspace,
	// we directly perform the job
	job.setRule(ResourcesPlugin.getWorkspace().getRoot());
	job.schedule();
	return true;
    }

}
