package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManagement {

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

	public String getXML(){

		String r = "";
		//r = r.concat("<applicationManagement>");
		r = r.concat("<contactPoint>"+contact+"</contactPoint>");

		r = r.concat("<remoteManagement>");
		for(int i = 0; i < getRemoteManagement().size(); i++)
			r = r.concat(remoteManagement.get(i).getXML());
		r = r.concat("</remoteManagement>");

		//r = r.concat("</applicationManagement>");

		return r;
	}

	public class RemoteManagement{

		private List<String> protocols;
		private Artifact software;

		public RemoteManagement(){
			software = new Artifact();
		}

		public List<String> getProtocol() {
			if(protocols == null)
				protocols = new ArrayList<String>();
			return protocols;
		}
		public Artifact getSoftware() {
			return software;
		}
		public void setSoftware(Artifact software) {
			this.software = software;
		}

		public String getXML(){

			String r = "";
			for(int i = 0; i< getProtocol().size(); i++)
				r = r.concat("<protocols>"+protocols.get(i)+"</protocols>");
			r = r.concat("<software>"+software.getXML()+"</software>");

			return r;
		}
	}

	/*
	 * <xs:element name="applicationManagement" minOccurs="0">
					<xs:complexType>
						<xs:sequence>
							<xs:element name="contactPoint" type="xs:string">
							</xs:element>
							<xs:element minOccurs="0" name="remoteManagement">
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
}