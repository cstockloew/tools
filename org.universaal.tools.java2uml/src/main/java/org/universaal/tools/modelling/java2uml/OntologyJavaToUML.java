package org.universaal.tools.modelling.java2uml;


import org.universAAL.middleware.owl.ObjectProperty;
import org.universAAL.middleware.owl.OntClassInfo;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.Property;
import org.universAAL.middleware.ui.owl.UIBusOntology;
import org.universAAL.ontology.ProfileOntology;

public class OntologyJavaToUML {

	//private static ProfileOntology ontology = new ProfileOntology();
	private static UIBusOntology ontology = new UIBusOntology();

	public static void main(String[] args) {
		OntologyManagement.getInstance().register(ontology);
		
		for (OntClassInfo info : ontology.getOntClassInfo()) {
			addClassToModel(info);
		}
		System.out.println("Done");
		
	}

	
	public static void addClassToModel(OntClassInfo info) {
		System.out.print("Class: " + info.getLocalName());
		for (String superCl : info.getNamedSuperClasses(false, true)) {
			System.out.print(" extends " + superCl);
		}
		System.out.println();
		for (Property prop: info.getProperties()) {	
			addPropertyToClass(prop);
		}
		
	}
	
	public static void addPropertyToClass(Property prop) {
		if (prop instanceof ObjectProperty)
			System.out.print( "  objectproperty");
		else 
			System.out.print("  datatypeproperty");
			
		System.out.println( (prop.isFunctional() ? " func " : " ")  + prop.getLocalName() + " : " + prop.getType());		
	}
	
}