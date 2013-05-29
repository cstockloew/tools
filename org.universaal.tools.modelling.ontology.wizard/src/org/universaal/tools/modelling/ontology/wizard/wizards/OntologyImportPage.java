package org.universaal.tools.modelling.ontology.wizard.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.ResourceManager;

public class OntologyImportPage extends WizardPage {

	/**
	 * Create the wizard.
	 */
	public OntologyImportPage() {
		super("wizardPage");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.universaal.tools.modelling.ontology.wizard", "icons/ic-uAAL-hdpi.png"));
		setTitle("Ontology import");
		setDescription("Select existing ontologies to import");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		ListViewer listViewer = new ListViewer(container, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		List list = listViewer.getList();
		list.setItems(new String[] {"org.universaal.middleware.owl", "org.universaal.middleware.service.owl", "org.universaal.ontology.phThing"});
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}
	
	OntologyProjectModel model;
	
	public void setModel(OntologyProjectModel model) {
		this.model = model;
	}
	
	public void updateFromModel() {
		
	}
	
	public void updateModel() {
		
	}

}
