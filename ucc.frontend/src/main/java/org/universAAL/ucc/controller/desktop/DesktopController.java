package org.universAAL.ucc.controller.desktop;

import org.universAAL.ucc.windows.PreferencesWindow;
import org.universAAL.ucc.windows.SearchWindow;
import org.universAAL.ucc.windows.ToolWindow;
import org.universAAL.ucc.windows.UccUI;

import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class DesktopController implements Button.ClickListener {
	private UccUI app;
	private Window main;
	
	public DesktopController(UccUI app) {
		this.app = app;
		this.main = app.getMainWindow();
//		this.app.getSearchButton().addListener(this);
//		this.app.getStartButton().addListener(this);
	}


	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == app.getStartButton()) {
			if (main.getChildWindows().size() > 0) {
				for (Window w : main.getChildWindows()) {
					if (w instanceof ToolWindow) {
						main.removeWindow(w);
					} else {
						ToolWindow startWindow = /*ToolWindow.getTooWindowInstance(app);*/ new ToolWindow(app);
						main.addWindow(startWindow);
					}
				}
			} else {
				ToolWindow startWin = /*ToolWindow.getTooWindowInstance(app);*/ new ToolWindow(app);
				main.addWindow(startWin);
			}
		}
		if (event.getButton() == app.getSearchButton()) {
			SearchWindow sWin = new SearchWindow(app);
			main.addWindow(sWin);
		}
		if (event.getButton() == app.getLogin()) {
			main.removeWindow(app.getLoginWindow());
			main.removeComponent(app.getVLog());
			main.setContent(app.createContent(this));
			//ToDo: implement registerDeployManager and install-method of the DeployManager
			//and call here DeployManager as service
			
			
			
		}
		if(event.getButton() == app.getAdminButton()) {
			PreferencesWindow pref = new PreferencesWindow(app);
			main.addWindow(pref);
		}

	}
	


}
