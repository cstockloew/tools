package uploadopensourceplugin.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

public class UploadOpenSourceWizardPage1 extends WizardPage {
	private Text text;

	/**
	 * Create the wizard.
	 */
	public UploadOpenSourceWizardPage1() {
		super("wizardPage");
		setTitle("Upload Open Source");
		setDescription("Please enter the URL to the SVN repository you want to upload to.");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		Label lblNewLabel = new Label(container, SWT.WRAP);
		GridData gd_lblNewLabel = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lblNewLabel.widthHint = 714;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("In order to use this wizard, you need to manually " +
				"create an SVN Repository you can use to share this project, " +
				"for example on sourceforge.net or code.google. " +
				"\r\nPlease enter the URL to where you want to share your project. " +
				"Please do not include the /trunk-part of the URL.");
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	public String getText(){
		return text.getText();
	}
}
