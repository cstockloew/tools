package org.universAAL.ucc.windows;

import java.util.Locale;
import java.util.ResourceBundle;

import org.universAAL.ucc.controller.preferences.PreferencesController;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class PreferencesWindow extends Window {
	private Button save;
	private Button reset;
	private TextField userTxt;
	private PasswordField pwdTxt;
	private TextField portTxt;
	private TextField urlTxt;
	private NativeSelect langSelect;
	private String base;
	private ResourceBundle bundle;
	private UccUI app;
	private PreferencesController con;
	private HorizontalLayout hl;

	
	public PreferencesWindow(UccUI app) {
		base = "resources.ucc";
		bundle = ResourceBundle.getBundle(base);
		this.app = app;
		setCaption(bundle.getString("preferences.capt"));
		setStyleName(Reindeer.WINDOW_LIGHT);
		setDraggable(false);
		setResizable(false);
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setSpacing(true);
		vl.setMargin(true);
		Label header = new Label(bundle.getString("header.pref"), Label.CONTENT_XHTML);
		vl.addComponent(header);
		userTxt = new TextField(bundle.getString("user.label"));
		userTxt.setImmediate(true);
		userTxt.setEnabled(false);
		vl.addComponent(userTxt);
		pwdTxt = new PasswordField(bundle.getString("pwd.label"));
		pwdTxt.setImmediate(true);
		pwdTxt.setEnabled(false);
		vl.addComponent(pwdTxt);
		Label sep1 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep1);
		urlTxt = new TextField(bundle.getString("url.label"));
		urlTxt.setImmediate(true);
		urlTxt.setEnabled(false);
		vl.addComponent(urlTxt);
		portTxt = new TextField(bundle.getString("port.label"));
		portTxt.setImmediate(true);
		portTxt.setEnabled(false);
		vl.addComponent(portTxt);
		Label sep2 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep2);
		langSelect = new NativeSelect(bundle.getString("lang.label"));
		langSelect.addItem(bundle.getString("english"));
		langSelect.addItem(bundle.getString("german"));
		if(Locale.getDefault().getLanguage().equals("de")) {
			langSelect.select(bundle.getString("german"));
		} else {
			langSelect.select(bundle.getString("english"));
		}
		langSelect.setEnabled(false);
		vl.addComponent(langSelect);
		
		hl = new HorizontalLayout();
//		hl.setSpacing(true);
//		hl.setMargin(true);
		save = new Button(bundle.getString("save.button"));
		save.setVisible(false);
		reset = new Button(bundle.getString("edit.button"));
		hl.addComponent(save);
		hl.addComponent(reset);
		vl.addComponent(hl);
		vl.setComponentAlignment(hl, Alignment.BOTTOM_RIGHT);
		setContent(vl);
		setWidth("265px");
//		setHeight("400px");
		setPositionX(app.getMainWindow().getBrowserWindowWidth()-325);
		setPositionY(45);
		con = new PreferencesController(app, this);
	}

	public Button getSave() {
		return save;
	}

	public void setSave(Button save) {
		this.save = save;
	}

	public Button getReset() {
		return reset;
	}

	public void setReset(Button reset) {
		this.reset = reset;
	}

	public TextField getUserTxt() {
		return userTxt;
	}

	public void setUserTxt(TextField userTxt) {
		this.userTxt = userTxt;
	}

	public PasswordField getPwdTxt() {
		return pwdTxt;
	}

	public void setPwdTxt(PasswordField pwdTxt) {
		this.pwdTxt = pwdTxt;
	}

	public TextField getPortTxt() {
		return portTxt;
	}

	public void setPortTxt(TextField portTxt) {
		this.portTxt = portTxt;
	}

	public TextField getUrlTxt() {
		return urlTxt;
	}

	public void setUrlTxt(TextField urlTxt) {
		this.urlTxt = urlTxt;
	}

	public NativeSelect getLangSelect() {
		return langSelect;
	}

	public void setLangSelect(NativeSelect langSelect) {
		this.langSelect = langSelect;
	}

	public HorizontalLayout getHl() {
		return hl;
	}

	public void setHl(HorizontalLayout hl) {
		this.hl = hl;
	}
	
	

}
