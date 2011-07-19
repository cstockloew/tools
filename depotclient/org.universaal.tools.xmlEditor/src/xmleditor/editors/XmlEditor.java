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
	private ProjectModel model;
	private Document doc;
	private IEditorInput input;
	

	private FieldsPage fields;
	private SourcePage source;

	public boolean isPageModified;

	public XmlEditor() {
		super();
//		isDirty=false;
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
		if (page == source) {
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
		int index;
		source = new SourcePage(this, model, doc);
		fields = new FieldsPage(this, model, getContainer());
		
		try {
			index = addPage(source, getEditorInput());
			setPageText(index, "Source");
			index = addPage(fields);
			setPageText(index, "Fields");
			setActivePage(1);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			if(isDirty()){
				source.updateXmlFromModel();
			}
			break;
		case 1:
			if(isPageModified){
				source.updateModelFromXml();
				fields.setFields();
			}
			break;
		}
		isPageModified=false;
		super.pageChange(newPage);
	}

	/**
	 * Is called when the user saves.
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		if(getActivePage() == 0){
			source.updateModelFromXml();
			source.updateXmlFromModel();
			fields.setFields();
		}else{
			source.updateXmlFromModel();
		}
		isPageModified=false;
		source.doSave(monitor);
		
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
			source.updateModelFromXml();
			source.updateXmlFromModel();
		}else{
			source.updateXmlFromModel();
		}
		isPageModified=false;
		source.doSaveAs();
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

	public void fieldsModified(){
		boolean wasDirty = isDirty();
		isPageModified = true;
		if(!wasDirty){
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}
}
