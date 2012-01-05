package org.universaal.tools.modelling.ontology.wizard.wizards;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Model;

public class OntologyProjectModel {
	String ontologyName = "MyOntology";
	String projectName = "MyProject";
	boolean useDerivedValues = true;
	String packageName = "org.universaal.ontology.myontology";
	String ontologyNamespace;

	List<String> importedOntologies = new ArrayList<String>();	
	Model mavenModel = new Model();

	public OntologyProjectModel() {
		mavenModel.setModelVersion("4.0.0"); //$NON-NLS-1$
	}
	
	public String getOntologyName() {
		return ontologyName;
	}
	public void setOntologyName(String ontologyName) {
		this.ontologyName = ontologyName;
	}
	public String getProjectName() {
		if (useDerivedValues) {
			return ontologyName;
		}
		else {
			return projectName;
		}
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public boolean isUseDerivedValues() {
		return useDerivedValues;
	}
	public void setUseDerivedValues(boolean useDerivedValues) {
		this.useDerivedValues = useDerivedValues;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String revertDomainName(String packageName) {
		String[] path = packageName.split("\\.");
		System.out.println(packageName + "lengende " + path.length);
		StringBuffer buffer = new StringBuffer();
		for (int x=path.length-1; x>=0; x--) {
			if (x < path.length-1)
				buffer.append(".");
			buffer.append(path[x]);
		}
		return buffer.toString();
	}
	
	public String getOntologyNamespace() {
		if (useDerivedValues) {
			return "http://"+revertDomainName(getPackageName()) + "/" + getOntologyName();
		}
		else {
			return ontologyNamespace;
		}
	}

	public void setOntologyNamespace(String ontologyNamespace) {
		this.ontologyNamespace = ontologyNamespace;
	}
	public List<String> getImportedOntologies() {
		return importedOntologies;
	}
	public Model getMavenModel() {
		return mavenModel;
	}

}
