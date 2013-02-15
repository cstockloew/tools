package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRequirements {

	private List<Requirement> requirementsList;

	public List<Requirement> getRequirementsList() {
		if(requirementsList == null)
			requirementsList = new ArrayList<Requirement>();
		return requirementsList;
	}
}