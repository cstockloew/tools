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
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.universaal.tools.dashboard.buttonlisteners.BuildProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.CreateNewItemListener;
import org.universaal.tools.dashboard.buttonlisteners.CreateNewProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.CreateOntologyProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.DebugProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.GenerateXmlListener;
import org.universaal.tools.dashboard.buttonlisteners.ImportExampleListener;
import org.universaal.tools.dashboard.buttonlisteners.ImportThirdPartyListener;
import org.universaal.tools.dashboard.buttonlisteners.PublishProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.RunProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.TemporaryListener;
import org.universaal.tools.dashboard.buttonlisteners.TemporaryMouseListener;
import org.universaal.tools.dashboard.buttonlisteners.TransformListener;
import org.universaal.tools.dashboard.buttonlisteners.UploadOpenSourceListener;
import org.universaal.tools.dashboard.listeners.ProjectNameListener;

/**
 * The Dashboardview itself.
 * @author Adrian
 *
 */
public class DashboardView extends ViewPart {

	public static final String ID = "org.universaal.tools.dashboard.views.DashboardView"; //$NON-NLS-1$
	private Button btnCreateProject;
	private Button btnImportProject;
	private Button btnEditProject;
	private Button btnCreateClass;
	private Button btnImportClass;
	private Button btnEditClass;
	private Button btnTestConformance;
	private Button btnExtractConfiguration;
	private Button btnRun;
	private Button btnDebug;
	private Button btnUploadOpenSource;
	private Button btnPublishUstore;
	private Button btnCreate;
	private Button btnEdit;
	private Label lblCombine;
	private Label lblCombineArrow;
	private Label lblBuildArrow;
	private Label lblBuild;

	private IProject project;

	private ISelectionListener selectionListener;
	private Label lblProjectProgressField;
	private Label lblProjectNameField;
	private Label lblTransformArrow;
	private Label lblTransform;
	private Button btnImportExample;
	private Button btnGenerateAalappxml;

	public DashboardView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite container = new Composite(scrolledComposite, SWT.NONE);
		GridLayout gl_container = new GridLayout(7, false);
		gl_container.verticalSpacing = 0;
		gl_container.horizontalSpacing = 0;
		container.setLayout(gl_container);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		Composite canvas_1 = new Composite(container, SWT.BORDER);
		canvas_1.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		canvas_1.setBackground(SWTResourceManager.getColor(135, 206, 235));
		GridLayout gl_canvas_1 = new GridLayout(1, false);
		gl_canvas_1.verticalSpacing = 2;
		canvas_1.setLayout(gl_canvas_1);

		Label lblApplicationDesign = new Label(canvas_1, SWT.NONE);
		lblApplicationDesign.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		lblApplicationDesign.setBackground(SWTResourceManager.getColor(135, 206, 235));
		lblApplicationDesign.setText("Ontology Project");

		btnCreate = new Button(canvas_1, SWT.NONE);
		btnCreate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCreate.setText("Create");

		btnEdit = new Button(canvas_1, SWT.NONE);
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnEdit.setText("Edit");
		new Label(container, SWT.NONE);

		Composite universaalLogoComp = new Composite(container, SWT.BORDER);
		universaalLogoComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		universaalLogoComp.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		universaalLogoComp.setBackground(SWTResourceManager.getColor(30, 144, 255));
		universaalLogoComp.setLayout(new GridLayout(2, false));

		Label imgUniversaalLogo = new Label(universaalLogoComp, SWT.NONE);
		imgUniversaalLogo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		imgUniversaalLogo.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/universaals.jpg"));

		Label lblProjectName = new Label(universaalLogoComp, SWT.NONE);
		lblProjectName.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		lblProjectName.setText("Project Name:");
		lblProjectName.setBackground(SWTResourceManager.getColor(30, 144, 255));

		lblProjectNameField = new Label(universaalLogoComp, SWT.NONE);
		lblProjectNameField.setBackground(SWTResourceManager.getColor(30, 144, 255));
		lblProjectNameField.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		lblProjectNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblPercentComp = new Label(universaalLogoComp, SWT.NONE);
		lblPercentComp.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		lblPercentComp.setText("Percentage complete");
		lblPercentComp.setBackground(SWTResourceManager.getColor(30, 144, 255));

		lblProjectProgressField = new Label(universaalLogoComp, SWT.NONE);
		lblProjectProgressField.setBackground(SWTResourceManager.getColor(30, 144, 255));
		lblProjectProgressField.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		lblProjectProgressField.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));



		Composite projectDefCanvas = new Composite(container, SWT.BORDER);
		projectDefCanvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		projectDefCanvas.setBackground(SWTResourceManager.getColor(135, 206, 235));
		GridLayout gl_projectDefCanvas = new GridLayout(1, false);
		gl_projectDefCanvas.verticalSpacing = 2;
		projectDefCanvas.setLayout(gl_projectDefCanvas);

		Label lblProjectDefinition = new Label(projectDefCanvas, SWT.NONE);
		lblProjectDefinition.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		lblProjectDefinition.setBackground(SWTResourceManager.getColor(135, 206, 235));
		lblProjectDefinition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblProjectDefinition.setText("Project Definition");

		btnCreateProject = new Button(projectDefCanvas, SWT.NONE);
		btnCreateProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCreateProject.setGrayed(false);
		btnCreateProject.setEnabled(true);
		btnCreateProject.setText("Create Project");


		btnImportProject = new Button(projectDefCanvas, SWT.NONE);
		btnImportProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnImportProject.setText("Import Project");


		btnEditProject = new Button(projectDefCanvas, SWT.NONE);
		btnEditProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnEditProject.setText("Edit Project");


		btnImportExample = new Button(projectDefCanvas, SWT.NONE);
		btnImportExample.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnImportExample.setText("Import Example");

		Label label_4 = new Label(container, SWT.NONE);
		label_4.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrows.png"));

		Composite composite_5 = new Composite(container, SWT.NONE);
		composite_5.setLayout(new GridLayout(1, false));
		composite_5.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		Composite transformCanvas = new Composite(composite_5, SWT.NONE);
		transformCanvas.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		transformCanvas.setLayout(new GridLayout(1, false));

		lblTransformArrow = new Label(transformCanvas, SWT.NONE);
		lblTransformArrow.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTransformArrow.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrow_down_s.png"));

		lblTransform = new Label(transformCanvas, SWT.NONE);
		lblTransform.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblTransform.setText("Transform");

		Composite javaClassesCanvas = new Composite(composite_5, SWT.BORDER);
		javaClassesCanvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		javaClassesCanvas.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		GridLayout gl_javaClassesCanvas = new GridLayout(1, false);
		gl_javaClassesCanvas.verticalSpacing = 2;
		javaClassesCanvas.setLayout(gl_javaClassesCanvas);
		javaClassesCanvas.setBackground(SWTResourceManager.getColor(135, 206, 235));

		Label lblJavaClasses = new Label(javaClassesCanvas, SWT.NONE);
		lblJavaClasses.setBackground(SWTResourceManager.getColor(135, 206, 235));
		lblJavaClasses.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblJavaClasses.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		lblJavaClasses.setText("Java Classes");

		btnCreateClass = new Button(javaClassesCanvas, SWT.NONE);
		btnCreateClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCreateClass.setText("Create");

		btnImportClass = new Button(javaClassesCanvas, SWT.NONE);
		btnImportClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnImportClass.setText("Import");

		btnEditClass = new Button(javaClassesCanvas, SWT.NONE);
		btnEditClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnEditClass.setText("Edit");


		Composite btnBuildProject = new Composite(container, SWT.NONE);
		btnBuildProject.setLayout(new GridLayout(1, false));

		lblBuildArrow = new Label(btnBuildProject, SWT.NONE);
		lblBuildArrow.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblBuildArrow.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrows.png"));

		lblBuild = new Label(btnBuildProject, SWT.NONE);
		lblBuild.setText("Build");
		lblBuild.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));

		Composite composite_3 = new Composite(container, SWT.NONE);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_3.setLayout(new GridLayout(2, false));

		Composite confParCanvas = new Composite(composite_3, SWT.BORDER);
		confParCanvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		confParCanvas.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		confParCanvas.setBackground(SWTResourceManager.getColor(135, 206, 235));
		confParCanvas.setLayout(new GridLayout(1, false));

		Label lblConfigurationParameters = new Label(confParCanvas, SWT.NONE);
		lblConfigurationParameters.setBackground(SWTResourceManager.getColor(135, 206, 235));
		lblConfigurationParameters.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		lblConfigurationParameters.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblConfigurationParameters.setText("Configuration Parameters");
		
        btnExtractConfiguration = new Button(confParCanvas, SWT.NONE);
        btnExtractConfiguration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnExtractConfiguration.setText("Extract the Configuration");

		Label label_12 = new Label(composite_3, SWT.NONE);
		label_12.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label_12.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrow_curve_left_down_s.png"));

		Composite grpApplicationBinary = new Composite(composite_3, SWT.BORDER | SWT.SHADOW_IN);
		grpApplicationBinary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_grpApplicationBinary = new GridLayout(1, false);
		gl_grpApplicationBinary.verticalSpacing = 2;
		grpApplicationBinary.setLayout(gl_grpApplicationBinary);
		grpApplicationBinary.setBackground(SWTResourceManager.getColor(135, 206, 235));
		grpApplicationBinary.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));

		Label lblApplicationBinary = new Label(grpApplicationBinary, SWT.NONE);
		lblApplicationBinary.setBackground(SWTResourceManager.getColor(135, 206, 235));
		lblApplicationBinary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblApplicationBinary.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		lblApplicationBinary.setText("Application Binary");

		btnTestConformance = new Button(grpApplicationBinary, SWT.NONE);
		btnTestConformance.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnTestConformance.setText("Test Conformance");

		btnRun = new Button(grpApplicationBinary, SWT.NONE);
		btnRun.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnRun.setText("Run");

		btnDebug = new Button(grpApplicationBinary, SWT.NONE);
		btnDebug.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnDebug.setText("Debug");

		Composite btnCombineProject = new Composite(composite_3, SWT.NONE);
		btnCombineProject.setLayout(new GridLayout(1, false));

		lblCombineArrow = new Label(btnCombineProject, SWT.NONE);
		lblCombineArrow.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblCombineArrow.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrows.png"));

		lblCombine = new Label(btnCombineProject, SWT.NONE);
		lblCombine.setText("Combine");
		lblCombine.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));

		Composite applDescCanvas = new Composite(composite_3, SWT.NONE);
		applDescCanvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		applDescCanvas.setLayout(new GridLayout(1, false));
		applDescCanvas.setBackground(SWTResourceManager.getColor(135, 206, 235));
		applDescCanvas.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));

		Label lblApplicationDescription = new Label(applDescCanvas, SWT.NONE);
		lblApplicationDescription.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		lblApplicationDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblApplicationDescription.setBackground(SWTResourceManager.getColor(135, 206, 235));
		lblApplicationDescription.setText("Application Description");

		btnGenerateAalappxml = new Button(applDescCanvas, SWT.NONE);
		btnGenerateAalappxml.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnGenerateAalappxml.setText("Generate application description");

		Label label_11 = new Label(composite_3, SWT.NONE);
		label_11.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label_11.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrow_curve_left_s.png"));

		Composite publishableCanvas = new Composite(container, SWT.BORDER | SWT.SHADOW_OUT);
		GridLayout gl_publishableCanvas = new GridLayout(1, false);
		gl_publishableCanvas.verticalSpacing = 2;
		publishableCanvas.setLayout(gl_publishableCanvas);
		publishableCanvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		publishableCanvas.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		publishableCanvas.setBackground(SWTResourceManager.getColor(135, 206, 235));

		Label lblPublishableApplication = new Label(publishableCanvas, SWT.NONE);
		lblPublishableApplication.setBackground(SWTResourceManager.getColor(135, 206, 235));
		lblPublishableApplication.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		lblPublishableApplication.setText("Publishable Application");

		btnPublishUstore = new Button(publishableCanvas, SWT.NONE);
		btnPublishUstore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnPublishUstore.setText("Publish to uStore");

		btnUploadOpenSource = new Button(publishableCanvas, SWT.NONE);
		btnUploadOpenSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnUploadOpenSource.setText("Upload Open Source");

		Composite grpProjectDefinition = new Composite(container, SWT.NONE);
		grpProjectDefinition.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		grpProjectDefinition.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		grpProjectDefinition.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridLayout gl_grpProjectDefinition = new GridLayout(1, false);
		gl_grpProjectDefinition.marginWidth = 0;
		gl_grpProjectDefinition.marginHeight = 0;
		grpProjectDefinition.setLayout(gl_grpProjectDefinition);


		scrolledComposite.setContent(container);
		scrolledComposite.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));

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

		//Project definition
		btnCreateProject.addSelectionListener(new CreateNewProjectListener(this));
		btnImportProject.addSelectionListener(new TemporaryListener(this, "Import Project"));
		btnEditProject.addSelectionListener(new TemporaryListener(this, "Edit Project"));
		btnImportExample.addSelectionListener(new ImportExampleListener(this));

		//Java Classes
		btnCreateClass.addSelectionListener(new CreateNewItemListener(this));
		btnImportClass.addSelectionListener(new TemporaryListener(this, "Import Class"));
		btnEditClass.addSelectionListener(new TemporaryListener(this, "Edit Class"));

		//Application Binary
		btnTestConformance.addSelectionListener(new TemporaryListener(this, "Test Conformance"));
		btnRun.addSelectionListener(new RunProjectListener(this));
		btnDebug.addSelectionListener(new DebugProjectListener(this));

		//Project Description
		btnGenerateAalappxml.addSelectionListener(new GenerateXmlListener(this));

		//Publishable Application
		btnUploadOpenSource.addSelectionListener(new UploadOpenSourceListener(this));
		btnPublishUstore.addSelectionListener(new PublishProjectListener(this));

		//Application Design
		btnCreate.addSelectionListener(new CreateOntologyProjectListener(this));
		btnEdit.addSelectionListener(new TemporaryListener(this, "Edit"));

		//
		lblBuild.addMouseListener(new BuildProjectListener(this));
		lblCombine.addMouseListener(new TemporaryMouseListener(this, "Combine Project"));
		lblCombineArrow.addMouseListener(new TemporaryMouseListener(this, "Combine Project"));
		lblBuildArrow.addMouseListener(new BuildProjectListener(this));
		lblTransformArrow.addMouseListener(new TransformListener(this));
		lblTransform.addMouseListener(new TransformListener(this));
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
		selectionListener = new ProjectNameListener(this);
		getSite().getPage().addPostSelectionListener(selectionListener);
	}


	public void setProjectName(String name){
		this.lblProjectNameField.setText(name);
	}

	public void setCurrentProject(IProject input){
		this.project = input;
	}

	public IProject getCurrentProject(){
		return project;
	}
}
