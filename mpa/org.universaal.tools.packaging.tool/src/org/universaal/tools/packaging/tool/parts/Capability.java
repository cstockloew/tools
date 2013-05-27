package org.universaal.tools.packaging.tool.parts;


public class Capability {

	private String name, value;

	public Capability(String name, String value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public final static String MANDATORY_TARGET_SPACE = "aal.target-space.category";
	public final static String MANDATORY_TARGET_SPACE_VERSION = "aal.target-space.version";
	public final static String MANDATORY_MW_VERSION = "aal.mw.version";
	public final static String MANDATORY_ONTOLOGIES = "aal.required-ontology"; 
	public final static String MANDATORY_TARGET_CONTAINER_NAME = "aal.target.container.name";
	public final static String MANDATORY_TARGET_CONTAINER_VERSION = "aal.target.container.version";
	public final static String MANDATORY_TARGET_DEPLOYMENT_TOOL = "aal.target.deployment-tool";

	public final static String OPTIONAL_OS = "aal.os.name"; 
	public final static String OPTIONAL_PLATFORM = "aal.platform.name";
	public final static String OPTIONAL_DEVICE_FEATURES_AUDIO = "aal.device.features.audio";
	public final static String OPTIONAL_DEVICE_FEATURES_VISUAL = "aal.device.features.visual";

	public String getXML(){
		return "<name>"+name+"</name><value>"+value+"</value>";
	}

	/*
aal.target-space.category
aal.target-space.version
aal.mw.version
aal.required-ontology
aal.target.container.name
aal.target.container.version
aal.target.deployment-tool

aal.os.name
aal.platform.name
aal.device.features.audio
aal.device.features.visual
...
	 */
}