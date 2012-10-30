package org.universAAL.codeassistant;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.EditorInputTransfer;
import org.eclipse.ui.part.PluginDropAdapter;

//public class CodeAssistantEditorDropAdapter extends PluginDropAdapter {
public class CodeAssistantEditorDropAdapter extends DropTargetAdapter {	
	
	public CodeAssistantEditorDropAdapter(StructuredViewer viewer) {
		super();
		//super(viewer);
	}

	IWorkbenchWindow window;

	public CodeAssistantEditorDropAdapter(IWorkbenchWindow window) {
		this.window = window;
	}
	
	@Override
	public void dragEnter(DropTargetEvent event) {
		// DND.DROP_DEFAULT allows drop target to set default operation.
		if (event.detail == DND.DROP_DEFAULT) {
			if ((event.operations & DND.DROP_COPY) != 0) {
				event.detail = DND.DROP_COPY;
			}
			else {
				event.detail = DND.DROP_NONE;
			}
		}
	}

	@Override
	public void dragLeave(DropTargetEvent event) {
	// Free resources allocated in dragEnter().
	}
	
	@Override
	public void dragOver(DropTargetEvent event) {
		event.feedback = DND.FEEDBACK_NONE;
	}
	
	@Override
	public void drop(DropTargetEvent event) {
		if (EditorInputTransfer.getInstance().isSupportedType(event.currentDataType)) {
			EditorInputTransfer.EditorInputData[] editorInputs=(EditorInputTransfer.EditorInputData[]) event.data;
			for (int i = 0; i < editorInputs.length; i++) {
				if (editorInputs[i] != null) {
					IEditorInput input = editorInputs[i].input;
					String editorId = editorInputs[i].editorId;
					try {
						window.getActivePage().openEditor(input, editorId);
					}
					catch (Exception e) {
						//System.out.println(e.getMessage());
					}
				}
			}
			event.detail = DND.DROP_COPY;
		}
	}

	public boolean validateDrop(Object target, int op, TransferData type) {
		return EditorInputTransfer.getInstance().isSupportedType(type);
	}
}