package org.universaal.tools.dashboard.views.old;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
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
import org.universaal.tools.dashboard.buttonlisteners.CommandCallingListener;
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
import org.universaal.tools.dashboard.views.DashboardView;



public class OldOntologyView extends Composite {

	private Button btnImportProject;
	private Button btnCreateClass;
	private Button btnImportClass;
	private Button btnTestConformance;
	private Button btnRun;
	private Button btnDebug;
	private Button btnUploadOpenSource;
	private Button btnPublishUstore;
	private Button btnCreate;
	
	private Button btnBuild;
	private Button btnTransform;
	private Label lblCombineArrow;
	private Label lblBuildArrow;

	private IProject project;

	private ISelectionListener selectionListener;
	private Label lblProjectNameField;
	private Button btnImportExample;
	private Button btnGenerateAalappxml;
	
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OldOntologyView(Composite parent, int style) {
		super(parent, style);
		
//		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
//		scrolledComposite.setExpandHorizontal(true);
//		scrolledComposite.setExpandVertical(true);

		Composite container = new Composite(parent, SWT.NONE);
				container.setLayout(new GridLayout(7, false));
		
				Composite universaalLogoComp = new Composite(container, SWT.BORDER);
				GridData gd_universaalLogoComp = new GridData(SWT.FILL, SWT.CENTER, false, false, 7, 1);
				gd_universaalLogoComp.widthHint = 839;
				universaalLogoComp.setLayoutData(gd_universaalLogoComp);
				universaalLogoComp.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
				universaalLogoComp.setBackground(SWTResourceManager.getColor(30, 144, 255));
				universaalLogoComp.setLayout(new GridLayout(3, false));
				
						Label lblProjectName = new Label(universaalLogoComp, SWT.NONE);
						lblProjectName.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
						lblProjectName.setText("Project Name:");
						lblProjectName.setBackground(SWTResourceManager.getColor(30, 144, 255));
						
								lblProjectNameField = new Label(universaalLogoComp, SWT.NONE);
								lblProjectNameField.setBackground(SWTResourceManager.getColor(30, 144, 255));
								lblProjectNameField.setFont(SWTResourceManager.getFont("Arial", 10, SWT.NORMAL));
								GridData gd_lblProjectNameField = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
								gd_lblProjectNameField.widthHint = 488;
								lblProjectNameField.setLayoutData(gd_lblProjectNameField);
										
												Label imgUniversaalLogo = new Label(universaalLogoComp, SWT.NONE);
												GridData gd_imgUniversaalLogo = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
												gd_imgUniversaalLogo.widthHint = 253;
												imgUniversaalLogo.setLayoutData(gd_imgUniversaalLogo);
												imgUniversaalLogo.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/universaals.jpg"));



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
		
				btnCreate = new Button(projectDefCanvas, SWT.NONE);
				btnCreate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				btnCreate.setText("Create");


		btnImportProject = new Button(projectDefCanvas, SWT.NONE);
		btnImportProject.setEnabled(false);
		btnImportProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnImportProject.setText("Import Project");


		btnImportExample = new Button(projectDefCanvas, SWT.NONE);
		btnImportExample.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnImportExample.setText("Import Example");
		
		Composite composite = new Composite(container, SWT.NONE);
				composite.setLayout(new GridLayout(1, false));
		
				Label label_4 = new Label(composite, SWT.NONE);
				label_4.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
				label_4.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/go-next.png"));
				
				btnTransform = new Button(composite, SWT.NONE);
				btnTransform.setImage(null);
				btnTransform.setText("Transform");

		Composite composite_5 = new Composite(container, SWT.NONE);
		composite_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_5.setLayout(new GridLayout(1, false));

		Composite javaClassesCanvas = new Composite(composite_5, SWT.BORDER);
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
		btnImportClass.setEnabled(false);
		btnImportClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnImportClass.setText("Import");


		Composite btnBuildProject = new Composite(container, SWT.NONE);
		btnBuildProject.setLayout(new GridLayout(1, false));

		lblBuildArrow = new Label(btnBuildProject, SWT.NONE);
		lblBuildArrow.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblBuildArrow.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/go-next.png"));
		
		btnBuild = new Button(btnBuildProject, SWT.NONE);
		btnBuild.setText("Build");

		Composite composite_3 = new Composite(container, SWT.NONE);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_3.setLayout(new GridLayout(1, false));

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
		btnTestConformance.setEnabled(false);
		btnTestConformance.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnTestConformance.setText("Test Conformance");

		btnRun = new Button(grpApplicationBinary, SWT.NONE);
		btnRun.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnRun.setText("Run");

		btnDebug = new Button(grpApplicationBinary, SWT.NONE);
		btnDebug.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnDebug.setText("Debug");

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
		btnGenerateAalappxml.setText("Create and edit");
		
				Composite btnCombineProject = new Composite(container, SWT.NONE);
				btnCombineProject.setLayout(new GridLayout(1, false));
				
						lblCombineArrow = new Label(btnCombineProject, SWT.NONE);
						lblCombineArrow.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
						lblCombineArrow.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/go-next.png"));
						
						Button btnCombine = new Button(btnCombineProject, SWT.NONE);
						btnCombine.setText("Combine");

		Composite publishableCanvas = new Composite(container, SWT.BORDER | SWT.SHADOW_OUT);
		publishableCanvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_publishableCanvas = new GridLayout(1, false);
		gl_publishableCanvas.verticalSpacing = 2;
		publishableCanvas.setLayout(gl_publishableCanvas);
		publishableCanvas.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		publishableCanvas.setBackground(SWTResourceManager.getColor(135, 206, 235));

		Label lblPublishableApplication = new Label(publishableCanvas, SWT.NONE);
		lblPublishableApplication.setBackground(SWTResourceManager.getColor(135, 206, 235));
		lblPublishableApplication.setFont(SWTResourceManager.getFont("Arial", 12, SWT.BOLD));
		lblPublishableApplication.setText("Publishable Ontology");

		btnPublishUstore = new Button(publishableCanvas, SWT.NONE);
		btnPublishUstore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnPublishUstore.setText("Publish to uStore");

		btnUploadOpenSource = new Button(publishableCanvas, SWT.NONE);
		btnUploadOpenSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnUploadOpenSource.setText("Upload Open Source");


//		scrolledComposite.setContent(container);
//		scrolledComposite.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		//createActions();

		//hookPageSelection();				
		
	}

	

	DashboardView part;
	
	protected DashboardView getPart() {
		return part;
	}
	
	protected void setPart(DashboardView part) {
		this.part = part;
	}
		
	
	private void addCommandCallingListener(Button btn, String commandID, String featureName) {
		btn.addSelectionListener(new CommandCallingListener(getPart(), commandID, featureName));		
	}
	
	/**
	 * Use this method to create all the buttonlisteners, and assign them to 
	 * their respective buttons.
	 */
	private void createActions() {
		btnImportProject.addSelectionListener(new TemporaryListener(getPart(), "Import Project"));
		//btnImportExample.addSelectionListener(new ImportExampleListener(this));
		addCommandCallingListener(btnImportExample,"org.universaal.importexternalproject.commands.importexample", "AAL Studio integration with Developer Depot");

		//Java Classes
		//btnCreateClass.addSelectionListener(new CreateNewItemListener(this));
		addCommandCallingListener(btnCreateClass,"org.universaal.tools.newwizard.plugin.command.startNewItemWizard", "AAL Studio Project Wizards");
		btnImportClass.addSelectionListener(new TemporaryListener(getPart(), "Import Class"));

		//Application Binary
		btnTestConformance.addSelectionListener(new TemporaryListener(getPart(), "Test Conformance"));
		//btnRun.addSelectionListener(new RunProjectListener(this));
		addCommandCallingListener(btnRun,"org.universaal.tools.buildserviceapplication.actions.RunAction", "AAL Studio Build");
		//btnDebug.addSelectionListener(new DebugProjectListener(this));
		addCommandCallingListener(btnDebug,"org.universaal.tools.buildserviceapplication.actions.DebugAction", "AAL Studio Build");

		//Project Description
		addCommandCallingListener(btnGenerateAalappxml,"org.universaal.tools.uploadopensourceplugin.generateaalapp", "AAL Studio integration with Developer Depot");

		//Publishable Application
		//btnUploadOpenSource.addSelectionListener(new UploadOpenSourceListener(this));
		addCommandCallingListener(btnUploadOpenSource,"org.universaal.tools.uploadopensourceplugin.commands.uploadopensource", "AAL Studio integration with Developer Depot");
		//btnPublishUstore.addSelectionListener(new PublishProjectListener(this));
		addCommandCallingListener(btnPublishUstore,"org.universaal.tools.buildserviceapplication.actions.PublishAction", "AAL Studio Build");

		//Application Design
		//btnCreate.addSelectionListener(new CreateOntologyProjectListener(this));
		addCommandCallingListener(btnCreate,"org.universaal.tools.modelling.ontology.wizard.commands.newOntologyProject", "AAL Studio Modelling Support");


		
		// Transitions
		addCommandCallingListener(btnBuild,"org.universaal.tools.buildserviceapplication.actions.BuildAction", "AAL Studio Build");
		addCommandCallingListener(btnTransform,"org.universaal.tools.transformationcommand.commands.ontUML2Java", "AAL Studio Transformations");
		
		
		//
		//lblBuild.addMouseListener(new BuildProjectListener(this));
		//lblCombine.addMouseListener(new TemporaryMouseListener(this, "Combine Project"));
		//lblCombineArrow.addMouseListener(new TemporaryMouseListener(this, "Combine Project"));
		//lblBuildArrow.addMouseListener(new BuildProjectListener(this));
		//lblTransformArrow.addMouseListener(new TransformListener(this));
		//lblTransform.addMouseListener(new TransformListener(this));
	}

	
	
	@Override
	public void dispose(){
		super.dispose();
		ResourceManager.dispose();
		SWTResourceManager.dispose();
		if(selectionListener!=null){
			getPart().getSite().getPage().removePostSelectionListener(selectionListener);
		}
	}

	/**
	 * Add selectionListener for updating Project Name
	 */
/*	private void hookPageSelection(){
		selectionListener = new ProjectElementSelectionListener(getPart());
		getPart().getSite().getPage().addPostSelectionListener(selectionListener);
	}
*/

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
