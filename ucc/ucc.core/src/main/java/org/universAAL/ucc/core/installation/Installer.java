package org.universAAL.ucc.core.installation;

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


import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.core.Activator;


/**
 * @author Alex
 * @version 1.0
 * @created 11-Jul-2011 15:57:26
 */
public class Installer extends ApplicationManager implements IInstaller {

	private BundleContext context;
	private ArrayList<Bundle> installedBundles=new ArrayList<Bundle>();
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
		return exdir;
	}

	/**
	 * Is bundle valid? All need files available? Dependencies to check and all
	 * right? Check if concrete instances available (but how)?
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
			if(content[i].equals("config.xml")) configok=true;
			if(content[i].equals("EULA.txt")) eulaok=true;
		}
		if(!jarok) throw new Exception("There is no installable jar File in uaal Package!");
		if(!configok) throw new Exception("config.xml file not found!");
		if(!eulaok) throw new Exception("No License agreement found!");
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
	destDir =Activator.getInformation().getRunDir() + File.separator + destDir;
	File appDir=new File(destDir);
	int suffix=1;
	while(appDir.exists()){
		destDir=destDir.substring(0, destDir.length())+suffix;
		appDir=new File(destDir);
		suffix++;
	}
	appDir.mkdir();
	try {
		JarFile jar;
		jar = new JarFile(path);
	Enumeration<JarEntry> e = jar.entries();
	while (e.hasMoreElements()) {
		JarEntry file = (JarEntry) e.nextElement();
		File f = new File(destDir + File.separator + file.getName());
		InputStream is = jar.getInputStream(file); // get the input stream
		FileOutputStream fos = new java.io.FileOutputStream(f);
		while (is.available() > 0) {  // write contents of 'is' to 'fos'
			fos.write(is.read());
		}
		fos.close();
		is.close();
	}
	return destDir;
	} catch (IOException e1) {
		Deinstaller.deleteFolder(appDir);
		return null;
	}	
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
}