package org.universAAL.ucc.plugin.karaf.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.plugin.karaf.gui.juic.Ui_KarafDeployView;
import org.universAAL.ucc.viewjambi.common.SubWindow;

import com.trolltech.qt.core.QDir;
import com.trolltech.qt.gui.*;

/**
 * This file deploys an application downloaded as .uaal to local Karaf 
 * It uses many methods (and modified accordingly) from ucc.core.installer
 *  
 * @author shanshan
 *
 */
public class KarafDeployView extends SubWindow {

    private static Ui_KarafDeployView karaf_base = new Ui_KarafDeployView();  

    private IPluginBase uCCPluginBase = null;
    
       
    public KarafDeployView(IPluginBase uCCPluginBase) {
        super(KarafDeployView.karaf_base);
        this.uCCPluginBase = uCCPluginBase;
        		
		karaf_base.pushButton_karaf.clicked.connect(this,"getKarafPath()");
		karaf_base.pushButton_app.clicked.connect(this, "getAppFile()");
		karaf_base.pushButton_ok.clicked.connect(this, "deployToKaraf()");
		karaf_base.pushButton_cancel.clicked.connect(this, "cancel()");
    }


    protected void getKarafPath() {
    	System.out.println("getting karaf installation path...");
    	
    	String directory = QFileDialog.getExistingDirectory(this,
                "karaf installation path", QDir.currentPath());
    	if (!directory.equals("")) {    		
				karaf_base.lineEdit_karaf.setText(directory);
				System.out.println("karaf installation path: " + karaf_base.lineEdit_karaf.text());
		}
	}
    
    protected void getAppFile() {
    	System.out.println("getting application installation file (.uaal)");
		QFileDialog dialog = new QFileDialog(this, "Please choose a file to install!", ".", "universAAL (*.uaal)");
		int result = dialog.exec();
		
		if (result == QDialog.DialogCode.Accepted.value()) {
			String file = dialog.selectedFiles().get(0);
			if (file != null)
				karaf_base.lineEdit_app.setText(file);
				System.out.println("application to be installed: " + karaf_base.lineEdit_app.text());
		}
	}
	
	protected void deployToKaraf() {
		System.out.println("Deploying to Karaf started");
		String karafPath = karaf_base.lineEdit_karaf.text();
		karafPath = karafPath.replace("/", "\\");
		String p=karaf_base.lineEdit_app.text();
		p=p.replace("/", "\\");
		try {
			String appDir = deployApplication(karafPath,p);
			QMessageBox.information(this, "Karaf deployment", "Deployment to Karaf has been successfully completed!");
			this.uCCPluginBase.getMainView().hideSubWindow(this);			
		} catch (NullPointerException e){
			QMessageBox.critical(this, "Error", "Deployment failed! The Application probably already is installed!");
		} catch (Exception e) {
			QMessageBox.critical(this, "Error", e.getMessage());
			this.uCCPluginBase.getMainView().hideSubWindow(this);
		}
	}
	
	protected void cancel() {
		System.out.println("Cancel button is pressed");
		this.uCCPluginBase.getMainView().removeSubWindow(this);
	}

	/**
	 * deploy the application appP to karafP
	 * @param karafP
	 * @param appP
	 * @return
	 * @throws Exception 
	 */
	private String deployApplication(String karafP, String appP) throws Exception  {
		// TODO: check if the application installation file is valid, i.e., the .uaal file
		// unzip the .uaal file, put
		System.out.println("[deployApplication] karaf path: " + karafP + "; app path: "+ appP);
		String exdir=extractBundles(karafP, appP);
		if (exdir==null) throw new Exception("Error extracting uaal Package");
		System.out.println("[karaf] the application files are extracted under " + exdir);
		File appDir=new File(exdir);
		checkApplicationForInstall(appDir);
		String[] bundlelist=appDir.list();
		for(int i=0;i<bundlelist.length;i++){
			if(bundlelist[i].endsWith(".jar")){
				// copy .jars to /deploy folder
				String dPath = karafP + "\\deploy";
				System.out.println("copy " + bundlelist[i] + " to " + dPath);
				File inFile = new File(appDir, bundlelist[i]);
				File outFile = new File(dPath, bundlelist[i]);
				//copyfile(inFile, outFile);
				copy(inFile, outFile);
			} 
			if (bundlelist[i].endsWith(".cfg")){
				// copy .cfgs to /etc folder
				String cPath = karafP + "\\etc";
				System.out.println("copy " + bundlelist[i] + " to " + cPath);
				File inFile = new File(appDir, bundlelist[i]);
				File outFile = new File(cPath, bundlelist[i]);
				//copyfile(inFile, outFile);
				copy(inFile, outFile);
			}
		} 
		return exdir;
	}
	
	/**
	 * Is bundle valid? All need files available? Dependencies to check and all
	 * right? Check if concrete instances available (but how)?
	 * 
	 * TODO: If we use features descriptor, then check this. To be updated...
	 * 
	 * @param Path
	 * @throws Exception 
	 */
	private void checkApplicationForInstall(File folder) throws Exception {
		String[] content = folder.list();
		boolean jarok=false;
		boolean configok=false;
		boolean eulaok=false;
		for(int i=0;i<content.length;i++){
			if(content[i].endsWith(".jar")) jarok=true;
			//if(content[i].equals("config.owl")) configok=true;
			if(content[i].equals("EULA.txt")) eulaok=true;
		}
		if(!jarok) throw new Exception("There is no installable jar File in uaal Package!");
		//if(!configok) throw new Exception("config.owl file not found!");
		//if(!eulaok) throw new Exception("No License agreement found!");
	}
	
	private String extractBundles(String karafPath, String path) {
	      
		String destDir = path.substring(path.lastIndexOf(File.separator) + 1,path.lastIndexOf("."));
		destDir = karafPath +"\\"+ destDir;
		File appDir=new File(destDir);
		int suffix=1;
		int slength=0;
		while(appDir.exists()){
			destDir=destDir.substring(0, destDir.length()-slength)+suffix;
			slength=(suffix+"").length();
			appDir=new File(destDir);
			suffix++;
		}
		appDir.mkdir();
		try {
			extractFolder(path, destDir);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return destDir;
		}
	
	static public void extractFolder(String zipFile, String destdir) throws ZipException, IOException 
	{
	    System.out.println(zipFile);
	    int BUFFER = 2048;
	    File file = new File(zipFile);

	    ZipFile zip = new ZipFile(file);
	    String newPath = destdir;

	    new File(newPath).mkdir();
	    Enumeration zipFileEntries = zip.entries();

	    // Process each entry
	    while (zipFileEntries.hasMoreElements())
	    {
	        // grab a zip file entry
	        ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	        String currentEntry = entry.getName();
	        File destFile = new File(newPath, currentEntry);
	        //destFile = new File(newPath, destFile.getName());
	        File destinationParent = destFile.getParentFile();

	        // create the parent directory structure if needed
	        destinationParent.mkdirs();

	        if (!entry.isDirectory())
	        {
	            BufferedInputStream is = new BufferedInputStream(zip
	            .getInputStream(entry));
	            int currentByte;
	            // establish buffer for writing file
	            byte data[] = new byte[BUFFER];

	            // write the current file to disk
	            FileOutputStream fos = new FileOutputStream(destFile);
	            BufferedOutputStream dest = new BufferedOutputStream(fos,
	            BUFFER);

	            // read and write until last byte is encountered
	            while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
	                dest.write(data, 0, currentByte);
	            }
	            dest.flush();
	            dest.close();
	            is.close();
	        }

	    }
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
	
	private void copyfile(File f1, File f2){
		  try{
			  //File f1 = new File(srFile);
			  //File f2 = new File(dtFile);
			  InputStream in = new FileInputStream(f1);
			  
			  //For Append the file.
			//  OutputStream out = new FileOutputStream(f2,true);

			  //For Overwrite the file.
			  OutputStream out = new FileOutputStream(f2);

			  byte[] buf = new byte[1024];
			  int len;
			  while ((len = in.read(buf)) > 0){
				  out.write(buf, 0, len);
			  }
			  in.close();
			  out.close();
			  System.out.println("File copied.");
		  }
		  catch(FileNotFoundException ex){
			  System.out.println(ex.getMessage() + " in the specified directory.");
			  System.exit(0);
		  }
		  catch(IOException e){
			  System.out.println(e.getMessage());  
		  }
		}
}
