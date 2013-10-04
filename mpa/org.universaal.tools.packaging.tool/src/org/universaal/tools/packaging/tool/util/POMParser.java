package org.universaal.tools.packaging.tool.util;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.License;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

public class POMParser {

	private File pom;
	private String groupID, artifactID, packaging, version, name, url, description;
	private List<Dependency> deps;
	private List<License> licenses;

	public POMParser(File pom){

		groupID = artifactID = packaging = version = name = url = description = "";
		deps = new ArrayList<Dependency>();
		licenses = new ArrayList<License>();
		this.pom = pom;
		analyzePom();
	}

	private void analyzePom(){

		try{
			Model model;
			Reader reader = new FileReader(pom);
			MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
			model = xpp3Reader.read(reader);
			reader.close();
			if(model.getGroupId() != null)
				groupID = model.getGroupId();
			if(model.getArtifactId() != null)
				artifactID = model.getArtifactId();
			if(model.getPackaging() != null)
				packaging = model.getPackaging();
			if(model.getVersion() != null)
				version = model.getVersion();
			if(model.getName() != null)
				name = model.getName();
			if(model.getUrl() != null)
				url = model.getUrl();
			if(model.getDescription() != null)
				description = model.getDescription();
			if(model.getDependencies() != null)
				deps = model.getDependencies();
			if(model.getLicenses() != null)
				licenses = model.getLicenses();
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public List<License> getLicenses() {
		return licenses;
	}

	public String getGroupId() {
		return groupID;
	}
	public String getArtifactId() {
		return artifactID;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getVersion() {
		return version;
	}
	
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public String getDescription() {
		return description;
	}
	public List<Dependency> getDeps() {
		return deps;
	}
}