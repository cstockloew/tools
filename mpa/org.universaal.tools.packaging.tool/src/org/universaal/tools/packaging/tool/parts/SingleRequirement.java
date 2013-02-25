package org.universaal.tools.packaging.tool.parts;

public class SingleRequirement {

	private String requirementName, requirementValue;
	private LogicalCriteria requirementCriteria;

	public SingleRequirement(String requirementName, String requirementValue, LogicalCriteria requirementCriteria){
		this.requirementName = requirementName;
		this.requirementValue = requirementValue;
		this.requirementCriteria = requirementCriteria;
	}

	public SingleRequirement(String requirementName, String requirementValue){
		this.requirementName = requirementName;
		this.requirementValue = requirementValue;
		this.requirementCriteria = LogicalCriteria.EQUAL;
	}

	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public String getRequirementValue() {
		return requirementValue;
	}
	public void setRequirementValue(String requirementValue) {
		this.requirementValue = requirementValue;
	}
	public LogicalCriteria getRequirementCriteria() {
		return requirementCriteria;
	}
	public void setRequirementCriteria(LogicalCriteria requirementCriteria) {
		this.requirementCriteria = requirementCriteria;
	}

	public String getXML(){
		return "<reqAtomName>"+requirementName+"</reqAtomName>"+"<reqAtomValue>"+requirementValue+"</reqAtomValue>"+"<reqCriteria>"+requirementCriteria.toString()+"</reqCriteria>";
	}

	/*
	 * <xs:complexType name="reqAtomType">
		<xs:annotation>
			<xs:documentation>describes a simple requirement</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="reqAtomName" type="xs:string" />
			<xs:element maxOccurs="unbounded" name="reqAtomValue"
				type="xs:string" />
			<xs:element default="equal" minOccurs="0" name="reqCriteria"
				type="uapp:logicalCriteriaType">
				<xs:annotation>
					<xs:documentation>should be considered as &quot;equal&quot;, when
						the element is omitted</xs:documentation>
				</xs:annotation>
			</xs:element>
		</xs:sequence>
	</xs:complexType>
	 */
}
