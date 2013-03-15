package org.universAAL.ucc.frontend.api.impl;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.universAAL.ucc.controller.install.UsrvInfoController;
import org.universAAL.ucc.frontend.api.IFrontend;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.UAPP;
import org.universAAL.ucc.model.install.License;
import org.universAAL.ucc.windows.LicenceWindow;
import org.universAAL.ucc.windows.UccUI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Implements the install and de-install processes. Is interface for the
 * DeployManagerService to trigger the different processes like installation and
 * de-installation.
 * 
 * @author merkle
 * 
 * modified by Shanshan, 13-03-2013
 * 
 */

public class FrontendImpl implements IFrontend {

	private final int BUFFER_SIZE = 4096;
	
	private static final String FILENAME_SEARCH_TAG="filename";


	@Override
	public void installService(String sessionkey, String downloadUri) {
		// Opens a browser window and loads the ucc site
		Desktop desk = Desktop.getDesktop();
		try {
			desk.browse(new URI("http://127.0.0.1:8080/ucc"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		//TODO: check for sessionkey
		
		// downloads a usrv-file from the given download-uri
		System.out.println("[FrontendImpl.installService] start download from " + downloadUri);
		// TO be unmarked
		//String usrvName = downloadUsrvFile(downloadUri);
		// Just for testing
		String usrvName = "corrected_hwo_usrv.usrv";
		try {
			// extracts the downloaded usrv file
			extractUsrvFile(usrvName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		try {
			// parses the configuration xml from the extracted usrv file
			// and creates the views (LicenseView and so on) to show to the user for further processing
			parseConfiguration();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/**
	 * Downloads usrv file from the given download uri
	 * 
	 * @param downloadUri
	 *            link from where to download the usrv file
	 */
	private String downloadUsrvFile(String downloadUri) {
		String usrvName = parseFileName(downloadUri);
		System.out.println("[FrontendImpl.downloadUsrvFile] the usrv file name is: " + usrvName);
		URL url = null;
		try {
			url = new URL(downloadUri);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			BufferedInputStream in = new BufferedInputStream(url.openStream());
			FileOutputStream out = new FileOutputStream(
					System.getenv("systemdrive") + "tempUsrvFiles/" + usrvName
							+ ".usrv");
			BufferedOutputStream bOut = new BufferedOutputStream(out, 1024);
			byte[] data = new byte[1024];
			while (in.read(data, 0, 1024) >= 0) {
				bOut.write(data);
			}
			in.close();
			bOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usrvName;
	}

	private String parseFileName(String url){
		String result="package.usrv";
		String[] values=url.split("&");
		for(int i=0;i<values.length;i++){
			if(values[i].startsWith(FILENAME_SEARCH_TAG))
				result=values[i].substring(FILENAME_SEARCH_TAG.length()+1);
		}
		return result;
	}
	
	/**
	 * Extracts the downloaded usrv file.
	 * 
	 * @param usrvName
	 *            name of the usrv file
	 * @throws IOException
	 */
	public void extractUsrvFile(String usrvName) throws IOException {
		File destDir = new File(System.getenv("systemdrive")
				+ "/tempUsrvFiles/" + usrvName);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipInputStream zipIn = null;
		try {
			zipIn = new ZipInputStream(
					new FileInputStream(System.getenv("systemdrive")
							+ "/tempUsrvFiles/" + usrvName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = System.getenv("systemdrive") + "/tempUsrvFiles/"
					+ entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();

	}

	/**
	 * Parses the given configuration xml from the usrv file to get some information about the usrv.
	 * @return AALService with some information about the usrv file
	 * @throws SAXException
	 * @throws IOException
	 */
	private void parseConfiguration() throws SAXException, IOException {
		File licenceFile = new File(System.getenv("systemdrive")
				+ "/tempUsrvFiles/config/hwo.usrv.xml");
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		File l = null;
		String txt = "";
		String slaName = "";
		License license = null;
		ArrayList<License> licenseList = new ArrayList<License>();
		ArrayList<File> list = new ArrayList<File>();
		AALService aal = null;

		DocumentBuilder builder = null;
		try {
			builder = fact.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = builder.parse(licenceFile);
		for (int k = 0; k < doc.getElementsByTagName("usrv:srv").getLength(); k++) {
			aal = new AALService();
			for (int ac = 0; ac < doc.getElementsByTagName("usrv:application")
					.getLength(); ac++) {
				UAPP uap = new UAPP();
				Node node = (Node) doc.getElementsByTagName("usrv:application")
						.item(ac);
				NodeList nodeList = node.getChildNodes();
				for (int b = 0; b < node.getChildNodes().getLength(); b++) {

					if (nodeList.item(b).getNodeName()
							.equals("usrv:artifactID")) {
						uap.setServiceId(nodeList.item(b).getTextContent());
						System.err.println(uap.getServiceId());
					}
					if (nodeList.item(b).getNodeName().equals("usrv:location")) {
						uap.setUappLocation(nodeList.item(b).getTextContent());
					}
					if (nodeList.item(b).getNodeName().equals("usrv:name")) {
						uap.setName(nodeList.item(b).getTextContent());
						System.err.println(uap.getName());
					}

				}
				aal.getUaapList().add(uap);
			}
			aal.setName(doc.getElementsByTagName("usrv:name").item(0)
					.getTextContent());
			aal.setProvider(doc.getElementsByTagName("usrv:serviceProvider")
					.item(0).getTextContent());
			aal.setDescription(doc.getElementsByTagName("usrv:description")
					.item(0).getTextContent());
			aal.setMajor(Integer.parseInt(doc
					.getElementsByTagName("usrv:major").item(0)
					.getTextContent()));
			aal.setMinor(Integer.parseInt(doc
					.getElementsByTagName("usrv:minor").item(0)
					.getTextContent()));
			aal.setMicro(Integer.parseInt(doc
					.getElementsByTagName("usrv:micro").item(0)
					.getTextContent()));
			String h = doc.getElementsByTagName("usrv:tags").item(0)
					.getTextContent();
			for (String t : h.split(",")) {
				aal.getTags().add(t);
			}
			license = new License();
			for (int s = 0; s < doc.getElementsByTagName("usrv:sla")
					.getLength(); s++) {
				Node node = (Node) doc.getElementsByTagName("usrv:sla").item(s);
				NodeList nodeList = node.getChildNodes();
				for (int c = 0; c < nodeList.getLength(); c++) {
					if (nodeList.item(c).getNodeName().equals("usrv:name")) {
						slaName = nodeList.item(c).getTextContent();
						license.setAppName(slaName);
					}
					if (nodeList.item(c).getNodeName().equals("usrv:link")) {
						String link = nodeList.item(c).getTextContent();
						link = link.substring(link.lastIndexOf("/"));
						File file = new File(System.getenv("systemdrive")
								+ "/tempUsrvFiles/licenses" + link);
						license.getSlaList().add(file);
					}
				}
			}

			for (int i = 0; i < doc.getElementsByTagName("usrv:license")
					.getLength(); i++) {
				Node n = (Node) doc.getElementsByTagName("usrv:license")
						.item(i);
				NodeList nlist = n.getChildNodes();

				for (int j = 0; j < nlist.getLength(); j++) {
					if (nlist.item(j).getNodeName().equals("usrv:link")) {
						txt = nlist.item(j).getTextContent();
						txt = txt.substring(txt.lastIndexOf("/"));
						l = new File(System.getenv("systemdrive")
								+ "/tempUsrvFiles/licenses" + txt);
						list.add(l);
					}

				}
			}

			license.setLicense(list);
			licenseList.add(license);
			aal.setLicenses(license);

		}
		LicenceWindow lw = new LicenceWindow(UccUI.getInstance(), licenseList, aal);
		new UsrvInfoController(aal, lw, UccUI.getInstance());
	}

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath)
			throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}


	/**
	 * Uninstalls the a installed AAL service.
	 */
	@Override
	public void uninstallService(String sessionkey, String serviceId) {
		// get the list of uapps installed for this serviceId
		// TODO: List<String appId> getInstalledApps(String serviceId)
		// for each uapp, call ucc.controller.requestToUninstall(serviceId, appId)
		// update the service registration
		
	}

	@Override
	public void update(String sessionKey, String usrvfile) {
		// parse usrvFile and get serviceId
		
		String serviceId = "";
		uninstallService(sessionKey, serviceId);
		installService(sessionKey, usrvfile);
	}

	@Override
	public String getInstalledServices(String sessionKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInstalledUnitsForService(String sessionKey,
			String serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

}
