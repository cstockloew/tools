package org.universaal.tools.umltools.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;


public class DuplicateWithECore extends AbstractHandler {
	
	Shell shell;
	

	protected static ResourceSet createAndInitResourceSet() {
		// Create a resource set and register XMI resource factory
		// associated with ".simple" file extension
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ubicompdescriptor", new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("simplelanguage", new XMIResourceFactoryImpl());
		return resourceSet;
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		this.shell = window.getShell();
		MessageDialog.openInformation(
				shell,
				"UML tools",
				"UML model created!");

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
	
/*	
	public void addLibrary() {
		FileDialog fd = new FileDialog(shell); 

		
		fd.setFilterExtensions(new String[] {"*.notation"});
		fd.setFilterNames(new String[] {"Papyrus notation file"});
		
		String ret = fd.open();
		
		if(ret!=null){
			ResourceSet resourceSet = createAndInitResourceSet(); 

			Object notationRoot = loadRootObject(ret);
			
			List allRes = resourceSet.getResources();
			List newList = new ArrayList<EObject>();
			for (int i = 0; i < allRes.size(); i++) {
				newList.add(allRes.get(i)); // TODO: Add loading
			}
			EcoreUtil.copyAll(newList);
			List<EObject> copyList = EcoreUtil.copyAll(newList);
		}			
	}	

	public void saveUserService() {
		FileDialog fd = new FileDialog(shell,SWT.SAVE); 
		
		fd.setFilterExtensions(new String[] {"*.simplelanguage"});
		fd.setFilterNames(new String[] {"User service"});
		
		fd.setFileName("The user service name");
		String ret = fd.open();

		if (ret != null) {
			UserServiceUtils.saveUserService(ret, userService);
		}
	}
		
*/	
	
	/**
	 * Create a new user service, and return the initialized service
	 * @return
	 */
	public static Object newUserService() {
/*		// Create an initialized resource set 
		ResourceSet resourceSet = createAndInitResourceSet();

		// Create a new resource associated with a file
		URI fileURI = URI.createURI("MyTest.simplelanguage"); //).getAbsolutePath();
		// URI fileURI = URI.createFileURI("MyTest.simple");
		Resource resource = resourceSet.createResource(fileURI);

		// Create a user service as the root element, and set its name
		UserService userService = SimpleLanguageFactory.eINSTANCE.createUserService();
		userService.setName("My first task");
	
		// Create an instance of a task, set its name, and add to the
		// content of the resource at root
		Task myTask = SimpleLanguageFactory.eINSTANCE.createTask();
		myTask.setName("My first task");
		userService.getTasks().add(myTask);
				
		resource.getContents().add(userService);	
		return userService;*/
		return null;
	}

	/**
	 * Load the root object of the file.
	 * 
	 * @param fileURI
	 * @return The loaded root object, or null if no root object could not be found
	 */
	public static Object loadRootObject(String fileURI) {
		
		if(fileURI !=null){
			ResourceSet resourceSet = createAndInitResourceSet(); 
			
			//Set the file name from the dialog
			URI uri = URI.createFileURI(fileURI);
			Resource resource = resourceSet.getResource(uri, true);
			try {
				Object userService = resource.getContents().get(0);

				return userService;				
			}
			catch (Exception e){
				System.out.println("failed to load content of file : " + fileURI);
			}			
		}			
		return null;
	}

	
	
	
}
