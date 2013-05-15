package org.universaal.tools.uStoreClientapplication.wizzard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PlatformUI;
import org.universaal.tools.uStoreClientapplication.actions.ApplicationCategory;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class MyPageTwoNew extends WizardPage {
	private Text text;
	private Text versionText;
	private Text versionDescriptionText;
	private String fileName="";
	private byte[] fileByte;
	private List<ApplicationCategory> categoryList;
	private Combo combo;
	
	
	/**
	 * Create the wizard.
	 */
	public MyPageTwoNew() {
		super("wizardPage");
		setTitle("Publish to uStore");
		setDescription("Provide general details");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Label lblApplicationFile = new Label(composite, SWT.RIGHT);
		lblApplicationFile.setText("File*");
		
		Composite composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.widthHint = 487;
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
					text.setText(buf.toString());
					File file = new File(buf.toString());
					fileName=file.getName();
					
					
					try {
						fileByte = getBytesFromFile(file);
						if(!versionText.getText().equals("")&&!versionDescriptionText.getText().equals("")&&fileByte!=null&&!text.getText().equals("")){
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
		btnBrowse.setText("Browse");
		
		text = new Text(composite_1, SWT.BORDER);
		GridData gd_text = new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1);
		gd_text.widthHint = 417;
		text.setLayoutData(gd_text);
		text.setEditable(false);
		
		Composite composite_2 = new Composite(container, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		composite_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		Label lblVersion = new Label(composite_2, SWT.RIGHT);
		lblVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVersion.setAlignment(SWT.RIGHT);
		lblVersion.setText("Version*");
		
		versionText = new Text(container, SWT.BORDER);
		versionText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!versionText.getText().trim().equals("")&&!versionDescriptionText.getText().trim().equals("")&&fileByte!=null&&!text.getText().trim().equals("")){
					setPageComplete(true);
				}else
					setPageComplete(false); 
			}
		});
		versionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblVersionDescription = new Label(container, SWT.NONE);
		lblVersionDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVersionDescription.setText("Version description*");
		
		versionDescriptionText = new Text(container, SWT.BORDER);
		GridData gd_versionDescriptionText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_versionDescriptionText.widthHint = 471;
		versionDescriptionText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!versionText.getText().trim().equals("")&&!versionDescriptionText.getText().trim().equals("")&&fileByte!=null&&!text.getText().trim().equals("")){
					setPageComplete(true);
				}else
					setPageComplete(false); 
			}
		});
		versionDescriptionText.setLayoutData(gd_versionDescriptionText);
		
		Label lblApplicationCategory = new Label(container, SWT.NONE);
		lblApplicationCategory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblApplicationCategory.setText("Application category*");
		
		combo = new Combo(container, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		setPageComplete(false);
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
	
	
	public List<ApplicationCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ApplicationCategory> categoryList) {
		this.categoryList = categoryList;
		for (int i = 0; i < categoryList.size(); i++) {
			combo.add(categoryList.get(i).getCategoryName());
		}
		if (combo.getItemCount() != 0)
			combo.select(0);
	}

	public Text getVersionText() {
		return versionText;
	}

	public void setVersionText(Text versionText) {
		this.versionText = versionText;
	}

	public Text getVersionDescriptionText() {
		return versionDescriptionText;
	}

	public void setVersionDescriptionText(Text versionDescriptionText) {
		this.versionDescriptionText = versionDescriptionText;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Combo getCombo() {
		return combo;
	}

	public byte[] getFileByte() {
		return fileByte;
	}

	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	
	
}
