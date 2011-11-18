package org.universaal.tools.configurationExtractor.ontology;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.TreeItem;
import org.universaal.tools.configurationExtractor.data.ConfigItem;
import org.universaal.tools.configurationExtractor.data.GeneralUCConfig;
import org.universaal.tools.configurationExtractor.data.ItemType;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.XSD;

/**
 * OntologyCreator is used to create an ontology from the configured configuration
 * 
 * @author schwende
 */
public class OntologyCreator {
	
	/**
	 * general usecases ontology namespace
	 */
	protected static final String PREFIX = "http://ontology.universAAL.org/usecases#";
	
	/**
	 * ontology namespace of the configured usecase
	 */
	private String UC_PREFIX;
	
	private int item_count;
	private Individual panel;
	
	

	/**
	 * creates a configuration-ontology and saves it to a file
	 * 
	 * @param itemMap ConfigItems for this usecase
	 * @param treeItems TreeItems of the configuration
	 * @param filename file to save the ontology to
	 */
	public void saveOntology(Map<Integer, ConfigItem> itemMap, TreeItem[] treeItems, String filename) throws Exception {
		System.out.println("creating the ontology ...");
		OntModel m = createOntology(itemMap, treeItems);
		FileWriter writer;
		
		System.out.println("saving the ontology ...");
		writer = new FileWriter(filename);
		m.write(writer, "RDF/XML-ABBREV");
		writer.close();
		System.out.println(filename + " saved.");
		
	}
	
	
	/**
	 * creates an OWL-source of the usecase configuration
	 * 
	 * @param itemMap ConfigItems for this usecase
	 * @param treeItems TreeItems of the configuration
	 * @return StringBuffer containing the OWL-source
	 */
	public StringBuffer getOntology(Map<Integer, ConfigItem> itemMap, TreeItem[] treeItems) throws Exception {	
		// save the ontology to a temporary file
		File temp = File.createTempFile("ontology.owl", ".tmp");
		saveOntology(itemMap, treeItems, temp.getAbsolutePath());
		
		// read the temporary file into a StringBuffer
		BufferedReader reader = new BufferedReader(new FileReader(temp));
		String line;
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line + "\n");
		}
		
		temp.deleteOnExit();
		
		return buffer;
	}

	private OntModel createOntology(Map<Integer, ConfigItem> itemMap, TreeItem[] treeItems) throws Exception {
		// check if the general config item can be found
		if (! (itemMap.get(treeItems[0].getData()) instanceof GeneralUCConfig)) {
			System.err.println("GeneralConfig not located at the expected location ...");
			return null;
		}
		
		GeneralUCConfig ucconf = (GeneralUCConfig) itemMap.get(treeItems[0].getData());
		// create the ontology model
		OntModel m = createModel();
		
		
		UC_PREFIX = "http://ontology.universAAL.org/usecases/" + ucconf.getUcNameTrimmed() 
				+ "-" + ucconf.getVersionNrTrimmed() + "-" + ucconf.getUid() +  "#";

		// set the main configuration item
		Individual conf = m.createIndividual(UC_PREFIX + "UCConfig", m.getOntClass(PREFIX + "UCConfig"));
		conf.addProperty(m.getProperty(PREFIX + "hasVersionNumber"), ucconf.getVersionNr());
		conf.addProperty(m.getProperty(PREFIX + "hasName"), ucconf.getUcName());
		conf.addProperty(m.getProperty(PREFIX + "hasAuthor"), ucconf.getAuthor());
		conf.addProperty(m.getProperty(PREFIX + "hasUID"), ucconf.getUid());
					
		item_count = 0;
		
		ConfigItem configItem, subItem;
        
		// loop through the tree
		for (int i = 1; i < treeItems.length; i++) {
			// get the ConfigItem for this TreeItem
			configItem = itemMap.get(treeItems[i].getData());
			addItem(m, conf, configItem); // add this item
			
			// loop through the subitems
			for (TreeItem subTreeItem : treeItems[i].getItems()) {
				subItem = itemMap.get(subTreeItem.getData());
				addItem(m, conf, subItem); // add this item
			}            	
		}
			
		
		return m;
	}

	/**
	 * Add a ConfigItem to the OWL model
	 * @param m Jena OWL model
	 * @param conf main configuration item
	 * @param configItemm ConfigItem to add
	 */
	private void addItem(OntModel m, Individual conf, ConfigItem configItem) {
		Individual indiv;
		switch (configItem.getItemType()) {
		
			case VARIABLE: // add variable properties
				indiv = m.createIndividual(UC_PREFIX + "Item_" + item_count++, m.getOntClass(PREFIX + "UCConfigItem"));
				conf.addProperty(m.getProperty(PREFIX + "hasItem"), indiv);
				indiv.addProperty(m.getProperty(PREFIX + "hasName"), configItem.getName());
				indiv.addProperty(m.getProperty(PREFIX + "hasValue"), configItem.getType());
				indiv.addProperty(m.getProperty(PREFIX + "hasLabelText"), configItem.getLabel());
				indiv.addProperty(m.getProperty(PREFIX + "hasHoverText"), configItem.getHover());
				indiv.addProperty(m.getProperty(PREFIX + "isInPanel"), panel);
				indiv.addProperty(m.getProperty(PREFIX + "hasLocationID"), item_count + "");
				break;
				
			case ONTOLOGY_PANEL: // add ontology panel properties
				panel = m.createIndividual(UC_PREFIX + "Item_" + item_count++, m.getOntClass(PREFIX + "OntologyPanel"));
				conf.addProperty(m.getProperty(PREFIX + "hasItem"), panel);
				panel.addProperty(m.getProperty(PREFIX + "hasCaption"), configItem.getCaption());
				panel.addProperty(m.getProperty(PREFIX + "hasName"), configItem.getName());
				panel.addProperty(m.getProperty(PREFIX + "hasDomain"), configItem.getDomain());
				panel.addProperty(m.getProperty(PREFIX + "hasLabelText"), configItem.getLabel());
				panel.addProperty(m.getProperty(PREFIX + "hasHoverText"), configItem.getHover());
				panel.addProperty(m.getProperty(PREFIX + "hasLocationID"), item_count + "");
				break;
				
			case PANEL: // add panel properties
				panel = m.createIndividual(UC_PREFIX + "Item_" + item_count++, m.getOntClass(PREFIX + "ConfigPanel"));
				conf.addProperty(m.getProperty(PREFIX + "hasItem"), panel);
				panel.addProperty(m.getProperty(PREFIX + "hasCaption"), configItem.getCaption());
				panel.addProperty(m.getProperty(PREFIX + "hasLocationID"), item_count + "");
				break;
				
		}
	}
	
    /**
     * @return the Jena OWL model for a usecase configuration
     */
    private static OntModel createModel() {
    	OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM); 
    	
    	/*
    	 * create the class UCConfig
    	 */
    
	    OntClass ucconfClass = m.createClass(PREFIX + "UCConfig");
	    
	    DatatypeProperty prop = m.createDatatypeProperty(PREFIX + "hasVersionNumber", true);
	    prop.addDomain(ucconfClass);
	    prop.addRange(XSD.xstring);
	    prop = m.createDatatypeProperty(PREFIX + "hasName", true);
	    prop.addDomain(ucconfClass);
	    prop.addRange(XSD.xstring);
	    prop = m.createDatatypeProperty(PREFIX + "hasAuthor", true);
	    prop.addDomain(ucconfClass);
	    prop.addRange(XSD.xstring);
	    prop = m.createDatatypeProperty(PREFIX + "hasUID", true);
	    prop.addDomain(ucconfClass);
	    prop.addRange(XSD.xstring);

	    
    	/*
    	 * create the class UCConfigItem
    	 */
	    
	    OntClass ucconfitemClass = m.createClass(PREFIX + "UCConfigItem");
	
	    prop = m.createDatatypeProperty(PREFIX + "hasValue", false);
	    prop.addDomain(ucconfitemClass);
	    prop.addRange(XSD.xstring);
	    prop.addRange(XSD.xint);
	    prop.addRange(XSD.xboolean);
	    prop = m.createDatatypeProperty(PREFIX + "hasName", true);
	    prop.addDomain(ucconfitemClass);
	    prop.addRange(XSD.xstring);
	    prop = m.createDatatypeProperty(PREFIX + "hasHoverText", true);
	    prop.addDomain(ucconfitemClass);
	    prop.addRange(XSD.xstring);
	    prop = m.createDatatypeProperty(PREFIX + "hasLabelText", true);
	    prop.addDomain(ucconfitemClass);
	    prop.addRange(XSD.xstring);


    	/*
    	 * create the class ConfigPanel
    	 */
	    
	    OntClass panelClass = m.createClass(PREFIX + "ConfigPanel");
	    panelClass.setSuperClass(ucconfitemClass);
	
	    prop = m.createDatatypeProperty(PREFIX + "hasCaption", true);
	    prop.addDomain(panelClass);
	    prop.addRange(XSD.xstring);
	    
	    ObjectProperty objProp = m.createObjectProperty(PREFIX + "hasItem", false);
	    objProp.addDomain(ucconfClass);
	    objProp.addRange(ucconfitemClass);
	    objProp.addRange(panelClass);


    	/*
    	 * create the class OntologyPanel
    	 */
	    
	    OntClass ontopanelClass = m.createClass(PREFIX + "OntologyPanel");
	    ontopanelClass.setSuperClass(ucconfitemClass);
	
	    prop = m.createDatatypeProperty(PREFIX + "hasCaption", true);
	    prop.addDomain(ontopanelClass);
	    prop.addRange(XSD.xstring);
	    
	    objProp = m.createObjectProperty(PREFIX + "hasItem", false);
	    objProp.addDomain(ucconfClass);
	    objProp.addRange(ucconfitemClass);
	    objProp.addRange(ontopanelClass);
	    
	    
	    
	    /*
	     * 
	     */

	    prop = m.createDatatypeProperty(PREFIX + "hasLocationID", true);
	    prop.addDomain(ucconfitemClass);
	    prop.addDomain(panelClass);
	    prop.addDomain(ontopanelClass);
	    prop.addRange(XSD.xint);
		return m;
	}

}
