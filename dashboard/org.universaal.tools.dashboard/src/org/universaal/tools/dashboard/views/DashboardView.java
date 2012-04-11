/*
	Copyright 2011 SINTEF, http://www.sintef.no

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
package org.universaal.tools.dashboard.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.universaal.tools.dashboard.buttonlisteners.TemporaryListener;
import org.universaal.tools.dashboard.buttonlisteners.TemporaryMouseListener;
import org.universaal.tools.dashboard.buttonlisteners.old.BuildProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.old.CreateNewItemListener;
import org.universaal.tools.dashboard.buttonlisteners.old.CreateNewProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.old.CreateOntologyProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.old.DebugProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.old.GenerateXmlListener;
import org.universaal.tools.dashboard.buttonlisteners.old.ImportExampleListener;
import org.universaal.tools.dashboard.buttonlisteners.old.ImportThirdPartyListener;
import org.universaal.tools.dashboard.buttonlisteners.old.PublishProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.old.RunProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.old.TransformListener;
import org.universaal.tools.dashboard.buttonlisteners.old.UploadOpenSourceListener;
import org.universaal.tools.dashboard.listeners.ProjectElementSelectionListener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * The Dashboardview itself.
 * @author Adrian
 *
 */
public class DashboardView extends ViewPart {

	public static final String ID = "org.universaal.tools.dashboard.views.DashboardView"; //$NON-NLS-1$

	private IProject project;

	private ISelectionListener selectionListener;
	private TabFolder tabFolder;
	private Label lblProject;

	public DashboardView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		lblProject = new Label(parent, SWT.NONE);
		lblProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblProject.setText("Project");

		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		{
			tabFolder = new TabFolder(scrolledComposite, SWT.NONE);
			
			TabItem tbtmCreatingAnAal = new TabItem(tabFolder, SWT.NONE);
			tbtmCreatingAnAal.setText("Creating an AAL Service");
			
			MySWT mySWT = new MySWT(tabFolder, SWT.NONE);
			tbtmCreatingAnAal.setControl(mySWT);
			
			TabItem tbtmOntologyProject = new TabItem(tabFolder, SWT.NONE);
			tbtmOntologyProject.setText("Creating an Ontology");
			
			OntologyView ontologyView2 = new OntologyView(tabFolder, SWT.NONE);
			ontologyView2.createActions(this);
			tbtmOntologyProject.setControl(ontologyView2);			
			new Label(ontologyView2, SWT.NONE);

		}
		scrolledComposite.setContent(tabFolder);
		scrolledComposite.setMinSize(tabFolder.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		createActions();
		initializeToolBar();
		initializeMenu();
		hookPageSelection();

	}

	/**
	 * Use this method to create all the buttonlisteners, and assign them to 
	 * their respective buttons.
	 */
	private void createActions() {
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void dispose(){
		super.dispose();
		ResourceManager.dispose();
		SWTResourceManager.dispose();
		if(selectionListener!=null){
			getSite().getPage().removePostSelectionListener(selectionListener);
		}
	}

	/**
	 * Add selectionListener for updating Project Name
	 */
	private void hookPageSelection(){
		selectionListener = new ProjectElementSelectionListener(this);
		getSite().getPage().addPostSelectionListener(selectionListener);
	}

	private IWorkbenchPart partOfLastSelection;
	
	
	public void setSelectedElementWithContext(Object selectedElement, String selectedElementString, IProject selectedProject, IWorkbenchPart partOfLastSelection) {		
		setLastSelectionPart(partOfLastSelection);
		setSelectedProject(selectedProject);
		setSelectedElement(selectedElement);
		setSelectedElementName(selectedElementString);
	}
	
	
	private void setLastSelectionPart(IWorkbenchPart partOfLastSelection) {
		this.partOfLastSelection = partOfLastSelection;
	}
	

	private void setSelectedElement(Object element) {
		setSelectedElementName(element.toString());
	}
	
	private void setSelectedElementName(String name){
		if (name != null)
			lblProject.setText(name);
		else 
			lblProject.setText("No element selected");			
	}

	private void setSelectedProject(IProject input){
		this.project = input;
	}

	public IProject getSelectedProject(){
		return project;
	}
	
	public IWorkbenchPart getPartOfLastSelection() {
		return partOfLastSelection;
	}
}
