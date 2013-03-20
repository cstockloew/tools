package org.universaal.uaaltools.configurationEditor.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.universaal.uaaltools.configurationEditor.editors.MultiPageEditor;

public class WidgetListener implements ModifyListener {
	
	private MultiPageEditor mpe;
	
	public WidgetListener(MultiPageEditor mpe) {
		this.mpe = mpe;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		
		
		if(e.widget instanceof Text) {
			Text wi = (Text) e.widget;
			
			if(WidgetMapping.get(wi) == WidgetMapping.ELEMENT){
				
				WidgetMapping.getElement(wi).setText(wi.getText());
				//System.out.println(WidgetMapping.getElement(wi).getValue());
				
			} else if (WidgetMapping.get(wi) == WidgetMapping.ATTRIBUTE) {
				
				WidgetMapping.getAttribute(wi).setValue(wi.getText());
				//System.out.println(WidgetMapping.getAttribute(wi).getValue());
			}
		
			
			
		}
		
		
		
		
		mpe.editorChanged();
	}

}
