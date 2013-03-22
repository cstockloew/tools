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
import java.util.Collections;

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
import org.universaal.tools.importexternalproject.xmlparser.ProjectSearcher;
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
	private Composite composite;
	private Button btnAll;
	private Button btnOfficialExamples;
	private Button btnThirdPartyApplications;
	private Label lblHeader;

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
		
		lblHeader = new Label(container, SWT.NONE | SWT.WRAP);
		GridData gd_lblHeader = new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1);
		gd_lblHeader.widthHint = 712;
		lblHeader.setLayoutData(gd_lblHeader);
		lblHeader.setText("The project will be imported using SVN. Please note" +
				" that installation of SVN Connectors will be started on the first" +
				" use of SVN - select e.g. the SVN Kit with highest version number.\n"
				+"If asked for a username and password, enter \"anonymous\" as " +
				"username, and blank password");
		
		composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		RadioButtonsListener radio = new RadioButtonsListener();
		btnOfficialExamples = new Button(composite, SWT.RADIO);
		btnOfficialExamples.setText("Official Examples");
		btnOfficialExamples.addSelectionListener(radio);
		
		btnThirdPartyApplications = new Button(composite, SWT.RADIO);
		btnThirdPartyApplications.setText("Third Party Applications");
		btnThirdPartyApplications.addSelectionListener(radio);
		
		btnAll = new Button(composite, SWT.RADIO);
		btnAll.setText("All");
		btnAll.addSelectionListener(radio);
		
		
		new Label(container, SWT.NONE);

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

		setPageComplete(false);

	}

	class ViewContentProvider implements IStructuredContentProvider{
		
		ArrayList<ProjectObject> projectList;
		ArrayList<ProjectObject> examplesList;
		ArrayList<ProjectObject> thirdPartyList;
		XmlParser parser;
		String examples;
		String thirdParty;
		
		/**
		 * Keeps track of three different lists. One containing the official 
		 * examples, one containing third party applications, and one containing
		 * both official examples and third party applications.
		 */
		public ViewContentProvider(){
			projectList = new ArrayList<ProjectObject>();
			examplesList = new ArrayList<ProjectObject>();
			thirdPartyList = new ArrayList<ProjectObject>();
			parser = new XmlParser();
			examples = ((ImportExternalWizard)getWizard()).getExamplesXML();
			thirdParty = ((ImportExternalWizard)getWizard()).getThirdPartyXML();
			
			parser.getAll(thirdParty, thirdPartyList);
			parser.getAll(examples, examplesList);
			
			projectList.addAll(thirdPartyList);
			projectList.addAll(examplesList);
			
			
		}

		@Override
		public void dispose() {
		}

		/**
		 * Is called when the user enters a searchterm. The method checks
		 * which radio button is selected, and only searches through that list.
		 * Afterwards, it sorts the list by name before displaying it.
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			setPageComplete(false);

			ArrayList<ProjectObject> result = new ArrayList<ProjectObject>();
			
			if(!btnAll.isDisposed() && btnAll.getSelection()){
				result = ProjectSearcher.search(projectList, (String)newInput);
			}else if(!btnOfficialExamples.isDisposed() && btnOfficialExamples.getSelection()){
				result = ProjectSearcher.search(examplesList, (String)newInput);
			}else if(!btnThirdPartyApplications.isDisposed() && btnThirdPartyApplications.getSelection()){
				result = ProjectSearcher.search(thirdPartyList, (String)newInput);
			}else{
				result = ProjectSearcher.search(projectList, "");
			}
			Collections.sort(result, new ProjectSearcher());
			projects = new ProjectObject[result.size()];
			for(int i=0; i<result.size();i++){
				projects[i] = result.get(i);
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
			text.setText("");
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
						res.getHostingSite()+"\n \n";
		description += (res.getContainsSubProjects() ? "This project consists of " +
				"several subprojects.\n\n" : "");
				
		description +=res.getDesc()+"\n \n" +
				"Tags:\n";
		ArrayList<String> tags = res.getTags();
		for(int i=0; i<tags.size(); i++){
			description += "-"+tags.get(i) + "\n";
		}
		description += 
				"\nProject License: \n"+
						res.getLicense()+"\n \n"+
				"Project License URL: \n"+
						res.getLicenseUrl();
					
		return description;
	}
	
	/**
	 * Notifies the tableviewer when the radio-button-selection is changed.
	 * @author Adrian
	 *
	 */
	private class RadioButtonsListener implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent e) {
			tableViewer.setInput(text.getText());
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
		
	}
	
}
