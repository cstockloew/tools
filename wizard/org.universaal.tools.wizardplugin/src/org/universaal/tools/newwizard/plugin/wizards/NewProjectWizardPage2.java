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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jdt.core.JavaConventions;
import org.universaal.tools.newwizard.plugin.Activator;

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
    private Button opublisherUIC;
    private Button ipublisher;
    private Button osubscriberUIH;
    private Button defscaller;
    private Button defcpublisher;
    private Button template;
    private Combo drop, drop1;
    private Text packaging;
    private Group containerClasses;
    public static final String VER_030_S = "0.3.0-SNAPSHOT"; //$NON-NLS-1$
    public static final String VER_031_S = "1.0.0"; //$NON-NLS-1$
    public static final String VER_110 = "1.1.0"; //$NON-NLS-1$

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
	GridLayout layoutParent = new GridLayout();
	containerParent.setLayout(layoutParent);
	layoutParent.numColumns = 1;
	layoutParent.verticalSpacing = 9;

	// First layout with the name of the package & template
	Composite containerInfo = new Composite(containerParent, SWT.NULL);
	GridLayout layoutInfo = new GridLayout();
	layoutInfo.numColumns = 2;
	layoutInfo.verticalSpacing = 9;
	// Set the help
	PlatformUI.getWorkbench().getHelpSystem()
		.setHelp(parent, Activator.PLUGIN_ID + ".help_project");
	containerInfo.setLayout(layoutInfo);
	containerInfo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

	// Name of the package
	Label label4 = new Label(containerInfo, SWT.NULL);
	label4.setText(Messages.getString("Page2.2")); //$NON-NLS-1$
	packaging = new Text(containerInfo, SWT.BORDER | SWT.SINGLE);
	GridData layoutInfo1 = new GridData(GridData.FILL_HORIZONTAL);
	packaging.setLayoutData(layoutInfo1);
	packaging.addModifyListener(new ModifyListener() {
	    public void modifyText(ModifyEvent e) {
		validateInput();
	    }
	});

	// Dropdown with middleware versions
	Label label6 = new Label(containerInfo, SWT.NULL);
	label6.setText(Messages.getString("Page2.23")); //$NON-NLS-1$
	drop1 = new Combo(containerInfo, SWT.READ_ONLY);
	drop1.select(0);
	GridData layoutInfo3 = new GridData(GridData.FILL_HORIZONTAL);
	drop1.setLayoutData(layoutInfo3);
	drop1.add(VER_030_S, 0);
	drop1.add(VER_031_S, 1);
	drop1.add(VER_110, 2);
	drop1.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		changeClasses();
		validateInput();
	    }
	});

	// Dropdown with template of full project
	Label label5 = new Label(containerInfo, SWT.NULL);
	label5.setText(Messages.getString("Page2.13")); //$NON-NLS-1$
	drop = new Combo(containerInfo, SWT.READ_ONLY);
	drop.select(0);
	GridData layoutInfo2 = new GridData(GridData.FILL_HORIZONTAL);
	drop.setLayoutData(layoutInfo2);
	drop.add(Messages.getString("Page2.14"), 0); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.15"), 1); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.16"), 2); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.17"), 3); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.18"), 4); //$NON-NLS-1$
	drop.add(Messages.getString("Page2.19"), 5); //$NON-NLS-1$
	drop.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		if (drop.getSelectionIndex() < 0
			|| drop.getSelectionIndex() == 5) {// TODO: remove 5
							   // when handler
							   // template ready
		    template.setSelection(false);
		    template.setEnabled(false);
		    enableClasses(true);
		} else {
		    template.setEnabled(true);
		}
		refreshClasses();
	    }
	});

	// Checkbox for full template project
	template = new Button(containerParent, SWT.CHECK);
	GridData layoutParent1 = new GridData(GridData.FILL_HORIZONTAL);
	template.setLayoutData(layoutParent1);
	template.setText(Messages.getString("Page2.22")); //$NON-NLS-1$
	template.setEnabled(false);
	template.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		enableClasses(!template.getSelection());
	    }
	});

	// Second layout with the checkboxes of classes
	containerClasses = new Group(containerParent, SWT.NONE);
	containerClasses.setText(Messages.getString("Page2.3")); //$NON-NLS-1$
	GridLayout layout = new GridLayout();
	containerClasses.setLayout(layout);
	containerClasses.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
		| GridData.HORIZONTAL_ALIGN_CENTER));
	layout.numColumns = 3;
	layout.verticalSpacing = 9;

	GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);

	// SCallee
	scallee = new Button(containerClasses, SWT.CHECK);
	scallee.setLayoutData(gd1);
	scallee.setText(Messages.getString("Page2.7")); //$NON-NLS-1$
	// SCaller
	scaller = new Button(containerClasses, SWT.CHECK);
	scaller.setLayoutData(gd1);
	scaller.setText(Messages.getString("Page2.6")); //$NON-NLS-1$
	scaller.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		if (defscaller != null) {
		    if (scaller.getSelection()/* &&!template.getSelection() */) {
			defscaller.setEnabled(true);
		    } else {
			defscaller.setSelection(false);
			defscaller.setEnabled(false);
		    }
		}
	    }
	});
	// DefaultSCaller
	defscaller = new Button(containerClasses, SWT.CHECK);
	defscaller.setLayoutData(gd1);
	defscaller.setText(Messages.getString("Page2.20")); //$NON-NLS-1$
	defscaller.setEnabled(false);

	// CSubscriber
	csubscriber = new Button(containerClasses, SWT.CHECK);
	csubscriber.setLayoutData(gd1);
	csubscriber.setText(Messages.getString("Page2.4")); //$NON-NLS-1$
	// CPublisher
	cpublisher = new Button(containerClasses, SWT.CHECK);
	cpublisher.setLayoutData(gd1);
	cpublisher.setText(Messages.getString("Page2.5")); //$NON-NLS-1$
	cpublisher.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		if (defcpublisher != null) {
		    if (cpublisher.getSelection()/* &&!template.getSelection() */) {
			defcpublisher.setEnabled(true);
		    } else {
			defcpublisher.setSelection(false);
			defcpublisher.setEnabled(false);
		    }
		}
	    }
	});
	// DefaultSCaller
	defcpublisher = new Button(containerClasses, SWT.CHECK);
	defcpublisher.setLayoutData(gd1);
	defcpublisher.setText(Messages.getString("Page2.21")); //$NON-NLS-1$
	defcpublisher.setEnabled(false);

	// OSubscriber
	osubscriberUIH = new Button(containerClasses, SWT.CHECK);
	osubscriberUIH.setLayoutData(gd1);
	osubscriberUIH.setText(Messages.getString("Page2.12")); //$NON-NLS-1$
	// OPublisher
	opublisherUIC = new Button(containerClasses, SWT.CHECK);
	opublisherUIC.setLayoutData(gd1);
	opublisherUIC.setText(Messages.getString("Page2.8")); //$NON-NLS-1$
	// Empty placeholder
	Label empty1 = new Label(containerClasses, SWT.NULL);
	empty1.setText(" "); //$NON-NLS-1$
	// ISubscriber
	isubscriber = new Button(containerClasses, SWT.CHECK);
	isubscriber.setLayoutData(gd1);
	isubscriber.setText(Messages.getString("Page2.9")); //$NON-NLS-1$
	// IPublisher
	ipublisher = new Button(containerClasses, SWT.CHECK);
	ipublisher.setLayoutData(gd1);
	ipublisher.setText(Messages.getString("Page2.11")); //$NON-NLS-1$
	// Empty placeholder
	Label empty2 = new Label(containerClasses, SWT.NULL);
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
	if (drop1.getSelectionIndex() < 0) {
	    setMessage(Messages.getString("Page2.24")); //$NON-NLS-1$
	    setPageComplete(false);
	    return;
	}
	setPageComplete(true);
	setErrorMessage(null);
	setMessage(null);
    }

    /**
     * Updates the checkboxes of the classes matching what is selected in
     * template dropdown & checkbox
     */
    private void refreshClasses() {
	boolean isnottemplate = !template.getSelection();
	csubscriber.setSelection(false);
	cpublisher.setSelection(false);
	scallee.setSelection(false);
	scaller.setSelection(false);
	isubscriber.setSelection(false);
	opublisherUIC.setSelection(false);
	ipublisher.setSelection(false);
	osubscriberUIH.setSelection(false);
	defcpublisher.setSelection(false);
	defscaller.setSelection(false);
	defcpublisher.setEnabled(false);
	defscaller.setEnabled(false);
	if (drop != null) {
	    switch (drop.getSelectionIndex()) {
	    case 0:
		csubscriber.setSelection(true);
		cpublisher.setSelection(true);
		defcpublisher.setEnabled(true & isnottemplate);
		scallee.setSelection(true);
		scaller.setSelection(true);
		defscaller.setEnabled(true & isnottemplate);
		isubscriber.setSelection((drop1.getSelectionIndex() < 2)&true);
		opublisherUIC.setSelection(true);
		break;
	    case 1:
		csubscriber.setSelection(true);
		cpublisher.setSelection(true);
		defcpublisher.setEnabled(true & isnottemplate);
		scallee.setSelection(true);
		scaller.setSelection(true);
		defscaller.setEnabled(true & isnottemplate);
		break;
	    case 2:
		cpublisher.setSelection(true);
		defcpublisher.setEnabled(true & isnottemplate);
		break;
	    case 3:
		cpublisher.setSelection(true);
		defcpublisher.setEnabled(true & isnottemplate);
		scallee.setSelection(true);
		break;
	    case 4:
		csubscriber.setSelection(true);
		cpublisher.setSelection(true);
		defcpublisher.setEnabled(true & isnottemplate);
		break;
	    case 5:
		ipublisher.setSelection((drop1.getSelectionIndex() < 2)&true);
		osubscriberUIH.setSelection(true);
		break;
	    default:
		break;
	    }
	}
	validateInput();
    }

    /**
     * Helper to enable/disable the classes checkboxes
     * 
     * @param enable
     *            True to enable the checkboxes, false otherwise
     */
    private void enableClasses(boolean enable) {
	if (containerClasses != null) {
	    Control[] checkboxes = containerClasses.getChildren();
	    for (int i = 0; i < checkboxes.length; i++) {
		checkboxes[i].setEnabled(enable);
	    }
	    refreshClasses();
	}
    }

    /**
     * Helper to change the classes checkboxes depending on MW version
     */
    private void changeClasses() {
	opublisherUIC.setSelection(false);
	osubscriberUIH.setSelection(false);
	isubscriber.setSelection(false);
	ipublisher.setSelection(false);
	if (drop1.getSelectionIndex() > 1) {
	    // Change from old I/O bus to new UI bus
	    opublisherUIC.setText(Messages.getString("Page2.25"));//$NON-NLS-1$
	    osubscriberUIH.setText(Messages.getString("Page2.26"));//$NON-NLS-1$
	    isubscriber.setSelection(false);
	    isubscriber.setVisible(false);
	    ipublisher.setVisible(false);
	} else {
	    // Change from new UI bus to old I/O bus
	    opublisherUIC.setText(Messages.getString("Page2.8"));//$NON-NLS-1$
	    osubscriberUIH.setText(Messages.getString("Page2.12"));//$NON-NLS-1$
	    isubscriber.setVisible(true);
	    ipublisher.setVisible(true);
	}
	refreshClasses();
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
	return opublisherUIC;
    }

    public Button getIpublisher() {
	return ipublisher;
    }

    public Button getOsubscriber() {
	return osubscriberUIH;
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

    public Button getTemplate() {
	return template;
    }

    public Combo getTemplateDropDown() {
	return drop;
    }

    public Combo getVersionDropDown() {
	return drop1;
    }

}