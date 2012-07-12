package org.universaal.tools.uStoreClientapplication.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.PlatformUI;
import org.universaal.commerce.ustore.tools.CatalogManager;
import org.universaal.commerce.ustore.tools.CatalogManagerServiceLocator;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class MetadataDialog extends Dialog {
	static private String USTORE_USERNAME = "admin";
	static private String USTORE_PASSWORD = "bigim222";
	protected Object result;
	protected Shell shell;
	private Text usernameText;
	private Text passwordText;
	private boolean canceled = true;
	private Text applicationNameText;
	private Text applicationShortDescriptionText;
	private Text applicationFullDescriptionText;
	private Text keywordsText;
	private Text manufacturerText;
	private Text manufacturerPartNumberText;
	private Text applicationURLText;
	private Text listPriceText;
	private Text groupIdText;
	private Text artifactIdText;
	private Text versionText;
	private Text fileNameText;
	private Text hardwareRequirementsText;
	private Text softwareRequirementsText;
	private Text developerContactDetailsText;
	private Text uploadTimeText;
	private List<ApplicationCategory> categoryList;
	private byte[] fullImageByte;
	private byte[] thumbnailImageByte;
	private byte[] fileImageByte;
	private Metadata metadata;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public MetadataDialog(Shell parent, int style) {
		super(parent, SWT.DIALOG_TRIM);
		setText("Publish to uStore");
		
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		// call web service to retrieve categories
		try {
			CatalogManagerServiceLocator loc = new CatalogManagerServiceLocator();
			CatalogManager man = loc.getCatalogManagerPort();
			String catalog = man.getAALApplicationsCategories(USTORE_USERNAME,
					USTORE_PASSWORD);
			ApplicationCategoryParser applicationCategoryParser = new ApplicationCategoryParser(
					catalog);
			applicationCategoryParser.createCategoryList();
			categoryList = applicationCategoryParser.getCategoryList();
			createContents();
			shell.open();
			shell.layout();
			Display display = getParent().getDisplay();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			return result;
		} catch (Exception ex) {
			MessageDialog.openInformation(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell(),
					"Error",
					"An error occured while connecting to uStore:\n"
							+ ex.getMessage());
			return null;
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 624);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.VERTICAL));

		Composite composite = new Composite(shell, SWT.NONE);
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.justify = true;
		rl_composite.fill = true;
		rl_composite.center = true;
		composite.setLayout(rl_composite);

		Label lblUsername = new Label(composite, SWT.NONE);
		lblUsername.setText("username");

		usernameText = new Text(composite, SWT.BORDER);

		Label lblPassword = new Label(composite, SWT.NONE);
		lblPassword.setText("password");

		passwordText = new Text(composite, SWT.PASSWORD | SWT.BORDER);

		Composite composite_1 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_1.justify = true;
		rl_composite_1.fill = true;
		rl_composite_1.center = true;
		composite_1.setLayout(rl_composite_1);

		Label lblApplicationName = new Label(composite_1, SWT.NONE);
		lblApplicationName.setText("Application name");

		applicationNameText = new Text(composite_1, SWT.BORDER);

		Composite composite_3 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_3 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_3.justify = true;
		rl_composite_3.fill = true;
		rl_composite_3.center = true;
		composite_3.setLayout(rl_composite_3);

		Label lblApplicationShortDescription = new Label(composite_3, SWT.NONE);
		lblApplicationShortDescription.setText("Application short description");

		applicationShortDescriptionText = new Text(composite_3, SWT.BORDER);

		Composite composite_4 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_4 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_4.center = true;
		rl_composite_4.fill = true;
		rl_composite_4.justify = true;
		composite_4.setLayout(rl_composite_4);

		Label lblApplicationFullDescription = new Label(composite_4, SWT.NONE);
		lblApplicationFullDescription.setText("Application full description");

		applicationFullDescriptionText = new Text(composite_4, SWT.BORDER);
		applicationFullDescriptionText.setLayoutData(new RowData(135, 43));

		Composite composite_5 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_5 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_5.fill = true;
		rl_composite_5.center = true;
		rl_composite_5.justify = true;
		composite_5.setLayout(rl_composite_5);

		Label lblKeywords = new Label(composite_5, SWT.NONE);
		lblKeywords.setText("Keywords");

		keywordsText = new Text(composite_5, SWT.BORDER);

		Composite composite_6 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_6 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_6.center = true;
		rl_composite_6.fill = true;
		rl_composite_6.justify = true;
		composite_6.setLayout(rl_composite_6);

		Label lblManufacturer = new Label(composite_6, SWT.NONE);
		lblManufacturer.setText("Manufacturer");

		manufacturerText = new Text(composite_6, SWT.BORDER);

		Composite composite_7 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_7 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_7.fill = true;
		rl_composite_7.center = true;
		rl_composite_7.justify = true;
		composite_7.setLayout(rl_composite_7);

		Label lblManufacturerPartNumber = new Label(composite_7, SWT.NONE);
		lblManufacturerPartNumber.setText("Manufacturer part number");

		manufacturerPartNumberText = new Text(composite_7, SWT.BORDER);

		Composite composite_8 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_8 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_8.center = true;
		rl_composite_8.justify = true;
		rl_composite_8.fill = true;
		composite_8.setLayout(rl_composite_8);

		Label lblApplicationUrl = new Label(composite_8, SWT.NONE);
		lblApplicationUrl.setText("Application URL");

		applicationURLText = new Text(composite_8, SWT.BORDER);

		Composite composite_9 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_9 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_9.justify = true;
		rl_composite_9.fill = true;
		rl_composite_9.center = true;
		composite_9.setLayout(rl_composite_9);

		Label lblCategory = new Label(composite_9, SWT.NONE);
		lblCategory.setText("Category");

		final Combo categoryCombo = new Combo(composite_9, SWT.READ_ONLY);
		for (int i = 0; i < categoryList.size(); i++) {
			categoryCombo.add(categoryList.get(i).getCategoryName());
		}
		if (categoryCombo.getItemCount() != 0)
			categoryCombo.select(0);

		Composite composite_10 = new Composite(shell, SWT.NONE);

		Label lblFullImage = new Label(composite_10, SWT.NONE);
		lblFullImage.setBounds(77, 10, 49, 13);
		lblFullImage.setText("Full image");
		final Label filename = new Label(composite_10, SWT.NONE);
		filename.setBounds(326, 0, 49, 13);
		Button btnNewButton = new Button(composite_10, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(shell, SWT.MULTI);

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
					filename.setText(buf.toString());
					File file = new File(buf.toString());
					try {
						fullImageByte = getBytesFromFile(file);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(264, 0, 68, 23);
		btnNewButton.setText("Browse");

		Composite composite_11 = new Composite(shell, SWT.NONE);

		Label lblThumbnail = new Label(composite_11, SWT.NONE);
		lblThumbnail.setBounds(98, 10, 49, 13);
		lblThumbnail.setText("Thumbnail");

		final Label thumbnailFileText = new Label(composite_11, SWT.NONE);
		thumbnailFileText.setBounds(329, 10, 49, 13);
		
		Button btnNewButton_1 = new Button(composite_11, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(shell, SWT.MULTI);

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
					thumbnailFileText.setText(buf.toString());
					File file = new File(buf.toString());
					try {
						thumbnailImageByte = getBytesFromFile(file);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_1.setBounds(249, 0, 68, 23);
		btnNewButton_1.setText("Browse");

		Composite composite_12 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_12 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_12.center = true;
		rl_composite_12.fill = true;
		rl_composite_12.justify = true;
		composite_12.setLayout(rl_composite_12);

		Label lblListPrice = new Label(composite_12, SWT.NONE);
		lblListPrice.setText("List price");

		listPriceText = new Text(composite_12, SWT.BORDER);

		Composite composite_13 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_13 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_13.fill = true;
		rl_composite_13.center = true;
		rl_composite_13.justify = true;
		composite_13.setLayout(rl_composite_13);

		Label lblGroupId = new Label(composite_13, SWT.NONE);
		lblGroupId.setText("Group Id");

		groupIdText = new Text(composite_13, SWT.BORDER);

		Composite composite_14 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_14 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_14.fill = true;
		rl_composite_14.center = true;
		rl_composite_14.justify = true;
		composite_14.setLayout(rl_composite_14);

		Label lblArtifactid = new Label(composite_14, SWT.NONE);
		lblArtifactid.setText("ArtifactId");

		artifactIdText = new Text(composite_14, SWT.BORDER);

		Composite composite_15 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_15 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_15.center = true;
		rl_composite_15.fill = true;
		rl_composite_15.justify = true;
		composite_15.setLayout(rl_composite_15);

		Label lblVersion = new Label(composite_15, SWT.NONE);
		lblVersion.setText("Version");

		versionText = new Text(composite_15, SWT.BORDER);

		Composite composite_16 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_16 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_16.fill = true;
		rl_composite_16.center = true;
		rl_composite_16.justify = true;
		composite_16.setLayout(rl_composite_16);

		Label lblFileName = new Label(composite_16, SWT.NONE);
		lblFileName.setText("File name");

		fileNameText = new Text(composite_16, SWT.BORDER);

		Composite composite_17 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_17 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_17.fill = true;
		rl_composite_17.center = true;
		rl_composite_17.justify = true;
		composite_17.setLayout(rl_composite_17);

		Label lblFile = new Label(composite_17, SWT.NONE);
		lblFile.setLayoutData(new RowData(104, SWT.DEFAULT));
		lblFile.setText("File");

		final Label fileText = new Label(composite_17, SWT.NONE);
		fileText.setLayoutData(new RowData(63, SWT.DEFAULT));

		Button btnBrowse = new Button(composite_17, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(shell, SWT.MULTI);

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
					fileText.setText(buf.toString());
					File file = new File(buf.toString());
					try {
						fileImageByte = getBytesFromFile(file);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowse.setText("Browse");

		Composite composite_18 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_18 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_18.center = true;
		rl_composite_18.fill = true;
		rl_composite_18.justify = true;
		composite_18.setLayout(rl_composite_18);

		Label lblHardwareRequirements = new Label(composite_18, SWT.NONE);
		lblHardwareRequirements.setText("Hardware requirements");

		hardwareRequirementsText = new Text(composite_18, SWT.BORDER);

		Composite composite_19 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_19 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_19.center = true;
		rl_composite_19.fill = true;
		rl_composite_19.justify = true;
		composite_19.setLayout(rl_composite_19);

		Label lblSoftwareRequirements = new Label(composite_19, SWT.NONE);
		lblSoftwareRequirements.setText("Software requirements");

		softwareRequirementsText = new Text(composite_19, SWT.BORDER);

		Composite composite_20 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_20 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_20.center = true;
		rl_composite_20.fill = true;
		rl_composite_20.justify = true;
		composite_20.setLayout(rl_composite_20);

		Label lblDeveloperContactDetails = new Label(composite_20, SWT.NONE);
		lblDeveloperContactDetails.setText("Developer contact details");

		developerContactDetailsText = new Text(composite_20, SWT.BORDER);

		Composite composite_21 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_21 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_21.center = true;
		rl_composite_21.fill = true;
		rl_composite_21.justify = true;
		composite_21.setLayout(rl_composite_21);

		Label lblUploadTimeTo = new Label(composite_21, SWT.NONE);
		lblUploadTimeTo.setText("Upload time to nexus");

		uploadTimeText = new Text(composite_21, SWT.BORDER);

		Composite composite_2 = new Composite(shell, SWT.NONE);
		RowLayout rl_composite_2 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_2.center = true;
		rl_composite_2.fill = true;
		rl_composite_2.justify = true;
		composite_2.setLayout(rl_composite_2);

		Button btnSubmit = new Button(composite_2, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				canceled = false;
				metadata = new Metadata();
				metadata.setUsername(usernameText.getText());
				metadata.setPassword(passwordText.getText());
				metadata.setApplicationName(applicationNameText.getText());
				metadata.setApplicationShortDescription(applicationShortDescriptionText
						.getText());
				metadata.setApplicationFullDescription(applicationFullDescriptionText
						.getText());
				metadata.setKeywords(keywordsText.getText());
				metadata.setManufacturer(manufacturerText.getText());
				metadata.setManufacturerPartNumber(manufacturerPartNumberText
						.getText());
				metadata.setApplicationURL(applicationURLText.getText());
				for (int i = 0; i < categoryList.size(); i++) {
					if (categoryList.get(i).getCategoryName()
							.equals(categoryCombo.getText())) {
						metadata.setCategory(categoryList.get(i)
								.getCategoryNumber());
						break;
					}
				}
				metadata.setFullImage(fullImageByte);
				metadata.setThumbnail(thumbnailImageByte);
				metadata.setListPrice(listPriceText.getText());
				metadata.setArtifactId(artifactIdText.getText());
				metadata.setGroupId(groupIdText.getText());
				metadata.setVersion(versionText.getText());
				metadata.setFileName(fileNameText.getText());
				metadata.setFile(fileImageByte);
				metadata.setHardwareRequirements(hardwareRequirementsText
						.getText());
				metadata.setSoftwareRequirements(softwareRequirementsText
						.getText());
				metadata.setDeveloperContactDetails(developerContactDetailsText
						.getText());
			//	metadata.setUploadTimeToNexus(uploadTimeText.getText());
				shell.close();
			}
		});
		btnSubmit.setText("Submit");

		Button btnCancel = new Button(composite_2, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				canceled = true;
				shell.close();
			}
		});
		btnCancel.setText("Cancel");

		
		usernameText.setText("admin");
		passwordText.setText("bigim222");
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("New Item");
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

	public boolean isCanceled() {
		return canceled;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

}
