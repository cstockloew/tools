package org.universaal.tools.conformanceTools.run;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.Reporting;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.universaal.tools.conformanceTools.checks.api.uaalDirectives;
import org.universaal.tools.conformanceTools.utils.POMeditor;

public class AALDirectivesMavenPlugin {

	private IProject p;
	private File pom;
	private POMeditor pomEditor;

	private final String UAAL_GID = "org.universAAL";
	private final String UAAL_AID = "uAAL.pom";

	private final String UAAL_PLUGIN_GROUP_ID = "org.universAAL.support";
	private final String UAAL_PLUGIN_ARTIFACT_ID = "uaalDirectives-maven-plugin";
	private final String UAAL_PLUGIN_VERSION = "1.3.1-SNAPSHOT";

	// it seems to be not working without adding this dependency
	private final String DOXIA_PLUGIN_GROUP_ID = "org.apache.maven.doxia";
	private final String DOXIA_PLUGIN_ARTIFACT_ID = "doxia-sink-api";
	private final String DOXIA_PLUGIN_VERSION = "1.3";

	private List<String> goals;

	public AALDirectivesMavenPlugin(IProject project){
		this.p = project;
		this.pom = new File(project.getFile("pom.xml").getLocation()+"");

		pomEditor = new POMeditor(this.pom);

		goals = new ArrayList<String>();
		for(int i = 0; i < uaalDirectives.values().length; i++)
			goals.add(uaalDirectives.values()[i]+"-check");
	}

	public List<Throwable> executeTests(){

		List<Throwable> output = null; 

		try{
			if(verifyAALDMPinPOMfile()){
				output = runTest();
				if(output != null)
					for(int i = 0; i < output.size(); i++){
						try{
							System.out.println("**\ngetLocalizedMessage: "+output.get(i).getLocalizedMessage()+
									" \ngetMessage: "+output.get(i).getMessage()+
									" \ngetCause: "+output.get(i).getCause());
						}
						catch(Exception ex){
							ex.printStackTrace();
						}
					}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return output;
	}

	/*
	 * <parent>
		<groupId>org.universAAL.support</groupId>
		<artifactId>support.pom</artifactId>
		<version>1.3.1-SNAPSHOT</version>
		<relativePath>../pom/</relativePath>
	</parent>
	 */

	private final String PARENT_POM_GID = "org.universAAL.support";
	private final String PARENT_POM_AID = "support.pom";
	private final String PARENT_POM_VERSION = "1.3.1-SNAPSHOT";

	private boolean verifyAALDMPinPOMfile(){

		try{
			boolean plugin_bool = false, dependency_bool = false, reporting = false;

			Model pomC = pomEditor.getPomContent();
			if(pomC != null){
				if(pomC.getParent() != null){ 
					if(!pomC.getParent().getGroupId().equalsIgnoreCase(PARENT_POM_GID) ||
							!pomC.getParent().getArtifactId().equalsIgnoreCase(PARENT_POM_AID))
						System.out.println("[ERROR] Selected project has a different PARENT declaration in POM file than "+PARENT_POM_GID+"/"+PARENT_POM_AID);
				}
				else
					System.out.println("[ERROR] Selected project has not PARENT declaration in POM file equal to "+PARENT_POM_GID+"/"+PARENT_POM_AID);

				if(pomC.getBuild() != null){
					if(pomC.getBuild().getPlugins() != null){
						for(int i = 0; i < pomC.getBuild().getPlugins().size(); i++){
							if(pomC.getBuild().getPlugins().get(i).getArtifactId().equalsIgnoreCase(UAAL_PLUGIN_ARTIFACT_ID)){

								plugin_bool = true;

								if(pomC.getBuild().getPlugins().get(i).getDependencies() != null){
									for(int j = 0; j < pomC.getBuild().getPlugins().get(i).getDependencies().size(); j++){
										if(pomC.getBuild().getPlugins().get(i).getDependencies().get(j).getArtifactId().equalsIgnoreCase(DOXIA_PLUGIN_ARTIFACT_ID)){

											dependency_bool = true;
										}
									}		
								}
							}
						}
					}
					if(pomC.getReporting() != null){
						for(int i = 0; i < pomC.getReporting().getPlugins().size(); i++){
							if(pomC.getReporting().getPlugins().get(i).getArtifactId().equalsIgnoreCase(UAAL_PLUGIN_ARTIFACT_ID))
								reporting = true;
						}
					}
				}

				if(!plugin_bool){

					Plugin pl = new Plugin();
					pl.setArtifactId(UAAL_PLUGIN_ARTIFACT_ID);
					pl.setGroupId(UAAL_PLUGIN_GROUP_ID);
					pl.setVersion(UAAL_PLUGIN_VERSION);
					pomC.getBuild().getPlugins().add(pl);

				}
				if(!dependency_bool){
					// doxia-sink-api dependency not found
					Dependency d = new Dependency();
					d.setArtifactId(DOXIA_PLUGIN_ARTIFACT_ID);
					d.setGroupId(DOXIA_PLUGIN_GROUP_ID);
					d.setVersion(DOXIA_PLUGIN_VERSION);
					for(int i = 0; i < pomC.getBuild().getPlugins().size(); i++){
						if(pomC.getBuild().getPlugins().get(i).getArtifactId().equalsIgnoreCase(UAAL_PLUGIN_ARTIFACT_ID)){
							pomC.getBuild().getPlugins().get(i).addDependency(d);
							System.out.println(DOXIA_PLUGIN_ARTIFACT_ID+" dependency added!");
						}
					}				
				}
				if(!reporting){
					ReportPlugin rp = new ReportPlugin();
					rp.setArtifactId(UAAL_PLUGIN_ARTIFACT_ID);
					rp.setGroupId(UAAL_PLUGIN_GROUP_ID);
					rp.setVersion(UAAL_PLUGIN_VERSION);
					pomC.setReporting(new Reporting());
					pomC.getReporting().addPlugin(rp);
				}

				for(int i = 0; i < pomC.getBuild().getPlugins().size(); i++)
					if(pomC.getBuild().getPlugins().get(i).getArtifactId().equalsIgnoreCase(UAAL_PLUGIN_ARTIFACT_ID)){

						PluginExecution pex;
						pex = new PluginExecution();
						for(int k = 0; k < goals.size(); k++){
							pex.addGoal(goals.get(k));
						}

						Xpp3Dom configuration = new Xpp3Dom("configuration");
						Xpp3Dom failOnMissMatch = new Xpp3Dom("failOnMissMatch");
						failOnMissMatch.setValue("false");
						configuration.addChild(failOnMissMatch);
						pex.setConfiguration(configuration);

						pex.setId("CT - uAAL checks");
						pex.setPhase("verify");
						pex.setInherited(false);

						if(pomC.getBuild().getPlugins().get(i).getExecutions() != null){
							for(int j = 0; j < pomC.getBuild().getPlugins().get(i).getExecutions().size(); j++){
								if(pomC.getBuild().getPlugins().get(i).getExecutions().get(j).getId().equalsIgnoreCase(pex.getId())){
									pomC.getBuild().getPlugins().get(i).getExecutions().remove(j);
									break;
								}
							}
						}
						pomC.getBuild().getPlugins().get(i).addExecution(pex);

						pex = new PluginExecution();
						pex.addGoal("site");
						pex.setId("CT - uAAL checks - site");
						pex.setPhase("site");
						pex.setInherited(false);

						if(pomC.getBuild().getPlugins().get(i).getExecutions() != null){
							for(int j = 0; j < pomC.getBuild().getPlugins().get(i).getExecutions().size(); j++){
								if(pomC.getBuild().getPlugins().get(i).getExecutions().get(j).getId().equalsIgnoreCase(pex.getId())){
									pomC.getBuild().getPlugins().get(i).getExecutions().remove(j);
									break;
								}
							}
						}
						pomC.getBuild().getPlugins().get(i).addExecution(pex);

						pomEditor.writePom(pomC);
						return true;
					}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return false;
	}

	private List<Throwable> runTest(){

		try{
			IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();
			IFile pomResource = p.getFile(IMavenConstants.POM_FILE_NAME);
			IMavenProjectFacade projectFacade = projectManager.create(pomResource, true, null);

			IMaven maven = MavenPlugin.getMaven();
			if(pomResource != null && projectFacade != null){
				MavenExecutionRequest request = projectManager.createExecutionRequest(pomResource, projectFacade.getResolverConfiguration(), null);

				List<String> goals = new ArrayList<String>();
				Properties props = new Properties();

				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceDescription description = workspace.getDescription();
				if (!description.isAutoBuilding())
					goals.add("compiler:compile"); // compile it if autobuilding is off
				goals.add("verify");

				request.setGoals(goals);
				request.setUserProperties(props);
				MavenExecutionResult execution_result = maven.execute(request, null);
				if(execution_result.getExceptions() == null || execution_result.getExceptions().isEmpty()){
					return Arrays.asList(new Throwable("No errors have been found!"));
				}
				else{
					return execution_result.getExceptions();
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return Arrays.asList(new Throwable("Something went wrong!"));
	}
}