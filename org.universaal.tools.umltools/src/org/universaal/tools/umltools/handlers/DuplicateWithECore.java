package org.universaal.tools.umltools.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.resource.UMLResource;

import org.eclipse.papyrus.resource.ModelException;
import org.eclipse.papyrus.resource.ModelSet;
import org.eclipse.papyrus.resource.ModelsReader;


import org.eclipse.papyrus.core.resourceloading.OnDemandLoadingModelSet;





public class DuplicateWithECore extends AbstractHandler {
	
	Shell shell;
	
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

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		shell = window.getShell();

		FileDialog fd = new FileDialog(shell); 

		fd.setFilterExtensions(new String[] {"*.di"});
		fd.setFilterNames(new String[] {"Papyrus model"});
				
		String inFileName = fd.open();
		
		if(inFileName!=null){

			// Ask for name of new model
			FileDialog fdSave = new FileDialog(shell,SWT.SAVE); 

			fdSave.setFilterExtensions(new String[] {"*.di"});
			fdSave.setFilterNames(new String[] {"Papyrus model"});

			String cloneFileName = fdSave.open();

			if(cloneFileName!=null) {
				clonePapyrusModel(inFileName, cloneFileName);
					
				MessageDialog.openInformation(
						shell,
						"Created model from template files",
						"UML model created!");				
			}
		}
		return null;
		
/*		
		FileDialog fd = new FileDialog(shell); 

		fd.setFilterExtensions(new String[] {"*.di"});
		fd.setFilterNames(new String[] {"Papyrus model"});
				
		String ret = fd.open();
		
		if(ret!=null){
			ModelSet resourceSet = createAndInitResourceSet(); 
			
		
			
			EObject diRoot = loadRootObject(resourceSet, ret);
			EObject notationRoot = loadRootObject(resourceSet, ret.replace(".di", ".notation"));
			EObject umlRoot = loadRootObject(resourceSet, ret.replace(".di", ".uml"));

			List<Resource> allRes = resourceSet.getResources();
			List<EObject> newList = new ArrayList<EObject>();
			for (int i = 0; i < allRes.size(); i++) {
				newList.add(allRes.get(i).getContents().get(0)); // Is any more explicit loading needed?
			}
			Collection<EObject> copyList = EcoreUtil.copyAll(newList);


			// Ask for name of new model
			FileDialog fdSave = new FileDialog(shell,SWT.SAVE); 

			fdSave.setFilterExtensions(new String[] {"*.di"});
			fdSave.setFilterNames(new String[] {"Papyrus model"});

			String notationFileName = fdSave.open();

			if(notationFileName!=null){


				// Create a new resource set for the copies, and add the each root object to a new resource in this set 
				ModelSet newResourceSet = createAndInitResourceSet();
				for (Iterator<EObject> iter = copyList.iterator(); iter.hasNext(); ) {
					EObject cr = iter.next();
					String saveFileName = notationFileName;
					if (cr instanceof Model) {
						saveFileName = saveFileName.replace(".di", ".uml");
					} else if (cr.getClass().getName().contains("Diagram")) {
						saveFileName = saveFileName.replace(".di", ".notation");						
					}
					URI uri = URI.createFileURI(saveFileName);
					Resource resource = newResourceSet.createResource(uri);
					resource.getContents().add(cr);
				}

				// Save the resources
				EList<Resource> resources = newResourceSet.getResources();
				for (Iterator<Resource> resIter = resources.iterator(); resIter.hasNext(); ) {					
					try {
						resIter.next().save(null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
				this.shell = window.getShell();
				MessageDialog.openInformation(
						shell,
						"Creating from template files",
						"UML model created!");
	
			}	
		}
		return null;
*/
	}
	
	
/*	
	public void loadPapyrusModel(String diFileName) {
		ModelSet resourceSet = createAndInitResourceSet(); 
					
		EObject diRoot = loadRootObject(resourceSet, diFileName);
		EObject notationRoot = loadRootObject(resourceSet, diFileName.replace(".di", ".notation"));
		EObject umlRoot = loadRootObject(resourceSet, diFileName.replace(".di", ".uml"));
				

	}
	
	
	public static ModelSet cloneResources() {
		ModelSet modelSet = new ModelSet();
		return modelSet;
	}
	

	public static ResourceSet cloneSingleFileModel(ResourceSet resourceSet, String modelURI) {
		int extIndex = modelURI.lastIndexOf(".") + 1;
		String ext = modelURI.substring(extIndex);
		
		return cloneModel(resourceSet, modelURI, new String[] {ext});		
	}
		
	public static ResourceSet clonePapyrusModel(ResourceSet resourceSet, String modelURI) {
		//org.eclipse.papyrus.resource.notation.NotationModel.NOTATION_FILE_EXTENSION;
		//org.eclipse.papyrus.resource.sasheditor.SashModel.MODEL_FILE_EXTENSION
		//org.eclipse.papyrus.resource.uml.UmlModel.UML_FILE_EXTENSION

		return cloneModel(resourceSet, modelURI, new String[] {"di", "notation", "uml"});
	}		
	
	public static ResourceSet cloneModel(ResourceSet resourceSet, String modelURI, String[] fileExtensions) {
		ResourceSet cloneSet = createAndInitResourceSet();
		// Clone the extension factory map
		Map<String, Object> toMap = cloneSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
		Map<String, Object> fromMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
		for (Iterator<String> it = fromMap.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			toMap.put(key, fromMap.get(key));			
		}

		int extIndex = modelURI.lastIndexOf(".") + 1;
		String baseURI = modelURI.substring(0, extIndex);
		
		List<EObject> listToClone = new ArrayList<EObject>();
		
		for (int i = 0; i < fileExtensions.length; i++) {
			EObject obj = loadRootObject(resourceSet, baseURI + fileExtensions[i]);
			listToClone.add(obj);
		}
		
		
		return cloneSet;
		
	}
*/

	public static ResourceSet clonePapyrusModel(String inModelURI, String cloneModelURI) {
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
			URI uri = URI.createFileURI(cloneFileName);
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
			URI uri = URI.createFileURI(fileURI);
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
	
	
	public static void papyrusTest() {

		//org.eclipse.papyrus.wizards.CreateModelWizard
	}
	
	/*	Code snippet to create a UML model, add some UML classes and add save to file	
	
	Model myModel = createModel("My model");				
	importPrimitiveTypes(myModel);
	
	Package pkg = myModel.createNestedPackage("My package");
	pkg.createOwnedClass("First class", false);
	org.eclipse.uml2.uml.Class secClass = pkg.createOwnedClass("SecondClass", true);
	org.eclipse.uml2.uml.Class thirdClass = pkg.createOwnedClass("ThirdClass", false);
	thirdClass.createGeneralization(secClass);
	thirdClass.createOwnedAttribute("theProperty", umlString);
	save(myModel, URI.createURI("platform:/resource/MyTest/mynewmodel.uml"));*/
	
	
	
	// Papyrus classes that may be of interrest
	
	//OnDemandLoadingModelSet theSet = new OnDemandLoadingModelSet();
	//org.eclipse.papyrus.core.utils.PapyrusEcoreUtils utils;		
	//org.eclipse.papyrus.resource.ModelsReader reader;
	//org.eclipse.papyrus.sashwindows.di.impl.DiPackageImpl pack;
	
	
}
