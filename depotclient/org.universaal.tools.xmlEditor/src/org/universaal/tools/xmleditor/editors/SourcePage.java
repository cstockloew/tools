/*
	Copyright 2011 SINTEF, http://www.sintef.no
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.tools.xmleditor.editors;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.universaal.tools.xmleditor.model.ProjectModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The page of the XML-editor that displays the raw XML-code.
 * @author Adrian
 *
 */
public class SourcePage extends StructuredTextEditor {
	
	private ProjectModel model;
	private XmlEditor parent;
	private Document doc;
	
	public SourcePage(XmlEditor parent, ProjectModel model, Document doc){
		this.parent = parent;
		this.model = model;
		this.doc = doc;
		
	}
	
	/**
	 * When the user has made changes to the XML-code, this method will be called
	 * when the user saves or changes page.
	 * It parses the modified XML, and updates the ProjectModel accordingly, so
	 * that when the user changes to the Fields-page, the updated data will be
	 * displayed.
	 */
	public void updateModelFromXml(){

		try {
			String string = this.getDocumentProvider().getDocument(this.getEditorInput()).get();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(string)));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		NodeList nList;
		Element node;
		String name, developer, date, url, svnurl, license, licenseUrl,
		subProjects, desc;
		ArrayList<String> tags = new ArrayList<String>();

		nList = doc.getElementsByTagName("name");
		node = (Element) nList.item(0);
		name =node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("developer");
		node = (Element) nList.item(0);
		developer = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("date");
		node = (Element) nList.item(0);
		date = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("url");
		node = (Element) nList.item(0);
		url = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("svnurl");
		node = (Element) nList.item(0);
		svnurl = node.getFirstChild().getNodeValue();
		
		nList = doc.getElementsByTagName("license");
		node = (Element) nList.item(0);
		license = node.getFirstChild().getNodeValue();
		
		nList = doc.getElementsByTagName("licenseurl");
		node = (Element) nList.item(0);
		licenseUrl = node.getFirstChild().getNodeValue();
		
		nList = doc.getElementsByTagName("subprojects");
		node = (Element) nList.item(0);
		subProjects = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("description");
		node = (Element) nList.item(0);
		desc = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("tag");
		for(int i=0; i<nList.getLength(); i++){
			node = (Element) nList.item(i);
			tags.add(node.getFirstChild().getNodeValue());
		}

		if(!name.equals(model.getName()))
			model.setName(name);
		if(!developer.equals(model.getDev()))
			model.setDev(developer);
		if(!date.equals(model.getDate()))
			model.setDate(date);
		if(!url.equals(model.getUrl()))
			model.setUrl(url);
		if(!svnurl.equals(model.getSvnUrl()))
			model.setSvnUrl(svnurl);
		if(!license.equals(model.getLicense()))
			model.setLicense(license);
		if(!licenseUrl.equals(model.getLicenseUrl()))
			model.setLicenseUrl(licenseUrl);
		if(!desc.equals(model.getDesc()))
			model.setDesc(desc);
	
		model.setContainsSubProjects(subProjects.equalsIgnoreCase("true"));
		model.setTags(tags);

	}
	
	/**
	 * Checks the ProjectObject model, and updates the xml accordingly. This
	 * will be called after a user has made changes in the fields-page, so that
	 * the raw XML will contain the correct data.
	 */
	public void updateXmlFromModel(){

		NodeList nList;
		Element node;

		try{
			nList = doc.getElementsByTagName("name");
			node = (Element) nList.item(0);
			node.setTextContent(model.getName());

			nList = doc.getElementsByTagName("developer");
			node = (Element) nList.item(0);
			node.setTextContent(model.getDev());

			nList = doc.getElementsByTagName("date");
			node = (Element) nList.item(0);
			node.setTextContent(model.getDate());

			nList = doc.getElementsByTagName("url");
			node = (Element) nList.item(0);
			node.setTextContent(model.getUrl());

			nList = doc.getElementsByTagName("svnurl");
			node = (Element) nList.item(0);
			node.setTextContent(model.getSvnUrl());
			
			nList = doc.getElementsByTagName("license");
			node = (Element) nList.item(0);
			node.setTextContent(model.getLicense());
			
			nList = doc.getElementsByTagName("licenseurl");
			node = (Element) nList.item(0);
			node.setTextContent(model.getLicenseUrl());
			
			nList = doc.getElementsByTagName("subprojects");
			node = (Element) nList.item(0);
			node.setTextContent(model.getContainsSubProjects() ? "true" : "false");

			nList = doc.getElementsByTagName("description");
			node = (Element) nList.item(0);
			node.setTextContent(model.getDesc());

			ArrayList<String> tempTags = model.getTags();
			nList = doc.getElementsByTagName("tags");
			Node oldTags = (Element) nList.item(0);
			Node project = node.getParentNode();

			
			doc.normalizeDocument();
			
			Node newTags = doc.createElement("tags");

			for(int i=0; i<tempTags.size(); i++){
				Element el = doc.createElement("tag");
				el.appendChild(doc.createTextNode(tempTags.get(i)));
				newTags.appendChild(el);
			}
			project.replaceChild(newTags, oldTags);
			

		}catch(Exception e){
			e.printStackTrace();
		}

		try {
			Source source = new DOMSource(doc);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory2 = TransformerFactory.newInstance();
			
			//The transformer makes the XML-output nicely formatted.
			Transformer transformer;
			transformer = factory2.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			
			transformer.transform(source, result);
			String str = stringWriter.getBuffer().toString();
			this.getDocumentProvider().getDocument(parent.getEditorInput()).set(str);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}	
	}

}
