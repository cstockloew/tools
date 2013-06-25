package org.universaal.tools.externalserviceintegrator.actions;

import org.universAAL.ri.wsdlToolkit.ioApi.WSOperation;



public class CreateOntologyFile {

	WSOperation operation;

	public CreateOntologyFile(WSOperation operation) {
		super();
		this.operation = operation;
	}
	
	
	public boolean createOWLFile(){
		try{
//			OntModel ontologyModel=
//	                ModelFactory.createOntologyModel();
//			String SOURCE = "http://www.universAAL.org/";
//		    String NS = SOURCE + "#";
//			
//		    //create operation class
//		    OntClass parentClass = ontologyModel.createClass();
//		    parentClass.set
			
			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	
}
