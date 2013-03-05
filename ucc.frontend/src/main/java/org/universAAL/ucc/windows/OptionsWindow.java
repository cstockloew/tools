package org.universAAL.ucc.windows;

import java.util.Arrays;
import java.util.ResourceBundle;

import org.universAAL.ucc.controller.install.OptionsController;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Window to select if an AAL service shall be installed or updated. 
 * This Window has a text field to insert the current session key and one to insert the path of
 * the usrv file.
 * 
 * @author merkle
 *
 */
public class OptionsWindow extends Window {
	private Form form1;
	private Form form2;
	private TextField sessionKey1;
	private TextField url1;
	private TextField sessionKey2;
	private TextField url2;
	private OptionGroup radio;
	private Button invoke;
	private String base;
	private ResourceBundle bundle;
	
	/**
	 * Creates a new OptionsWindow
	 * @param app the main Application
	 */
	public OptionsWindow(UccUI app) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		setCaption(bundle.getString("option.caption"));
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setSpacing(true);
		vl.setMargin(true);
		setContent(vl);
		form1 = new Form();

		form1.setWriteThrough(false);
		radio = new OptionGroup("",Arrays.asList(new String[]{bundle.getString("options.install.label"), bundle.getString("update.label")}));
		radio.setImmediate(true);
		radio.setNullSelectionAllowed(false);
		vl.addComponent(radio);
		sessionKey1 = new TextField(bundle.getString("sessionKey.label"));
		sessionKey1.setImmediate(true);
		sessionKey1.setWidth("14em");
		form1.addField(bundle.getString("sessionKey.label"), sessionKey1);
		url1 = new TextField(bundle.getString("usrv.url.label"));
		url1.setImmediate(true);
		url1.setWidth("14em");
		form1.addField(bundle.getString("usrv.url.label"), url1);
		vl.addComponent(form1);

		form2 = new Form();
		sessionKey2 = new TextField(bundle.getString("sessionKey.label"));
		sessionKey2.setImmediate(true);
		sessionKey2.setWidth("14em");
		form2.addField(bundle.getString("sessionKey.label"), sessionKey2);
		url2 = new TextField(bundle.getString("usrv.url.label"));
		url2.setImmediate(true);
		url2.setWidth("14em");
		form2.addField(bundle.getString("usrv.url.label"), url2);
		vl.addComponent(form2);
		invoke = new Button(bundle.getString("invoke.button"));
		vl.addComponent(invoke);
		vl.setComponentAlignment(invoke, Alignment.BOTTOM_RIGHT);
		setWidth("450px");
		setHeight("400px");
		center();
		setClosable(false);
		setModal(true);
		new OptionsController(app, this);
		
	}
	
	/**
	 * Gets the first form of the window
	 * @return first form
	 */
	public Form getForm1() {
		return form1;
	}

	/**
	 * Sets the first form of the window
	 * @param form1
	 */
	public void setForm1(Form form1) {
		this.form1 = form1;
	}

	/**
	 * Gets the second form of the window.
	 * @return second form
	 */
	public Form getForm2() {
		return form2;
	}

	/**
	 * Sets the second form of the Window
	 * @param form2 the second form of the window
	 */
	public void setForm2(Form form2) {
		this.form2 = form2;
	}

	/**
	 * Gets the first session key
	 * @return the first session key
	 */
	public TextField getSessionKey1() {
		return sessionKey1;
	}

	/**
	 * Sets the first session key	
	 * @param sessionKey1 the first session key
	 */
	public void setSessionKey1(TextField sessionKey1) {
		this.sessionKey1 = sessionKey1;
	}

	/**
	 * Gets the first url to the usrv file
	 * @return the first url
	 */
	public TextField getUrl1() {
		return url1;
	}

	/**
	 * Sets the first url to the usrv file
	 * @param url1 the first url
	 */
	public void setUrl1(TextField url1) {
		this.url1 = url1;
	}

	/**
	 * Gets the second session key
	 * @return the second session key
	 */
	public TextField getSessionKey2() {
		return sessionKey2;
	}

	/**
	 * Sets the second session key
	 * @param sessionKey2 the second session key
	 */
	public void setSessionKey2(TextField sessionKey2) {
		this.sessionKey2 = sessionKey2;
	}

	/**
	 * Gets the second url to urv file path
	 * @return second url
	 */
	public TextField getUrl2() {
		return url2;
	}

	/**
	 * Sets the second url oft the usr file path
	 * @param url2 the second url
	 */
	public void setUrl2(TextField url2) {
		this.url2 = url2;
	}

	/**
	 * Gets the radio button, which shows,
	 * if you install or update the current usrv
	 * file.
	 * @return radio button with decision
	 */
	public OptionGroup getRadio() {
		return radio;
	}

	/**
	 * Sets radio button
	 * @param radio the radio button
	 */
	public void setRadio(OptionGroup radio) {
		this.radio = radio;
	}

	/**
	 * Gets the invoke button
	 * @return invoke button
	 */
	public Button getInvoke() {
		return invoke;
	}

	/**
	 * Sets the invoke button
	 * @param invoke the invoke button
	 */
	public void setInvoke(Button invoke) {
		this.invoke = invoke;
	}
	
	

}
