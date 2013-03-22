package org.universaal.tools.packaging.tool.parts;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class App {

	private String name, appID, description, applicationProfile = "org.universAAL.ontology.profile.AALAppSubProfile";
	private Version version;
	private boolean multipart;
	private String tags;
	private Contact applicationProvider;
	private List<LicenseSet> licenses;

	public App(){
		name  = Application.defaultString;
		appID = Application.defaultString;
		description = Application.defaultString;
		tags = Application.defaultString;

		version = new Version();
		applicationProvider = new Contact();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplicationProfile() {
		return applicationProfile;
	}

	public void setApplicationProfile(String applicationProfile) {
		this.applicationProfile = applicationProfile;
	}

	public Version getVersion() {
		return version;
	}

	public boolean isMultipart() {
		return multipart;
	}

	public void setMultipart(boolean multipart) {
		this.multipart = multipart;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Contact getApplicationProvider() {
		return applicationProvider;
	}

	public List<LicenseSet> getLicenses() {
		if(licenses == null)
			licenses = new ArrayList<App.LicenseSet>();
		return licenses;
	}

	public String getXML(){

		String r = "";
		//r = r.concat("<app>");
		r = r.concat("<name>"+name+"</name>");
		r = r.concat("<version>"+version.getXML()+"</version>");
		r = r.concat("<appId>"+appID+"</appId>");
		r = r.concat("<description>"+description+"</description>");
		r = r.concat("<multipart>"+multipart+"</multipart>");
		r = r.concat("<tags>"+tags+"</tags>");
		r = r.concat("<applicationProvider>"+applicationProvider.getXML()+"</applicationProvider>");
		for(int i = 0; i < getLicenses().size(); i++)
			r = r.concat(licenses.get(i).getXML());
		r = r.concat("<applicationProfile>"+applicationProfile+"</applicationProfile>");
		//r = r.concat("</app>");

		return r;
	}

	public class SLA {

		private String name;		
		private URI link;

		public SLA(){

			name = Application.defaultString;
			link = URI.create(Application.defaultURL);
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public URI getLink() {
			return link;
		}
		public void setLink(URI link) {
			this.link = link;
		}

		public String getXML(){
			
			String link_string = "";
			if(link.getScheme() != null && link.getScheme().equalsIgnoreCase("file")){
				String[] splitted = link.toASCIIString().split("/"); 
				link_string = splitted[splitted.length-1];
			}
			else
				link_string = link.toASCIIString();
			
			return "<sla><name>"+name+"</name><link>"+link_string+"</link></sla>";
		}
	}

	public class LicenseSet{

		private SLA sla;
		private List<License> licenseList;

		public LicenseSet(){
			sla = new SLA();
		}

		public SLA getSla() {
			return sla;
		}
		public void setSla(SLA sla) {
			this.sla = sla;
		}
		public List<License> getLicenseList() {
			if(licenseList == null)
				licenseList = new ArrayList<License>();
			return licenseList;
		}

		public String getXML(){

			String r = "";
			r = r.concat("<licenses>");
			for(int i = 0; i< licenseList.size(); i++)
				r = r.concat("<license>"+licenseList.get(i).getXML()+"</license>");
			r = r.concat(sla.getXML());
			r = r.concat("</licenses>");

			return r;
		}
	}

	/*
	 * <xs:element name="app">
					<xs:complexType>
						<xs:sequence>
							<xs:element name="name" type="xs:string">
							</xs:element>
							<xs:element name="version" type="uapp:versionType">
							</xs:element>
							<xs:element name="appId" type="xs:string">
							</xs:element>
							<xs:element name="description" type="xs:string">
							</xs:element>
							<xs:element name="multipart" type="xs:boolean">
							</xs:element>
							<xs:element name="tags" type="xs:string">
							</xs:element>
							<xs:element name="applicationProvider" type="uapp:contactType">
							</xs:element>
							<xs:element maxOccurs="unbounded" minOccurs="0" name="licenses">
								<xs:complexType>
									<xs:sequence>
										<xs:element maxOccurs="unbounded" minOccurs="0"
											name="license" type="uapp:licenseType" />
										<xs:element minOccurs="0" name="sla">
											<xs:complexType>
												<xs:sequence>
													<xs:element name="name" type="xs:string" />
													<xs:element name="link" type="xs:anyURI" />
												</xs:sequence>
											</xs:complexType>
										</xs:element>
									</xs:sequence>
								</xs:complexType>
							</xs:element>
							<xs:element name="applicationProfile" type="xs:string">
							</xs:element>
						</xs:sequence>
					</xs:complexType>
				</xs:element>
	 */
}