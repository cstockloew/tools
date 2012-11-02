package org.universaal.tools.uStoreClientapplication.wizzard;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.universaal.commerce.ustore.tools.CatalogManager;
import org.universaal.commerce.ustore.tools.CatalogManagerServiceLocator;

import org.universaal.tools.uStoreClientapplication.actions.ApplicationCategory;
import org.universaal.tools.uStoreClientapplication.actions.ApplicationCategoryParser;
import org.universaal.tools.uStoreClientapplication.actions.Metadata;

public class PublishWizard extends Wizard {
	static private String USTORE_USERNAME = "admin";
	static private String USTORE_PASSWORD = "bigim222";
	protected MyPageOne one;
	protected MyPageTwo two;
	protected MyPageThree three;
	private List<ApplicationCategory> categoryList;
	private Metadata metadata;

	public PublishWizard() {
		super();
		setNeedsProgressMonitor(true);
		ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(
				"org.universaal.tools.uStoreClientPlugin", //$NON-NLS-1$
				"icons/ic-uAAL-hdpi.png"); //$NON-NLS-1$
		setDefaultPageImageDescriptor(image);
		setWindowTitle("Publish application to uStore");

		try {
			CatalogManagerServiceLocator loc = new CatalogManagerServiceLocator();
			CatalogManager man = loc.getCatalogManagerPort();
			String catalog = man.getAALApplicationsCategories(USTORE_USERNAME,
					USTORE_PASSWORD);
			ApplicationCategoryParser applicationCategoryParser = new ApplicationCategoryParser(
					catalog);
			applicationCategoryParser.createCategoryList();
			categoryList = applicationCategoryParser.getCategoryList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addPages() {
		one = new MyPageOne();
		two = new MyPageTwo();
		three = new MyPageThree();
		addPage(one);
		addPage(three);
		addPage(two);

		two.setCategoryList(categoryList);
	}

	public boolean performFinish() {
		metadata = new Metadata();
		metadata.setUsername(one.getUsernameText().getText());
		metadata.setPassword(one.getPasswordText().getText());
		metadata.setApplicationName(two.getApplicationNameText());
		metadata.setApplicationShortDescription(two
				.getApplicationShortDescriptionText().getText());
		metadata.setApplicationFullDescription(two
				.getApplicationFullDescriptionText().getText());
		metadata.setKeywords(two.getKeywordsText().getText());
		metadata.setManufacturer(two.getManufacturerText().getText());
		metadata.setManufacturerPartNumber(two.getManufacturerPartNumberText()
				.getText());
		metadata.setApplicationURL(two.getApplicationURLText().getText());

		metadata.setCategory(categoryList.get(
				two.getCategoryCombo().getSelectionIndex()).getCategoryNumber());
		metadata.setVersionNotes(three.getVersionNotesText().getText());
		metadata.setForPurchase(Boolean.valueOf(three.getIsForPurchasecombo()
				.getText()));
		metadata.setFullImage(three.getFullImageByte());
		metadata.setThumbnail(three.getThumbnailImageByte());
		metadata.setListPrice(two.getListPriceText().getText());
		metadata.setThumbnailImageFileName(three.getThumbnailName());
		metadata.setFullImageFileName(three.getImageName());
		// metadata.setArtifactId(three.getArtifactIdText().getText());
		// metadata.setGroupId(three.getGroupIdText().getText());
		metadata.setVersion(three.getVersionText().getText());
		metadata.setFileName(three.getFileName());
		metadata.setFile(three.getFileImageByte());
		metadata.setHardwareRequirements(two.getHardwareRequirementsText()
				.getText());
		metadata.setSoftwareRequirements(two.getSoftwareRequirementsText()
				.getText());
		metadata.setDeveloperContactDetails(two
				.getDeveloperContactDetailsText().getText());
		java.util.Calendar date = java.util.Calendar.getInstance();
		metadata.setUploadTimeToNexus(date);

		return true;
	}

	public List<ApplicationCategory> getCategoryList() {
		return categoryList;
	}

	public Metadata getMetadata() {
		return metadata;
	}

}
