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
import java.io.InputStream;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.universaal.tools.packaging.tool.util.EffectivePOMContainer;
import org.w3c.dom.Document;

/**
 * 
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano Lenzi</a>
 * @version $LastChangedRevision$ ( $LastChangedDate$ )
 */
public class ExecutionUnit implements Serializable {

	private Document document;
	private XPathFactory xpf;
    private XPath xp;
    private String tempDir = org.universaal.tools.packaging.tool.Activator.tempDir;
	private Part part;
	private String id, configPath;
	private File[] configFilesAndFolders;
	private String groupId, artifactId, packaging, classifier, version;
	//private int spaceStartLevel = -1000;

	public ExecutionUnit(/*String id,*/ File[] configFilesAndFolders, Part part/*, int spaceStartLevel*/){
		//this.id = id;
		this.configFilesAndFolders = configFilesAndFolders;
		this.part = part;
		//this.spaceStartLevel = spaceStartLevel;
	}

	public File[] getConfigFilesAndFolders() {
		return configFilesAndFolders;
	}
	
	public void setConfigFileAndFolders(File[] configFilesAndFolders) {
		this.configFilesAndFolders = configFilesAndFolders;
	}
	
	/*
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

	*/
	
	public String getArtifactId(){
		return this.artifactId;
	}
	
	public String getXML(){

		this.setIdAndConfigPath();
		
		if(configFilesAndFolders != null){
			String r = "<executionUnit>";		
			r = r.concat("<deploymentUnit>"+id+"</deploymentUnit>");
			r = r.concat("<configFiles>"+configPath+"</configFiles>");
			//if(spaceStartLevel != -1000)
			//	r = r.concat("<spaceStartLevel>"+spaceStartLevel+"</spaceStartLevel>");		
			r = r.concat("</executionUnit>");

			return r;
		}
		else
			return "";
	}

	private void setIdAndConfigPath() {
		try{
			/*System.out.println("Readine Epom:"+tempDir+"/"+part.getName()+".epom.xml");
	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	
	        document = db.parse(tempDir+"/"+part.getName()+".epom.xml");
	        xpf = XPathFactory.newInstance();
            xp = xpf.newXPath();
            */
			
			EffectivePOMContainer.setDocument(part.getName());

            groupId = EffectivePOMContainer.getGroupId();
            artifactId = EffectivePOMContainer.getArtifactId();
            packaging = EffectivePOMContainer.getPackaging();
            classifier = EffectivePOMContainer.getClassifier();
            version = EffectivePOMContainer.getVersion();

            /*
            
            groupId = xp.evaluate("//project/groupId/text()", document.getDocumentElement());
            artifactId = xp.evaluate("//project/artifactId/text()", document.getDocumentElement());
            packaging = xp.evaluate("//project/pakaging/text()", document.getDocumentElement());
            classifier = xp.evaluate("//project/classifier/text()", document.getDocumentElement());
            version = xp.evaluate("//project/version/text()", document.getDocumentElement());
			
			*/
            
            id = groupId+":"+artifactId+":"+(packaging != "" ? packaging+":": "")+(classifier != "" ? classifier+":": "")+version;
			configPath = "config/"+artifactId;
			
		} catch (Exception e) {
    		e.printStackTrace();
    	}
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
