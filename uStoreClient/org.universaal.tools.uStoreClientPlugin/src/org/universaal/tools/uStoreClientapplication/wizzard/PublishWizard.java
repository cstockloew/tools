package org.universaal.tools.uStoreClientapplication.wizzard;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.universaal.commerce.ustore.tools.AALApplicationManager;
import org.universaal.commerce.ustore.tools.AALApplicationManagerServiceLocator;
import org.universaal.commerce.ustore.tools.CatalogManager;
import org.universaal.commerce.ustore.tools.CatalogManagerServiceLocator;

import org.universaal.tools.uStoreClientapplication.actions.Application;
import org.universaal.tools.uStoreClientapplication.actions.ApplicationCategory;
import org.universaal.tools.uStoreClientapplication.actions.ApplicationCategoryParser;
import org.universaal.tools.uStoreClientapplication.actions.Metadata;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class PublishWizard extends Wizard {
	protected MyPageOne one;
	protected MyPageTwoNew two;
	// protected MyPageThree three;
	protected MyPageThreeUAAP threeUAAP;
	protected MyPageThreeApplication threeApplication;
	private List<ApplicationCategory> categoryList;
	private Metadata metadata;
	private String applicationId;
	private String username;
	private String password;
	private boolean isUAAP = false;
	private String pathForUAPPFile;
	
	
	public PublishWizard(String applicationId, String username, String password,String pathForUAPPFile) {
		super();
		setNeedsProgressMonitor(true);
		this.applicationId = applicationId;
		this.username = username;
		this.password = password;
		ImageDescriptor image = AbstractUIPlugin.imageDescriptorFromPlugin(
				"org.universaal.tools.uStoreClientPlugin", //$NON-NLS-1$
				"icons/ic-uAAL-hdpi.png"); //$NON-NLS-1$
		setDefaultPageImageDescriptor(image);
		setWindowTitle("Publish application to uStore");
		this.pathForUAPPFile=pathForUAPPFile;
	}

	public void addPages() {

		one = new MyPageOne(username,password);
		two = new MyPageTwoNew(pathForUAPPFile);
		// three = new MyPageThree();
		threeUAAP = new MyPageThreeUAAP();
		threeApplication = new MyPageThreeApplication();
		addPage(one);
		addPage(two);
		// addPage(three);
		addPage(threeUAAP);
		addPage(threeApplication);
		
	}

	public boolean performFinish() {
		metadata = new Metadata();
		if (isUAAP) {
			metadata.setUsername(one.getUsernameText().getText());
			metadata.setPassword(one.getPasswordText().getText());
			metadata.setApplicationId(applicationId);
			metadata.setApplicationFullDescription(threeUAAP
					.getDescriptionText().getText());
			metadata.setURL(threeUAAP.getURLText().getText());
			String parentCategoryId = two.getCategoryList()
					.get(two.getCombo().getSelectionIndex())
					.getCategoryNumber();
			metadata.setParentCategoryId(parentCategoryId);
			metadata.setFullImageFileName(threeUAAP.getImageFileName());
			metadata.setFullImage(threeUAAP.getImageFile());
			metadata.setThumbnailImageFileName(threeUAAP.getThumbnailFileName());
			metadata.setThumbnail(threeUAAP.getThumbnailFile());
			metadata.setListPrice(threeUAAP.getPriceText().getText());
			metadata.setVersion(two.getVersionText().getText());
			metadata.setVersionNotes(two.getVersionDescriptionText().getText());
			metadata.setFileName(two.getFileName());
			metadata.setFile(two.getFileByte());
			if (threeUAAP
					.getReadyForPurchaseCombo()
					.getItem(
							threeUAAP.getReadyForPurchaseCombo()
									.getSelectionIndex()).equals("Yes")) {
				metadata.setForPurchase(true);
			} else
				metadata.setForPurchase(false);

		} else {
			metadata.setUsername(one.getUsernameText().getText());
			metadata.setPassword(one.getPasswordText().getText());
			// page two
			metadata.setApplicationId(applicationId);
			metadata.setApplicationName(threeApplication.getNameText()
					.getText());
			metadata.setApplicationShortDescription(threeApplication
					.getShortDescriptionText().getText());
			metadata.setApplicationFullDescription(threeApplication
					.getDescriptionText().getText());
			metadata.setKeywords(threeApplication.getKeywordsText().getText());
			metadata.setDeveloperName(threeApplication.getDeveloperNameText()
					.getText());
			metadata.setDeveloperEmail(threeApplication.getDeveloperEmailText()
					.getText());
			metadata.setDeveloperPhone(threeApplication.getDeveloperPhoneText()
					.getText());
			metadata.setOrganizationName(threeApplication
					.getOrganizationNameText().getText());
			metadata.setOrganizationURL(threeApplication
					.getOrganizationURLText().getText());
			metadata.setOrganizationCertificate(threeApplication
					.getOrganizationCertificateText().getText());
			metadata.setURL(threeApplication.getURLText().getText());
			String parentCategoryId = two.getCategoryList()
					.get(two.getCombo().getSelectionIndex())
					.getCategoryNumber();
			metadata.setParentCategoryId(parentCategoryId);
			// page three
			metadata.setFullImageFileName(threeApplication.getImageFileName());
			metadata.setFullImage(threeApplication.getImageFile());
			metadata.setThumbnailImageFileName(threeApplication
					.getThumbnailFileName());
			metadata.setThumbnail(threeApplication.getThumbnailFile());
			metadata.setListPrice(threeApplication.getPriceText().getText());
			metadata.setVersion(two.getVersionText().getText());
			metadata.setVersionNotes(two.getVersionDescriptionText().getText());
			metadata.setFileName(two.getFileName());
			metadata.setFile(two.getFileByte());
			metadata.setServiceLevelAgreement(threeApplication
					.getServiceLevelAgreementText().getText());
			metadata.setRequirements(threeApplication.getRequirementsText()
					.getText());
			metadata.setLicenses(threeApplication.getLicensesText().getText());
			metadata.setCapabilities(threeApplication.getCapabilitiesText()
					.getText());
			if (threeApplication
					.getReadyForPurchaseCombo()
					.getItem(
							threeApplication.getReadyForPurchaseCombo()
									.getSelectionIndex()).equals("Yes")) {
				metadata.setForPurchase(true);
			} else
				metadata.setForPurchase(false);

		}

		return true;
	}

	@Override
	public boolean canFinish() {
		if (isUAAP) {
			if (!one.getUsernameText().getText().equals("")
					&& !one.getPasswordText().getText().equals("")
					&& !two.getFileName().equals("")
					&& two.getFileByte() != null
					&& !two.getVersionText().getText().equals("")
					&& !two.getVersionDescriptionText().getText().equals("")
					&& two.getCategoryList().size() != 0
					) {
				return true;

			} else
				return false;
		} else {
			if (!one.getUsernameText().getText().equals("")
					&& !one.getPasswordText().getText().equals("")
					&& !two.getFileName().equals("")
					&& two.getFileByte() != null
					&& !two.getVersionText().getText().equals("")
					&& !two.getVersionDescriptionText().getText().equals("")
					&& two.getCategoryList().size() != 0
					&& !threeApplication.getNameText().getText().equals("")) {
				return true;

			} else
				return false;
		}

	}

	public List<ApplicationCategory> getCategoryList() {
		return categoryList;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		// TODO Auto-generated method stub

		if (page instanceof MyPageOne) {
			try {
				CatalogManagerServiceLocator loc = new CatalogManagerServiceLocator();
				CatalogManager man = loc.getCatalogManagerPort();
				String catalog = man.getAALApplicationsCategories(one
						.getUsernameText().getText(), one.getPasswordText()
						.getText());
				ApplicationCategoryParser applicationCategoryParser = new ApplicationCategoryParser(
						catalog);
				applicationCategoryParser.createCategoryList();
				categoryList = applicationCategoryParser.getCategoryList();
				two.setCategoryList(categoryList);
			} catch (Exception ex) {
				ex.printStackTrace();
				MessageDialog.openError(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "Error",
						ex.toString());
				return null;
			}

		} else if (page instanceof MyPageTwoNew) {
			String str = ((MyPageTwoNew) page).getFileName();

			if (str.toLowerCase().trim().endsWith("uapp")) { 
				isUAAP = true;
				// get parent category id from second page

				

				
				return threeUAAP;
			} else {
				isUAAP = false;
				return threeApplication;
			}
		}
		return super.getNextPage(page);
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		// TODO Auto-generated method stub
		return super.getPreviousPage(page);
	}

	private List<Application> parseXml(String xml) {
		List<Application> list = new ArrayList<Application>();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = loadXMLFromString(xml);
			NodeList nList = doc.getElementsByTagName("application");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Application app = new Application();
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					app.setId(getTagValue("id", eElement));
					app.setName(getTagValue("name", eElement));

					list.add(app);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}

	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	private Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	public boolean isUAAP() {
		return isUAAP;
	}

	public void setUAAP(boolean isUAAP) {
		this.isUAAP = isUAAP;
	}

}
