package org.universaal.tools.packaging.tool.parts;

public class Requirement {

	/*
	 * <xs:complexType name="reqType">
		<xs:annotation>
			<xs:documentation>describes single offering, mostly used for devices
				and platforms</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:choice>
				<xs:element name="reqAtom" type="uapp:reqAtomType" />
				<xs:element name="reqGroup" type="uapp:reqGroupType" />
			</xs:choice>
			<xs:element minOccurs="0" name="optional" type="xs:boolean" />
		</xs:sequence>
	</xs:complexType>
	 */
	private SingleRequirement singleRequirement;
	private RequirementsGroup requirementGroup;
	private boolean singleReq;
	private boolean optional;

	public Requirement(SingleRequirement singleRequirement, boolean optional){
		this.singleRequirement = singleRequirement;
		this.requirementGroup = null;
		this.optional = optional;
		this.singleReq = true;
	}

	public Requirement(RequirementsGroup requirementGroup, boolean optional){
		this.requirementGroup = requirementGroup;
		this.singleRequirement = null;
		this.optional = optional;
		this.singleReq = false;
	}

	public SingleRequirement getSingleRequirement() {
		return singleRequirement;
	}

	public RequirementsGroup getRequirementGroup() {
		return requirementGroup;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public boolean isSingleReq() {
		return singleReq;
	}
}