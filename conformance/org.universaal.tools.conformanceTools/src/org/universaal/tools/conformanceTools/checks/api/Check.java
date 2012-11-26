package org.universaal.tools.conformanceTools.checks.api;

import org.eclipse.core.resources.IResource;

public interface Check {

	public final String ok = ""; //image path
	public final String ko = ""; //image path
	public final String maybe = ""; //image path

	public String getCheckName();

	public String getCheckDescription();

	public String check(IResource resource) throws Exception;

	public String getCheckResultDescription();
}