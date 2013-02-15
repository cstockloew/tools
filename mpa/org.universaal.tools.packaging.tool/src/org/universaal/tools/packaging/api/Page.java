package org.universaal.tools.packaging.api;

import org.universaal.tools.packaging.tool.parts.MultipartApplication;

public interface Page {

	public void setMPA(MultipartApplication mpa);

	public boolean validate();
}