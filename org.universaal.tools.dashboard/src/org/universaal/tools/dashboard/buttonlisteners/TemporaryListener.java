package org.universaal.tools.dashboard.buttonlisteners;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.part.ViewPart;

public class TemporaryListener implements SelectionListener {
	
	ViewPart view;
	String message;
	
	public TemporaryListener(ViewPart view, String input){
		this.view = view;
		this.message = input;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		MessageDialog.openInformation(
				view.getSite().getShell(),
				"Sample View",
				message);
		
	}

}
