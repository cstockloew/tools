package xmleditor.editors;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import xmleditor.model.ProjectModel;

public class FieldsPage extends Composite {

	private XmlEditor parent;
	private Text projectName, developerName, date, url, svnUrl, tags;
	private StyledText description;
	private ProjectModel model;

	/**
	 * @wbp.parser.constructor
	 */
	public FieldsPage(Composite container){
		super(container, SWT.NONE);
	}
	
	public FieldsPage(XmlEditor parent, ProjectModel model, Composite container){
		super(container, SWT.NONE);
		this.parent = parent;
		this.model = model;

		FieldListener listen = new FieldListener();

		this.setLayout(new GridLayout(2, false));

		Label lblProjectName = new Label(this, SWT.NONE);
		lblProjectName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProjectName.setText("Project Name");

		projectName = new Text(this, SWT.BORDER);
		projectName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblDeveloper = new Label(this, SWT.NONE);
		lblDeveloper.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDeveloper.setText("Developer");

		developerName = new Text(this, SWT.BORDER);
		developerName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		Label lblDate = new Label(this, SWT.NONE);
		lblDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDate.setText("Date");

		date = new Text(this, SWT.BORDER);
		date.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		Label lblUrl = new Label(this, SWT.NONE);
		lblUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUrl.setText("URL");

		url = new Text(this, SWT.BORDER);
		url.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		Label lblSvnUrl = new Label(this, SWT.NONE);
		lblSvnUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSvnUrl.setText("SVN URL");

		svnUrl = new Text(this, SWT.BORDER);
		svnUrl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));


		Label lblTags = new Label(this, SWT.NONE);
		lblTags.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTags.setText("Tags");
		lblTags.setToolTipText("Enter project tags, separated by commas. E.g. \"tag, tag2\"");

		tags = new Text(this, SWT.BORDER);
		tags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));


		Label lblDescription = new Label(this, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setText("Description");

		description = new StyledText(this, SWT.BORDER | SWT.WRAP);
		description.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		setFields();

		description.addModifyListener(listen);
		svnUrl.addModifyListener(listen);
		url.addModifyListener(listen);
		date.addModifyListener(listen);
		developerName.addModifyListener(listen);
		projectName.addModifyListener(listen);
		tags.addModifyListener(listen);
	}
	
	public void setFields(){
		projectName.setText(model.getpName());
		developerName.setText(model.getpDev());
		date.setText(model.getpDate());
		url.setText(model.getpUrl());
		svnUrl.setText(model.getpSvnUrl());
		description.setText(model.getpDesc());
		tags.setText(getTagsStringFromModel());
	}
	
	class FieldListener implements ModifyListener{

		@Override
		public void modifyText(ModifyEvent e) {
//			parent.isDirty = true;
			parent.fieldsModified();
			
			if(e.getSource()==projectName){
				model.setpName(projectName.getText());
			}else if(e.getSource() == developerName){
				model.setpDev(developerName.getText());
			}else if(e.getSource()==date){
				model.setpDate(date.getText());
			}else if(e.getSource()==url){
				model.setpUrl(url.getText());
			}else if(e.getSource()==svnUrl){
				model.setpSvnUrl(svnUrl.getText());
			}else if(e.getSource()==description){
				model.setpDesc(description.getText());
			}else if(e.getSource()==tags)
				model.setpTags(getTagsFromFields());
		}
	}
	
	private ArrayList<String> getTagsFromFields(){
		ArrayList<String> result = new ArrayList<String>();
		String string = tags.getText();
		String tempString="";
		int prevComma=0;
		int nextComma=string.indexOf(',');
		while(nextComma>=0){
			tempString=string.substring(prevComma, nextComma);
			prevComma=nextComma+1;
			nextComma = string.indexOf(',', prevComma);
			result.add(tempString.trim());
		}
		result.add((string.substring(prevComma)).trim());
		return result;
	}
	
	private String getTagsStringFromModel(){
		String result="";
		ArrayList<String> tags = model.getpTags();
		for(int i=0; i<tags.size();i++){
			result += tags.get(i);
			if(i!=tags.size()-1){
				result += ", ";
			}
		}
		return result;
	}


}