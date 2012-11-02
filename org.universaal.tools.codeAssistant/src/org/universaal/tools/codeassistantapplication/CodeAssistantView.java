package org.universaal.tools.codeassistantapplication;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.EditorInputTransfer;
import org.eclipse.ui.part.ViewPart;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import org.universaal.tools.codeassistantapplication.editor.CodeAssistantEditor;
import org.universaal.tools.codeassistantapplication.editor.CodeAssistantEditorInput;
import org.universaal.tools.codeassistantapplication.editor.model.Entity;
import org.universaal.tools.codeassistantapplication.editor.model.TreeNode;

public class CodeAssistantView extends ViewPart{
	public static final String ID = "org.universAAL.codeassistant.CodeAssistantView";
	//public static final String ID = "org.universaal.tools.codeassistantapplication.CodeAssistantView";
	private StructuredViewer viewer;
	@Override
	public void createPartControl(Composite parent) {
	    //super(parent, SWT.NULL);
		this.viewer = new TreeViewer(parent);
	    FillLayout compositeLayout = new FillLayout();
	    parent.setLayout(compositeLayout);

	    viewer.setContentProvider(new ITreeContentProvider() {
	    	public Object[] getChildren(Object parentElement) {
	    		return ((TreeNode) parentElement).getChildren().toArray();
	    	}

	    	public Object getParent(Object element) {
	    		return ((TreeNode) element).getParent();
	    	}

	    	public boolean hasChildren(Object element) {
		        return ((TreeNode) element).getChildren().size() > 0;
	    	}

	    	public Object[] getElements(Object inputElement) {
		        return ((TreeNode) inputElement).getChildren().toArray();
	    	}

	    	public void dispose() { }

	    	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) { }
	    });
	    viewer.setLabelProvider(new LabelProvider(){
	    	@Override
	    	public Image getImage(Object element) {
	    		return ((TreeNode) element).getImage();
	    	}
	    });
	    
	    viewer.setInput(TreeNode.getInstance());
	    this.initDragAndDrop(this.viewer);
	}
	
	protected void initDragAndDrop(final StructuredViewer viewer) {
		int operations =  DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[]{EditorInputTransfer.getInstance()};
		
		DragSourceListener listener = new DragSourceAdapter() {
			@Override
			public void dragStart(DragSourceEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				event.doit = (selection.size() > 0) ? true : false;
				super.dragStart(event);
			}
			@Override
			public void dragSetData(DragSourceEvent event) {
				if (EditorInputTransfer.getInstance().isSupportedType(event.dataType)) {
					IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
					Object[] selectedObjects = selection.toArray();
					if (selectedObjects.length > 0) {
						EditorInputTransfer.EditorInputData[] inputs = new EditorInputTransfer.EditorInputData[selectedObjects.length];
						for (int i = 0; i < selectedObjects.length; i++) {
							if (selectedObjects[i] instanceof TreeNode) {
								TreeNode tn = (TreeNode) selectedObjects[i];
								Entity q = (Entity) tn.getEntity();
								EditorInputTransfer.EditorInputData data = EditorInputTransfer.createEditorInputData(CodeAssistantEditor.ID, new CodeAssistantEditorInput(q));
								if (data != null) {
									inputs[i] = data;
								}
							}
						}
						event.data = inputs;
						return;
					}
				}
				event.doit = false;
			}
			@Override
			public void dragFinished(DragSourceEvent event) {
				//System.out.println("DRAG STOP");
			}
		};
	
		viewer.addDragSupport(operations, transferTypes, listener);
	}
	
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}	
}