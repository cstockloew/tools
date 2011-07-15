package org.universaal.tools.dashboard.listeners;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.universaal.tools.dashboard.views.DashboardView;

public class ProjectNameListener implements ISelectionListener {
	
	IWorkbenchPart part;
	IWorkbenchPart source;
	ISelection selection;
	
	public ProjectNameListener(IWorkbenchPart source){
		this.source = source;
	}

	@Override
	public void selectionChanged(IWorkbenchPart arg0, ISelection arg1) {
		this.part = arg0;
		this.selection = arg1;
		pageSelectionChanged();

	}
	
	protected void pageSelectionChanged(){
		if(part == source){
			return;
		}
		if(!(selection instanceof IStructuredSelection)){
			return;
		}
		IStructuredSelection sel = (IStructuredSelection)selection;
		IProject project=null;
		Object element;
		IPath path;
		
		element = sel.getFirstElement();
		
		if(element instanceof IResource){
			project = ((IResource)element).getProject();
		}else if (element instanceof PackageFragment){
			IJavaProject jProject = ((PackageFragment)element).getJavaProject();
			project = jProject.getProject();
		}else if (element instanceof IJavaElement){
			IJavaProject jProject = ((IJavaElement)element).getJavaProject();
			project = jProject.getProject();
		}
		
		if(project!=null){
			path = project.getFullPath();
			((DashboardView)source).setProjectName(path.toPortableString());
		}
		
		((DashboardView)source).setCurrentProject(project);
		
		
		
	}

}
