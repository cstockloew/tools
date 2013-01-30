package org.universaal.tools.codeassistantapplication;

import java.net.URL;
import java.util.Vector;

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

import org.universaal.tools.codeassistantapplication.editor.CodeAssistantEditor;
import org.universaal.tools.codeassistantapplication.editor.CodeAssistantEditorInput;
import org.universaal.tools.codeassistantapplication.editor.model.Entity;
import org.universaal.tools.codeassistantapplication.editor.model.TreeNode;
import org.universaal.tools.codeassistantapplication.ontologyrepository.client.RepositoryClient;

public class CodeAssistantView extends ViewPart{
	public static final String ID = "org.universAAL.codeassistant.CodeAssistantView";
	//public static final String ID = "org.universaal.tools.codeassistantapplication.CodeAssistantView";
	private StructuredViewer viewer;
	private Vector selectedTypes=new Vector();

	public void init(final Composite parent, IProgressMonitor monitor) {
		//System.out.println("DOWNLOAD ONTOLOGIES");

		// Authentication
		AuthenticationDialog dialog = new AuthenticationDialog(parent.getShell());
		int result = dialog.open();
		if (result==0)
			RepositoryClient.setAPIKey(dialog.getKey());
		else
			RepositoryClient.setAPIKey("");
					
		monitor.worked(30);
		Startup s = new Startup();
		boolean b = s.earlyStartup();
		monitor.worked(50);
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
	}
	
	@Override
	public void createPartControl(Composite p) {
		final String projectName = "CodeAssistant";
		final Composite parent = p; 
		final StructuredViewer viewer = new TreeViewer(parent);
		this.viewer = viewer;
		Job job = new Job("Create Code Asistant View") {
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Building Code Assistant View ...", 100);
				setProperty(IProgressConstants.KEEP_PROPERTY,Boolean.FALSE);
				try {
					URL url = Platform.getBundle("org.universaal.tools.codeAssistant").getEntry("CodeAssistantFiles/icons/repo.gif");
					setProperty(IProgressConstants.ICON_PROPERTY,ImageDescriptor.createFromURL(url));
					Thread.sleep(1000);

					Display.getDefault().syncExec(new Runnable() {
					      @Override
					      public void run() {
					    	    monitor.worked(20);
					    	    init(parent, monitor);
								monitor.worked(60);
					    	  
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
							    monitor.worked(70);
							    viewer.setInput(TreeNode.getInstance());
							    monitor.worked(80);
							    initDragAndDrop(viewer);
					      }
					    });
					monitor.worked(100);
					return Status.OK_STATUS;
				} 
				catch (Exception ex) {
					return Status.CANCEL_STATUS;
				}
			}
		};
		job.setUser(true);
		job.schedule();
		job.setPriority(Job.DECORATE);
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