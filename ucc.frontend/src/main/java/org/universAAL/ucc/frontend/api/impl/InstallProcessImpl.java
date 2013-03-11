package org.universAAL.ucc.frontend.api.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.universAAL.ucc.controller.install.DeployConfigController;
import org.universAAL.ucc.controller.install.DeployStrategyController;
import org.universAAL.ucc.controller.install.UsrvInfoController;
import org.universAAL.ucc.frontend.api.IWindow;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.UAPP;
import org.universAAL.ucc.model.install.License;
import org.universAAL.ucc.windows.DeployConfigView;
import org.universAAL.ucc.windows.DeployStrategyView;
import org.universAAL.ucc.windows.LicenceWindow;
import org.universAAL.ucc.windows.UccUI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class InstallProcessImpl implements IWindow {

	@Override
	public void getLicenseView(String licensePath) {
		File licenceFile = new File(licensePath+"/config/hwo.usrv.xml");
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		File l = null;
		LicenceWindow lw = null;
		String txt = "";
		String appName = "";
		String slaName = "";
		License license = null;
		ArrayList<License> licenseList = new ArrayList<License>();
		ArrayList<File> list = new ArrayList<File>();
		AALService aal = null;
		try {
			DocumentBuilder builder = fact.newDocumentBuilder();
			Document doc = builder.parse(licenceFile);
			for(int k = 0; k < doc.getElementsByTagName("usrv:srv").getLength(); k++) {
				aal = new AALService();
				for(int ac = 0; ac < doc.getElementsByTagName("usrv:application").getLength(); ac++) {
					UAPP uap = new UAPP();
					Node node = (Node)doc.getElementsByTagName("usrv:application").item(ac);
					NodeList nodeList = node.getChildNodes();
					for(int b = 0; b < node.getChildNodes().getLength(); b++) {
						
						if(nodeList.item(b).getNodeName().equals("usrv:artifactID")) {
							uap.setServiceId(nodeList.item(b).getTextContent());
							System.err.println(uap.getServiceId());
						}
						if(nodeList.item(b).getNodeName().equals("usrv:location")) {
							uap.setUappLocation(nodeList.item(b).getTextContent());
						}
						if(nodeList.item(b).getNodeName().equals("usrv:name")) {
							uap.setName(nodeList.item(b).getTextContent());
							System.err.println(uap.getName());
						}
						
					}
					aal.getUaapList().add(uap);
				}
					aal.setName(doc.getElementsByTagName("usrv:name").item(0).getTextContent());
					aal.setProvider(doc.getElementsByTagName("usrv:serviceProvider").item(0).getTextContent());
					aal.setDescription(doc.getElementsByTagName("usrv:description").item(0).getTextContent());
					aal.setMajor(Integer.parseInt(doc.getElementsByTagName("usrv:major").item(0).getTextContent()));
					aal.setMinor(Integer.parseInt(doc.getElementsByTagName("usrv:minor").item(0).getTextContent()));
					aal.setMicro(Integer.parseInt(doc.getElementsByTagName("usrv:micro").item(0).getTextContent()));
					String h = doc.getElementsByTagName("usrv:tags").item(0).getTextContent();
					for(String t : h.split(",")) {
						aal.getTags().add(t);
					}
				license = new License();
				for(int s = 0; s < doc.getElementsByTagName("usrv:sla").getLength(); s++) {
					Node node = (Node) doc.getElementsByTagName("usrv:sla").item(s);
					NodeList nodeList = node.getChildNodes();
					for(int c = 0; c < nodeList.getLength(); c++) {
						if(nodeList.item(c).getNodeName().equals("usrv:name")) {
							slaName = nodeList.item(c).getTextContent();
							license.setAppName(slaName);
						}
						if(nodeList.item(c).getNodeName().equals("usrv:link")) {
							String link = nodeList.item(c).getTextContent();
							link = link.substring(link.lastIndexOf("/"));
							File file = new File(licensePath+"/licenses"+link);
							license.getSlaList().add(file);
						}
					}
				}

			for(int i = 0; i < doc.getElementsByTagName("usrv:license").getLength(); i++) {
				Node n = (Node) doc.getElementsByTagName("usrv:license").item(i);
				NodeList nlist = n.getChildNodes();
				
				for(int j = 0; j < nlist.getLength(); j++) {
					if(nlist.item(j).getNodeName().equals("usrv:link")) {
						txt = nlist.item(j).getTextContent();
						txt = txt.substring(txt.lastIndexOf("/"));
						l = new File(licensePath+"/licenses"+txt);
						list.add(l);
					}
					
				}
			}
			license.setLicense(list);
			licenseList.add(license);
			aal.setLicenses(license);
			}
			lw = new LicenceWindow(UccUI.getInstance(), licenseList, aal);
			UccUI.getInstance().getMainWindow().addWindow(lw);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void getDeployStratgyView(String name, String serviceId, String uappLocation, UAPP uapp) {
		DeployStrategyView dsv = new DeployStrategyView(name, serviceId, uappLocation);
		new DeployStrategyController(UccUI.getInstance(), dsv, uapp);
		UccUI.getInstance().getMainWindow().addWindow(dsv);
		
	
}
	@Override
	public void getDeployConfigView(UAPP uapp, boolean isLastPart) {
		DeployConfigView dcv = new DeployConfigView(UccUI.getInstance(), uapp.getServiceId(), uapp.getUappLocation());
		UccUI.getInstance().getMainWindow().addWindow(dcv);
		DeployConfigController dcc = new DeployConfigController(UccUI.getInstance(), dcv, isLastPart);
		
	}

}
