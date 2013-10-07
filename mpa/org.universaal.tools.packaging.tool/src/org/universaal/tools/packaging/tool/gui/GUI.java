/*

        Copyright 2007-2014 CNR-ISTI, http://isti.cnr.it
        Institute of Information Science and Technologies
        of the Italian National Research Council

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
 */
package org.universaal.tools.packaging.tool.gui;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.maven.execution.MavenExecutionRequest;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.universaal.tools.packaging.tool.api.Page;
import org.universaal.tools.packaging.tool.api.WizardMod;
import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.Application;
import org.universaal.tools.packaging.tool.parts.ApplicationManagement;
import org.universaal.tools.packaging.tool.parts.ApplicationManagement.RemoteManagement;
import org.universaal.tools.packaging.tool.parts.License;
import org.universaal.tools.packaging.tool.parts.LicenseSet;
import org.universaal.tools.packaging.tool.parts.MPA;
import org.universaal.tools.packaging.tool.parts.Part;
import org.universaal.tools.packaging.tool.util.ConfigProperties;
import org.universaal.tools.packaging.tool.util.Configurator;
import org.universaal.tools.packaging.tool.util.EffectivePOMContainer;
import org.universaal.tools.packaging.tool.util.POM_License;
import org.universaal.tools.packaging.tool.zip.CreateJar;
import org.universaal.tools.packaging.tool.zip.UAPP;

/**
 * 
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano Lenzi</a>
 * @version $LastChangedRevision$ ( $LastChangedDate$ )
 */
public class GUI extends WizardMod {

	public MPA mpa;
	private PageImpl p0, p1, p2, pl, pdu, par, p3, p4, p5, ppB, ppDU, ppEU, ppPC, ppPR, p, p_end;
	private List<IProject> parts;
	
	private static GUI instance;

	private String destination;
	private String tempDir = org.universaal.tools.packaging.tool.Activator.tempDir;
	private String mavenTempDir;
	private String mainPartName;
	
	public File recoveryStorage = null;
	public boolean recovered = false;

	public GUI(List<IProject> parts, Boolean recovered, String mainPartName) {

		super();
		setNeedsProgressMonitor(true);
		
		this.mainPartName = mainPartName;
		
		checkPersistence(recovered);

		if(mpa == null){
			mpa = new MPA();
		}
		
		instance = this;
		this.parts = parts;

	}

	public IWizardPage getStartingPage() {
		String startPage = (mpa.getAAL_UAPP().getCurrentPageTitle() != "") ? mpa.getAAL_UAPP().getCurrentPageTitle() : Page.PAGE_START;
		return getPage(startPage);
	}
	
	private void checkPersistence(Boolean recovered) {
		if ( Configurator.local.isPersistanceEnabled() ) {
			this.recovered = recovered;
			
			//File tmpDir = new File(tempDir);
			File recovery = new File(tempDir + ConfigProperties.RECOVERY_FILE_NAME_DEFAULT);
			recoveryStorage = recovery;
				   
			//if ( tmpDir.exists() ) {
				if ( recovery.exists() && recovered) {
					
					try {
						ObjectInputStream ois = new ObjectInputStream( new FileInputStream( recovery ) );
					    MPA recoveredStatus = (MPA) ois.readObject();
					    //multipartApplication.setApplication(recoveredStatus.getAAL_UAPP());
					    if (recoveredStatus != null){
					    	System.out.println("Loading recovered data from "+recovery.getAbsolutePath());
						    mpa = recoveredStatus;
						    destination = mpa.getAAL_UAPP().getDestination();
						} else {
					    	System.out.println("[WARNING] Unable to load data from recovery file");
					    }
					} catch (Exception e) {		
					    e.printStackTrace();
					}
			    } 

			//} 
		} // else System.out.println("Recovering not enabled");
	}

	private void loadDataFromManPOM() {
		
		EffectivePOMContainer.setDocument(mainPartName);
		
		Application app = mpa.getAAL_UAPP();
		
		app.getApplication().setName(EffectivePOMContainer.getName());
		app.getApplication().setAppID(EffectivePOMContainer.getArtifactId());
		app.getApplication().setDescription(EffectivePOMContainer.getDescription());
		app.getApplication().getApplicationProvider().setOrganizationName(EffectivePOMContainer.getOrganization().name);
		try {
			app.getApplication().getApplicationProvider().setWebAddress(new URI(EffectivePOMContainer.getOrganization().url));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		app.getApplication().getVersion().setVersion(EffectivePOMContainer.getVersion());
		
		EffectivePOMContainer.getDependencies();
		try {
			app.getApplication().getApplicationProvider().setWebAddress(new URI(EffectivePOMContainer.getOrganization().url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Licenses
		List<POM_License> Licenses = EffectivePOMContainer.getLicenses();
		if(Licenses != null){
			List<License> licenseList = new ArrayList<License>();
			for(int i = 0; i < Licenses.size(); i++){
				//System.out.println("Getting linense "+i+" of "+Licenses.size());
				
				License aLicense = new License();
				aLicense.setName(Licenses.get(i).name);
				try {
					aLicense.setLink(new URI(Licenses.get(i).url));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				licenseList.add(aLicense);
			}
			LicenseSet aLicenseSet = new LicenseSet();
			aLicenseSet.setLicenseList(licenseList);
			
			List<LicenseSet> ls = app.getApplication().getLicenses();
			ls.add(aLicenseSet);
			app.getApplication().setLicenses(ls);
		}

		// Bundle, Artifact Id and Version per part
		for(int i = 0; i < parts.size(); i++){

			EffectivePOMContainer.setDocument(parts.get(i).getName());
			
			String bundleVersion = EffectivePOMContainer.getBundleVersion() != "" ? EffectivePOMContainer.getBundleVersion() : EffectivePOMContainer.getVersion();
			app.getAppParts().get(i).setPartBundle(EffectivePOMContainer.getBundleId(), bundleVersion);
			
			RemoteManagement rm = app.getAppManagement().new RemoteManagement();
			rm.getSoftware().setArtifactID(EffectivePOMContainer.getArtifactId());
			rm.getSoftware().getVersion().setVersion(EffectivePOMContainer.getVersion());
			app.getAppManagement().getRemoteManagement().add(rm);

		}

	}

	public static synchronized GUI getInstance(){
		return instance;
	}

	@Override
	public void addPages() {
		if(this.parts != null){
			
			if(parts.size() > 1)
				mpa.getAAL_UAPP().getApplication().setMultipart(true);
			else
				mpa.getAAL_UAPP().getApplication().setMultipart(false);

			createTempContainer();
			genEffectivePom(this.parts);
			if(!recovered) loadDataFromManPOM();
			
			p0 = new StartPage(Page.PAGE_START);
			addPage(p0);
			p0.setMPA(mpa);
			
			p1 = new Page1(Page.PAGE1);
			addPage(p1);
			p1.setMPA(mpa);

			p2 = new Page2(Page.PAGE2);
			addPage(p2);
			p2.setMPA(mpa);

			pdu = new PageDU(Page.PAGE_DU);
			addPage(pdu);
			pdu.setMPA(mpa);
		
			par = new PageAppResources(Page.PAGE_APP_RESOURCES);
			addPage(par);
			par.setMPA(mpa);
		
			pl = new PageLicenses(Page.PAGE_LICENSE);
			addPage(pl);
			pl.setMPA(mpa);
		
			p3 = new Page3(Page.PAGE3);
			addPage(p3);
			p3.setMPA(mpa);

			p4 = new Page4(Page.PAGE4, 0, null, null);
			addPage(p4);
			p4.setMPA(mpa);

			p5 = new Page5(Page.PAGE5);
			addPage(p5);
			p5.setMPA(mpa);
		
			for(int i = 0; i < parts.size(); i++){

				String partName = parts.get(i).getName();
				
				ppB = new PagePartBundle(Page.PAGE_PART_BUNDLE+partName, i); //deployment units
				addPage(ppB);
				ppB.setMPA(mpa);
				ppB.setArtifact(parts.get(i));

				ppDU = new PagePartDU(Page.PAGE_PART_DU+partName, i); //deployment units
				addPage(ppDU);
				ppDU.setMPA(mpa);
				ppDU.setArtifact(parts.get(i));
				
				ppEU = new PagePartEU(Page.PAGE_PART_EU+partName, i); // execution units
				addPage(ppEU);
				ppEU.setMPA(mpa);
				ppEU.setArtifact(parts.get(i));

				ppPC = new PagePartPC(Page.PAGE_PART_PC+partName, i); // part capabilities
				addPage(ppPC);
				ppPC.setMPA(mpa);
				ppPC.setArtifact(parts.get(i));

				ppPR = new PagePartPR(Page.PAGE_PART_PR+partName, i, 0, null, null); // part requirements
				addPage(ppPR);
				ppPR.setMPA(mpa);
				ppPR.setArtifact(parts.get(i));
			}

			p_end = new EndPage(Page.PAGE_END);
			addPage(p_end);
			p_end.setMPA(mpa);
			addingPermanentStorageDecorator();
		}
		else{
			p = new ErrorPage(Page.PAGE_ERROR);
			addPage(p);
		}	
	}
	
	private void addingPermanentStorageDecorator() {/*
	    if ( ! Configurator.local.isPersistanceEnabled() ) return;
	    IWizardPage[] phases = getPages();
	    ArrayList<IWizardPage> decoratedPhases = new ArrayList<IWizardPage>();
	    for (int i = 0; i < phases.length; i++) {
		if ( phases[i] instanceof PageImpl ) {
		   decoratedPhases.add( new PersistencePageDecorator( (PageImpl) phases[i]) );
		} else {
		    decoratedPhases.add( phases[i] );
		}
	    }
	    setPages(decoratedPhases);*/
	}

	@Override
	public boolean performCancel(){
		return MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Confirm Exit", "Are you sure you want to Exit?");
	}
	
	@Override
	public boolean performFinish() {

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(new Cursor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay(), SWT.CURSOR_WAIT));
		
		try {
			
			// create descriptor
			File file = new File(tempDir+"/config/"+mpa.getAAL_UAPP().getApplication().getName()+"."+Page.DESCRIPTOR_FILENAME_SUFFIX);
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(mpa.getXML());
			out.close();

			// create jars
			CreateJar jar = new CreateJar();
			for(int i = 0; i < parts.size(); i++)		
				jar.create(parts.get(i), i+1);

			// copy SLA and licenses (if possible)
			for(int i = 0; i < mpa.getAAL_UAPP().getApplication().getLicenses().size(); i++){

				if(mpa.getAAL_UAPP().getApplication().getLicenses().get(i).getSla().getLink().getScheme() != null && 
						mpa.getAAL_UAPP().getApplication().getLicenses().get(i).getSla().getLink().getScheme().equalsIgnoreCase("file")){ // copy files
					File sla = new File(mpa.getAAL_UAPP().getApplication().getLicenses().get(i).getSla().getLink());
					copyFile(sla, new File(tempDir+"/license/"+sla.getName()));
				}

				for(int j = 0; j < mpa.getAAL_UAPP().getApplication().getLicenses().get(i).getLicenseList().size(); j++){

					if(mpa.getAAL_UAPP().getApplication().getLicenses().get(i).getLicenseList().get(j).getLink().getScheme() != null && 
							mpa.getAAL_UAPP().getApplication().getLicenses().get(i).getLicenseList().get(j).getLink().getScheme().equalsIgnoreCase("file")){ // copy files
						File license = new File(mpa.getAAL_UAPP().getApplication().getLicenses().get(i).getLicenseList().get(j).getLink());
						copyFile(license, new File(tempDir+"/license/"+license.getName()));
					}
				}
			}

			// copy config files files and folders
			for(int i = 0; i < mpa.getAAL_UAPP().getAppParts().size(); i++){
				//for(int j = 0; j < mpa.getAAL_UAPP().getAppParts().get(i).getExecutionUnits().size(); j++){

				if(mpa.getAAL_UAPP().getAppParts().get(i).getExecutionUnit() != null){
				
					//File[] configFilesAndFolders = mpa.getAAL_UAPP().getAppParts().get(i).getExecutionUnits().get(j).configFilesAndFolders();
					File[] configFilesAndFolders = mpa.getAAL_UAPP().getAppParts().get(i).getExecutionUnit().getConfigFilesAndFolders();
					File tmp = new File(tempDir+"/config/"+mpa.getAAL_UAPP().getAppParts().get(i).getExecutionUnit().getArtifactId());
					tmp.mkdir();
					copyFilesAndFolders(configFilesAndFolders, tempDir+"/config/"+mpa.getAAL_UAPP().getAppParts().get(i).getExecutionUnit().getArtifactId()+"/");
					//copyFile(configFile, new File(tempDir+"/config/"+configFile.getName()));
				//}
				
				} else System.out.println("ExecUnit Null");
			}
			
			// copy additional resources
			if(mpa.getAAL_UAPP().getAppResouces() != null){
				
				//File[] configFilesAndFolders = mpa.getAAL_UAPP().getAppParts().get(i).getExecutionUnits().get(j).configFilesAndFolders();
				File[] appResources = mpa.getAAL_UAPP().getAppResouces();
				copyFilesAndFolders(appResources, tempDir+"/resources/");
				//copyFile(configFile, new File(tempDir+"/config/"+configFile.getName()));
			//}
			
			} else System.out.println("No additional resources");
			
			// copy icon file if set and eventually resize it
			File iconFile = mpa.getAAL_UAPP().getApplication().getMenuEntry().getIconFile();
			
			File tmpFile = new File(tempDir+"/img.png");
			if(tmpFile.exists()) tmpFile.delete();
			
			if(iconFile != null && iconFile.exists()){
				if (mpa.getAAL_UAPP().getApplication().getMenuEntry().getIconScale()){
					try {
						BufferedImage img = ImageIO.read(iconFile);
						Image scaled = img.getScaledInstance(512, 512, Image.SCALE_AREA_AVERAGING);
						BufferedImage buffered = new BufferedImage(512, 512, img.getType());
						buffered.getGraphics().drawImage(scaled, 0, 0 , null);
						File outputFile = new File(tempDir+"/bin/icon/"+iconFile.getName());
						ImageIO.write(buffered, "png", outputFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else copyFile(iconFile, new File(tempDir+"/bin/icon/"+iconFile.getName()));
			}
	
			// remove epom files if exists
			for(int i = 0; i < mpa.getAAL_UAPP().getAppParts().size(); i++){
				Part part = mpa.getAAL_UAPP().getAppParts().get(i);
				File toBeRemoved = new File(tempDir+"/"+part.getName()+".epom.xml");
				if (toBeRemoved.exists()) toBeRemoved.delete();
			}
			
			UAPP descriptor = new UAPP();
			descriptor.createUAPPfile(tempDir, destination);

			callUSTORE(destination);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(new Cursor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay(), SWT.CURSOR_ARROW));
		
		return true;
	}

	private void callUSTORE(String pathToUAPPFile){

		try{
			if (MessageDialog.openConfirm(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Confirm", "Do you want to upload the configuration to uStore?")) 
			{
				try {
					IHandlerService handlerService = (IHandlerService) (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);

					ICommandService commandService = (ICommandService) (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
					Command showElement = commandService.getCommand("org.universaal.tools.uStoreClienteapplication.actions.PublishAction");

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("org.universaal.tools.uStoreClienteapplication.filePathParameter", pathToUAPPFile);
					ParameterizedCommand paramShowElement = ParameterizedCommand.generateCommand(showElement, params);

					ExecutionEvent execEvent = handlerService.createExecutionEvent(paramShowElement, new Event());
					try {
						showElement.executeWithChecks(execEvent);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}	
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public int getPartsCount(){
		return parts.size();
	}

	public IProject getPart(String name){
		for(int i = 0; i < parts.size(); i++)
			if(parts.get(i).getName().equalsIgnoreCase(name))
				return parts.get(i);

		return null;
	}

	public List<IProject> getParts(){
		return parts;
	}

	private void createTempContainer(){

		try{
			File f = new File(tempDir);
			f.mkdir();
			mavenTempDir = f.getAbsolutePath();
			
			File bin, icon, config, doc, license, resources, part, emptyFile;

			bin = new File(f+"/bin");
			bin.mkdir();
			icon = new File(f+"/bin/icon");
			icon.mkdir();
			config = new File(f+"/config");
			config.mkdir();
			doc = new File(f+"/doc");
			doc.mkdir();
			license = new File(f+"/license");
			license.mkdir();
			resources = new File(f+"/resources");
			resources.mkdir();

			emptyFile = new File(f+"/bin/.empty"); 
			emptyFile.createNewFile();
			
			emptyFile = new File(f+"/bin/icon/.empty"); 
			emptyFile.createNewFile();
			
			emptyFile = new File(f+"/config/.empty"); 
			emptyFile.createNewFile();

			emptyFile = new File(f+"/doc/.empty");
			emptyFile.createNewFile();

			emptyFile = new File(f+"/license/.empty");
			emptyFile.createNewFile();

			emptyFile = new File(f+"/resources/.empty");
			emptyFile.createNewFile();

			for(int i = 0; i < parts.size(); i++){
				part = new File(f+"/bin/part"+(i+1));
				part.mkdir();
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public String getTempDir() {
		return tempDir;
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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	private void copyFilesAndFolders(File[] list, String before){
		list = sortList(list);
		for(int i=0; i<list.length; i++){
			//System.out.println(list[i].getAbsolutePath()+" -> "+ before+list[i].getName());
			
			if(list[i].isDirectory()){ 
				File[] tmp = list[i].listFiles();
				File f = new File(before+list[i].getName());
				f.mkdir();
				copyFilesAndFolders(tmp, (before+list[i].getName()+"/"));
			} else {
				copyFile(new File(list[i].getAbsolutePath()), new File(before+list[i].getName()));
			}
		}
	}
	
	private File[] sortList(File[] list){
		List<File> dirs = new ArrayList<File>();
		List<File> files = new ArrayList<File>();
		List<File> merged = new ArrayList<File>();
		
		for(int i=0; i<list.length; i++){
			
			if(list[i].isDirectory()){
				dirs.add(list[i]);
			} else {
				files.add(list[i]);
			}
		}

		if(files != null) merged.addAll(files);
		if(dirs != null) merged.addAll(dirs);
		
		File[] sorted = new File[merged.size()];
		
		for(int i=0; i<merged.size(); i++)
			sorted[i] = merged.get(i);
		
		return sorted;
		
	}
	
	private static <T> T[] concat(T[] first, T[] second) {
		  T[] result = Arrays.copyOf(first, first.length + second.length);
		  System.arraycopy(second, 0, result, first.length, second.length);
		  return result;
	}
	
	private void genEffectivePom(List<IProject> parts){
	
		//System.out.println("Generating Effective POM");
		
		for(int i=0; i<parts.size();i++){
		
			IProject part = parts.get(i);
			
			String partName = part.getName();
			mpa.getAAL_UAPP().getAppParts().add(new Part("part"+(i+1),partName));
			
			//System.out.println("Part name:"+partName);
			
			IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();
			IFile pomResource = part.getFile(IMavenConstants.POM_FILE_NAME);
			IMavenProjectFacade projectFacade = projectManager.create(pomResource, true, null);
	
			IMaven maven = MavenPlugin.getMaven();
	
			if(pomResource != null && projectFacade != null){
				
				//System.out.println("Resource not null, trying to hrlp:effective-pom");
				
				MavenExecutionRequest request;
				try {
					request = projectManager.createExecutionRequest(pomResource, projectFacade.getResolverConfiguration(), null);
					
					List<String> goals = new ArrayList<String>();
					Properties props = new Properties();
					props.setProperty("output", mavenTempDir+"/"+partName+".epom.xml");
					
					goals.add("help:effective-pom");
		
					request.setGoals(goals);
					request.setUserProperties(props);
					maven.execute(request, null);
					//System.out.println("Done.");
					EffectivePOMContainer.addDocument(partName, mavenTempDir+"/"+partName+".epom.xml");
					EffectivePOMContainer.getDependencies();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		
	}
}