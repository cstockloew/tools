package org.universaal.tools.packaging.tool.util;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class Dialog {

	public File open(Shell shell, String[] filterExt, boolean open, String topText){

		FileDialog fd;
		if(open)
			fd = new FileDialog(shell, SWT.OPEN);
		else
			fd = new FileDialog(shell, SWT.SAVE);
		fd.setText(topText);
		fd.setFilterPath("C:/");
		fd.setFileName("");
		//String[] filterExt = {"*.uapp"};
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		if ( selected == null ) {
			return null;
		} else {
			return new File(selected);
		}
	}

	public File open(Shell shell, String filename, String[] filterExt, boolean open, String topText){

		FileDialog fd;
		if(open)
			fd = new FileDialog(shell, SWT.OPEN);
		else
			fd = new FileDialog(shell, SWT.SAVE);
		fd.setText(topText);
		fd.setFilterPath("C:/");
		fd.setFileName(filename);
		//String[] filterExt = {"*.uapp"};
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		if ( selected == null ) {
			return null;
		} else {
			return new File(selected);
		}
	}
}