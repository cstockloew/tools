package org.universaal.tools.buildserviceapplication.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Text;
import org.apache.maven.DefaultMaven;
import org.apache.maven.Maven;
import org.apache.maven.cli.MavenCli;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulator;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingRequest;



/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	Text artifactFileNameText;
	Text pomFileNameText;
	private MavenExecutionRequestPopulator populator;
	private DefaultPlexusContainer container;
	private Maven maven;



	private SettingsBuilder settingsBuilder;

	/**
	 * The constructor.
	 */
	public SampleAction() {
	}



	/**
	 * Returns the root path of the selected project
	 * within eclipse workspace.
	 */
	private String getSelectedProjectPath(){
		try{
			String projectPath="";
			IWorkbench workbench= PlatformUI.getWorkbench();
			IWorkbenchWindow window= workbench.getActiveWorkbenchWindow();		
			TreeSelection selection=(TreeSelection)window.getActivePage().getSelection();
			if(selection.getPaths().length!=0){
				TreePath path=selection.getPaths()[0];		
				Object sel=path.getSegment(0);	
				if(sel instanceof org.eclipse.core.internal.resources.Project){
					org.eclipse.core.internal.resources.Project project =(org.eclipse.core.internal.resources.Project)sel;			
					projectPath=project.getLocation().toString();
				}		
				projectPath=projectPath.replace("file:/", "");
				//System.out.println("Path to project: "+projectPath);
				return projectPath;
			}
			else{
				return "";
			}
		}catch(Exception ex){
			return "";
		}
	}






	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		try{
			System.out.println(Platform.getLocation());
			String selectedProject=getSelectedProjectPath();
			if(!selectedProject.equals("")){
				File workingDir = new File(selectedProject);		   
				MavenCli cli = new MavenCli();
				cli.doMain(new String[] { "package" }, workingDir.getAbsolutePath(),
						System.out, System.err);		    
				DefaultMaven maven = new DefaultMaven();
				DefaultMavenExecutionRequest request = new
				DefaultMavenExecutionRequest();
				request.setBaseDirectory(workingDir);
				List<String> goals = new ArrayList<String>();
				goals.add("clean");
				goals.add("install");
				request.setGoals(goals);		
				final String userSettings = MavenCli.DEFAULT_USER_SETTINGS_FILE.getAbsolutePath().trim();
				System.out.println(userSettings);  
				request.setLocalRepositoryPath(new File(userSettings));	
				setUpMavenBuild();
				install(getSelectedProjectPath());
				MessageDialog.openInformation(
						window.getShell(),
						"BuildServiceApplication",
				"Service/Application successfully builded");
				//TODO implement creation of launch configuration file and execution of bundle
				CreateConfigurationFile f=new CreateConfigurationFile();

			}
			else{
				MessageDialog.openInformation(
						window.getShell(),
						"BuildServiceApplication",
				"Please select the project you want to build in the Project Explorer tab.");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			MessageDialog.openInformation(
					window.getShell(),
					"BuildServiceApplication",
			"Service/Application building failed");
		}
	}





	/**
	 * Sets up Maven embedder for execution.	
	 */
	protected void setUpMavenBuild(){
		try{
			container = new DefaultPlexusContainer();
			this.maven = container.lookup( Maven.class );
			this.populator = container.lookup( MavenExecutionRequestPopulator.class );
			this.settingsBuilder = container.lookup( SettingsBuilder.class );
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}



	/**
	 * Maven Excecution request.
	 */
	public MavenExecutionRequest createExecutionRequest() throws Exception{
		SettingsBuildingRequest settingsRequest = new DefaultSettingsBuildingRequest();
		settingsRequest.setUserSettingsFile( MavenCli.DEFAULT_USER_SETTINGS_FILE );
		settingsRequest.setGlobalSettingsFile( MavenCli.DEFAULT_GLOBAL_SETTINGS_FILE );
		MavenExecutionRequest request = new DefaultMavenExecutionRequest();
		request.setUserSettingsFile( settingsRequest.getUserSettingsFile() );
		request.setGlobalSettingsFile( settingsRequest.getGlobalSettingsFile() );
		request.setSystemProperties( System.getProperties() );
		populator.populateFromSettings( request,settingsBuilder.build( settingsRequest ).getEffectiveSettings() );
		return request;
	}

	/**
	 * Runs maven -install command which
	 * actually builds the project and 
	 * installs artifact to the local repository
	 */
	protected void install( String path ) throws Exception{
		File basedir = new File(getSelectedProjectPath());
		MavenExecutionRequest request = createExecutionRequest();
		request.setPom( new File( basedir, "pom.xml" ) );
		request.setGoals( Arrays.asList( "install" ) );
		populator.populateDefaults( request );
		MavenExecutionResult result = maven.execute( request );		
	}



	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}