package org.universAAL.ucc.windows;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.bind.JAXBException;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.universAAL.ucc.controller.aalspace.AddNewPersonController;

public class AddNewPersonWindow extends Window {
	private UccUI app;
	private VerticalLayout layout;
	private HumansWindow hWindow;
	private String flatId;
	
	public AddNewPersonWindow(HumansWindow win, UccUI app) throws JAXBException, IOException, ParseException {
		super("Add new Person");
		this.app = app;
		this.hWindow = win;
		center();
		setWidth(500, Sizeable.UNITS_PIXELS);
		setHeight(400, Sizeable.UNITS_PIXELS);
		layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		setContent(layout);
		new AddNewPersonController(this, hWindow, app);
	}

public void addWindowContent(Component c) {
		layout.addComponent(c);
	}

public String getFlatId() {
	return flatId;
}

public void setFlatId(String flatId) {
	this.flatId = flatId;
}


}
