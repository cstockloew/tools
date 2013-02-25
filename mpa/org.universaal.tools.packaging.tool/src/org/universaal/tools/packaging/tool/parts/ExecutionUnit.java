package org.universaal.tools.packaging.tool.parts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExecutionUnit {

	private List<SingleExecutionUnit> executionUnit;

	public List<SingleExecutionUnit> getExecutionUnit(){
		if(executionUnit == null)
			executionUnit = new ArrayList<ExecutionUnit.SingleExecutionUnit>();
		return executionUnit;
	}

	public String getXML(){
		
		String r = "";
		for(int i = 0; i < executionUnit.size(); i++)
			r = r.concat("<executionUnit>"+executionUnit.get(i).getXML()+"</executionUnit>");
		return r;
	}

	public class SingleExecutionUnit{

		private File configFile;
		private int spaceStartLevel = 0;

		public SingleExecutionUnit(){
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

		public String getXML(){
			return "<configFiles>"+configFile.toString()+"</configFiles>"+"<spaceStartLevel>"+spaceStartLevel+"</spaceStartLevel>";
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
}
