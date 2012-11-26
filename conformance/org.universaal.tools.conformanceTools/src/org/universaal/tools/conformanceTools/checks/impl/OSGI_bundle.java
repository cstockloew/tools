package org.universaal.tools.conformanceTools.checks.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.universaal.tools.conformanceTools.checks.api.Check;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OSGI_bundle implements Check{

	private String result = "Test not yet performed.";

	@Override
	public String getCheckName() {
		return "Is it an OSGI bundle?";
	}

	@Override
	public String getCheckDescription() {
		return "This test will inspect POM file searching for bundle information.";
	}

	@Override
	public String check(IResource resource) throws Exception {

		POM_file hasPom = new POM_file();
		File pomFile = hasPom.getPOMfile(resource);
		if(pomFile != null){

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document results = docBuilder.parse(pomFile);

			NodeList pcks = results.getElementsByTagName("packaging");
			for(int i = 0; i < pcks.getLength(); i++){
				Node pck = pcks.item(i);
				if(pck.getNodeType() == Node.ELEMENT_NODE){
					Element pck_ = (Element) pck;
					if(pck_.getFirstChild() != null && pck_.getFirstChild().getNodeValue().equalsIgnoreCase("bundle")){
						result = "This project is an OSGI bundle.";
						return ok;
					}
				}
			}
		}

		result = "This project is not an OSGI bundle.";
		return ko;
	}

	@Override
	public String getCheckResultDescription() {
		return result;
	}

	private String getProjectPath(IResource resource) throws CoreException{

		return ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+((IProject)resource).getDescription().getName();
	}
}