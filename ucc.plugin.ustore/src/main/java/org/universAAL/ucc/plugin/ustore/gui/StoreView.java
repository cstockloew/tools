package org.universAAL.ucc.plugin.ustore.gui;

import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.viewjambi.common.SubWindow;

import ucc.universAAL.ucc.plugin.ustore.gui.juic.Ui_StoreView;

import com.trolltech.qt.core.QSize;

public class StoreView extends SubWindow {
	
	private static Ui_StoreView install_base = new Ui_StoreView();
	private IPluginBase uCCPluginBase = null;
	
	public StoreView(IPluginBase uCCPluginBase) {
		super(StoreView.install_base);
		this.uCCPluginBase = uCCPluginBase;
		
		this.setMinimumSize(new QSize(500,  800));
		
    	
  	}
	
	
	
}
