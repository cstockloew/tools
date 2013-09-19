package org.universaal.tools.packaging.tool.parts;

public class KarafFeature {

	private String name, version, description, resolver, startLevel, groupID, artifactID;// bundle;
	private boolean start;

	public KarafFeature(String name, String version, String groupID, String artifactID){

		this.name = name;
		this.version = version;
		this.groupID = groupID;
		this.artifactID = artifactID;

		//this.bundle = "mvn:"+groupID+"/"+artifactID+"/"+version;

		this.description = "";
		this.resolver = "";
		this.startLevel = "0";
		this.start = false;
	}

	public KarafFeature(String name, String version, String groupID, String artifactID, String description, String resolver, String startLevel, boolean start){

		this.name = name;
		this.version = version;
		this.groupID = groupID;
		this.artifactID = artifactID;

		//this.bundle = "mvn:"+groupID+"/"+artifactID+"/"+version;

		this.description = description;
		this.resolver = resolver;
		this.start = start;
		this.startLevel = startLevel;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResolver() {
		return resolver;
	}
	public void setResolver(String resolver) {
		this.resolver = resolver;
	}
	public String getStartLevel() {
		return startLevel;
	}
	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getArtifactID() {
		return artifactID;
	}
	public void setArtifactID(String artifactID) {
		this.artifactID = artifactID;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
//	public String getBundle(){
//		return bundle;
//	}
}