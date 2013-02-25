package org.universaal.tools.packaging.tool.parts;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Part {

	private String id;
	private List<Capability> partCapabilities;
	private List<Requirement> partRequirements;
	private List<DeploymentUnit> deploymentUnits;
	private List<ExecutionUnit> executionUnits;

	public Part(String id){
		SecureRandom random = new SecureRandom();

		this.id = id+"_"+new BigInteger(130, random).toString(32);;
	}

	public String getId() {
		return id;
	}
	public List<Capability> getPartCapabilities() {
		if(partCapabilities == null)
			partCapabilities = new ArrayList<Capability>();
		return partCapabilities;
	}
	public List<Requirement> getPartRequirements() {
		if(partRequirements == null)
			partRequirements = new ArrayList<Requirement>();
		return partRequirements;
	}
	public List<DeploymentUnit> getDeploymentUnits() {
		if(deploymentUnits == null)
			deploymentUnits = new ArrayList<DeploymentUnit>();
		return deploymentUnits;
	}
	public List<ExecutionUnit> getExecutionUnits() {
		if(executionUnits == null)
			executionUnits = new ArrayList<ExecutionUnit>();
		return executionUnits;
	}

	public String getXML(){
		
		String r = "";
		r = r.concat("<part>");

		r = r.concat("<partCapabilities>");
		for(int i = 0; i < getPartCapabilities().size(); i++)
			r = r.concat("<capability>"+partCapabilities.get(i).getXML()+"</capability>");
		r = r.concat("</partCapabilities>");

		r = r.concat("<partRequirements>");
		for(int i = 0; i < getPartRequirements().size(); i++)
			r = r.concat("<requirement>"+partRequirements.get(i).getXML()+"</requirement>");
		r = r.concat("</partRequirements>");

		for(int i = 0; i < getDeploymentUnits().size(); i++)
			r = r.concat(deploymentUnits.get(i).getXML());
		
		for(int i = 0; i < getExecutionUnits().size(); i++)
			r = r.concat(executionUnits.get(i).getXML());
		
		r = r.concat("<partId>"+id+"</partId>");

		r = r.concat("</part>");

		return r;
	}

	/*
	 * <xs:element name="part">
		<xs:complexType>
			<xs:sequence>
				<xs:element minOccurs="0" name="partCapabilities">
					<xs:complexType>
						<xs:sequence>
							<xs:element maxOccurs="unbounded" name="capability"
								type="uapp:capabilityType" />
						</xs:sequence>
					</xs:complexType>
				</xs:element>
				<xs:element minOccurs="0" name="partRequirements">
					<xs:complexType>
						<xs:sequence>
							<xs:element name="requirement" maxOccurs="unbounded"
								type="uapp:reqType" />
						</xs:sequence>
					</xs:complexType>
				</xs:element>
				<xs:element maxOccurs="unbounded" ref="uapp:deploymentUnit" />
				<xs:element maxOccurs="unbounded" minOccurs="0"
					ref="uapp:executionUnit" />
			</xs:sequence>
			<xs:attribute name="partId" type="xs:ID" />
		</xs:complexType>
	</xs:element>
	 */
}