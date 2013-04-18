package org.universAAL.ucc.windows;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.bind.JAXBException;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.universAAL.ucc.controller.aalspace.AddNewHardwareController;

public class AddNewHardwareWindow extends Window {
	private UccUI app;
	private VerticalLayout layout;
	private HardwareWindow hWindow;
//	private String flatId;
	
	public AddNewHardwareWindow(/*String flat,*/ HardwareWindow win, RoomsWindow rWin, UccUI app) throws JAXBException, IOException, ParseException {
		super("Add new Hardware");
		this.app = app;
		this.hWindow = win;
//		this.flatId = flat;
		setWidth(500, Sizeable.UNITS_PIXELS);
		setHeight(400, Sizeable.UNITS_PIXELS);
		layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		center();
		setContent(layout);
		new AddNewHardwareController(this, hWindow, rWin, app);
	}

public void addWindowContent(Component c) {
		layout.addComponent(c);
	}

//public String getFlatId() {
//	return flatId;
//}
//
//public void setFlatId(String flatId) {
//	this.flatId = flatId;
//}


}
