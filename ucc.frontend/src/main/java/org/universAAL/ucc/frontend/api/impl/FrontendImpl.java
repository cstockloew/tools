package org.universAAL.ucc.frontend.api.impl;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.universAAL.ucc.model.uapp.AalUapp;
import org.universAAL.ucc.model.uapp.Bundle;
import org.universAAL.ucc.model.uapp.DeploymentUnit;
import org.universAAL.ucc.model.uapp.Feature;
import org.universAAL.ucc.model.uapp.FeaturesRoot;
import org.universAAL.ucc.model.uapp.Part.PartRequirements;
import org.universAAL.ucc.model.uapp.ReqType;
import org.universAAL.ucc.model.usrv.AalUsrv;
import org.universAAL.ucc.model.usrv.AalUsrv.Srv.Licenses;
import org.universAAL.ucc.model.usrv.LicenseType;
import org.universAAL.middleware.deploymanager.uapp.model.Part;
//import org.universAAL.middleware.interfaces.mpa.model.Part;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.ucc.controller.desktop.DesktopController;
import org.universAAL.ucc.controller.install.UsrvInfoController;
import org.universAAL.ucc.frontend.api.IFrontend;
import org.universAAL.ucc.model.AALService;
import org.universAAL.ucc.model.UAPPPart;
import org.universAAL.ucc.model.UAPPReqAtom;
import org.universAAL.ucc.model.install.License;
import org.universAAL.ucc.database.parser.ParserService;
import org.universAAL.ucc.service.api.IServiceManagement;
import org.universAAL.ucc.service.manager.Activator;
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
 *         modified by Shanshan, 13-03-2013
 * 
 */

public class FrontendImpl implements IFrontend {

	private final int BUFFER_SIZE = /* 4096 */153600;

	private static final String FILENAME_SEARCH_TAG = "filename";

	private static final String usrvLocalStore = System.getenv("systemdrive")
			+ "/tempUsrvFiles/";

	private static String uappURI;

	private static String userSession;

	public boolean installService(String sessionkey, String serviceId,
			String serviceLink) {
		// Opens a browser window and loads the ucc site
		// Desktop desk = Desktop.getDesktop();
		// try {
		// desk.browse(new URI("http://127.0.0.1:8080/ucc"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// check for sessionkey
		// if(sessionkey.equals(DesktopController.getSessionKey())) {
		// downloads a usrv-file from the given download-uri
		// TO be unmarked
		String usrvName = "";
		// try {
		//
		// usrvName = downloadUsrvFile(downloadUri,
		// /*"corrected_hwo_usrv.usrv"*/ "HWO_Service.usrv");
		// } catch (IOException e2) {
		// e2.printStackTrace();
		// }
//		System.err.println("In installService [FrontendImpl]");
//		File temp = new File(usrvLocalStore
//				+ /* "corrected_hwo_usrv.usrv" */"HWO_Service.usrv");
//		 if (temp.exists()) {
//		 try {
//		 extractFolder(usrvLocalStore + /* "corrected_hwo_usrv.usrv" */"HWO_Service.usrv",usrvLocalStore);
//		 } catch (ZipException e2) {
//		 // TODO Auto-generated catch block
//		 e2.printStackTrace();
//		 } catch (IOException e2) {
//		 // TODO Auto-generated catch block
//		 e2.printStackTrace();
//		 }

		// Copy uapp files to C:/tempUsrvFiles/hwo_uapp/
//		 uappURI = createUAPPLocation(usrvLocalStore + "bin");

		//
		// // extract available uapp files
//		 File usrv = new File(usrvLocalStore + "hwo_uapp");
//		 File[] uapps = usrv.listFiles();
//		 for (File cur : uapps) {
//		 try {
//		 extractFolder(usrvLocalStore + "hwo_uapp/" + cur.getName(),
//		 usrvLocalStore + "hwo_uapp/");
//		 } catch (ZipException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 } catch (IOException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }
//		 }
		 
		// parse uapp.config.xml
		ArrayList<UAPPPart> apps = null;
		apps = parseUappConfiguration("hwo_uapp/config/hwo.uapp.xml");

		// try {
		// parses the configuration xml from the extracted usrv file
		// and creates the views (LicenseView and so on) to show to the
		// user
		// for further processing
//		 parseConfiguration(
//		 /* "config/hwo.usrv.xml" */"config/HWO Service.xml",apps);
		// } catch (SAXException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } return true;
		// } else {
		// //TODO: SessionKey was not right, what todo?
		// }
//		 }
		return false;
	}

	/**
	 * Downloads usrv file from the given download uri
	 * 
	 * @param downloadUri
	 *            link from where to download the usrv file
	 */
	private String downloadUsrvFile(String downloadUri, String filename)
			throws IOException {
		System.out
				.println("[FrontendImpl.downloadUsrvFile] the usrv file name is: "
						+ filename);
		URL url = new URL(downloadUri);
		URLConnection con = url.openConnection();
		InputStream in = new BufferedInputStream(con.getInputStream());
		FileOutputStream out = new FileOutputStream(
				System.getenv("systemdrive") + "/tempUsrvFiles/" + filename);
		byte[] chunk = new byte[153600];
		int chunkSize;
		while ((chunkSize = in.read(chunk)) > 0) {
			out.write(chunk, 0, chunkSize);
			chunk = new byte[153600];
		}
		out.flush();
		out.close();
		in.close();

		return filename;
	}

	/**
	 * Parses the given configuration xml from an uapp file to get some
	 * information from the uapp file
	 * 
	 */
	private ArrayList<UAPPPart> parseUappConfiguration(String f) {
		File config = new File(usrvLocalStore + f);
		ArrayList<UAPPPart> appsList = new ArrayList<UAPPPart>();
		File l = null;
		String txt = "";
		String slaName = "";
		License license = null;
		ArrayList<License> licenseList = new ArrayList<License>();
		ArrayList<File> list = new ArrayList<File>();
		AALService aal = null;
		// Read uapp config xml
		AalUapp uapp = null;
		ParserService ps = Activator.getParserService();
		uapp = ps.getUapp("C:/tmpConfigFiles/hwo.uapp.xml");
		List<org.universAAL.ucc.model.uapp.Part> parts = uapp
				.getApplicationPart().getPart();
		System.err.println(parts.size());
		for (org.universAAL.ucc.model.uapp.Part p : parts) {
			UAPPPart ua = new UAPPPart();
			Part part = new Part();
			System.err.println(p.getPartId());
			part.setPartId(p.getPartId());
			ua.setPart(part);
			ua.setBundleId(p.getBundleId());
			System.err.println(p.getBundleId());
			ua.setBundleVersion(p.getBundleVersion());
			System.err.println(p.getDeploymentUnit().size());
			
			//Getting DeploymentUnit
			for(DeploymentUnit du : p.getDeploymentUnit()) {
				//Getting ContainerUnits
				if(du.isSetContainerUnit()) {
					//Karaf features
					if(du.getContainerUnit().isSetKaraf()) {
						System.err.println(du.getId());
						System.err.println(du.getContainerUnit().getKaraf().getFeatures().getName());
						for(Serializable so : du.getContainerUnit().getKaraf().getFeatures().getRepositoryOrFeature()) {
							if(so instanceof Feature) {
								Feature feat = (Feature)so;
								for(Serializable dco : feat.getDetailsOrConfigOrConfigfile()) {
									if(dco instanceof Bundle) {
										Bundle b = (Bundle)dco;
										ua.setUappLocation(b.getValue().trim());
										System.err.println("Bundle-Value: "+b.getValue());
									}
								}
							}
						}
						System.err.println("Featuresize: "+du.getContainerUnit().getKaraf().getFeatures().getRepositoryOrFeature().size());
						
						System.err.println("Feauture: "+du.getContainerUnit().getKaraf().getFeatures().getRepositoryOrFeature().get(0).toString());
					}
					//Android app
					if(du.getContainerUnit().isSetAndroid()) { 
						for(String loc : du.getContainerUnit().getAndroid().getLocation()) {
							ua.setUappLocation(loc);
							System.err.println(loc);
						}
					}
					//Equinox Container as runtime
					if(du.getContainerUnit().isSetEquinox()) {
						//TODO: Parsing for Equinox Container
					}
					//Felix Container as runtime
					if(du.getContainerUnit().isSetFelix()) {
						//TODO: Parsing for Felix
					}
					if(du.getContainerUnit().isSetTomcat()) {
						//TODO: Parsing for Tomcat
					}
					if(du.getContainerUnit().isSetOsgiAndroid()) {
						//TODO: Parsing for OSGI Android
					}
				}
				//OS Unit
				if(du.isSetOsUnit()) {
					//TODO: Parse Values for OSUnit
				}
				//PlatformUnit
				if(du.isSetPlatformUnit()) {
					//TODO: Parse Values for PlatformUnit
				}
			}
			
			//Getting UAPPReqAtom for validation
			UAPPReqAtom atom = null;
			PartRequirements pr = p.getPartRequirements();
			for(ReqType rt : pr.getRequirement()) {
				atom = new UAPPReqAtom();
				if(rt.isSetReqAtom()) {
					System.err.println("ReqAtom Name: "+rt.getReqAtom().getReqAtomName());
//					atom.setName(rt.getReqAtom().getReqAtomName());
					System.err.println("ReqAtom Value: "+rt.getReqAtom().getReqAtomValue());
//					atom.setValue(rt.getReqAtom().getReqAtomValue());
					if(rt.getReqAtom().getReqCriteria() != null) {
						System.err.println("ReqAtom Criteria: "+rt.getReqAtom().getReqCriteria().value());
						atom.setCriteria(rt.getReqAtom().getReqCriteria().value());
					}
				}
				if(rt.isSetReqGroup()) {
					for(ReqType rType : rt.getReqGroup().getRequirement()) {
						if(rType.isSetReqAtom()) {
							System.err.println(rType.getReqAtom().getReqAtomName());
							System.err.println(rType.getReqAtom().getReqAtomValue());
							System.err.println(rType.getReqAtom().getReqCriteria());
						}
					}
				}
				ua.addReqAtoms(atom);
			}
			
			ua.setAppId(uapp.getApp().getAppId());
			ua.setDescription(uapp.getApp().getDescription());
			ua.setMultipart(uapp.getApp().isMultipart());
			ua.setName(uapp.getApp().getName());
			ua.setMajor(uapp.getApp().getVersion().getMajor());
			ua.setMicro(uapp.getApp().getVersion().getMicro());
			ua.setMinor(uapp.getApp().getVersion().getMinor());
			appsList.add(ua);
			
			//Only for testing
			
			aal = new AALService();
			for(org.universAAL.ucc.model.uapp.AalUapp.App.Licenses ls : uapp.getApp().getLicenses()) {
				license = new License();
				if(ls.isSetSla()) {
					slaName = ls.getSla().getName();
					System.err.println("SLA-Name: "+slaName);
					license.setAppName(slaName);
					if(ls.getSla().isSetLink()) {
						String link = ls.getSla().getLink();
						System.err.println(link);
						link = link.substring(link.lastIndexOf("/"));
						System.err.println(link);
						File file = new File(usrvLocalStore + "licenses" + link);
						license.getSlaList().add(file);
					}
					
				}
				if(ls.isSetLicense()) {
					for(org.universAAL.ucc.model.uapp.LicenseType lt : ls.getLicense()) {
						
							System.err.println("LicenseType is set!!! "+lt.getLink());
							if(lt.isSetLink()) {
								txt = lt.getLink();
								System.err.println(txt);
								txt = txt.substring(txt.lastIndexOf("/"));
								System.err.println(txt);
								l = new File(usrvLocalStore + "licenses" + txt);
								list.add(l);
							}
						
					}
				}
				
			}
			license.setLicense(list);
			licenseList.add(license);
			aal.setLicenses(license);
			
		}
		parseConfiguration(f, appsList, licenseList, aal);
		// Start of old/hardcoded parsing
		// ArrayList<UAPPPart> appsList = new ArrayList<UAPPPart>();
		// DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		// DocumentBuilder builder = null;
		// try {
		// builder = fact.newDocumentBuilder();
		// } catch (ParserConfigurationException e) {
		// e.printStackTrace();
		// }

		// Document doc = builder.parse(config);

		// for(int i = 0; i <
		// doc.getElementsByTagName("uapp:applicationPart").getLength(); i++) {
		// for (int j = 0; j <
		// doc.getElementsByTagName("uapp:part").getLength(); j++) {
		// UAPPPart ua = new UAPPPart();
		// String partId = doc.getElementsByTagName("uapp:part").item(j)
		// .getAttributes().getNamedItem("partId").getNodeValue();
		// System.out.println("[FrontendImpl] PartId: " + partId);
		// Part p = new Part();
		// // TODO: Nicole check! Currently p/Part has just partId, all other
		// // part info is null/unset
		// p.setPartId(partId);
		//
		// ua.setPart(p);
		// NodeList childs = doc.getElementsByTagName("uapp:part").item(j)
		// .getChildNodes();
		// for (int t = 0; t < childs.getLength(); t++) {
		// if (childs.item(t).getNodeName().equals("uapp:bundleId")) {
		// String bundleId = childs.item(t).getTextContent();
		// System.err.println("Bundle-ID: " + bundleId);
		// ua.setBundleId(bundleId);
		// }
		// if (childs.item(t).getNodeName().equals("uapp:bundleVersion")) {
		// String bundleVersion = childs.item(t).getTextContent();
		// System.err.println("Bundle-Version: " + bundleVersion);
		// ua.setBundleVersion(bundleVersion);
		// }
		//
		// if (childs.item(t).getNodeName().equals("uapp:deploymentUnit")) {
		// System.err
		// .println("[FrontendImpl.parseUappConfiguration] IN DEPLOYMENT UNIT");
		// Node dun = childs.item(t);
		// for (int du = 0; du < dun.getChildNodes().getLength(); du++) {
		// if (dun.getChildNodes().item(du).getNodeName()
		// .equals("uapp:containerUnit")) {
		// Node cun = dun.getChildNodes().item(du);
		// NodeList nl = cun.getChildNodes();
		// System.err.println("IN CONTAINER UNIT!");
		// //
		// for (int n = 0; n < nl.getLength(); n++) {
		// if (nl.item(n).getNodeName()
		// .equals("uapp:karaf")) {
		// System.err.println("IN KARAF");
		// NodeList kn = nl.item(n).getChildNodes();
		// for (int k = 0; k < kn.getLength(); k++) {
		// if (kn.item(k).getNodeName()
		// .equals("features")) {
		// System.err.println("IN FEATURES");
		// NodeList fn = kn.item(k)
		// .getChildNodes();
		// for (int fi = 0; fi < fn
		// .getLength(); fi++) {
		// if (fn.item(fi).getNodeName()
		// .equals("feature")) {
		// System.err
		// .println("IN FEATURE");
		// Node nn = fn.item(fi);
		// NodeList nnList = nn
		// .getChildNodes();
		// for (int cam = 0; cam < nnList
		// .getLength(); cam++) {
		// if (nnList
		// .item(cam)
		// .getNodeName()
		// .equals("bundle")) {
		// System.err
		// .println("IN BUNDLE");
		// String location = nnList
		// .item(cam)
		// .getTextContent();
		// ua.setUappLocation(location);
		// System.err
		// .println("LOCATION is: "
		// + location);
		// }
		// }
		// }
		// }
		// }
		//
		// }
		// } else {
		// NodeList childList = nl.item(n)
		// .getChildNodes();
		// for (int cl = 0; cl < childList.getLength(); cl++) {
		// if (childList.item(cl).getNodeName()
		// .equals("uapp:location")) {
		// String location = nl.item(n)
		// .getTextContent();
		// ua.setUappLocation(location);
		// System.err.println("Location is: "
		// + location);
		// }
		// }
		// }
		//
		// }
		//
		// }
		// }
		// }
		//
		// // for checking part requirements
		// if (childs.item(t).getNodeName()
		// .equals("uapp:partRequirements")) {
		// System.err
		// .println("[FrontendImpl.parseUappConfiguration] In part requirements");
		// Node dun = childs.item(t);
		// for (int du = 0; du < dun.getChildNodes().getLength(); du++) {
		// if (dun.getChildNodes().item(du).getNodeName()
		// .equals("uapp:requirement")) {
		// Node cun = dun.getChildNodes().item(du);
		// NodeList nl = cun.getChildNodes();
		//
		// for (int n = 0; n < nl.getLength(); n++) {
		// if (nl.item(n).getNodeName()
		// .equals("uapp:reqAtom")) {
		// System.err.println("IN reqAtom");
		// Node ratom = nl.item(n);
		// System.err.println("NL: " + nl.getLength());
		// NodeList rl = ratom.getChildNodes();
		// System.err.println("RL: " + rl.getLength());
		// UAPPReqAtom atom = new UAPPReqAtom();
		// // Putting values of a reqatom
		// for (int k = 0; k < rl.getLength(); k++) {
		// // Here was UAPPReqAtom
		// if (rl.item(k).getNodeName()
		// .equals("uapp:reqAtomName")) {
		// String value = rl.item(k)
		// .getTextContent();
		// System.out
		// .println("[FrontendImpl.parsePartConfiguration] get a reqAtomName: "
		// + value);
		// atom.setName(value);
		// }
		// if (rl.item(k).getNodeName()
		// .equals("uapp:reqAtomValue")) {
		// String value = rl.item(k)
		// .getTextContent();
		// System.out
		// .println("[FrontendImpl.parsePartConfiguration] get a reqAtomValue: "
		// + value);
		// List<String> vals = new ArrayList<String>();
		// vals.add(value);
		// atom.setValue(vals);
		// }
		//
		// if (rl.item(k).getNodeName()
		// .equals("uapp:reqCriteria")) {
		// String value = rl.item(k)
		// .getTextContent();
		// System.out
		// .println("[FrontendImpl.parsePartConfiguration] get a reqCriteria: "
		// + value);
		// atom.setCriteria(value);
		//
		// }
		//
		// }
		// System.err.println("Criteria: "
		// + atom.getCriteria() + " Name: "
		// + atom.getName() + " Value: "
		// + atom.getValue());
		// ua.addReqAtoms(atom);
		// }
		// }
		// }
		// }
		// }
		//
		// for (int k = 0; k < doc.getElementsByTagName("uapp:app")
		// .getLength(); k++) {
		// String name = doc.getElementsByTagName("uapp:name").item(k)
		// .getTextContent();
		// ua.setName(name);
		// String desc = doc.getElementsByTagName("uapp:description")
		// .item(k).getTextContent();
		// ua.setDescription(desc);
		// String appId = doc.getElementsByTagName("uapp:appId")
		// .item(k).getTextContent();
		// ua.setAppId(appId);
		// for (int m = 0; m < doc
		// .getElementsByTagName("uapp:version").getLength(); m++) {
		// String major = doc.getElementsByTagName("uapp:major")
		// .item(m).getTextContent();
		// ua.setMajor(Integer.valueOf(major));
		// String micro = doc.getElementsByTagName("uapp:micro")
		// .item(m).getTextContent();
		// ua.setMicro(Integer.valueOf(micro));
		// String minor = doc.getElementsByTagName("uapp:minor")
		// .item(m).getTextContent();
		// ua.setMinor(Integer.valueOf(minor));
		// }
		// boolean multi = Boolean.valueOf(doc
		// .getElementsByTagName("uapp:multipart").item(k)
		// .getTextContent());
		// ua.setMultipart(multi);
		//
		// }
		// }
		// appsList.add(ua);
		//
		// }
		// }
		// System.err.println("parseUAPPconfiguration");
		return appsList;

	}

	/**
	 * Parses the given configuration xml from the usrv file to get some
	 * information about the usrv.
	 * 
	 * @return AALService with some information about the usrv file
	 * @throws SAXException
	 * @throws IOException
	 */
	private AALService parseConfiguration(String f, ArrayList<UAPPPart> apps, ArrayList<License>licenseList, AALService aal) {
		File licenceFile = new File(usrvLocalStore + f);
		//Parsing usrv.xml
//		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		File l = null;
		String txt = "";
		String slaName = "";
		License license = null;
//		ArrayList<License> licenseList = new ArrayList<License>();
		ArrayList<File> list = new ArrayList<File>();
//		AALService aal = null;
		ParserService ps = Activator.getParserService();
		AalUsrv usrv = ps.getUsrv("C:/tmpConfigFiles/HWO Service.xml");
		aal = new AALService();
		for(UAPPPart ua : apps) {
			System.err.println(ua.getAppId());
			aal.getUaapList().add(ua);
		}
		if(usrv.isSetSrv()) {
			if(usrv.getSrv().isSetServiceId()) {
				System.err.println("Service-ID: "+usrv.getSrv().getServiceId());
				aal.setServiceId(usrv.getSrv().getServiceId());
			}
			if(usrv.getSrv().isSetName()) {
				aal.setName(usrv.getSrv().getName());
				System.err.println("Service-Name: "+usrv.getSrv().getName());
			}
			if(usrv.getSrv().isSetServiceProvider()) {
				aal.setProvider(usrv.getSrv().getServiceProvider().getOrganizationName());
				System.err.println("ServiceProvider: "+aal.getProvider());
			}
			if(usrv.getSrv().isSetDescription()) {
				aal.setDescription(usrv.getSrv().getDescription());
				System.err.println("Description: "+aal.getDescription());
			}
			if(usrv.getSrv().isSetVersion()) {
				aal.setMajor(usrv.getSrv().getVersion().getMajor());
				aal.setMinor(usrv.getSrv().getVersion().getMinor());
				aal.setMicro(usrv.getSrv().getVersion().getMicro());
			}
			if(usrv.getSrv().isSetTags()) {
				aal.getTags().add(usrv.getSrv().getTags());
				System.err.println("Tags: "+aal.getTags());
			}
//		if(usrv.getSrv().isSetLicenses()) {
//			System.err.println("Size of licenses: "+usrv.getSrv().getLicenses().size());
//			for(Licenses ls : usrv.getSrv().getLicenses()) {
//				license = new License();
//	
//				if(ls.isSetSla()) {
//					slaName = ls.getSla().getName();
//					System.err.println("SLA-Name: "+slaName);
//					license.setAppName(slaName);
//					if(ls.getSla().isSetLink()) {
//						String link = ls.getSla().getLink();
//						System.err.println(link);
//						link = link.substring(link.lastIndexOf("/"));
//						System.err.println(link);
//						File file = new File(usrvLocalStore + "licenses" + link);
//						license.getSlaList().add(file);
//					}
//				} 
//				else 
//				if(ls.isSetLicense()) {
//					System.err.println("In Licenses "+ls.getLicense().size());
//					
//					for(LicenseType lt : ls.getLicense()) {
//						System.err.println("LicenseType is set!!! "+lt.getLink());
//						if(lt.isSetLink()) {
//							txt = lt.getLink();
//							System.err.println(txt);
//							txt = txt.substring(txt.lastIndexOf("/"));
//							System.err.println(txt);
//							l = new File(usrvLocalStore + "licenses" + txt);
//							list.add(l);
//						}
//					}
//				}
//				
//			}
//			license.setLicense(list);
//			licenseList.add(license);
//			aal.setLicenses(license);
//		}
		
		
//		DocumentBuilder builder = null;
//		try {
//			builder = fact.newDocumentBuilder();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//		Document doc = builder.parse(licenceFile);
//		for (int k = 0; k < doc.getElementsByTagName("usrv:srv").getLength(); k++) {
//			aal = new AALService();
//
//			for (UAPPPart ua : apps) {
//				aal.getUaapList().add(ua);
//			}
//			aal.setServiceId(doc.getElementsByTagName("usrv:serviceId").item(0)
//					.getTextContent());
//			aal.setName(doc.getElementsByTagName("usrv:name").item(0)
//					.getTextContent());
//			aal.setProvider(doc.getElementsByTagName("usrv:serviceProvider")
//					.item(0).getTextContent());
//			aal.setDescription(doc.getElementsByTagName("usrv:description")
//					.item(0).getTextContent());
//			aal.setMajor(Integer.parseInt(doc
//					.getElementsByTagName("usrv:major").item(0)
//					.getTextContent()));
//			aal.setMinor(Integer.parseInt(doc
//					.getElementsByTagName("usrv:minor").item(0)
//					.getTextContent()));
//			aal.setMicro(Integer.parseInt(doc
//					.getElementsByTagName("usrv:micro").item(0)
//					.getTextContent()));
//			String h = doc.getElementsByTagName("usrv:tags").item(0)
//					.getTextContent();
//			for (String t : h.split(",")) {
//				aal.getTags().add(t);
//			}
//			license = new License();
//			for (int s = 0; s < doc.getElementsByTagName("usrv:sla")
//					.getLength(); s++) {
//				Node node = (Node) doc.getElementsByTagName("usrv:sla").item(s);
//				NodeList nodeList = node.getChildNodes();
//				for (int c = 0; c < nodeList.getLength(); c++) {
//					if (nodeList.item(c).getNodeName().equals("usrv:name")) {
//						slaName = nodeList.item(c).getTextContent();
//						license.setAppName(slaName);
//					}
//					if (nodeList.item(c).getNodeName().equals("usrv:link")) {
//						String link = nodeList.item(c).getTextContent();
//						link = link.substring(link.lastIndexOf("/"));
//						File file = new File(usrvLocalStore + "licenses" + link);
//						license.getSlaList().add(file);
//					}
//				}
//			}
//
//			for (int i = 0; i < doc.getElementsByTagName("usrv:license")
//					.getLength(); i++) {
//				Node n = (Node) doc.getElementsByTagName("usrv:license")
//						.item(i);
//				NodeList nlist = n.getChildNodes();
//
//				for (int j = 0; j < nlist.getLength(); j++) {
//					if (nlist.item(j).getNodeName().equals("usrv:link")) {
//						txt = nlist.item(j).getTextContent();
//						txt = txt.substring(txt.lastIndexOf("/"));
//						l = new File(usrvLocalStore + "licenses" + txt);
//						list.add(l);
//					}
//
//				}
//			}
//
//			license.setLicense(list);
//			licenseList.add(license);
//			aal.setLicenses(license);
//
//		}
		System.err.println("SET LicenseWindow");
		LicenceWindow lw = null;
		try {
			lw = new LicenceWindow(UccUI.getInstance(), licenseList, aal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new UsrvInfoController(aal, lw, UccUI.getInstance());
		}
		return aal;
	}

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	// private void extractFile(ZipInputStream zipIn, String filePath)
	// throws IOException {
	// File f = new File(filePath);
	//
	// BufferedOutputStream bos = new BufferedOutputStream(
	// new FileOutputStream(filePath));
	// byte[] bytesIn = new byte[BUFFER_SIZE];
	// int read = 0;
	// while ((read = zipIn.read(bytesIn)) != -1) {
	// bos.write(bytesIn, 0, read);
	// }
	// bos.close();
	//
	// }

	private String createUAPPLocation(String path) {
		File pa = new File(path);
		File[] dirs = pa.listFiles();
		File rootFile = new File(usrvLocalStore + "hwo_uapp");
		rootFile.mkdir();
		for (int i = 0; i < dirs.length; i++) {
			File f = new File(usrvLocalStore + "hwo_uapp/" + dirs[i].getName());
			System.err.println("Dir-Name: " + dirs[i].getName());
			if (dirs[i].isDirectory()) {
				f.mkdir();
				// File[] child = dirs[i].listFiles();
				// for(int j = 0; j < child.length; j++) {
				// System.err.println("Directory Path + Child Path: "+usrvLocalStore+"hwo_uapp/"+dirs[i].getName()+"/"+child[j].getName());
				// File cf = new
				// File(usrvLocalStore+"hwo_uapp/"+dirs[i].getName()+"/"+child[j].getName());
				// child[j].renameTo(cf);
				// }
			}
			// if(!f.getName().substring(f.getName().indexOf(".")+1).equals("uapp"))
			// {
			dirs[i].renameTo(f);
			System.err.println(f.getAbsolutePath());
			// }
		}
		return usrvLocalStore + "hwo_uapp";
	}

	/**
	 * Uninstalls the a installed AAL service.
	 */
	public void uninstallService(String sessionKey, String serviceId) {
		// get the list of uapps installed for this serviceId
		// TODO: List<String appId> getInstalledApps(String serviceId)
		// for each uapp, call ucc.controller.requestToUninstall(serviceId,
		// appId)
		// update the service registration
		IServiceManagement sm = Activator.getMgmt();
		List<String> uappList = sm.getInstalledApps(serviceId);
		for (String del : uappList) {
			// Later uncomment this
			// InstallationResults result =
			// Activator.getDeinstaller().requestToUninstall(serviceId, del);
			// if(result.equals(InstallationResults.SUCCESS)) {
			// Activator.getReg().unregisterService(serviceId);
			// }
		}

	}

	public void update(String sessionKey, String serviceId, String serviceLink) {
		uninstallService(sessionKey, serviceId);
		installService(sessionKey, serviceId, serviceLink);
	}

	public String getInstalledServices(String sessionKey) {
		String services = Activator.getMgmt().getInstalledServices();
		System.out
				.println("[FrontendImpl.getInstalledServices] the services installed: "
						+ services);
		return services;
	}

	public String getInstalledUnitsForService(String sessionKey,
			String serviceId) {
		String units = Activator.getMgmt().getInstalledUnitsForService(
				serviceId);
		System.out
				.println("[FrontendImpl.getInstalledUnitsForServices] the units installed: "
						+ units);
		return units;
	}

	public static String getUappURI() {
		return uappURI;
	}

	public static void setUappURI(String uappURI) {
		FrontendImpl.uappURI = uappURI;
	}

	static public void extractFolder(String zipFile, String destdir)
			throws ZipException, IOException {
		System.out.println("[Installer.extractFolder] the zip file is: "
				+ zipFile + " and dest dir: " + destdir);
		int BUFFER = 2048;
		File file = new File(zipFile);

		ZipFile zip = new ZipFile(file);
		String newPath = destdir;

		new File(newPath).mkdir();
		Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();

		// Process each entry
		while (zipFileEntries.hasMoreElements()) {
			// grab a zip file entry
			ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
			String currentEntry = entry.getName();
			File destFile = new File(newPath, currentEntry);
			// destFile = new File(newPath, destFile.getName());
			File destinationParent = destFile.getParentFile();

			// create the parent directory structure if needed
			destinationParent.mkdirs();

			if (!entry.isDirectory()) {
				BufferedInputStream is = new BufferedInputStream(
						zip.getInputStream(entry));
				int currentByte;
				// establish buffer for writing file
				byte data[] = new byte[BUFFER];

				// write the current file to disk
				FileOutputStream fos = new FileOutputStream(destFile);
				BufferedOutputStream dest = new BufferedOutputStream(fos,
						BUFFER);

				// read and write until last byte is encountered
				while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, currentByte);
				}
				dest.flush();
				dest.close();
				is.close();
			}

		}
	}

	/**
	 * Returns a generated Sessionkey for uStore
	 */
	public String getSessionKey(String username, String password) {
		SecureRandom sr = new SecureRandom();
		userSession = new BigInteger(130, sr).toString(32);
		return userSession;
	}

	public static String getUserSession() {
		return userSession;
	}

}
