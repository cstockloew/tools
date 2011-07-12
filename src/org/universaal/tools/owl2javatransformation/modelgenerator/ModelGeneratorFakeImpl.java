package org.universaal.tools.owl2javatransformation.modelgenerator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.universaal.tools.owl2javatransformation.model.OntologyClass;
import org.universaal.tools.owl2javatransformation.model.OntologyProperty;
import org.universaal.tools.owl2javatransformation.model.PropertyRestriction;
import org.universaal.tools.owl2javatransformation.model.RestrictionType;

public class ModelGeneratorFakeImpl implements ModelGenerator {

	@Override
	public Set<OntologyClass> generateClasses() {
		Set<OntologyClass> classes = new HashSet<OntologyClass>();
		String className = "MyTest";
		String packageName = "org.universAAL.ontology.phThing";
		Set<OntologyProperty> declaredProps = generateProps(className);
		Set<String> importedPackages = getImports(className);
		Set<PropertyRestriction> restrictions = generateRestrictions(className, declaredProps);
		OntologyClass ontologyClass = new OntologyClass(className, "org.universAAL.test.my-test", "just for fun", declaredProps, 
				"Sensor", importedPackages, packageName, restrictions);
		classes.add(ontologyClass);
		OntologyClass ontologyClass2 = new OntologyClass("OtherClassName", "org.universAAL.test.other-class-name", "just for fun", 
				new HashSet<OntologyProperty>(), "ManagedIndividual", importedPackages, packageName, new HashSet<PropertyRestriction>());
		classes.add(ontologyClass2);
		return classes;
	}

	
	private Set<OntologyProperty> generateProps(String clazzName) {
		Set<OntologyProperty> declaredProps = new HashSet<OntologyProperty>();
		OntologyProperty ontologyProperty = new OntologyProperty("MyTestProp", "org.universAAL.test.my-test-prop", 
				"i don't know", clazzName, "OtherClassName", false);
		declaredProps.add(ontologyProperty);
		OntologyProperty ontologyProperty2 = new OntologyProperty("MyTestDataTypeProp", "org.universAAL.test.my-test-data-prop", 
				"i don't know", clazzName, "Boolean", true);
		declaredProps.add(ontologyProperty2);
		return declaredProps;
	}
	
	private Set<PropertyRestriction> generateRestrictions(String clazzName, Set<OntologyProperty> properties) {
		Set<PropertyRestriction> propertyRestriction = new HashSet<PropertyRestriction>();
		Iterator<OntologyProperty> iter = properties.iterator();
		OntologyProperty firstProp = iter.next();
		PropertyRestriction restr = new PropertyRestriction(firstProp, 1, 0, RestrictionType.ALL_VALUES_FROM);
		propertyRestriction.add(restr);
		OntologyProperty secondProp = iter.next();
		PropertyRestriction restr2 = new PropertyRestriction(secondProp, 1, 1, RestrictionType.ALL_VALUES_FROM);
		propertyRestriction.add(restr2);
		OntologyProperty ontologyProperty = new OntologyProperty("measured_value", "http://ontology.universAAL.org/Device.owl#measuredValue", 
				"i don't know", clazzName, "Boolean", false);
		PropertyRestriction restr3 = new PropertyRestriction(ontologyProperty, 1, 0, RestrictionType.JUST_CARDINALITY);
		propertyRestriction.add(restr3);
		return propertyRestriction;
	}
	
	private Set<String> getImports(String clazzName) {
		Set<String> importedPackages = new HashSet<String>();
		importedPackages.add("org.universAAL.middleware.rdf");
		importedPackages.add("org.universAAL.middleware.owl");
		return importedPackages;
	}	
	
}
