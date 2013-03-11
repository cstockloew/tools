package org.universaal.tools.packaging.tool.parts;

public class Version {

	private String major, minor, micro, build;

	public Version(){
		major = Application.defaultString;
		minor = Application.defaultString;
		micro = Application.defaultString;
		build = Application.defaultString;
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

	public String getVersion(){
		if(!major.isEmpty() && !minor.isEmpty() && !micro.isEmpty() && !build.isEmpty())
			return major+"."+minor+"."+micro+"."+build;

		return Application.defaultVersion;
	}

	public void setVersion(String v){

		try{
			if(v != null && !v.isEmpty()){
				String[] vs = v.split(".");
				if(vs.length > 0){
					major = vs[0];
					if(vs[1] != null)
						minor = vs[1];
					if(vs[2] != null)
						micro = vs[2];
					if(vs[3] != null)
						build = vs[3];
				}
				else
					major = v;
			}
		}
		catch(Exception ex){}
	}

	public String getXML(){
		return "<major>"+major+"</major>"+"<minor>"+minor+"</minor>"+"<micro>"+micro+"</micro>"+"<build>"+build+"</build>";
	}

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
}