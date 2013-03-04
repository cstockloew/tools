package org.universaal.tools.packaging.tool.util;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class Dialog {

	public File open(Shell s){

		FileDialog fd = new FileDialog(s, SWT.OPEN);
		fd.setText("Path to UAPP file");
		fd.setFilterPath("C:/");
		fd.setFileName("");
		String[] filterExt = {"*.*"};//{ "*.txt", "*.doc", ".rtf", "*.*" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();

		return new File(selected);
	}
	
	public File open(Shell s, String filename){

		FileDialog fd = new FileDialog(s, SWT.OPEN);
		fd.setText("Path to UAPP file");
		fd.setFilterPath("C:/");
		fd.setFileName(filename);
		String[] filterExt = {"*.*"};//{ "*.txt", "*.doc", ".rtf", "*.*" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();

		return new File(selected);
	}
}
