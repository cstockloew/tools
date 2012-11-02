package org.universaal.tools.codeassistantapplication.editor;

import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorInputTransfer;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import org.universaal.tools.codeassistantapplication.CodeAssistantEditorDropAdapter;

public class CodeAssistantEditor extends EditorPart {
	public static final String ID = "org.universAAL.codeassistant.editor.codeassistanteditor";
	private StructuredViewer viewer;
	private IEditorSite site;

    public CodeAssistantEditor() {
        super();
    }
    
    @Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof CodeAssistantEditorInput)) {
			throw new RuntimeException("Wrong input");
		}
    	this.site = site;
    	setSite(site);
    	setInput((CodeAssistantEditorInput)input);
	}

    
	@Override
	public void createPartControl(Composite parent) {
		String partName = "";
	  	FillLayout layout = new FillLayout();
	  	parent.setLayout(layout);

	  	this.viewer = new ListViewer(parent);
		int ops = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[]{EditorInputTransfer.getInstance()};
		((StructuredViewer)this.viewer).addDropSupport(ops, transfers, new CodeAssistantEditorDropAdapter(this.viewer));
		this.viewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				Vector v = (Vector)inputElement;
		        return v.toArray();
			}
		      
			public void dispose() {
				//System.out.println("Disposing ...");
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				//System.out.println("Input changed: old=" + oldInput + ", new=" + newInput);
			}
		});		
		
		IEditorInput input = this.getEditorInput();
		String text =((CodeAssistantEditorInput)input).getEntity().getText();
		String uri =((CodeAssistantEditorInput)input).getEntity().getUri();
		String property =((CodeAssistantEditorInput)input).getEntity().getProperty();
		String RDFType =((CodeAssistantEditorInput)input).getEntity().getRDFType();
		String range =((CodeAssistantEditorInput)input).getEntity().getRange();
		
		try{
			IEditorPart part = getEditorSite().getPage().getActiveEditor();
			if (!(part instanceof AbstractTextEditor))
			      return;
			ITextEditor editor = (ITextEditor)part;
			IDocumentProvider dp = editor.getDocumentProvider();
			IDocument doc = dp.getDocument(editor.getEditorInput());
			if (editor instanceof ITextEditor) {
				int offset = 0;
				ISelectionProvider selectionProvider = ((ITextEditor)editor).getSelectionProvider();
				ISelection selection = selectionProvider.getSelection();
				if (selection instanceof ITextSelection) {
				    ITextSelection textSelection = (ITextSelection)selection;
				    offset = textSelection.getOffset();
				}
				else
					offset = doc.getLineOffset(doc.getNumberOfLines()-1);
				String pasteText = new String();
				if (property.length()>0)
					pasteText = uri;
				else
					pasteText = text + " " + text.toLowerCase();
				doc.replace(offset, 0, pasteText);
			}			
		}
		catch(Exception e){e.printStackTrace();}

		
		if (text != null) {
			Vector items = new Vector();
			items.add("Name: "+text);
			if (uri!=null)
				if (uri.length()>0)
					items.add("URI: "+uri);
			if (property!=null)
				if (property.length()>0){
					items.add("Property: "+property);
					partName="Property Details";
				}
				else
					partName="Class Details";
			else
				partName="Class Details";
			if (range!=null)
				items.add("Range: "+range);
			if (RDFType!=null)
				items.add("RDFType: "+RDFType);
			setPartName(partName);
			this.viewer.setInput(items);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

	}
	
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isDirty() {
		//firePropertyChange(IEditorPart.PROP_DIRTY);
		return false;
	}
	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
} 
