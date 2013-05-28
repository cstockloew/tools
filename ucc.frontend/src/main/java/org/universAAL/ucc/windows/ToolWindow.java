package org.universAAL.ucc.windows;

import java.util.ResourceBundle;

import org.universAAL.ucc.controller.desktop.ToolController;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class ToolWindow extends Window {
	private Label installLabel;
	private Label addLabel;
	private Label configLabel;
	private Label storeLabel;
	private Button installButton;
	private Button configButton;
	private Button personButton;
	private Button editPerson;
	private Button editHW;
	private Button editUC;
	private Button uStoreButton;
	private Button openAAL;
	private Button logoutButton;
	private Button searchButton;
	private Button uninstallButton;
	private TextField searchText;
	private VerticalLayout vl;
	private Window installWindow;
	// public static ToolWindow toolWin;
	private UccUI app;
	private String base;
	private ResourceBundle res;

	public ToolWindow(UccUI app) {
		super("uCC Tools");
		this.app = app;
		base = "resources.ucc";
		res = ResourceBundle.getBundle(base);
		setStyleName(Reindeer.WINDOW_LIGHT);
		vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setSpacing(true);
		Label sep0 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep0);
		installLabel = new Label("<b>"+res.getString("aal.service")+"</b>",
				Label.CONTENT_XHTML);
		vl.addComponent(installLabel);
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.setStyleName("menubutton");
//		hl.setWidth("100%");
		installButton = new Button(res.getString("install.label"));
		installButton.setDescription(res.getString("install.button.tooltip"));
		installButton.setIcon(new ThemeResource(
				"img/Harddisk50x52.png"));
		hl.addComponent(installButton);
		uninstallButton = new Button(res.getString("uninstall.usrv"));
		uninstallButton.setDescription(res.getString("uninstall.button"));
		uninstallButton.setIcon(new ThemeResource("img/Schluessel50x52.png"));
//		uninstallButton.setEnabled(false);
		hl.addComponent(uninstallButton);
		vl.addComponent(hl);
		vl.setComponentAlignment(hl, Alignment.TOP_LEFT);
		addLabel = new Label(res.getString("add.label"), Label.CONTENT_XHTML);
		Label sep1 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep1);
		vl.addComponent(addLabel);
		configButton = new Button(res.getString("add.hardware.button"));
		configButton.setDescription(res.getString("add.hardware.tooltip"));
		configButton.setIcon(new ThemeResource("img/Hardware50x50.png"));
//		configButton.setEnabled(false);
		HorizontalLayout config = new HorizontalLayout();
		config.setSpacing(true);
		config.setStyleName("menubutton");
		config.addComponent(configButton);
		personButton = new Button("Person");
		personButton.setDescription(res.getString("add.person.tooltip"));
//		personButton.setEnabled(false);
		personButton.setIcon(new ThemeResource("img/Person-01.png"));
		config.addComponent(personButton);
		vl.addComponent(config);
		Label sep2 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep2);
		configLabel = new Label(res.getString("control.label"),
				Label.CONTENT_XHTML);
		vl.addComponent(configLabel);
		HorizontalLayout editHl = new HorizontalLayout();
		editHl.setSpacing(true);
		editHl.setStyleName("menubutton");
		editHW = new Button(res.getString("add.hardware.button"));
		editHW.setDescription(res.getString("edit.hardware.tooltip"));
//		editHW.setEnabled(false);
		editHW.setIcon(new ThemeResource("img/Hardware-01.png"));
		editHl.addComponent(editHW);
		editPerson = new Button(res.getString("add.person.button"));
		editPerson.setDescription(res.getString("edit.person.tooltip"));
//		editPerson.setEnabled(false);
		editPerson.setIcon(new ThemeResource("img/Person ohne-01.png"));
		editHl.addComponent(editPerson);
		editUC = new Button(res.getString("config.usrv"));
		editUC.setDescription(res.getString("edit.uc.tooltip"));
//		editUC.setEnabled(false);
		editUC.setIcon(new ThemeResource("img/Zahnrad50x50.png"));
		editHl.addComponent(editUC);
		vl.addComponent(editHl);
		Label sep3 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep3);
		storeLabel = new Label(res.getString("stores.label"),
				Label.CONTENT_XHTML);
		vl.addComponent(storeLabel);
		HorizontalLayout store = new HorizontalLayout();
		store.setStyleName("menubutton");
		store.setSpacing(true);
		uStoreButton = new Button("uStore");
		uStoreButton.setDescription(res.getString("ustore.button.tooltip"));
		uStoreButton.setIcon(new ThemeResource("img/Use Cases 1 ohne-01.png"));

		store.addComponent(uStoreButton);
		openAAL = new Button("openAAL");
		openAAL.setDescription(res.getString("openaal.button.tooltip"));
		openAAL.setIcon(new ThemeResource("img/Openaal_logo.png.jpg"));
		openAAL.setEnabled(false);
		store.addComponent(openAAL);
		vl.addComponent(store);
		Label sep4 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep4);
		HorizontalLayout hh = new HorizontalLayout();
		searchText = new TextField();
		searchText.setEnabled(false);
		searchButton = new Button(res.getString("search.button"));
		searchButton.setEnabled(false);
		logoutButton = new Button(res.getString("logout.button"));

		// logoutButton.setEnabled(false);
		hh.addComponent(searchText);
		hh.addComponent(searchButton);
		vl.addComponent(hh);
		Label sep5 = new Label("<hr/>", Label.CONTENT_XHTML);
		vl.addComponent(sep5);
		vl.addComponent(logoutButton);
		vl.setComponentAlignment(logoutButton, Alignment.BOTTOM_RIGHT);
		setClosable(false);
		setWidth("350px");
		// setHeight(app.getMainWindow().getBrowserWindowHeight()+"px");
		setResizable(false);
		setPositionX(0);
		setPositionY(45);
		setDraggable(false);
		setContent(vl);
		ToolController tc = new ToolController(app, this);
		installButton.addListener(tc);
		uninstallButton.addListener(tc);
		uStoreButton.addListener(tc);
		openAAL.addListener(tc);
		logoutButton.addListener(tc);
		configButton.addListener(tc);
		personButton.addListener(tc);
		editHW.addListener(tc);
		editPerson.addListener(tc);
		editUC.addListener(tc);
	}

	public Button getUninstallButton() {
		return uninstallButton;
	}

	public void setUninstallButton(Button uninstallButton) {
		this.uninstallButton = uninstallButton;
	}

	public Label getInstallLabel() {
		return installLabel;
	}

	public void setInstallLabel(Label installLabel) {
		this.installLabel = installLabel;
	}

	public Label getAddLabel() {
		return addLabel;
	}

	public void setAddLabel(Label addLabel) {
		this.addLabel = addLabel;
	}

	public Label getConfigLabel() {
		return configLabel;
	}

	public void setConfigLabel(Label configLabel) {
		this.configLabel = configLabel;
	}

	public Label getStoreLabel() {
		return storeLabel;
	}

	public void setStoreLabel(Label storeLabel) {
		this.storeLabel = storeLabel;
	}

	public Button getInstallButton() {
		return installButton;
	}

	public void setInstallButton(Button installButton) {
		this.installButton = installButton;
	}

	public Button getConfigButton() {
		return configButton;
	}

	public void setConfigButton(Button configButton) {
		this.configButton = configButton;
	}

	public Button getPersonButton() {
		return personButton;
	}

	public void setPersonButton(Button personButton) {
		this.personButton = personButton;
	}

	public Button getEditPerson() {
		return editPerson;
	}

	public void setEditPerson(Button editPerson) {
		this.editPerson = editPerson;
	}

	public Button getEditHW() {
		return editHW;
	}

	public void setEditHW(Button editHW) {
		this.editHW = editHW;
	}

	public Button getEditUC() {
		return editUC;
	}

	public void setEditUC(Button editUC) {
		this.editUC = editUC;
	}

	public Button getuStoreButton() {
		return uStoreButton;
	}

	public void setuStoreButton(Button uStoreButton) {
		this.uStoreButton = uStoreButton;
	}

	public Button getOpenAAL() {
		return openAAL;
	}

	public void setOpenAAL(Button openAAL) {
		this.openAAL = openAAL;
	}

	public Button getLogoutButton() {
		return logoutButton;
	}

	public void setLogoutButton(Button logoutButton) {
		this.logoutButton = logoutButton;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public TextField getSearchText() {
		return searchText;
	}

	public void setSearchText(TextField searchText) {
		this.searchText = searchText;
	}

	public VerticalLayout getVl() {
		return vl;
	}

	public void setVl(VerticalLayout vl) {
		this.vl = vl;
	}

	public Window getInstallWindow() {
		return installWindow;
	}

	public void setInstallWindow(Window installWindow) {
		this.installWindow = installWindow;
	}

	// public static ToolWindow getToolWin() {
	// return toolWin;
	// }
	//
	// public static void setToolWin(ToolWindow toolWin) {
	// ToolWindow.toolWin = toolWin;
	// }

	public UccUI getApp() {
		return app;
	}

	public void setApp(UccUI app) {
		this.app = app;
	}

}
