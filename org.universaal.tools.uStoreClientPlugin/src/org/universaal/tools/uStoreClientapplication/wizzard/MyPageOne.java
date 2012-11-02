package org.universaal.tools.uStoreClientapplication.wizzard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.universaal.tools.uStoreClientapplication.Activator;

public class MyPageOne extends WizardPage {
	private Text usernameText;
	private Text passwordText;
	private Composite container;


	public MyPageOne() {
		super("Publish to uStore");
		setTitle("Publish to uStore");
		setDescription("Provide uStore credentials");
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
		label1.setText("Username");

		usernameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		usernameText.setText("");
		usernameText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!usernameText.getText().isEmpty()
						&& !passwordText.getText().isEmpty()) {
					setPageComplete(true);

				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		usernameText.setLayoutData(gd);

		// password field
		Label label2 = new Label(container, SWT.NULL);
		label2.setText("Password");

		passwordText = new Text(container, SWT.PASSWORD | SWT.BORDER
				| SWT.SINGLE);
		passwordText.setText("");
		passwordText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!usernameText.getText().isEmpty()
						&& !passwordText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});

		passwordText.setLayoutData(gd);

		// Required to avoid an error in the system
		setControl(container);
		setPageComplete(false);

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

}