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
import org.universaal.tools.packaging.tool.parts.MPA;
import org.universaal.tools.packaging.tool.parts.Part;

public class GUI extends WizardMod {

	private ExecutionEvent event;
	public static MPA mpa;
	private PageImpl p1, p2, pl, p3, p4, p5, pp, p;
	private List<IProject> artifacts;

	public GUI(){
		super();
		setNeedsProgressMonitor(true);
	}

	public GUI(MPA multipartApplication, ExecutionEvent event) {
		super();
		setNeedsProgressMonitor(true);
		this.event = event;
		mpa = multipartApplication;
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

			for(int i = 0; i < artifacts.size(); i++){
				mpa.getApplication().getParts().add(new Part(artifacts.get(i).getName()));
				pp = new PagePart(Page.PAGE_PART+(i+1), i);
				addPage(pp);
				pp.setMPA(mpa);
				pp.setArtifact(artifacts.get(i));
			}
		}
		else{
			p = new ErrorPage(Page.PAGE_ERROR);
			addPage(p);
		}	
	}

	@Override
	public boolean performFinish() {
		//TODO generate XML

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

		artifacts = new ArrayList<IProject>();

		ISelection selection = window.getSelectionService().getSelection("org.eclipse.jdt.ui.PackageExplorer");
		if(selection == null)
			selection = window.getSelectionService().getSelection("org.eclipse.ui.navigator.ProjectExplorer");

		if ((selection != null) && (selection instanceof StructuredSelection)) {

			Iterator selected = ((StructuredSelection) selection).iterator();
			while(selected.hasNext()){
				Object sel = selected.next();

				if(sel instanceof IProject)
					artifacts.add((IProject) sel);
			}
		}
	}

	public int getArtifactsCount(){
		return artifacts.size();
	}
}