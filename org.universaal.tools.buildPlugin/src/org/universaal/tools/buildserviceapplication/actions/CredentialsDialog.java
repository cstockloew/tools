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
package org.universaal.tools.buildserviceapplication.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.universaal.tools.buildserviceapplication.Activator;

public class CredentialsDialog extends Dialog {
	private Text usernameText;
	private Text passwordText;
	private Button btnSaveCredentials;
	private boolean saveCredentials = true;
	private String username = "";
	private String password = "";
	private boolean canceled = true;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public CredentialsDialog(Shell parentShell) {
		super(parentShell);

	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Developer Depot credentials");
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText("Username");

		usernameText = new Text(container, SWT.BORDER);
		usernameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				username = usernameText.getText();

			}
		});
		usernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_1.setText("Password");

		passwordText = new Text(container, SWT.PASSWORD | SWT.BORDER);
		passwordText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				password = passwordText.getText();
			}
		});
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		new Label(container, SWT.NONE);

		btnSaveCredentials = new Button(container, SWT.CHECK);
		btnSaveCredentials.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnSaveCredentials.getSelection()) {

					saveCredentials = true;
				}
			}
		});
		btnSaveCredentials.setText("Save credentials");

		// load credentials if they exist
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		File dir = new File(workspaceDirectory.getAbsolutePath()
				+ File.separator + ".metadata" + File.separator + ".plugins"
				+ File.separator + Activator.PLUGIN_ID);
		File file = new File(dir + File.separator + ".dd");
		try {
			if (file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(dir
						+ File.separator + ".dd"));

				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				usernameText.setText(line);
				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
					passwordText.setText(line);
					break;
				}
				// String everything = sb.toString();
				br.close();
				btnSaveCredentials.setSelection(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);

		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				canceled = false;

			}
		});
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);

		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				canceled = true;
			}
		});
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(294, 152);
	}

	public Text getUsernameText() {
		return usernameText;
	}

	public void setUsernameText(Text usernameText) {
		this.usernameText = usernameText;
	}

	public Text getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(Text passwordText) {
		this.passwordText = passwordText;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public boolean isSaveCredentials() {
		return saveCredentials;
	}

	public void setSaveCredentials(boolean saveCredentials) {
		this.saveCredentials = saveCredentials;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
