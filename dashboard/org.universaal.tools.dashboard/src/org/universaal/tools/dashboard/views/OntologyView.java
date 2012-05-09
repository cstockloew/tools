package org.universaal.tools.dashboard.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.universaal.tools.dashboard.buttonlisteners.CommandCallingListener;
import org.universaal.tools.dashboard.buttonlisteners.TemporaryListener;

public class OntologyView extends Composite {

	Button btnCreate;
	Button btnImportProject;
	Button btnImportExample;
	Button btnTransform;
	Button btnCreateClass;
	Button btnBuild;
	Button btnTestConformance;
	Button btnCombine;
	Button btnPublishOpenSource;
	Button btnCreateAndEdit;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OntologyView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(7, false));
		
		Group grpProject = new Group(this, SWT.NONE);
		grpProject.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.BOLD));
		grpProject.setBackground(SWTResourceManager.getColor(135, 206, 250));
		grpProject.setLayout(new FillLayout(SWT.VERTICAL));
		grpProject.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		grpProject.setText("Project");
		
		btnCreate = new Button(grpProject, SWT.NONE);
		btnCreate.setText("Create");
		
		btnImportProject = new Button(grpProject, SWT.NONE);
		btnImportProject.setEnabled(false);
		btnImportProject.setText("Import Project");
		
		btnImportExample = new Button(grpProject, SWT.NONE);
		btnImportExample.setText("Import Example");
		
		Composite composite = new Composite(this, SWT.NONE);
		RowLayout rl_composite = new RowLayout(SWT.VERTICAL);
		rl_composite.center = true;
		composite.setLayout(rl_composite);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		
		Label lblAbc = new Label(composite, SWT.NONE);
		lblAbc.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/go-next.png"));
		
		btnTransform = new Button(composite, SWT.NONE);
		btnTransform.setText("Transform");
		
		Group grpJavaClasses = new Group(this, SWT.NONE);
		grpJavaClasses.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.BOLD));
		grpJavaClasses.setBackground(SWTResourceManager.getColor(135, 206, 250));
		grpJavaClasses.setLayout(new FillLayout(SWT.VERTICAL));
		GridData gd_grpJavaClasses = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2);
		gd_grpJavaClasses.widthHint = 108;
		grpJavaClasses.setLayoutData(gd_grpJavaClasses);
		grpJavaClasses.setText("Java Classes");
		
		btnCreateClass = new Button(grpJavaClasses, SWT.NONE);
		btnCreateClass.setEnabled(false);
		btnCreateClass.setText("View and Edit");
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		RowLayout rl_composite_1 = new RowLayout(SWT.VERTICAL);
		rl_composite_1.center = true;
		composite_1.setLayout(rl_composite_1);
		composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		
		Label label = new Label(composite_1, SWT.NONE);
		label.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/go-next.png"));
		
		btnBuild = new Button(composite_1, SWT.NONE);
		btnBuild.setText("Build");
		
		Group grpApplicationBinary = new Group(this, SWT.NONE);
		grpApplicationBinary.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.BOLD));
		grpApplicationBinary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpApplicationBinary.setBackground(SWTResourceManager.getColor(135, 206, 250));
		grpApplicationBinary.setText("Ontology Binary");
		grpApplicationBinary.setLayout(new FillLayout(SWT.VERTICAL));
		
		btnTestConformance = new Button(grpApplicationBinary, SWT.NONE);
		btnTestConformance.setEnabled(true);
		btnTestConformance.setText("Test Conformance");
		
		Composite composite_2 = new Composite(this, SWT.NONE);
		RowLayout rl_composite_2 = new RowLayout(SWT.VERTICAL);
		rl_composite_2.center = true;
		composite_2.setLayout(rl_composite_2);
		composite_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		
		Label label_1 = new Label(composite_2, SWT.NONE);
		label_1.setImage(ResourceManager.getPluginImage("org.universaal.tools.dashboard", "icons/go-next.png"));
		
		btnCombine = new Button(composite_2, SWT.NONE);
		btnCombine.setEnabled(false);
		btnCombine.setText("Package");
		
		Group grpPublishableOntology = new Group(this, SWT.BORDER);
		grpPublishableOntology.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.BOLD));
		grpPublishableOntology.setBackground(SWTResourceManager.getColor(135, 206, 250));
		grpPublishableOntology.setLayout(new FillLayout(SWT.VERTICAL));
		grpPublishableOntology.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		grpPublishableOntology.setText("Publishable Ontology");
		
		btnPublishOpenSource = new Button(grpPublishableOntology, SWT.NONE);
		btnPublishOpenSource.setText("Publish Open Source");
		
		Group grpApplicationDescription = new Group(this, SWT.NONE);
		grpApplicationDescription.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.BOLD));
		grpApplicationDescription.setBackground(SWTResourceManager.getColor(135, 206, 250));
		GridData gd_grpApplicationDescription = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_grpApplicationDescription.widthHint = 152;
		grpApplicationDescription.setLayoutData(gd_grpApplicationDescription);
		grpApplicationDescription.setText("Application Description");
		grpApplicationDescription.setLayout(new FillLayout(SWT.VERTICAL));
		
		btnCreateAndEdit = new Button(grpApplicationDescription, SWT.NONE);
		btnCreateAndEdit.setText("Create and Edit");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	DashboardView containingPart;
	
	private void addCommandCallingListener(Button btn, String commandID, String featureName) {
		btn.addSelectionListener(new CommandCallingListener(containingPart, commandID, featureName));		
	}
	
	/**
	 * Use this method to create all the buttonlisteners, and assign them to 
	 * their respective buttons.
	 */
	protected void createActions(DashboardView containingPart) {
		this.containingPart = containingPart;
//		btnImportProject.addSelectionListener(new TemporaryListener(this, "Import Project"));
		//btnImportExample.addSelectionListener(new ImportExampleListener(this));
		addCommandCallingListener(btnImportExample,"org.universaal.importexternalproject.commands.importexample", "AAL Studio integration with Developer Depot");

		//Java Classes
		//btnCreateClass.addSelectionListener(new CreateNewItemListener(this));
		addCommandCallingListener(btnCreateClass,"org.universaal.tools.newwizard.plugin.command.startNewItemWizard", "AAL Studio Project Wizards");
//		btnImportClass.addSelectionListener(new TemporaryListener(this, "Import Class"));

		//Application Binary
		addCommandCallingListener(btnTestConformance, "org.universaal.tools.conformanceTools.commands.ConformanceToolsRun", "AAL Studio Conformance Tools");
		//		btnTestConformance.addSelectionListener(new TemporaryListener(this, "Test Conformance"));
		//btnRun.addSelectionListener(new RunProjectListener(this));
		//addCommandCallingListener(btnRun,"org.universaal.tools.buildserviceapplication.actions.RunAction", "AAL Studio Build");
		//btnDebug.addSelectionListener(new DebugProjectListener(this));
		//addCommandCallingListener(btnDebug,"org.universaal.tools.buildserviceapplication.actions.DebugAction", "AAL Studio Build");

		//Project Description
		addCommandCallingListener(btnCreateAndEdit,"org.universaal.tools.uploadopensourceplugin.generateaalapp", "AAL Studio integration with Developer Depot");

		//Publishable Application
		//btnUploadOpenSource.addSelectionListener(new UploadOpenSourceListener(this));
		addCommandCallingListener(btnPublishOpenSource,"org.universaal.tools.uploadopensourceplugin.commands.uploadopensource", "AAL Studio integration with Developer Depot");
		//btnPublishUstore.addSelectionListener(new PublishProjectListener(this));
		//addCommandCallingListener(btnPublishToUStore,"org.universaal.tools.buildserviceapplication.actions.PublishAction", "AAL Studio Build");

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
}
