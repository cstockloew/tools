package org.universAAL.ucc.core.information;

import java.util.ArrayList;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.universAAL.ucc.api.core.IInformation;
import org.universAAL.ucc.core.Activator;

public class Information implements IInformation {
	
	private BundleContext context = null;
	private String rundir=null;
	private String bundledir=null;
	
	public Information(BundleContext context) {
		this.context = context;
		String bundlePath = context.getBundle().getLocation();
		System.out.println(bundlePath);
	
		if(countOccurrences(bundlePath)==2){
			rundir= bundlePath.substring(bundlePath.indexOf(":")+2, bundlePath.lastIndexOf("/"));
			System.out.println(rundir);
			rundir= rundir.substring(0, rundir.lastIndexOf("/"));
			System.out.println(rundir);
			rundir= rundir.substring(0, rundir.lastIndexOf("/")+1);
			System.out.println(rundir);
			bundledir="plugins/";
		}else{
			/*rundir= bundlePath.substring(bundlePath.indexOf(":")+1, bundlePath.lastIndexOf("/")+1);
			System.out.println(rundir);*/
			rundir="";
			bundledir="bundles/";
		}
	}

	public String[] activeBundles() {
		Bundle[] bundles = context.getBundles();
		ArrayList<String> activeBundles = new ArrayList<String>();
		
		for (int i = 0; i<bundles.length; i++)
			if (bundles[i].getState() == Bundle.ACTIVE)
				activeBundles.add(bundles[i].getSymbolicName());
		
		String[] result = new String[activeBundles.size()];
		
		return activeBundles.toArray(result);
	}

	public Bundle[] bundles() {
		return context.getBundles();
	}
	public void setRunDir(String path){
		this.rundir=path;
	}
	public String getRunDir(){
		return this.rundir;
	}
	public String getBundleDir(){
		return this.bundledir;
	}
	private static int countOccurrences(String a){
		int result=0;
		char[] b= a.toCharArray();
		for(int i=0;i<b.length;i++){
			if(b[i]==':')
				result++;
		}
		return result;
	}

}
