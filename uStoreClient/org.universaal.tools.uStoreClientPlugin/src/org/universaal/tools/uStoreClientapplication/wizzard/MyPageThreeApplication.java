/*
	Copyright 2011-2014 CERTH-ITI, http://www.iti.gr
	Information Technologies Institute (ITI)
	Centre For Research and Technology Hellas (CERTH)
	
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.tools.uStoreClientapplication.wizzard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.FillLayout;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class MyPageThreeApplication extends WizardPage {
	private Text nameText;
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
	private Text priceText;
	private Text serviceLevelAgreementText;
	private Text requirementsText;
	private Text licensesText;
	private Text capabilitiesText;
	private Text imageText;
	private Text thumbnailText;
	private String thumbnailFileName;
	private byte[] thumbnailFile;
	private String imageFileName;
	private byte[] imageFile;
	private Combo readyForPurchaseCombo;

	/**
	 * Create the wizard.
	 */
	public MyPageThreeApplication() {
		super("wizardPage");
		setTitle("Publish to uStore");
		setDescription("Provide Application details");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(container, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name*");
		
		nameText = new Text(container, SWT.BORDER);
		nameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!nameText.getText().trim().equals("")){
					setPageComplete(true);
				}else
					setPageComplete(false); 
			}
		});
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
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
		lblDeveloperEmail.setText("Developer email");
		
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
		
		Label lblImage = new Label(container, SWT.NONE);
		lblImage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblImage.setText("Image");
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new BorderLayout(0, 0));
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_composite.heightHint = 28;
		gd_composite.widthHint = 363;
		composite.setLayoutData(gd_composite);
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.WEST);
		composite_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Button btnBrowse = new Button(composite_1, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.MULTI);

				String fn = dlg.open();
				if (fn != null) {
					// Append all the selected files. Since getFileNames()
					// returns only
					// the names, and not the path, prepend the path,
					// normalizing
					// if necessary
					StringBuffer buf = new StringBuffer();
					String[] files = dlg.getFileNames();
					for (int i = 0, n = files.length; i < n; i++) {
						buf.append(dlg.getFilterPath());
						if (buf.charAt(buf.length() - 1) != File.separatorChar) {
							buf.append(File.separatorChar);
						}
						buf.append(files[i]);
						buf.append(" ");
					}
					imageText.setText(buf.toString());
					File file = new File(buf.toString());
					imageFileName=file.getName();
					
					
					try {
						imageFile = getBytesFromFile(file);
						
					} catch (Exception e1) {
						e1.printStackTrace();
						
					}
				}
			}
		});
		btnBrowse.setText("Browse");
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.CENTER);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		imageText = new Text(composite_2, SWT.BORDER);
		imageText.setEditable(false);
		
		Label lblThumbnail = new Label(container, SWT.NONE);
		lblThumbnail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblThumbnail.setText("Thumbnail");
		
		Composite composite_3 = new Composite(container, SWT.NONE);
		composite_3.setLayout(new BorderLayout(0, 0));
		GridData gd_composite_3 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_composite_3.heightHint = 28;
		composite_3.setLayoutData(gd_composite_3);
		
		Composite composite_4 = new Composite(composite_3, SWT.NONE);
		
		composite_4.setLayoutData(BorderLayout.WEST);
		composite_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Button btnBrowse_1 = new Button(composite_4, SWT.NONE);
		btnBrowse_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.MULTI);

				String fn = dlg.open();
				if (fn != null) {
					// Append all the selected files. Since getFileNames()
					// returns only
					// the names, and not the path, prepend the path,
					// normalizing
					// if necessary
					StringBuffer buf = new StringBuffer();
					String[] files = dlg.getFileNames();
					for (int i = 0, n = files.length; i < n; i++) {
						buf.append(dlg.getFilterPath());
						if (buf.charAt(buf.length() - 1) != File.separatorChar) {
							buf.append(File.separatorChar);
						}
						buf.append(files[i]);
						buf.append(" ");
					}
					thumbnailText.setText(buf.toString());
					File file = new File(buf.toString());
					thumbnailFileName=file.getName();
					
					
					try {
						thumbnailFile = getBytesFromFile(file);
						
					} catch (Exception e1) {
						e1.printStackTrace();
						
					}
				}
			}
		});
		btnBrowse_1.setText("Browse");
		
		Composite composite_5 = new Composite(composite_3, SWT.NONE);
		composite_5.setLayoutData(BorderLayout.CENTER);
		composite_5.setLayout(new FillLayout(SWT.HORIZONTAL));
		thumbnailText = new Text(composite_5, SWT.BORDER);
		thumbnailText.setEditable(false);
		
		Label lblListPrice = new Label(container, SWT.NONE);
		lblListPrice.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblListPrice.setText("List price");
		
		priceText = new Text(container, SWT.BORDER);
		priceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Service level agreement");
		
		serviceLevelAgreementText = new Text(container, SWT.BORDER);
		serviceLevelAgreementText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblRequirements = new Label(container, SWT.NONE);
		lblRequirements.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRequirements.setText("Requirements");
		
		requirementsText = new Text(container, SWT.BORDER);
		requirementsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLicenses = new Label(container, SWT.NONE);
		lblLicenses.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLicenses.setText("Licenses");
		
		licensesText = new Text(container, SWT.BORDER);
		licensesText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCapabilities = new Label(container, SWT.NONE);
		lblCapabilities.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCapabilities.setText("Capabilities");
		
		capabilitiesText = new Text(container, SWT.BORDER);
		capabilitiesText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblReadyForPurchase = new Label(container, SWT.NONE);
		lblReadyForPurchase.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblReadyForPurchase.setText("Ready for purchase");
		
		 readyForPurchaseCombo = new Combo(container, SWT.READ_ONLY);
		readyForPurchaseCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		readyForPurchaseCombo.add("Yes");
		readyForPurchaseCombo.add("No");
		
		readyForPurchaseCombo.select(0);
	}

	
	// Returns the contents of the file in a byte array.
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public Text getNameText() {
		return nameText;
	}

	public void setNameText(Text nameText) {
		this.nameText = nameText;
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

	public Text getPriceText() {
		return priceText;
	}

	public void setPriceText(Text priceText) {
		this.priceText = priceText;
	}

	public Text getServiceLevelAgreementText() {
		return serviceLevelAgreementText;
	}

	public void setServiceLevelAgreementText(Text serviceLevelAgreementText) {
		this.serviceLevelAgreementText = serviceLevelAgreementText;
	}

	public Text getRequirementsText() {
		return requirementsText;
	}

	public void setRequirementsText(Text requirementsText) {
		this.requirementsText = requirementsText;
	}

	public Text getLicensesText() {
		return licensesText;
	}

	public void setLicensesText(Text licensesText) {
		this.licensesText = licensesText;
	}

	public Text getCapabilitiesText() {
		return capabilitiesText;
	}

	public void setCapabilitiesText(Text capabilitiesText) {
		this.capabilitiesText = capabilitiesText;
	}

	public Text getImageText() {
		return imageText;
	}

	public void setImageText(Text imageText) {
		this.imageText = imageText;
	}

	public Text getThumbnailText() {
		return thumbnailText;
	}

	public void setThumbnailText(Text thumbnailText) {
		this.thumbnailText = thumbnailText;
	}

	public String getThumbnailFileName() {
		return thumbnailFileName;
	}

	public void setThumbnailFileName(String thumbnailFileName) {
		this.thumbnailFileName = thumbnailFileName;
	}

	public byte[] getThumbnailFile() {
		return thumbnailFile;
	}

	public void setThumbnailFile(byte[] thumbnailFile) {
		this.thumbnailFile = thumbnailFile;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public byte[] getImageFile() {
		return imageFile;
	}

	public void setImageFile(byte[] imageFile) {
		this.imageFile = imageFile;
	}

	public Combo getReadyForPurchaseCombo() {
		return readyForPurchaseCombo;
	}

	public void setReadyForPurchaseCombo(Combo readyForPurchaseCombo) {
		this.readyForPurchaseCombo = readyForPurchaseCombo;
	}
	
	
	
}
