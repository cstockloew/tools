package org.universaal.tools.transformationcommand.handlers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mofscript.MOFScriptModel.MOFScriptSpecification;
import org.eclipse.mofscript.parser.MofScriptParseError;
import org.eclipse.mofscript.parser.ParserUtil;
import org.eclipse.mofscript.runtime.ExecutionManager;
import org.eclipse.mofscript.runtime.ExecutionMessageListener;
import org.eclipse.mofscript.runtime.MofScriptExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public abstract class TransformationHandler extends AbstractHandler implements ExecutionMessageListener {
	String transformationFileName;
	String thisBundleName;
	private MessageConsole myConsole;
	private MessageConsoleStream stream;


	/**
	 * Finally, I am subversive
	 */
	public void setFileAndBundleName(String theTransformationFile, String theBundle) {
		transformationFileName = theTransformationFile;
		thisBundleName = theBundle;	

	}	



	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// First, retrieve the current selection and check whether it is a file
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ISelection selection = window.getSelectionService().getSelection();
		if ((selection != null) && (selection instanceof StructuredSelection)) {
			Object selectedFile = ((StructuredSelection)selection).getFirstElement();
			if (selectedFile instanceof IFile) {
				// If the selection is a file, start the transformation
				doTransform((IFile)selectedFile, event);
			}
		}			
		else {
			MessageDialog.openInformation(
					window.getShell(),
					"Transformation Command",
					"The selection is not a valid source for transformation" );			
		}		

		return null;
	}


	public void doTransform(IFile inputFile, ExecutionEvent event) {
		myConsole = findConsole("MOFScript2 Console");
		stream = myConsole.newMessageStream();

		IPath path = new Path(transformationFileName);
		URL l = FileLocator.find(Platform.getBundle(thisBundleName), path, null);

		try {
			l = FileLocator.toFileURL(l);
		} catch (IOException e) {
			System.out.println("Could not locate transformation script");
			return;
		}
		if (l != null) {
			System.out.print("Running transformation script: ");
			System.out.println(l);
		}

		ParserUtil parserUtil = new ParserUtil();
		ExecutionManager execMgr = ExecutionManager.getExecutionManager();       
		//
		// The parserutil parses and sets the input transformation model
		// for the execution manager.
		//

		File f = null;        
		try {
			f = new File (l.toURI());
		} catch (URISyntaxException e) {
			System.out.println("Could not find URI for transformation script");
			return;
		}

		MOFScriptSpecification spec = parserUtil.parse(f, true);
		// check for errors:
		int errorCount = ParserUtil.getModelChecker().getErrorCount();
		Iterator errorIt = ParserUtil.getModelChecker().getErrors(); // Iterator of MofScriptParseError objects

		System.out.println ("Preparing transformation...");       
		if (errorCount > 0) {

			System.out.println ("Error parsing transformation: " + errorCount + " errors");       

			for (;errorIt.hasNext();) {
				MofScriptParseError parseError = (MofScriptParseError) errorIt.next();
				System.out.println("\t \t: Error: " + parseError.toString());
			}           
			return;           
		}



		// load source model
		XMIResourceFactoryImpl _xmiFac = new XMIResourceFactoryImpl();         
		EObject sourceModel = null;
		//        File sourceModelFile = new File(selectedFile.getLocationURI()); // new File ("SM.ecore");       
		ResourceSet rSet = new ResourceSetImpl ();
		rSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", _xmiFac);

		//System.out.println("Converting URI for selected file: " + inputFile.getLocationURI().toString());

		URI uri = null;
		try {
			uri = URI.createURI(inputFile.getLocationURI().toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		System.out.println("Converted URI for selected file");

		Resource resource = rSet.getResource(uri, true);

		if (resource != null) {
			if (resource.getContents().size() > 0) {
				sourceModel = (EObject) resource.getContents().get(0);
			}
		}       

		if (sourceModel == null) {
			System.out.println("Source model could not be located");
			return;
		}
		System.out.println("Adding source model");
        
		// set the source model for the execution manager   
		execMgr.addSourceModel(sourceModel);

		// sets the root output directory, if any is desired (e.g. "c:/temp")
		IProject project = inputFile.getProject();
		execMgr.setRootDirectory(findDirectory(project));

		
		// if true, files are not generated to the file system, but populated into a filemodel
		// which can be fetched afterwards. Value false will result in standard file generation
		execMgr.setUseFileModel(false);
		// Turns on/off system logging
		execMgr.setUseLog(false);
		
		
		execMgr.setBlockCommentTag("//");
		
		// Adds an output listener for the transformation execution.
		execMgr.getExecutionStack().addOutputMessageListener(this);   
		try {
			System.out.println("Performing transformation");

			execMgr.executeTransformation();           
			System.out.println("Completed transformation");
			//New code
			project.refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (MofScriptExecutionException mex) {
			mex.printStackTrace();
		} catch (CoreException e){
			e.printStackTrace();
		}     
	}

	@Override
	public void executionMessage(String arg0, String arg1) {
		// Ignore messages from MOFscript for now
//		System.out.println(arg1);		
		if (arg0 == null || arg0.equals("") || arg0.equals("println"))
			stream.println(arg1);
		else if (arg0.equalsIgnoreCase("print"))
			stream.print(arg1);
	}

	/**
	 * Reads preferences and finds the correct directory to save files to.
	 * @param inputFile
	 * @return
	 */
	private String findDirectory(IProject project){
		if(getAbsolutePathBooleanFromPreferences()){
			return getDirectoryFromPreferences();
		}else{
			String result = project.getLocation() + 
					(getDirectoryFromPreferences().charAt(0)=='/' ? "" : "/")+ 
					getDirectoryFromPreferences();
			System.out.println("Returning " + result);
			return result;
		}

	}

	protected abstract String getDirectoryFromPreferences();
	protected abstract boolean getAbsolutePathBooleanFromPreferences();
	
	private MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	   }
}
