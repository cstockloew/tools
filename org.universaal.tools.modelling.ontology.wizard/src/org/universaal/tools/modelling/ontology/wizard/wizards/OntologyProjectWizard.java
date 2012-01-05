package org.universaal.tools.modelling.ontology.wizard.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class OntologyProjectWizard extends Wizard implements INewWizard {
	
	OntologyProjectModel model = new OntologyProjectModel();
	
	OntologyMainPage mainPage;
	OntologyImportPage importPage;
	MavenDetailsPage mavenPage;

	public OntologyProjectWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		mainPage = new OntologyMainPage();
		mainPage.setModel(model);
		importPage = new OntologyImportPage();
		importPage.setModel(model);
		mavenPage = new MavenDetailsPage();
		mavenPage.setModel(model.mavenModel);
		addPage(mainPage);
		addPage(importPage);
		addPage(mavenPage);
	}

	@Override
	public boolean performFinish() {
		return false;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}

}
