package org.universaal.tools.codeassistantapplication.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import org.universaal.tools.codeassistantapplication.editor.CodeAssistantEditor;
import org.universaal.tools.codeassistantapplication.editor.CodeAssistantEditorInput;
import org.universaal.tools.codeassistantapplication.editor.model.Entity;
import org.universaal.tools.codeassistantapplication.CodeAssistantView;

public class CallEditor extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    System.out.println("called");
    // Get the view
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
    IWorkbenchPage page = window.getActivePage();
    CodeAssistantView view = (CodeAssistantView) page.findView(CodeAssistantView.ID);
    // Get the selection
    ISelection selection = view.getSite().getSelectionProvider().getSelection();
    if (selection != null && selection instanceof IStructuredSelection) {
      Object obj = ((IStructuredSelection) selection).getFirstElement();
      // If we had a selection lets open the editor
      System.out.println("-->"+obj.toString());
      if (obj != null) {
    	  Entity anItem = (Entity)obj;
      	  CodeAssistantEditorInput input = new CodeAssistantEditorInput(anItem.getId());
      	  try {
      		  page.openEditor(input, CodeAssistantEditor.ID);

      	  } catch (PartInitException e) {
      		  throw new RuntimeException(e);
      	  }
      }
    }

    return null;
  }

} 