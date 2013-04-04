package org.universaal.tools.packaging.tool.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.universaal.tools.packaging.api.Page;
import org.universaal.tools.packaging.api.WizardMod;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.MPA;
import org.universaal.tools.packaging.tool.parts.Part;
import org.universaal.tools.packaging.tool.zip.CreateJar;
import org.universaal.tools.packaging.tool.zip.UAPP;

public class GUI extends WizardMod {

	//private ExecutionEvent event;
	public MPA mpa;
	private PageImpl p0, p1, p2, pl, p3, p4, p5, ppDU, ppEU, ppPC, ppPR, p, p_end;
	private List<IProject> parts;

	private static GUI instance;

	private String tempDir;  
	private String destination;

	public GUI(/*ExecutionEvent event*/List<IProject> parts) {

		super();
		setNeedsProgressMonitor(true);

		//this.event = event; 
		mpa = new MPA();
		instance = this;
		this.parts = parts;
	}

	public static synchronized GUI getInstance(){
		return instance;
	}

	@Override
	public void addPages() {

		if(this.parts != null){
			//			try {
			//				analyzeSelection(HandlerUtil.getActiveWorkbenchWindowChecked(event));
			//			} catch (Exception e) {
			//				e.printStackTrace();
			//			}

			p0 = new StartPage(Page.PAGE_START);
			addPage(p0);
			p0.setMPA(mpa);

			p1 = new Page1(Page.PAGE1);
			addPage(p1);
			p1.setMPA(mpa);

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

				//POMParser p = new POMParser(new File(parts.get(i).getFile("pom.xml").getLocation()+""));
				mpa.getAAL_UAPP().getAppParts().add(new Part("part"+(i+1)));

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
		}
		else{
			p = new ErrorPage(Page.PAGE_ERROR);
			addPage(p);
		}	

		createTempContainer();
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

			UAPP descriptor = new UAPP();
			descriptor.createUAPPfile(tempDir, destination);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	//	private void analyzeSelection(IWorkbenchWindow window){
	//
	//		parts = new ArrayList<IProject>();
	//
	//		ISelection selection = window.getSelectionService().getSelection("org.eclipse.jdt.ui.PackageExplorer");
	//		if(selection == null)
	//			selection = window.getSelectionService().getSelection("org.eclipse.ui.navigator.ProjectExplorer");
	//
	//		if ((selection != null) && (selection instanceof StructuredSelection)) {
	//
	//			Iterator selected = ((StructuredSelection) selection).iterator();
	//			while(selected.hasNext()){
	//				Object sel = selected.next();
	//
	//				if(sel instanceof IProject)
	//					parts.add((IProject) sel);
	//			}
	//		}
	//	}

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
			SecureRandom random = new SecureRandom();			
			tempDir = System.getProperty("java.io.tmpdir")+"/MPA_"+new BigInteger(130, random).toString(32)+"/";
			File f = new File(tempDir);
			f.mkdir();

			File bin, config, doc, license, part, emptyFile;

			bin = new File(f+"/bin");
			bin.mkdir();
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