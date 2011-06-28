package org.universaal.tools.dashboard.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.universaal.tools.dashboard.buttonlisteners.BuildProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.CreateNewItemListener;
import org.universaal.tools.dashboard.buttonlisteners.CreateNewProjectListener;
import org.universaal.tools.dashboard.buttonlisteners.ImportExampleListener;
import org.universaal.tools.dashboard.buttonlisteners.TemporaryListener;
import org.universaal.tools.dashboard.buttonlisteners.TemporaryMouseListener;
import org.universaal.tools.dashboard.listeners.ProjectNameListener;

import swing2swt.layout.FlowLayout;

public class DashboardView extends ViewPart {

	public static final String ID = "org.universaal.tools.views.DashboardView"; //$NON-NLS-1$
	private Button btnCreateProject;
	private Button btnImportProject;
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
	private Label lblBuildArrow;
	private Label lblBuild;
	
	private ISelectionListener selectionListener;
	private Label lblProjectProgressField;
	private Label lblProjectNameField;
	private Label lblTransformArrow;
	private Label lblTransform;
	private Button btnImportExample;

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
		projectDefCanvas.setBackground(SWTResourceManager.getColor(173, 216, 230));
		GridLayout gl_projectDefCanvas = new GridLayout(1, false);
		gl_projectDefCanvas.verticalSpacing = 0;
		projectDefCanvas.setLayout(gl_projectDefCanvas);
		FormData fd_canvas = new FormData();
		fd_canvas.top = new FormAttachment(0, 150);
		projectDefCanvas.setLayoutData(fd_canvas);
		
		Label lblProjectDefinition = new Label(projectDefCanvas, SWT.WRAP);
		GridData gd_lblProjectDefinition = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblProjectDefinition.widthHint = 71;
		lblProjectDefinition.setLayoutData(gd_lblProjectDefinition);
		lblProjectDefinition.setText("Project Definition");
		lblProjectDefinition.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblProjectDefinition.setBackground(SWTResourceManager.getColor(173, 216, 230));
		new Label(projectDefCanvas, SWT.NONE);
		
		btnCreateProject = new Button(projectDefCanvas, SWT.NONE);
		btnCreateProject.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_button = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 64;
		btnCreateProject.setLayoutData(gd_button);
		btnCreateProject.setText("Create");
		
		btnImportProject = new Button(projectDefCanvas, SWT.NONE);
		btnImportProject.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_button_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_1.widthHint = 59;
		btnImportProject.setLayoutData(gd_button_1);
		btnImportProject.setText("Import");
		
		btnEditProject = new Button(projectDefCanvas, SWT.NONE);
		btnEditProject.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_button_2 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_2.widthHint = 53;
		btnEditProject.setLayoutData(gd_button_2);
		btnEditProject.setText("Edit");
		
		Label arrrow1 = new Label(composite, SWT.NONE);
		arrrow1.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrows.png"));
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(projectDefCanvas, 35, SWT.TOP);
		fd_label_1.left = new FormAttachment(projectDefCanvas);
		arrrow1.setLayoutData(fd_label_1);
		
		Canvas javaClassesCanvas = new Canvas(composite, SWT.BORDER);
		javaClassesCanvas.setBackground(SWTResourceManager.getColor(173, 216, 230));
		GridLayout gl_javaClassesCanvas = new GridLayout(1, false);
		gl_javaClassesCanvas.verticalSpacing = 0;
		javaClassesCanvas.setLayout(gl_javaClassesCanvas);
		FormData fd_canvas_1 = new FormData();
		fd_canvas_1.bottom = new FormAttachment(projectDefCanvas, 0, SWT.BOTTOM);
		fd_canvas_1.top = new FormAttachment(projectDefCanvas, 0, SWT.TOP);
		fd_canvas_1.left = new FormAttachment(arrrow1);
		javaClassesCanvas.setLayoutData(fd_canvas_1);
		
		Label lblJavaClasses = new Label(javaClassesCanvas, SWT.WRAP);
		GridData gd_lblJavaClasses = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblJavaClasses.widthHint = 62;
		lblJavaClasses.setLayoutData(gd_lblJavaClasses);
		lblJavaClasses.setText("Java Classes");
		lblJavaClasses.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblJavaClasses.setBackground(SWTResourceManager.getColor(173, 216, 230));
		new Label(javaClassesCanvas, SWT.NONE);
		
		btnCreateClass = new Button(javaClassesCanvas, SWT.NONE);
		btnCreateClass.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_button_3 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_3.widthHint = 65;
		btnCreateClass.setLayoutData(gd_button_3);
		btnCreateClass.setText("Create");
		
		btnImportClass = new Button(javaClassesCanvas, SWT.NONE);
		btnImportClass.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_button_4 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_4.widthHint = 26;
		btnImportClass.setLayoutData(gd_button_4);
		btnImportClass.setText("Import");
		
		btnEditClass = new Button(javaClassesCanvas, SWT.NONE);
		btnEditClass.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_button_5 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_5.widthHint = 63;
		btnEditClass.setLayoutData(gd_button_5);
		btnEditClass.setText("Edit");
		
		Composite btnBuildProject = new Composite(composite, SWT.NONE);
		GridLayout gl_composite_1 = new GridLayout(1, false);
		gl_composite_1.verticalSpacing = 0;
		gl_composite_1.marginWidth = 0;
		btnBuildProject.setLayout(gl_composite_1);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.top = new FormAttachment(javaClassesCanvas, 30, SWT.TOP);
		fd_composite_1.left = new FormAttachment(javaClassesCanvas);
		btnBuildProject.setLayoutData(fd_composite_1);
		
		lblBuildArrow = new Label(btnBuildProject, SWT.NONE);
		GridData gd_label_3 = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		gd_label_3.heightHint = 23;
		lblBuildArrow.setLayoutData(gd_label_3);
		lblBuildArrow.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrows.png"));
		
		Canvas applicationBinCanvas = new Canvas(composite, SWT.BORDER);
		applicationBinCanvas.setBackground(SWTResourceManager.getColor(173, 216, 230));
		GridLayout gl_applicationBinCanvas = new GridLayout(1, false);
		gl_applicationBinCanvas.verticalSpacing = 0;
		applicationBinCanvas.setLayout(gl_applicationBinCanvas);
		FormData fd_canvas_2 = new FormData();
		fd_canvas_2.bottom = new FormAttachment(projectDefCanvas, 0, SWT.BOTTOM);
		fd_canvas_2.top = new FormAttachment(projectDefCanvas, 0, SWT.TOP);
		fd_canvas_2.height = 100;
		fd_canvas_2.left = new FormAttachment(btnBuildProject);
		
		lblBuild = new Label(btnBuildProject, SWT.CENTER);
		lblBuild.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblBuild.setFont(SWTResourceManager.getFont("Arial", 9, SWT.BOLD));
		lblBuild.setText("Build");
		applicationBinCanvas.setLayoutData(fd_canvas_2);
		
		Label lblApplicationBinary = new Label(applicationBinCanvas, SWT.WRAP);
		GridData gd_lblApplicationBinary = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblApplicationBinary.widthHint = 79;
		lblApplicationBinary.setLayoutData(gd_lblApplicationBinary);
		lblApplicationBinary.setText("Application Binary");
		lblApplicationBinary.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblApplicationBinary.setBackground(SWTResourceManager.getColor(173, 216, 230));
		new Label(applicationBinCanvas, SWT.NONE);
		
		btnTestConformance = new Button(applicationBinCanvas, SWT.NONE);
		btnTestConformance.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_button_6 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_button_6.widthHint = 112;
		btnTestConformance.setLayoutData(gd_button_6);
		btnTestConformance.setText("Test \r\nconformance");
		
		btnRun = new Button(applicationBinCanvas, SWT.NONE);
		btnRun.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_btnRun = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnRun.widthHint = 81;
		btnRun.setLayoutData(gd_btnRun);
		btnRun.setText("Run");
		
		btnDebug = new Button(applicationBinCanvas, SWT.NONE);
		btnDebug.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_btnDebug = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnDebug.widthHint = 71;
		btnDebug.setLayoutData(gd_btnDebug);
		btnDebug.setText("Debug");
		
		Composite btnCombineProject = new Composite(composite, SWT.NONE);
		GridLayout gl_composite_2 = new GridLayout(1, false);
		gl_composite_2.verticalSpacing = 0;
		gl_composite_2.marginWidth = 0;
		btnCombineProject.setLayout(gl_composite_2);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.top = new FormAttachment(applicationBinCanvas, 30, SWT.TOP);
		fd_composite_2.left = new FormAttachment(applicationBinCanvas);
		btnCombineProject.setLayoutData(fd_composite_2);
		
		lblCombineArrow = new Label(btnCombineProject, SWT.NONE);
		GridData gd_label_6 = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
		gd_label_6.heightHint = 19;
		lblCombineArrow.setLayoutData(gd_label_6);
		lblCombineArrow.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrows.png"));
		
		lblCombine = new Label(btnCombineProject, SWT.NONE);
		lblCombine.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblCombine.setText("Combine");
		lblCombine.setFont(SWTResourceManager.getFont("Arial", 9, SWT.BOLD));
		
		Canvas publishableCanvas = new Canvas(composite, SWT.BORDER);
		publishableCanvas.setBackground(SWTResourceManager.getColor(173, 216, 230));
		GridLayout gl_publishableCanvas = new GridLayout(1, false);
		gl_publishableCanvas.verticalSpacing = 0;
		publishableCanvas.setLayout(gl_publishableCanvas);
		FormData fd_canvas_3 = new FormData();
		fd_canvas_3.bottom = new FormAttachment(projectDefCanvas, 0, SWT.BOTTOM);
		fd_canvas_3.top = new FormAttachment(applicationBinCanvas, 0, SWT.TOP);
		fd_canvas_3.height = 100;
		fd_canvas_3.left = new FormAttachment(btnCombineProject);
		publishableCanvas.setLayoutData(fd_canvas_3);
		
		Label lblPublishableApplication = new Label(publishableCanvas, SWT.WRAP);
		GridData gd_lblPublishableApplication = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblPublishableApplication.widthHint = 85;
		lblPublishableApplication.setLayoutData(gd_lblPublishableApplication);
		lblPublishableApplication.setText("Publishable application");
		lblPublishableApplication.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblPublishableApplication.setBackground(SWTResourceManager.getColor(173, 216, 230));
		new Label(publishableCanvas, SWT.NONE);
		
		btnPublishUstore = new Button(publishableCanvas, SWT.NONE);
		btnPublishUstore.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_btnPublishUstore = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnPublishUstore.widthHint = 126;
		btnPublishUstore.setLayoutData(gd_btnPublishUstore);
		btnPublishUstore.setText("Publish to uStore");
		
		btnPublishOpenSource = new Button(publishableCanvas, SWT.NONE);
		btnPublishOpenSource.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		GridData gd_btnPublishOpenSource = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnPublishOpenSource.widthHint = 85;
		btnPublishOpenSource.setLayoutData(gd_btnPublishOpenSource);
		btnPublishOpenSource.setText("Publish open source");
		
		Canvas applDescCanvas = new Canvas(composite, SWT.BORDER);
		applDescCanvas.setBackground(SWTResourceManager.getColor(173, 216, 230));
		applDescCanvas.setLayout(new GridLayout(1, false));
		FormData fd_applDescCanvas = new FormData();
		fd_applDescCanvas.right = new FormAttachment(btnCombineProject);
		fd_applDescCanvas.left = new FormAttachment(applicationBinCanvas, 0, SWT.LEFT);
		fd_applDescCanvas.top = new FormAttachment(applicationBinCanvas, 6);
		applDescCanvas.setLayoutData(fd_applDescCanvas);
		
		Label lblApplicationDescription = new Label(applDescCanvas, SWT.WRAP | SWT.CENTER);
		GridData gd_lblApplicationDescription = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblApplicationDescription.widthHint = 111;
		lblApplicationDescription.setLayoutData(gd_lblApplicationDescription);
		lblApplicationDescription.setText("Application Description");
		lblApplicationDescription.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblApplicationDescription.setBackground(SWTResourceManager.getColor(173, 216, 230));
		
		Canvas confParCanvas = new Canvas(composite, SWT.BORDER);
		confParCanvas.setBackground(SWTResourceManager.getColor(173, 216, 230));
		confParCanvas.setLayout(new GridLayout(1, false));
		FormData fd_confParCanvas = new FormData();
		fd_confParCanvas.right = new FormAttachment(btnCombineProject);
		fd_confParCanvas.bottom = new FormAttachment(applicationBinCanvas, -6);
		fd_confParCanvas.left = new FormAttachment(applicationBinCanvas, 0, SWT.LEFT);
		confParCanvas.setLayoutData(fd_confParCanvas);
		
		Label lblConfigurationParameters = new Label(confParCanvas, SWT.WRAP | SWT.CENTER);
		GridData gd_lblConfigurationParameters = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lblConfigurationParameters.widthHint = 85;
		lblConfigurationParameters.setLayoutData(gd_lblConfigurationParameters);
		lblConfigurationParameters.setText("Configuration Parameters");
		lblConfigurationParameters.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblConfigurationParameters.setBackground(SWTResourceManager.getColor(173, 216, 230));
		
		Label label_11 = new Label(composite, SWT.NONE);
		label_11.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrow_curve_left_s.png"));
		label_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_label_11 = new FormData();
		fd_label_11.bottom = new FormAttachment(applDescCanvas, -20, SWT.BOTTOM);
		fd_label_11.right = new FormAttachment(btnCombineProject, -20, SWT.RIGHT);
		label_11.setLayoutData(fd_label_11);
		
		Label label_12 = new Label(composite, SWT.NONE);
		label_12.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrow_curve_left_down_s.png"));
		FormData fd_label_12 = new FormData();
		fd_label_12.right = new FormAttachment(btnCombineProject, -20, SWT.RIGHT);
		fd_label_12.top = new FormAttachment(confParCanvas, 20, SWT.TOP);
		label_12.setLayoutData(fd_label_12);
		
		Canvas canvas_6 = new Canvas(composite, SWT.BORDER);
		fd_canvas.left = new FormAttachment(0, 10);
		
		btnImportExample = new Button(projectDefCanvas, SWT.NONE);
		btnImportExample.setText("Import Example");
		canvas_6.setBackground(SWTResourceManager.getColor(100, 149, 237));
		GridLayout gl_canvas_6 = new GridLayout(1, false);
		gl_canvas_6.verticalSpacing = 0;
		canvas_6.setLayout(gl_canvas_6);
		FormData fd_canvas_6 = new FormData();
		fd_canvas_6.top = new FormAttachment(0, 10);
		fd_canvas_6.left = new FormAttachment(0, 400);
		canvas_6.setLayoutData(fd_canvas_6);
		
		Label label_13 = new Label(canvas_6, SWT.CENTER);
		label_13.setImage(SWTResourceManager.getImage("C:\\Users\\Adrian\\Pictures\\Icons\\universaals.jpg"));
		GridData gd_label_13 = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_label_13.widthHint = 231;
		gd_label_13.heightHint = 48;
		label_13.setLayoutData(gd_label_13);
		label_13.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_13.setBackground(SWTResourceManager.getColor(100, 149, 237));
		label_13.setAlignment(SWT.LEFT);
		
		Composite composite_1 = new Composite(canvas_6, SWT.NONE);
		composite_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		GridLayout gl_composite_3 = new GridLayout(2, false);
		gl_composite_3.marginHeight = 0;
		composite_1.setLayout(gl_composite_3);
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_composite_1.heightHint = 18;
		gd_composite_1.widthHint = 179;
		composite_1.setLayoutData(gd_composite_1);
		
		Label lblProjectName = new Label(composite_1, SWT.NONE);
		GridData gd_lblProjectName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblProjectName.heightHint = 22;
		lblProjectName.setLayoutData(gd_lblProjectName);
		lblProjectName.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblProjectName.setBackground(SWTResourceManager.getColor(100, 149, 237));
		lblProjectName.setText("Project Name: ");
		
		lblProjectNameField = new Label(composite_1, SWT.WRAP);
		lblProjectNameField.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblProjectNameField.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		lblProjectNameField.setText("no project selected");
		lblProjectNameField.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		
		Composite composite_2 = new Composite(canvas_6, SWT.NONE);
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		GridLayout gl_composite_4 = new GridLayout(2, false);
		gl_composite_4.marginHeight = 0;
		composite_2.setLayout(gl_composite_4);
		GridData gd_composite_2 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_composite_2.heightHint = 20;
		gd_composite_2.widthHint = 178;
		composite_2.setLayoutData(gd_composite_2);
		
		Label lblProjectProgress = new Label(composite_2, SWT.NONE);
		lblProjectProgress.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblProjectProgress.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		lblProjectProgress.setText("Project Progress:");
		
		lblProjectProgressField = new Label(composite_2, SWT.NONE);
		lblProjectProgressField.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
		lblProjectProgressField.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		
		Composite transformCanvas = new Composite(composite, SWT.NONE);
		GridLayout gl_transformCanvas = new GridLayout(1, false);
		gl_transformCanvas.verticalSpacing = 0;
		gl_transformCanvas.marginRight = 5;
		transformCanvas.setLayout(gl_transformCanvas);
		FormData fd_transformCanvas = new FormData();
		fd_transformCanvas.right = new FormAttachment(btnBuildProject);
		fd_transformCanvas.left = new FormAttachment(javaClassesCanvas, 0, SWT.LEFT);
		fd_transformCanvas.bottom = new FormAttachment(javaClassesCanvas);
		transformCanvas.setLayoutData(fd_transformCanvas);
		
		Canvas canvas_1 = new Canvas(composite, SWT.BORDER);
		canvas_1.setBackground(SWTResourceManager.getColor(173, 216, 230));
		FormData fd_canvas_8;
		GridLayout gl_canvas_1 = new GridLayout(1, false);
		gl_canvas_1.verticalSpacing = 0;
		canvas_1.setLayout(gl_canvas_1);
		fd_canvas_8 = new FormData();
		fd_canvas_8.bottom = new FormAttachment(transformCanvas);
		fd_canvas_8.left = new FormAttachment(javaClassesCanvas, 0, SWT.LEFT);
		canvas_1.setLayoutData(fd_canvas_8);
		
		Label lblApplicationDesign = new Label(canvas_1, SWT.WRAP);
		lblApplicationDesign.setBackground(SWTResourceManager.getColor(173, 216, 230));
		lblApplicationDesign.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		GridData gd_lblApplicationDesign = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblApplicationDesign.widthHint = 79;
		lblApplicationDesign.setLayoutData(gd_lblApplicationDesign);
		lblApplicationDesign.setText("Application Design");
		
		btnCreate = new Button(canvas_1, SWT.NONE);
		btnCreate.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		btnCreate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCreate.setText("Create");
		
		btnEdit = new Button(canvas_1, SWT.NONE);
		btnEdit.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEdit.setText("Edit");
		
		
		
		lblTransformArrow = new Label(transformCanvas, SWT.NONE);
		lblTransformArrow.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTransformArrow.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/arrow_down_s.png"));
		
		lblTransform = new Label(transformCanvas, SWT.NONE);
		lblTransform.setAlignment(SWT.CENTER);
		GridData gd_lblTransform = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblTransform.widthHint = 67;
		lblTransform.setLayoutData(gd_lblTransform);
		lblTransform.setFont(SWTResourceManager.getFont("Arial", 9, SWT.BOLD));
		lblTransform.setText("Transform");
		
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		createActions();
		initializeToolBar();
		initializeMenu();
		hookPageSelection();
		
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
		//Add button-listeners
		
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
		btnRun.addSelectionListener(new TemporaryListener(this, "Run program"));
		btnDebug.addSelectionListener(new TemporaryListener(this, "Debug"));
		
		//Publishable Application
		btnPublishOpenSource.addSelectionListener(new TemporaryListener(this, "Publish Open Source"));
		btnPublishUstore.addSelectionListener(new TemporaryListener(this, "Publis uStore"));
		
		//Application Design
		btnCreate.addSelectionListener(new TemporaryListener(this, "Create"));
		btnEdit.addSelectionListener(new TemporaryListener(this, "Edit"));
		
		//
		lblBuild.addMouseListener(new BuildProjectListener(this));
		lblCombine.addMouseListener(new TemporaryMouseListener(this, "Combine Project"));
		lblCombineArrow.addMouseListener(new TemporaryMouseListener(this, "Combine Project"));
		lblBuildArrow.addMouseListener(new BuildProjectListener(this));
		lblTransformArrow.addMouseListener(new TemporaryMouseListener(this, "Transform"));
		lblTransform.addMouseListener(new TemporaryMouseListener(this, "Transform"));
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
}
