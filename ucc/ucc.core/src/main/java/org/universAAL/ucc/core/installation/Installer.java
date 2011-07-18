package org.universAAL.ucc.core.installation;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.core.Activator;

/**
 * @author Alex
 * @version 1.0
 * @created 11-Jul-2011 15:57:26
 */
public class Installer extends ApplicationManager implements IInstaller {

	public Installer() {

	}

	public Installer(String path) {
		installApplication(path);
	}

	public void finalize() throws Throwable {
		System.out.println();
	}

	/**
	 * 
	 * @param Path
	 */
	public boolean installApplication(String path) {

		if (checkApplicationForInstall(path))
			return installBundle(path);

		return false;
	}

	/**
	 * Is bundle valid? All need files available? Dependencies to check and all
	 * right? Check if concrete instances available (but how)?
	 * 
	 * @param Path
	 */
	private boolean checkApplicationForInstall(String path) {
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
	private boolean installBundle(String path) {
		if (!path.endsWith(".uaal")) {
			System.out
					.println("No valid intput file: Must end with \".uaal\"!");
			return false;
		}

		File file = new File(path);
		String filename = file.getName();
		filename = filename.substring(filename.lastIndexOf(File.separator) + 1);
		filename = Activator.getRundir() + File.separator + filename;
		System.out.println(filename);

		File uaal_home = new File(filename);

		boolean success = copy(file, uaal_home);// file.renameTo(uaal_home);

		if (success) {
			System.out.println("Bundle installed!");
			Activator.getModel().getApplicationRegistration()
					.registerApplicaton(generateAppName(filename));
			return true;
		} else
			System.out.println("Error during install of bundle!");

		return false;
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
}