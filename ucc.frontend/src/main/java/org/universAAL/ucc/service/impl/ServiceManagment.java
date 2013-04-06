package org.universAAL.ucc.service.impl;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

import org.universAAL.ucc.service.api.IServiceManagement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ServiceManagment implements IServiceManagement {

	/**
	 * return a list of installed services in the following format: <services>
	 * <service>serviceId</service> <service>serviceId</service> ... </services>
	 */
	public String getInstalledServices() {
		if (new File(Model.SERVICEFILENAME).exists()) {
			String services = "<services>";
			// List<String> list = new ArrayList<String>();
			Document doc = Model.getSrvDocument();
			NodeList nodeList = doc.getElementsByTagName("service");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element element = (Element) nodeList.item(i);
				// list.add(element.getAttribute("serviceId"));
				services = services + "<serviceId>"
						+ element.getAttribute("serviceId") + "</serviceId>";
			}

			// return list;
			services += "</services>";
			return services;

		} else {
			// return new ArrayList<String>();
			return "<services></services>";
		}
	}

	/**
	 * get installed units for a service in the following format: <serviceUnits>
	 * <unit><id>bundleId</id><version>bundleVersion</version></unit>
	 * <unit><id>bundleId</id><version>bundleVersion</version></unit> ...
	 * </serviceUnits>
	 */
	public String getInstalledUnitsForService(String serviceId) {
		if (new File(Model.SERVICEFILENAME).exists()) {
			String services = "<serviceUnits>";
			Document doc = Model.getSrvDocument();
			Element serviceEl = getService(serviceId, doc);
			NodeList nodeList = serviceEl.getElementsByTagName("bundle");
			System.out
					.println("[ServiceManagement.getInstalledUnitsForService] the number of nodes for bundle: "
							+ nodeList.getLength());
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element element = (Element) nodeList.item(i);
				services = services + "<unit><id>" + element.getAttribute("id")
						+ "</id><version>" + element.getAttribute("version")
						+ "</version></unit>";
			}
			services += "</serviceUnits>";
			return services;

		} else {
			return "<serviceUnits></serviceUnits>";
		}
	}

	public static Element getService(String serviceId, Document doc) {
		NodeList nodeList = doc.getDocumentElement().getChildNodes();
		System.out.println("[nodeList] the number of child nodes: "
				+ nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element el = (Element) nodeList.item(i);
			System.out
					.println("[ServiceManagent.getService] has a node with serviceId: "
							+ el.getAttribute("serviceId"));
			if (el.getAttribute("serviceId").equals(serviceId)) {
				System.out
						.println("[ServiceManagement.getService] there is a service with id: "
								+ serviceId);
				return el;
			}
		}
		System.out
				.println("[ServiceManagement.getService] there is no service with id: "
						+ serviceId);
		return null;
	}

	/**
	 * get the list of appIds that are installed for a service
	 * 
	 * @param serviceId
	 * @return
	 */
	public List<String> getInstalledApps(String serviceId) {
		if (new File(Model.SERVICEFILENAME).exists()) {
			List<String> list = new ArrayList<String>();
			Document doc = Model.getSrvDocument();
			Element serviceEl = getService(serviceId, doc);
			NodeList nodeList = serviceEl.getElementsByTagName("application");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element element = (Element) nodeList.item(i);
				list.add(element.getAttribute("appId"));
			}

			return list;
		} else {
			return new ArrayList<String>();
		}
	}

}
