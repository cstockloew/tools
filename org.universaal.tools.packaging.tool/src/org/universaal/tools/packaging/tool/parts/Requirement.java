package org.universaal.tools.packaging.tool.parts;

public class Requirement {

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

	@Override
	public boolean equals(Object other){

		if(other == null)
			return false;

		if(other == this)
			return true;

		if(other instanceof Requirement){

			Requirement req = (Requirement)other;

			if(req.isSingleReq()){
				if(req.getRequirementGroup() == null && req.getSingleRequirement().equals(this.singleRequirement) &&
						req.isOptional() == this.optional)
					return true;
			}
			else{
				if(req.getRequirementGroup().equals(this.requirementGroup) && req.getSingleRequirement() == null &&
						req.isOptional() == this.optional)
					return true;
			}			
		}

		return false;
	}

	public String getXML(){
		if(singleReq)
			return "<reqAtom>"+singleRequirement.getXML()+"</reqAtom><optional>"+optional+"</optional>";
		else
			return "<reqGroup>"+requirementGroup.getXML()+"</reqGroup><optional>"+optional+"</optional>";
	}

	/*
	 * <xs:complexType name="reqType">
		<xs:sequence>
			<xs:choice>
				<xs:element name="reqAtom" type="uapp:reqAtomType" />
				<xs:element name="reqGroup" type="uapp:reqGroupType" />
			</xs:choice>
			<xs:element minOccurs="0" name="optional" type="xs:boolean" />
		</xs:sequence>
	</xs:complexType>
	 */
}