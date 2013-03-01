package org.universaal.tools.packaging.tool.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.universaal.tools.packaging.api.Page;
import org.universaal.tools.packaging.api.WizardMod;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.MPA;
import org.universaal.tools.packaging.tool.parts.Part;

public class GUI extends WizardMod {

	private ExecutionEvent event;
	public MPA mpa;
	private PageImpl p1, p2, pl, p3, p4, p5, ppDU, ppEU, ppPC, ppPR, p;
	private List<IProject> parts;

	private static GUI instance;

	//	private GUI(){
	//		super();
	//		setNeedsProgressMonitor(true);
	//	}

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
	}

	@Override
	public boolean performFinish() {

		//TODO generate XML and compress app

		File file = new File(mpa.getApplication().getApplication().getName()+".xml");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(mpa.getXML());
			out.close();
		} catch (IOException e) {
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
}