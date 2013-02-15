package org.universaal.tools.packaging.tool.parts;

public class Artifact {

	/*
	 * <xs:complexType name="artifactType">
		<xs:sequence>
			<xs:element name="artifactId" type="xs:string" />
			<xs:element name="version" type="uapp:versionType" />
		</xs:sequence>
	</xs:complexType>
	 */
	private String artifactID;
	private Version version;

	public Artifact(){
		artifactID = MultipartApplication.defaultString;
	}

	public String getArtifactID() {
		return artifactID;
	}
	public void setArtifactID(String artifactID) {
		this.artifactID = artifactID;
	}
	public Version getVersion() {
		return version;
	}
	public void setVersion(Version version) {
		this.version = version;
	}
}
