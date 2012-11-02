package org.universaal.tools.uStoreClientapplication.wizzard;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.universaal.tools.uStoreClientapplication.Activator;
import org.universaal.tools.uStoreClientapplication.actions.ApplicationCategory;

public class MyPageTwo extends WizardPage {
	private Text applicationNameText;
	private Text applicationShortDescriptionText;
	private Text applicationFullDescriptionText;
	private Text keywordsText;
	private Text manufacturerText;
	private Text manufacturerPartNumberText;
	private Text applicationURLText;
	private Text listPriceText;
	private Text hardwareRequirementsText;
	private Text softwareRequirementsText;
	private Text developerContactDetailsText;
	private Composite container;
	private List<ApplicationCategory> categoryList;
	private Combo categoryCombo;

	public MyPageTwo() {
		super("Publish to uStore");
		setTitle("Publish to uStore");
		setDescription("Provide application details");
		setControl(applicationNameText);
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
		label1.setText("Application name");

		applicationNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		applicationNameText.setText("");
		applicationNameText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		applicationNameText.setLayoutData(gd);

		Label applicationShortDescriptionLabel = new Label(container, SWT.NULL);
		applicationShortDescriptionLabel
				.setText("Application short description");

		applicationShortDescriptionText = new Text(container, SWT.BORDER
				| SWT.SINGLE);
		applicationShortDescriptionText.setText("");
		applicationShortDescriptionText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
		applicationShortDescriptionText.setLayoutData(gd1);

		Label applicationFullDescriptionLabel = new Label(container, SWT.NULL);
		applicationFullDescriptionLabel.setText("Application full description");

		applicationFullDescriptionText = new Text(container, SWT.BORDER
				| SWT.SINGLE);
		applicationFullDescriptionText.setText("");
		applicationFullDescriptionText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		applicationFullDescriptionText.setLayoutData(gd2);

		Label keywordsLabel = new Label(container, SWT.NULL);
		keywordsLabel.setText("Keywords");

		keywordsText = new Text(container, SWT.BORDER | SWT.SINGLE);
		keywordsText.setText("");
		keywordsText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
		keywordsText.setLayoutData(gd3);

		Label manufacturerLabel = new Label(container, SWT.NULL);
		manufacturerLabel.setText("Manufacturer");

		manufacturerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		manufacturerText.setText("");
		manufacturerText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
		manufacturerText.setLayoutData(gd4);

		Label manufacturerPartNumberLabel = new Label(container, SWT.NULL);
		manufacturerPartNumberLabel.setText("Manufacturer part number");

		manufacturerPartNumberText = new Text(container, SWT.BORDER
				| SWT.SINGLE);
		manufacturerPartNumberText.setText("");
		manufacturerPartNumberText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd5 = new GridData(GridData.FILL_HORIZONTAL);
		manufacturerPartNumberText.setLayoutData(gd5);

		Label developerContactDetailsLabel = new Label(container, SWT.NULL);
		developerContactDetailsLabel.setText("Developer contact details");

		developerContactDetailsText = new Text(container, SWT.BORDER
				| SWT.SINGLE);
		developerContactDetailsText.setText("");
		developerContactDetailsText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd6 = new GridData(GridData.FILL_HORIZONTAL);
		developerContactDetailsText.setLayoutData(gd6);

		Label applicationURLLabel = new Label(container, SWT.NULL);
		applicationURLLabel.setText("Application URL");

		applicationURLText = new Text(container, SWT.BORDER | SWT.SINGLE);
		applicationURLText.setText("");
		applicationURLText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd7 = new GridData(GridData.FILL_HORIZONTAL);
		applicationURLText.setLayoutData(gd7);

		Label listPriceLabel = new Label(container, SWT.NULL);
		listPriceLabel.setText("List price");

		listPriceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		listPriceText.setText("");
		listPriceText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd8 = new GridData(GridData.FILL_HORIZONTAL);
		listPriceText.setLayoutData(gd8);

		Label hardwareRequirementsLabel = new Label(container, SWT.NULL);
		hardwareRequirementsLabel.setText("Hardware requirements");

		hardwareRequirementsText = new Text(container, SWT.BORDER | SWT.SINGLE);
		hardwareRequirementsText.setText("");
		hardwareRequirementsText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd9 = new GridData(GridData.FILL_HORIZONTAL);
		hardwareRequirementsText.setLayoutData(gd9);

		Label softwareRequirementsLabel = new Label(container, SWT.NULL);
		softwareRequirementsLabel.setText("Software requirements");

		softwareRequirementsText = new Text(container, SWT.BORDER | SWT.SINGLE);
		softwareRequirementsText.setText("");
		softwareRequirementsText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!applicationNameText.getText().isEmpty()
						&& !applicationShortDescriptionText.getText().isEmpty()
						&& !applicationFullDescriptionText.getText().isEmpty()
						&& !keywordsText.getText().isEmpty()
						&& !manufacturerText.getText().isEmpty()
						&& !developerContactDetailsText.getText().isEmpty()
						&& !manufacturerPartNumberText.getText().isEmpty()
						&& !applicationURLText.getText().isEmpty()
						&& !listPriceText.getText().isEmpty()
						&& !hardwareRequirementsText.getText().isEmpty()
						&& !softwareRequirementsText.getText().isEmpty()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		});
		GridData gd10 = new GridData(GridData.FILL_HORIZONTAL);
		softwareRequirementsText.setLayoutData(gd10);

		Label categoryLabel = new Label(container, SWT.NULL);
		categoryLabel.setText("Category");

		categoryCombo = new Combo(container, SWT.READ_ONLY);
		for (int i = 0; i < categoryList.size(); i++) {
			categoryCombo.add(categoryList.get(i).getCategoryName());
		}
		if (categoryCombo.getItemCount() != 0)
			categoryCombo.select(0);
		GridData gd11 = new GridData(GridData.FILL_HORIZONTAL);
		categoryCombo.setLayoutData(gd11);

		// Required to avoid an error in the system
		setControl(container);
		setPageComplete(false);
	}

	public String getApplicationNameText() {
		return applicationNameText.getText();
	}

	public List<ApplicationCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ApplicationCategory> categoryList) {
		this.categoryList = categoryList;
	}

	public Text getApplicationShortDescriptionText() {
		return applicationShortDescriptionText;
	}

	public void setApplicationShortDescriptionText(
			Text applicationShortDescriptionText) {
		this.applicationShortDescriptionText = applicationShortDescriptionText;
	}

	public Text getApplicationFullDescriptionText() {
		return applicationFullDescriptionText;
	}

	public void setApplicationFullDescriptionText(
			Text applicationFullDescriptionText) {
		this.applicationFullDescriptionText = applicationFullDescriptionText;
	}

	public Text getKeywordsText() {
		return keywordsText;
	}

	public void setKeywordsText(Text keywordsText) {
		this.keywordsText = keywordsText;
	}

	public Text getManufacturerText() {
		return manufacturerText;
	}

	public void setManufacturerText(Text manufacturerText) {
		this.manufacturerText = manufacturerText;
	}

	public Text getManufacturerPartNumberText() {
		return manufacturerPartNumberText;
	}

	public void setManufacturerPartNumberText(Text manufacturerPartNumberText) {
		this.manufacturerPartNumberText = manufacturerPartNumberText;
	}

	public Text getApplicationURLText() {
		return applicationURLText;
	}

	public void setApplicationURLText(Text applicationURLText) {
		this.applicationURLText = applicationURLText;
	}

	public Text getListPriceText() {
		return listPriceText;
	}

	public void setListPriceText(Text listPriceText) {
		this.listPriceText = listPriceText;
	}

	public Text getHardwareRequirementsText() {
		return hardwareRequirementsText;
	}

	public void setHardwareRequirementsText(Text hardwareRequirementsText) {
		this.hardwareRequirementsText = hardwareRequirementsText;
	}

	public Text getSoftwareRequirementsText() {
		return softwareRequirementsText;
	}

	public void setSoftwareRequirementsText(Text softwareRequirementsText) {
		this.softwareRequirementsText = softwareRequirementsText;
	}

	public void setApplicationNameText(Text applicationNameText) {
		this.applicationNameText = applicationNameText;
	}

	public Combo getCategoryCombo() {
		return categoryCombo;
	}

	public void setCategoryCombo(Combo categoryCombo) {
		this.categoryCombo = categoryCombo;
	}

	public Text getDeveloperContactDetailsText() {
		return developerContactDetailsText;
	}

	public void setDeveloperContactDetailsText(Text developerContactDetailsText) {
		this.developerContactDetailsText = developerContactDetailsText;
	}

}
