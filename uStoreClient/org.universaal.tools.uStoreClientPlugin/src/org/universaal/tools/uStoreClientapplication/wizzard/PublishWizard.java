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
	private String applicationId;
	private String username;
	private String password;

	public PublishWizard(String applicationId,String username,String password) {
		super();
		setNeedsProgressMonitor(true);
		this.applicationId=applicationId;
		this.username=username;
		this.password=password;
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
		
		one = new MyPageOne(username,password);
		two = new MyPageTwo();
		three = new MyPageThree();
		
		addPage(one);
		addPage(two);
		addPage(three);
		

		two.setCategoryList(categoryList);
	}

	public boolean performFinish() {
		metadata = new Metadata();
		//page one
		metadata.setUsername(one.getUsernameText().getText());
		metadata.setPassword(one.getPasswordText().getText());
		//page two
		metadata.setApplicationId(applicationId);
		metadata.setApplicationName(two.getApplicationNameText().getText());
		metadata.setApplicationShortDescription(two.getShortDescriptionText().getText());
		metadata.setApplicationFullDescription(two.getDescriptionText().getText());
		metadata.setKeywords(two.getKeywordsText().getText());
		metadata.setDeveloperName(two.getDeveloperNameText().getText());
		metadata.setDeveloperEmail(two.getDeveloperEmailText().getText());
		metadata.setDeveloperPhone(two.getDeveloperPhoneText().getText());
		metadata.setOrganizationName(two.getOrganizationNameText().getText());
		metadata.setOrganizationURL(two.getOrganizationURLText().getText());
		metadata.setOrganizationCertificate(two.getOrganizationCertificateText().getText());
		metadata.setURL(two.getURLText().getText());
		metadata.setParentCategoryId(two.getCategoryList().get(two.getCombo().getSelectionIndex()).getCategoryNumber());
		//page three
		metadata.setFullImageFileName(three.getImageName());
		metadata.setFullImage(three.getFileImageByte());
		metadata.setThumbnailImageFileName(three.getThumbnailName());
		metadata.setThumbnail(three.getThumbnailImageByte());
		metadata.setListPrice(three.getListPriceText().getText());
		metadata.setVersion(three.getVersionText().getText());
		metadata.setVersionNotes(three.getVersionNotesText().getText());
		metadata.setFileName(three.getFileName());
		metadata.setFile(three.getFileByte());
		metadata.setServiceLevelAgreement(three.getServiceLevelAgreementText().getText());
		metadata.setRequirements(three.getRequirementsText().getText());
		metadata.setLicenses(three.getLicensesText().getText());
		metadata.setCapabilities(three.getCapabilitiesText().getText());
		if(three.getReadyForPurchaseCombo().getItem(three.getReadyForPurchaseCombo().getSelectionIndex()).equals("Yes")){
			metadata.setForPurchase(true);
		}else
			metadata.setForPurchase(false);
		return true;
	}

	
	
	
	@Override
	public boolean canFinish() {
		if(!one.getUsernameText().getText().equals("")&&
				!one.getPasswordText().getText().equals("")&&
				!two.getApplicationNameText().getText().equals("")&&
				!three.getVersionText().getText().equals("")&&
				!three.getVersionNotesText().getText().equals("")&&
				three.getFileByte()!=null
				){
			return true;
			
		}else
			return false;
	}

	public List<ApplicationCategory> getCategoryList() {
		return categoryList;
	}

	public Metadata getMetadata() {
		return metadata;
	}

}
