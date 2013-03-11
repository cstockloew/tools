package org.universAAL.ucc.controller.install;

import org.universAAL.ucc.windows.DeployConfigView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class DeployConfigController implements Button.ClickListener {
	private DeployConfigView win;
	
	public DeployConfigController(DeployConfigView win) {
		this.win = win;
		win.getNext().addListener(this);
		win.getPrevious().addListener(this);
		win.getCancel().addListener(this);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == win.getNext()) {
			
		}
		if(event.getButton() == win.getPrevious()) {
			
		}
		if(event.getButton() == win.getCancel()) {
			
		}
		
	}

}
