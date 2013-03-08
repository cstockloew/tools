package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRequirements {

	private List<Requirement> requirementsList;

	public List<Requirement> getRequirementsList() {

		if(requirementsList == null)
			requirementsList = new ArrayList<Requirement>();

		/* CAUTION - REQUIRED BECAUSE OF METHODS THAT OVERRIDE VALUES WITH NULL */
		List<Requirement> ret = new ArrayList<Requirement>();
		for(int i = 0; i < requirementsList.size(); i++)
			if(requirementsList.get(i) != null)
				ret.add(requirementsList.get(i));

		requirementsList.clear();
		for(int i = 0; i < ret.size(); i++)
			requirementsList.add(ret.get(i));
		/* CAUTION - REQUIRED BECAUSE OF METHODS THAT OVERRIDE VALUES WITH NULL */

		return requirementsList;
	}

	public String getXML(){

		String r = "";

		for(int i = 0; i < getRequirementsList().size(); i++)
			r = r.concat("<requirement>"+getRequirementsList().get(i).getXML()+"</requirement>");

		return r;
	}

	/*
	 * <xs:element minOccurs="0" name="applicationRequirements">
					<xs:complexType>
						<xs:sequence>
							<xs:element name="requirement" maxOccurs="unbounded"
								type="uapp:reqType" />
						</xs:sequence>
					</xs:complexType>
				</xs:element>
	 */
}