package xmleditor.editors;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.parsers.DOMParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.core.internal.provisional.contenttype.ContentTypeIdForXML;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import xmleditor.model.ProjectModel;

public class XmlEditor extends MultiPageEditorPart implements IResourceChangeListener{

	public static final String ID = "xmleditor.editors.EditorPart1"; //$NON-NLS-1$
	protected StructuredTextEditor structured;
	private ProjectModel model;
	private Text projectName;
	private Text developerName;
	private Text date, url, svnUrl, tags;
	private StyledText description;
	private Document doc;
	private IEditorInput input;

	private boolean isDirty, isPageModified;

	public XmlEditor() {
		super();
		isDirty=false;
		isPageModified = false;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
		input = editorInput;
		parseInput();
	}

	@Override
	protected IEditorSite createSite(IEditorPart page) {
		IEditorSite site = null;
		if (page == structured) {
			site = new MultiPageEditorSite(this, page) {
				public String getId() {
					// Sets this ID so nested editor is configured for XML source
					return ContentTypeIdForXML.ContentTypeID_XML + ".source"; //$NON-NLS-1$;
				}
			};
		}
		else {
			site = super.createSite(page);
		}
		return site;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		System.out.println("Resource changed");
	}

	void updateTitle(){
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

	@Override
	protected void createPages() {
		createPage2();
		createPage1();
		setActivePage(1);
	}



	/**
	 * Creates the page containing the textfields. This is the active page when
	 * the editor opens.
	 */
	void createPage1(){

		FieldListener listen = new FieldListener();

		Composite container = new Composite(getContainer(), SWT.NONE);
		container.setLayout(new GridLayout(2, false));

		Label lblProjectName = new Label(container, SWT.NONE);
		lblProjectName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProjectName.setText("Project Name");

		projectName = new Text(container, SWT.BORDER);
		projectName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblDeveloper = new Label(container, SWT.NONE);
		lblDeveloper.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDeveloper.setText("Developer");

		developerName = new Text(container, SWT.BORDER);
		developerName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		Label lblDate = new Label(container, SWT.NONE);
		lblDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDate.setText("Date");

		date = new Text(container, SWT.BORDER);
		date.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		Label lblUrl = new Label(container, SWT.NONE);
		lblUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUrl.setText("URL");

		url = new Text(container, SWT.BORDER);
		url.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		Label lblSvnUrl = new Label(container, SWT.NONE);
		lblSvnUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSvnUrl.setText("SVN URL");

		svnUrl = new Text(container, SWT.BORDER);
		svnUrl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		Label lblTags = new Label(container, SWT.NONE);
		lblTags.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTags.setText("Tags");
		lblTags.setToolTipText("Enter project tags, separated by commas. E.g. \"tag, tag2\"");

		tags = new Text(container, SWT.BORDER);
		tags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));


		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setText("Description");

		description = new StyledText(container, SWT.BORDER | SWT.WRAP);
		description.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));



		int index = addPage(container);
		setPageText(index, "Forms");

		setFields();

		description.addModifyListener(listen);
		svnUrl.addModifyListener(listen);
		url.addModifyListener(listen);
		date.addModifyListener(listen);
		developerName.addModifyListener(listen);
		projectName.addModifyListener(listen);
		tags.addModifyListener(listen);
	}


	/**
	 * Creates the page containing the source of the xml-file
	 */
	void createPage2(){
		try{
			structured = new StructuredTextEditor();
			int index = addPage(structured, getEditorInput());
			setPageText(index, "Source");
		}catch(Exception e){

		}

	}

	/**
	 * Is called when the user changes page. If there has been any changes, the
	 * model is updated, read and used to update the information on the new 
	 * page.
	 */
	@Override
	protected void pageChange(int newPage){
		switch(newPage){
		case 0:
			if(isDirty){
				updateXmlFromModel();
			}
			break;
		case 1:
			if(isPageModified){
				updateFieldsFromXml();
			}
			break;
		}
		isDirty=false;
		super.pageChange(newPage);
	}

	/**
	 * Is called when the user saves.
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		if(getActivePage() == 0){
			updateFieldsFromXml();
			updateXmlFromModel();
		}else{
			updateXmlFromModel();
		}
		isPageModified=false;
		structured.doSave(monitor);
	}

	/**
	 * Returns true so that "Save as..." is allowed
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Is called when the user selects "Save As..."
	 */
	@Override
	public void doSaveAs() {
		if(getActivePage() == 0){
			updateFieldsFromXml();
			updateXmlFromModel();
		}else{
			updateXmlFromModel();
		}
		isPageModified=false;
		structured.doSaveAs();
	}

	/**
	 * Is called when the editor is started. Parses the input xml-file and
	 * creates a ProjectModel-object containing all the information in the xml.
	 * If the xml-file is an empty file, an exception is caught and an empty
	 * project model is created, so that the user can fill in information,
	 * and the correct xml will be generated.
	 */
	public void parseInput(){
		if(input instanceof FileEditorInput){
			IFile file = ((FileEditorInput)input).getFile();
			try {
				InputStream is = file.getContents();
				DOMParser parser = new DOMParser();
				parser.parse(new InputSource(is));
				doc = parser.getDocument();
				doc.normalizeDocument();

			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				
				try {
					Node node, project;
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					doc = builder.newDocument();
					doc.normalizeDocument();
					
					project = doc.createElement("project");
					doc.appendChild(project);
					node = doc.createElement("name");
					project.appendChild(node);
					node = doc.createElement("developer");
					project.appendChild(node);
					node = doc.createElement("date");
					project.appendChild(node);
					node = doc.createElement("url");
					project.appendChild(node);
					node = doc.createElement("svnurl");
					project.appendChild(node);
					node = doc.createElement("description");
					project.appendChild(node);
					node = doc.createElement("tags");
					project.appendChild(node);
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		NodeList nList;
		Element node;
		String name, developer, date, url, svnurl, desc;
		ArrayList<String> tags = new ArrayList<String>();
		try{
			Node project = doc.getElementsByTagName("project").item(0);
			
			if(project==null){
				System.out.println("Project er lik null");
				project = doc.createElement("project");
				doc.appendChild(project);
			}

			nList = doc.getElementsByTagName("name");
			node = (Element) nList.item(0);
			if(node!=null)
				name =node.getFirstChild().getNodeValue();
			else{
				project.appendChild(doc.createElement("name"));
				name = "";
			}
				
			nList = doc.getElementsByTagName("developer");
			node = (Element) nList.item(0);
			if(node!=null)
				developer = node.getFirstChild().getNodeValue();
			else{
				project.appendChild(doc.createElement("developer"));
				developer = "";
			}
			
			nList = doc.getElementsByTagName("date");
			node = (Element) nList.item(0);
			if(node!=null)
				date = node.getFirstChild().getNodeValue();
			else{
				project.appendChild(doc.createElement("date"));
				date = "";
			}

			nList = doc.getElementsByTagName("url");
			node = (Element) nList.item(0);
			if(node!=null)
				url = node.getFirstChild().getNodeValue();
			else{
				project.appendChild(doc.createElement("url"));
				url = "";
			}

			nList = doc.getElementsByTagName("svnurl");
			node = (Element) nList.item(0);
			if(node!=null)
				svnurl = node.getFirstChild().getNodeValue();
			else{
				project.appendChild(doc.createElement("svnurl"));
				svnurl = "";
			}
			nList = doc.getElementsByTagName("description");
			node = (Element) nList.item(0);
			if(node!=null)
				desc = node.getFirstChild().getNodeValue();
			else{
				project.appendChild(doc.createElement("description"));
				desc = "";
			}
			
			tags=parseTagsFromXML();

			System.out.println("Nonempty model created.");
			model = new ProjectModel(name, developer, date, url, svnurl, desc, tags, this);
		}catch(DOMException e){
			e.printStackTrace();
			
		}catch(Exception e){
			//TODO Handle other information contained in the xml
			e.printStackTrace();
			System.out.println("Empty model created");
			model = new ProjectModel("","","","","","",tags,this);
		}
	}

	/**
	 * Is called when the user switches from the source to the textfields-page.
	 * The xml is read and the model updated with any changes. Then the 
	 * textfields are updated by reading the model.
	 */
	public void updateFieldsFromXml(){

		try {
			String string = structured.getDocumentProvider().getDocument(structured.getEditorInput()).get();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(string)));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		NodeList nList;
		Element node;
		String name, developer, date, url, svnurl, desc;
		ArrayList<String> tags = new ArrayList<String>();

		nList = doc.getElementsByTagName("name");
		node = (Element) nList.item(0);
		name =node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("developer");
		node = (Element) nList.item(0);
		developer = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("date");
		node = (Element) nList.item(0);
		date = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("url");
		node = (Element) nList.item(0);
		url = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("svnurl");
		node = (Element) nList.item(0);
		svnurl = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("description");
		node = (Element) nList.item(0);
		desc = node.getFirstChild().getNodeValue();

		nList = doc.getElementsByTagName("tag");
		for(int i=0; i<nList.getLength(); i++){
			node = (Element) nList.item(i);
			tags.add(node.getFirstChild().getNodeValue());
		}

		if(!name.equals(model.getpName()))
			model.setpName(name);
		if(!developer.equals(model.getpDev()))
			model.setpDev(developer);
		if(!date.equals(model.getpDate()))
			model.setpDate(date);
		if(!url.equals(model.getpUrl()))
			model.setpUrl(url);
		if(!svnurl.equals(model.getpSvnUrl()))
			model.setpSvnUrl(svnurl);
		if(!desc.equals(model.getpDesc()))
			model.setpDesc(desc);

		model.setpTags(tags);

		setFields();
	}

	/**
	 * Reads the project model and updates the contents of textfields.
	 */
	public void setFields(){
		projectName.setText(model.getpName());
		developerName.setText(model.getpDev());
		date.setText(model.getpDate());
		url.setText(model.getpUrl());
		svnUrl.setText(model.getpSvnUrl());
		description.setText(model.getpDesc());
		tags.setText(getTagsStringFromModel());
	}

	/**
	 * Is called when the user switches from the textfields-page to the 
	 * source-page. The model is read and the contents of the xml-file is 
	 * changed to match the model.
	 */
	public void updateXmlFromModel(){

		NodeList nList;
		Element node;

		try{
			nList = doc.getElementsByTagName("name");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpName());

			nList = doc.getElementsByTagName("developer");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpDev());

			nList = doc.getElementsByTagName("date");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpDate());

			nList = doc.getElementsByTagName("url");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpUrl());

			nList = doc.getElementsByTagName("svnurl");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpSvnUrl());

			nList = doc.getElementsByTagName("description");
			node = (Element) nList.item(0);
			node.setTextContent(model.getpDesc());

			ArrayList<String> tempTags = model.getpTags();
			nList = doc.getElementsByTagName("tags");
			Node oldTags = (Element) nList.item(0);
			Node project = node.getParentNode();

			
			doc.normalizeDocument();
			
			Node newTags = doc.createElement("tags");

			for(int i=0; i<tempTags.size(); i++){
				Element el = doc.createElement("tag");
				el.appendChild(doc.createTextNode(tempTags.get(i)));
				newTags.appendChild(el);
			}
			project.replaceChild(newTags, oldTags);
			

		}catch(Exception e){
			e.printStackTrace();
		}

		try {
			Source source = new DOMSource(doc);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory2 = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = factory2.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			
			transformer.transform(source, result);
			String str = stringWriter.getBuffer().toString();
			structured.getDocumentProvider().getDocument(structured.getEditorInput()).set(str);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * Marks the editor as dirty if changes are made.
	 */
	@Override
	protected void handlePropertyChange(int propertyId){
		if(propertyId == IEditorPart.PROP_DIRTY){
			isPageModified = isDirty();
		}
		super.handlePropertyChange(propertyId);
	}

	public boolean isDirty(){
		return isPageModified || super.isDirty();
	}

	/**
	 * 
	 * Keeps the model updated when changes happen in the textfields.
	 *
	 */
	class FieldListener implements ModifyListener{

		@Override
		public void modifyText(ModifyEvent e) {
			isDirty = true;
			fieldsModified();
			if(e.getSource()==projectName){
				model.setpName(projectName.getText());
			}else if(e.getSource() == developerName){
				model.setpDev(developerName.getText());
			}else if(e.getSource()==date){
				model.setpDate(date.getText());
			}else if(e.getSource()==url){
				model.setpUrl(url.getText());
			}else if(e.getSource()==svnUrl){
				model.setpSvnUrl(svnUrl.getText());
			}else if(e.getSource()==description){
				model.setpDesc(description.getText());
			}else if(e.getSource()==tags)
				model.setpTags(getTagsFromFields());
		}
	}

	/**
	 * Reads the xml, and puts all the tags in an ArrayList<String>
	 * 
	 */
	private ArrayList<String> parseTagsFromXML(){
		NodeList nList;
		Element node;
		ArrayList<String> tags = new ArrayList<String>();

		nList = doc.getElementsByTagName("tag");
		for(int i=0; i<nList.getLength();i++){
			node = (Element) nList.item(i);
			tags.add(node.getFirstChild().getNodeValue());
		}

		return tags;	
	}

	/**
	 * Reads the ArrayList<String> in the model, and constructs a String with
	 * all the tags separated by commas.
	 * 
	 */
	private String getTagsStringFromModel(){
		String result="";
		ArrayList<String> tags = model.getpTags();
		for(int i=0; i<tags.size();i++){
			result += tags.get(i);
			if(i!=tags.size()-1){
				result += ", ";
			}
		}
		return result;
	}

	/**
	 * Reads the tags-textfield and constructs an ArrayList<String> containing
	 * all the tags.
	 * 
	 */
	private ArrayList<String> getTagsFromFields(){
		ArrayList<String> result = new ArrayList<String>();
		String string = tags.getText();
		String tempString="";
		int prevComma=0;
		int nextComma=string.indexOf(',');
		while(nextComma>=0){
			tempString=string.substring(prevComma, nextComma);
			prevComma=nextComma+1;
			nextComma = string.indexOf(',', prevComma);
			result.add(tempString.trim());
		}
		result.add((string.substring(prevComma)).trim());
		return result;
	}

	private void fieldsModified(){
		boolean wasDirty = isDirty();
		isPageModified = true;
		if(!wasDirty){
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}



}
