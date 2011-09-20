package org.universaal.tools.owl2javatransformation.modelgenerator;

import java.util.Set;

import org.universaal.tools.owl2javatransformation.model.OntologyClass;

/**
 * Generates the model
 * @author wolf
 *
 */
public interface ModelGenerator {
	
	/**
	 * Generates a set of classes. These classes represent the model.
	 * @return
	 */
	public Set<OntologyClass> generateClasses();

}
