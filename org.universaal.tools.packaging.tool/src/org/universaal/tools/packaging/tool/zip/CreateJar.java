package org.universaal.tools.packaging.tool.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.universaal.tools.packaging.tool.gui.GUI;
import org.universaal.tools.packaging.tool.util.POMParser;

public class CreateJar {

	public void create(IProject part, int partNumber){

		GUI g = GUI.getInstance();
		String destination_path = g.getTempDir()+"/bin/part"+partNumber+"/";

		try{			
			String path = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+part.getDescription().getName();
			POMParser p = new POMParser(new File(part.getFile("pom.xml").getLocation()+""));			
			String fileName = p.getArtifactID()+"-"+p.getVersion()+".jar";

			Manifest manifest = new Manifest();
			manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");			

			JarOutputStream target = new JarOutputStream(new FileOutputStream(destination_path+fileName), manifest);
			add(new File(path), target, ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/");
			target.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		try{
			//if file KAR is present, add it to partX folder
			String path = ResourcesPlugin.getWorkspace().getRoot().getLocation().makeAbsolute()+"/"+part.getDescription().getName();
			POMParser p = new POMParser(new File(part.getFile("pom.xml").getLocation()+""));
			String fileName = p.getArtifactID()+"-"+p.getVersion()+".kar";
			File kar = new File(path+"/target/"+fileName);
			if(kar.exists())
				copyFile(kar, new File(destination_path+fileName));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void add(File source, JarOutputStream target, String startFrom){

		BufferedInputStream in = null;
		try{			
			if (source.isDirectory()){

				String name = source.getPath().replace("\\", "/");
				name = name.replace(startFrom, "");

				if (!name.isEmpty()){

					if (!name.endsWith("/"))
						name += "/";

					JarEntry entry = new JarEntry(name);
					entry.setTime(source.lastModified());
					target.putNextEntry(entry);
					target.closeEntry();
				}
				for (File nestedFile: source.listFiles())
					add(nestedFile, target, startFrom);
				return;
			}

			JarEntry entry = new JarEntry(source.getPath().replace("\\", "/").replace(startFrom, ""));
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));

			byte[] buffer = new byte[1024];
			while (true)
			{
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

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