package org.universaal.tools.conformanceTools.checks.api;

public abstract class CheckImpl implements Check {

	protected String result = "Test not yet performed.";
	
	@Override
	public String getCheckResultDescription() {
		return result;
	}
}