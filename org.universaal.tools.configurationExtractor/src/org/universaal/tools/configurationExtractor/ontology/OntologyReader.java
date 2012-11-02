package org.universaal.tools.configurationExtractor.ontology;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.universaal.tools.configurationExtractor.data.ConfigItem;
import org.universaal.tools.configurationExtractor.data.GeneralUCConfig;
import org.universaal.tools.configurationExtractor.data.ItemType;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import static org.universaal.tools.configurationExtractor.ontology.OntologyCreator.PREFIX;

/**
 * OntologyReader reads an OWL cource text and creates the ConfigItems from it
 * 
 * @author schwende
 */
public class OntologyReader {
	
	/**
	 * @param text OWL source text
	 * @return ConfigItems for a given OWL source text
	 */
	public static LinkedList<ConfigItem> getConfig(String text) throws Exception {
		// save the OWL text to a temporary file
		File temp = File.createTempFile("ontology.owl", ".tmp");
		temp.deleteOnExit();
		FileWriter writer = new FileWriter(temp);
		writer.write(text);
		writer.close();
		
		// read this file and parse it
		return readOntology(temp.getAbsolutePath());			
	}
	
	/**
	 * @param filename file to parse
	 * @return ConfigItems for configuration in OWL file
	 */
	private static LinkedList<ConfigItem> readOntology(String filename) {
		// read the ontology from the file
		OntModel m = readOntologyFromFile(filename);
		
		ConfigItem item;		
		Map<Integer, ConfigItem> configItems = new HashMap<Integer, ConfigItem>();
		GeneralUCConfig generalConfig = null;
		
		
		ExtendedIterator<OntClass> classes = m.listClasses();

		// loop through all classes in the ontology
	    while (classes.hasNext()) {
	      OntClass thisClass = (OntClass) classes.next();
	      // only accept an instance of UCConfigItem
	      if (! thisClass.getURI().equals(PREFIX + "UCConfigItem")) {
		      continue;
	      }
	      System.out.println("Found class: " + thisClass.toString());

	      
	      ExtendedIterator<? extends OntResource> instances = thisClass.listInstances();
	      
	      // loop through instances connected with this UCConfigItem
	      while (instances.hasNext()) {
	        Individual thisInstance = (Individual) instances.next();
	        
	        if (thisInstance.getOntClass().getURI().equals(PREFIX + "UCConfig")) {
	        	// thisInstance is UCConfig instance
	        	generalConfig = new GeneralUCConfig();
	        	
	    		DatatypeProperty pr = m.getDatatypeProperty(PREFIX + "hasAuthor");
	    		generalConfig.setParameter(GeneralUCConfig.AUTHOR, thisInstance.getProperty(pr).getLiteral().toString());
	    		
	    		pr = m.getDatatypeProperty(PREFIX + "hasName");
	    		generalConfig.setParameter(GeneralUCConfig.UC_NAME, thisInstance.getProperty(pr).getLiteral().toString());

	    		pr = m.getDatatypeProperty(PREFIX + "hasVersionNumber");
	    		generalConfig.setParameter(GeneralUCConfig.VERSION_NR, thisInstance.getProperty(pr).getLiteral().toString());

	    		pr = m.getDatatypeProperty(PREFIX + "hasUID");
	    		generalConfig.setParameter(GeneralUCConfig.UID, thisInstance.getProperty(pr).getLiteral().toString());
	    		
	    		System.out.println("GeneralConfig found!");
	    		
	    		
	        } else if (thisInstance.getOntClass().getURI().equals(PREFIX + "ConfigPanel")) {
	        	// thisInstance is ConfigPanel instance
	        	item = new ConfigItem(ItemType.PANEL);
	        	
	        	DatatypeProperty pr = m.getDatatypeProperty(PREFIX + "hasCaption");
	    		item.setParameter(ConfigItem.CAPTION, thisInstance.getProperty(pr).getLiteral().toString());
	    		
	    		pr = m.getDatatypeProperty(PREFIX + "hasLocationID");
	    		configItems.put(thisInstance.getProperty(pr).getLiteral().getInt(), item);
	        	
	        	System.out.println("ConfigPanel found!");
	        	
	        	
	        } else if (thisInstance.getOntClass().getURI().equals(PREFIX + "OntologyPanel")) {
	        	// thisInstance is ConfigPanel instance
	        	item = new ConfigItem(ItemType.ONTOLOGY_PANEL);
	        	
	        	DatatypeProperty pr = m.getDatatypeProperty(PREFIX + "hasCaption");
	    		item.setParameter(ConfigItem.CAPTION, thisInstance.getProperty(pr).getLiteral().toString());
	    		
	    		pr = m.getDatatypeProperty(PREFIX + "hasLocationID");
	    		configItems.put(thisInstance.getProperty(pr).getLiteral().getInt(), item);
	    		
	    		pr = m.getDatatypeProperty(PREFIX + "hasHoverText");
	    		item.setParameter(GeneralUCConfig.HOVER, thisInstance.getProperty(pr).getLiteral().toString());
	    		
	    		pr = m.getDatatypeProperty(PREFIX + "hasLabelText");
	    		item.setParameter(GeneralUCConfig.LABEL, thisInstance.getProperty(pr).getLiteral().toString());

	    		pr = m.getDatatypeProperty(PREFIX + "hasName");
	    		item.setParameter(GeneralUCConfig.NAME, thisInstance.getProperty(pr).getLiteral().toString());

	    		pr = m.getDatatypeProperty(PREFIX + "hasDomain");
    			item.setParameter(GeneralUCConfig.DOMAIN, thisInstance.getProperty(pr).getLiteral().toString());
	        	
	        	System.out.println("OntologyPanel found!");
	        	
	        	
	        } else if (thisInstance.getOntClass().getURI().equals(PREFIX + "UCConfigItem")) {
	        	// thisInstance is UCConfigItem instance
	        	item = new ConfigItem(ItemType.VARIABLE);

	    		DatatypeProperty pr = m.getDatatypeProperty(PREFIX + "hasHoverText");
	    		item.setParameter(GeneralUCConfig.HOVER, thisInstance.getProperty(pr).getLiteral().toString());
	    		
	    		pr = m.getDatatypeProperty(PREFIX + "hasLabelText");
	    		item.setParameter(GeneralUCConfig.LABEL, thisInstance.getProperty(pr).getLiteral().toString());

	    		pr = m.getDatatypeProperty(PREFIX + "hasName");
	    		item.setParameter(GeneralUCConfig.NAME, thisInstance.getProperty(pr).getLiteral().toString());

	    		pr = m.getDatatypeProperty(PREFIX + "hasValue");
    			item.setParameter(GeneralUCConfig.TYPE, thisInstance.getProperty(pr).getLiteral().toString());
	    		
	    		pr = m.getDatatypeProperty(PREFIX + "hasLocationID");
	    		configItems.put(thisInstance.getProperty(pr).getLiteral().getInt(), item);
	    		
	        	System.out.println("UCConfigItem found!");
	        	
	        } else {
	        	System.err.println("Found unknown instance: " + thisInstance.toString());	        	
	        }
	        
	      }
	      
	    }
	    
	    LinkedList<ConfigItem> items = new LinkedList<ConfigItem>();
	    items.add(generalConfig);
	    
	    for (Integer i : configItems.keySet()) {
			items.add(configItems.get(i));
		}
		
		return items;
	}
	
	/**
	 * @param filename file to read
	 * @return OntModel for the ontology in a given file
	 */
	private static OntModel readOntologyFromFile(String filename) {
		OntModel m = ModelFactory.createOntologyModel();

		InputStream in = FileManager.get().open(filename);
		if (in == null) {
		    System.err.println("File: " + filename + " not found");
		    return null;
		}        
		m.read(in, null);
		
		return m;
	}
	
}
