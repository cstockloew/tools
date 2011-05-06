package org.universaal.tools.dashboard.views;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.part.ViewPart;
import org.universaal.tools.newwizard.plugin.wizards.NewProjectWizard;

public class OverviewView extends ViewPart {

	private Canvas canvas;
	private Button button01;
	private Button button02;
	private Button button03;

	private static Listener listener;
	
	private static NewProjectWizard newProjectWizard;
	
	public OverviewView() {}

	@Override
	public void createPartControl(Composite parent) {
		canvas = new Canvas(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		//Here be buttons. 
		
		// The developer can create a new uAAL project
		button01 = new Button(canvas, SWT.PUSH);
		button01.setBounds(10, 10, 125, 50);
		//TODO tie in with wizard
		button01.setText("New Project");
		
		// The developer can select the template for the project
		//TODO tie in with wizard
		button02 = new Button(canvas, SWT.PUSH);
		button02.setBounds(10, 50, 125, 50);
		button02.setText("Select template");
		
		// The developer can code whatever logic is required
		button03 = new Button(canvas, SWT.PUSH);
		button03.setBounds(10, 90, 125, 50);
		button03.setText("Code!");
		
		
		//TODO make sensible event!
		listener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (event.widget == button01){
					//TODO open the new project wizard
					newProjectWizard = new NewProjectWizard();
					newProjectWizard.init(getViewSite().getWorkbenchWindow().getWorkbench(), null);
					WizardDialog dialog = new WizardDialog(getViewSite().getWorkbenchWindow().getWorkbench().getActiveWorkbenchWindow().getShell(), newProjectWizard);
					dialog.create();
					dialog.open();
				} else if(event.widget == button02){
					//TODO do something
				} else if(event.widget == button03){
					//TODO do something
				}
			}
			};
	
		/*
		// Instantiates and initializes the wizard
	   	NewProjectWizard wizard = new NewProjectWizard();
	   	wizard.init(part.getSite().getWorkbenchWindow().getWorkbench(),(IStructuredSelection)selection);
	    
	   	
	   	// Instantiates the wizard container with the wizard and opens it
	    WizardDialog dialog = new WizardDialog(shell, wizard);
	    dialog.create();
	    dialog.open();*/
		

	}

	@Override
	public void setFocus() {}


}
