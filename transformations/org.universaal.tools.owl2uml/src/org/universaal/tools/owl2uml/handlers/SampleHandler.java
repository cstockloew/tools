package org.universaal.tools.owl2uml.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {

	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);

		IStructuredSelection selectedFileSelection = (IStructuredSelection) selection;

		Object obj = selectedFileSelection.getFirstElement();
		IResource selectedFile = (IResource) obj;

		String selectedInput = selectedFile.getLocation().toString();
		String umlOutput = selectedInput.substring(
				selectedInput.lastIndexOf("\\") + 1,
				selectedInput.lastIndexOf("."))
				+ ".uml";
		String replumlFile = umlOutput.replace("/", "\\");

		String[] arg = { selectedInput, replumlFile, "UML2" };

		org.universaal.tools.owl2uml.OWL2UML.main(arg);
		return null;
	}

	protected String getPersistentProperty(IResource res, QualifiedName qn) {
		try {
			return res.getPersistentProperty(qn);
		} catch (CoreException e) {
			return "";
		}
	}
}
