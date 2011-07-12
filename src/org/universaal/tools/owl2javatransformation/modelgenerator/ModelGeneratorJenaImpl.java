package org.universaal.tools.owl2javatransformation.modelgenerator;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.universaal.tools.owl2javatransformation.model.OntologyClass;
import org.universaal.tools.owl2javatransformation.model.OntologyEntity;
import org.universaal.tools.owl2javatransformation.model.OntologyProperty;
import org.universaal.tools.owl2javatransformation.model.PropertyRestriction;
import org.universaal.tools.owl2javatransformation.model.RestrictionType;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class ModelGeneratorJenaImpl implements ModelGenerator {
	
	public static final PrintStream LOGGER = System.out;

	/**
	 * Return an Instance that already loads the OWL Definition
	 * 
	 * @param pathToOwl
	 * @return A OntologyModel that implements the OWL definition
	 */
	public static ModelGeneratorJenaImpl getInstanceFromOwl(String pathToOwl) {
		return new ModelGeneratorJenaImpl(pathToOwl);
	}	

	/**
	 * Generates a set of OntologyClasses.
	 */
	@Override
	public Set<OntologyClass> generateClasses() {
		createClasses();
		return new HashSet<OntologyClass>(classes.values());
	}

	private static final Set<String> getImportedPackages() {
		Set<String> importedPackages = new HashSet<String>();
		importedPackages.add("org.universAAL.middleware.rdf");
		importedPackages.add("org.universAAL.middleware.owl");
		return importedPackages;
	}

	private OntModel jenaModel;
	private Map<String, OntologyClass> classes;

	
	
	private ModelGeneratorJenaImpl(String pathToOwl) {
		jenaModel = generateJenaAPIModel(pathToOwl);
		classes = new HashMap<String, OntologyClass>();
	}
	
	private final OntModel generateJenaAPIModel(String pathToOwl) {
		InputStream in = FileManager.get().open(pathToOwl);
		if (in == null) {
			throw new IllegalArgumentException("File: " + pathToOwl
					+ " not found");
		}
		OntModel ontModel = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModel.read(in, null);
		return ontModel;
	}

	private void createClasses() {
		ExtendedIterator<OntClass> classIter = jenaModel.listNamedClasses();
		while (classIter.hasNext()) {
			OntClass current = classIter.next();
			if (! (current.isRestriction() || current.getURI().equals(OntologyEntity.TOP_MOST_CLASS_URI)) ) {
				LOGGER.println("currently processing class: " + current.getURI());
				OntologyClass transformClass = transformClass(current);
				classes.put(current.getURI(), transformClass);
			}
		}
	}

	private OntologyClass transformClass(OntClass ontClass) {
		return new OntologyClass(OntologyEntity.uriToSimpleName(ontClass.getURI()), ontClass.getURI(), 
					ontClass.getComment(null) == null ? "" : ontClass.getComment(null), getDeclaredProperties(ontClass), 
					OntologyEntity.uriToSimpleName(getSuperClass(ontClass)), getImportedPackages(), 
							getPackageName(ontClass.getURI()), getRestrictions(ontClass));
	}
	
	private String getSuperClass(OntClass ontClass) {
		Set<OntClass> classes = new HashSet<OntClass>();
		Iterator<OntClass> iter = ontClass.listSuperClasses(true);
		while (iter.hasNext()) {
			OntClass clazz = iter.next();	
			if (!clazz.isRestriction()) {
				classes.add(clazz);
			}
		}
		Preconditions.checkState(classes.size() == 1, "Class should have only one super class that is not a property restriction", ontClass);
		return classes.iterator().next().getURI();
	}

	private Set<PropertyRestriction> getRestrictions(OntClass ontClass) {
		Set<PropertyRestriction> restrictions = new HashSet<PropertyRestriction>();
		ExtendedIterator<OntClass> iter = ontClass.listSuperClasses(true);
		while (iter.hasNext()) {
			OntClass clazz = iter.next();
			if (clazz.isRestriction()) {
				Restriction restriction = clazz.asRestriction();
				LOGGER.println("Currently processing restriction: " + restriction);
				RestrictionType restrictionType = getRestrictionType(restriction);
				int maxCard = getMaxCardinality(restriction);
				int minCard = getMinCardinality(restriction);
				OntologyProperty ontologyProperty = new OntologyProperty(OntologyEntity.uriToSimpleName(restriction.getOnProperty().getURI()), 
						restriction.getOnProperty().getURI(), restriction.getComment(null) == null ? "" : restriction.getComment(null), 
						OntologyEntity.uriToSimpleName(ontClass.getURI()), 
						OntologyEntity.dataTypeToSimpleName(restriction.asAllValuesFromRestriction().getAllValuesFrom().getURI()), 
						restriction.getOnProperty().isDatatypeProperty());
				restrictions.add(new PropertyRestriction(ontologyProperty, maxCard, minCard, restrictionType));
			}
		}
		return restrictions;
	}

	private int getMinCardinality(Restriction restriction) {
		return 0;
	}

	private int getMaxCardinality(Restriction restriction) {
		return 1;
	}

	private RestrictionType getRestrictionType(Restriction restriction) {
		if (restriction.isAllValuesFromRestriction()) return RestrictionType.ALL_VALUES_FROM;
		Preconditions.checkState(false, "Restriction type is not supported", restriction);
		return null;
	}

	private String getPackageName(String uri) {
		return "org.universAAL.ontology.test";
	}

	private OntologyProperty transformProperty(OntProperty ontProp) {
		if (ontProp.isDatatypeProperty()) {
			
			return new OntologyProperty(OntologyEntity.uriToSimpleName(ontProp.getURI()), ontProp.getURI(), 
					ontProp.getComment(null) == null ? "" : ontProp.getComment(null), OntologyEntity.uriToSimpleName(ontProp.getDomain().getURI()), 
					OntologyEntity.dataTypeToSimpleName(ontProp.getRange().getURI()), 
					true);
		}
		else 
			return new OntologyProperty(OntologyEntity.uriToSimpleName(ontProp.getURI()), ontProp.getURI(), 
				ontProp.getComment(null), OntologyEntity.uriToSimpleName(ontProp.getDomain().getURI()), 
				OntologyEntity.uriToSimpleName(ontProp.getRange().getURI()), 
				false);
	}
	
	private Set<OntologyProperty> getDeclaredProperties(OntClass ontClass) {
		Set<OntologyProperty> declaredProps = new HashSet<OntologyProperty>();
		ExtendedIterator<OntProperty> iterator = ontClass.listDeclaredProperties(true);
		while (iterator.hasNext()) {
			OntProperty property = iterator.next();
			LOGGER.println("Currently processing property: " + property);
				declaredProps.add(transformProperty(property));
		}
		return declaredProps;
	}


}
