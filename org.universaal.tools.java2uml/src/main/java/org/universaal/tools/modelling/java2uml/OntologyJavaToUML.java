package org.universaal.tools.modelling.java2uml;


import java.util.HashMap;
import java.util.Map;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.*;
import org.universAAL.middleware.owl.ObjectProperty;
import org.universAAL.middleware.owl.OntClassInfo;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.Property;
import org.universAAL.middleware.ui.owl.UIBusOntology;
import org.universAAL.ontology.ProfileOntology;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.location.LocationOntology;

public class OntologyJavaToUML {
	public static void main(String[] args) {
		OntologyJavaToUML java2uml = new OntologyJavaToUML();
		java2uml.generateUML();
	}

	private DataRepOntology dataRepOntology = new DataRepOntology();
	private UIBusOntology uiBusOntology = new UIBusOntology();
	private ProfileOntology profileOntology = new ProfileOntology();
	private PhThingOntology phThingOntology = new PhThingOntology();
	private LocationOntology locationOntology = new LocationOntology();

	private Map<String, Package> packageMap = new HashMap<String, Package>();
	
		
	public OntologyJavaToUML() {
		OntologyManagement.getInstance().register(dataRepOntology);
		OntologyManagement.getInstance().register(uiBusOntology);
		OntologyManagement.getInstance().register(profileOntology);
		OntologyManagement.getInstance().register(locationOntology);
		//OntologyManagement.getInstance().register(phThingOntology);
	}

	public void generateUML() {		
		generateUMLForOntology(dataRepOntology);
		generateUMLForOntology(uiBusOntology);
		generateUMLForOntology(profileOntology);
		generateUMLForOntology(locationOntology);
		//generateUMLForOntology(phThingOntology);
	}
	
	public void generateUMLForOntology(Ontology ontology) {
		System.out.println("======= Generating for " + ontology.getInfo().getResourceLabel() + "=======");
		System.out.println("Namespace: " +ontology.getInfo().getNamespace());

		//Model myModel = createModel("My model");				
		// importPrimitiveTypes(myModel);
		
//		Package pkg = myModel.createNestedPackage("My package");
		
		for (OntClassInfo info : ontology.getOntClassInfo()) {
			addClassToModel(info);
		}
		System.out.println("=======  Done with  " +  ontology.getInfo().getResourceLabel() + "  =======");
		
	}
	
	
	//private static ProfileOntology ontology = new ProfileOntology();

	public void addClassToModel(OntClassInfo info) {
		System.out.print("Class: " + info.getLocalName());
		for (String superCl : info.getNamedSuperClasses(false, true)) {
			System.out.print(" extends " + superCl);
		}
		
		System.out.println();

		System.out.println(" URI: " + info.getURI().toString());
		
		
		for (Property prop: info.getProperties()) {	
			addPropertyToClass(prop);
		}
		
	}
	
	public void addPropertyToClass(Property prop) {
		if (prop instanceof ObjectProperty)
			System.out.print( "  objectproperty");
		else 
			System.out.print("  datatypeproperty");
			
		System.out.println( (prop.isFunctional() ? " func " : " ")  + prop.getLocalName() + " : " + prop.getType());		
	}
	
	
	
	protected Model createModel(String name) {

		Model model = UMLFactory.eINSTANCE.createModel();
        model.setName(name);
        
//        out("Model '" + model.getQualifiedName() + "' created.");
        return model;

	}	
	
	
}