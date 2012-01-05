package org.universaal.tools.modelling.ontology.wizard.wizards;

import org.apache.maven.model.Model;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;

/**
 * The first wizard page allows setting the details for the project, represented
 * by the basic data for the maven POM, the name of the project and the
 * description.
 */

public class MavenDetailsPage extends WizardPage {

    private Text groupId;
    private Text artifactId;
    private Text version;
    private Text name;
    private Text description;

    public MavenDetailsPage() {
	super("wizardPage"); //$NON-NLS-1$
	setTitle(Messages.getString("Page1.0")); //$NON-NLS-1$
	setDescription(Messages.getString("Page1.9")); //$NON-NLS-1$
    }
    /**
     * @wbp.parser.constructor
     */
    public MavenDetailsPage(ISelection selection) {
	super("wizardPage"); //$NON-NLS-1$
	setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.universaal.tools.modelling.ontology.wizard", "icons/ic-uAAL-hdpi.png"));
	setTitle(Messages.getString("Page1.0")); //$NON-NLS-1$
	setDescription(Messages.getString("Page1.9")); //$NON-NLS-1$
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
	Composite container = new Composite(parent, SWT.NULL);
	GridLayout layout = new GridLayout();
	container.setLayout(layout);
	layout.numColumns = 2;
	layout.verticalSpacing = 9;
	// Group Id
	Label label1 = new Label(container, SWT.NULL);
	label1.setText(Messages.getString("Page1.1")); //$NON-NLS-1$
	groupId = new Text(container, SWT.BORDER | SWT.SINGLE);
	groupId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	groupId.addModifyListener(new ModifyListener() {
	    public void modifyText(ModifyEvent e) {
		validate();
	    }
	});
	// Artifact Id
	Label label2 = new Label(container, SWT.NULL);
	label2.setText(Messages.getString("Page1.2")); //$NON-NLS-1$
	artifactId = new Text(container, SWT.BORDER | SWT.SINGLE);
	artifactId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	artifactId.addModifyListener(new ModifyListener() {
	    public void modifyText(ModifyEvent e) {
		validate();
	    }
	});
	// Version
	Label label3 = new Label(container, SWT.NULL);
	label3.setText(Messages.getString("Page1.3")); //$NON-NLS-1$
	version = new Text(container, SWT.BORDER | SWT.SINGLE);
	version.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	version.addModifyListener(new ModifyListener() {
	    public void modifyText(ModifyEvent e) {
		validate();
	    }
	});
	// Name
	Label label4 = new Label(container, SWT.NULL);
	label4.setText(Messages.getString("Page1.4")); //$NON-NLS-1$
	name = new Text(container, SWT.BORDER | SWT.SINGLE);
	name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	name.addModifyListener(new ModifyListener() {
	    public void modifyText(ModifyEvent e) {
		// TODO: Need to validate the name?
	    }
	});
	// Description
	Label label5 = new Label(container, SWT.NULL);
	label5.setText(Messages.getString("Page1.5")); //$NON-NLS-1$
	description = new Text(container, SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
	GridData gd5 = new GridData(SWT.FILL, SWT.FILL, false, true);
	gd5.minimumHeight = 20;
	description.setLayoutData(gd5);
	description.addModifyListener(new ModifyListener() {
	    public void modifyText(ModifyEvent e) {
		// TODO: Need to validate description?
	    }
	});

	validate();
	setControl(container);
    }

    void validate() {
	// These must not be empty
	if (groupId.getText().trim().length() == 0) {
	    setErrorMessage(Messages.getString("Page1.6")); //$NON-NLS-1$
	    setPageComplete(false);
	    return;
	}

	if (artifactId.getText().trim().length() == 0) {
	    setErrorMessage(Messages.getString("Page1.7")); //$NON-NLS-1$
	    setPageComplete(false);
	    return;
	}

	if (version.getText().trim().length() == 0) {
	    setErrorMessage(Messages.getString("Page1.8")); //$NON-NLS-1$
	    setPageComplete(false);
	    return;
	}
	setPageComplete(true);
	setErrorMessage(null);
	setMessage(null);
    }

    public Text getMavenGroupId() {
	return groupId;
    }

    public Text getMavenArtifactId() {
	return artifactId;
    }

    public Text getMavenVersion() {
	return version;
    }

    public Text getMavenName() {
	return name;
    }

    public Text getMavenDescription() {
	return description;
    }
    
    Model model;
    
    public void setModel(Model model) {
    	this.model = model;
    }
	public void updateFromModel() {
		getMavenGroupId().setText(model.getGroupId());
		getMavenArtifactId().setText(model.getArtifactId());
		getMavenVersion().setText(model.getVersion());
		getMavenName().setText(model.getName());
		getMavenDescription().setText(model.getDescription());		
	}
	
	public void updateModel() {
		model.setGroupId(getMavenGroupId().getText());
		model.setArtifactId(getMavenArtifactId().getText());
		model.setVersion(getMavenVersion().getText());
		model.setName(getMavenName().getText());
		model.setDescription(getMavenDescription().getText());		
	}   
    
}