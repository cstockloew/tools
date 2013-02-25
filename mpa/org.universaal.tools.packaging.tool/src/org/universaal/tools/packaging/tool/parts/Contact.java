package org.universaal.tools.packaging.tool.parts;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Contact {

	private String organizationName, contactPerson, streetAddress, email, phone;
	private URI certificate, webAddress;
	private List<OtherChannel> otherChannels;

	public Contact(){

		organizationName = Application.defaultString;
		contactPerson = Application.defaultString;
		streetAddress = Application.defaultString;
		email = Application.defaultString;
		phone = Application.defaultString;

		try{
			certificate = URI.create(Application.defaultURL);
			webAddress = URI.create(Application.defaultURL);
		}
		catch(Exception ex){}
	}

	public Contact(URI certificate, URI webAddress){

		organizationName = Application.defaultString;
		contactPerson = Application.defaultString;
		streetAddress = Application.defaultString;
		email = Application.defaultString;
		phone = Application.defaultString;

		this.certificate = certificate;
		this.webAddress = webAddress;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public URI getCertificate() {
		return certificate;
	}

	public void setCertificate(URI certificate) {
		this.certificate = certificate;
	}

	public URI getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(URI webAddress) {
		this.webAddress = webAddress;
	}

	public List<OtherChannel> getOtherChannels() {
		if(otherChannels == null)
			otherChannels = new ArrayList<Contact.OtherChannel>();

		return otherChannels;
	}

	public String getXML(){

		String r = "";
		r = r.concat("<organizationName>"+organizationName+"</organizationName>");
		r = r.concat("<certificate>"+certificate.toASCIIString()+"</certificate>");
		r = r.concat("<contactPerson>"+contactPerson+"</contactPerson>");
		r = r.concat("<streetAddress>"+streetAddress+"</streetAddress>");
		r = r.concat("<email>"+email+"</email>");
		r = r.concat("<webAddress>"+webAddress.toASCIIString()+"</webAddress>");
		r = r.concat("<phone>"+phone+"</phone>");
		
		for(int i = 0; i < getOtherChannels().size(); i++)
			r = r.concat("<otherChannel>"+otherChannels.get(i).getXML()+"</otherChannel>");
		
		return r;
	}

	public class OtherChannel{

		private String channelName, channelDetails;

		public OtherChannel(){
			channelName = Application.defaultString;
			channelDetails = Application.defaultString;
		}

		public String getChannelName() {
			return channelName;
		}

		public void setChannelName(String channelName) {
			this.channelName = channelName;
		}

		public String getChannelDetails() {
			return channelDetails;
		}

		public void setChannelDetails(String channelDetails) {
			this.channelDetails = channelDetails;
		}

		public String getXML(){
			return "<channelName>"+channelName+"</channelName>"+"<channelDetails>"+channelDetails+"</channelDetails>";
		}
	}

	/*
	 * <xs:sequence>
			<xs:element minOccurs="0" name="organizationName" type="xs:string">
			</xs:element>
			<xs:element minOccurs="0" name="certificate" type="xs:anyURI" />
			<xs:element minOccurs="0" name="contactPerson" type="xs:string">
			</xs:element>
			<xs:element minOccurs="0" name="streetAddress" type="xs:string">
			</xs:element>
			<xs:element name="email" type="xs:string">
			</xs:element>
			<xs:element minOccurs="0" name="webAddress" type="xs:anyURI">
			</xs:element>
			<xs:element minOccurs="0" name="phone" type="xs:string">
			</xs:element>
			
			<xs:element minOccurs="0" name="otherChannel">
				<xs:complexType>
					<xs:sequence>
						<xs:element name="channelName" type="xs:string">
						</xs:element>
						<xs:element name="channelDetails" type="xs:string">
						</xs:element>
					</xs:sequence>
				</xs:complexType>
			</xs:element>
		</xs:sequence>
	 */
}