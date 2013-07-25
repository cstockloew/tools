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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.universaal.tools.packaging.tool.api.Page;
import org.universaal.tools.packaging.tool.api.WizardMod;
import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.MPA;
import org.universaal.tools.packaging.tool.parts.Part;
import org.universaal.tools.packaging.tool.util.ConfigProperties;
import org.universaal.tools.packaging.tool.util.Configurator;
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
	private PageImpl p0, p1, p2, pl, p3, p4, p5, ppB, ppDU, ppEU, ppPC, ppPR, p, p_end;
	private List<IProject> parts;

	
	private static GUI instance;

	private String destination;
	private String tempDir = org.universaal.tools.packaging.tool.Activator.tempDir;
	public File recoveryStorage = null;

	public GUI(List<IProject> parts, Boolean recovered) {

		super();
		setNeedsProgressMonitor(true);
	
		checkPersistence(recovered);
				
		if(mpa == null){
			mpa = new MPA();
		}
		
		instance = this;
		this.parts = parts;
		
	}

	private void checkPersistence(Boolean recovered) {
		if ( Configurator.local.isPersistanceEnabled() ) {
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

	public static synchronized GUI getInstance(){
		return instance;
	}

	@Override
	public void addPages() {
		if(this.parts != null){
			
			p0 = new StartPage(Page.PAGE_START);
			addPage(p0);
			p0.setMPA(mpa);
			
			p1 = new Page1(Page.PAGE1);
			addPage(p1);
			p1.setMPA(mpa);
/*
			p2 = new Page2(Page.PAGE2);
			addPage(p2);
			p2.setMPA(mpa);

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
			 
			if(parts.size() > 1)
				mpa.getAAL_UAPP().getApplication().setMultipart(true);
			else
				mpa.getAAL_UAPP().getApplication().setMultipart(false);

			for(int i = 0; i < parts.size(); i++){

				String partName = parts.get(i).getName();

				mpa.getAAL_UAPP().getAppParts().add(new Part("part"+(i+1)));

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
*/
			p_end = new EndPage(Page.PAGE_END);
			addPage(p_end);
			p_end.setMPA(mpa);
			addingPermanentStorageDecorator();
		}
		else{
			p = new ErrorPage(Page.PAGE_ERROR);
			addPage(p);
		}	

		createTempContainer();
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

			// copy properties files
			for(int i = 0; i < mpa.getAAL_UAPP().getAppParts().size(); i++){
				for(int j = 0; j < mpa.getAAL_UAPP().getAppParts().get(i).getExecutionUnits().size(); j++){

					File configFile = mpa.getAAL_UAPP().getAppParts().get(i).getExecutionUnits().get(j).getConfigFile();
					copyFile(configFile, new File(tempDir+"/config/"+configFile.getName()));
				}
			}

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
	
			UAPP descriptor = new UAPP();
			descriptor.createUAPPfile(tempDir, destination);

			callUSTORE(destination);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

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

			File bin, icon, config, doc, license, part, emptyFile;

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

			emptyFile = new File(f+"/config/.empty"); 
			emptyFile.createNewFile();

			emptyFile = new File(f+"/doc/.empty");
			emptyFile.createNewFile();

			emptyFile = new File(f+"/license/.empty");
			emptyFile.createNewFile();

			emptyFile = new File(f+"/bin/icon/.empty"); 
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
}