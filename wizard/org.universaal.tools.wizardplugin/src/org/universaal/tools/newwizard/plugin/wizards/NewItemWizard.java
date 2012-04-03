package org.universaal.tools.newwizard.plugin.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
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
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.universaal.tools.newwizard.plugin.versions.IMWVersion;

/**
 * This is a new wizard. Its role is to create a new file.
 */
public class NewItemWizard extends Wizard implements INewWizard {

    /**
     * The only page of the wizard
     */
    private NewItemWizardPage page;
    /**
     * Used to determine where to put the new item.
     */
    private IStructuredSelection selection;

    /**
     * Default Constructor.
     */
    public NewItemWizard() {
	super();
	setNeedsProgressMonitor(true);
	ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(
		"org.universaal.tools.newwizard.plugin", //$NON-NLS-1$
		"icons/ic-uAAL-hdpi.png"); //$NON-NLS-1$
	setDefaultPageImageDescriptor(image);
	setWindowTitle(Messages.getString("Item.0"));
    }

    /**
     * We will accept the selection in the workbench to see if we can initialize
     * from it. I guess is automatically handled by INewWizard.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection sel) {
	selection = sel;
    }

    public void addPages() {
	page = new NewItemWizardPage(selection);
	page.init(selection);
	addPage(page);
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
	final IMWVersion mwVersion=page.getMWVersion();
	final String clsname = page.getTextClass().getText();
	final int clstype = page.getDropClass().getSelectionIndex();

	// this job performs the creation of the item
	Job job = new WorkspaceJob(Messages
		.getString("Item.1")) { //$NON-NLS-1$
	    public IStatus runInWorkspace(IProgressMonitor monitor) {
		try {
		    // Get the place to put the new file (or create it)
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
		    if(!mwVersion.createNewFile(container, pack.getElementName(), clstype, clsname, monitor)){
			MessageDialog.openError(getShell(),
				Messages.getString("Item.2"), //$NON-NLS-1$
				Messages.getString("Item.3")); //$NON-NLS-1$
		    }
		    // Now update dependencies in pom
		    IFile pom = pack.getJavaProject().getProject().getFile(
			    "pom.xml"); //$NON-NLS-1$
		    if (pom.exists()) {
			// Modify the pom to  add dependencies
			mwVersion.modifyPOMFile(pom, clstype, monitor);
		    } else {
			MessageDialog.openError(getShell(),
				Messages.getString("Item.4"), //$NON-NLS-1$
				Messages.getString("Item.5")); //$NON-NLS-1$
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
			    MessageDialog.openError(getShell(), 
				    Messages.getString("Item.6"), result //$NON-NLS-1$
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
