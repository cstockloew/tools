package org.universaal.tools.packaging.tool.gui;

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
import org.universaal.tools.packaging.tool.parts.Application;
import org.universaal.tools.packaging.tool.parts.MPA;

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

			for(int i = 0; i < artifacts.size(); i++){

				mpa.getApplications().add(new Application());

				p1 = new Page1(Page.PAGE1+(i+1), i);
				addPage(p1);
				p1.setMPA(mpa);

				p2 = new Page2(Page.PAGE2+(i+1), i);
				addPage(p2);
				p2.setMPA(mpa);

				pl = new PageLicenses(Page.PAGE_LICENSE+(i+1), i);
				addPage(pl);
				pl.setMPA(mpa);

				p3 = new Page3(Page.PAGE3+(i+1), i);
				addPage(p3);
				p3.setMPA(mpa);

				p4 = new Page4(Page.PAGE4+(i+1), i);
				addPage(p4);
				p4.setMPA(mpa);

				p5 = new Page5(Page.PAGE5+(i+1), i);
				addPage(p5);
				p5.setMPA(mpa);

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