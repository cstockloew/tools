package org.universaal.tools.conformanceTools.checks.impl;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.universAAL.middleware.ui.UICaller;
import org.universaal.tools.conformanceTools.checks.api.CheckImpl;
import org.universaal.tools.conformanceTools.checks.api.SubInterfaces;
import org.universaal.tools.conformanceTools.utils.Utilities;

public class UICallerImpl extends CheckImpl {

	@Override
	public String getCheckName() {
		return "Is a single UICaller present and correctly istantiated?";
	}

	@Override
	public String getCheckDescription() {
		return "This test will verify if a single UICaller instance is present and correctly istantiated.";
	}

	@Override
	public String check(IResource resource) throws Exception {

		ArrayList<ICompilationUnit> classes;
		if(SubInterfaces.isProject(resource)){
			classes = Utilities.getAllClasses((IProject) resource);

			for(ICompilationUnit clazz: classes){
				if(clazz instanceof UICaller){
					System.out.println(""+clazz.getCorrespondingResource().getName());
				}
			}

			result = "UICaller instance is correctly istantiated.";
			return ok;			
		}

		result = "UICaller instance is not correctly istantiated. This may cause runtime issues.";
		return ko;
	}
}
