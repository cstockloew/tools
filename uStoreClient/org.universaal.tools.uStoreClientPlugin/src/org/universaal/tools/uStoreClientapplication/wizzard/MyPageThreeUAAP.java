package org.universaal.tools.uStoreClientapplication.wizzard;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.PlatformUI;

import swing2swt.layout.BorderLayout;

import org.universaal.tools.uStoreClientapplication.actions.Application;
import org.eclipse.swt.layout.FillLayout;


public class MyPageThreeUAAP extends WizardPage {
	private Text descriptionText;
	private Text URLText;
	private Text priceText;
	private Text thumbnailText;

	private String thumbnailFileName;
	private byte[] thumbnailFile;
	private String imageFileName;
	private byte[] imageFile;
	private Text imageText;
	private Combo readyForPurchaseCombo;
	


	/**
	 * Create the wizard.
	 */
	public MyPageThreeUAAP() {
		super("wizardPage");
		setTitle("Publish to uStore");
		setDescription("Provide uAAP file details");
		
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setText("Description");
		
		descriptionText = new Text(container, SWT.BORDER);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblUrl = new Label(container, SWT.NONE);
		lblUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUrl.setText("URL");
		
		URLText = new Text(container, SWT.BORDER);
		URLText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblImage = new Label(container, SWT.NONE);
		lblImage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblImage.setText("Image");
		
		Composite composite_3 = new Composite(container, SWT.NONE);
		composite_3.setLayout(new BorderLayout(0, 0));
		GridData gd_composite_3 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_composite_3.heightHint = 28;
		gd_composite_3.widthHint = 386;
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
		btnBrowse_1.setText("Browse");
		
		Composite composite_5 = new Composite(composite_3, SWT.NONE);
		composite_5.setLayoutData(BorderLayout.CENTER);
		composite_5.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		imageText = new Text(composite_5, SWT.BORDER | SWT.READ_ONLY);
		
		Label lblThumbnail = new Label(container, SWT.NONE);
		lblThumbnail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblThumbnail.setText("Thumbnail");
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new BorderLayout(0, 0));
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_composite.heightHint = 29;
		composite.setLayoutData(gd_composite);
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		composite_1.setLayoutData(BorderLayout.WEST);
		
		Button btnBrowse = new Button(composite_1, SWT.NONE);
		btnBrowse.setText("Browse");
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
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.CENTER);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		thumbnailText = new Text(composite_2, SWT.BORDER);
		thumbnailText.setEditable(false);
		
		Label lblListPrice = new Label(container, SWT.NONE);
		lblListPrice.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblListPrice.setText("List price");
		
		priceText = new Text(container, SWT.BORDER);
		priceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblReadyForPurchase = new Label(container, SWT.NONE);
		lblReadyForPurchase.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblReadyForPurchase.setText("Ready for purchase");
		
		 readyForPurchaseCombo = new Combo(container, SWT.READ_ONLY);
		readyForPurchaseCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		readyForPurchaseCombo.add("Yes");
		readyForPurchaseCombo.add("No");
		
		readyForPurchaseCombo.select(0);
		
		
		setPageComplete(true); 
		
		
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

	

	public Text getDescriptionText() {
		return descriptionText;
	}

	public Text getURLText() {
		return URLText;
	}

	public Text getPriceText() {
		return priceText;
	}

	public Text getThumbnailText() {
		return thumbnailText;
	}

	public String getThumbnailFileName() {
		return thumbnailFileName;
	}

	public byte[] getThumbnailFile() {
		return thumbnailFile;
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

	@Override
	public boolean canFlipToNextPage() {
		return false;
	}
	
	
	
	
}
