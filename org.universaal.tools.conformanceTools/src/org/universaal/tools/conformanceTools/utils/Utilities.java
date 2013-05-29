package org.universaal.tools.conformanceTools.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.osgi.framework.Bundle;

public class Utilities {

	public static ArrayList<ICompilationUnit> getAllClasses(IProject p) throws JavaModelException {

		ArrayList<ICompilationUnit> classList = new ArrayList<ICompilationUnit>();

		IJavaProject javaProject = JavaCore.create(p);
		IPackageFragment[] packages = javaProject.getPackageFragments();

		for (IPackageFragment myPackage : packages) {
			ICompilationUnit[] cus = myPackage.getCompilationUnits();
			for(ICompilationUnit cu : cus){
				classList.add(cu);
			}
		}		

		return classList;
	}

	public static void copyFile(Bundle bundle, String destPath, String fileName, String sourcePath){

		try{
			Path path = new Path(sourcePath+fileName);
			URL fileURL = Platform.find(bundle, path);
			InputStream is = fileURL.openStream(); 			
			OutputStream os = new FileOutputStream(destPath+fileName);
			byte[] buffer = new byte[4096];  
			int bytesRead;  
			while ((bytesRead = is.read(buffer)) != -1) {  
				os.write(buffer, 0, bytesRead);  
			}  
			is.close();  
			os.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}