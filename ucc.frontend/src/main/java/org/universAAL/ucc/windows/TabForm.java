package org.universAAL.ucc.windows;

import java.util.ArrayList;
import java.util.Iterator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;

import org.universAAL.ucc.controller.aalspace.PersonWindowController;

public class TabForm extends Form{
	private Button saveButton;
	private Button resetButton;
	private Button editButton;
	private Button deleteButton;
	private String header;
	private String id;
	
	public TabForm() {
		setSizeFull();
		setImmediate(true);
		setWriteThrough(false);
	}
	
	
	public void createFooter() {
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.setMargin(true);
		saveButton = new Button("Save", this, "commit");
		saveButton.setVisible(false);
		resetButton = new Button("Reset", this, "discard");
		resetButton.setVisible(false);
		editButton = new Button("Edit");
		deleteButton = new Button("Delete");
		hl.addComponent(editButton);
		hl.addComponent(saveButton);
		//hl.addComponent(resetButton);
		hl.addComponent(deleteButton);
		setFooter(hl);
	}
	

	public Button getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(Button saveButton) {
		this.saveButton = saveButton;
	}

	public Button getResetButton() {
		return resetButton;
	}

	public void setResetButton(Button resetButton) {
		this.resetButton = resetButton;
	}

	public Button getEditButton() {
		return editButton;
	}

	public void setEditButton(Button editButton) {
		this.editButton = editButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(Button deleteButton) {
		this.deleteButton = deleteButton;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	
	

}
