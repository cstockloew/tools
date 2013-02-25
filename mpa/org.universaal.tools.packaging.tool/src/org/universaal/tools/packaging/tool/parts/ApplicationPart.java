package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class ApplicationPart {

	private List<Part> parts;

	public List<Part> getParts(){
		if(parts == null)
			parts = new ArrayList<Part>();
		return parts;
	}

	public String getXML(){

		String r = "";		
		r = r.concat("<applicationPart>");
		for(int i = 0; i < parts.size(); i++)
			r = r.concat(parts.get(i).getXML());
		r = r.concat("</applicationPart>");
		return r;
	}

	/*
	 * <xs:element name="applicationPart">
					<xs:complexType>
						<xs:sequence>
							<xs:element maxOccurs="unbounded" ref="uapp:part" />
						</xs:sequence>
					</xs:complexType>
				</xs:element>
	 */
}
