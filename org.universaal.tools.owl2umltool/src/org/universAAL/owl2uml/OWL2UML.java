package org.universAAL.owl2uml;

import java.io.IOException;

import org.universAAL.owl2uml.core.UML2Parser;

public class OWL2UML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String ontologyFile = args[0];
		String umlFile = args[1];

		UML2Parser parser = new UML2Parser();
		parser.loadOntology(ontologyFile, "", false);
		parser.generateUMLContent();
		try {
			parser.write(umlFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
