/*****************************************************************************************
	Copyright 2012-2014 CERTH-HIT, http://www.hit.certh.gr/
	Hellenic Institute of Transport (HIT)
	Centre For Research and Technology Hellas (CERTH)
	
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 *****************************************************************************************/

package org.universaal.tools.codeassistantapplication;

import java.net.URL;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorInputTransfer;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IProgressConstants;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.universaal.tools.codeassistantapplication.editor.CodeAssistantEditor;
import org.universaal.tools.codeassistantapplication.editor.CodeAssistantEditorInput;
import org.universaal.tools.codeassistantapplication.editor.model.Entity;
import org.universaal.tools.codeassistantapplication.editor.model.TreeNode;
import org.universaal.tools.codeassistantapplication.ontologyrepository.client.RepositoryClient;

public class CodeAssistantView extends ViewPart{
	public static final String ID = "org.universAAL.codeassistant.CodeAssistantView";
	//public static final String ID = "org.universaal.tools.codeassistantapplication.CodeAssistantView";

	private StructuredViewer viewer;
	private Composite parent; 
	private Vector selectedTypes=new Vector();
	private Shell shell;
	
	public void init(final Composite parent, IProgressMonitor monitor) {

/*
		// Authentication
		AuthenticationDialog dialog = new AuthenticationDialog(parent.getShell());
		int result = dialog.open();
		if (result==0)
			RepositoryClient.setAPIKey(dialog.getKey());
		else
			RepositoryClient.setAPIKey("");
*/					

		Startup s = new Startup();
		monitor.worked(30);
		boolean b = s.earlyStartup();
		monitor.worked(50);
/*		
		if (!b) {
			Button continueButton = new Button(parent.getShell(), SWT.PUSH);
			continueButton.setText("Continue");
			continueButton.pack();
			Button cancelButton = new Button(parent.getShell(), SWT.PUSH);
			cancelButton.setText("Cancel");
			cancelButton.pack();
			
			MessageDialog messageBox = new MessageDialog(parent.getShell(), "Code Assistant Info", null,
					"Code assistant tool cannot access ontology repository.", MessageDialog.WARNING, new String[]{"Continue", "Abort"}, 0);
			int rc = messageBox.open();
			switch (rc){
				case 0:
					//System.out.println("Continue");
					break;
				case 1:
					//System.out.println("Abort");
					System.exit(1);
					break;
			}
		}
*/
	}
	
	private void getOntologies(IProgressMonitor monitor){
		try {
			monitor.worked(20);
			//TimeUnit.SECONDS.sleep(2);
			init(parent, monitor);
			monitor.worked(70);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
    private void createView(IProgressMonitor monitor){
    	Display.getDefault().asyncExec(new Runnable() {
    		@Override
		    public void run() {
    			try{
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
    			initDragAndDrop(viewer);
    			//MessageDialog.openInformation(shell, "Coding Assistant ", "The view has finished.");
    			
    			}
				catch (Exception ex) {
					ex.printStackTrace();
				}    			
    		}
    	});
    }
	
	private void createCodeAssistanView(Composite p){
		Job job = new Job("Create Code Asistant View") {
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Building Code Assistant View ...", 100);
				setProperty(IProgressConstants.KEEP_PROPERTY,Boolean.FALSE);
				try{
					getOntologies(monitor);
					monitor.setTaskName("Begin createView now...");
					monitor.worked(80);
				    createView(monitor);
				    monitor.worked(100);
				    monitor.setTaskName("Done...");
			        monitor.done();
					return Status.OK_STATUS;
				} 
				catch (Exception ex) {
					ex.printStackTrace();
					return Status.CANCEL_STATUS;
				}
			}
		};
//?		job.schedule(1000);
//?		job.cancel(); //Did not work!
		job.setUser(false);
		job.schedule();
//?		job.schedule(3000); /It did not work
		job.setPriority(Job.DECORATE);
	}
	
	@Override
	public void createPartControl(Composite p) {
		String projectName = "CodeAssistant";
		parent = p; 
		viewer = new TreeViewer(parent);
		//shell = getSite().getShell();

		createCodeAssistanView(p);
	}
	
	protected void initDragAndDrop(final StructuredViewer viewer) {
		int operations =  DND.DROP_COPY | DND.DROP_MOVE;
		//Transfer[] transferTypes = new Transfer[]{EditorInputTransfer.getInstance()};
		Transfer[] transferTypes = new Transfer[]{TextTransfer.getInstance()};
		
		DragSourceListener listener = new DragSourceAdapter() {
			@Override
			public void dragStart(DragSourceEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				event.doit = (selection.size() > 0) ? true : false;
				super.dragStart(event);
			}
			@Override
			public void dragSetData(DragSourceEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				TreeNode node = (TreeNode) selection.getFirstElement();
				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
				      event.data = node.getEntity().getUri(); 
				}
/*				
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
*/				
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