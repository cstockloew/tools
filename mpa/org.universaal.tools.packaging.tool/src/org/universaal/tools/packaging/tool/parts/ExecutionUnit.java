package org.universaal.tools.packaging.tool.parts;

import java.io.File;

public class ExecutionUnit {

	private String id;
	private File configFile;
	private int spaceStartLevel = -1000;

	public ExecutionUnit(){
		configFile = new File(Application.defaultFile);
	}

	public ExecutionUnit(String id, File configFile, int spaceStartLevel){
		this.id = id;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getXML(){

		if(configFile != null){
			String r = "<executionUnit>";		
			r = r.concat("<deploymentUnit>"+id+"</deploymentUnit>");
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
