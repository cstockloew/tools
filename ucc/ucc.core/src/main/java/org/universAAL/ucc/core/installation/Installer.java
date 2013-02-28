package org.universAAL.ucc.core.installation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.interfaces.PeerRole;
import org.universAAL.middleware.managers.api.DeployManager;
import org.universAAL.middleware.managers.api.AALSpaceManager;
import org.universAAL.middleware.managers.api.InstallationResults;
import org.universAAL.middleware.managers.api.UAPPPackage;
import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.core.Activator;



/**
 * @author Alex
 * @version 1.0
 * @created 11-Jul-2011 15:57:26
 * 
 * modified 19-09-2012 by Shanshan
 * 
 */
public class Installer extends ApplicationManager implements IInstaller {

	private BundleContext context;
	private ArrayList<Bundle> installedBundles=new ArrayList<Bundle>();
	private boolean mpa = false; // flag to indicate if the application is MPA
	private boolean initialized = false;
	private DeployManager deployManager;
	private AALSpaceManager aalSpaceManager;
	private static String MPA_EXTENSION=".uapp";
	
	public Installer(BundleContext con) {
		context=con;
	}

	public Installer(String path) throws Exception {
		//installApplication(path);
		installService(path);
	}

	public void finalize() throws Throwable {
		System.out.println();
	}

	/**
	 * extract the uapp file
	 * @param path: the path for the uapp file 
	 * @param serviceId: the serviceId that the uapp file belongs to
	 * return the path for the extract .uapp file (root)
	 * @throws Exception 
	 */
	public String installApplication(String path) throws Exception {
		//Activator.getModel().getApplicationRegistration().writeToConfigFile("test");
		System.out.println("[Installer.installApplication] the path for install is: " + path);
		String exdir=extractBundles(path);
		if(exdir==null)throw new Exception("[Installer.installApplication] Error extracting .uapp Package");
		// convert "/" to "\"
		exdir = exdir.replaceAll("/", "\\");
		
		checkApplicationForInstall(exdir);
		return exdir;
		
	}

	/**
	 * Is bundle valid? All need files available? Dependencies to check and all
	 * right? Check if concrete instances available (but how)? 
	 * 
	 * Does the .uapp file exist?
	 * Does the license file exist? (which one to check/how?)
	 * 
	 * @param Path
	 * 
	 * @throws Exception 
	 */
	private void checkApplicationForInstall(String folder) throws Exception {
		String uappfile = null;
		boolean aslok=false;
		boolean uappok=false;
		System.out.println("[Installer.checkApplicationForInstall] the uapp file folder: " + folder); 
		File confolder = new File(folder+"\\config");
		String[] content = confolder.list();
		for(int i=0;i<content.length;i++){
			System.out.println("[Installer.checkApplicationForInstall] get one file under /config/:" + content[i]);
			if(content[i].contains(".uapp")) {
				uappok=true;
				uappfile = confolder + "\\" + content[i];
				System.out.println("[Installer.checkApplicationForInstall] the uapp file is:" + uappfile);
			}
		}
		confolder = new File(folder+"\\license");
		content = confolder.list();
		for(int i = 0;i<content.length;i++){
			if(content[i].equals("ASL2.0.txt")) aslok=true;
		}
		if(!aslok) throw new Exception("No license agreement found!");
		if(!uappok) throw new Exception("The uapp file not found!");
		// initialization: get references to DeployManager and AALSpaceManager
		//initMpaInstallation();
			
	}
	

	
	/**
	 * 
	 * @param Path
	 */
	private Bundle installBundle(String path) {
			try {
				
				Bundle neu=context.installBundle("file:"+path);
				System.out.println("====================================");
				System.out.println("BundleID: "+neu.getBundleId());
				System.out.println("Location: "+neu.getLocation());
				System.out.println("state: "+neu.getState());
				System.out.println("symbolic Name: "+neu.getSymbolicName());
				System.out.println("====================================");
				return neu;
			} catch (BundleException e) {
				System.out.println("Error installing bundle");
				return null;
			}
	}
	public ArrayList<Bundle> getInstalledBundles(){
		return installedBundles;
	}
	public void resetBundles(){
		installedBundles.clear();
	}

	public boolean copy(File inputFile, File outputFile) {
		try {
			FileReader in = new FileReader(inputFile);
			FileWriter out = new FileWriter(outputFile);
			int c;

			while ((c = in.read()) != -1)
				out.write(c);

			in.close();
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
private String extractBundles(String path) {
    String destDir = path.substring(path.lastIndexOf(File.separator) + 1,path.lastIndexOf("."));
    System.out.println("[Installer.extractBundles] destDir - " + destDir);
    destDir =Activator.getInformation().getBundleDir() + destDir;
	//destDir =Activator.getInformation().getBundleDir() +"/"+ destDir; //Does this work only on Linux/Unix?
	System.out.println("[Installer.extractBundles] destDir is " + destDir);
	destDir =destDir.replace("/", "\\");  // For windows version
	//System.out.println("[Installer.extractBundles] destDir2 is " + destDir);
	File appDir=new File(destDir);
	System.out.println("[Installer.extractBundles] the path for zip file is: " + path + 
			" and the destination path is: " + destDir);
	int suffix=1;
	int slength=0;
	while(appDir.exists()){
		destDir=destDir.substring(0, destDir.length()-slength)+suffix;
		slength=(suffix+"").length();
		appDir=new File(destDir);
		suffix++;
		System.out.println("[Installer.extractBundles] appDir - " + appDir + " suffix -" + suffix);
	}
	appDir.mkdir();
	try {
		extractFolder(path, destDir);
	} catch (ZipException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("[Installer.extractBundles] the destination path is: " + destDir);
		return destDir;
	}

public void revertInstallation(File folder){
	Iterator<Bundle> itr=installedBundles.iterator();
	while(itr.hasNext()){
		try {
			Bundle b=itr.next();
			if(!(b.getState()==Bundle.UNINSTALLED))
				b.uninstall();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Deinstaller.deleteFolder(folder);
}

static public void extractFolder(String zipFile, String destdir) throws ZipException, IOException 
{
    System.out.println("[Installer.extractFolder] the zip file is: " + zipFile + " and dest dir: " + destdir);
    int BUFFER = 2048;
    File file = new File(zipFile);

    ZipFile zip = new ZipFile(file);
    String newPath = destdir;

    new File(newPath).mkdir();
    Enumeration zipFileEntries = zip.entries();

    // Process each entry
    while (zipFileEntries.hasMoreElements())
    {
        // grab a zip file entry
        ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
        String currentEntry = entry.getName();
        File destFile = new File(newPath, currentEntry);
        //destFile = new File(newPath, destFile.getName());
        File destinationParent = destFile.getParentFile();

        // create the parent directory structure if needed
        destinationParent.mkdirs();

        if (!entry.isDirectory())
        {
            BufferedInputStream is = new BufferedInputStream(zip
            .getInputStream(entry));
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
	 * initiation for MPA installation
	 * - get AALSpaceManager
	 * - get DeployManager
	 */
	private boolean initMpaInstallation()  {
		System.out.println("[Installer.initMpaInstallation]");
		ModuleContext moduleContext = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		
		if (moduleContext==null)
			System.out.println("[Installer.initMpaInstallation] moduleContext is null!");
		else {
			System.out.println("[Installer.initMpaInstallation] moduleContext exists!");
			moduleContext.logDebug("[Installer.initMpaInstallation] moduleContext exists", null, null);
		}
		//Object[] aalManagers = (Object[]) moduleContext.getContainer().fetchSharedObject(moduleContext,new Object[]{AALSpaceManager.class.getName().toString()});
		Object aalManagers = moduleContext.getContainer().fetchSharedObject(moduleContext,new Object[]{AALSpaceManager.class.getName().toString()});
		if(aalManagers != null){
			moduleContext.logDebug("[Installer.initMpaInstallation] AALSpaceManagers found...", null, null);
			System.out.println("[Installer.initMpaInstallation] AALSpaceManagers found...");
			if(aalManagers instanceof AALSpaceManager){
				aalSpaceManager = (AALSpaceManager)aalManagers;				
			}

			else{
				moduleContext.logWarn("Installer.initMpaInstallation] No AALSpaceManagers found", null, null);
				System.out.println("[Installer.initMpaInstallation]No AALSpaceManagers found");
				initialized = false;
				return initialized;
			}
		}else{
			moduleContext.logWarn("Installer.initMpaInstallation] No AALSpaceManagers found", null, null);
			System.out.println("[MpaParser]No AALSpaceManagers found");
			initialized = false;
			return initialized;
		}

		
		Object refs = moduleContext.getContainer().fetchSharedObject(moduleContext,new Object[]{DeployManager.class.getName().toString()});
		if(refs != null){
			deployManager = (DeployManager)refs;						
			initialized = true;
			return initialized;
		}else{
			moduleContext.logWarn("Installer.initMpaInstallation] No DeployManager found", null, null);
			System.out.println("Installer.initMpaInstallation] No DeployManager found");
			initialized = false;
			return initialized;
		}
	}

	/**
	 * call DeployManager to install MPA using the specified layout
	 * 
	 */
	public InstallationResults requestToInstall(UAPPPackage uapp) {
		System.out.println("[Installer.requestToInstall] deployFolder: " + uapp.getFolder());
		// initialization: get references to DeployManager and AALSpaceManager
		if (!initialized) initMpaInstallation();
		if (deployManager==null) {
			System.out.println("[Installer.requestToInstall] No deploy manager exists!");
			return InstallationResults.NOT_A_DEPLOYMANAGER;
		}
		System.out.println("[Installer.requestToInstall] Call deploy manager to install...");
		return deployManager.requestToInstall(uapp);
	}

	/**
	 * get peers in AALSpace from the AALSpaceManager
	 * 
	 */
	public Map<String, PeerCard> getPeers() {
		// initialization: get references to DeployManager and AALSpaceManager
		if (!initialized) initMpaInstallation();
		
		Map peers = new HashMap();
		if (aalSpaceManager!=null) {
			peers = aalSpaceManager.getPeers();
			System.out.println("[Installer.getPeers()]" + peers.toString());
		} else {
			// use faked data to test without really connected to DeployManager
			PeerCard card= new PeerCard(PeerRole.PEER, "karaf", "Java");
			System.out.println("[Installer.getPeers] peerCard1 for testing: " + card.getPeerID() + "/"
					+ card.getOS() + "/" + card.getPLATFORM_UNIT() + "/" + card.getCONTAINER_UNIT() + "/" + card.getRole());
			peers.put("Node1", card);
			card= new PeerCard(PeerRole.PEER, "karaf", "Java");		// to have a different unique PeerId	
			peers.put("Node2", card);
			System.out.println("[Installer.getPeers] peerCard2 for testing: " + card.getPeerID() + "/"
					+ card.getOS() + "/" + card.getPLATFORM_UNIT() + "/" + card.getCONTAINER_UNIT() + "/" + card.getRole());
			card= new PeerCard(PeerRole.PEER, "karaf", "C++");		// to have a different unique PeerId	
			peers.put("Node3", card);
			System.out.println("[Installer.getPeers] peerCard3 for testing: " + card.getPeerID() + "/"
					+ card.getOS() + "/" + card.getPLATFORM_UNIT() + "/" + card.getCONTAINER_UNIT() + "/" + card.getRole());
		}
		return peers;
	}
	
	/**
	 * installing a service 
	 * @param path - the path of the downloaded .usrv file
	 * return the folder of the extracted contents for the .usrv file
	 */
	public String installService(String path) {
		// TODO: add a window to show the starting of installing the service
		// extract .usrv file under "/bundles" folder
		System.out.println("[Installer.installService] extract the .usrv file from: " + path);
		String srvPath = extractBundles(path);
		System.out.println("[Installer.installService] the service bundles are extracted to: " + srvPath);	
		// TODO: check the contents of the .usrv file
		// initialization: get references to DeployManager and AALSpaceManager
		initMpaInstallation();
		return srvPath;		
	}
	
	/**
	 * installing a service from uStore
	 * @param path: the path for the downloaded .usrv file
	 *  
	 */
	public void installServiceFromOnlineStore(String path)  {
		String srvPath = installService(path);
		// install each .uapp file found			
		if(srvPath==null) 
			System.out.println("[Installer.installServiceFromOnlineStore] Error extracting .usrv Package");
			//TODO: update with the .usrv file structure - this has the same logic as InstallView.installFile()		
			// get usrvId
			UsrvParser usrv = new UsrvParser(srvPath+File.separator+"config");
			String serviceId = usrv.getServiceId();
			System.out.println("[Installer.installServiceFromOnlineStore] the serviceId is: " + serviceId);
			srvPath = srvPath + File.separator + "bin";
			System.out.println("[Installer.installServiceFromOnlineStore] the .uapp files contained in: " + srvPath);
			File appDir=new File(srvPath);
			String[] content = appDir.list();
			String appPath;
			for(int i=0;i<content.length;i++){
				if(content[i].endsWith(".uapp")) {				
					try {
						appPath = installApplication(srvPath + content[i]);
						Activator.getMainWindow().installApp(appPath, serviceId);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
			}
		
		
	}

	public InstallationResults requestToUninstall(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}