package org.universaal.tools.modelling.ontology.wizard.wizards;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Model;

public class OntologyProjectModel  {
	String ontologyName = "MyOntology";
	String projectName = "MyProject";
	boolean useDerivedValues = true;
	String packageName = "org.universaal.ontology.myontology";
	String ontologyNamespace;

	List<String> importedOntologies = new ArrayList<String>();	
	Model mavenModel = new Model();

	public OntologyProjectModel() {
		mavenModel.setModelVersion("4.0.0"); //$NON-NLS-1$
		mavenModel.setArtifactId(ontologyName);
		mavenModel.setGroupId(packageName);
		mavenModel.setVersion("0.1");
	}
	
	public String getOntologyName() {
		return ontologyName;
	}
	public void setOntologyName(String ontologyName) {
		if (this.ontologyName != ontologyName) {
			support.firePropertyChange("ontologyName", this.ontologyName, this.ontologyName = ontologyName);
			if (useDerivedValues) {
				setProjectName(ontologyName);
				setOntologyNamespace("http://"+revertDomainName(packageName) + "/" + getOntologyName());
			}
		}
	}
	public String getProjectName() {
		//if (useDerivedValues) {
		//	return ontologyName;
		//}
		//else {
			return projectName;
		//}
	}
	public void setProjectName(String projectName) {
		if (this.projectName != projectName) {
			support.firePropertyChange("projectName", this.projectName, this.projectName = projectName);
		}
	}
	public boolean isUseDerivedValues() {
		return useDerivedValues;
	}
	public void setUseDerivedValues(boolean useDerivedValues) {
		if (this.useDerivedValues != useDerivedValues) {
			support.firePropertyChange("useDerivedValues", this.useDerivedValues, this.useDerivedValues = useDerivedValues);			
		}
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		if (this.packageName != packageName) {
			support.firePropertyChange("packageName", this.packageName, this.packageName = packageName);
			if (useDerivedValues) 
				setOntologyNamespace("http://"+revertDomainName(packageName) + "/" + getOntologyName());
		}
	}
	
	public String revertDomainName(String packageName) {
		String[] path = packageName.split("\\.");
		StringBuffer buffer = new StringBuffer();
		for (int x=path.length-1; x>=0; x--) {
			if (x < path.length-1)
				buffer.append(".");
			buffer.append(path[x]);
		}
		return buffer.toString();
	}
	
	public String getOntologyNamespace() {
		//if (useDerivedValues) {
		//	return "http://"+revertDomainName(getPackageName()) + "/" + getOntologyName();
		//}
		//else {
			return ontologyNamespace;
		//}
	}

	public void setOntologyNamespace(String ontologyNamespace) {
		if (this.ontologyNamespace != ontologyNamespace) {
			support.firePropertyChange("ontologyNamespace", this.ontologyNamespace, this.ontologyNamespace = ontologyNamespace);
		}
	}
	
	public List<String> getImportedOntologies() {
		return importedOntologies;
	}
	public Model getMavenModel() {
		return mavenModel;
	}
	
	transient PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
    	support.addPropertyChangeListener(propertyName, listener);
    }
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName,
                listener);
    }
}
