package org.universaal.tools.packaging.tool.parts;

public class RequirementsGroup {

	private LogicalRelation relation;
	private SingleRequirement req1, req2;

	public RequirementsGroup(SingleRequirement req1, SingleRequirement req2, LogicalRelation relation){
		this.req1 = req1;
		this.req2 = req2;
		this.relation = relation;
	}

	public LogicalRelation getRelation() {
		return relation;
	}
	public void setRelation(LogicalRelation relation) {
		this.relation = relation;
	}
	public SingleRequirement getReq1() {
		return req1;
	}
	public void setReq1(SingleRequirement req1) {
		this.req1 = req1;
	}
	public SingleRequirement getReq2() {
		return req2;
	}
	public void setReq2(SingleRequirement req2) {
		this.req2 = req2;
	}

	@Override
	public boolean equals(Object other){
		
		if(other == this)
			return true;

		if(other instanceof RequirementsGroup){

			RequirementsGroup req = (RequirementsGroup)other;
			if(req.getReq1().equals(this.req1) && req.getReq2().equals(this.req2) && req.getRelation().equals(this.relation))
				return true;
		}

		return false;
	}

	public String getXML(){
		return "<logicalRelation>"+relation.toString()+"</logicalRelation><requirement>"+req1.getXML()+"</requirement><requirement>"+req2.getXML()+"</requirement>";
	}

	/*
	 * <xs:complexType name="reqGroupType">
		<xs:sequence>
			<xs:element name="logicalRelation" type="uapp:logicalRelationType" />
			<xs:element maxOccurs="2" minOccurs="2" name="requirement"
				type="uapp:reqType" />
		</xs:sequence>
	</xs:complexType>
	 */
}