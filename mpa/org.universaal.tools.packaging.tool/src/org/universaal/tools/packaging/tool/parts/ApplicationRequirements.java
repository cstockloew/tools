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

	public String getXML(){
		String r = "";

		for(int i = 0; i < getRequirementsList().size(); i++)
			r = r.concat("<requirement>"+requirementsList.get(i).getXML()+"</requirement>");

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