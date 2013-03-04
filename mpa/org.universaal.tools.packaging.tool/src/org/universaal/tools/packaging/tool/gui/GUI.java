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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.universaal.tools.packaging.api.Page;
import org.universaal.tools.packaging.api.WizardMod;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.MPA;
import org.universaal.tools.packaging.tool.parts.Part;
import org.universaal.tools.packaging.tool.util.Dialog;
import org.universaal.tools.packaging.tool.zip.CreateJar;
import org.universaal.tools.packaging.tool.zip.UAAP;

public class GUI extends WizardMod {

	private ExecutionEvent event;
	public MPA mpa;
	private PageImpl p1, p2, pl, p3, p4, p5, ppDU, ppEU, ppPC, ppPR, p;
	private List<IProject> parts;

	private static GUI instance;

	private String tempDir;  

	public GUI(ExecutionEvent event) {

		super();
		setNeedsProgressMonitor(true);

		this.event = event; 
		mpa = new MPA();
		instance = this;
	}

	public static synchronized GUI getInstance(){
		return instance;
	}

	@Override
	public void addPages() {

		if(event != null){
			try {
				analyzeSelection(HandlerUtil.getActiveWorkbenchWindowChecked(event));
			} catch (Exception e) {
				e.printStackTrace();
			}

			p1 = new Page1(Page.PAGE1);
			addPage(p1);
			p1.setMPA(mpa);

			p2 = new Page2(Page.PAGE2);
			addPage(p2);
			p2.setMPA(mpa);

			pl = new PageLicenses(Page.PAGE_LICENSE);
			pl.setPageComplete(true);
			addPage(pl);
			pl.setMPA(mpa);

			p3 = new Page3(Page.PAGE3);
			addPage(p3);
			p3.setMPA(mpa);

			p4 = new Page4(Page.PAGE4);
			addPage(p4);
			p4.setMPA(mpa);

			p5 = new Page5(Page.PAGE5);
			addPage(p5);
			p5.setMPA(mpa);

			for(int i = 0; i < parts.size(); i++){

				String partName = parts.get(i).getName();

				mpa.getApplication().getParts().add(new Part(partName));

				if(parts.size() > 1)
					mpa.getApplication().getApplication().setMultipart(true);
				else
					mpa.getApplication().getApplication().setMultipart(false);

				ppDU = new PagePartDU(Page.PAGE_PART+partName, i); //deployment units
				addPage(ppDU);
				ppDU.setMPA(mpa);
				ppDU.setArtifact(parts.get(i));

				ppEU = new PagePartEU(Page.PAGE_PART+partName, i); // execution units
				addPage(ppEU);
				ppEU.setMPA(mpa);
				ppEU.setArtifact(parts.get(i));

				ppPC = new PagePartPC(Page.PAGE_PART+partName, i); // part capabilities
				addPage(ppPC);
				ppPC.setMPA(mpa);
				ppPC.setArtifact(parts.get(i));

				ppPR = new PagePartPR(Page.PAGE_PART+partName, i); // part requirements
				addPage(ppPR);
				ppPR.setMPA(mpa);
				ppPR.setArtifact(parts.get(i));
			}
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
			File file = new File(tempDir+"/config/"+mpa.getApplication().getApplication().getName()+".xml");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(mpa.getXML());
			out.close();

			// create jars
			CreateJar jar = new CreateJar();
			for(int i = 0; i < parts.size(); i++)		
				jar.run(parts.get(i), i+1);

			// copy licenses

			// copy properties files
			for(int i = 0; i < mpa.getApplication().getParts().size(); i++){
				for(int j = 0; j < mpa.getApplication().getParts().get(i).getExecutionUnits().size(); j++){
					
					File configFile = mpa.getApplication().getParts().get(i).getExecutionUnits().get(j).getConfigFile();
					File fileIntoUAAP = new File(tempDir+"/config/"+configFile.getName());

					InputStream in = new FileInputStream(configFile);
					OutputStream out_ = new FileOutputStream(fileIntoUAAP);

					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0){
						out_.write(buf, 0, len);
					}
					in.close();
					out.close();
				}
			}

			UAAP descriptor = new UAAP();
			descriptor.createUAAPfile(tempDir, 
					new Dialog().open(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), mpa.getApplication().getApplication().getName()+".uapp").getAbsolutePath());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	private void analyzeSelection(IWorkbenchWindow window){

		parts = new ArrayList<IProject>();

		ISelection selection = window.getSelectionService().getSelection("org.eclipse.jdt.ui.PackageExplorer");
		if(selection == null)
			selection = window.getSelectionService().getSelection("org.eclipse.ui.navigator.ProjectExplorer");

		if ((selection != null) && (selection instanceof StructuredSelection)) {

			Iterator selected = ((StructuredSelection) selection).iterator();
			while(selected.hasNext()){
				Object sel = selected.next();

				if(sel instanceof IProject)
					parts.add((IProject) sel);
			}
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
			SecureRandom random = new SecureRandom();			
			tempDir = System.getProperty("java.io.tmpdir")+"/MPA_"+new BigInteger(130, random).toString(32)+"/";
			File f = new File(tempDir);
			f.mkdir();
			//System.out.println("tempDir: "+f.getAbsolutePath());

			File bin, config, doc, license, part;

			bin = new File(f+"/bin");
			bin.mkdir();
			config = new File(f+"/config");
			config.mkdir();
			doc = new File(f+"/doc");
			doc.mkdir();
			license = new File(f+"/license");
			license.mkdir();

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
}