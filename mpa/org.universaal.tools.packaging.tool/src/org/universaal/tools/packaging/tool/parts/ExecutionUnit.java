package org.universaal.tools.packaging.tool.parts;

import java.io.File;

public class ExecutionUnit {

	private File configFile;
	private int spaceStartLevel = -1000;

	public ExecutionUnit(){
		configFile = new File(Application.defaultFile);
	}

	public ExecutionUnit(File configFile, int spaceStartLevel){
		this.configFile = configFile;
		this.spaceStartLevel = spaceStartLevel;
	}

	public File getConfigFile() {
		return configFile;
	}
	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}
	public int getSpaceStartLevel() {
		return spaceStartLevel;
	}
	public void setSpaceStartLevel(int spaceStartLevel) {
		this.spaceStartLevel = spaceStartLevel;
	}

	public String getXML(){

		if(configFile != null){
			String r = "";
			r = r.concat("<executionUnit>");		
			r = r.concat("<configFiles>"+configFile.getName()+"</configFiles>");
			if(spaceStartLevel != -1000)
				r = r.concat("<spaceStartLevel>"+spaceStartLevel+"</spaceStartLevel>");		
			r = r.concat("</executionUnit>");

			return r;
		}
		else
			return "";
	}
}

/*
 * <xs:element name="executionUnit">
		<xs:complexType>
			<xs:sequence>
				<!-- <xs:element name="deploymentUnit" type="xs:IDREF"/> -->
				<xs:element name="configFiles" />
				<xs:element name="spaceStartLevel" minOccurs="0" />
			</xs:sequence>
		</xs:complexType>
	</xs:element>
 */
//}
