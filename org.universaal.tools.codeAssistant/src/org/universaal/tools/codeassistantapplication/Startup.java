/*****************************************************************************************
	Copyright 2012-2014 CERTH-HIT, http://www.hit.certh.gr/
	Hellenic Institute of Transport (HIT)
	Centre For Research and Technology Hellas (CERTH)
	
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 *****************************************************************************************/

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
		try {
			File parentDir = ipath.toFile();
			File owlDir = new File(parentDir.toString(),filesDir);
			boolean result = owlDir.mkdir();  
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
