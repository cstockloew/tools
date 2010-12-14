package org.universaal.tools.dashboard.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

public class StatemachineView extends ViewPart {

	private boolean pressed = false;
	
	public StatemachineView() {
		//TODO Auto-generated constructor stub
	}

	/**
	 * So far only a little printing.
	 */
	@Override
	public void createPartControl(Composite parent) {
		Button setStatusLine = new Button(parent, SWT.PUSH);
		setStatusLine.setText("Set Statusline");
		
		setStatusLine.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				String message = "Hello, hello my yellow friend";
				if(pressed){
					message = "Well, thank you for pressing me";
				}
				setStatusLine(message);
				pressed = !pressed;
			}
		});
		
		Text text = new Text(parent, SWT.BORDER);
		text.setText("Imagine a fantastic user interface here");
		//TODO Make proper functionality
	}
	
	private void setStatusLine(String message){
		IActionBars bars = getViewSite().getActionBars();
		bars.getStatusLineManager().setMessage(message);
	}

	@Override
	public void setFocus() {}

}
