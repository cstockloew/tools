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

public class MyPageThree extends WizardPage {
	private Text groupIdText;
	private Text artifactIdText;
	private Text versionText;
	private Text fileNameText;
	private Composite container;
	static private String USTORE_USERNAME = "admin";
	static private String USTORE_PASSWORD = "bigim222";
	private byte[] fullImageByte;
	private byte[] thumbnailImageByte;
	private byte[] fileImageByte;

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
		label1.setText("Group Id");

		groupIdText = new Text(container, SWT.BORDER | SWT.SINGLE);
		groupIdText.setText("");
		groupIdText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!groupIdText.getText().isEmpty()
						&& !artifactIdText.getText().isEmpty()
						&& !versionText.getText().isEmpty()
						&& !fileNameText.getText().isEmpty()
						&& fullImageByte != null && thumbnailImageByte != null
						&& fileImageByte != null) {
					setPageComplete(true);

				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		groupIdText.setLayoutData(gd);

		// password field
		Label label2 = new Label(container, SWT.NULL);
		label2.setText("Artifact Id");

		artifactIdText = new Text(container, SWT.BORDER | SWT.SINGLE);
		artifactIdText.setText("");
		artifactIdText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!groupIdText.getText().isEmpty()
						&& !artifactIdText.getText().isEmpty()
						&& !versionText.getText().isEmpty()
						&& !fileNameText.getText().isEmpty()
						&& fullImageByte != null && thumbnailImageByte != null
						&& fileImageByte != null) {
					setPageComplete(true);

				} else {
					setPageComplete(false);
				}
			}

		});

		artifactIdText.setLayoutData(gd);

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
				if (!groupIdText.getText().isEmpty()
						&& !artifactIdText.getText().isEmpty()
						&& !versionText.getText().isEmpty()
						&& !fileNameText.getText().isEmpty()
						&& fullImageByte != null && thumbnailImageByte != null
						&& fileImageByte != null) {
					setPageComplete(true);

				} else {
					setPageComplete(false);
				}
			}

		});

		versionText.setLayoutData(gd);

		Label label4 = new Label(container, SWT.NULL);
		label4.setText("File name");

		fileNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileNameText.setText("");
		fileNameText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!groupIdText.getText().isEmpty()
						&& !artifactIdText.getText().isEmpty()
						&& !versionText.getText().isEmpty()
						&& !fileNameText.getText().isEmpty()
						&& fullImageByte != null && thumbnailImageByte != null
						&& fileImageByte != null) {
					setPageComplete(true);

				} else {
					setPageComplete(false);
				}
			}

		});

		fileNameText.setLayoutData(gd);

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
					try {
						fileImageByte = getBytesFromFile(file);
						if (!groupIdText.getText().isEmpty()
								&& !artifactIdText.getText().isEmpty()
								&& !versionText.getText().isEmpty()
								&& !fileNameText.getText().isEmpty()
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
					try {
						thumbnailImageByte = getBytesFromFile(file);
						if (!groupIdText.getText().isEmpty()
								&& !artifactIdText.getText().isEmpty()
								&& !versionText.getText().isEmpty()
								&& !fileNameText.getText().isEmpty()
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
					try {
						fullImageByte = getBytesFromFile(file);
						if (!groupIdText.getText().isEmpty()
								&& !artifactIdText.getText().isEmpty()
								&& !versionText.getText().isEmpty()
								&& !fileNameText.getText().isEmpty()
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
		return groupIdText;
	}

	public void setGroupIdText(Text groupIdText) {
		this.groupIdText = groupIdText;
	}

	public Text getArtifactIdText() {
		return artifactIdText;
	}

	public void setArtifactIdText(Text artifactIdText) {
		this.artifactIdText = artifactIdText;
	}

	public Text getVersionText() {
		return versionText;
	}

	public void setVersionText(Text versionText) {
		this.versionText = versionText;
	}

	public Text getFileNameText() {
		return fileNameText;
	}

	public void setFileNameText(Text fileNameText) {
		this.fileNameText = fileNameText;
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