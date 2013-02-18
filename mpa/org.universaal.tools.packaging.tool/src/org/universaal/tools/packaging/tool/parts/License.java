package org.universaal.tools.packaging.tool.parts;

import java.net.URI;
import java.net.URISyntaxException;

public class License {

	/*
	 * <xs:complexType name="licenseType">
		<xs:annotation>
			<xs:documentation>describe the single license</xs:documentation>
		</xs:annotation>
		<xs:sequence>
			<xs:element name="category" type="uapp:licenseCategoryType" />
			<xs:element name="name" type="xs:string" />
			<xs:element name="link" type="xs:anyURI" />
		</xs:sequence>
	</xs:complexType>
	 */

	LicenseCategory category;
	String name;
	URI link;

	public License(){
		name = Application.defaultString;
		try {
			link= new URI(Application.defaultURL);
		} catch (URISyntaxException e) {
		}
		category = LicenseCategory.APPLICATION;
	}

	public LicenseCategory getCategory() {
		return category;
	}
	public void setCategory(LicenseCategory category) {
		this.category = category;
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