package org.universaal.tools.subversiveplugin.wizards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.team.svn.core.connector.SVNConnectorException;
import org.eclipse.team.svn.core.connector.SVNRevision;
import org.eclipse.team.svn.core.operation.CompositeOperation;
import org.eclipse.team.svn.core.operation.remote.GetFileContentOperation;
import org.eclipse.team.svn.core.operation.remote.management.AddRepositoryLocationOperation;
import org.eclipse.team.svn.core.operation.remote.management.SaveRepositoryLocationsOperation;
import org.eclipse.team.svn.core.resource.IRepositoryLocation;
import org.eclipse.team.svn.core.resource.IRepositoryResource;
import org.eclipse.team.svn.core.resource.IRepositoryResource.Information;
import org.eclipse.team.svn.core.svnstorage.SVNRemoteStorage;
import org.eclipse.team.svn.core.svnstorage.SVNRepositoryFile;
import org.eclipse.team.svn.core.svnstorage.SVNRepositoryFolder;
import org.eclipse.team.svn.core.utility.ProgressMonitorUtility;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.universaal.tools.subversiveplugin.Activator;
import org.universaal.tools.subversiveplugin.preferences.PreferenceConstants;

public class ImportExampleWizardPage extends WizardPage {
	
	private Table table;
	private TableViewer tableViewer;
	
	private IRepositoryLocation[] locs;
	private IRepositoryLocation location;
	private SVNRepositoryFolder fold;
	private IRepositoryResource[] children;
	private Information childInfo;
	
	private Label lblDetails;
	private StyledText detailsBox;
	
	private String repositoryUrl;
	private String folderUrl;
	
	private TableColumn nameClm, dateClm, authorClm;

	
	public ImportExampleWizardPage(){
		super("wizardPage");
		setTitle("Import UNIVERSAAL Example");
		setDescription("Please select an example to import.");
		
	}
	
	public void createControl(Composite parent) {
		loadPreferenceValues();
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		tableViewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		nameClm = new TableColumn(table, SWT.LEAD);
		nameClm.setText("Name");
		nameClm.setWidth(150);
		
		dateClm = new TableColumn(table, SWT.LEAD);
		dateClm.setText("Last commit");
		dateClm.setWidth(200);
		
		authorClm = new TableColumn(table, SWT.LEAD);
		authorClm.setText("Last committer");
		authorClm.setWidth(200);
		
		tableViewer.setContentProvider(new ViewContentProvider());
		tableViewer.setLabelProvider(new ViewLabelProvider());
		tableViewer.setInput(this);
		tableViewer.addSelectionChangedListener(new ChoiceListener());
		
		lblDetails = new Label(container, SWT.NONE);
		lblDetails.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblDetails.setText("Details for the selected example:");
		
		detailsBox = new StyledText(container, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		detailsBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	
	}
	
	public void loadPreferenceValues(){
		IPreferenceStore pref = Activator.getDefault().getPreferenceStore();
		repositoryUrl = pref.getString(PreferenceConstants.P_URL);
		folderUrl = pref.getString(PreferenceConstants.P_FOLDER);	
	}

	class ViewContentProvider implements IStructuredContentProvider {
		
		private boolean createLocation = true;
		
		public ViewContentProvider(){
			locs = SVNRemoteStorage.instance().getRepositoryLocations();
			
			for(int i=0; i<locs.length;i++){
				if(locs[i].getUrl().equals(repositoryUrl)){
					createLocation = false;
				}
			}
			if(createLocation){
				IRepositoryLocation loc = SVNRemoteStorage.instance().newRepositoryLocation();
				loc.setUrl(repositoryUrl);
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
			
			for(int i=0;i<locs.length;i++){
				if(locs[i].getUrl().equals(repositoryUrl)){
					location = locs[i];
					fold = new SVNRepositoryFolder(location, 
							repositoryUrl
							+folderUrl,
							SVNRevision.HEAD );
					break;
				}
			}
			
			try {
				children = fold.getChildren();
			} catch (SVNConnectorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {

			
			return children;
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		public String getColumnText(Object obj, int index) {
			childInfo = ((IRepositoryResource)obj).getInfo();
			switch(index){
			case 0: return ((IRepositoryResource)obj).getName();
			case 1: return longToDate(childInfo.lastChangedDate);
			case 2: return childInfo.lastAuthor;
			default: return "";
			}
		}
		public Image getColumnImage(Object obj, int index) {
			if(index == 0){
				return getImage(obj);
			}
			return null;
		}
		public Image getImage(Object obj) {
			return ResourceManager.getPluginImage("org.universaal.tools.subversivePlugin", "icons/compile.png");
//			return PlatformUI.getWorkbench().
//					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	
	private class ChoiceListener implements ISelectionChangedListener{

		@Override
		public void selectionChanged(SelectionChangedEvent arg0) {
			IRepositoryResource res = (IRepositoryResource) tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex());
			((ImportExampleWizard)getWizard()).setResource(res);
			detailsBox.setText(parseFile(tableViewer.getTable().getSelectionIndex()));
		}
		
	}
	
	private String longToDate(long number){
		Date date = new Date(number);
		String str = date.toString();
		return str;
		
	}
	
	private String parseFile(int i){
		SVNRepositoryFile file = 
			new SVNRepositoryFile(location,
				children[i].getUrl()+"/readme.txt", 
				SVNRevision.HEAD);
		
		GetFileContentOperation op = new GetFileContentOperation(file);
		op.run(new NullProgressMonitor());
		try {
			InputStream str = op.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(str));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null){
				sb.append(line+"\n");
			}
			str.close();
			if(sb.toString().equals("")){
				return "**Could not read readme.txt**";
			}
			return sb.toString();
		} catch (IOException e){
			return "Could not open readme.txt";
		}
	}

}
