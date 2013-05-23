package org.universAAL.ucc.windows;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.bind.JAXBException;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import org.universAAL.ucc.controller.aalspace.PersonWindowController;

public class HumansWindow extends Window {
	private Tree userTree;
	private HorizontalSplitPanel split;
	private UccUI app;
	private String flatId;
	
	public HumansWindow(UccUI app) throws JAXBException, IOException, ParseException {
		StringBuffer breadcrump = new StringBuffer();
		int counter = 0;
		for(Window w : app.getMainWindow().getChildWindows()) {
			counter++;
			if(counter == app.getMainWindow().getChildWindows().size())
				breadcrump.append(w.getCaption()+" > ");
		}		
		breadcrump.append("Persons");
		setCaption(breadcrump.toString());
//		this.flatId = flat;
		this.app = app;
		setWidth(500, Sizeable.UNITS_PIXELS);
		setHeight(365, Sizeable.UNITS_PIXELS);
		center();
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
		new PersonWindowController(this, app);
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
