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
import org.universaal.tools.packaging.api.WizardMod;
import org.universaal.tools.packaging.tool.parts.MultipartApplication;

public class GUI extends WizardMod {

	private List<IProject> artifacts;
	private ExecutionEvent event;
	public static MultipartApplication app;
	private PageImpl p1, p2, pl, p3, p4, p5, pp, p;

	public GUI(){
		super();
		setNeedsProgressMonitor(true);

		app = new MultipartApplication();
	}

	public GUI(MultipartApplication application, ExecutionEvent event) {
		super();
		setNeedsProgressMonitor(true);
		app = application;
		this.event = event;
	}

	@Override
	public void addPages() {

		if(event != null){
			try {
				analyzeSelection(HandlerUtil.getActiveWorkbenchWindowChecked(event));
			} catch (Exception e) {
				e.printStackTrace();
			}

			p1 = new Page1("Application details");
			addPage(p1);
			p1.setMPA(app);

			p2 = new Page2("Contacts");
			addPage(p2);
			p2.setMPA(app);

			pl = new PageLicenses("SLA and licenses");
			addPage(pl);
			pl.setMPA(app);

			p3 = new Page3("Application capabilities");
			addPage(p3);
			p3.setMPA(app);

			p4 = new Page4("Application requirements");
			addPage(p4);
			p4.setMPA(app);

			p5 = new Page5("Application management");
			addPage(p5);
			p5.setMPA(app);

			for(int i = 0; i < artifacts.size(); i++){

				pp = new PagePart("Part #"+(i+1)+" details");
				addPage(pp);
				pp.setMPA(app);
				pp.setArtifact(artifacts.get(i));
			}
		}
		else{
			p = new ErrorPage("MPA error");
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
}