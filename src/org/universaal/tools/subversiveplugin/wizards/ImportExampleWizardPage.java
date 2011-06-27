package org.universaal.tools.subversiveplugin.wizards;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.team.svn.core.connector.SVNConnectorException;
import org.eclipse.team.svn.core.connector.SVNRevision;
import org.eclipse.team.svn.core.operation.CompositeOperation;
import org.eclipse.team.svn.core.operation.remote.management.AddRepositoryLocationOperation;
import org.eclipse.team.svn.core.operation.remote.management.SaveRepositoryLocationsOperation;
import org.eclipse.team.svn.core.resource.IRepositoryLocation;
import org.eclipse.team.svn.core.resource.IRepositoryResource;
import org.eclipse.team.svn.core.svnstorage.SVNRemoteStorage;
import org.eclipse.team.svn.core.svnstorage.SVNRepositoryFolder;
import org.eclipse.team.svn.core.utility.ProgressMonitorUtility;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ImportExampleWizardPage extends WizardPage {
	
	private Table table;
	private TableViewer tableViewer;
	
	private IRepositoryLocation[] locs;
	private SVNRepositoryFolder fold;
	private IRepositoryResource[] children;

	
	public ImportExampleWizardPage(){
		super("wizardPage");
		setTitle("Import UNIVERSAAL Example");
		setDescription("Please select an example to import.");
		
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		tableViewer = new TableViewer(container, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableViewer.setContentProvider(new ViewContentProvider());
		tableViewer.setLabelProvider(new ViewLabelProvider());
		tableViewer.setSorter(new NameSorter());
		tableViewer.setInput(this);
		tableViewer.addSelectionChangedListener(new ChoiceListener());
		
	}

	class ViewContentProvider implements IStructuredContentProvider {
		
		private boolean createLocation = true;
		
		public ViewContentProvider(){
			locs = SVNRemoteStorage.instance().getRepositoryLocations();
			
			for(int i=0; i<locs.length;i++){
				if(locs[i].getUrl().equals("http://forge.universaal.org/svn/uaaltools")){
					createLocation = false;
				}
			}
			if(createLocation){
				IRepositoryLocation loc = SVNRemoteStorage.instance().newRepositoryLocation();
				loc.setUrl("http://forge.universaal.org/svn/uaaltools");
				loc.reconfigure();
				AddRepositoryLocationOperation op = new AddRepositoryLocationOperation(loc);
				SaveRepositoryLocationsOperation savOp = new SaveRepositoryLocationsOperation();
				CompositeOperation comOp = new CompositeOperation(op.getId(), op.getMessagesClass());
				comOp.add(op);
				comOp.add(savOp);
				ProgressMonitorUtility.doTaskScheduledDefault(comOp);			
			}
			
			while(locs.length==0){
				locs = SVNRemoteStorage.instance().getRepositoryLocations();
			}
			fold = new SVNRepositoryFolder(locs[0], "http://forge.universaal.org/svn/uaaltools/trunk/tutorials",SVNRevision.HEAD );
			
			
		}
		
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {

			try {
				children = fold.getChildren();
			} catch (SVNConnectorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return children;
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return ((IRepositoryResource)obj).getName();
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	class NameSorter extends ViewerSorter {
	}
	
	private class ChoiceListener implements ISelectionChangedListener{

		@Override
		public void selectionChanged(SelectionChangedEvent arg0) {
			IRepositoryResource res = (IRepositoryResource) tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex());
			((ImportExampleWizard)getWizard()).setResource(res);
		}
		
	}

}
