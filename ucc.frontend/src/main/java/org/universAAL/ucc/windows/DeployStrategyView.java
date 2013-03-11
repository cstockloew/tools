package org.universAAL.ucc.windows;

import java.util.Arrays;
import java.util.ResourceBundle;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DeployStrategyView extends Window {
	private Form form;
	private TextField txt;
	private OptionGroup options;
	private Button ok;
	private Button cancel;
	private String base;
	private ResourceBundle bundle;
	
	public DeployStrategyView(String name, String serviceId, String uaapPath) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		setCaption(bundle.getString("header.deploy.strategy"));
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setMargin(true);
		vl.setSpacing(true);
		form = new Form();
		txt = new TextField(bundle.getString("application.label"));
		txt.setWidth("14em");
		txt.setValue(name);
		form.addField(bundle.getString("application.label"), txt);
		vl.addComponent(form);
		options = new OptionGroup("", Arrays.asList(new String[]{bundle.getString("opt.available.nodes"), bundle.getString("opt.selected.nodes")}));
		options.select(bundle.getString("opt.available.nodes"));
		options.setImmediate(true);
		options.setNullSelectionAllowed(false);
		vl.addComponent(options);
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.setMargin(true);
		ok = new Button(bundle.getString("ok.button"));
		cancel = new Button(bundle.getString("cancel.button"));
		hl.addComponent(ok);
		hl.addComponent(cancel);
		vl.addComponent(hl);
		vl.setComponentAlignment(hl, Alignment.BOTTOM_CENTER);
		setWidth("425px");
		setClosable(false);
		center();
		setResizable(false);
		setModal(true);
		setContent(vl);
	}

	public TextField getTxt() {
		return txt;
	}

	public void setTxt(TextField txt) {
		this.txt = txt;
	}

	public OptionGroup getOptions() {
		return options;
	}

	public void setOptions(OptionGroup options) {
		this.options = options;
	}

	public Button getOk() {
		return ok;
	}

	public void setOk(Button ok) {
		this.ok = ok;
	}

	public Button getCancel() {
		return cancel;
	}

	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}
	
	

}
