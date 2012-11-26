package org.universaal.tools.conformanceTools.checks.impl;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public class SubInterfaces {

	public static boolean isContainer(IResource resource){
		return resource instanceof IContainer;
	}

	public static boolean isFile(IResource resource){
		return resource instanceof IFile;
	}

	public static boolean isFolder(IResource resource){
		return resource instanceof IFolder;
	}

	public static boolean isProject(IResource resource){
		return resource instanceof IProject;
	}
}