package org.universaal.tools.uStoreClientapplication.actions;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.StringReader;

public class ApplicationCategoryParser {

	private List<ApplicationCategory> categoryList = new ArrayList<ApplicationCategory>();
	private String input;

	public ApplicationCategoryParser(String input) {
		super();
		this.input = input;
	}

	public void createCategoryList() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = loadXMLFromString(input);
			NodeList nList = doc.getElementsByTagName("category");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					ApplicationCategory cat = new ApplicationCategory();
					cat.setCategoryName(getTagValue("name", eElement));
					cat.setCategoryNumber(getTagValue("id", eElement));
					categoryList.add(cat);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	private Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	public List<ApplicationCategory> getCategoryList() {
		return categoryList;
	}
	
	
}
