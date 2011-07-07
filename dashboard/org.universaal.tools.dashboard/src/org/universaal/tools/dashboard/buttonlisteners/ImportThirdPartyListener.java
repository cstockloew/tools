package org.universaal.tools.dashboard.buttonlisteners;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

public class ImportThirdPartyListener implements SelectionListener {

	ViewPart view;

	public ImportThirdPartyListener(ViewPart view) {
		this.view = view;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		IHandlerService handlerService = (IHandlerService)view.getSite().getService(IHandlerService.class);

		try {
			handlerService.executeCommand("org.universaal.importexternalproject.commands.importthirdparty", null);
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotDefinedException e1) {
			MessageDialog.openInformation(view.getSite().getShell(),
					"Command not defined.",
					"This command was not available. " +
					"Please install AAL Studio Developer Depot Client Plugin.");
			e1.printStackTrace();
		} catch (NotEnabledException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotHandledException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

}
