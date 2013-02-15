package org.universaal.tools.packaging.tool.parts;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Contact {

	/*
	 * <xs:sequence>
			<xs:element minOccurs="0" name="organizationName" type="xs:string">
				<xs:annotation>
					<xs:documentation>Organization name, optional</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element minOccurs="0" name="certificate" type="xs:anyURI" />
			<xs:element minOccurs="0" name="contactPerson" type="xs:string">
				<xs:annotation>
					<xs:documentation>Name of the contact person, optional
					</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element minOccurs="0" name="streetAddress" type="xs:string">
				<xs:annotation>
					<xs:documentation>Street address for contacts, free text, optional
					</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element name="email" type="xs:string">
				<xs:annotation>
					<xs:documentation>email for contacts, optional</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element minOccurs="0" name="webAddress" type="xs:anyURI">
				<xs:annotation>
					<xs:documentation>Web site of the provider, free text, optional
					</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element minOccurs="0" name="phone" type="xs:string">
				<xs:annotation>
					<xs:documentation>Phone number for contacts, free text, optional
					</xs:documentation>
				</xs:annotation>
			</xs:element>
			<xs:element minOccurs="0" name="otherChannel">
				<xs:annotation>
					<xs:documentation>Other optional communication channels, fax, IM...
					</xs:documentation>
				</xs:annotation>
				<xs:complexType>
					<xs:sequence>
						<xs:element name="channelName" type="xs:string">
							<xs:annotation>
								<xs:documentation>The name/type of the channel, e.g. Fax,
									Skype...</xs:documentation>
							</xs:annotation>
						</xs:element>
						<xs:element name="channelDetails" type="xs:string">
							<xs:annotation>
								<xs:documentation>the identifier for this channel, e.g. Skype
									profile name, Fax number...</xs:documentation>
							</xs:annotation>
						</xs:element>
					</xs:sequence>
				</xs:complexType>
			</xs:element>
		</xs:sequence>
	 */

	String organizationName, contactPerson, streetAddress, email, phone;
	URI certificate, webAddress;
	List<OtherChannel> otherChannels;

	public Contact(){

		organizationName = MultipartApplication.defaultString;
		contactPerson = MultipartApplication.defaultString;
		streetAddress = MultipartApplication.defaultString;
		email = MultipartApplication.defaultString;
		phone = MultipartApplication.defaultString;

		try{
			certificate = new URI(MultipartApplication.defaultURL);
			webAddress = new URI(MultipartApplication.defaultURL);
		}
		catch(Exception ex){}
	}

	public Contact(URI certificate, URI webAddress){

		organizationName = MultipartApplication.defaultString;
		contactPerson = MultipartApplication.defaultString;
		streetAddress = MultipartApplication.defaultString;
		email = MultipartApplication.defaultString;
		phone = MultipartApplication.defaultString;

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

	public class OtherChannel{

		String channelName, channelDetails;

		public OtherChannel(){
			channelName = MultipartApplication.defaultString;
			channelDetails = MultipartApplication.defaultString;
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
	}
}