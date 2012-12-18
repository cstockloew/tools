package org.universaal.uaalpax.model;

import org.eclipse.swt.widgets.Shell;

public interface ModelDialogProvider {
	public int openDialog(String title, String message, String... buttons);
	
	public void showErrorMessage(String title, String message);
	
	public Shell getShell();
}
