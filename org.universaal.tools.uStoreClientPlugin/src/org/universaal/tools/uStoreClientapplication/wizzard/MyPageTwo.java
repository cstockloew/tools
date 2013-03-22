package org.universaal.tools.uStoreClientapplication.wizzard;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.PlatformUI;
import org.universaal.tools.uStoreClientapplication.Activator;
import org.universaal.tools.uStoreClientapplication.actions.ApplicationCategory;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class MyPageTwo extends WizardPage {
	private Text applicationNameText;
	private Text shortDescriptionText;
	private Text descriptionText;
	private Text keywordsText;
	private Text developerNameText;
	private Text developerEmailText;
	private Text developerPhoneText;
	private Text organizationNameText;
	private Text organizationURLText;
	private Text organizationCertificateText;
	private Text URLText;
	private Combo combo;
	
	private List<ApplicationCategory> categoryList;

	/**
	 * Create the wizard.
	 */
	public MyPageTwo() {
		super("wizardPage");
		setTitle("Publish to uStore");
		setDescription("Provide application details");
		
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		 PlatformUI.getWorkbench().getHelpSystem()
		   .setHelp(parent, Activator.PLUGIN_ID + ".help_item");
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Label lblApplicationName = new Label(container, SWT.NONE);
		lblApplicationName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblApplicationName.setText("Application Name*");
		
		applicationNameText = new Text(container, SWT.BORDER);
		applicationNameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!applicationNameText.getText().equals("")){
					setPageComplete(true);
				}else
					setPageComplete(false); 
			}
		});
	
		applicationNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblShortDescription = new Label(container, SWT.NONE);
		lblShortDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblShortDescription.setText("Short description");
		
		shortDescriptionText = new Text(container, SWT.BORDER);
		shortDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setText("Description");
		
		descriptionText = new Text(container, SWT.BORDER);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblKeywords = new Label(container, SWT.NONE);
		lblKeywords.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblKeywords.setText("Keywords");
		
		keywordsText = new Text(container, SWT.BORDER);
		keywordsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDeveloperName = new Label(container, SWT.NONE);
		lblDeveloperName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDeveloperName.setText("Developer name");
		
		developerNameText = new Text(container, SWT.BORDER);
		developerNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDeveloperEmail = new Label(container, SWT.NONE);
		lblDeveloperEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDeveloperEmail.setText("Developer e-mail");
		
		developerEmailText = new Text(container, SWT.BORDER);
		developerEmailText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDeveloperPhone = new Label(container, SWT.NONE);
		lblDeveloperPhone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDeveloperPhone.setText("Developer phone");
		
		developerPhoneText = new Text(container, SWT.BORDER);
		developerPhoneText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOrganizationName = new Label(container, SWT.NONE);
		lblOrganizationName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOrganizationName.setText("Organization name");
		
		organizationNameText = new Text(container, SWT.BORDER);
		organizationNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOrganizationUrl = new Label(container, SWT.NONE);
		lblOrganizationUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOrganizationUrl.setText("Organization URL");
		
		organizationURLText = new Text(container, SWT.BORDER);
		organizationURLText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOrganizationCertificate = new Label(container, SWT.NONE);
		lblOrganizationCertificate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOrganizationCertificate.setText("Organization certificate");
		
		organizationCertificateText = new Text(container, SWT.BORDER);
		organizationCertificateText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblUrl = new Label(container, SWT.NONE);
		lblUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUrl.setText("URL");
		
		URLText = new Text(container, SWT.BORDER);
		URLText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblApplicationCategory = new Label(container, SWT.NONE);
		lblApplicationCategory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblApplicationCategory.setText("Application category");
		
		 combo = new Combo(container, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		for (int i = 0; i < categoryList.size(); i++) {
			combo.add(categoryList.get(i).getCategoryName());
		}
		if (combo.getItemCount() != 0)
			combo.select(0);
		setPageComplete(false);
	}

	public Text getApplicationNameText() {
		return applicationNameText;
	}

	public void setApplicationNameText(Text applicationNameText) {
		this.applicationNameText = applicationNameText;
	}

	public Text getShortDescriptionText() {
		return shortDescriptionText;
	}

	public void setShortDescriptionText(Text shortDescriptionText) {
		this.shortDescriptionText = shortDescriptionText;
	}

	public Text getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(Text descriptionText) {
		this.descriptionText = descriptionText;
	}

	public Text getKeywordsText() {
		return keywordsText;
	}

	public void setKeywordsText(Text keywordsText) {
		this.keywordsText = keywordsText;
	}

	public Text getDeveloperNameText() {
		return developerNameText;
	}

	public void setDeveloperNameText(Text developerNameText) {
		this.developerNameText = developerNameText;
	}

	public Text getDeveloperEmailText() {
		return developerEmailText;
	}

	public void setDeveloperEmailText(Text developerEmailText) {
		this.developerEmailText = developerEmailText;
	}

	public Text getDeveloperPhoneText() {
		return developerPhoneText;
	}

	public void setDeveloperPhoneText(Text developerPhoneText) {
		this.developerPhoneText = developerPhoneText;
	}

	public Text getOrganizationNameText() {
		return organizationNameText;
	}

	public void setOrganizationNameText(Text organizationNameText) {
		this.organizationNameText = organizationNameText;
	}

	public Text getOrganizationURLText() {
		return organizationURLText;
	}

	public void setOrganizationURLText(Text organizationURLText) {
		this.organizationURLText = organizationURLText;
	}

	public Text getOrganizationCertificateText() {
		return organizationCertificateText;
	}

	public void setOrganizationCertificateText(Text organizationCertificateText) {
		this.organizationCertificateText = organizationCertificateText;
	}

	public Text getURLText() {
		return URLText;
	}

	public void setURLText(Text uRLText) {
		URLText = uRLText;
	}

	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	public List<ApplicationCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ApplicationCategory> categoryList) {
		this.categoryList = categoryList;
	}

	
	
	
}
