package org.universaal.tools.packaging.tool.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.PlatformUI;

public class TextExt extends org.eclipse.swt.widgets.Text {

	protected void checkSubclass() {
	}
	
	public TextExt(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void addTooltip(String text){
		final ToolTip t = new ToolTip(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.BALLOON);
		t.setText(text);
		t.setVisible(false);
		
		this.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {
				t.setVisible(false);				
			}

			public void focusGained(FocusEvent e) {
				Text actionWidget = (Text) e.widget;
				Point loc = actionWidget.getParent().toDisplay(actionWidget.getLocation());
                //System.out.println("Widget: loc.x = "+loc.x+" | loc.y = "+loc.y);
                t.setLocation(loc.x+((int)actionWidget.getSize().x/4), loc.y+actionWidget.getSize().y-(int)actionWidget.getSize().y/4);
                t.setVisible(true);				
			}
		});
	}
	
}