package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class MPA {

	private List<Application> applications;

	public List<Application> getApplications() {
		if(applications == null)
			applications = new ArrayList<Application>();
		return applications;
	}
}