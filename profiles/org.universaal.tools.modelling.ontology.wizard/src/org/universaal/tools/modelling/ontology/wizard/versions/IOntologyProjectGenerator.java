package org.universaal.tools.modelling.ontology.wizard.versions;

import org.apache.maven.model.Dependency;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.ProjectImportConfiguration;
import org.universaal.tools.modelling.ontology.wizard.wizards.OntologyProjectModel;

public interface IOntologyProjectGenerator {
    /**
     * Identifies MW version 0.3.0-SNAPSHOT.
     */
    public static final int VER_030=0;
    /**
     * Identifies MW version 1.0.0.
     */
    public static final int VER_100=1;
    /**
     * Identifies MW version 1.1.0.
     */
    public static final int VER_110=2;
    /**
     * Identifies MW version 1.2.0.
     */
    public static final int VER_120=3;
    /**
     * Identifies MW version 1.3.0.
     */
    public static final int VER_130=4;
    /**
     * Identifies MW version 2.0.0.
     */
    public static final int VER_200=5;
    
    /**
     * Get the MW version identifier.
     * 
     * @return The constant that identifies the MW version represented by this
     *         implementor.
     */
    public int getMWVersionNumber();
    
    
	/**
	 * Create a Maven POM file set up for the content of the ontology project
	 * @param ontModel The model with information about the ontology project
	 * @param project The Eclipse project
	 * @param configuration A Maven project configuration
	 * @param monitor The progress monitor for the job this is performed in
	 * @throws CoreException
	 */
	public void createPOM(OntologyProjectModel ontModel,
			IProject project, ProjectImportConfiguration configuration,
			IProgressMonitor monitor) throws CoreException;
	
	/**
	 * Creates a set of UML artefacts based on the provided model with content from the Wizard.
	 * The artefacts are added to the project identified by the Wizard. The Eclipse project must 
	 * exist before this method is called.
	 *  
	 * @param model
	 */
	public void createUMLArtefacts(OntologyProjectModel model);
	
	public Dependency[] getDependencies();
	
}
