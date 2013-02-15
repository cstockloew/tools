package org.universaal.tools.packaging.tool.parts;

public class Version {

	/*
	 * <xs:element default="0" name="major" type="xs:int" />
			<xs:element default="0" name="minor" type="xs:int" />
			<xs:element default="0" name="micro" type="xs:int" />
			<xs:element minOccurs="0" name="build" type="xs:string">
				<xs:annotation>
					<xs:documentation>e.g. major.minor.micro-build</xs:documentation>
				</xs:annotation>
			</xs:element>
	 * 
	 */

	String major, minor, micro, build;

	public Version(){
		major = MultipartApplication.defaultString;
		minor = MultipartApplication.defaultString;
		micro = MultipartApplication.defaultString;
		build = MultipartApplication.defaultString;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public String getMicro() {
		return micro;
	}

	public void setMicro(String micro) {
		this.micro = micro;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}
}