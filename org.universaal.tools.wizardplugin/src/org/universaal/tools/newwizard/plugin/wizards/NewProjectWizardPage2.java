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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jdt.core.JavaConventions;

/**
 * The second wizard page allows setting the name of the root package, where all
 * wrapper classes will be created. The wrapper classes can be selected in this
 * page, depending on what buses do you want the project to connect to.
 */

public class NewProjectWizardPage2 extends WizardPage {

    private Button csubscriber;
    private Button cpublisher;
    private Button scallee;
    private Button scaller;
    private Button isubscriber;
    private Button opublisher;
    private Button ipublisher;
    private Button osubscriber;
    private Button defscaller;
    private Button defcpublisher;
    private Combo drop;
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
	Composite containerParent = new Composite(parent, SWT.NULL);
	GridLayout layoutP = new GridLayout();
	containerParent.setLayout(layoutP);
	layoutP.numColumns = 1;
	layoutP.verticalSpacing = 9;
	
	// First layout with the name of the package
	Composite container1 = new Composite(containerParent, SWT.NULL);
	GridLayout layout2 = new GridLayout();
	container1.setLayout(layout2);
	container1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	layout2.numColumns = 2;
	layout2.verticalSpacing = 9;
	// Name of the package
	Label label4 = new Label(container1, SWT.NULL);
	label4.setText(Messages.getString("Page2.2")); //$NON-NLS-1$
	packaging = new Text(container1, SWT.BORDER | SWT.SINGLE);
	GridData gd7 = new GridData(GridData.FILL_HORIZONTAL);
	packaging.setLayoutData(gd7);
	packaging.addModifyListener(new ModifyListener() {
	    public void modifyText(ModifyEvent e) {
		validateInput();
	    }
	});
	
	// Dropdown with template of full project
	Label label5 = new Label(container1, SWT.NULL);
	label5.setText(Messages.getString("Page2.13")); //$NON-NLS-1$
	
	drop = new Combo(container1, SWT.READ_ONLY);
	drop.select(0);
	GridData gd10 = new GridData(GridData.FILL_HORIZONTAL);
	drop.setLayoutData(gd10);
	drop.add(Messages.getString("Page2.14"), 0); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.15"), 1); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.16"), 2); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.17"), 3); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.18"), 4); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.19"), 5); //$NON-NLS-1$
	drop.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		csubscriber.setSelection(false);
		cpublisher.setSelection(false);
		scallee.setSelection(false);
		scaller.setSelection(false);
		isubscriber.setSelection(false);
		opublisher.setSelection(false);
		ipublisher.setSelection(false);
		osubscriber.setSelection(false);
		defcpublisher.setSelection(false);
		defscaller.setSelection(false);
		defcpublisher.setEnabled(false);
		defscaller.setEnabled(false);
		switch (drop.getSelectionIndex()) {
		case 0:
		    csubscriber.setSelection(true);
		    cpublisher.setSelection(true);
		    defcpublisher.setEnabled(true);
		    scallee.setSelection(true);
		    scaller.setSelection(true);
		    defscaller.setEnabled(true);
		    isubscriber.setSelection(true);
		    opublisher.setSelection(true);
		    break;
		case 1:
		    csubscriber.setSelection(true);
		    cpublisher.setSelection(true);
		    defcpublisher.setEnabled(true);
		    scallee.setSelection(true);
		    scaller.setSelection(true);
		    defscaller.setEnabled(true);
		    break;
		case 2:
		    cpublisher.setSelection(true);
		    defcpublisher.setEnabled(true);
		    break;
		case 3:
		    cpublisher.setSelection(true);
		    defcpublisher.setEnabled(true);
		    scallee.setSelection(true);
		    break;
		case 4:
		    csubscriber.setSelection(true);
		    cpublisher.setSelection(true);
		    defcpublisher.setEnabled(true);
		    break;
		case 5:
		    ipublisher.setSelection(true);
		    osubscriber.setSelection(true);
		    break;
		default:
		    break;
		}
		validateInput();
	    }
	});
	
	// Second layout with the checkboxes of classes
	Group container2 = new Group(containerParent, SWT.NONE);
	container2.setText(Messages.getString("Page2.3")); //$NON-NLS-1$
	GridLayout layout = new GridLayout();
	container2.setLayout(layout);
	container2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
		| GridData.HORIZONTAL_ALIGN_CENTER));
	layout.numColumns = 3;
	layout.verticalSpacing = 9;
	
	GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
	
	// SCallee
	scallee = new Button(container2, SWT.CHECK);
	scallee.setLayoutData(gd1);
	scallee.setText(Messages.getString("Page2.7")); //$NON-NLS-1$
	// SCaller
	scaller = new Button(container2, SWT.CHECK);
	scaller.setLayoutData(gd1);
	scaller.setText(Messages.getString("Page2.6")); //$NON-NLS-1$
	scaller.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		if(defscaller!=null){
		    if(scaller.getSelection()){
			defscaller.setEnabled(true);
		    }else{
			defscaller.setSelection(false);
			defscaller.setEnabled(false);
		    }
		}
	    }
	});
	// DefaultSCaller
	defscaller = new Button(container2, SWT.CHECK);
	defscaller.setLayoutData(gd1);
	defscaller.setText(Messages.getString("Page2.20")); //$NON-NLS-1$
	defscaller.setEnabled(false);
	
	// CSubscriber
	csubscriber = new Button(container2, SWT.CHECK);
	csubscriber.setLayoutData(gd1);
	csubscriber.setText(Messages.getString("Page2.4")); //$NON-NLS-1$
	// CPublisher
	cpublisher = new Button(container2, SWT.CHECK);
	cpublisher.setLayoutData(gd1);
	cpublisher.setText(Messages.getString("Page2.5")); //$NON-NLS-1$
	cpublisher.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		if(defcpublisher!=null){
		    if(cpublisher.getSelection()){
			defcpublisher.setEnabled(true);
		    }else{
			defcpublisher.setSelection(false);
			defcpublisher.setEnabled(false);
		    }
		}
	    }
	});
	// DefaultSCaller
	defcpublisher = new Button(container2, SWT.CHECK);
	defcpublisher.setLayoutData(gd1);
	defcpublisher.setText(Messages.getString("Page2.21")); //$NON-NLS-1$
	defcpublisher.setEnabled(false);

	// OSubscriber
	osubscriber = new Button(container2, SWT.CHECK);
	osubscriber.setLayoutData(gd1);
	osubscriber.setText(Messages.getString("Page2.12")); //$NON-NLS-1$
	// OPublisher
	opublisher = new Button(container2, SWT.CHECK);
	opublisher.setLayoutData(gd1);
	opublisher.setText(Messages.getString("Page2.8")); //$NON-NLS-1$
	//Empty placeholder
	Label empty1 = new Label(container2, SWT.NULL);
	empty1.setText(" "); //$NON-NLS-1$
	// ISubscriber
	isubscriber = new Button(container2, SWT.CHECK);
	isubscriber.setLayoutData(gd1);
	isubscriber.setText(Messages.getString("Page2.9")); //$NON-NLS-1$
	// IPublisher
	ipublisher = new Button(container2, SWT.CHECK);
	ipublisher.setLayoutData(gd1);
	ipublisher.setText(Messages.getString("Page2.11")); //$NON-NLS-1$
	//Empty placeholder
	Label empty2 = new Label(container2, SWT.NULL);
	empty2.setText(" "); //$NON-NLS-1$
	
	validateInput();
	setControl(containerParent);
    }

    /**
     * Ensures that package field is set and compliant with Java. Uses
     * deprecated validatePackageName method. Didn´t find the new alternative.
     */
    private void validateInput() {
	String packageName = packaging.getText();
	if (packageName.trim().length() != 0) {
	    // @SuppressWarnings("deprecation")
	    IStatus status = JavaConventions.validatePackageName(packageName);
	    // TODO: Use new method to check package naming.
	    if (!status.isOK()) {
		setErrorMessage(status.getMessage());
		setPageComplete(false);
		return;
	    }
	} else {
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
    
    public Button getIpublisher() {
	return ipublisher;
    }

    public Button getOsubscriber() {
	return osubscriber;
    }
    
    public Button getDefCpublisher() {
	return defcpublisher;
    }
    
    public Button getDefScaller() {
	return defscaller;
    }

    public Text getPackaging() {
	return packaging;
    }

}