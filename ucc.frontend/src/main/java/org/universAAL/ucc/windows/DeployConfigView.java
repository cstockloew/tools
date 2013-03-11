package org.universAAL.ucc.windows;

import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.api.IInstaller;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DeployConfigView extends Window {
	private Label label;
	private TextField txt;
	private NativeSelect select;
	private Button previous;
	private Button next;
	private Button cancel;
	private String base;
	private ResourceBundle bundle;
	private UccUI app;
	
	public DeployConfigView(UccUI app, String serviceId, String uappPath) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		BundleContext bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ServiceReference ref = bc.getServiceReference(IInstaller.class.getName());
		IInstaller installer = (IInstaller) bc.getService(ref);
		setCaption(bundle.getString("deploy.config.capt"));
		this.app = app;
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setSpacing(true);
		vl.setMargin(true);
		label = new Label(bundle.getString("app.part.label"));
		vl.addComponent(label);
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.setMargin(true);
		txt = new TextField(bundle.getString("app.part"));
		txt.setWidth("14em");
		hl.addComponent(txt);
		select = new NativeSelect(bundle.getString("node.label"));
		select.setWidth("14em");
		select.setHeight("2em");
		select.setImmediate(true);
		select.setNullSelectionAllowed(false);
		//Add Nodes to dropdown box
//		for(Map.Entry entry : installer.getPeers().entrySet()) {
//			select.addItem(entry.getValue().toString());
//		}
		hl.addComponent(select);
		vl.addComponent(hl);
		vl.setComponentAlignment(hl, Alignment.MIDDLE_CENTER);
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setMargin(true);
		hl2.setSpacing(true);
		previous = new Button(bundle.getString("prev.button"));
		next = new Button(bundle.getString("next.button"));
		cancel = new Button(bundle.getString("cancel.button"));
		hl2.addComponent(previous);
		hl2.addComponent(next);
		hl2.addComponent(cancel);
		vl.addComponent(hl2);
		vl.setComponentAlignment(hl2, Alignment.BOTTOM_CENTER);
		setContent(vl);
		center();
		setWidth("450px");
		setHeight("250px");
		setResizable(false);
		setModal(true);
		setClosable(false);
	}

	public TextField getTxt() {
		return txt;
	}

	public void setTxt(TextField txt) {
		this.txt = txt;
	}

	public NativeSelect getSelect() {
		return select;
	}

	public void setSelect(NativeSelect select) {
		this.select = select;
	}

	public Button getPrevious() {
		return previous;
	}

	public void setPrevious(Button previous) {
		this.previous = previous;
	}

	public Button getNext() {
		return next;
	}

	public void setNext(Button next) {
		this.next = next;
	}

	public Button getCancel() {
		return cancel;
	}

	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}
	
	

}
