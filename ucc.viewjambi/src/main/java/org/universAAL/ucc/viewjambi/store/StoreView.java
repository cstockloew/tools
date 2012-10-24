package org.universAAL.ucc.viewjambi.store;

import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_StoreView;

import com.trolltech.qt.core.QSize;

public class StoreView extends SubWindow {
	
	private static Ui_StoreView install_base = new Ui_StoreView();

	public StoreView() {
		super(StoreView.install_base);
		
		this.setMinimumSize(new QSize(500,  500));
		
    	
  	}
	
	
}
