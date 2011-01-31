package org.universaal.tools.dashboard.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.part.ViewPart;

public class OverviewView extends ViewPart {

	private Canvas canvas;
	private Button button01;
	private Button button02;

	
	//TODO Fix listners
	private static Listener button01Listener;
	private static Listener button02Listener;
	
	public OverviewView() {}

	@Override
	public void createPartControl(Composite parent) {
		canvas = new Canvas(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		//Here be buttons. 
		button01 = new Button(canvas, SWT.PUSH);
		button01.setBounds(10, 10, 100, 50);
		button01.setText("First button");

		//TODO make sensible event!
/*		button01Listener = new Listener() {
			public void handleEvent(Event event) {
				
				}
				
			};*/
	
		
//		button01.addListener(SWT.Selection, button01Listener);
		
		button02 = new Button(canvas, SWT.PUSH);
		button02.setBounds(10, 50, 100, 50);
		button02.setText("Second button");
		
	//	button02.addListener(SWT.Selection, button02Listener);
		
	}

	@Override
	public void setFocus() {}


}
