package org.universaal.tools.subversiveplugin.wizards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.team.svn.core.connector.SVNConnectorException;
import org.eclipse.team.svn.core.connector.SVNRevision;
import org.eclipse.team.svn.core.operation.CompositeOperation;
import org.eclipse.team.svn.core.operation.remote.GetFileContentOperation;
import org.eclipse.team.svn.core.operation.remote.management.AddRepositoryLocationOperation;
import org.eclipse.team.svn.core.operation.remote.management.DiscardRepositoryLocationsOperation;
import org.eclipse.team.svn.core.operation.remote.management.SaveRepositoryLocationsOperation;
import org.eclipse.team.svn.core.resource.IRepositoryLocation;
import org.eclipse.team.svn.core.resource.IRepositoryResource;
import org.eclipse.team.svn.core.resource.IRepositoryResource.Information;
import org.eclipse.team.svn.core.svnstorage.SVNRemoteStorage;
import org.eclipse.team.svn.core.svnstorage.SVNRepositoryFile;
import org.eclipse.team.svn.core.svnstorage.SVNRepositoryFolder;
import org.eclipse.team.svn.core.utility.ProgressMonitorUtility;
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
	
	private Display display;

	
	public ImportExampleWizardPage(){
		super("wizardPage");
		setTitle("Import universAAL Example");
		setDescription("Please select an example to import.");
		display = Display.getDefault();
		
	}
	
	public void createControl(Composite parent) {
		loadPreferenceValues();
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		tableViewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		table = tableViewer.getTable();
		GridData gd_table = new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1);
		gd_table.heightHint = 89;
		table.setLayoutData(gd_table);
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
		tableViewer.setInput(container);
		tableViewer.addSelectionChangedListener(new ChoiceListener());
		
		lblDetails = new Label(container, SWT.NONE);
		lblDetails.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblDetails.setText("Details for the selected example:");
		
		detailsBox = new StyledText(container, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		GridData gd_detailsBox = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_detailsBox.heightHint = 140;
		detailsBox.setLayoutData(gd_detailsBox);
		
	
	}
	
	public void loadPreferenceValues(){
		IPreferenceStore pref = Activator.getDefault().getPreferenceStore();
		repositoryUrl = pref.getString(PreferenceConstants.P_URL);
		folderUrl = pref.getString(PreferenceConstants.P_FOLDER);
		if(repositoryUrl.charAt(repositoryUrl.length()-1) == '/'){
			repositoryUrl = repositoryUrl.substring(0, repositoryUrl.length()-2);
		}
		if(folderUrl.charAt(0) != '/'){
			folderUrl = "/"+folderUrl;
		}
	}

	class ViewContentProvider implements IStructuredContentProvider {
		
		private boolean createLocation = true;
		
		public ViewContentProvider(){
			locs = SVNRemoteStorage.instance().getRepositoryLocations();
			
			//Check to see if the Repositorylocation already exists.
			for(int i=0; i<locs.length;i++){
				if(locs[i].getUrl().equals(repositoryUrl)){
					createLocation = false;
				}
			}
			//If it does not exist, it is created here.
			if(createLocation){
				IRepositoryLocation loc = SVNRemoteStorage.instance().newRepositoryLocation();
				loc.setUrl(repositoryUrl+folderUrl);
				loc.reconfigure();
				AddRepositoryLocationOperation op = new AddRepositoryLocationOperation(loc);
				SaveRepositoryLocationsOperation savOp = new SaveRepositoryLocationsOperation();
				CompositeOperation comOp = new CompositeOperation(op.getId(), op.getMessagesClass());
				comOp.add(op);
				comOp.add(savOp);
				ProgressMonitorUtility.doTaskScheduledDefault(comOp);			
			}
			
			//Wait for Subversive to finish creating the location, and then
			//find the relevant folder.
			boolean finished = false;
			while(!finished){
				locs = SVNRemoteStorage.instance().getRepositoryLocations();
				
				for(int i=0;i<locs.length;i++){
					if(locs[i].getUrl().equals(repositoryUrl+folderUrl)){
						location = locs[i];
						System.out.println("Loop!");
						location.setUsername("anonymous");
						fold = new SVNRepositoryFolder(location, 
								repositoryUrl
								+folderUrl,
								SVNRevision.HEAD );
						finished = true;
						break;
					}
				}
			}
			
			//Get a list of the examples contained in the Tutorials-folder. If 
			//an exception is thrown, the user is alerted, and the repository is 
			//removed from the list of repositories.
			try {
				children = fold.getChildren();
			} catch (SVNConnectorException e) {
				MessageDialog.openError(getShell(), 
						"SVN Error", 
						"An error occured during communication with the SVN repository. \n" +
						"This may be caused by an invalid URL, or an aborted login. \n"+
						"If you did not abort the login-procedure, " +
						"please go to Window -> Preferences -> Import Example " +
						"Preferences, and check the URL.");
				IRepositoryLocation[] toBeDiscarded = new IRepositoryLocation[1];
				toBeDiscarded[0] = location;
				DiscardRepositoryLocationsOperation discard = 
					new DiscardRepositoryLocationsOperation(toBeDiscarded);
				discard.run(new NullProgressMonitor());
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
			int index = tableViewer.getTable().getSelectionIndex();
			IRepositoryResource res = (IRepositoryResource) tableViewer.getElementAt(index);
			((ImportExampleWizard)getWizard()).setResource(res);
			detailsBox.setText("Loading details...");
			new Thread(new ReadmeParser(index)).start();
		}
		
	}
	
	private String longToDate(long number){
		Date date = new Date(number);
		String str = date.toString();
		return str;
		
	}
	
	//Class that is used in its own thread to fetch the information from the 
	//readme-file. Uses an asyncExec() to update the UI.
	private class ReadmeParser implements Runnable{
		
		int index;
		
		public ReadmeParser(int i){
			this.index = i;
		}

		@Override
		public void run() {
			String details = parseFile(index);
			display.asyncExec(new UpdateDetails(details));
		}
	}
	
	//Used by Readmeparser. Launched in an asyncExec() to update the UI.
	private class UpdateDetails implements Runnable{
		
		private String details;
		
		public UpdateDetails(String text){
			this.details = text;
		}

		@Override
		public void run() {
			detailsBox.setText(details);
		}
		
	}
	
	//Finds the readme.txt in the selected project, and returns its contents in
	//a String.
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
