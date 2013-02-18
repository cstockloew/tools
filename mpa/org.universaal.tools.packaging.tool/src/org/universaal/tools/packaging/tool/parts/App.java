package org.universaal.tools.packaging.tool.parts;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class App {

	private String name, appID, description, applicationProfile = "org.universAAL.ontology.profile.AALAppSubProfile";
	private Version version;
	private boolean multipart;
	private List<String> tags;
	private Contact applicationProvider;
	private List<LicenseSet> licenses;

	public App(){
		name  = Application.defaultString;
		appID = Application.defaultString;
		description = Application.defaultString;
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
		if(version == null)
			version = new Version();
		return version;
	}

	public boolean isMultipart() {
		return multipart;
	}

	public void setMultipart(boolean multipart) {
		this.multipart = multipart;
	}

	public List<String> getTags() {
		if(tags == null)
			tags = new ArrayList<String>();
		return tags;
	}

	public Contact getApplicationProvider() {
		if(applicationProvider == null)
			applicationProvider = new Contact();
		return applicationProvider;
	}

	public List<LicenseSet> getLicenses() {
		if(licenses == null)
			licenses = new ArrayList<App.LicenseSet>();
		return licenses;
	}

	public class SLA {

		String name;		
		URI link;

		public SLA(){

			name = Application.defaultString;
			try {
				link = new URI(Application.defaultURL);
			} catch (URISyntaxException e) {
			}
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
	}

	public class LicenseSet{

		SLA sla;
		List<License> licenseList;

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
	}

	/*
	 * <xs:element name="app">
					<xs:annotation>
						<xs:documentation>basic info about the application
						</xs:documentation>
					</xs:annotation>
					<xs:complexType>
						<xs:sequence>
							<xs:element name="name" type="xs:string">
								<xs:annotation>
									<xs:documentation>friendly name e.g. Medication Manager
									</xs:documentation>
								</xs:annotation>
							</xs:element>
							<xs:element name="version" type="uapp:versionType">
								<xs:annotation>
									<xs:documentation>version of the application</xs:documentation>
								</xs:annotation>
							</xs:element>
							<xs:element name="appId" type="xs:string">
								<xs:annotation>
									<xs:documentation>unique name. It is used as key by the Space
										Application Registry e.g.
										org.universaal.aaalapplication.medmanager</xs:documentation>
								</xs:annotation>
							</xs:element>
							<xs:element name="description" type="xs:string">
								<xs:annotation>
									<xs:documentation>free text description about the application
									</xs:documentation>
								</xs:annotation>
							</xs:element>
							<xs:element name="multipart" type="xs:boolean">
								<xs:annotation>
									<xs:documentation>The application consists of multiple parts
									</xs:documentation>
								</xs:annotation>
							</xs:element>
							<xs:element name="tags" type="xs:string">
								<xs:annotation>
									<xs:documentation>comma separated list of tags, e.g.
										&quot;medication, medication monitoring, control&quot;
									</xs:documentation>
								</xs:annotation>
							</xs:element>
							<xs:element name="applicationProvider" type="uapp:contactType">
								<xs:annotation>
									<xs:documentation>contact information to the provider of the
										application</xs:documentation>
								</xs:annotation>
							</xs:element>
							<xs:element maxOccurs="unbounded" minOccurs="0" name="licenses">
								<xs:annotation>
									<xs:documentation>root license - each artifact may be licensed
										under different license</xs:documentation>
								</xs:annotation>
								<xs:complexType>
									<xs:sequence>
										<xs:element maxOccurs="unbounded" minOccurs="0"
											name="license" type="uapp:licenseType" />
										<xs:element minOccurs="0" name="sla">
											<xs:annotation>
												<xs:documentation>service level agreement, if any
												</xs:documentation>
											</xs:annotation>
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
								<xs:annotation>
									<xs:documentation>URI of the AALAppSubProfile??? Is it not
										fixed to org.universAAL.ontology.profile.AALAppSubProfile?
									</xs:documentation>
								</xs:annotation>
							</xs:element>
						</xs:sequence>
					</xs:complexType>
				</xs:element>
	 */
}