package org.universaal.tools.conformanceTools.checks.api;

import org.eclipse.core.resources.IResource;

public interface Check {

	public final String ok = "icon_success_sml.gif";
	public final String ko = "icon_error_sml.gif"; 
	public final String unknown = "icon_question_sml.gif"; 

	public String getCheckName();

	public String getCheckDescription();

	public String check(IResource resource) throws Exception;

	public String getCheckResultDescription();
}