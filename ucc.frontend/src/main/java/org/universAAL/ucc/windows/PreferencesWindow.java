package org.universAAL.ucc.windows;

import java.util.Locale;
import java.util.ResourceBundle;

import org.universAAL.ucc.controller.preferences.PreferencesController;
import org.universAAL.ucc.model.preferences.Preferences;

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
	private TextField userTxt2;
	private PasswordField pwdTxt2;
	private NativeSelect langSelect;
	private String base;
	private ResourceBundle bundle;
	private UccUI app;
	private PreferencesController con;
	private HorizontalLayout hl;

	
	public PreferencesWindow(UccUI app, Preferences pref) {
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
		
		//ustore interface access
		Label header = new Label(bundle.getString("header.pref"), Label.CONTENT_XHTML);
		vl.addComponent(header);
		
		userTxt = new TextField(bundle.getString("user.label"));
		userTxt.setImmediate(true);
		userTxt.setValue(pref.getUsername());
		vl.addComponent(userTxt);
		
		pwdTxt = new PasswordField(bundle.getString("pwd.label"));
		pwdTxt.setImmediate(true);
		pwdTxt.setValue(pref.getPassword());
		vl.addComponent(pwdTxt);
		
		Label sep1 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep1);
		
		urlTxt = new TextField(bundle.getString("url.label"));
		urlTxt.setImmediate(true);
		urlTxt.setValue(pref.getShopUrl());
		vl.addComponent(urlTxt);
		
		portTxt = new TextField(bundle.getString("port.label"));
		portTxt.setImmediate(true);
		portTxt.setValue(pref.getPort());
		vl.addComponent(portTxt);
		
		Label sep2 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep2);
		
		//ustore plugin account
		Label plugLabel = new Label("<b>"+bundle.getString("plugin.label")+"</b>", Label.CONTENT_XHTML);
		vl.addComponent(plugLabel);
		
		userTxt2 = new TextField(bundle.getString("user.label"));
		userTxt2.setImmediate(true);
		userTxt2.setValue(pref.getUsername2());
		vl.addComponent(userTxt2);
		

		pwdTxt2 = new PasswordField(bundle.getString("pwd.label"));
		pwdTxt2.setImmediate(true);
		pwdTxt2.setValue(pref.getPassword2());
		vl.addComponent(pwdTxt2);
		
		Label sep3 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep3);
		Label genLabel = new Label("<b>"+bundle.getString("general.label")+"</b>", Label.CONTENT_XHTML);
		vl.addComponent(genLabel);
		
		langSelect = new NativeSelect(bundle.getString("lang.label"));
		langSelect.addItem(bundle.getString("english"));
		langSelect.addItem(bundle.getString("german"));
		if(pref.getLanguage() == null || pref.getLanguage().equals("")) {
			if(Locale.getDefault().getLanguage().equals("de")) {
				langSelect.select(bundle.getString("german"));
			} else {
				langSelect.select(bundle.getString("english"));
			}
		} else {
			if(pref.getLanguage().equals("de")) 
				langSelect.select(bundle.getString("german"));
			else 
				langSelect.select(bundle.getString("english"));
		}
		
		vl.addComponent(langSelect);
		
		hl = new HorizontalLayout();
		save = new Button(bundle.getString("save.button"));
		reset = new Button(bundle.getString("reset.button"));
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

	public TextField getUserTxt2() {
		return userTxt2;
	}

	public void setUserTxt2(TextField userTxt2) {
		this.userTxt2 = userTxt2;
	}

	public PasswordField getPwdTxt2() {
		return pwdTxt2;
	}

	public void setPwdTxt2(PasswordField pwdTxt2) {
		this.pwdTxt2 = pwdTxt2;
	}
	
	

}
