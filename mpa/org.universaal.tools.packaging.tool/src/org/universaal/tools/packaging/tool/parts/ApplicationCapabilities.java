package org.universaal.tools.packaging.tool.parts;

import java.util.Enumeration;
import java.util.Properties;

public class ApplicationCapabilities {

	private Properties capabilities;

	public ApplicationCapabilities(){

		capabilities = new Properties();

		//		Mandatory[] mandatory = Capability.Mandatory.values();
		//		for(int i = 0; i < mandatory.length; i++){
		//			Capability c = new Capability(mandatory[i].toString(), Application.defaultString);
		//			capabilities.put(c.getName(), c.getValue());
		//		}
		//
		//		Optional[] optional = Capability.Optional.values();
		//		for(int i = 0; i < optional.length; i++){
		//			Capability c = new Capability(optional[i].toString(), Application.defaultString);
		//			capabilities.put(c.getName(), c.getValue());
		//		}

		capabilities.put(Capability.MANDATORY_TARGET_SPACE, "");
		capabilities.put(Capability.MANDATORY_TARGET_SPACE_VERSION, "");
		capabilities.put(Capability.MANDATORY_MW_VERSION, "");
		capabilities.put(Capability.MANDATORY_ONTOLOGIES, "");
		capabilities.put(Capability.MANDATORY_TARGET_CONTAINER_NAME, "");
		capabilities.put(Capability.MANDATORY_TARGET_CONTAINER_VERSION, "");
		capabilities.put(Capability.MANDATORY_TARGET_DEPLOYMENT_TOOL, "");
		capabilities.put(Capability.OPTIONAL_OS, "");
		capabilities.put(Capability.OPTIONAL_PLATFORM, "");
		capabilities.put(Capability.OPTIONAL_DEVICE_FEATURES_AUDIO, "");
		capabilities.put(Capability.OPTIONAL_DEVICE_FEATURES_VISUAL, "");
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
					if(value != null && !value.isEmpty())
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