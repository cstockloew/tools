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
package org.universaal.tools.uploadopensourceplugin.handlers;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Class that generates the aalapp.xml file for a selected project. This file
 * is meant to contain important information about a project, such as author,
 * URL to SVN Repository and a description of the project.
 * @author Adrian
 *
 */
public class GenerateAalApp extends AbstractHandler {

	/**
	 * First finds the selected project in the package explorer, and then generates
	 * a new file at the project's root. Then opens the new file with an editor.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		ISelectionService service = PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getSelectionService();
		IStructuredSelection structured = (IStructuredSelection) service
				.getSelection("org.eclipse.jdt.ui.PackageExplorer");
		IProject project=null;
		Object element;
		IPath path;

		if(structured!=null){
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

		}
		if(project == null){
			MessageDialog.openInformation(PlatformUI.getWorkbench().
					getActiveWorkbenchWindow().getShell(), "No selected project",
					"Please select a project in the package explorer.");
			return null;
		}

		String string = project.getLocation().toPortableString();

		File file = new File(string, "aalapp.xml");
		try {
			file.createNewFile();
			project.refreshLocal(IProject.DEPTH_ONE, new NullProgressMonitor());

			IFile fileToOpen;
			do{
				fileToOpen = project.getFile("aalapp.xml");
			}while(fileToOpen==null);

			IEditorDescriptor desc = PlatformUI.getWorkbench().
					getEditorRegistry().getDefaultEditor(file.getName());
			page.openEditor(new FileEditorInput(fileToOpen), desc.getId());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (PartInitException e1) {
			e1.printStackTrace();
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
