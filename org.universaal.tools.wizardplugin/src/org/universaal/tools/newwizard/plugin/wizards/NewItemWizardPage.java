package org.universaal.tools.newwizard.plugin.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage; //import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.universaal.tools.newwizard.plugin.Activator;

/**
 * The only wizard page allows setting the basic details for the new item, like
 * type, package and name.
 */
public class NewItemWizardPage extends NewTypeWizardPage {
    private Combo drop;
    private Text clasname;

    public NewItemWizardPage(ISelection selection) {
	super(true, Messages.getString("PageI.0")); //$NON-NLS-1$
	setTitle(Messages.getString("PageI.1")); //$NON-NLS-1$
	setDescription(Messages.getString("PageI.2")); //$NON-NLS-1$
    }

    /**
     * Used to setup initial content of page
     * 
     * @param selection
     */
    public void init(IStructuredSelection selection) {
	IJavaElement jelem = getInitialJavaElement(selection);
	initContainerPage(jelem);
	initTypePage(jelem);
//	doStatusUpdate();
	validateInput();
	// IDialogSettings dialogSettings = getDialogSettings();
	// if (dialogSettings != null) {
	// IDialogSettings section = dialogSettings.getSection("wizardPage");
	// }
	setPageComplete(false);
    }

    /**
     * Used to update and validate content, because of extending
     * NewTypeWizardPage
     */
    private void doStatusUpdate() {
	IStatus[] status = new IStatus[] { fContainerStatus, fPackageStatus };
	updateStatus(status);
    }

    public void createControl(Composite parent) {
	initializeDialogUnits(parent);
	Composite containerP = new Composite(parent, SWT.NULL);
	GridLayout layoutP = new GridLayout();
	containerP.setLayout(layoutP);
	layoutP.numColumns = 1;
	layoutP.verticalSpacing = 9;
	// First layout with the name of the package
	Composite container2 = new Composite(containerP, SWT.NULL);
	GridLayout layout2 = new GridLayout();
	container2.setLayout(layout2);
	container2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	layout2.numColumns = 4;
	layout2.verticalSpacing = 9;
	// provided by NewTypeWizardPage
	createContainerControls(container2, 4);
	createPackageControls(container2, 4);

	// Second layout with the checkboxes of classes
	Group container = new Group(containerP, SWT.NONE);
	container.setText("classes"); //$NON-NLS-1$
	GridLayout layout = new GridLayout();
	container.setLayout(layout);
	container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
		| GridData.HORIZONTAL_ALIGN_CENTER));
	layout.numColumns = 3;
	layout.verticalSpacing = 9;

	// Name of the class
	Label label4 = new Label(container, SWT.NULL);
	label4.setText("classname"); //$NON-NLS-1$
	clasname = new Text(container, SWT.BORDER | SWT.SINGLE);
	GridData gd0 = new GridData(GridData.FILL_HORIZONTAL);
	clasname.setLayoutData(gd0);
	clasname.addPaintListener(new PaintListener() {
	    public void paintControl(PaintEvent arg0) {
		validateInput();
	    }
	});
	clasname.addModifyListener(new ModifyListener() {
	    public void modifyText(ModifyEvent e) {
		validateInput();
	    }
	});

	// Dropdown with type of item
	drop = new Combo(container, SWT.READ_ONLY);
	drop.select(0);
	GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
	drop.setLayoutData(gd1);
	drop.add(Messages.getString("PageI.3"), 0); //$NON-NLS-1$
	drop.add(Messages.getString("PageI.4"), 1); //$NON-NLS-1$
	drop.add(Messages.getString("PageI.5"), 2); //$NON-NLS-1$
	drop.add(Messages.getString("PageI.6"), 3); //$NON-NLS-1$
	drop.add(Messages.getString("PageI.7"), 4); //$NON-NLS-1$
	drop.add(Messages.getString("PageI.8"), 5); //$NON-NLS-1$
	drop.add(Messages.getString("PageI.9"), 6); //$NON-NLS-1$
	drop.add(Messages.getString("PageI.14"), 7); //$NON-NLS-1$
	drop.add(Messages.getString("PageI.15"), 8); //$NON-NLS-1$
	drop.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		validateInput();
	    }
	});
	//TODO: I don´t know why this doesn´t work with getShell in NewItemWizad.addPages (see Project wizard)
	PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, Activator.PLUGIN_ID + ".help_item");
	validateInput();
	setControl(container);
    }

    public Combo getDrop() {
	return drop;
    }

    public Text getClasname() {
	return clasname;
    }

    private void validateInput() {
	if (clasname == null || drop == null) {
	    setMessage(Messages.getString("PageI.10")); //$NON-NLS-1$
	    setPageComplete(false);
	    return;
	}
	if (clasname.getText().isEmpty()) {
	    setErrorMessage(Messages.getString("PageI.11")); //$NON-NLS-1$
	    setPageComplete(false);
	    return;
	}
	String clsName = clasname.getText() + ".class"; //$NON-NLS-1$
	if (clsName.trim().length() != 0) {
	    IStatus status = JavaConventions.validateClassFileName(clsName);
	    // TODO: Use new method to check class naming.
	    if (!status.isOK()) {
		setErrorMessage(status.getMessage());
		setPageComplete(false);
		return;
	    }
	} else {
	    setErrorMessage(Messages.getString("PageI.12")); //$NON-NLS-1$
	    setPageComplete(false);
	    return;
	}

	if (drop.getSelectionIndex() < 0) {
	    setErrorMessage(Messages.getString("PageI.13")); //$NON-NLS-1$
	    setPageComplete(false);
	    return;
	}
	setPageComplete(true);
	setErrorMessage(null);
	setMessage(null);
	doStatusUpdate();
    }

    public String getFileTemplateName() {
	switch (drop.getSelectionIndex()) {
	case 0:
	    return "CSubscriber.java"; //$NON-NLS-1$
	case 1:
	    return "CPublisher.java"; //$NON-NLS-1$
	case 2:
	    return "SCallee.java"; //$NON-NLS-1$
	case 3:
	    return "SCaller.java"; //$NON-NLS-1$
	case 4:
	    return "ISubscriber.java"; //$NON-NLS-1$
	case 5:
	    return "OPublisher.java"; //$NON-NLS-1$
	case 6:
	    return "SCalleeProvidedService.java"; //$NON-NLS-1$
	case 7:
	    return "IPublisher.java"; //$NON-NLS-1$
	case 8:
	    return "OSubscriber.java"; //$NON-NLS-1$
	default:
	    return null;
	}
    }

    protected void handleFieldChanged(String fieldName) {
	// TODO Auto-generated method stub
	super.handleFieldChanged(fieldName);
//	doStatusUpdate();
	validateInput();
    }

}
