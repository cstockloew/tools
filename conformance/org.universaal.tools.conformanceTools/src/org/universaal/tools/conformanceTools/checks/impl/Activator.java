package org.universaal.tools.conformanceTools.checks.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IResource;
import org.universaal.tools.conformanceTools.checks.api.Check;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Activator implements Check {

	private String result = "Test not yet performed.";

	@Override
	public String getCheckName() {
		return "Is an Activator class present?";
	}

	@Override
	public String getCheckDescription() {
		return "This test will verify if selected project has an associated Activator class.";
	}

	@Override
	public String check(IResource resource) throws Exception {

		POM_file test = new POM_file();
		File pomFile = test.getPOMfile(resource);
		if(pomFile != null){

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document results = docBuilder.parse(pomFile);

			NodeList pcks = results.getElementsByTagName("Bundle-Activator");
			for(int i = 0; i < pcks.getLength(); i++){
				Node pck = pcks.item(i);
				if(pck.getNodeType() == Node.ELEMENT_NODE){
					Element pck_ = (Element) pck;
					if(pck_.getFirstChild() != null && pck_.getFirstChild().getNodeValue() != null && !pck_.getFirstChild().getNodeValue().isEmpty()){
						result = "This project has a proper Activator class.";
						return ok;
					}
				}
			}
		}

		result = "This project has not a proper Activator class.";
		return ko;
	}

	@Override
	public String getCheckResultDescription() {
		return result;
	}
}
