package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class ApplicationPart {

	/*
	 * <xs:element name="applicationPart">
					<xs:annotation>
						<xs:documentation>The list of software artifacts composing the
							application
						</xs:documentation>
					</xs:annotation>
					<xs:complexType>
						<xs:sequence>
							<xs:element maxOccurs="unbounded" ref="uapp:part" />
						</xs:sequence>
					</xs:complexType>
				</xs:element>
	 */

	List<Part> parts;

	public List<Part> getParts(){
		if(parts == null)
			parts = new ArrayList<Part>();
		return parts;
	}
}
