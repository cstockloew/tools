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

public class UploadOpenSourceListener implements SelectionListener {

	ViewPart view;
	
	public UploadOpenSourceListener(ViewPart input) {
		this.view = input;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		IHandlerService handlerService = (IHandlerService)view.getSite().getService(IHandlerService.class);
		try {
			handlerService.executeCommand("org.universaal.tools.uploadopensourceplugin.commands.uploadopensource", null);
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotDefinedException e1) {
			MessageDialog.openInformation(view.getSite().getShell(),
					"Command not defined.",
					"This command was not available. " +
					"Please install AAL Studio BuildPlugin.");
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
		
		
		

	}

}
