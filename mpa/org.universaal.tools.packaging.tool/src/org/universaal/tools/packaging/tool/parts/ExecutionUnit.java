package org.universaal.tools.packaging.tool.parts;

import java.io.File;

public class ExecutionUnit {

	/*private List<SingleExecutionUnit> executionUnit;

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
	}*/

	//public class SingleExecutionUnit{

	private File configFile;
	private int spaceStartLevel = 0;

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
			r = r.concat("<configFiles>"+configFile.getName()+"</configFiles>"+"<spaceStartLevel>"+spaceStartLevel+"</spaceStartLevel>");		
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
