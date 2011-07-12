package org.universaal.tools.owl2javatransformation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.universaal.tools.owl2javatransformation.model.OntologyClass;
import org.universaal.tools.owl2javatransformation.modelgenerator.ModelGeneratorJenaImpl;

/**
 * This class is the entry point for the ontology creator package. It is used to
 * create a Java API from an OWL-Definition, thus simplifying later application
 * development. This main file is started by the corresponding ant-task @see
 * ant/createOntology.xml to read the owl file and start the class generation.
 * 
 * @author wolf@fzi.de
 * 
 */

public class Owl2JavaTransformation {

	/**
	 * Main method starting the class generation from an OWL file
	 * 
	 * @param args
	 *            the first String argument identifies the OWL file location, no
	 *            further arguments are expected and thus ignored
	 */
	public static void main(String[] args) {
		String pathToOwl = args[0];
		String outputPath = args[1];
		if (pathToOwl == null) {
			throw new IllegalArgumentException(
					"Usage: java org.openaal.ontologycreator.OntologyCreater.java my_ontology.owl outputpath");
		}
		new Owl2JavaTransformation().writeModelClasses(outputPath, 
				ModelGeneratorJenaImpl.getInstanceFromOwl(pathToOwl).generateClasses());
	}

		/**
	 * Creates the final java files using the velocity framework. The templates
	 * are merged with the parsed Ontology data
	 * 
	 * @param outputPath
	 *            the target where generated classes should be stored
	 */
	private void writeModelClasses(String outputPath, Set<OntologyClass> classes) {
		try {
			Velocity.init();
			VelocityContext context = new VelocityContext();
			// write each class to a file
			for (OntologyClass clazz : classes) {
				context.put("class", clazz);
				Template template = Velocity.getTemplate("src/templates/uAALClass.vm");
				BufferedWriter bw = openOutputFile(outputPath, clazz);
				template.merge(context, bw);
				bw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * create output directory
	 * 
	 * @param outputPath
	 *            the target location of the files
	 * @param clazz
	 *            the SamClass that should be stored in a file
	 * @throws IOException
	 *             happens if directory cannot be created
	 */
	private String createOutputDirectory(String outputPath)
			throws IOException {
		String directory = outputPath;
		File dir = new File(directory);
		dir.mkdirs();
		return directory;
	}

	/**
	 * Opens the resulting file for this class
	 * 
	 * @param outputPath
	 *            the base directory for this file
	 * @param clazz
	 *            the OntologyClass that should be written
	 * @return A BufferedWriter to store the results
	 * @throws IOException
	 */
	private BufferedWriter openOutputFile(String outputPath, OntologyClass clazz)
			throws IOException {
		String dir = createOutputDirectory(outputPath);
		File outfile = new File(dir, clazz.getName() + ".java");
		FileWriter fw = new FileWriter(outfile);
		return new BufferedWriter(fw);
	}
}
