package org.universAAL.ucc.core.installation;

import java.io.File;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.universAAL.ucc.api.core.IDeinstaller;
import org.universAAL.ucc.api.model.IApplicationManagment;
import org.universAAL.ucc.api.model.IApplicationRegistration;
import org.universAAL.ucc.core.Activator;


public class Deinstaller extends ApplicationManager implements IDeinstaller {
	private IApplicationManagment manager=Activator.getModel().getApplicationManagment();
	private IApplicationRegistration register= Activator.getModel().getApplicationRegistration();
	private BundleContext context;
	
	public Deinstaller(BundleContext con){
		context=con;
	}
	
	public boolean deinstallAppication(String appName) {
		if(manager.containsApplication(appName)){
			List<String> bundles=manager.getBundles(appName);
//			Iterator<String> itr=bundles.iterator();
//			while(itr.hasNext()){
//				String bundleName = itr.next();
//				if(!deinstallBundle(appName, bundleName))
//				return false;
//			}
			removeData(appName, bundles);
			Activator.getModel().getApplicationRegistration().removeConfigFile(appName,Activator.getInformation().getRunDir());
			register.unregisterApplication(appName);
			System.out.println("Deinstalled  "+appName);
			return true;
		}else{
			return false;
		}
		
	}
	
//	private boolean deinstallBundle(String appName, String bundleName) {
//		List<String> list = new ArrayList<String>();
//		list.add(bundleName);
//		removeData("real Dir to come..", list);
//		return register.unregisterBundle(appName, bundleName);
//	}
	
	private boolean removeData(String appDir, List<String> bundles){
		Bundle[] installedBundles=context.getBundles();
		Iterator<String> itr= bundles.iterator();
		String version,name;
		String versionInstalled,nameInstalled;
		while(itr.hasNext()){
			String[] temp=itr.next().split("_");
			name=temp[0];
			version=temp[1];
			for(int i=0;i<installedBundles.length;i++){
				Dictionary dict = installedBundles[i].getHeaders();
				nameInstalled=installedBundles[i].getSymbolicName();
				versionInstalled=(String) dict.get("Bundle-Version");
				if(name.equals(nameInstalled)& version.equals(versionInstalled))
					try {
						installedBundles[i].uninstall();
					} catch (BundleException e) {
						return false;
					}
			}	
		}
//		if(!appDir.endsWith("bundles"))
//			deleteFolder(new File(appDir));
		return true;
	}
	public static void deleteFolder(File folder){
		File[] df=folder.listFiles();
		if(df==null) return;
		for(int i=0;i<df.length;i++){
			df[i].delete();
		}
		folder.delete();
	}

}
