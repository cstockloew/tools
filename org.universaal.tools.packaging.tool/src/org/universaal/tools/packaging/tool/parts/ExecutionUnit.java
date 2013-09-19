/*

        Copyright 2007-2014 CNR-ISTI, http://isti.cnr.it
        Institute of Information Science and Technologies
        of the Italian National Research Council

        See the NOTICE file distributed with this work for additional
        information regarding copyright ownership

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package org.universaal.tools.packaging.tool.parts;

import java.io.File;
import java.io.Serializable;

/**
 * 
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano Lenzi</a>
 * @version $LastChangedRevision$ ( $LastChangedDate$ )
 */
public class ExecutionUnit implements Serializable {

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
