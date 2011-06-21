package org.universaal.tools.dashboard.buttonlisteners;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.ui.part.ViewPart;

public class TemporaryListener implements MouseListener {
	
	ViewPart view;
	String message;
	
	public TemporaryListener(ViewPart view, String input){
		this.view = view;
		this.message = input;
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub
		MessageDialog.openInformation(
				view.getSite().getShell(),
				"Sample View",
				message);
	}

}
