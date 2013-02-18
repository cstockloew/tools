package org.universaal.tools.packaging.tool.parts;

import java.io.File;

public class ExecutionUnit {

	private File configFile;
	private int spaceStartLevel = 0;

	public ExecutionUnit(){
		configFile = new File(Application.defaultFile);
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
}
