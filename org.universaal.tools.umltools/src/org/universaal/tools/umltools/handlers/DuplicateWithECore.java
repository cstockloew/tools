package org.universaal.tools.umltools.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
	

	protected static ModelSet createAndInitResourceSet() {
		// Create a resource set and register XMI resource factory
		// associated with ".simple" file extension
		ModelSet resourceSet = new ModelSet();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
        (Resource.Factory.Registry.DEFAULT_EXTENSION, 
   	         new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				"ecore", new EcoreResourceFactoryImpl());
		
		//resourceSet.
		/*		ResourceSet resourceSet = new ResourceSetImpl();
		
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("simplelanguage", new XMIResourceFactoryImpl());

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
	        (Resource.Factory.Registry.DEFAULT_EXTENSION, 
	         new XMIResourceFactoryImpl());		
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);


		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				//GMFReource.
				UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);

*/	
		
		return resourceSet;
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		
/*		org.eclipse.papyrus.sashwindows.di.DiPackage.eINSTANCE;
		impl.DiPackageImpl.init();
		org.eclipse.gmf.runtime.notation.
*/		
		

		
		//OnDemandLoadingModelSet theSet = new OnDemandLoadingModelSet();
		
		
		//org.eclipse.papyrus.core.utils.PapyrusEcoreUtils utils;
		
		//org.eclipse.papyrus.core.resourceloading.
		

		org.eclipse.papyrus.resource.ModelsReader reader;
		org.eclipse.papyrus.sashwindows.di.impl.DiPackageImpl pack;
		
		
		FileDialog fd = new FileDialog(shell); 
		

		fd.setFilterExtensions(new String[] {"*.di"});
		fd.setFilterNames(new String[] {"Papyrus model"});
		
//		fd.setFilterExtensions(new String[] {"*.notation"});
//		fd.setFilterNames(new String[] {"Papyrus notation file"});
		
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
				ResourceSet newResourceSet = createAndInitResourceSet();
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
		
		
/*		Model myModel = createModel("My model");				
		importPrimitiveTypes(myModel);
		
		Package pkg = myModel.createNestedPackage("My package");
		pkg.createOwnedClass("First class", false);
		org.eclipse.uml2.uml.Class secClass = pkg.createOwnedClass("SecondClass", true);
		org.eclipse.uml2.uml.Class thirdClass = pkg.createOwnedClass("ThirdClass", false);
		thirdClass.createGeneralization(secClass);
		thirdClass.createOwnedAttribute("theProperty", umlString);
//		thirdClass.
		
		save(myModel, URI.createURI("platform:/resource/MyTest/mynewmodel.uml"));*/
		return null;
	}
	

	/**
	 * Load the root object of the file.
	 * 
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
				EObject userService = resource.getContents().get(0);

				return userService;				
			}
			catch (Exception e){
				System.out.println("failed to load content of file : " + fileURI);
			}			
		}			
		return null;
	}
	
	
	
}
