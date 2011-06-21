package org.universaal.tools.dashboard.views;

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
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.wb.swt.ResourceManager;
import org.universaal.tools.dashboard.buttonlisteners.CreateNewItemListener;
import org.universaal.tools.dashboard.buttonlisteners.CreateNewProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.TemporaryListener;

public class DashboardView extends ViewPart {

	public static final String ID = "sintef.dashboard.views.DashboardView"; //$NON-NLS-1$
	private Label btnTransform;
	private Button btnCreateProject;
	private Button btnCreateItem;
	private Button btnEditProject;
	private Button btnCreateClass;
	private Button btnImportClass;
	private Button btnEditClass;
	private Button btnTestConformance;
	private Button btnRun;
	private Button btnDebug;
	private Button btnPublishOpenSource;
	private Button btnPublishUstore;
	private Button btnCreate;
	private Button btnEdit;
	private Label lblCombine;
	private Label lblCombineArrow;
	private Label lblBuild;
	private Label lblBuildArrow;

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
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		Canvas projectDefCanvas = new Canvas(composite, SWT.BORDER);
		projectDefCanvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		projectDefCanvas.setLayout(new GridLayout(1, false));
		FormData fd_canvas = new FormData();
		fd_canvas.top = new FormAttachment(0, 120);
		projectDefCanvas.setLayoutData(fd_canvas);
		
		Label lblProjectDefinition = new Label(projectDefCanvas, SWT.WRAP);
		GridData gd_lblProjectDefinition = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblProjectDefinition.widthHint = 66;
		lblProjectDefinition.setLayoutData(gd_lblProjectDefinition);
		lblProjectDefinition.setText("Project Definition");
		lblProjectDefinition.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblProjectDefinition.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		new Label(projectDefCanvas, SWT.NONE);
		
		btnCreateProject = new Button(projectDefCanvas, SWT.NONE);
		GridData gd_button = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 84;
		btnCreateProject.setLayoutData(gd_button);
		btnCreateProject.setText("Create Project");
		
		btnCreateItem = new Button(projectDefCanvas, SWT.NONE);
		GridData gd_button_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_1.widthHint = 59;
		btnCreateItem.setLayoutData(gd_button_1);
		btnCreateItem.setText("Create Item");
		
		btnEditProject = new Button(projectDefCanvas, SWT.NONE);
		GridData gd_button_2 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_2.widthHint = 53;
		btnEditProject.setLayoutData(gd_button_2);
		btnEditProject.setText("Edit");
		
		Label arrrow1 = new Label(composite, SWT.NONE);
		arrrow1.setImage(org.eclipse.wb.swt.ResourceManager.getPluginImage("Sintef.dashboard", "icons/pil2.png"));
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(projectDefCanvas, 50, SWT.TOP);
		fd_label_1.left = new FormAttachment(projectDefCanvas);
		arrrow1.setLayoutData(fd_label_1);
		
		Canvas javaClassesCanvas = new Canvas(composite, SWT.BORDER);
		javaClassesCanvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		javaClassesCanvas.setLayout(new GridLayout(1, false));
		FormData fd_canvas_1 = new FormData();
		fd_canvas_1.top = new FormAttachment(projectDefCanvas, 0, SWT.TOP);
		fd_canvas_1.left = new FormAttachment(arrrow1);
		javaClassesCanvas.setLayoutData(fd_canvas_1);
		
		Label lblJavaClasses = new Label(javaClassesCanvas, SWT.WRAP);
		GridData gd_lblJavaClasses = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblJavaClasses.widthHint = 77;
		lblJavaClasses.setLayoutData(gd_lblJavaClasses);
		lblJavaClasses.setText("Java Classes");
		lblJavaClasses.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblJavaClasses.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		new Label(javaClassesCanvas, SWT.NONE);
		
		btnCreateClass = new Button(javaClassesCanvas, SWT.NONE);
		GridData gd_button_3 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_3.widthHint = 43;
		btnCreateClass.setLayoutData(gd_button_3);
		btnCreateClass.setText("Create");
		
		btnImportClass = new Button(javaClassesCanvas, SWT.NONE);
		GridData gd_button_4 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_4.widthHint = 26;
		btnImportClass.setLayoutData(gd_button_4);
		btnImportClass.setText("Import");
		
		btnEditClass = new Button(javaClassesCanvas, SWT.NONE);
		GridData gd_button_5 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_5.widthHint = 63;
		btnEditClass.setLayoutData(gd_button_5);
		btnEditClass.setText("Edit");
		
		Composite btnBuildProject = new Composite(composite, SWT.NONE);
		GridLayout gl_composite_1 = new GridLayout(1, false);
		gl_composite_1.marginWidth = 0;
		btnBuildProject.setLayout(gl_composite_1);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.top = new FormAttachment(projectDefCanvas, 45, SWT.TOP);
		fd_composite_1.left = new FormAttachment(javaClassesCanvas);
		btnBuildProject.setLayoutData(fd_composite_1);
		
		lblBuildArrow = new Label(btnBuildProject, SWT.NONE);
		GridData gd_label_3 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_label_3.heightHint = 51;
		lblBuildArrow.setLayoutData(gd_label_3);
		lblBuildArrow.setImage(org.eclipse.wb.swt.ResourceManager.getPluginImage("Sintef.dashboard", "icons/pil2.png"));
		
		lblBuild = new Label(btnBuildProject, SWT.NONE);
		lblBuild.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblBuild.setText("Build");
		lblBuild.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblBuild.setAlignment(SWT.CENTER);
		
		Canvas applicationBinCanvas = new Canvas(composite, SWT.BORDER);
		applicationBinCanvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		applicationBinCanvas.setLayout(new GridLayout(1, false));
		FormData fd_canvas_2 = new FormData();
		fd_canvas_2.top = new FormAttachment(projectDefCanvas, 0, SWT.TOP);
		fd_canvas_2.left = new FormAttachment(btnBuildProject);
		applicationBinCanvas.setLayoutData(fd_canvas_2);
		
		Label lblApplicationBinary = new Label(applicationBinCanvas, SWT.WRAP);
		GridData gd_lblApplicationBinary = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblApplicationBinary.widthHint = 79;
		lblApplicationBinary.setLayoutData(gd_lblApplicationBinary);
		lblApplicationBinary.setText("Application Binary");
		lblApplicationBinary.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblApplicationBinary.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		new Label(applicationBinCanvas, SWT.NONE);
		
		btnTestConformance = new Button(applicationBinCanvas, SWT.NONE);
		GridData gd_button_6 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_6.widthHint = 103;
		btnTestConformance.setLayoutData(gd_button_6);
		btnTestConformance.setText("Test \r\nconformance");
		
		btnRun = new Button(applicationBinCanvas, SWT.NONE);
		GridData gd_btnRun = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnRun.widthHint = 81;
		btnRun.setLayoutData(gd_btnRun);
		btnRun.setText("Run");
		
		btnDebug = new Button(applicationBinCanvas, SWT.NONE);
		GridData gd_btnDebug = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnDebug.widthHint = 71;
		btnDebug.setLayoutData(gd_btnDebug);
		btnDebug.setText("Debug");
		
		Composite btnCombineProject = new Composite(composite, SWT.NONE);
		GridLayout gl_composite_2 = new GridLayout(1, false);
		gl_composite_2.marginWidth = 0;
		btnCombineProject.setLayout(gl_composite_2);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.top = new FormAttachment(projectDefCanvas, 45, SWT.TOP);
		fd_composite_2.left = new FormAttachment(applicationBinCanvas);
		btnCombineProject.setLayoutData(fd_composite_2);
		
		lblCombineArrow = new Label(btnCombineProject, SWT.NONE);
		GridData gd_label_6 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_label_6.heightHint = 52;
		lblCombineArrow.setLayoutData(gd_label_6);
		lblCombineArrow.setImage(org.eclipse.wb.swt.ResourceManager.getPluginImage("Sintef.dashboard", "icons/pil2.png"));
		
		lblCombine = new Label(btnCombineProject, SWT.NONE);
		lblCombine.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblCombine.setText("Combine");
		lblCombine.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		
		Canvas publishableCanvas = new Canvas(composite, SWT.BORDER);
		publishableCanvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		publishableCanvas.setLayout(new GridLayout(1, false));
		FormData fd_canvas_3 = new FormData();
		fd_canvas_3.top = new FormAttachment(projectDefCanvas, 0, SWT.TOP);
		fd_canvas_3.left = new FormAttachment(btnCombineProject);
		publishableCanvas.setLayoutData(fd_canvas_3);
		
		Label lblPublishableApplication = new Label(publishableCanvas, SWT.WRAP);
		GridData gd_lblPublishableApplication = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblPublishableApplication.widthHint = 100;
		lblPublishableApplication.setLayoutData(gd_lblPublishableApplication);
		lblPublishableApplication.setText("Publishable application");
		lblPublishableApplication.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblPublishableApplication.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		new Label(publishableCanvas, SWT.NONE);
		
		btnPublishUstore = new Button(publishableCanvas, SWT.NONE);
		GridData gd_btnPublishUstore = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnPublishUstore.widthHint = 114;
		btnPublishUstore.setLayoutData(gd_btnPublishUstore);
		btnPublishUstore.setText("Publish uStore");
		
		btnPublishOpenSource = new Button(publishableCanvas, SWT.NONE);
		GridData gd_btnPublishOpenSource = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnPublishOpenSource.widthHint = 114;
		btnPublishOpenSource.setLayoutData(gd_btnPublishOpenSource);
		btnPublishOpenSource.setText("Publish open source");
		
		Canvas applDescCanvas = new Canvas(composite, SWT.BORDER);
		applDescCanvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		applDescCanvas.setLayout(new GridLayout(1, false));
		FormData fd_applDescCanvas = new FormData();
		fd_applDescCanvas.top = new FormAttachment(applicationBinCanvas, 6);
		fd_applDescCanvas.right = new FormAttachment(applicationBinCanvas, 0, SWT.RIGHT);
		applDescCanvas.setLayoutData(fd_applDescCanvas);
		
		Label lblApplicationDescription = new Label(applDescCanvas, SWT.WRAP | SWT.CENTER);
		GridData gd_lblApplicationDescription = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblApplicationDescription.widthHint = 103;
		lblApplicationDescription.setLayoutData(gd_lblApplicationDescription);
		lblApplicationDescription.setText("Application Description");
		lblApplicationDescription.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblApplicationDescription.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		
		Canvas confParCanvas = new Canvas(composite, SWT.BORDER);
		confParCanvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		confParCanvas.setLayout(new GridLayout(1, false));
		FormData fd_confParCanvas = new FormData();
		fd_confParCanvas.bottom = new FormAttachment(applicationBinCanvas, -6);
		fd_confParCanvas.left = new FormAttachment(applicationBinCanvas, 0, SWT.LEFT);
		confParCanvas.setLayoutData(fd_confParCanvas);
		
		Label lblConfigurationParameters = new Label(confParCanvas, SWT.WRAP | SWT.CENTER);
		GridData gd_lblConfigurationParameters = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lblConfigurationParameters.widthHint = 103;
		lblConfigurationParameters.setLayoutData(gd_lblConfigurationParameters);
		lblConfigurationParameters.setText("Configuration Parameters");
		lblConfigurationParameters.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblConfigurationParameters.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		
		Label label_11 = new Label(composite, SWT.NONE);
		label_11.setImage(org.eclipse.wb.swt.ResourceManager.getPluginImage("Sintef.dashboard", "icons/pilopp.png"));
		label_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_label_11 = new FormData();
		fd_label_11.bottom = new FormAttachment(applDescCanvas, 0, SWT.BOTTOM);
		fd_label_11.left = new FormAttachment(applDescCanvas);
		label_11.setLayoutData(fd_label_11);
		
		Label label_12 = new Label(composite, SWT.NONE);
		label_12.setImage(ResourceManager.getPluginImage("Sintef.dashboard", "icons/pilned.png"));
		FormData fd_label_12 = new FormData();
		fd_label_12.top = new FormAttachment(confParCanvas, 0, SWT.TOP);
		fd_label_12.right = new FormAttachment(btnCombineProject, 0, SWT.RIGHT);
		label_12.setLayoutData(fd_label_12);
		
		Canvas canvas_6 = new Canvas(composite, SWT.BORDER);
		fd_canvas.left = new FormAttachment(0, 10);
		canvas_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		canvas_6.setLayout(new GridLayout(1, false));
		FormData fd_canvas_6 = new FormData();
		fd_canvas_6.top = new FormAttachment(0, 10);
		fd_canvas_6.left = new FormAttachment(0, 10);
		canvas_6.setLayoutData(fd_canvas_6);
		
		Label label_13 = new Label(canvas_6, SWT.CENTER);
		label_13.setImage(SWTResourceManager.getImage("C:\\Users\\Adrian\\Pictures\\Icons\\universaals.jpg"));
		GridData gd_label_13 = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_label_13.widthHint = 178;
		gd_label_13.heightHint = 48;
		label_13.setLayoutData(gd_label_13);
		label_13.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_13.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		label_13.setAlignment(SWT.CENTER);
		
		Label lblProjectProgress = new Label(canvas_6, SWT.NONE);
		lblProjectProgress.setText("Project Progress");
		lblProjectProgress.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblProjectProgress.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		
		Label lblProjectPercentage = new Label(canvas_6, SWT.NONE);
		lblProjectPercentage.setText("0%");
		lblProjectPercentage.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		
		btnTransform = new Label(composite, SWT.NONE);
		btnTransform.setImage(SWTResourceManager.getImage("C:\\Users\\Adrian\\Pictures\\Icons\\pilopp.png"));
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(javaClassesCanvas, 12, SWT.LEFT);
		fd_label.top = new FormAttachment(javaClassesCanvas);
		btnTransform.setLayoutData(fd_label);
		
		Canvas canvas_1 = new Canvas(composite, SWT.BORDER);
		canvas_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		FormData fd_canvas_8;
		canvas_1.setLayout(new GridLayout(1, false));
		fd_canvas_8 = new FormData();
		fd_canvas_8.bottom = new FormAttachment(btnTransform, 133, SWT.BOTTOM);
		fd_canvas_8.top = new FormAttachment(btnTransform, 5);
		fd_canvas_8.left = new FormAttachment(javaClassesCanvas, 0, SWT.LEFT);
		fd_canvas_8.right = new FormAttachment(javaClassesCanvas, 0, SWT.RIGHT);
		canvas_1.setLayoutData(fd_canvas_8);
		
		Label lblApplicationDesign = new Label(canvas_1, SWT.WRAP);
		lblApplicationDesign.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		lblApplicationDesign.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		GridData gd_lblApplicationDesign = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblApplicationDesign.widthHint = 79;
		lblApplicationDesign.setLayoutData(gd_lblApplicationDesign);
		lblApplicationDesign.setText("Application Design");
		new Label(canvas_1, SWT.NONE);
		
		btnCreate = new Button(canvas_1, SWT.NONE);
		btnCreate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCreate.setText("Create");
		
		btnEdit = new Button(canvas_1, SWT.NONE);
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEdit.setText("Edit");
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setImage(org.eclipse.wb.swt.ResourceManager.getPluginImage("Sintef.dashboard", "icons/pil2.png"));
		FormData fd_label_2 = new FormData();
		fd_label_2.top = new FormAttachment(arrrow1, 80);
		fd_label_2.right = new FormAttachment(arrrow1, 0, SWT.RIGHT);
		label_1.setLayoutData(fd_label_2);
		
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setImage(org.eclipse.wb.swt.ResourceManager.getPluginImage("Sintef.dashboard", "icons/pilned.png"));
		FormData fd_label_3 = new FormData();
		fd_label_3.top = new FormAttachment(projectDefCanvas);
		fd_label_3.right = new FormAttachment(projectDefCanvas, 0, SWT.RIGHT);
		label_2.setLayoutData(fd_label_3);
		
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));


		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
		//Add button-listeners
		
		//Project definition
		btnCreateProject.addMouseListener(new CreateNewProjectListener(this));
		btnCreateItem.addMouseListener(new CreateNewItemListener(this));
		btnEditProject.addMouseListener(new TemporaryListener(this, "Edit Project"));
		
		//Java Classes
		btnCreateClass.addMouseListener(new TemporaryListener(this, "Create Class"));
		btnImportClass.addMouseListener(new TemporaryListener(this, "Import Class"));
		btnEditClass.addMouseListener(new TemporaryListener(this, "Edit Class"));
		
		//Application Binary
		btnTestConformance.addMouseListener(new TemporaryListener(this, "Test Conformance"));
		btnRun.addMouseListener(new TemporaryListener(this, "Run program"));
		btnDebug.addMouseListener(new TemporaryListener(this, "Debug"));
		
		//Publishable Application
		btnPublishOpenSource.addMouseListener(new TemporaryListener(this, "Publish Open Source"));
		btnPublishUstore.addMouseListener(new TemporaryListener(this, "Publis uStore"));
		
		//Application Design
		btnCreate.addMouseListener(new TemporaryListener(this, "Create"));
		btnEdit.addMouseListener(new TemporaryListener(this, "Edit"));
		
		//
		btnTransform.addMouseListener(new TemporaryListener(this, "Transform"));
		lblCombine.addMouseListener(new TemporaryListener(this, "Combine Project"));
		lblCombineArrow.addMouseListener(new TemporaryListener(this, "Combine Project"));
		lblBuild.addMouseListener(new TemporaryListener(this, "Build Project"));
		lblBuildArrow.addMouseListener(new TemporaryListener(this, "Build Project"));
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
}
