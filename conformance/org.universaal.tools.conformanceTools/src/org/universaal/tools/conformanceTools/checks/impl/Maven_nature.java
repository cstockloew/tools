package org.universaal.tools.conformanceTools.checks.impl;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.universaal.tools.conformanceTools.checks.api.Check;

public class Maven_nature implements Check {

	private String result = "Test not yet performed.";

	@Override
	public String getCheckName() {
		return "Is it a Maven project?";
	}

	@Override
	public String getCheckDescription() {
		return "This test will verify if selected project is a Maven project";
	}

	@Override
	public String check(IResource resource) throws Exception {

		if(SubInterfaces.isProject(resource)){
			IProject p = (IProject) resource;

			if(p.hasNature(IMavenConstants.NATURE_ID)){
				result = "Selected project has Maven nature.";
				return ok;
			}
		}

		result = "Selected project has not Maven nature.";
		return ko;
	}

	@Override
	public String getCheckResultDescription() {
		return result;
	}
}