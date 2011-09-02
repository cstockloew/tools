package model.xml;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;


import java.util.Iterator;
import java.util.LinkedList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import view.Elements.BigElement;
import view.Elements.ElementG;
import view.Elements.ListG;

import view.Elements.Root;
import view.LowLevelPanels.*;
import view.MainPanels.XMLPanel;


//For jdk1.5 with built in xerces parser
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;


public class XMLCreator {


	private Document dom;
	private XMLPanel xmlPanel=XMLPanel.getInstance();
	
	private static XMLCreator xmlCreator=new XMLCreator();

	private XMLCreator() {
		//create a list to hold the data
//		myData = eList;

		//Get a DOM object
		createDocument();
		createDOMTree();
	}
	public void init(){
		createDocument();
		createDOMTree();
	}

	public static XMLCreator getInstance(){
		return xmlCreator;
	}

	/**
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

	/**
	 * The real workhorse which creates the XML structure
	 */
	private void createDOMTree(){
		PanelWithElements panelWithElements= PanelWithElements.getInstance();
		Root root = panelWithElements.getRoot();
		//create the root element
		Element rootEle = dom.createElement("root");
		
		rootEle.setAttribute("id", root.getId());
		
		rootEle.setAttribute("name", root.getName());

		rootEle.setAttribute("title", root.getTitle());
		
		rootEle.setAttribute("info", root.getInformation());
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
	 * Helper method which creates a XML element <Book>
	 * @param b The book for which we need to create an xml representation
	 * @return XML element snippet representing a book
	 */
	private Element createElement(ElementG b){

		Element elementEle = dom.createElement(b.getElementType());
		elementEle.setAttribute("id", b.getId());

		Element typeEle = dom.createElement("type");
		Text typeText = dom.createTextNode(b.getType());
		typeEle.appendChild(typeText);
		elementEle.appendChild(typeEle);
		
		
		Element labelEle = dom.createElement("label");
		Text labelText = dom.createTextNode(b.getLabel());
		labelEle.appendChild(labelText);
		elementEle.appendChild(labelEle);

	
		Element titleEle = dom.createElement("title");
		Text titleText = dom.createTextNode(b.getTitle());
		titleEle.appendChild(titleText);
		elementEle.appendChild(titleEle);
		
	
		Element sValueEle = dom.createElement("standartValue");
		Text sValueText = dom.createTextNode(b.getStandartV());
		sValueEle.appendChild(sValueText);
		elementEle.appendChild(sValueEle);
		
		Element domainEle = dom.createElement("domain");
		Text domainText = dom.createTextNode(b.getDomain());
		domainEle.appendChild(domainText);
		elementEle.appendChild(domainEle);

		return elementEle;

	}
	//String label,String title, String limit,String domain
	private Element createList(ListG b){

		Element elementEle = dom.createElement(b.getElementType());

		Element labelEle = dom.createElement("label");
		Text labelText = dom.createTextNode(b.getLabel());
		labelEle.appendChild(labelText);
		elementEle.appendChild(labelEle);


		Element titleEle = dom.createElement("title");
		Text titleText = dom.createTextNode(b.getTitle());
		titleEle.appendChild(titleText);
		elementEle.appendChild(titleEle);

		Element limitEle = dom.createElement("limit");
		Text limitText = dom.createTextNode(b.getLimit()+"");
		limitEle.appendChild(limitText);
		elementEle.appendChild(limitEle);
		
		
		Element domainEle = dom.createElement("domain");
		Text domainText = dom.createTextNode(b.getDomain());
		domainEle.appendChild(domainText);
		elementEle.appendChild(domainEle);

		return elementEle;

	}
	
	
	
	private Element createListPanel(BigListPanel lp){
			
		Element elementEle = dom.createElement("listPanel");
		elementEle.setAttribute("id", lp.getId());


		Element titleEle = dom.createElement("title");
		Text titleText = dom.createTextNode(lp.getTitle());
		titleEle.appendChild(titleText);
		elementEle.appendChild(titleEle);

		return elementEle;
	}
	
	private Element createPanel(BigPanel p){
		
		Element elementEle = dom.createElement("Panel");

		Element titleEle = dom.createElement("title");
		Text titleText = dom.createTextNode(p.getTitle());
		System.out.println("panel=="+p.getTitle());
		titleEle.appendChild(titleText);
		elementEle.appendChild(titleEle);
		
		return elementEle;
	}
	
	
	

	/**
	 *
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


			//to generate a file output use fileoutputstream instead of system.out
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

	       // Setup format settings
//	       outFormat.setEncoding(XML_ENCODING);
//	       outFormat.setVersion(XML_VERSION);
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

