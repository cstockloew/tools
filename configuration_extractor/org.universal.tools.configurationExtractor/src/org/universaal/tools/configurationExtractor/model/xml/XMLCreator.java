package org.universaal.tools.configurationExtractor.model.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.universaal.tools.configurationExtractor.view.Elements.BigElement;
import org.universaal.tools.configurationExtractor.view.Elements.ElementG;
import org.universaal.tools.configurationExtractor.view.Elements.ListG;
import org.universaal.tools.configurationExtractor.view.Elements.Root;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.*;
import org.universaal.tools.configurationExtractor.view.MainPanels.XMLPanel;

import org.apache.xml.serialize.XMLSerializer; 
import org.apache.xml.serialize.OutputFormat;


@SuppressWarnings("deprecation")
/**
 * This class contains methods to create a XML Data from the current configuration of CE.
 */
public class XMLCreator {

	private Document dom;
	private XMLPanel xmlPanel=XMLPanel.getInstance();
	private static XMLCreator xmlCreator=new XMLCreator();
	/**
	 * Constructor is private because of singleton pattern of this class
	 */
	private XMLCreator() {
		init();
	}
	/**
	 * This method initializes a dom object and creates a initial tree structure
	 */
	public void init(){
		createDocument();
		createDOMTree();
	}

	public static XMLCreator getInstance(){
		return xmlCreator;
	}

	/*
	 * Using JAXP in implementation independent manner create a document object
	 * using which we create a xml tree in memory
	 */
	private void createDocument() {

		//get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
		//get an instance of builder
		DocumentBuilder db = dbf.newDocumentBuilder();

		//create an instance of DOM
		dom = db.newDocument();

		}catch(ParserConfigurationException pce) {
			//dump it
			System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
			System.exit(1);
		}

	}

	/*
	 * This method creates the initial XML structure
	 */
	private void createDOMTree(){
		PanelWithElements panelWithElements= PanelWithElements.getInstance();
		Root root = panelWithElements.getRoot();
		
		//create the root element
		Element rootEle = dom.createElement("root");
		
		rootEle.setAttribute("id", root.getId());
		
		rootEle.setAttribute("name", root.getName());

		rootEle.setAttribute("title", root.getTitle());
		
		rootEle.setAttribute("info", root.getHelp());
		dom.appendChild(rootEle);
		Element panelEle=dom.createElement("panels");
		rootEle.appendChild(panelEle);
		LinkedList<ParentPanel> panels=panelWithElements.getParentsList();
		
		for(int i=0;i<panels.size();i++){
			ParentPanel parent = panels.get(i);
			Element elementPanel=null;
			
			if(parent instanceof BigListPanel){
				elementPanel = createListPanel((BigListPanel)parent);
				panelEle.appendChild(elementPanel);
				
			}else if(parent instanceof BigPanel){
				elementPanel = createPanel((BigPanel)parent);
				panelEle.appendChild(elementPanel);
			}else{
				System.out.println("Element wurde nicht definiert");
			}
			
			LinkedList<BigElement> elementsList = parent.getElementsList();

			Iterator it  = elementsList.iterator();
			while(it.hasNext()) {
				BigElement b = (BigElement)it.next();
			
				if(b instanceof ElementG){
					Element elementEle = createElement((ElementG)b);
					elementPanel.appendChild(elementEle);
				}else if(b instanceof ListG){
					Element elementEle = createList((ListG)b);
					elementPanel.appendChild(elementEle);
				}else{
					System.out.println("Element wurde nicht definiert");
				}
			}
		}	

	}

	/**
	 * Helper method which creates a XML element <Element>
	 * @param e The Element for which we need to create an xml representation
	 * @return XML element representing a Element
	 */
	private Element createElement(ElementG e){

		Element elementEle = dom.createElement(e.getElementType());
		elementEle.setAttribute("id", e.getId());

		Element typeEle = dom.createElement("type");
		Text typeText = dom.createTextNode(e.getType());
		typeEle.appendChild(typeText);
		elementEle.appendChild(typeEle);
		
		
		Element labelEle = dom.createElement("label");
		Text labelText = dom.createTextNode(e.getLabel());
		labelEle.appendChild(labelText);
		elementEle.appendChild(labelEle);

	
		Element titleEle = dom.createElement("title");
		Text titleText = dom.createTextNode(e.getTitle());
		titleEle.appendChild(titleText);
		elementEle.appendChild(titleEle);
		
	
		Element sValueEle = dom.createElement("standartValue");
		Text sValueText = dom.createTextNode(e.getStandartV());
		sValueEle.appendChild(sValueText);
		elementEle.appendChild(sValueEle);
		
		Element domainEle = dom.createElement("domain");
		Text domainText = dom.createTextNode(e.getDomain());
		domainEle.appendChild(domainText);
		elementEle.appendChild(domainEle);

		return elementEle;

	}
	/**
	 * Helper method which creates a XML element <List>
	 * @param l The List for which we need to create an xml representation
	 * @return XML element representing a List
	 */
	private Element createList(ListG l){

		Element elementEle = dom.createElement(l.getElementType());

		Element labelEle = dom.createElement("label");
		Text labelText = dom.createTextNode(l.getLabel());
		labelEle.appendChild(labelText);
		elementEle.appendChild(labelEle);
		Element titleEle = dom.createElement("title");
		Text titleText = dom.createTextNode(l.getTitle());
		titleEle.appendChild(titleText);
		elementEle.appendChild(titleEle);

		Element limitEle = dom.createElement("limit");
		Text limitText = dom.createTextNode(l.getLimit()+"");
		limitEle.appendChild(limitText);
		elementEle.appendChild(limitEle);
		
		
		Element domainEle = dom.createElement("domain");
		Text domainText = dom.createTextNode(l.getDomain());
		domainEle.appendChild(domainText);
		elementEle.appendChild(domainEle);

		return elementEle;

	}
	
	/**
	 * Helper method which creates a XML element <ListPanel>
	 * @param lp The BigListPanel for which we need to create an xml representation
	 * @return XML element representing a BigListPanel
	 */
	private Element createListPanel(BigListPanel lp){
			
		Element elementEle = dom.createElement("listPanel");
		elementEle.setAttribute("id", lp.getId());


		Element titleEle = dom.createElement("title");
		Text titleText = dom.createTextNode(lp.getTitle());
		titleEle.appendChild(titleText);
		elementEle.appendChild(titleEle);

		return elementEle;
	}
	
	/**
	 * Helper method which creates a XML element <Panel>
	 * @param p The BigPanel for which we need to create an xml representation
	 * @return XML element representing a BigPanel
	 */	
	private Element createPanel(BigPanel p){
	
		Element elementEle = dom.createElement("Panel");
		Element titleEle = dom.createElement("title");
		Text titleText = dom.createTextNode(p.getTitle());
		titleEle.appendChild(titleText);
		elementEle.appendChild(titleEle);
		
		return elementEle;
	}
	
	/**
	 * This method printed a XML text (via Controller) to XML Panel ("expert mode"of GUI) 
	 *
     */
	public void printToXmlPanel(){
		String s=generateXMLString(dom);
		xmlPanel.setText(s);
		
	}
	
	/**
	 * This method uses Xerces specific classes
	 * prints the XML document to file.
     */
	public void printToFile(File file){

		try
		{
			//print
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);

			//to generate output to console use this serializer
//			XMLSerializer serializer = new XMLSerializer(System.out, format);

			//to generate a file output use fileoutputstream 
			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(file), format);

			serializer.serialize(dom);

		} catch(IOException ie) {
		    ie.printStackTrace();
		}
	}
	// Generate String out of the XML document object
	   private String generateXMLString(Document xmlDoc) {

	     StringWriter  strWriter    = null;
	     XMLSerializer probeMsgSerializer   = null;
	     OutputFormat  outFormat    = null;
	     String xmlStr;

	     try {
	       probeMsgSerializer = new XMLSerializer();
	       strWriter = new StringWriter();
	       outFormat = new OutputFormat();

	       outFormat.setIndenting(true);
	       outFormat.setIndent(4);

	       // Define a Writer
	       probeMsgSerializer.setOutputCharStream(strWriter);

	       // Apply the format settings
	       probeMsgSerializer.setOutputFormat(outFormat);

	       // Serialize XML Document
	       probeMsgSerializer.serialize(xmlDoc);
	       xmlStr = strWriter.toString();
	       strWriter.close();

	     } catch (IOException ioEx) {
	         System.out.println("Error " + ioEx);
	         return "fehler";
	     }
	     return xmlStr;
	   }




}

