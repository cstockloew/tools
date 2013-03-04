package org.universaal.tools.packaging.tool.parts;


public class DeploymentUnit {

	private String id;
	private OS osType;
	private Platform platformType;	
	private ContainerUnit cu;

	public DeploymentUnit(String id, OS osType){		

		this.id = id;
		this.osType = osType;
		this.platformType = null;
		this.cu = null;
	}

	public DeploymentUnit(String id, Platform platformType){		

		this.id = id;
		this.osType = null;
		this.platformType = platformType;
		this.cu = null;
	}

	public DeploymentUnit(String id, ContainerUnit cu){		

		this.id = id;
		this.osType = null;
		this.platformType = null;
		this.cu = cu;
	}

	public String getId() {
		return id;
	}

	public OS getOsType() {
		return osType;
	}

	public Platform getPlatformType() {
		return platformType;
	}

	public ContainerUnit getCu() {
		return cu;
	}

	public void setCu(ContainerUnit cu) {
		this.cu = cu;
	}

	public String getXML(){

		String r = "";

		r = r.concat("<deploymentUnit><id>"+id+"</id>");
		if(cu != null)
			r = r.concat("<containerUnit>"+cu.getXML()+"</containerUnit>");
		else if(osType != null)
			r = r.concat("<osUnit>"+osType.toString()+"</osUnit>");
		else if(platformType != null)
			r = r.concat("<platformUnit>"+platformType.toString()+"</platformUnit>");

		r = r.concat("</deploymentUnit>");
		return r;
	}

	/*
	 * <xs:element name="deploymentUnit">
		<xs:complexType>
			<xs:choice>
				<xs:element name="osUnit" type="uapp:osType">
				</xs:element>
				<xs:element name="platformUnit" type="uapp:platformType">
				</xs:element>
				<xs:element name="containerUnit">
					<xs:complexType>
						<xs:choice>
							<xs:element name="karaf">
								<xs:complexType>
									<xs:sequence>
										<xs:element name="embedding" type="uapp:embeddingType" />
										<xs:element ref="krf:features" />
									</xs:sequence>
								</xs:complexType>
							</xs:element>
							<xs:element name="android">
								<xs:complexType>
									<xs:sequence>
										<xs:element name="name" type="xs:string" />
										<xs:element minOccurs="0" name="description" type="xs:string" />
										<xs:element maxOccurs="unbounded" name="location"
											type="xs:anyURI" />
									</xs:sequence>
								</xs:complexType>
							</xs:element>
							<xs:element name="tomcat" />
							<xs:element name="equinox" />
							<xs:element name="felix" />
							<xs:element name="osgi-android" />
						</xs:choice>
					</xs:complexType>
				</xs:element>
			</xs:choice>
			<xs:attribute name="id" type="xs:ID" />
		</xs:complexType>
	</xs:element>
	 */
}