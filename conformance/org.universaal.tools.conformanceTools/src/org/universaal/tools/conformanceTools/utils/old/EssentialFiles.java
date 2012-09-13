package org.universaal.tools.conformanceTools.utils.old;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.universaal.tools.conformanceTools.Activator;
import org.universaal.tools.conformanceTools.utils.RunPlugin;

public class EssentialFiles {

	public static boolean verify(String projectDest, RunPlugin plugin){

		try{
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectDest);

			InputStream jar, xml;
			String reportDirCKS = OptionsParser.get("reportDirCKS");//"checkstyle_violations_reports";
			String reportDirFB = OptionsParser.get("reportDirFB");//"findbugs_violations_reports";

			if(plugin == RunPlugin.CheckStyle){
				xml = new URL("platform:/plugin/"+Activator.PLUGIN_ID
						+"/src/org/universaal/tools/conformanceTools/"
						+OptionsParser.get("checkstyleChecksFilePath")
						+OptionsParser.get("checkstyleChecksFile"))
							.openConnection().getInputStream(); 
				
				jar = new URL("platform:/plugin/"+Activator.PLUGIN_ID
						+"/"
						+OptionsParser.get("checkstyleJarPath")+"/"
						+OptionsParser.get("checkstyleJar")) //+"/checkstyle-5.5-all.jar")
							.openConnection().getInputStream(); 

				IFile file = project.getFile(OptionsParser.get("checkstyleChecksFile")); // checks to execute
				if(!file.exists())
					file.create(xml, true, null);

				IFolder lib = project.getFolder("lib");
				if(!lib.exists())
					lib.create(true, true, null);
				file = project.getFile("lib/"+OptionsParser.get("checkstyleJar"));//checkstyle-5.5-all.jar"); // checkstyle plugin
				if(!file.exists())
					file.create(jar, true, null);

				IFolder target = project.getFolder(reportDirCKS);
				if(!target.exists())
					target.create(true, true, null);

				return true;
			}
			else if(plugin == RunPlugin.FindBugs){

				IFolder libFolder = project.getFolder("lib");
				if(!libFolder.exists())
					libFolder.create(true, true, null);

				libFolder = project.getFolder("lib/findbugs");
				if(!libFolder.exists())
					libFolder.create(true, true, null);

				InputStream findbugsLibs = new URL("platform:/plugin/"
				+Activator.PLUGIN_ID
				+"/src/org/universaal/tools/conformanceTools/utils/findbugs.properties").openConnection().getInputStream(); 
				
				BufferedReader a = new BufferedReader(new InputStreamReader(findbugsLibs));
				String lib;
				while((lib = a.readLine()) != null){
					
					InputStream toCopy = new URL("platform:/plugin/"+Activator.PLUGIN_ID+"/lib/findbugs/"+lib).openConnection().getInputStream(); 
					IFile file = project.getFile("lib/findbugs/"+lib);
					if(!file.exists())
						file.create(toCopy, true, null);
				}

				IFolder target = project.getFolder(reportDirFB);
				if(!target.exists())
					target.create(true, true, null);

				return true;
			}

			return false;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
}