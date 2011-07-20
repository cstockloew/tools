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


public class SourcePage extends StructuredTextEditor {
	
	private ProjectModel model;
	private XmlEditor parent;
	private Document doc;
	private boolean isPageModified;
	
	public SourcePage(XmlEditor parent, ProjectModel model, Document doc){
		this.parent = parent;
		this.model = model;
		this.doc = doc;
		this.isPageModified = false;
		
	}
	
	public void updateModelFromXml(){

		try {
			String string = this.getDocumentProvider().getDocument(this.getEditorInput()).get();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(string)));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		NodeList nList;
		Element node;
		String name, developer, date, url, svnurl, desc;
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

		nList = doc.getElementsByTagName("description");
		node = (Element) nList.item(0);
		desc = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("tag");
		for(int i=0; i<nList.getLength(); i++){
			node = (Element) nList.item(i);
			tags.add(node.getFirstChild().getNodeValue());
		}

		if(!name.equals(model.getpName()))
			model.setpName(name);
		if(!developer.equals(model.getpDev()))
			model.setpDev(developer);
		if(!date.equals(model.getpDate()))
			model.setpDate(date);
		if(!url.equals(model.getpUrl()))
			model.setpUrl(url);
		if(!svnurl.equals(model.getpSvnUrl()))
			model.setpSvnUrl(svnurl);
		if(!desc.equals(model.getpDesc()))
			model.setpDesc(desc);

		model.setpTags(tags);

	}
	
	public void updateXmlFromModel(){

		NodeList nList;
		Element node;

		try{
			nList = doc.getElementsByTagName("name");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpName());

			nList = doc.getElementsByTagName("developer");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpDev());

			nList = doc.getElementsByTagName("date");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpDate());

			nList = doc.getElementsByTagName("url");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpUrl());

			nList = doc.getElementsByTagName("svnurl");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpSvnUrl());

			nList = doc.getElementsByTagName("description");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpDesc());

			ArrayList<String> tempTags = model.getpTags();
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
			Transformer transformer;
			transformer = factory2.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			
			transformer.transform(source, result);
			String str = stringWriter.getBuffer().toString();
			this.getDocumentProvider().getDocument(parent.getEditorInput()).set(str);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
