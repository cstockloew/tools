package org.universAAL.ucc.core.installation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.core.Activator;


/**
 * @author Alex
 * @version 1.0
 * @created 11-Jul-2011 15:57:26
 * 
 * modified 31-05-2012 by Shanshan
 * 
 */
public class Installer extends ApplicationManager implements IInstaller {

	private BundleContext context;
	private ArrayList<Bundle> installedBundles=new ArrayList<Bundle>();
	private boolean mpa = false; // flag to indicate if the application is MPA
	
	public Installer(BundleContext con) {
		context=con;
	}

	public Installer(String path) throws Exception {
		installApplication(path);
	}

	public void finalize() throws Throwable {
		System.out.println();
	}

	/**
	 * 
	 * @param Path
	 * @throws Exception 
	 */
	public String installApplication(String path) throws Exception {
		//Activator.getModel().getApplicationRegistration().writeToConfigFile("test");

		String exdir=extractBundles(path);
		if(exdir==null)throw new Exception("Error extracting uaal Package");
		File appDir=new File(exdir);
		checkApplicationForInstall(appDir);
		if (!mpa) {			
			// install on local node / OSGi container
		String[] bundlelist=appDir.list();
		for(int i=0;i<bundlelist.length;i++){
			if(bundlelist[i].endsWith(".jar")){
				Bundle temp=installBundle(exdir+File.separator+bundlelist[i]);
				if(temp==null){
					revertInstallation(appDir);
					throw new Exception("Error installing Bundle "+ bundlelist[i]);
				}
				installedBundles.add(temp);
			}
		}
		}
		return exdir;
		
	}

	/**
	 * Is bundle valid? All need files available? Dependencies to check and all
	 * right? Check if concrete instances available (but how)? 
	 * 
	 * Check if the application is MPA?
	 * 
	 * @param Path
	 * @throws Exception 
	 */
	private void checkApplicationForInstall(File folder) throws Exception {
		String[] content = folder.list();
		boolean jarok=false;
		boolean configok=false;
		boolean eulaok=false;
		for(int i=0;i<content.length;i++){
			if(content[i].endsWith(".jar")) jarok=true;
			if(content[i].equals("config.owl")) configok=true;
			if(content[i].equals("EULA.txt")) eulaok=true;
			if(content[i].endsWith(".mpa")) mpa=true;
		}
		if(!jarok) throw new Exception("There is no installable jar File in uaal Package!");
		if(!configok) throw new Exception("config.owl file not found!");
		//if(!eulaok) throw new Exception("No License agreement found!");
		if(mpa) System.out.println("This is a multi-part application...");
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
	File appDir;
//	if(destDir.equals("NutritionalAdvisor")){
//		destDir=Activator.getInformation().getBundleDir();
//		appDir=new File(destDir);
//	}else{
		//destDir =Activator.getInformation().getBundleDir()+"/"+ destDir; Does this work only on Linux/Unix?
		destDir =Activator.getInformation().getBundleDir()+"\\"+ destDir;  // For windows version	
		appDir=new File(destDir);
		int suffix=1;
		int slength=0;
		while(appDir.exists()){
			destDir=destDir.substring(0, destDir.length()-slength)+suffix;
			slength=(suffix+"").length();
			appDir=new File(destDir);
			suffix++;
		}
		appDir.mkdir();
//	}
	
	try {
		extractFolder(path, destDir);
	} catch (ZipException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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
    System.out.println(zipFile);
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
	
}