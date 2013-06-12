package org.universAAL.ucc.windows;

import java.util.List;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DeinstallWindow extends Window {
	private ListSelect list;
	private Button del;
	private Button cancel;
	
	public DeinstallWindow(List<String> serviceIds) {
		super("Deinstallation");
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setSpacing(true);
		vl.setMargin(true);
		list = new ListSelect("AAL services:");
		list.setImmediate(true);
		list.setRows(10);
		list.setColumns(10);
		list.setMultiSelect(false);
		for(String item : serviceIds) {
			list.addItem(item);
		}
		vl.addComponent(list);
		vl.setComponentAlignment(list, Alignment.MIDDLE_CENTER);
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		del = new Button("Delete");
		cancel = new Button("Close");
		hl.addComponent(del);
		hl.addComponent(cancel);
		vl.addComponent(hl);
		vl.setComponentAlignment(hl, Alignment.BOTTOM_CENTER);
		setWidth("400px");
		setHeight("350px");
		center();
		setContent(vl);
	}

	public ListSelect getList() {
		return list;
	}

	public void setList(ListSelect list) {
		this.list = list;
	}

	public Button getDel() {
		return del;
	}

	public void setDel(Button del) {
		this.del = del;
	}

	public Button getCancel() {
		return cancel;
	}

	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}
	
	

}
