package org.universaal.tools.uStoreClientapplication.wizzard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.universaal.tools.uStoreClientapplication.Activator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import swing2swt.layout.FlowLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class MyPageThree extends WizardPage {
	private Text listPriceText;
	private Text versionText;
	private Text versionNotesText;
	private Text serviceLevelAgreementText;
	private Text requirementsText;
	private Text licensesText;
	private Text capabilitiesText;
	private String fileName;
	private byte[] fileByte;
	private byte[] thumbnailImageByte;
	private byte[] fileImageByte;
	private byte[] uAAPFileByte;
	private Text text_1;
	private Text text_2;
	private Text text_11;
	private Text text;
	private String imageName;
	private String uAAPFileName;
	private String thumbnailName;
	private Combo readyForPurchaseCombo;
	
	

	/**
	 * Create the wizard.
	 */
	public MyPageThree() {
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
		
		Label lblImage = new Label(container, SWT.NONE);
		lblImage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblImage.setAlignment(SWT.RIGHT);
		lblImage.setText("Image");
		
		Composite composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayout(new BorderLayout(0, 0));
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.heightHint = 24;
		gd_composite_1.widthHint = 456;
		composite_1.setLayoutData(gd_composite_1);
		
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
					text_1.setText(buf.toString());
					File file = new File(buf.toString());
					imageName=file.getName();
					try {
						fileByte = getBytesFromFile(file);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowse.setLayoutData(BorderLayout.WEST);
		btnBrowse.setText("Browse");
		
		text_1 = new Text(composite_1, SWT.BORDER);
		text_1.setEditable(false);
		text_1.setLayoutData(BorderLayout.CENTER);
		
		Label lblThumbnailImage = new Label(container, SWT.NONE);
		lblThumbnailImage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblThumbnailImage.setAlignment(SWT.RIGHT);
		lblThumbnailImage.setText("Thumbnail image");
		
		Composite composite_2 = new Composite(container, SWT.NONE);
		composite_2.setLayout(new BorderLayout(0, 0));
		GridData gd_composite_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_2.heightHint = 24;
		gd_composite_2.widthHint = 456;
		composite_2.setLayoutData(gd_composite_2);
		
		Button btnBrowse_1 = new Button(composite_2, SWT.NONE);
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
					text_2.setText(buf.toString());
					File file = new File(buf.toString());
					thumbnailName=file.getName();
					try {
						thumbnailImageByte = getBytesFromFile(file);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowse_1.setLayoutData(BorderLayout.WEST);
		btnBrowse_1.setText("Browse");
		
		text_2 = new Text(composite_2, SWT.BORDER);
		text_2.setEditable(false);
		text_2.setLayoutData(BorderLayout.CENTER);
		
		Label lblListPriceusd = new Label(container, SWT.NONE);
		lblListPriceusd.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblListPriceusd.setText("List price (USD)");
		
		listPriceText = new Text(container, SWT.BORDER);
		listPriceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblVersion = new Label(container, SWT.NONE);
		lblVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVersion.setText("Version*");
		
		versionText = new Text(container, SWT.BORDER);
		versionText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!versionText.getText().equals("")&&
						!versionNotesText.getText().equals("")&&
						fileByte!=null){
					setPageComplete(true);
				}else
					setPageComplete(false);
			}
		});
	
		versionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblVersionNotes = new Label(container, SWT.NONE);
		lblVersionNotes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVersionNotes.setText("Version notes*");
		
		versionNotesText = new Text(container, SWT.BORDER);
		versionNotesText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!versionText.getText().equals("")&&
						!versionNotesText.getText().equals("")&&
						fileByte!=null){
					setPageComplete(true);
				}else
					setPageComplete(false);
			}
		});
		versionNotesText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblApplicationFile = new Label(container, SWT.NONE);
		lblApplicationFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblApplicationFile.setAlignment(SWT.RIGHT);
		lblApplicationFile.setText("Application file*");
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new BorderLayout(0, 0));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite.heightHint = 23;
		gd_composite.widthHint = 455;
		composite.setLayoutData(gd_composite);
		
		Button btnBrowse_2 = new Button(composite, SWT.NONE);
		btnBrowse_2.addSelectionListener(new SelectionAdapter() {
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
					text.setText(buf.toString());
					File file = new File(buf.toString());
					fileName=file.getName();
					try {
						fileByte = getBytesFromFile(file);
						if(!versionText.getText().equals("")&&
								!versionNotesText.getText().equals("")&&
								fileByte!=null){
							setPageComplete(true);
						}else
							setPageComplete(false);
					} catch (Exception e1) {
						e1.printStackTrace();
						setPageComplete(false);
					}
				}
			}
		});
		btnBrowse_2.setLayoutData(BorderLayout.WEST);
		btnBrowse_2.setText("Browse");
		
		text = new Text(composite, SWT.BORDER);
		text.setEditable(false);
		text.setLayoutData(BorderLayout.CENTER);
		
		Label lblServiceLevelAgreement = new Label(container, SWT.NONE);
		lblServiceLevelAgreement.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblServiceLevelAgreement.setText("Service level agreement");
		
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
		readyForPurchaseCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		readyForPurchaseCombo.add("Yes");
		readyForPurchaseCombo.add("No");
		
		readyForPurchaseCombo.select(0);
		
		
		//add row for uAAP file
		Label lbluAAP = new Label(container, SWT.NONE);
		lbluAAP.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbluAAP.setAlignment(SWT.RIGHT);
		lbluAAP.setText("uAAP File");
		
		Composite composite_11 = new Composite(container, SWT.NONE);
		composite_11.setLayout(new BorderLayout(0, 0));
		GridData gd_composite_11 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_11.heightHint = 24;
		gd_composite_11.widthHint = 456;
		composite_11.setLayoutData(gd_composite_11);
		
		Button btnBrowse11 = new Button(composite_11, SWT.NONE);
		btnBrowse11.addSelectionListener(new SelectionAdapter() {
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
					text_11.setText(buf.toString());
					File file = new File(buf.toString());
					uAAPFileName=file.getName();
					try {
						uAAPFileByte = getBytesFromFile(file);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowse11.setLayoutData(BorderLayout.WEST);
		btnBrowse11.setText("Browse");
		
		text_11 = new Text(composite_11, SWT.BORDER);
		text_11.setEditable(false);
		text_11.setLayoutData(BorderLayout.CENTER);
		
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
	
	

	public Text getListPriceText() {
		return listPriceText;
	}

	public void setListPriceText(Text listPriceText) {
		this.listPriceText = listPriceText;
	}

	public Text getVersionText() {
		return versionText;
	}

	public void setVersionText(Text versionText) {
		this.versionText = versionText;
	}

	public Text getVersionNotesText() {
		return versionNotesText;
	}

	public void setVersionNotesText(Text versionNotesText) {
		this.versionNotesText = versionNotesText;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}




	public byte[] getFileByte() {
		return fileByte;
	}

	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}

	public byte[] getThumbnailImageByte() {
		return thumbnailImageByte;
	}

	public void setThumbnailImageByte(byte[] thumbnailImageByte) {
		this.thumbnailImageByte = thumbnailImageByte;
	}

	public byte[] getFileImageByte() {
		return fileImageByte;
	}

	public void setFileImageByte(byte[] fileImageByte) {
		this.fileImageByte = fileImageByte;
	}

	public Text getText_1() {
		return text_1;
	}

	public void setText_1(Text text_1) {
		this.text_1 = text_1;
	}

	public Text getText_2() {
		return text_2;
	}

	public void setText_2(Text text_2) {
		this.text_2 = text_2;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getThumbnailName() {
		return thumbnailName;
	}

	public void setThumbnailName(String thumbnailName) {
		this.thumbnailName = thumbnailName;
	}

	public Combo getReadyForPurchaseCombo() {
		return readyForPurchaseCombo;
	}

	public void setReadyForPurchaseCombo(Combo readyForPurchaseCombo) {
		this.readyForPurchaseCombo = readyForPurchaseCombo;
	}

	public byte[] getuAAPFileByte() {
		return uAAPFileByte;
	}

	public void setuAAPFileByte(byte[] uAAPFileByte) {
		this.uAAPFileByte = uAAPFileByte;
	}

	public String getuAAPFileName() {
		return uAAPFileName;
	}

	public void setuAAPFileName(String uAAPFileName) {
		this.uAAPFileName = uAAPFileName;
	}
	
	
	
	
}
