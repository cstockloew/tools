package org.universaal.tools.packaging.tool.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.universaal.tools.packaging.tool.gui.GUI;
import org.universaal.tools.packaging.tool.util.POMParser;

public class CreateJar {

	public void create(IProject part, int partNumber){

		GUI g = GUI.getInstance();
		String destination_path = g.getTempDir()+"/bin/part"+partNumber+"/";
		POMParser p = new POMParser(new File(part.getFile("pom.xml").getLocation()+""));			

		String sourcePath = part.getLocation().toString();
		try{							
			String fileName = p.getArtifactID()+"-"+p.getVersion()+".jar";

			IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();
			IFile pomResource = g.getPart(part.getName()).getFile(IMavenConstants.POM_FILE_NAME);
			IMavenProjectFacade projectFacade = projectManager.create(pomResource, true, null);

			IMaven maven = MavenPlugin.getMaven();
			if(pomResource != null && projectFacade != null){
				MavenExecutionRequest request = projectManager.createExecutionRequest(pomResource, projectFacade.getResolverConfiguration(), null);

				List<String> goals = new ArrayList<String>();
				Properties props = new Properties();

				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceDescription description = workspace.getDescription();
				if (!description.isAutoBuilding())
					goals.add("compiler:compile"); // compile it if autobuilding is off
				goals.add("package");

				request.setGoals(goals);
				request.setUserProperties(props);
				MavenExecutionResult execution_result = maven.execute(request, null);
				if(execution_result.getExceptions() != null && !execution_result.getExceptions().isEmpty())
					for(int i = 0; i < execution_result.getExceptions().size(); i++)
						System.out.println("\nERROR: "+execution_result.getExceptions().get(i).getMessage());

				copyFile(new File(sourcePath+"/target/"+fileName), new File(destination_path+fileName));
			}	
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		try{
			//if file KAR is present, add it to partX folder

			String fileName = p.getArtifactID()+"-"+p.getVersion()+".kar";
			File kar = new File(sourcePath+"/target/"+fileName);
			if(kar.exists())
				copyFile(kar, new File(destination_path+fileName));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	//	private void add(File source, JarOutputStream target, String startFrom){
	//
	//		BufferedInputStream in = null;
	//		try{			
	//			if (source.isDirectory()){
	//
	//				String name = source.getPath().replace("\\", "/");
	//				name = name.replace(startFrom, "");
	//
	//				if (!name.isEmpty()){
	//
	//					if (!name.endsWith("/"))
	//						name += "/";
	//
	//					JarEntry entry = new JarEntry(name);
	//					entry.setTime(source.lastModified());
	//					target.putNextEntry(entry);
	//					target.closeEntry();
	//				}
	//				for (File nestedFile: source.listFiles())
	//					add(nestedFile, target, startFrom);
	//				return;
	//			}
	//
	//			JarEntry entry = new JarEntry(source.getPath().replace("\\", "/").replace(startFrom, ""));
	//			entry.setTime(source.lastModified());
	//			target.putNextEntry(entry);
	//			in = new BufferedInputStream(new FileInputStream(source));
	//
	//			byte[] buffer = new byte[1024];
	//			while (true)
	//			{
	//				int count = in.read(buffer);
	//				if (count == -1)
	//					break;
	//				target.write(buffer, 0, count);
	//			}
	//			target.closeEntry();
	//		}
	//		catch(Exception ex){
	//			ex.printStackTrace();
	//		}
	//		finally{
	//			if (in != null)
	//				try {
	//					in.close();
	//				} catch (IOException e) {
	//					e.printStackTrace();
	//				}
	//		}
	//	}

	private void copyFile(File source, File destination){

		try{
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(destination);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}