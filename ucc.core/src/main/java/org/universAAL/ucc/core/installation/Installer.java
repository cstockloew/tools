package org.universAAL.ucc.core.installation;

import java.io.File;

import org.universAAL.ucc.Activator;
import org.universAAL.ucc.api.core.IInstaller;

/**
 * @author Alex
 * @version 1.0
 * @created 11-Jul-2011 15:57:26
 */
public class Installer extends ApplicationManager implements IInstaller {

	public Installer(){

	}
	
	public Installer(String path){
		installApplication(path);
	}

	public void finalize() throws Throwable {
		System.out.println();
	}
	
	/**
	 * 
	 * @param Path
	 */
	public boolean installApplication(String path){
		
		if (checkApplicationForInstall(path))
			return installBundle(path);
		
		return false;
	}

	/**
	 * Is bundle valid? All need files available? Dependencies to check and all right?
	 * Check if concrete instances available (but how)?
	 * 
	 * @param Path
	 */
	private boolean checkApplicationForInstall(String path){
		if (path == null || path.equals(""))
			return false;
		
		File installFile = new File(path);
		if (installFile.isFile())
			return true;
		
		return false;
	}

	/**
	 * 
	 * @param Path
	 */
	private boolean installBundle(String path){
		File file = new File(path);
		boolean success = file.renameTo(file);
		
		if (success) {
			System.out.println("Bundle installed!");
			Activator.getModel().getApplicationRegistration().registerApplicaton(generateAppName(path));
			return true;
		} else
			System.out.println("Error during install of bundle!");
		
		return false;
	}
}