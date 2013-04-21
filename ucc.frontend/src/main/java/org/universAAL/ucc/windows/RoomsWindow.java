package org.universAAL.ucc.windows;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.bind.JAXBException;

import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

import org.universAAL.ucc.controller.aalspace.HardwareWindowController;
import org.universAAL.ucc.controller.aalspace.RoomsWindowController;

public class RoomsWindow extends Window {
	private Tree userTree;
	private HorizontalSplitPanel split;
	private UccUI app;
	private String flatId;
	private RoomsWindowController rwc;
	
	public RoomsWindow(String flat, UccUI app) throws JAXBException, IOException, ParseException {
		//super("Rooms of "+flat);
		StringBuffer breadcrump = new StringBuffer();
		int counter = 0;
		for(Window w : app.getMainWindow().getChildWindows()) {
			counter++;
			if(counter == app.getMainWindow().getChildWindows().size())
				breadcrump.append(w.getCaption()+" > ");
		}
		
		breadcrump.append("Rooms of "+flat);
		setCaption(breadcrump.toString());
//		this.flatId = flat;
		center();
		this.app = app;
		setWidth(500, Sizeable.UNITS_PIXELS);
		setHeight(365, Sizeable.UNITS_PIXELS);
		userTree = new Tree();
		userTree.setImmediate(true);
		userTree.setSelectable(true);
		userTree.setNullSelectionAllowed(false);
		split = new HorizontalSplitPanel();
		//split.setSizeFull();
		split.setMargin(true);
		split.setStyleName(Reindeer.SPLITPANEL_SMALL);
		split.setSplitPosition(200, Sizeable.UNITS_PIXELS);
		split.setLocked(true);
		setContent(split);
		rwc = new RoomsWindowController(this, app);
	}
	
	public RoomsWindowController getRwc() {
		return rwc;
	}

	public void setRwc(RoomsWindowController rwc) {
		this.rwc = rwc;
	}

	public void addFirstComponent(Component c) {
		split.setFirstComponent(c);
	}
	
	public void addSecondComponent(Component c) {
		split.setSecondComponent(c);
	}

	public Tree getUserTree() {
		return userTree;
	}

	public void setUserTree(Tree userTree) {
		this.userTree = userTree;
	}

	public String getFlatId() {
		return flatId;
	}

	public void setFlatId(String flatId) {
		this.flatId = flatId;
	}	
	
	


}
