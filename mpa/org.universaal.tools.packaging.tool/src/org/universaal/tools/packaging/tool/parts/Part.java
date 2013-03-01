package org.universaal.tools.packaging.tool.parts;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.universaal.tools.packaging.tool.parts.Capability.Mandatory;
import org.universaal.tools.packaging.tool.parts.Capability.Optional;

public class Part {

	private String id; // uStore ID
	private String name; // internal use, name of the project representing the part
	//private List<Capability> partCapabilities;
	private Properties partCapabilities;
	private List<Requirement> partRequirements;
	private List<DeploymentUnit> deploymentUnits;
	private List<ExecutionUnit> executionUnits;

	public Part(String id){
		SecureRandom random = new SecureRandom();

		this.name = id;
		this.id = id+"_"+new BigInteger(130, random).toString(32);

		partCapabilities = new Properties();
		Mandatory[] mandatory = Capability.Mandatory.values();
		for(int i = 0; i < mandatory.length; i++){
			Capability c = new Capability(mandatory[i].toString(), Application.defaultString);
			partCapabilities.put(c.getName(), c.getValue());
		}

		Optional[] optional = Capability.Optional.values();
		for(int i = 0; i < optional.length; i++){
			Capability c = new Capability(optional[i].toString(), Application.defaultString);
			partCapabilities.put(c.getName(), c.getValue());
		}
	}

	public String getId() {
		return id;
	}
	public String getName(){
		return name;
	}
	//	public List<Capability> getPartCapabilities() {
	//		if(partCapabilities == null)
	//			partCapabilities = new ArrayList<Capability>();
	//		return partCapabilities;
	//	}
	public Properties getPartCapabilities() {
		return partCapabilities;
	}
	public void setPartCapabilities(Properties partCapabilities) {
		this.partCapabilities = partCapabilities;
	}
	public void setCapability(String name, String value){
		partCapabilities.put(name, value);
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
		try{
			Enumeration<Object> cs = partCapabilities.keys();
			while(cs.hasMoreElements()){
				String key = (String) cs.nextElement();
				if(key != null){
					String value = (String) partCapabilities.get(key);
					r = r.concat("<capability><name>"+key+"</name>"+"<value>"+value+"</value></capability>");
				}
			}
		}
		catch(Exception ex){}
		r = r.concat("</partCapabilities>");

		//		for(int i = 0; i < getPartCapabilities().size(); i++)
		//			r = r.concat("<capability>"+partCapabilities.get(i).getXML()+"</capability>");
		//		r = r.concat("</partCapabilities>");

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