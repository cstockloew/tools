/*
	Copyright 2011 SINTEF, http://www.sintef.no
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
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

/**
 * Class that listens to the workbench to keep track of the currently selected
 * project.
 * @author Adrian
 *
 */
public class ProjectElementSelectionListener implements ISelectionListener {
	
	IWorkbenchPart part;
	IWorkbenchPart source;
	ISelection selection;
	
	public ProjectElementSelectionListener(IWorkbenchPart source){
		this.source = source;
	}

	@Override
	public void selectionChanged(IWorkbenchPart arg0, ISelection arg1) {
		this.part = arg0;
		this.selection = arg1;
		pageSelectionChanged();

	}
	
	/**
	 * When a new item is selected in the package explorer, it finds the 
	 * project containing this item, and then calls a method to display the 
	 * name of this project in the Dashboard.
	 */
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
		
		
		String resString = "";
		
		element = sel.getFirstElement();
		
		if(element instanceof IResource){
			resString = "Resource: " + ((IResource) element).getFullPath();
			project = ((IResource)element).getProject();
//			resString = "Resource ";
		}else if (element instanceof PackageFragment){
			resString = "PackageFragment: " + ((PackageFragment) element).getPath().toString();
			IJavaProject jProject = ((PackageFragment)element).getJavaProject();
			project = jProject.getProject();
			//resString = "PackageFragement ";
		}else if (element instanceof IJavaElement){
			resString = "Java: " + ((IJavaElement) element).getPath().toString();
			IJavaProject jProject = ((IJavaElement)element).getJavaProject();
			project = jProject.getProject();			
		}
		else {
			//if (element != null)
			//	resString = "Other: " + element.toString();
			return;
		}
/*		
		if(project!=null){
			path = project.getFullPath();
//			((DashboardView)source).setProjectName(resString + path.toPortableString());
//			element.
		}
*/		
		((DashboardView)source).setSelectedElementWithContext(element, resString, project, part);
		
		
		//((DashboardView)source).setProjectName(part.getTitle() + " " + part.getSite().getId() + " " + resString);				
		//((DashboardView)source).setCurrentProject(project);
		
		
		
	}

}
