package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManagement {

	/*
	 * <xs:element name="applicationManagement" minOccurs="0">
					<xs:annotation>
						<xs:documentation>Management capabilities.</xs:documentation>
					</xs:annotation>
					<xs:complexType>
						<xs:sequence>
							<xs:element name="contactPoint" type="xs:string">
								<xs:annotation>
									<xs:documentation>person or center in charge of providing
										assistance for the management
									</xs:documentation>
								</xs:annotation>
							</xs:element>
							<xs:element minOccurs="0" name="remoteManagement">
								<xs:annotation>
									<xs:documentation>software and protocols used for the remote
										access and management of the application
									</xs:documentation>
								</xs:annotation>
								<xs:complexType>
									<xs:sequence>
										<xs:element maxOccurs="unbounded" name="protocols"
											type="xs:string" />
										<xs:element name="software" type="uapp:artifactType" />
									</xs:sequence>
								</xs:complexType>
							</xs:element>
						</xs:sequence>
					</xs:complexType>
				</xs:element>
	 */
	private String contact;
	private List<RemoteManagement> remoteManagement;

	public ApplicationManagement(){
		contact = Application.defaultString;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<RemoteManagement> getRemoteManagement() {
		if(remoteManagement == null)
			remoteManagement = new ArrayList<RemoteManagement>();
		return remoteManagement;
	}

	public class RemoteManagement{

		private String protocol;
		private Artifact software;

		public RemoteManagement(){
			protocol = Application.defaultString;
			software = new Artifact();
		}

		public String getProtocol() {
			return protocol;
		}
		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}
		public Artifact getSoftware() {
			return software;
		}
		public void setSoftware(Artifact software) {
			this.software = software;
		}
	}
}