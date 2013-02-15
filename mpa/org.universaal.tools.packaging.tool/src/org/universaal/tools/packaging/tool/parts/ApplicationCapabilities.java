package org.universaal.tools.packaging.tool.parts;

import java.util.Properties;

import org.universaal.tools.packaging.tool.parts.Capability.Mandatory;
import org.universaal.tools.packaging.tool.parts.Capability.Optional;

public class ApplicationCapabilities {

	//private List<Capability> capabilities;
	private Properties capabilities;

	public ApplicationCapabilities(){
		capabilities = new Properties();

		Mandatory[] mandatory = Capability.Mandatory.values();
		for(int i = 0; i < mandatory.length; i++){
			Capability c = new Capability(mandatory[i].toString(), MultipartApplication.defaultString);
			capabilities.put(c.getName(), c.getValue());
		}

		Optional[] optional = Capability.Optional.values();
		for(int i = 0; i < optional.length; i++){
			Capability c = new Capability(optional[i].toString(), MultipartApplication.defaultString);
			capabilities.put(c.getName(), c.getValue());
		}
	}

	public /*List<Capability>*/ Properties getCapabilities() {
		//if(capabilities == null)
		//capabilities = new ArrayList<Capability>();
		return capabilities;
	}

	public void setCapability(String name, String value){
		capabilities.put(name, value);
	}

	public void setCapabilities(Properties cs){
		capabilities = cs;
	}
}