package org.universaal.tools.packaging.tool.parts;

public class Artifact {

	private String artifactID;
	private Version version;

	public Artifact(){
		artifactID = Application.defaultString;
		version = new Version();
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

	public String getXML(){

		return "<artifactId>"+artifactID+"</artifactId><version>"+version.getXML()+"</version>";
	}

	/*
			<xs:element name="artifactId" type="xs:string" />
			<xs:element name="version" type="uapp:versionType" />
	 */
}
