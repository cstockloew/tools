package org.universaal.tools.packaging.tool.parts;

import java.util.Enumeration;
import java.util.Properties;

import org.universaal.tools.packaging.tool.parts.Capability.Mandatory;
import org.universaal.tools.packaging.tool.parts.Capability.Optional;

public class ApplicationCapabilities {

	private Properties capabilities;

	public ApplicationCapabilities(){
		capabilities = new Properties();

		Mandatory[] mandatory = Capability.Mandatory.values();
		for(int i = 0; i < mandatory.length; i++){
			Capability c = new Capability(mandatory[i].toString(), Application.defaultString);
			capabilities.put(c.getName(), c.getValue());
		}

		Optional[] optional = Capability.Optional.values();
		for(int i = 0; i < optional.length; i++){
			Capability c = new Capability(optional[i].toString(), Application.defaultString);
			capabilities.put(c.getName(), c.getValue());
		}
	}

	public Properties getCapabilities() {
		return capabilities;
	}

	public void setCapability(String name, String value){
		capabilities.put(name, value);
	}

	public void setCapabilities(Properties cs){
		capabilities = cs;
	}

	public String getXML(){

		String r = "";

		try{
			Enumeration<Object> cs = capabilities.keys();
			while(cs.hasMoreElements()){
				String key = (String) cs.nextElement();
				if(key != null){
					String value = (String) capabilities.get(key);
					r = r.concat("<capability><name>"+key+"</name>"+"<value>"+value+"</value></capability>");
				}
			}
		}
		catch(Exception ex){}

		return r;
	}

	/*
	 * <xs:complexType name="capabilityType">
		<xs:sequence>
			<xs:element name="name" type="xs:string">
			</xs:element>
			<xs:element name="value" type="xs:string">
			</xs:element>
		</xs:sequence>
	</xs:complexType>
	 */
}