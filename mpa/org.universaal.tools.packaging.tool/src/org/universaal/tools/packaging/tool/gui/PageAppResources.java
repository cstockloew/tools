package org.universaal.tools.packaging.tool.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.tool.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.ExecutionUnit;
import org.universaal.tools.packaging.tool.util.Dialog;
import org.universaal.tools.packaging.tool.validators.FileV;
import org.universaal.tools.packaging.tool.validators.IntegerV;

public class PageAppResources extends PageImpl {

	private File[] listFilesandDirs;
	private Text result;
	
	protected PageAppResources(String pageName) {
		super(pageName, "Specify additional resource files and folders");
	}

	public void createControl(final Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);	

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 1;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		//List<ExecutionUnit> eus = app.getAppParts().get(partNumber).getExecutionUnits();

		Button b1 = new Button(container, SWT.PUSH);
		b1.setText("Browse Files and Folders");
		
		b1.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				Dialog fd = new Dialog();
				listFilesandDirs = fd.openMulti(parent.getShell(), "Please select files and folders");
				if(listFilesandDirs != null){
					parent.getShell().setCursor(new Cursor(parent.getShell().getDisplay(), SWT.CURSOR_WAIT));
					String tree = generateTree(listFilesandDirs,0);
					result.setText(tree);
					parent.getShell().setCursor(new Cursor(parent.getShell().getDisplay(), SWT.CURSOR_ARROW));
				} else {
					result.setText("");
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	
		
		
		Label l1 = new Label(container, SWT.NULL);
		l1.setText("Selected Files and folders:");
		result = new Text(container, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		result.setLayoutData(new GridData(GridData.FILL_HORIZONTAL, 300));
		
		setPageComplete(true);
	}

	
	@Override
	public boolean nextPressed() {

		//String id = app.getAppParts().get(partNumber).getDeploymentUnits().get(0).getId();

		try{
			if(listFilesandDirs != null){
				app.setAppResouces(listFilesandDirs);
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return true;
	}

	private String generateTree(File[] list, int spaces){
		list = sortList(list);
		String buffer = "";
		for(int i=0; i<list.length; i++){
			buffer += new String(new char[spaces]).replace('\0', ' ');
			if(list[i].isDirectory()){ 
				buffer += "/"+list[i].getName()+System.getProperty("line.separator");
				File[] tmp = list[i].listFiles();
				buffer += generateTree(tmp, (spaces+5));
			} else {
				buffer += list[i].getName()+System.getProperty("line.separator");
			}
		}
		
		return buffer;
	}
	
	private File[] sortList(File[] list){
		List<File> dirs = new ArrayList<File>();
		List<File> files = new ArrayList<File>();
		List<File> merged = new ArrayList<File>();
		
		for(int i=0; i<list.length; i++){
			
			if(list[i].isDirectory()){
				dirs.add(list[i]);
			} else {
				files.add(list[i]);
			}
		}

		if(files != null) merged.addAll(files);
		if(dirs != null) merged.addAll(dirs);
		
		File[] sorted = new File[merged.size()];
		
		for(int i=0; i<merged.size(); i++)
			sorted[i] = merged.get(i);
		
		return sorted;
		
	}
	
	private static <T> T[] concat(T[] first, T[] second) {
		  T[] result = Arrays.copyOf(first, first.length + second.length);
		  System.arraycopy(second, 0, result, first.length, second.length);
		  return result;
	}
	
	//capability
	//describes single offering, mostly used for devices and platforms
	//name.value
}