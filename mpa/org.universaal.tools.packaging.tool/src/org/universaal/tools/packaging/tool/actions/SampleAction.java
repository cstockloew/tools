package org.universaal.tools.packaging.tool.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.handlers.HandlerUtil;
import org.universaal.tools.packaging.tool.gui.GUI;
import org.universaal.tools.packaging.tool.parts.MultipartApplication;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate 
 */
public class SampleAction extends AbstractHandler {

	public MultipartApplication app;
	public static GUI gui;

	public Object execute(ExecutionEvent event) throws ExecutionException {

		app = new MultipartApplication();

		gui = new GUI(app, event);

		IWorkbenchWindow w = HandlerUtil.getActiveWorkbenchWindow(event);
		WizardDialog wizardDialog = new WizardDialog(w.getShell(), gui);
		wizardDialog.open();

		//		Marshaller m = new Marshaller(new File("c:\\testtesttest.xml"));
		//		try {
		//			m.marshalToMPA();
		//		} catch (JAXBException e) {
		//			e.printStackTrace();
		//		}

		return null;
	}

	public MultipartApplication getApp() {
		return app;
	}
}