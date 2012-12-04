package org.universaal.uaalpax.model;

public interface ModelDialogProvider {
	public int openDialog(String title, String message, String... buttons);
	
	public void showErrorMessage(String title, String message);
}
