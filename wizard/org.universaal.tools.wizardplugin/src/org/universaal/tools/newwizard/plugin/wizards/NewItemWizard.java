package org.universaal.tools.newwizard.plugin.wizards;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    }

    public void init(IWorkbench arg0, IStructuredSelection arg1) {
	selection = arg1;
    }

    public void addPages() {
	page = new NewItemWizardPage(selection);
	page.init(selection);
	addPage(page);
    }

    /**
     * This method is called when 'Finish' button is pressed in the wizard. We
     * will create an operation and run it using wizard as execution context.
     */
    @Override
    public boolean performFinish() {
	// get info from the wizard
	final String clsname = page.getClasname().getText();
	final String clstype = page.getFileTemplateName();
	final int clsnumber = page.getDrop().getSelectionIndex();

	// this job performs the creationof the item
	Job job = new WorkspaceJob("wizard.item.job") { //$NON-NLS-1$
	    @Override
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
		    InputStream stream = customizeFileStream(clstype, pack
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
			InputStream streamaux = customizeFileStream(
				"SCalleeProvidedService.java", pack.getElementName(), //$NON-NLS-1$
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
			pom.setContents(modifyPomStream(pom.getContents(),
				clsnumber), true, true, monitor);
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

    /**
     * This method parses the existing pom of the project and adds dependencies
     * for the new items
     * 
     * @param instream
     *            Input stream to the POM file
     * @param clsnumber
     *            Type of new item being generated
     * @return The stream of the POM modified
     */
    private InputStream modifyPomStream(InputStream instream, int clsnumber) {
	// TODO use XML parsing instead of manually parsing the file.
	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    instream));
	    StringBuilder output = new StringBuilder();
	    String line;
	    boolean context = false;
	    boolean service = false;
	    boolean io = false;
	    while ((line = reader.readLine()) != null) {
		if (line.contains("mw.bus.context")) { //$NON-NLS-1$
		    context = true;
		}
		if (line.contains("mw.bus.service")) { //$NON-NLS-1$
		    service = true;
		}
		if (line.contains("mw.bus.io")) { //$NON-NLS-1$
		    io = true;
		}
		if (line.contains("</dependencies>")) { //$NON-NLS-1$
		    StringBuilder outputnew = new StringBuilder();
		    if (!context && (clsnumber == 0 || clsnumber == 1)) {
			outputnew
				.append("		<dependency>\n" //$NON-NLS-1$
					+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
					+ "			<artifactId>mw.bus.context</artifactId>\n" //$NON-NLS-1$
					+ "			<version>0.3.0-SNAPSHOT</version>\n" //$NON-NLS-1$
					+ "		</dependency>\n"); //$NON-NLS-1$
		    }
		    if (!service
			    && (clsnumber == 2 || clsnumber == 3 || clsnumber == 6)) {
			outputnew
				.append("		<dependency>\n" //$NON-NLS-1$
					+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
					+ "			<artifactId>mw.bus.service</artifactId>\n" //$NON-NLS-1$
					+ "			<version>0.3.0-SNAPSHOT</version>\n" //$NON-NLS-1$
					+ "		</dependency>\n"); //$NON-NLS-1$
		    }
		    if (!io && (clsnumber == 4 || clsnumber == 5)) {
			outputnew
				.append("		<dependency>\n" //$NON-NLS-1$
					+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
					+ "			<artifactId>mw.bus.io</artifactId>\n" //$NON-NLS-1$
					+ "			<version>0.3.0-SNAPSHOT</version>\n" //$NON-NLS-1$
					+ "		</dependency>\n"); //$NON-NLS-1$
		    }
		    outputnew.append("</dependencies>"); //$NON-NLS-1$
		    line = line
			    .replace("</dependencies>", outputnew.toString()); //$NON-NLS-1$
		}
		output.append(line + "\n"); //$NON-NLS-1$
	    }
	    return new ByteArrayInputStream(output.toString().getBytes());
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Puts the template content into the newly generated item
     * 
     * @param fileTemplateName
     *            Name of the template file to use
     * @param packname
     *            Name of the package where it is generated
     * @param filename
     *            Name of the item being generated
     * @return The stream of the generated content
     */
    private InputStream customizeFileStream(String fileTemplateName,
	    String packname, String filename) {
	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    this.getClass().getClassLoader().getResourceAsStream(
			    "files/" + fileTemplateName))); //$NON-NLS-1$
	    StringBuilder output = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
		if (line.contains("/*TAG:PACKAGE*/")) { //$NON-NLS-1$
		    line = "package " + packname + ";\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (line.contains("/*TAG:CLASSNAME*/")) { //$NON-NLS-1$
		    line = line.replace("/*TAG:CLASSNAME*/", filename); //$NON-NLS-1$
		}
		output.append(line + "\n"); //$NON-NLS-1$
	    }
	    return new ByteArrayInputStream(output.toString().getBytes());
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }
}
