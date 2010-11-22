package org.universaal.tools.newwizard.plugin.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jdt.core.JavaConventions;

/**
 * The second wizard page allows setting the name of the root package, where
 * all wrapper classes will be created. The wrapper classes can be selected in
 * this page, depending on what buses do you want the project to connect to.
 */

public class NewProjectWizardPage2 extends WizardPage {
	
	private Button csubscriber;
	private Button cpublisher;
	private Button scallee;
	private Button scaller;
	private Button isubscriber;
	private Button opublisher;
	private Text packaging;

	/**
	 * Constructor for NewProjectWizardPage2.
	 * 
	 * @param pageName
	 */
	public NewProjectWizardPage2(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.getString("Page2.0")); //$NON-NLS-1$
		setDescription(Messages.getString("Page2.1")); //$NON-NLS-1$
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {	
		Composite containerP = new Composite(parent, SWT.NULL);
		GridLayout layoutP = new GridLayout();
		containerP.setLayout(layoutP);
		layoutP.numColumns = 1;
		layoutP.verticalSpacing = 9;	
		//First layout with the name of the package
		Composite container2 = new Composite(containerP, SWT.NULL);
		GridLayout layout2 = new GridLayout();
		container2.setLayout(layout2);
		container2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		layout2.numColumns = 2;
		layout2.verticalSpacing = 9;	
		//Name of the package
		Label label4 = new Label(container2, SWT.NULL);
		label4.setText(Messages.getString("Page2.2")); //$NON-NLS-1$
		packaging = new Text(container2, SWT.BORDER | SWT.SINGLE);
		GridData gd7 = new GridData(GridData.FILL_HORIZONTAL);
		packaging.setLayoutData(gd7);
		packaging.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});
		//Second layout with the checkboxes of classes
		Group container = new Group(containerP, SWT.NONE);
		container.setText(Messages.getString("Page2.3")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_CENTER));
		layout.numColumns = 2;
		layout.verticalSpacing = 9;	
		//CSubscriber
		csubscriber = new Button(container, SWT.CHECK);
		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
		csubscriber.setLayoutData(gd1);
		csubscriber.setText(Messages.getString("Page2.4")); //$NON-NLS-1$
		csubscriber.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		          //TODO: Do we really need to do something here?
		        }
		      });
		//CPublisher
		cpublisher = new Button(container, SWT.CHECK);
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		cpublisher.setLayoutData(gd2);
		cpublisher.setText(Messages.getString("Page2.5")); //$NON-NLS-1$
		cpublisher.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  //TODO: Do we really need to do something here?
		        }
		      });
		
		//SCallee
		scallee = new Button(container, SWT.CHECK);
		GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
		scallee.setLayoutData(gd3);
		scallee.setText(Messages.getString("Page2.7")); //$NON-NLS-1$
		scallee.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  //TODO: Do we really need to do something here?
		        }
		      });
		//SCaller
		scaller = new Button(container, SWT.CHECK);
		GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
		scaller.setLayoutData(gd4);
		scaller.setText(Messages.getString("Page2.6")); //$NON-NLS-1$
		scaller.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  //TODO: Do we really need to do something here?
		        }
		      });
		//ISubscriber
		isubscriber = new Button(container, SWT.CHECK);
		GridData gd5 = new GridData(GridData.FILL_HORIZONTAL);
		isubscriber.setLayoutData(gd5);
		isubscriber.setText(Messages.getString("Page2.9")); //$NON-NLS-1$
		isubscriber.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  //TODO: Do we really need to do something here?
		        }
		      });
		//OPublisher
		opublisher = new Button(container, SWT.CHECK);
		GridData gd6 = new GridData(GridData.FILL_HORIZONTAL);
		opublisher.setLayoutData(gd6);
		opublisher.setText(Messages.getString("Page2.8")); //$NON-NLS-1$
		opublisher.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  //TODO: Do we really need to do something here?
		        }
		      });
		
		validateInput();
		setControl(container);
	}

	/**
	 * Ensures that package field is set and compliant with Java.
	 * Uses deprecated validatePackageName method.
	 * Didn´t find the new alternative.
	 */

	private void validateInput() {
		String packageName = packaging.getText();
	    if(packageName.trim().length() != 0) {
//	      @SuppressWarnings("deprecation")
	      IStatus status = JavaConventions.validatePackageName(packageName);
	      //TODO: Use new method to check package naming.
	      if(!status.isOK()) {
	        setErrorMessage(status.getMessage());
	        setPageComplete(false);
	        return;
	      }
	    }else{
	    	setMessage(Messages.getString("Page2.10")); //$NON-NLS-1$
	        setPageComplete(false);
	        return;
	    }
	    setPageComplete(true);
	    setErrorMessage(null);
	    setMessage(null);
	}
	
	
	public Button getCsubscriber() {
		return csubscriber;
	}
	public Button getCpublisher() {
		return cpublisher;
	}
	public Button getScallee() {
		return scallee;
	}
	public Button getScaller() {
		return scaller;
	}
	public Button getIsubscriber() {
		return isubscriber;
	}
	public Button getOpublisher() {
		return opublisher;
	}
	public Text getPackaging() {
		return packaging;
	}

}