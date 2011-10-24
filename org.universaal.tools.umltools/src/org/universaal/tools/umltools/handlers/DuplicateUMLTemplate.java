package org.universaal.tools.umltools.handlers;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class DuplicateUMLTemplate extends AbstractHandler {
	
	
    ResourceSet resourceSet;
    PrimitiveType umlString;
    PrimitiveType umlBoolean;
    
    
	/**
	 * The constructor.
	 */
	public DuplicateUMLTemplate() {
		resourceSet = new ResourceSetImpl();
		registerResourceFactories();
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"UML tools",
				"UML model created!");

		Model myModel = createModel("My model");				
		importPrimitiveTypes(myModel);
		
		Package pkg = myModel.createNestedPackage("My package");
		pkg.createOwnedClass("First class", false);
		org.eclipse.uml2.uml.Class secClass = pkg.createOwnedClass("SecondClass", true);
		org.eclipse.uml2.uml.Class thirdClass = pkg.createOwnedClass("ThirdClass", false);
		thirdClass.createGeneralization(secClass);
		thirdClass.createOwnedAttribute("theProperty", umlString);
//		thirdClass.
		
		save(myModel, URI.createURI("platform:/resource/MyTest/mynewmodel.uml"));
		return null;
	}
	
	public static boolean DEBUG = true;

	protected static void out(String output) {
		if (DEBUG) {
			System.out.println(output);
		}
	}

	protected static void err(String error) {
		System.err.println(error);
	}
	
	protected Model createModel(String name) {

		Model model = UMLFactory.eINSTANCE.createModel();
        model.setName(name);
        
        out("Model '" + model.getQualifiedName() + "' created.");
        return model;

	}	

	
	protected void registerResourceFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
			UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
	}

	protected void registerPathmaps(URI uri) {
		URIConverter.URI_MAP.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP),
			uri.appendSegment("libraries").appendSegment(""));

		URIConverter.URI_MAP.put(URI.createURI(UMLResource.METAMODELS_PATHMAP),
			uri.appendSegment("metamodels").appendSegment(""));

		URIConverter.URI_MAP.put(URI.createURI(UMLResource.PROFILES_PATHMAP),
			uri.appendSegment("profiles").appendSegment(""));
	}

	protected void save(org.eclipse.uml2.uml.Package package_, URI uri) {
		Resource resource = resourceSet.createResource(uri);
		EList contents = resource.getContents();

		contents.add(package_);		
		

		for (Iterator allContents = UMLUtil.getAllContents(package_, true, false); allContents.hasNext();) {

			EObject eObject = (EObject) allContents.next();

			if (eObject instanceof Element) {
				contents.addAll(((Element) eObject).getStereotypeApplications());
			}
		}

		try {
			resource.save(null);
			
			out("Done saving: " + uri.toString());
		} catch (IOException ioe) {
			err(ioe.getMessage());
		}
	}

	protected org.eclipse.uml2.uml.Package load(URI uri) {
		org.eclipse.uml2.uml.Package package_ = null;

		try {
			Resource resource = resourceSet.getResource(uri, true);

			package_ = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(),
					UMLPackage.Literals.PACKAGE);
		} catch (WrappedException we) {
			err(we.getMessage());
		}

		return package_;
	}
	
	protected void importPrimitiveTypes(org.eclipse.uml2.uml.Package model) {
		Model umlLibrary = (Model) load(URI
			.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI));

		umlString = (PrimitiveType) umlLibrary.getOwnedType("String");
		umlBoolean = (PrimitiveType) umlLibrary.getOwnedType("Boolean");
		model.createElementImport(umlString);
		model.createElementImport(umlBoolean);
	}	
	
	
}
