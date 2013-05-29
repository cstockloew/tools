package org.universaal.tools.codeassistantapplication;

import java.io.File;
import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.universaal.tools.codeassistantapplication.ontologyrepository.client.RepositoryClient;

public class Startup{
	private static String codeAssistantDir = new String("CodeAssistantFiles");
	private static String filesDir = new String("owlfiles");
	private static URL owlFilesDir = null;

	public boolean earlyStartup() {
		boolean res=false;
		IPath ipath = Activator.getDefault().getStateLocation();
		//System.out.println("ipath="+ipath);
		try {
			File parentDir = ipath.toFile();
			File owlDir = new File(parentDir.toString(),filesDir);
			boolean result = owlDir.mkdir();  
		    //if(result)    
		      //System.out.println("Directory for owl files have been created.");
		    res = RepositoryClient.downloadAllOntologies(owlDir.toString());
		}
		catch (Exception e) { e.printStackTrace(); }
		return res;
	}

/*	
	public boolean earlyStartup() {
		boolean res=false;
		IPath ipath = new Path(codeAssistantDir+File.separator);
		
		owlFilesDir = FileLocator.find(Platform.getBundle("org.universaal.tools.codeAssistant"), ipath, null);
		try {
			//System.out.println("owlFilesDir="+owlFilesDir);
			owlFilesDir = FileLocator.toFileURL(owlFilesDir);
			String tmp = owlFilesDir.toString();
			if (tmp.indexOf(" ")!=-1)
				tmp = (owlFilesDir.toString()).replaceAll(" ", "%20");
			owlFilesDir = new URL(tmp);
			
			File parentDir = new File(FileLocator.resolve(owlFilesDir).toURI());
			File owlDir = new File(parentDir.toString(),filesDir);
			boolean result = owlDir.mkdir();  
		    //if(result)    
		      //System.out.println("Directory for owl files have been created.");
		    res = RepositoryClient.downloadAllOntologies(owlDir.toString());
		}
		catch (Exception e) { e.printStackTrace(); }
		return res;
	}
*/
/*	
	public boolean earlyStartup() {
		boolean res=false;
		IPath ipath = new Path(codeAssistantDir+File.separator+filesDir+File.separator);
		owlFilesDir = FileLocator.find(Platform.getBundle("org.universaal.tools.codeAssistant"), ipath, null);
		try {
			System.out.println("owlFilesDir="+owlFilesDir);
			owlFilesDir = FileLocator.toFileURL(owlFilesDir);
			String tmp = owlFilesDir.toString();
			if (tmp.indexOf(" ")!=-1)
				tmp = (owlFilesDir.toString()).replaceAll(" ", "%20");
			owlFilesDir = new URL(tmp);
			File directory = new File(FileLocator.resolve(owlFilesDir).toURI());
			if (directory.isDirectory())
				res = RepositoryClient.downloadAllOntologies(directory.toString());
		}
		catch (Exception e) { e.printStackTrace(); }
		return res;
	}
*/
}
