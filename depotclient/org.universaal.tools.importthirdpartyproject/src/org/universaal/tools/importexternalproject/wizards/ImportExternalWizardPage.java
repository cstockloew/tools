/*
	Copyright 2011 SINTEF, http://www.sintef.no
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.tools.importexternalproject.wizards;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.universaal.tools.importexternalproject.xmlparser.ProjectObject;
import org.universaal.tools.importexternalproject.xmlparser.XmlParser;

/**
 * The only page of the Import Third Party Application wizard.
 * 
 * @author Adrian
 *
 */
public class ImportExternalWizardPage extends WizardPage {
	private Table table;
	private TableColumn nameClm, dateClm, authorClm;
	private ProjectObject[] projects;
	private String files;
	private TableViewer tableViewer;
	private StyledText styledText;
	private Label lblEnterSearchTerm;
	private Text text;
	private Button btnSearch;
	private Button btnListAll;
	private boolean importExtension;

	/**
	 * Creates the page, and sets title. The boolean input tells the object
	 * whether it was created by the command, or launched from the File->Import.
	 * This is done to give the window the correct size no matter where it is
	 * created from.
	 * @param input true if created by File->Import. False otherwise.
	 */
	protected ImportExternalWizardPage(boolean input) {
		super("Import external project");
		setTitle("Import External Project.");
		setDescription("Please select a project to import.");
		importExtension=input;
	}

	@Override
	public void createControl(Composite parent) {

		//Sets the size if it was created by File->Import.
		if(importExtension){
			getWizard().getContainer().getShell().setSize(850, 500);
		}
		
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(4, false));

		lblEnterSearchTerm = new Label(container, SWT.NONE);
		lblEnterSearchTerm.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEnterSearchTerm.setText("Enter search term:");

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		text.addFocusListener(new SearchFieldFocusListener());

		btnSearch = new Button(container, SWT.NONE);
		btnSearch.setText("Search");
		btnSearch.addSelectionListener(new Search());

		btnListAll = new Button(container, SWT.NONE);
		btnListAll.setText("List all");
		btnListAll.addSelectionListener(new ListAll());

		tableViewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 2));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		nameClm = new TableColumn(table, SWT.LEAD);
		nameClm.setResizable(false);
		nameClm.setText("Project Name");
		nameClm.setWidth(200);

		dateClm = new TableColumn(table, SWT.LEAD);
		dateClm.setResizable(false);
		dateClm.setText("Uploaded");
		dateClm.setWidth(100);

		authorClm = new TableColumn(table, SWT.LEFT);
		authorClm.setResizable(false);
		authorClm.setText("Developer");
		authorClm.setWidth(200);
		
		tableViewer.setContentProvider(new ViewContentProvider());
		tableViewer.setLabelProvider(new ViewLabelProvider());
		tableViewer.setInput("");
		tableViewer.addSelectionChangedListener(new ChoiceListener());

		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblDescription.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblDescription.setText("Description:");

		styledText = new StyledText(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		styledText.setEditable(false);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		setPageComplete(false);

	}

	class ViewContentProvider implements IStructuredContentProvider{

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
		}

		/**
		 * This is used to update the list when the user enters a searchterm,
		 * and presses Search. It first searches for matches by name, and then
		 * by tags. Name-matches are given a flag to show that they matched by 
		 * name, and will always be displayed before the projects that only match
		 * by tags.
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			setPageComplete(false);
			ArrayList<ProjectObject> nameResult = new ArrayList<ProjectObject>();
			XmlParser parser = new XmlParser();
			files = ((ImportExternalWizard)getWizard()).getXML();
			String searchString = (String)newInput;

			//These methods take an ArrayList as input nameResult, and places
			//the matches directly into it instead of returning values
			parser.searchNames(files, nameResult, searchString);
			parser.searchTags(files, nameResult, searchString);

			projects = new ProjectObject[nameResult.size()];
			for(int i=0;i<nameResult.size();i++){
				projects[i] = nameResult.get(i);
			}
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return projects;
		}

	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider{

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			switch(columnIndex){
			case 0: return ((ProjectObject)element).getName();
			case 1: return ((ProjectObject)element).getDate();
			case 2: return ((ProjectObject)element).getDeveloper();
			default: return "";
			}
		}

	}

	private class ChoiceListener implements ISelectionChangedListener{

		/**
		 * Marks the page as complete so that the wizard can be finished, and 
		 * also sets the currentproject in the main part of the wizard.
		 */
		@Override
		public void selectionChanged(SelectionChangedEvent arg0) {
			setPageComplete(true);
			int index = tableViewer.getTable().getSelectionIndex();
			ProjectObject res = (ProjectObject) tableViewer.getElementAt(index);
			if(res!=null){
				((ImportExternalWizard)getWizard()).setChosen(res);
				String description = buildDescription(res);
				styledText.setText(description);
				
			}
		}
	}

	/**
	 * SelectionListener tied to the Search-button. It sets the searchstring as
	 * new input for the TableViewer, and triggers a search for that string.
	 * @author Adrian
	 *
	 */
	private class Search implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent e) {
			tableViewer.setInput(text.getText());

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub

		}
	}
	
	/**
	 * SelectionListener tied to the List All-button. It sets an empty string 
	 * as input for the TableViewer, and as a result, the tableviewer displays
	 * all projects in the xml-file.
	 * @author Adrian
	 *
	 */
	private class ListAll implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent e) {
			tableViewer.setInput("");
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
		
	}
	
	/**
	 * Disables the wizard "Finish"-button when the search-field has focus. 
	 * Workaround to avoid users accidentally pressing enter to do a search, as 
	 * this would instead finish the wizard and import a selected project.
	 * @author Adrian
	 *
	 */
	private class SearchFieldFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			setPageComplete(false);
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * Builds the description that is displayed in the StyledText-box.
	 * @param res - The ProjectObject that will be described.
	 * @return String containing a formatted description.
	 */
	private String buildDescription(ProjectObject res){
		String description = 
				"Project homepage: \n"+
						res.getHomePage()+"\n\n"+
				"Project hosted at: \n"+
						res.getHostingSite()+"\n \n"+ res.getDesc()+"\n \n" +
				"Tags:\n";
		ArrayList<String> tags = res.getTags();
		for(int i=0; i<tags.size(); i++){
			description += tags.get(i) + "\n";
		}
		return description;
	}

}
