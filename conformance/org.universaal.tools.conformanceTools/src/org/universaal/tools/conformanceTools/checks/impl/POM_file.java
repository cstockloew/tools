package org.universaal.tools.conformanceTools.checks.impl;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.universaal.tools.conformanceTools.checks.api.Check;

public class POM_file implements Check {

	private String result = "Test not yet performed.";

	@Override
	public String getCheckName() {
		return "Is a POM file present?";
	}

	@Override
	public String getCheckDescription() {
		return "This test will verify if selected project has an associated POM file.";
	}

	@Override
	public String check(IResource resource) throws Exception {

		if(SubInterfaces.isProject(resource)){
			IProject p = (IProject) resource;

			if(p.getFile(IMavenConstants.POM_FILE_NAME) != null){
				result = "Selected project has POM file.";
				return ok;
			}
		}

		result = "Selected project has not a POM file";
		return ko;
	}

	@Override
	public String getCheckResultDescription() {
		return result;
	}

	public File getPOMfile(IResource resource) throws Exception{

		if(check(resource).equals(ok)){
			return new File(((IProject)resource).getFile(IMavenConstants.POM_FILE_NAME).getLocationURI());
		}

		return null;
	}
}