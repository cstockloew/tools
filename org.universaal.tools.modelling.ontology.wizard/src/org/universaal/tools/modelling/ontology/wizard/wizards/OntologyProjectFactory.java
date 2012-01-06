package org.universaal.tools.modelling.ontology.wizard.wizards;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Model;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.project.ProjectImportConfiguration;		
import org.eclipse.m2e.core.ui.internal.actions.OpenMavenConsoleAction;
import org.eclipse.papyrus.resource.ModelSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.universaal.tools.modelling.ontology.wizard.Activator;

/**
 * This class is a factory that crates an ontology project, including
 * its Eclipse, Maven and UML model artefacts
 *  
 * @author erlend
 *
 */
public class OntologyProjectFactory {
	/**
	 * Creates the ontology project based on the provided model
	 * 
	 * @param model
	 */
	public static void create(OntologyProjectModel model) {
		createEclipseProject(model);
		createMavenArtefacts(model);
		createUMLArtefacts(model);
	}
	
	protected static void createEclipseProject(OntologyProjectModel model) {
		
	}

	static final String[] folders = new String[] {
		"src/main/java", "src/test/java", "src/main/resources", "src/test/resources" };

	
	protected static boolean createMavenArtefacts(OntologyProjectModel ontologyModel) {
		return false;
	
	}
	
	public static final String MODEL_DIR = "models";
	public static final String DI_FILE = "model.di";
	
	protected static void createUMLArtefacts(OntologyProjectModel model) {
		URI fromUri = URI.createPlatformPluginURI("/" + Activator.PLUGIN_ID + "/" + MODEL_DIR + "/" + DI_FILE, true);
		//fromUri.appendSegments(new String[] {MODEL_DIR, DI_FILE});
	
		
		URI toUri = URI.createPlatformResourceURI("/" + model.getMavenModel().getArtifactId() + "/" + model.getOntologyName() + ".di", true);		
		//fromUri.appendSegment(model.getOntologyName() + ".di");			
	
		clonePapyrusModel(fromUri.toString(), toUri.toString());	
	}
	
	/** 
	 * Create a new resource set and register XMI, UML and eCore resource factories 
	 * with it. The ModelSet subclass of resource set is used because it may give 
	 * better support for Papyrus models
	 * 
	 * @return A new resource set with registered resource factories
	 */
	protected static ModelSet createAndInitResourceSet() {
		ModelSet resourceSet = new ModelSet();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
        (Resource.Factory.Registry.DEFAULT_EXTENSION, 
   	         new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				"ecore", new EcoreResourceFactoryImpl());		
		
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				".notation", new GMFResourceFactory());
		
		return resourceSet;
	}	
	
	public static ResourceSet clonePapyrusModel(String inModelURI, String cloneModelURI) {
		System.out.println("InModel: " + inModelURI);
		System.out.println("OutModel: " + cloneModelURI);
		
		//org.eclipse.papyrus.resource.notation.NotationModel.NOTATION_FILE_EXTENSION;
		//org.eclipse.papyrus.resource.sasheditor.SashModel.MODEL_FILE_EXTENSION
		//org.eclipse.papyrus.resource.uml.UmlModel.UML_FILE_EXTENSION

		return cloneModel(inModelURI, cloneModelURI, new String[] {"di", "notation", "uml"});
	}		
	
	
	public static ResourceSet cloneModel(String inModelURI, String cloneModelURI, String[] fileExtensions) {
		ResourceSet inSet = createAndInitResourceSet();
		
		int inExtIndex = inModelURI.lastIndexOf(".") + 1;
		String inBaseURI = inModelURI.substring(0, inExtIndex);
		
		List<EObject> listToClone = new ArrayList<EObject>();
		Map<String,String> classNameToExtensionMap = new HashMap<String, String>();
		
		
		// Load the models and collect the root objects to be cloned
		for (int i = 0; i < fileExtensions.length; i++) {
			EObject obj = loadRootObject(inSet, inBaseURI + fileExtensions[i]);
			listToClone.add(obj);
			classNameToExtensionMap.put(obj.eClass().getName(), fileExtensions[i]);
		}
		
		// Start the cloning
		ResourceSet cloneSet = createAndInitResourceSet();
		Collection<EObject> clonedList = EcoreUtil.copyAll(listToClone);
		
		int cloneExtIndex = cloneModelURI.lastIndexOf(".") + 1;
		String cloneBaseURI = cloneModelURI.substring(0, cloneExtIndex);
		
		// Add the clones to the clone resource set and set the right file names
		for (Iterator<EObject> iter = clonedList.iterator(); iter.hasNext(); ) {
			EObject cr = iter.next();

			String cloneFileName = cloneBaseURI + classNameToExtensionMap.get(cr.eClass().getName());
			URI uri = URI.createURI(cloneFileName); //createFileURI
			Resource resource = cloneSet.createResource(uri);
			resource.getContents().add(cr);			
		}

		// Save the resources
		EList<Resource> resources = cloneSet.getResources();
		for (Iterator<Resource> resIter = resources.iterator(); resIter.hasNext(); ) {					
			try {
				resIter.next().save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return cloneSet;		
	}
	
	
	
	
	
	/**
	 * Load the root object of the file by getting it through the 
	 * provided resource set.
	 * 
	 * @param resourceSet The resource set used to load the resource
	 * @param fileURI
	 * @return The loaded root object, or null if no root object could not be found
	 */
	public static EObject loadRootObject(ResourceSet resourceSet, String fileURI) {
		
		if(fileURI !=null){
			//Set the file name from the dialog
			URI uri = URI.createURI(fileURI); // createFileURI
			Resource resource = resourceSet.getResource(uri, true);
			try {
				resource.load(null);
				EObject object = resource.getContents().get(0);

				return object;				
			}
			catch (Exception e){
				System.out.println("failed to load content of file : " + fileURI);
			}			
		}			
		return null;
	}
	
}
