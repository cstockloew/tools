package org.universaal.tools.packaging.tool.parts;

import java.net.URI;

public class License {

	private LicenseCategory category;
	private String name;
	private URI link;

	public License(){
		name = Application.defaultString;
		link = URI.create(Application.defaultURL);
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

	public String getXML(){

		String link_string = "";
		if(link.getScheme() != null && link.getScheme().equalsIgnoreCase("file")){
			String[] splitted = link.toASCIIString().split("/"); 
			link_string = splitted[splitted.length-1];
		}
		else
			link_string = link.toASCIIString();
		
		return "<category>"+category.toString()+"</category><name>"+name+"</name><link>"+link_string+"</link>";
	}

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
}