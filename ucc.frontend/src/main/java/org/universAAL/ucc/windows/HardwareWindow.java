package org.universAAL.ucc.windows;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.bind.JAXBException;

import sun.awt.WindowClosingListener;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

import org.universAAL.ucc.controller.aalspace.HardwareWindowController;
import org.universAAL.ucc.controller.aalspace.PersonWindowController;
import org.universAAL.ucc.subscriber.SensorActivityTimeChangedListener;
import org.universAAL.ucc.subscriber.SensorEventSubscriber;

public class HardwareWindow extends Window implements Window.CloseListener{
	private Tree userTree;
	private HorizontalSplitPanel split;
	private UccUI app;
//	private String flatId;
	private HardwareWindowController hwc;
	
	public HardwareWindow(/*String flat,*/ UccUI app) throws JAXBException, IOException, ParseException {
		//super("Hardware of "+flat);
//		this.flatId = flat;
		this.app = app;
		StringBuffer breadcrump = new StringBuffer();
		int counter = 0;
		for(Window w : app.getMainWindow().getChildWindows()) {
			counter++;
			if(counter == app.getMainWindow().getChildWindows().size())
				breadcrump.append(w.getCaption()+" > ");
		}
//		breadcrump.append("Hardware of "+flat);
//		setCaption(breadcrump.toString());
		setCaption("Hardware");
		center();
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
		hwc = new HardwareWindowController(this, app);
	}
	
	public HardwareWindowController getHwc() {
		return hwc;
	}

	public void setHwc(HardwareWindowController hwc) {
		this.hwc = hwc;
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

//	public String getFlatId() {
//		return flatId;
//	}
//
//	public void setFlatId(String flatId) {
//		this.flatId = flatId;
//	}


	public void windowClose(CloseEvent e) {
//		hwc.getSensorEventSubscriber().removeListener(hwc);
		
	}

}
