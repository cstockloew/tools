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

	public enum Mandatory{
		TARGET_SPACE, TARGET_SPACE_VERSION, MW_VERSION, ONTOLOGIES, TARGET_CONTAINER_NAME, TARGET_CONTAINER_VERSION, TARGET_DEPLOYMENT_TOOL
	}

	public enum Optional{
		OS, PLATFORM, DEVICE_FEATURES_AUDIO, DEVICE_FEATURES_VISUAL
	}

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