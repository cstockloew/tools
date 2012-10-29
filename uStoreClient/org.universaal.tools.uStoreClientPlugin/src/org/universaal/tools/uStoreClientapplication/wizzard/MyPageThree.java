package org.universaal.tools.uStoreClientapplication.wizzard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.universaal.tools.uStoreClientapplication.Activator;
import org.eclipse.swt.widgets.Combo;

public class MyPageThree extends WizardPage {
	private Text versionNotesText;
	private Text versionText;
	private Composite container;
	private byte[] fullImageByte;
	private byte[] thumbnailImageByte;
	private byte[] fileImageByte;
	private Combo isForPurchasecombo;
	private String fileName;
	private String imageName;
	private String thumbnailName;

	public MyPageThree() {
		super("Publish to uStore");
		setTitle("Publish to uStore");
		setDescription("Provide application details");
	}

	@Override
	public void createControl(Composite parent) {
		 PlatformUI.getWorkbench().getHelpSystem()
		   .setHelp(parent, Activator.PLUGIN_ID + ".help_item");
		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		Label label1 = new Label(container, SWT.NULL);
		label1.setText("Version notes");

		versionNotesText = new Text(container, SWT.BORDER | SWT.SINGLE);
		versionNotesText.setText("");
		versionNotesText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!versionNotesText.getText().isEmpty()
						&& !isForPurchasecombo.getText().isEmpty()
						&& !versionText.getText().isEmpty()
						
						&& fullImageByte != null && thumbnailImageByte != null
						&& fileImageByte != null) {
					setPageComplete(true);

				} else {
					setPageComplete(false);
				}
			}

		});
		versionNotesText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// password field
		Label label2 = new Label(container, SWT.NULL);
		label2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label2.setText("Purchase by users");
		
		isForPurchasecombo  = new Combo(container, SWT.NONE|SWT.READ_ONLY);
		isForPurchasecombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		isForPurchasecombo.add("Yes");
		isForPurchasecombo.add("No");
		isForPurchasecombo.select(0);
		Label label3 = new Label(container, SWT.NULL);
		label3.setText("Version");

		versionText = new Text(container, SWT.BORDER | SWT.SINGLE);
		versionText.setText("");
		versionText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!versionNotesText.getText().isEmpty()
						&& !isForPurchasecombo.getText().isEmpty()
						&& !versionText.getText().isEmpty()						
						&& fullImageByte != null && thumbnailImageByte != null
						&& fileImageByte != null) {
					setPageComplete(true);

				} else {
					setPageComplete(false);
				}
			}

		});
		versionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label5 = new Label(container, SWT.NULL);
		label5.setText("File");
		Composite composite_11 = new Composite(container, SWT.NONE);
		RowLayout rl_composite_12 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_12.center = true;
		rl_composite_12.fill = true;
		rl_composite_12.justify = true;
		composite_11.setLayout(rl_composite_12);

		Button btnBrowse = new Button(composite_11, SWT.NONE);
		final Label fileText = new Label(composite_11, SWT.NONE);

		fileText.setText("Please select a file");
		fileText.setLayoutData(new RowData(300, SWT.DEFAULT));
		btnBrowse.addSelectionListener(new SelectionAdapter() {
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
					fileText.setText(buf.toString());
					File file = new File(buf.toString());
					fileName=file.getName();
					try {
						fileImageByte = getBytesFromFile(file);
						if (!versionNotesText.getText().isEmpty()
								&& !isForPurchasecombo.getText().isEmpty()
								&& !versionText.getText().isEmpty()
								
								&& fullImageByte != null
								&& thumbnailImageByte != null
								&& fileImageByte != null) {
							setPageComplete(true);

						} else {
							setPageComplete(false);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowse.setText("Browse");

		Label label6 = new Label(container, SWT.NULL);
		label6.setText("Thumbnail");
		Composite composite_21 = new Composite(container, SWT.NONE);
		RowLayout rl_composite_22 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_22.center = true;
		rl_composite_22.fill = true;
		rl_composite_22.justify = true;
		composite_21.setLayout(rl_composite_22);

		Button btnBrowse2 = new Button(composite_21, SWT.NONE);
		final Label thumbnailText = new Label(composite_21, SWT.NONE);

		thumbnailText.setText("Please select a file");
		thumbnailText.setLayoutData(new RowData(300, SWT.DEFAULT));
		btnBrowse2.addSelectionListener(new SelectionAdapter() {
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
					thumbnailName=file.getName();
					try {
						thumbnailImageByte = getBytesFromFile(file);
						if (!versionNotesText.getText().isEmpty()
								&& !isForPurchasecombo.getText().isEmpty()
								&& !versionText.getText().isEmpty()
								
								&& fullImageByte != null
								&& thumbnailImageByte != null
								&& fileImageByte != null) {
							setPageComplete(true);

						} else {
							setPageComplete(false);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowse2.setText("Browse");

		Label label7 = new Label(container, SWT.NULL);
		label7.setText("Full image");
		Composite composite_31 = new Composite(container, SWT.NONE);
		RowLayout rl_composite_32 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_32.center = true;
		rl_composite_32.fill = true;
		rl_composite_32.justify = true;
		composite_31.setLayout(rl_composite_32);

		Button btnBrowse3 = new Button(composite_31, SWT.NONE);
		final Label fullImageText = new Label(composite_31, SWT.NONE);
		fullImageText.setLayoutData(new RowData(300, SWT.DEFAULT));
		fullImageText.setText("Please select a file");
		btnBrowse3.addSelectionListener(new SelectionAdapter() {
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
					fullImageText.setText(buf.toString());
					File file = new File(buf.toString());
					imageName=file.getName();
					try {
						fullImageByte = getBytesFromFile(file);
						if (!versionNotesText.getText().isEmpty()
								&& !isForPurchasecombo.getText().isEmpty()
								&& !versionText.getText().isEmpty()
								
								&& fullImageByte != null
								&& thumbnailImageByte != null
								&& fileImageByte != null) {
							setPageComplete(true);

						} else {
							setPageComplete(false);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowse3.setText("Browse");

		// Required to avoid an error in the system
		setControl(container);
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

	public Text getGroupIdText() {
		return versionNotesText;
	}

	public void setGroupIdText(Text groupIdText) {
		this.versionNotesText = groupIdText;
	}

	

	public Combo getIsForPurchasecombo() {
		return isForPurchasecombo;
	}

	public void setIsForPurchasecombo(Combo isForPurchasecombo) {
		this.isForPurchasecombo = isForPurchasecombo;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public byte[] getFullImageByte() {
		return fullImageByte;
	}

	public void setFullImageByte(byte[] fullImageByte) {
		this.fullImageByte = fullImageByte;
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

}