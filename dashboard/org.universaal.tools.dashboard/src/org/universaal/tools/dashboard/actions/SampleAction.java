package org.universaal.tools.dashboard.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.ClasspathEntry;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	ISelectionService service;
	IStructuredSelection structured;
	IFile file;
	IPath path;
	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		Shell parentShell = window.getShell();
		IProject project=null;
		Object element;
		try {
			//handlerService.executeCommand("org.universaal.tools.newwizard.plugin.command.startNewWizard", null);
			this.service = window.getSelectionService();
			this.structured = (IStructuredSelection) (service.getSelection("org.eclipse.jdt.ui.PackageExplorer"));
			element = structured.getFirstElement();
			
			if(element instanceof IResource){
				project = ((IResource)element).getProject();
			}else if (element instanceof PackageFragment){
				IJavaProject jProject = ((PackageFragment)element).getJavaProject();
				project = jProject.getProject();
			}else if (element instanceof IJavaElement){
				IJavaProject jProject = ((IJavaElement)element).getJavaProject();
				project = jProject.getProject();
			}
			
			if(project!=null)
				path = project.getFullPath();
			
			if(path!=null)
				MessageDialog.openInformation(parentShell, "Path", path.toPortableString());

		} catch (Exception e){
			e.printStackTrace();
			
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}